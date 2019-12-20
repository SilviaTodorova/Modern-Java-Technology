package bg.sofia.uni.fmi.mjt.shopping.portal;

import java.time.LocalDate;
import java.util.Objects;

public class PriceStatistic implements Comparable<PriceStatistic> {
    private LocalDate date;
    private double averagePrice;
    private double lowerPrice;

    public PriceStatistic(LocalDate date, double averagePrice, double lowerPrice) {
        this.setDate(date);
        this.setAveragePrice(averagePrice);
        this.setLowerPrice(lowerPrice);
    }

    /**
     * Returns the date for which the statistic is
     * collected.
     */
    public LocalDate getDate() {
        return this.date;
    }

    /**
     * Returns the lowest total price from the offers
     * for this product for the specific date.
     */
    public double getLowestPrice() {
        return this.lowerPrice;
    }

    /**
     * Return the average total price from the offers
     * for this product for the specific date.
     */
    public double getAveragePrice() {
        return averagePrice;
    }

    public void setDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException();
        }

        this.date = date;
    }

    public void setAveragePrice(double averagePrice) {
        if (averagePrice < 0) {
            throw new IllegalArgumentException();
        }

        this.averagePrice = averagePrice;
    }

    public void setLowerPrice(double lowerPrice) {
        if (lowerPrice < 0) {
            throw new IllegalArgumentException();
        }

        this.lowerPrice = lowerPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PriceStatistic other = (PriceStatistic) o;
        boolean equalsAveragePrice = Double.compare(other.getAveragePrice(), averagePrice) == 0;
        boolean equalsLowerPrice = Double.compare(other.getLowestPrice(), lowerPrice) == 0;
        boolean equalsDate = Objects.equals(other.getDate(), date);

        return equalsAveragePrice && equalsLowerPrice && equalsDate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, averagePrice, lowerPrice);
    }

    @Override
    public int compareTo(PriceStatistic other) {
        if (this.equals(other)) {
            return 0;
        }

        LocalDate date = this.getDate();
        LocalDate otherDate = other.getDate();
        return otherDate.compareTo(date);
    }
}