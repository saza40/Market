package se.lexicon.market.component.service;

import com.so4it.gs.rpc.Service;
import com.so4it.gs.rpc.ServiceBindingType;
import com.so4it.gs.rpc.ServiceProvider;

@ServiceProvider
public interface MarketOrderComponentServiceProvider {

    @Service(value = ServiceBindingType.GS_REMOTING, name = MarketOrderComponentService.DEFAULT_BEAN_NAME)
    MarketOrderComponentService getMarketComponentService();
}
