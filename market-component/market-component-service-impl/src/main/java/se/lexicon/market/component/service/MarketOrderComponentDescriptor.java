package se.lexicon.market.component.service;

import com.so4it.gs.rpc.Component;
import com.so4it.gs.rpc.ServiceBindingType;


@Component(
        name = "checkout",
        serviceProviders = {
                MarketOrderComponentServiceProvider.class, MarketOrderComponentServiceProvider.class,
        },
        defaultServiceType = ServiceBindingType.GS_REMOTING
)
public class MarketOrderComponentDescriptor {
}
