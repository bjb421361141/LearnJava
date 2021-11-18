package eshopUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import eshopUtil.Exception.BusinessException;
import eshopUtil.entity.FreightTemplate;
import eshopUtil.entity.LogisticsTemplateConfig;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.util.CollectionUtils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class Main {
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
        LogisticsCustomizationTool tool = new LogisticsCustomizationTool(LogisticsCustomizationTool.LogisticsSchemeCollection.Economics); //经济类
        BigDecimal freeDelivery = tool.getOptimalFreight(countryNm, weight, freeFreightCountryNm);
        List<LogisticsTemplateConfig> logisticsTemplateConfigs = tool.getLogisticsTemplateConfig(weight,freeDelivery);
        if(!CollectionUtils.isEmpty(logisticsTemplateConfigs)) {
            HSSFWorkbook wb = new HSSFWorkbook();
            for (LogisticsTemplateConfig logisticsTemplateConfig : logisticsTemplateConfigs) {
                Map<String, List<String>> tmp = logisticsTemplateConfig.getConfigContent(true);
                writeDataToSheet(wb,logisticsTemplateConfig.getLogisticsSchemeNm(),tmp);
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
     * @param wb excel 对象
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
