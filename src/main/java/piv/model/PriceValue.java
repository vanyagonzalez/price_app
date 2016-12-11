package piv.model;

import org.apache.commons.lang3.RandomUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * Class for history entities
 */
public class PriceValue implements Comparable<PriceValue> {
    private Date begin;
    private Date end;
    private Long value;

    public PriceValue(Date begin, Date end, Long value) {
        this.begin = begin;
        this.end = end;
        this.value = value;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Long getValue() {
        return value;
    }

    public Date getBegin() {
        return begin;
    }

    public Date getEnd() {
        return end;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    /**
     * compare price periods.
     * @param priceValue value for comparing
     * @return -1 -- if period is older than argument period; +1 if period is newer; 0 if periods are intersected
     */
    public int compareTo(PriceValue priceValue) {
        int beginCompareResult = begin.compareTo(priceValue.begin);
        int endCompareResult = end.compareTo(priceValue.end);
        return beginCompareResult < 0 && endCompareResult < 0 ? -1 : beginCompareResult > 0 && endCompareResult > 0 ? 1 : 0;
    }

    public static PriceValue random() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -1 * RandomUtils.nextInt(1, 100));
        Date endDate = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, -1 * RandomUtils.nextInt(1, 100));
        Date beginDate = calendar.getTime();

        return new PriceValue(beginDate, endDate, RandomUtils.nextLong(0, Long.MAX_VALUE));
    }
}
