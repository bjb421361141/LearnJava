package eshopUtil.entity;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 物流模板设置
 */
public class LogisticsTemplateConfig {
    private String logisticsSchemeCd; //物流方案编码
    private String logisticsSchemeNm; //物流方案名称
    private BigDecimal freeDelivery; //包邮价格
    private List<Config> countryConfig;

    public String getLogisticsSchemeNm() {
        return logisticsSchemeNm;
    }

    public LogisticsTemplateConfig(String logisticsSchemeCd, String logisticsSchemeNm, BigDecimal freeDelivery) {
        this.logisticsSchemeCd = logisticsSchemeCd;
        this.logisticsSchemeNm = logisticsSchemeNm;
        this.freeDelivery = freeDelivery;
    }

    /**
     * 设置方案
     *
     * @param countryCd       国家
     * @param logisticsCharge 物流费用
     */
    public void setConfig(String countryCd, String countryNm, BigDecimal logisticsCharge) {
        if (countryConfig == null) {
            countryConfig = new ArrayList<>();
        }
        countryConfig.add(new Config(countryCd, countryNm, logisticsCharge));
    }

    public Map<String, List<String>> getConfigContent(boolean isShowFeeDetail) {
        TreeMap<String, List<String>> countryFreightInfMap = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                double o1Val = Double.parseDouble(o1)*100;
                double o2Val = Double.parseDouble(o2)*100;
                if (o1Val > o2Val) {
                    return 1;
                } else if (o1Val < o2Val) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
        for (Config config : countryConfig) {
            StringBuilder msg = new StringBuilder();
            if (isShowFeeDetail) {
                msg.append(config.countryNm + "(运费：" + config.logisticsCharge.setScale(2,BigDecimal.ROUND_UP) + ")-简称：" + config.countryCd);
            } else {
                msg.append(config.countryNm );
            }
            Double dp = config.discountPercent.setScale(2,BigDecimal.ROUND_HALF_DOWN).doubleValue();
            if (dp > 1) {
                List<String> tmp = countryFreightInfMap.getOrDefault("0", new ArrayList<String>());
                tmp.add(msg.toString());
                countryFreightInfMap.put("0",tmp);
            } else if(dp > 0 && dp < 1) {
                List<String> tmp = countryFreightInfMap.getOrDefault(dp.toString(), new ArrayList<String>());
                tmp.add(msg.toString());
                countryFreightInfMap.put(dp.toString(),tmp);
            } else {
                List<String> tmp = countryFreightInfMap.getOrDefault("-1", new ArrayList<String>());
                tmp.add(msg.toString());
                countryFreightInfMap.put("-1",tmp);
            }
        }
        return countryFreightInfMap;
    }

    class Config {
        String countryCd;
        String countryNm;
        BigDecimal logisticsCharge;
        BigDecimal discountPercent;

        public Config(String countryCd, String countryNm, BigDecimal logisticsCharge) {
            this.countryCd = countryCd;
            this.countryNm = countryNm;
            this.logisticsCharge = logisticsCharge;
            BigDecimal rto = freeDelivery.divide(logisticsCharge, 2, 1);  //fixme 需要向下取整
            this.discountPercent = rto;
        }
    }


}
