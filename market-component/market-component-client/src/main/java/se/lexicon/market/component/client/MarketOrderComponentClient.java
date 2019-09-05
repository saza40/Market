package se.lexicon.market.component.client;

import se.lexicon.market.component.domain.MarketOrder;
import se.lexicon.market.component.domain.MarketOrders;

import java.util.Set;

/**
 * @author Magnus Poromaa {@literal <mailto:magnus.poromaa@so4it.com/>}
 */
public interface MarketOrderComponentClient {

    Set<String> getInstruments(String ssn);
    MarketOrders getMarketOrders(String instrument, String ssn);

    // Delivering back the orderId so it later can be mached with the deals
    Boolean placeMarketOrder(MarketOrder marketOrder);

}
