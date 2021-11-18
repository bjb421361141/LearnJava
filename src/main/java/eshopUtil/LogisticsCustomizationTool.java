package eshopUtil;

import eshopUtil.Exception.BusinessException;
import eshopUtil.entity.FreightTemplate;
import eshopUtil.entity.LogisticsTemplateConfig;
import eshopUtil.entity.Programme;
import org.springframework.util.StringUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static eshopUtil.util.Constants.LogisticsSchemeNmMap;

/**
 * 物流定制工具类(普货)
 */
public class LogisticsCustomizationTool {

    /**
     * 物流类别集合
     */
    public enum LogisticsSchemeCollection {

        Economics("AE_CN_SUPER_ECONOMY_G,CAINIAO_EXPEDITED_ECONOMY,CAINIAO_SUPER_ECONOMY_SG,CAINIAO_SUPER_ECONOMY"),
        SimpleEasy("CAINIAO_ECONOMY,CAINIAO_ECONOMY_SG"),
        Standard("CAINIAO_STANDARD,AE_CAINIAO_STANDARD,EMS_ZX_ZX_US")
//        SpecialGoods(""),
//        CentralizedTransportation("")
        ;
        String value;

        LogisticsSchemeCollection(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    static Map<String, String> countryNmMap = new HashMap<>();

    static {
        Properties prop = new Properties();
        try {
            String file = "F:\\workSpace\\LearnJava\\src\\main\\resources\\conf\\properties\\country_mapper.properties";
            InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "UTF-8");
            BufferedReader read = new BufferedReader(isr);
            prop.load(read);
            for (String key : prop.stringPropertyNames()) {
                countryNmMap.put(key, prop.getProperty(key));
            }
            read.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    List<FreightTemplate> freightTemplateList = new ArrayList<>();  //物流计费模板类，每个FreightTemplate 是单独的一条路线

    public LogisticsCustomizationTool(LogisticsSchemeCollection logisticsSchemeTp) {
        String propertiesPath = LogisticsCustomizationTool.class.getResource("/").getPath() + "/conf/properties/";
        String[] logisticsSchemeCds = logisticsSchemeTp.getValue().split(",");
        for (String logisticsSchemeCd : logisticsSchemeCds) {
            FreightTemplate tmp = new FreightTemplate(logisticsSchemeCd, propertiesPath + "/" + logisticsSchemeCd + ".properties");
            freightTemplateList.add(tmp);
        }
    }

    /**
     * 根据国家名称获取国家简称编号
     *
     * @param countryNms 国家名称
     * @return
     */
    private static List<String> getCountryCd(String countryNms) {
        List<String> countryCds = new ArrayList<>();
        String compareStr = "," + countryNms + ",";
        for (String countryCd : countryNmMap.keySet()) {
            if (compareStr.contains("," + countryNmMap.get(countryCd) + ",")) {
                countryCds.add(countryCd);
            }
        }
        return countryCds;
    }

    /**
     * 根据国家和重量获取最优的路线方案
     * Programme:方案可能为空
     *
     * @param countryCds 国家简称列表
     * @param weight     重量
     * @return 国家 - 最优方案
     */
    private Map<String, Programme> getOptimalProgramme(List<String> countryCds, double weight) {
        Map<String, Programme> ansMap = new HashMap<>();
        for (String countryCd : countryCds) {
            Programme tmp = new Programme();
            tmp.setCountryCd(countryCd);
            tmp.setWeight(weight);
            for (FreightTemplate template : freightTemplateList) {
                BigDecimal fee = new BigDecimal("-1");
                if (template.getFreightMap().containsKey(countryCd)) {
                    fee = template.getFreightMap().get(countryCd).getFreightCharge(weight);
                }
                if( StringUtils.isEmpty(tmp.getPrice()) || (fee.compareTo(new BigDecimal(-1)) > 0 && tmp.getPrice().compareTo(fee) > 0)) {
                    tmp.setPrice(fee);
                    tmp.setLogisticsSchemeCd(template.getLogisticsSchemeCd());
                }
            }
            ansMap.put(countryCd, tmp);
        }
        return ansMap;
    }

    /**
     * 根据国家和重量算出最优的路线，并根据必须包邮国家制定合适的包邮价格
     * 将包邮国家进行排序取出最大值即可
     * fixme 优化最优邮费计算方法
     *
     * @param countryCds           测算国家
     * @param weight               测算重量
     * @param freeChargeCountryCds 包邮国家
     * @return
     */
    public BigDecimal getOptimalFreight(List<String> countryCds, double weight, List<String> freeChargeCountryCds) {
        //获取最优包邮运费
        Map<String, Programme> ansMap = this.getOptimalProgramme(countryCds, weight);
        BigDecimal maxPriceCharge = new BigDecimal(-1);
        for (String freeChargeCountryCd : freeChargeCountryCds) {
            BigDecimal tmp = ansMap.get(freeChargeCountryCd).getPrice();
            if (tmp != null && maxPriceCharge.compareTo(tmp) < 0) {
                maxPriceCharge = tmp;
            }
        }
        return maxPriceCharge;
    }

    public BigDecimal getOptimalFreight(String countryNms, double weight, String freeChargeCountryNms) {
        List<String> countryCd = getCountryCd(countryNms);
        List<String> freeFreightCountryCd = getCountryCd(freeChargeCountryNms);
        return this.getOptimalFreight(countryCd, weight, freeFreightCountryCd);
    }


    /**
     * 根据重量，包邮价格获得该工具类下各个路线的物流模板设置
     *
     * @param weight       重量
     * @param freeDelivery 包邮价格
     * @return
     */
    public List<LogisticsTemplateConfig> getLogisticsTemplateConfig(double weight, BigDecimal freeDelivery) {
        List<LogisticsTemplateConfig> ansConfig = new ArrayList<>();
        for (FreightTemplate template : freightTemplateList) {
            String LogisticsSchemeNm = LogisticsSchemeNmMap.get(template.getLogisticsSchemeCd());
            LogisticsTemplateConfig ltcObj = new LogisticsTemplateConfig(template.getLogisticsSchemeCd(), LogisticsSchemeNm, freeDelivery);
            for (FreightTemplate.Freight freight : template.getFreightLst()) {
                BigDecimal tmpPrice = freight.getFreightCharge(weight);
                ltcObj.setConfig(freight.getCountryCd(), countryNmMap.get(freight.getCountryCd()), tmpPrice);
            }
            ansConfig.add(ltcObj);
        }
        return ansConfig;
    }

    /**
     * 单纯计算运费
     */
    public List<Programme> getFreight(String logisticsSchemeCd ,String countryNms, double weight) throws BusinessException {
        if(StringUtils.isEmpty(logisticsSchemeCd)) {
            throw new BusinessException("未指定物流路线");
        }
        List<Programme> tmp = new ArrayList<>();
        List<String> countryCd = getCountryCd(countryNms);
        for (FreightTemplate template : freightTemplateList) {
            if(logisticsSchemeCd.equals(template.getLogisticsSchemeCd())) {
                Map<String, FreightTemplate.Freight> freightMap = template.getFreightMap();
                for (String country : countryCd) {
                    Programme p  = new Programme();
                    p.setCountryCd(country);
                    p.setCountryNm(countryNmMap.get(country));
                    p.setLogisticsSchemeCd(logisticsSchemeCd);
                    p.setWeight(weight);
                    if(freightMap.containsKey(country)) {
                        BigDecimal price = freightMap.get(country).getFreightCharge(weight);
                        p.setPrice(price);
                    } else {
                        p.setPrice(new BigDecimal(-1));
                    }
                }
            }
        }
        if(tmp.size() <= 0){
            throw new BusinessException("未找到指定物流路径");
        }
        return tmp;
    }
}
