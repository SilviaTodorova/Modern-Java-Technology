package bg.sofia.uni.fmi.mjt.shopping.portal.offer;

import java.time.LocalDate;
import java.util.Objects;

public abstract class AbstractOffer implements Offer {
    private String productName;
    private LocalDate date;
    private String description;
    private double price;
    private double shippingPrice;

    public AbstractOffer(String productName, LocalDate date, String description, double price, double shippingPrice) {
        this.setProductName(productName);
        this.setDate(date);
        this.setDescription(description);
        this.setPrice(price);
        this.setShippingPrice(shippingPrice);
    }

    @Override
    public String getProductName() {
        return this.productName;
    }

    @Override
    public LocalDate getDate() {
        return this.date;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public double getPrice() {
        return this.price;
    }

    @Override
    public double getShippingPrice() {
        return this.shippingPrice;
    }

    @Override
    public double getTotalPrice() {
        return this.price + this.shippingPrice;
    }

    @Override
    public boolean isSubmittedInLastNDays(int n) {
        LocalDate now = LocalDate.now();
        LocalDate beforeNDays = now.minusDays(n - 1);

        if (this.date.equals(now) || this.date.equals(beforeNDays)) {
            return true;
        }

        return this.date.isAfter(beforeNDays) && this.date.isBefore(now);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getProductName(), this.getDate(), this.getTotalPrice());
    }

    @Override
    public int compareTo(Offer other) {
        if (this.equals(other)) {
            return 0;
        }

        LocalDate otherDate = other.getDate();
        return this.date.compareTo(otherDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null) {
            return false;
        }

        AbstractOffer other = (AbstractOffer) o;

        String productName = this.getProductName();
        String productNameLowerCase = productName.toLowerCase();

        String otherProductName = other.getProductName();
        String otherProductNameLowerCase = otherProductName.toLowerCase();

        boolean equalsProductName = Objects.equals(productNameLowerCase, otherProductNameLowerCase);
        boolean equalsDate = Objects.equals(this.getDate(), other.getDate());
        boolean equalsTotal = Objects.equals(this.getTotalPrice(), other.getTotalPrice());

        return equalsProductName && equalsDate && equalsTotal;
    }

    private void setProductName(String productName) {
        this.productName = productName;
    }

    private void setDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException();
        }

        this.date = date;
    }

    private void setDescription(String description) {
        if (description == null) {
            throw new IllegalArgumentException();
        }

        this.description = description;
    }

    private void setPrice(double price) {
        if (price <= 0) {
            throw new IllegalArgumentException();
        }

        this.price = price;
    }

    private void setShippingPrice(double shippingPrice) {
        if (shippingPrice < 0) {
            throw new IllegalArgumentException();
        }

        this.shippingPrice = shippingPrice;
    }
}
