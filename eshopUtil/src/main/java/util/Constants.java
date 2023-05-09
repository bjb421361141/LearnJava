package util;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 静态类
 */
public class Constants {
    public static BigDecimal EXCHANGE_RATE = new BigDecimal(6.4);
    public static BigDecimal FREIGHT_PROPORTION = new BigDecimal(0.6); //运费占比不能超过60%

    public static Map<String, String> LogisticsSchemeNmMap = new HashMap<String, String>();

    static {
        //经济
        LogisticsSchemeNmMap.put("AE_CN_SUPER_ECONOMY_G", "菜鸟超级经济Global");
        LogisticsSchemeNmMap.put("CAINIAO_EXPEDITED_ECONOMY", "菜鸟专线经济");
        LogisticsSchemeNmMap.put("CAINIAO_SUPER_ECONOMY_SG", "菜鸟特货专线－超级经济");
        LogisticsSchemeNmMap.put("CAINIAO_SUPER_ECONOMY", "菜鸟超级经济");
        //简易
        LogisticsSchemeNmMap.put("CAINIAO_ECONOMY", "AliExpress 无忧物流-简易");
        LogisticsSchemeNmMap.put("CAINIAO_ECONOMY_SG", "菜鸟特货专线－简易");
        //标准
        LogisticsSchemeNmMap.put("CAINIAO_STANDARD", "AliExpress 无忧物流-标准");
        LogisticsSchemeNmMap.put("AE_CAINIAO_STANDARD", "菜鸟专线-标准");
        LogisticsSchemeNmMap.put("EMS_ZX_ZX_US", "e邮宝");
    }

    ;
}
