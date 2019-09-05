package se.lexicon.market.api.client;

import se.lexicon.market.api.MarketApiServiceGrpc;
import com.so4it.common.util.object.Required;
import com.so4it.ft.core.FaultTolerantBean;
import com.so4it.metric.springframework.MetricsBean;
import se.lexicon.market.component.domain.MarketOrder;
import se.lexicon.market.component.domain.MarketOrders;

import java.util.Set;

@FaultTolerantBean(groupKey = MarketApiClientImpl.MARKET_API_CLIENT_NAME)
@MetricsBean(name = MarketApiClientImpl.MARKET_API_CLIENT_NAME)
public class MarketApiClientImpl implements MarketApiClient {

    static final String MARKET_API_CLIENT_NAME = "MARKET_API_CLIENT";

    private final MarketApiServiceGrpc.MarketApiServiceBlockingStub marketApiServiceBlockingStub;

    MarketApiClientImpl(MarketApiServiceGrpc.MarketApiServiceBlockingStub marketApiServiceBlockingStub) {
        this.marketApiServiceBlockingStub = Required.notNull(marketApiServiceBlockingStub, "marketApiServiceBlockingStub");
    }

//    @Override
//    public com.seb.account.component.domain.AccountBalance createAccountBalance(com.seb.account.component.domain.CreateAccountBalanceRequest request) {
//        return AccountBalanceApiMapper.map(accountApiServiceBlockingStub.createAccountBalance(AccountBalanceApiMapper.map(request)));
//    }
//
//    @Override
//    public com.seb.account.component.domain.AccountTransaction createAccountTransaction(com.seb.account.component.domain.CreateAccountTransactionRequest request) {
//        return AccountTransactionApiMapper.map(accountApiServiceBlockingStub.createAccountTransaction(AccountTransactionApiMapper.map(request)));
//    }

    @Override
    public Set<String> getInstruments(String ssn) {
        return null;
    }

    @Override
    public MarketOrders getMarketOrders(String instrument, String ssn) {
        return null;}

    @Override
    public Boolean placeMarketOrder(MarketOrder marketOrder) {
        return null;}

}
