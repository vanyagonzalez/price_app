package piv.dto;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * DTO for price representation
 */
public class Price {
    private String productCode;
    private Integer number;
    private Integer depart;
    private Date begin;
    private Date end;
    private Long value;

    public Price(String productCode, Integer number, Integer depart, Date begin, Date end, Long value) {
        this.productCode = productCode;
        this.number = number;
        this.depart = depart;
        this.begin = begin;
        this.end = end;
        this.value = value;
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

    public Date getBegin() {
        return begin;
    }

    public Date getEnd() {
        return end;
    }

    public Long getValue() {
        return value;
    }

    public static Price random() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -1 * RandomUtils.nextInt(1, 100));
        Date endDate = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, -1 * RandomUtils.nextInt(1, 100));
        Date beginDate = calendar.getTime();

        return new Price(
                RandomStringUtils.randomAscii(RandomUtils.nextInt(1, 10))
                , RandomUtils.nextInt(0, 3)
                , RandomUtils.nextInt(0, 3)
                , beginDate
                , endDate
                , RandomUtils.nextLong(0, Long.MAX_VALUE)
        );
    }
}
