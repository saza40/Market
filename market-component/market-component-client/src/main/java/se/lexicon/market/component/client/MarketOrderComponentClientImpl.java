package se.lexicon.market.component.client;

import se.lexicon.market.component.domain.MarketOrder;
import se.lexicon.market.component.domain.MarketOrders;
import se.lexicon.market.component.service.MarketOrderComponentService;
import com.so4it.common.util.object.Required;

import java.util.Set;

/**
 * @author Magnus Poromaa {@literal <mailto:magnus.poromaa@so4it.com/>}
 */
public class MarketOrderComponentClientImpl implements MarketOrderComponentClient {

    private MarketOrderComponentService marketOrderComponentService;

    public MarketOrderComponentClientImpl(MarketOrderComponentService marketOrderComponentService) {
        this.marketOrderComponentService = Required.notNull(marketOrderComponentService,"marketComponentService");
    }

    @Override
    public Set<String> getInstruments(String ssn){ return marketOrderComponentService.getInstruments(ssn); }

    @Override
    public MarketOrders getMarketOrders(String instrument, String ssn){
        return marketOrderComponentService.getMarketOrders(instrument,ssn); }

    @Override
    public Boolean placeMarketOrder(MarketOrder marketOrder) {
        return marketOrderComponentService.placeMarketOrder(marketOrder); }

}
