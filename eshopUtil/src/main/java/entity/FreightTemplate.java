package entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import exception.BusinessException;
import util.Constants;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

import static java.util.stream.Collectors.toList;

/**
 * 运费模板类
 */
public class FreightTemplate {
    private String LogisticsSchemeCd; //模板代码
    private List<Freight> freightLst = new ArrayList<>();
    private Map<String, Freight> freightMap = new HashMap<>();

    public String getLogisticsSchemeCd() {
        return LogisticsSchemeCd;
    }

    public List<Freight> getFreightLst() {
        return freightLst;
    }

    public Map<String, Freight> getFreightMap() {
        return freightMap;
    }

    /**
     * 根据json数据 生成具体路线的物流定价
     * fixme 后续需要加上价格限制 ，国家 订单价格（人民币）
     *
     * @param logisticsSchemeCd 物流模板名称
     * @param logisticsPropPath 物流模板json数据文件路径
     */
    public FreightTemplate(String logisticsSchemeCd, String logisticsPropPath) {
        LogisticsSchemeCd = logisticsSchemeCd;
        //生成物流信息
        Properties prop = new Properties();
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(logisticsPropPath));
            prop.load(in);     ///加载属性列表
            JSONObject priceStrictMap = null;
            if (prop.containsKey("StrictPriceLst")) {
                String strictPriceLstStr = prop.getProperty("StrictPriceLst");
                priceStrictMap = JSON.parseObject(strictPriceLstStr);
            }
            for (String key : prop.stringPropertyNames()) {
                if("StrictPriceLst".equals(key)) {
                    continue;
                }
                BigDecimal priceStrict = null;
                if (priceStrictMap != null) {
                    if (priceStrictMap.containsKey(key)) {
                        priceStrict = new BigDecimal((String) priceStrictMap.get(key));
                    } else if (priceStrictMap.containsKey("Default")) {
                        priceStrict = new BigDecimal((String) priceStrictMap.get("Default")).multiply(Constants.EXCHANGE_RATE);
                        if (priceStrict.compareTo(new BigDecimal(-1)) > 0) {
                            priceStrict = priceStrict.multiply(Constants.EXCHANGE_RATE); //换算成人民币
                        }
                    } else {
                        throw new BusinessException("未找到该国家及Default最低定价！");
                    }
                } else {
                    priceStrict = new BigDecimal(-1);
                }
                Freight tmp = new Freight(key, prop.getProperty(key), priceStrict);
                freightLst.add(tmp);  //国家编码 和 运费json字符串
                freightMap.put(key, tmp);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }

    /**
     * 运费类
     */
    public class Freight {
        private String countryCd; //国家编码
        private List<Section> freightSections; //物流定价区间
        private BigDecimal strictPrice;  //限价订单价格

        /**
         * 传入符合要求的json数据构造运费类
         *
         * @param freightSectionJsons
         */
        public Freight(String countryCd, String freightSectionJsons, BigDecimal strictPrice) throws BusinessException {
            this.countryCd = countryCd;
            JSONArray array = JSON.parseArray(freightSectionJsons);
            List<Section> list = JSONObject.parseArray(array.toJSONString(), Section.class);
            this.freightSections = list.stream().sorted(Comparator.comparing(Section::getLowerWeight)).collect(toList()); //1.8版本以上才可以使用
            this.strictPrice = strictPrice;
            if (!isContinuousInterval()) {
                throw new BusinessException(countryCd + "该运费模板不是一个连续的区间");
            }
        }

        public String getCountryCd() {
            return countryCd;
        }

        public List<Section> getFreightSections() {
            return freightSections;
        }

