package entity;

import java.math.BigDecimal;

/**
 * 物流方案类
 */
public class Programme {
    private String countryCd; //国家编码
    private String countryNm; //国家编码
    private double weight; //克重
    private BigDecimal price; //价格
    private String LogisticsSchemeCd; //物流方案编码

    public String getCountryCd() {
        return countryCd;
    }

    public void setCountryCd(String countryCd) {
        this.countryCd = countryCd;
    }

    public String getCountryNm() {
        return countryNm;
    }

    public void setCountryNm(String countryNm) {
        this.countryNm = countryNm;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getLogisticsSchemeCd() {
        return LogisticsSchemeCd;
    }

    public void setLogisticsSchemeCd(String logisticsSchemeCd) {
        LogisticsSchemeCd = logisticsSchemeCd;
    }
}
