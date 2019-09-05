package se.lexicon.market.api.client;

import se.lexicon.market.component.domain.MarketOrder;
import se.lexicon.market.component.domain.MarketOrders;

import java.util.Set;

public interface MarketApiClient {
    String DEFAULT_API_BEAN_NAME = "marketApiClient";

    Set<String> getInstruments(String ssn);
    MarketOrders getMarketOrders(String instrument, String ssn);

    // Delivering back the orderId so it later can be mached with the deals
    Boolean placeMarketOrder(MarketOrder marketOrder);

}