        /**
         * 判断对象是否是连续的区间
         *
         * @return
         */
        public boolean isContinuousInterval() {
            BigDecimal tmpNum = new BigDecimal(Integer.MIN_VALUE);
            BigDecimal minvalue = new BigDecimal(Integer.MIN_VALUE);
            for (Section freightSection : this.freightSections) {
                if (tmpNum.compareTo(minvalue) != 0) {
                    BigDecimal lowerWeight = new BigDecimal(freightSection.getLowerWeight());
                    if (tmpNum.compareTo(lowerWeight) != 0) {
                        return false;
                    }
                }
                tmpNum = new BigDecimal(freightSection.getHigherWeight());
            }
            return tmpNum.compareTo(minvalue) != 0;
        }

        /**
         * 根据重量计算运费信息 (运费超过限价的60%就自动变为 不计算)
         *
         * @param weight 重量
         * @return
         */
        public BigDecimal getFreightCharge(double weight) {
            for (Section freightSection : this.freightSections) {
                if (freightSection.isInSection(weight)) {
                    BigDecimal weightCharge = freightSection.getCharge(weight);
                    if (this.strictPrice.equals(new BigDecimal(-1))) {
                        return weightCharge;
                    } else {
                        return strictPrice.multiply(Constants.FREIGHT_PROPORTION).compareTo(weightCharge) < 0 ? new BigDecimal(-1) : weightCharge;
                    }
                }
            }
            return new BigDecimal(-1);
        }

        /**
         * 根据重量列表计算运费
         *
         * @param weights 重量列表
         * @return
         */
        public BigDecimal[] getFreightChargeForArr(double[] weights) {
            BigDecimal[] ansCharges = new BigDecimal[weights.length];
            for (int i = 0; i < weights.length; i++) {
                double weight = weights[i];
                for (Section freightSection : this.freightSections) {
                    if (freightSection.isInSection(weight)) {
                        ansCharges[i] = freightSection.getCharge(weight);
                        break;
                    }
                }
            }
            return ansCharges;
        }
    }

    /**
     * 运费区间
     */
    public static class Section {
        private double lowerWeight;
        private double higherWeight;
        private BigDecimal serviceCharge;   //服务费  单位:美金/kg
        private BigDecimal registrationFee; // 挂号费 单位:美金

        public double getLowerWeight() {
            return lowerWeight;
        }

        public void setLowerWeight(double lowerWeight) {
            this.lowerWeight = lowerWeight;
        }

        public double getHigherWeight() {
            return higherWeight;
        }

        public void setHigherWeight(double higherWeight) {
            this.higherWeight = higherWeight;
        }

        public BigDecimal getServiceCharge() {
            return serviceCharge;
        }

        public void setServiceCharge(BigDecimal serviceCharge) {
            this.serviceCharge = serviceCharge;
        }

        public BigDecimal getRegistrationFee() {
            return registrationFee;
        }

        public void setRegistrationFee(BigDecimal registrationFee) {
            this.registrationFee = registrationFee;
        }

        public Section() {

        }

        public Section(double lowerWeight, double higherWeight, BigDecimal serviceCharge, BigDecimal registrationFee) throws BusinessException {
            if (lowerWeight >= higherWeight) {
                throw new BusinessException("最低值不能大于等于最高值");
            }
            if (lowerWeight < 0 || higherWeight < 0) {
                throw new BusinessException("最低值,最高值不能小于0");
            }
            this.lowerWeight = lowerWeight;
            this.higherWeight = higherWeight;
            this.serviceCharge = serviceCharge;
            this.registrationFee = registrationFee;
        }

        /**
         * 左不包右包
         * @param weight
         * @return
         */
        public boolean isInSection(double weight) {
            return this.lowerWeight < weight && weight <= this.higherWeight;
        }

        /**
         * 计算公式：服务费 * 重量 + 挂号费
         *
         * @param weight 重量 kg
         * @return
         */
        public BigDecimal getCharge(double weight) {
            if (isInSection(weight)) {
                BigDecimal bweight = new BigDecimal(weight);
                return this.serviceCharge.multiply(bweight).add(this.registrationFee);
            }
            return new BigDecimal(0);
        }
    }
}

