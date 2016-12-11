package piv.model;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

/**
 * class for PriceKey entities
 */

public class PriceKey {
    private Long id;
    private String productCode;
    private Integer number;
    private Integer depart;

    public PriceKey(String productCode, Integer number, Integer depart) {
        this.productCode = productCode;
        this.number = number;
        this.depart = depart;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductCode() {
        return productCode;
    }

    public Integer getNumber() {
        return number;
    }

    public Integer getDepart() {
        return depart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PriceKey)) return false;

        PriceKey key = (PriceKey) o;

        if (!productCode.equals(key.productCode)) return false;
        if (!number.equals(key.number)) return false;
        return depart.equals(key.depart);

    }

    @Override
    public int hashCode() {
        int result = productCode.hashCode();
        result = 31 * result + number.hashCode();
        result = 31 * result + depart.hashCode();
        return result;
    }

    public static PriceKey random() {
        return new PriceKey(
                RandomStringUtils.randomAscii(RandomUtils.nextInt(1, 10))
                , RandomUtils.nextInt(0, 3)
                , RandomUtils.nextInt(0, 3)
        );
    }
}
