package bg.sofia.uni.fmi.mjt.shopping.portal;

import bg.sofia.uni.fmi.mjt.shopping.portal.exceptions.NoOfferFoundException;
import bg.sofia.uni.fmi.mjt.shopping.portal.exceptions.OfferAlreadySubmittedException;
import bg.sofia.uni.fmi.mjt.shopping.portal.exceptions.ProductNotFoundException;
import bg.sofia.uni.fmi.mjt.shopping.portal.offer.Offer;
import bg.sofia.uni.fmi.mjt.shopping.portal.offer.PremiumOffer;
import bg.sofia.uni.fmi.mjt.shopping.portal.offer.RegularOffer;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Collection;

import static junit.framework.TestCase.assertEquals;

public class ShoppingDirectoryImplTest {
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

    @Test(expected = IllegalArgumentException.class)
    public void testSubmitNullOfferThrowIllegalArgumentException() throws ProductNotFoundException, OfferAlreadySubmittedException {
        ShoppingDirectory directory = new ShoppingDirectoryImpl();
        directory.submitOffer(null);
    }

    @Test(expected = OfferAlreadySubmittedException.class)
    public void testSubmitOfferTwiceThrowOfferAlreadySubmittedException() throws ProductNotFoundException, OfferAlreadySubmittedException {
        ShoppingDirectory directory = new ShoppingDirectoryImpl();
        Offer offer = new RegularOffer(NAME, DATE, DESCRIPTION, PRICE, SHIPPING_PRICE);
        directory.submitOffer(offer);
        directory.submitOffer(offer);
    }

    @Test
    public void testSubmitOfferWithTheSameName() throws ProductNotFoundException, OfferAlreadySubmittedException {
        ShoppingDirectory directory = new ShoppingDirectoryImpl();
        Offer offer1 = new RegularOffer(NAME, DATE, DESCRIPTION, PRICE + PRICE, SHIPPING_PRICE);
        Offer offer2 = new RegularOffer(NAME, DATE, DESCRIPTION, PRICE, SHIPPING_PRICE);
        directory.submitOffer(offer1);
        directory.submitOffer(offer2);
    }

    @Test
    public void testSubmitOffersWithDiffDates() throws ProductNotFoundException, OfferAlreadySubmittedException {
        ShoppingDirectory directory = new ShoppingDirectoryImpl();
        Offer offer1 = new RegularOffer(NAME, DATE_BEFORE_TWO_DAYS, DESCRIPTION, PRICE, SHIPPING_PRICE);
        Offer offer2 = new RegularOffer(NAME, DATE, DESCRIPTION, PRICE, SHIPPING_PRICE);
        directory.submitOffer(offer2);
        directory.submitOffer(offer1);
    }

    @Test
    public void testSubmitDifferentOffers() throws ProductNotFoundException, OfferAlreadySubmittedException {
        ShoppingDirectory directory = new ShoppingDirectoryImpl();
        Offer offer1 = new RegularOffer(NAME, DATE, DESCRIPTION, PRICE, SHIPPING_PRICE);
        Offer offer2 = new PremiumOffer(NAME_DIFF, DATE, DESCRIPTION, PRICE + PRICE + PRICE, SHIPPING_PRICE, DISCOUNT);
        Offer offer3 = new PremiumOffer(NAME_DIFF, DATE, DESCRIPTION, PRICE, SHIPPING_PRICE, DISCOUNT);
        directory.submitOffer(offer1);
        directory.submitOffer(offer2);
        directory.submitOffer(offer3);
    }

    @Test(expected = ProductNotFoundException.class)
    public void testFindAllOffersInEmptyDirectoryThrowProductNotFoundException() throws IllegalArgumentException, ProductNotFoundException {
        ShoppingDirectory directory = new ShoppingDirectoryImpl();
        directory.findAllOffers(NAME);
    }

    @Test(expected = ProductNotFoundException.class)
    public void testFindBestOfferInEmptyDirectoryThrowProductNotFoundException() throws IllegalArgumentException, ProductNotFoundException, NoOfferFoundException {
        ShoppingDirectory directory = new ShoppingDirectoryImpl();
        directory.findBestOffer(NAME);
    }

    @Test(expected = ProductNotFoundException.class)
    public void testFindAllOffersWithProductNameNotExistThrowProductNotFoundException() throws IllegalArgumentException, ProductNotFoundException, OfferAlreadySubmittedException {
        ShoppingDirectory directory = new ShoppingDirectoryImpl();
        Offer offer = new RegularOffer(NAME, DATE, DESCRIPTION, PRICE, SHIPPING_PRICE);
        directory.submitOffer(offer);
        directory.findAllOffers(NAME_DIFF);
    }

    @Test(expected = ProductNotFoundException.class)
    public void testFindBestOfferWithProductNameNotExistThrowProductNotFoundException() throws IllegalArgumentException, ProductNotFoundException, OfferAlreadySubmittedException, NoOfferFoundException {
        ShoppingDirectory directory = new ShoppingDirectoryImpl();
        Offer offer = new RegularOffer(NAME, DATE, DESCRIPTION, PRICE, SHIPPING_PRICE);
        directory.submitOffer(offer);
        directory.findBestOffer(NAME_DIFF);
    }

    @Test(expected = NoOfferFoundException.class)
    public void testFindBestOfferWithProductNameExistButSubmittedBeforeNDaysThrowProductNotFoundException() throws IllegalArgumentException, ProductNotFoundException, OfferAlreadySubmittedException, NoOfferFoundException {
        ShoppingDirectory directory = new ShoppingDirectoryImpl();
        Offer offer = new RegularOffer(NAME, DATE.minusDays(50), DESCRIPTION, PRICE, SHIPPING_PRICE);
        directory.submitOffer(offer);
        directory.findBestOffer(NAME);
    }

