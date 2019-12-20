package bg.sofia.uni.fmi.mjt.shopping.portal.offer;

import java.time.LocalDate;

public class PremiumOffer extends AbstractOffer {
    private static final double SCALE = Math.pow(10, 2);
    private static final double LOWER_LIMIT = 0;
    private static final double UPPER_LIMIT = 100;

    private double discount;

    public PremiumOffer(String name, LocalDate date, String desc, double price, double shipping, double discount) {
        super(name, date, desc, price, shipping);
        this.setDiscount(discount);
    }

    private void setDiscount(double discount) {
        if (discount < LOWER_LIMIT || discount > UPPER_LIMIT) {
            throw new IllegalArgumentException();
        }

        this.discount = Math.round(discount * SCALE) / SCALE;
    }

    @Override
    public double getTotalPrice() {
        double totalPrice = super.getTotalPrice();
        double percent = this.discount / UPPER_LIMIT;
        double discountOfPrice = totalPrice * percent;
        return totalPrice - discountOfPrice;
    }
}
