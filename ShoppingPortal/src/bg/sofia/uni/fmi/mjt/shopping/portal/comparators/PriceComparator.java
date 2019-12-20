package bg.sofia.uni.fmi.mjt.shopping.portal.comparators;

import bg.sofia.uni.fmi.mjt.shopping.portal.offer.Offer;

import java.util.Comparator;

public class PriceComparator implements Comparator<Offer> {
    @Override
    public int compare(Offer firstOffer, Offer secondOffer) {
        if (firstOffer.equals(secondOffer)) {
            return 0;
        }

        double firstOfferTotalPrice = firstOffer.getTotalPrice();
        double secondOfferTotalPrice = secondOffer.getTotalPrice();

        int difference = Double.compare(firstOfferTotalPrice, secondOfferTotalPrice);
        if (difference == 0) {
            return firstOffer.compareTo(secondOffer);
        } else {
            return difference;
        }
    }
}