    @Test
    public void testFindBestOfferWithProductNameExist() throws IllegalArgumentException, ProductNotFoundException, OfferAlreadySubmittedException, NoOfferFoundException {
        ShoppingDirectory directory = new ShoppingDirectoryImpl();
        Offer offer = new RegularOffer(NAME, DATE, DESCRIPTION, PRICE, SHIPPING_PRICE);
        directory.submitOffer(offer);
        Offer bestOffer = directory.findBestOffer(NAME);
        assertEquals("Test best offer", offer, bestOffer);
    }

    @Test
    public void testFindBestOfferInFullDirectory() throws IllegalArgumentException, ProductNotFoundException, OfferAlreadySubmittedException, NoOfferFoundException {
        ShoppingDirectory directory = new ShoppingDirectoryImpl();
        Offer offer1 = new RegularOffer(NAME, DATE, DESCRIPTION, PRICE * PRICE, SHIPPING_PRICE);
        Offer offer2 = new RegularOffer(NAME_DIFF, DATE, DESCRIPTION, PRICE - 10, SHIPPING_PRICE);
        Offer offer3 = new RegularOffer(NAME, DATE.minusDays(10), DESCRIPTION, PRICE, SHIPPING_PRICE);
        Offer offer4 = new RegularOffer(NAME, DATE.minusDays(DAYS * 2), DESCRIPTION, PRICE, SHIPPING_PRICE);
        Offer offer5 = new RegularOffer(NAME, DATE, DESCRIPTION, PRICE - 20, SHIPPING_PRICE);

        directory.submitOffer(offer1);
        directory.submitOffer(offer2);
        directory.submitOffer(offer3);
        directory.submitOffer(offer4);
        directory.submitOffer(offer5);

        Offer bestOffer = directory.findBestOffer(NAME);
        assertEquals("Test best offer", offer5, bestOffer);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindAllOffersWithProductNameNullThrowIllegalArgumentException() throws IllegalArgumentException, ProductNotFoundException {
        ShoppingDirectory directory = new ShoppingDirectoryImpl();
        directory.findAllOffers(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindBestOfferWithProductNameNullThrowIllegalArgumentException() throws IllegalArgumentException, ProductNotFoundException, NoOfferFoundException {
        ShoppingDirectory directory = new ShoppingDirectoryImpl();
        directory.findBestOffer(null);
    }

    @Test
    public void testFindAllOffersWithProductNameExist() throws IllegalArgumentException, ProductNotFoundException, OfferAlreadySubmittedException {
        ShoppingDirectory directory = new ShoppingDirectoryImpl();
        Offer offer1 = new RegularOffer(NAME, DATE, DESCRIPTION, PRICE * PRICE, SHIPPING_PRICE);
        Offer offer2 = new RegularOffer(NAME_DIFF, DATE, DESCRIPTION, PRICE - 10, SHIPPING_PRICE);
        Offer offer3 = new RegularOffer(NAME, DATE.minusDays(10), DESCRIPTION, PRICE, SHIPPING_PRICE);
        Offer offer4 = new RegularOffer(NAME, DATE.minusDays(DAYS * 2), DESCRIPTION, PRICE, SHIPPING_PRICE);
        directory.submitOffer(offer1);
        directory.submitOffer(offer2);
        directory.submitOffer(offer3);
        directory.submitOffer(offer4);
        Collection<Offer> offers = directory.findAllOffers(NAME);
        offers.size();

        assertEquals("Size must be 1", 2, offers.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void collectProductStatisticsNullProductName() throws OfferAlreadySubmittedException, ProductNotFoundException {
        ShoppingDirectory directory = new ShoppingDirectoryImpl();
        directory.collectProductStatistics(null);
    }

    @Test(expected = ProductNotFoundException.class)
    public void collectProductStatisticsNotExistProductName() throws OfferAlreadySubmittedException, ProductNotFoundException {
        ShoppingDirectory directory = new ShoppingDirectoryImpl();
        directory.collectProductStatistics(NAME);
    }

    @Test
    public void collectProductStatistics() throws OfferAlreadySubmittedException, ProductNotFoundException {
        ShoppingDirectory directory = new ShoppingDirectoryImpl();
        Offer offer1 = new RegularOffer(NAME, DATE, DESCRIPTION, PRICE * PRICE, SHIPPING_PRICE);
        Offer offer2 = new RegularOffer(NAME, DATE.minusDays(1), DESCRIPTION, PRICE - 10, SHIPPING_PRICE);
        Offer offer3 = new RegularOffer(NAME, DATE.minusDays(10), DESCRIPTION, PRICE, SHIPPING_PRICE);
        Offer offer4 = new RegularOffer(NAME, DATE.minusDays(DAYS * 2), DESCRIPTION, PRICE, SHIPPING_PRICE);
        Offer offer5 = new RegularOffer(NAME, DATE.minusDays(29), DESCRIPTION, PRICE - 2, SHIPPING_PRICE);
        Offer offer6 = new RegularOffer(NAME, DATE, DESCRIPTION, PRICE, SHIPPING_PRICE);

        directory.submitOffer(offer1);
        directory.submitOffer(offer2);
        directory.submitOffer(offer3);
        directory.submitOffer(offer4);
        directory.submitOffer(offer5);
        directory.submitOffer(offer6);

        var test = directory.collectProductStatistics(NAME);
    }


}
