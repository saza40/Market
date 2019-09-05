package se.lexicon.market.component.service;


import com.so4it.gs.rpc.Broadcast;
import com.so4it.gs.rpc.Routing;
import se.lexicon.market.component.domain.MarketOrder;
import se.lexicon.market.component.domain.MarketOrders;

import java.math.BigDecimal;
import java.util.Set;

public interface MarketOrderComponentService {

    String DEFAULT_BEAN_NAME = "marketComponentService";

    @Broadcast(reducer = InstrumentRemoteResultReducer.class)
    Set<String> getInstruments(String ssn);

    MarketOrders getMarketOrders(@Routing String Instrument, String ssn);

    // Returning back the OrderId from the created order
    Boolean placeMarketOrder(@Routing("getInstrument") MarketOrder marketOrder);

    @Broadcast(reducer = BigDecimalRemoteResultReducer.class)
    BigDecimal getTotalMarketValueOfAllMarkets();

}
