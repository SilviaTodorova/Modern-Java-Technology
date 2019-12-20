package bg.sofia.uni.fmi.mjt.shopping.portal;

import bg.sofia.uni.fmi.mjt.shopping.portal.comparators.PriceComparator;
import bg.sofia.uni.fmi.mjt.shopping.portal.offer.Offer;
import bg.sofia.uni.fmi.mjt.shopping.portal.offer.PremiumOffer;
import bg.sofia.uni.fmi.mjt.shopping.portal.offer.RegularOffer;
import org.junit.Test;

import java.time.LocalDate;
import java.util.SortedSet;
import java.util.TreeSet;

import static junit.framework.TestCase.assertTrue;

public class PriceComparatorTest {
    private static final String NAME = "PRODUCT_NAME";
    private static final String DESCRIPTION = "PRODUCT_DESCRIPTION";
    private static final double PRICE = 20.3;
    private static final double SHIPPING_PRICE = 4;
    private static final double DISCOUNT = 2.231;
    private static final LocalDate DATE = LocalDate.now();
    private static final LocalDate DATE_BEFORE_TWO_DAYS = LocalDate.now().minusDays(2);

    @Test
    public void testPriceComparator() {
        Offer offer1 = new RegularOffer(NAME, DATE, DESCRIPTION, PRICE, SHIPPING_PRICE);
        Offer offer2 = new RegularOffer(NAME, DATE_BEFORE_TWO_DAYS, DESCRIPTION, PRICE + 10, SHIPPING_PRICE);
        Offer offer3 = new RegularOffer(NAME, DATE_BEFORE_TWO_DAYS, DESCRIPTION, PRICE - 20, SHIPPING_PRICE);
        Offer offer4 = new RegularOffer(NAME, DATE_BEFORE_TWO_DAYS, DESCRIPTION, PRICE - 20, SHIPPING_PRICE);
        Offer offer5 = new PremiumOffer(NAME, DATE_BEFORE_TWO_DAYS, DESCRIPTION, PRICE + PRICE, SHIPPING_PRICE, DISCOUNT);
        Offer offer6 = new RegularOffer(NAME, DATE_BEFORE_TWO_DAYS, DESCRIPTION, PRICE - 0.5, SHIPPING_PRICE);

        SortedSet<Offer> sortedOffers = new TreeSet<>(new PriceComparator());
        sortedOffers.add(offer1);
        sortedOffers.add(offer2);
        sortedOffers.add(offer3);
        sortedOffers.add(offer4);
        sortedOffers.add(offer5);
        sortedOffers.add(offer6);

        double[] expected = {4.300000000000001, 23.8, 24.3, 34.3, 43.60542};
        double[] actual = sortedOffers.stream().mapToDouble(Offer::getTotalPrice).toArray();
        assertTrue("Test Price Comparator - Ascending order", compareArrays(expected, actual));
    }

    private boolean compareArrays(double[] first, double[] second) {
        int firstSize = first.length;
        int secondSize = first.length;

        if (firstSize != secondSize) {
            return false;
        }

        for (int index = 0; index < firstSize; index++) {
            if (first[index] != second[index]) {
                return false;
            }
        }
        return true;
    }
}
