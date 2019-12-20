package bg.sofia.uni.fmi.mjt.shopping.portal.offer;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

import java.time.LocalDate;

public class OfferTest {
    private static final int CONVERT_RATE = 100;
    private static final int DAYS = 30;
    private static final double SCALE = Math.pow(10, 2);
    private static final String NAME = "PRODUCT_NAME";
    private static final String NAME_LOWERCASE = "product_name";
    private static final String NAME_DIFF = "name";
    private static final String DESCRIPTION = "PRODUCT_DESCRIPTION";
    private static final double PRICE = 20.3;
    private static final double SHIPPING_PRICE = 4;
    private static final double DISCOUNT = 2.231;
    private static final LocalDate DATE = LocalDate.now();
    private static final LocalDate DATE_BEFORE_TWO_DAYS = LocalDate.now().minusDays(2);

    @Test
    public void testPremiumOfferGetName() {
        Offer offer = new PremiumOffer(NAME, DATE, DESCRIPTION, PRICE, SHIPPING_PRICE, DISCOUNT);
        assertEquals("Test PremiumOffer - GetProductName", NAME, offer.getProductName());
    }

    @Test
    public void testRegularOfferGetName() {
        Offer offer = new RegularOffer(NAME, DATE, DESCRIPTION, PRICE, SHIPPING_PRICE);
        assertEquals("Test RegularOffer - GetProductName", NAME, offer.getProductName());
    }

    @Test
    public void testPremiumOfferGetDescription() {
        Offer offer = new PremiumOffer(NAME, DATE, DESCRIPTION, PRICE, SHIPPING_PRICE, DISCOUNT);
        assertEquals("Test PremiumOffer - GetDescription", DESCRIPTION, offer.getDescription());
    }

    @Test
    public void testRegularOfferGetDescription() {
        Offer offer = new RegularOffer(NAME, DATE, DESCRIPTION, PRICE, SHIPPING_PRICE);
        assertEquals("Test RegularOffer - GetDescription", DESCRIPTION, offer.getDescription());
    }

    @Test
    public void testPremiumOfferGetTotalPrice() {
        Offer offer = new PremiumOffer(NAME, DATE, DESCRIPTION, PRICE, SHIPPING_PRICE, DISCOUNT);

        double total = PRICE + SHIPPING_PRICE;
        double discount = Math.round(DISCOUNT * SCALE) / SCALE;
        double expectedTotalPrice = total - total * (discount / CONVERT_RATE);
        double actualTotalPrice = offer.getTotalPrice();
        assertEquals("Test PremiumOffer - GetTotalPrice", expectedTotalPrice, actualTotalPrice);
    }

    @Test
    public void testRegularOfferGetTotalPrice() {
        Offer offer = new RegularOffer(NAME, DATE, DESCRIPTION, PRICE, SHIPPING_PRICE);
        assertEquals("Test RegularOffer - GetTotalPrice", PRICE + SHIPPING_PRICE, offer.getTotalPrice());
    }

    @Test
    public void testRegularOfferEquals() {
        Offer offer1 = new RegularOffer(NAME, DATE, DESCRIPTION, PRICE, SHIPPING_PRICE);
        Offer offer2 = new RegularOffer(NAME_LOWERCASE, DATE, DESCRIPTION, PRICE, SHIPPING_PRICE);
        assertTrue("Test Equals RegularOffer", offer1.equals(offer2));
    }

    @Test
    public void testRegularOfferNotEquals() {
        Offer offer1 = new RegularOffer(NAME, DATE, DESCRIPTION, PRICE, SHIPPING_PRICE);
        Offer offer2 = new RegularOffer(NAME_DIFF, DATE, DESCRIPTION, PRICE, SHIPPING_PRICE);
        assertFalse("Test Not Equals RegularOffer", offer1.equals(offer2));
    }

    @Test
    public void testRegularOfferCompareToSmaller() {
        Offer offer1 = new RegularOffer(NAME, DATE_BEFORE_TWO_DAYS, DESCRIPTION, PRICE, SHIPPING_PRICE);
        Offer offer2 = new RegularOffer(NAME_DIFF, DATE, DESCRIPTION, PRICE, SHIPPING_PRICE);
        int actual = offer1.compareTo(offer2);
        assertTrue("Test RegularOffer CompareTo - Smaller", actual < 0);
    }

    @Test
    public void testRegularOfferCompareToBigger() {
        Offer offer1 = new RegularOffer(NAME, DATE, DESCRIPTION, PRICE, SHIPPING_PRICE);
        Offer offer2 = new RegularOffer(NAME, DATE_BEFORE_TWO_DAYS, DESCRIPTION, PRICE, SHIPPING_PRICE);
        assertTrue("Test RegularOffer CompareTo - Bigger", offer1.compareTo(offer2) > 0);
    }

    @Test
    public void testOfferIsSubmittedInLastNDaysWithDateNow() {
        Offer offer = new RegularOffer(NAME, DATE, DESCRIPTION, PRICE, SHIPPING_PRICE);
        assertTrue("Test RegularOffer is submitted in last n days", offer.isSubmittedInLastNDays(DAYS));
    }

    @Test
    public void testOfferIsSubmittedInLastNDaysWithDateTwoDaysBefore() {
        Offer offer = new RegularOffer(NAME, DATE.minusDays(2), DESCRIPTION, PRICE, SHIPPING_PRICE);
        assertTrue("Test RegularOffer is submitted in last n days", offer.isSubmittedInLastNDays(DAYS));
    }

    @Test
    public void testOfferIsSubmittedInLastNDaysWithDate2NDaysBefore() {
        Offer offer = new RegularOffer(NAME, DATE.minusDays(2 * DAYS), DESCRIPTION, PRICE, SHIPPING_PRICE);
        assertFalse("Test RegularOffer is submitted in last n days", offer.isSubmittedInLastNDays(DAYS));
    }
}
