package bg.sofia.uni.fmi.mjt.shopping.portal.offer;

import java.time.LocalDate;

public class RegularOffer extends AbstractOffer {
    public RegularOffer(String name, LocalDate date, String desc, double price, double shipping) {
        super(name, date, desc, price, shipping);
    }
}
