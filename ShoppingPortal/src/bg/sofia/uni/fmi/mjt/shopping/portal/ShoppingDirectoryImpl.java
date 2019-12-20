package bg.sofia.uni.fmi.mjt.shopping.portal;

import bg.sofia.uni.fmi.mjt.shopping.portal.comparators.PriceComparator;
import bg.sofia.uni.fmi.mjt.shopping.portal.exceptions.NoOfferFoundException;
import bg.sofia.uni.fmi.mjt.shopping.portal.exceptions.OfferAlreadySubmittedException;
import bg.sofia.uni.fmi.mjt.shopping.portal.exceptions.ProductNotFoundException;
import bg.sofia.uni.fmi.mjt.shopping.portal.offer.Offer;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.HashMap;

public class ShoppingDirectoryImpl implements ShoppingDirectory {
    private static final int DAYS = 30;
    private final Map<String, Set<Offer>> offers;

    public ShoppingDirectoryImpl() {
        this.offers = new HashMap<>();
    }

    @Override
    public Collection<Offer> findAllOffers(String productName) throws ProductNotFoundException {
        if (productName == null) {
            throw new IllegalArgumentException();
        }

        if (!offers.containsKey(productName)) {
            throw new ProductNotFoundException();
        }

        // Offers must be sorted by price in ascending order
        Collection<Offer> offers = this.offers.get(productName);

        Set<Offer> submittedOffersInLastNDays = new LinkedHashSet<>();

        // Filter these offers which aren't submitted in the last N days
        for (Offer offer : offers) {
            if (offer.isSubmittedInLastNDays(DAYS)) {
                submittedOffersInLastNDays.add(offer);
            }
        }

        return submittedOffersInLastNDays;
    }

    @Override
    public Offer findBestOffer(String productName) throws ProductNotFoundException, NoOfferFoundException {
        Collection<Offer> offersSubmittedInLastNDays = this.findAllOffers(productName);

        if (offersSubmittedInLastNDays.size() == 0) {
            throw new NoOfferFoundException();
        }

        return offersSubmittedInLastNDays.iterator().next();
    }

    @Override
    public Collection<PriceStatistic> collectProductStatistics(String productName) throws ProductNotFoundException {
        if (productName == null) {
            throw new IllegalArgumentException();
        }

        if (!offers.containsKey(productName)) {
            throw new ProductNotFoundException();
        }

        Collection<Offer> offers = this.offers.get(productName);
        Collection<LocalDate> dates = distinctDates(offers);
        Set<PriceStatistic> statistics = new TreeSet<>();
        for (LocalDate date : dates) {
            Collection<Offer> offersFilteredByDate = filterByDate(date, offers);
            offers.removeAll(offersFilteredByDate);

            PriceStatistic statistic = createStatistic(date, offersFilteredByDate);
            statistics.add(statistic);
        }

        return statistics;
    }

    @Override
    public void submitOffer(Offer offer) throws OfferAlreadySubmittedException {
        if (offer == null) {
            throw new IllegalArgumentException();
        }

        String productName = offer.getProductName();
        Set<Offer> offersForProduct = new TreeSet<>(new PriceComparator());

        if (this.offers.containsKey(productName)) {
            offersForProduct = this.offers.get(productName);
            if (offersForProduct.contains(offer)) {
                throw new OfferAlreadySubmittedException();
            }
        }

        offersForProduct.add(offer);
        this.offers.put(productName, offersForProduct);
    }

    private static Collection<LocalDate> distinctDates(Collection<Offer> offers) {
        Set<LocalDate> dates = new HashSet<>();

        for (Offer offer : offers) {
            LocalDate date = offer.getDate();
            dates.add(date);
        }

        return dates;
    }

    private static Collection<Offer> filterByDate(LocalDate date, Collection<Offer> offers) {
        Set<Offer> filtered = new TreeSet<>(new PriceComparator());

        for (Offer offer : offers) {
            LocalDate offerDate = offer.getDate();
            if (offerDate.equals(date)) {
                filtered.add(offer);
            }
        }

        return filtered;
    }

    private static double sumTotalPrices(Collection<Offer> offers) {
        double sumTotalPrices = 0;

        for (Offer offer : offers) {
            sumTotalPrices += offer.getTotalPrice();
        }

        return sumTotalPrices;
    }

    private static PriceStatistic createStatistic(LocalDate date, Collection<Offer> offers) {
        int countOffers = offers.size();
        double sumTotalPrices = 0;
        double averagePrice = 0;
        double lowerPrice = 0;

        if (countOffers > 0) {
            sumTotalPrices = sumTotalPrices(offers);
            averagePrice = sumTotalPrices / countOffers;
            Offer offerWithLowerPrice = offers.iterator().next();
            lowerPrice = offerWithLowerPrice.getTotalPrice();
        }

        return new PriceStatistic(date, averagePrice, lowerPrice);
    }
}
