package se.lexicon.market.api.client;

import se.lexicon.market.api.MarketApiServiceGrpc;
import com.so4it.api.AbstractApiClientProviderBeanPublisher;
import com.so4it.api.ApiFrameworkConfiguration;
import com.so4it.api.importer.ApiClientProviderDefinition;
import com.so4it.api.util.StatusRuntimeExceptionBeanProxy;
import com.so4it.common.bean.BeanContext;
import com.so4it.common.bean.BeanProxyInvocationHandler;
import com.so4it.ft.core.FaultTolerantBeanProxy;
import com.so4it.metric.springframework.MetricsTimerBeanProxy;
import com.so4it.request.core.RequestContextBeanProxy;
import io.grpc.ManagedChannel;

public class MarketApiProviderBeanPublisher extends AbstractApiClientProviderBeanPublisher {

    @Override
    public void publish(ApiClientProviderDefinition apiProviderDefinition,
                        BeanContext beanContext,
                        ApiFrameworkConfiguration configuration,
                        ManagedChannel managedChannel) {
        MarketApiServiceGrpc.AccountApiServiceBlockingStub agreementApiServiceBlockingStub = MarketApiServiceGrpc.newBlockingStub(managedChannel);
        se.lexicon.account.api.client.MarketApiClient accountApiClientImpl = new MarketApiClientImpl(agreementApiServiceBlockingStub);
        se.lexicon.account.api.client.MarketApiClient accountApiClient = BeanProxyInvocationHandler.create(
                se.lexicon.account.api.client.MarketApiClient.class,
                marketApiClientImpl,
                StatusRuntimeExceptionBeanProxy.create(),
                MetricsTimerBeanProxy.create(accountApiClientImpl),
                //FaultTolerantBeanProxy.create(accountApiClientImpl, beanContext),
                RequestContextBeanProxy.create());
        beanContext.register(se.lexicon.account.api.client.MarketApiClient.DEFAULT_API_BEAN_NAME, accountApiClient);
    }

}



