package org.sigma.sigmastatistics.model.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class StatisticsDTO {

    private BigDecimal sum;
    private BigDecimal avg;
    private BigDecimal max;
    private BigDecimal min;
    private long count;

    public StatisticsDTO(BigDecimal sum, BigDecimal avg, BigDecimal max, BigDecimal min, long count) {
        this.setSum(sum);
        this.setAvg(avg);
        this.setMax(max);
        this.setMin(min);
        this.setCount(count);
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum.setScale(2, RoundingMode.HALF_UP);
    }

    public void setAvg(BigDecimal avg) {
        this.avg = avg.setScale(2, RoundingMode.HALF_UP);
    }

    public void setMax(BigDecimal max) {
        this.max = max.setScale(2, RoundingMode.HALF_UP);
    }

    public void setMin(BigDecimal min) {
        this.min = min.setScale(2, RoundingMode.HALF_UP);
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getCount() {
        return count;
    }

    public BigDecimal getSum() {
        return sum;
    }
}
