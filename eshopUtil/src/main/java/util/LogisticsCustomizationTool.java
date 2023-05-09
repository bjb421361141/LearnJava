package util;

import entity.FreightTemplate;
import entity.LogisticsTemplateConfig;
import entity.Programme;
import exception.BusinessException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

import static util.Constants.LogisticsSchemeNmMap;

//import static eshopUtil.util.Constants.LogisticsSchemeNmMap;

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
                if (tmp.getPrice() != null || (fee.compareTo(new BigDecimal(-1)) > 0 && tmp.getPrice().compareTo(fee) > 0)) {
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
    public List<Programme> getFreight(String logisticsSchemeCd, String countryNms, double weight) throws BusinessException {
        if (StringUtils.isEmpty(logisticsSchemeCd)) {
            throw new BusinessException("未指定物流路线");
        }
        List<Programme> tmp = new ArrayList<>();
        List<String> countryCd = getCountryCd(countryNms);
        for (FreightTemplate template : freightTemplateList) {
            if (logisticsSchemeCd.equals(template.getLogisticsSchemeCd())) {
                Map<String, FreightTemplate.Freight> freightMap = template.getFreightMap();
                for (String country : countryCd) {
                    Programme p = new Programme();
                    p.setCountryCd(country);
                    p.setCountryNm(countryNmMap.get(country));
                    p.setLogisticsSchemeCd(logisticsSchemeCd);
                    p.setWeight(weight);
                    if (freightMap.containsKey(country)) {
                        BigDecimal price = freightMap.get(country).getFreightCharge(weight);
                        p.setPrice(price);
                    } else {
                        p.setPrice(new BigDecimal(-1));
                    }
                }
            }
        }
        if (tmp.size() <= 0) {
            throw new BusinessException("未找到指定物流路径");
        }
        return tmp;
    }

    /**
     * 传入某一条路线的运费信息，
     * 输入克重和包邮金额，自动算出包邮国家和折扣比例(包含运费信息)
     * <p>
     * 参数1：物流方式，克重，包邮金额  -->获取单独一条路线的折扣方式
     * 参数2：物流方式列表，克重，测算的国家列表，必须包邮国家列表（非必填） //自动计算合适的包邮金额  -->获取多条路线下最优的物流方式
     * <p>
     * 新思路：
     * 获取克重信息，按传入的模板（多个）计算最低的物流成本及物流方式
     * 根据必须包邮国家指定最低承担包邮价格
     *
     * @param args
     */

    public static void main(String[] args) {
        double weight = 0.6;
        String countryNm = "俄罗斯,乌克兰,白俄罗斯,西班牙,智利,英国,波兰,美国,法国,意大利,以色列,加拿大,荷兰,德国,澳大利亚,土耳其,韩国,日本,泰国,新加坡,印度尼西亚,马来西亚,菲律宾,越南,葡萄牙,墨西哥,比利时,瑞士,捷克,瑞典,新西兰,匈牙利,斯洛伐克,爱尔兰,阿拉伯联合酋长国,沙特阿拉伯,巴西";
        String freeFreightCountryNm = "韩国,日本,波兰,英国,俄罗斯,法国,西班牙,乌克兰,美国,白俄罗斯,德国,荷兰,意大利,澳大利亚,以色列";
        LogisticsCustomizationTool tool = new LogisticsCustomizationTool(LogisticsSchemeCollection.Economics); //经济类
        BigDecimal freeDelivery = tool.getOptimalFreight(countryNm, weight, freeFreightCountryNm);
        List<LogisticsTemplateConfig> logisticsTemplateConfigs = tool.getLogisticsTemplateConfig(weight, freeDelivery);
        if (!CollectionUtils.isEmpty(logisticsTemplateConfigs)) {
            HSSFWorkbook wb = new HSSFWorkbook();
            for (LogisticsTemplateConfig logisticsTemplateConfig : logisticsTemplateConfigs) {
                Map<String, List<String>> tmp = logisticsTemplateConfig.getConfigContent(true);
                writeDataToSheet(wb, logisticsTemplateConfig.getLogisticsSchemeNm(), tmp);
            }
            FileOutputStream output = null;
            try {
                output = new FileOutputStream("C:\\Users\\Baijb\\Desktop\\FreightCharge.xls");
                wb.write(output);
                output.flush();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将特定数据写入excel中
     *
     * @param wb      excel 对象
     * @param sheetNm 表单名称
     * @param data
     */
    private static void writeDataToSheet(HSSFWorkbook wb, String sheetNm, Map<String, List<String>> data) {
        HSSFSheet sheet = wb.createSheet(sheetNm);  //物流路线的名称
        //合并单元格
        int rowidx = 0;
        for (String rtoStr : data.keySet()) {
            int countrySize = data.get(rtoStr).size();
            HSSFRow row = sheet.getRow(rowidx) != null ? sheet.getRow(rowidx) : sheet.createRow(rowidx); //不存在就创建
            HSSFCell cell = row.createCell(0);
            cell.setCellValue(rtoStr); //折扣比例
            if (countrySize > 1) {
                sheet.addMergedRegion(new CellRangeAddress(rowidx, rowidx + countrySize - 1, 0, 0));
            }
            Iterator<String> iterator = data.get(rtoStr).iterator();
            do {
                row.createCell(1).setCellValue(iterator.next());//往临近的位置设置国家信息
                rowidx++;
                row = sheet.createRow(rowidx);
            } while (iterator.hasNext());
        }
    }
}
