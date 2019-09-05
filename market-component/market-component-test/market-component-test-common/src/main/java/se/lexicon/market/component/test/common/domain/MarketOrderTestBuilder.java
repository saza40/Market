package se.lexicon.market.component.test.common.domain;

import se.lexicon.market.component.domain.*;
import com.so4it.common.util.object.Required;
import com.so4it.test.domain.AbstractTestBuilder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;

/**
 * @author Magnus Poromaa {@literal <mailto:magnus.poromaa@so4it.com/>}
 */
public class MarketOrderTestBuilder extends AbstractTestBuilder<MarketOrder> {

    private MarketOrder.Builder builder;

    public MarketOrderTestBuilder(MarketOrder.Builder builder) {
        this.builder = Required.notNull(builder, "builder");
        this.builder
                //.withId("1111111111")
                .withSsn("1111111111")
                .withOrderId("1111111111-1")
                .withAmount(BigDecimal.TEN)
                .withInstrument("ABB")
                .withNoOfItems(100)
                .withSide(Side.BUY)
                .withMinMaxValue(Money.builder()
                    .withAmount((BigDecimal.valueOf(50d)))
                    .withCurrency(Currency.getInstance("SEK"))
                    .build())
                .withInsertionTimestamp(Instant.now())
                .withOrderBookId("ABB/SEK")
                .withMarketPriceType(MarketPriceType.MARKET);
    }

    public static MarketOrderTestBuilder builder() {
        return new MarketOrderTestBuilder(MarketOrder.builder());
    }


    public MarketOrderTestBuilder withId(String id){
        builder.withId(id);
        return this;
    }

    public MarketOrderTestBuilder withSsn(String ssn){
        builder.withSsn(ssn);
        return this;
    }

    public MarketOrderTestBuilder withOrderId(String orderId){
        builder.withOrderId(orderId);
        return this;
    }

    public MarketOrderTestBuilder withInstrument(String instrument){
        builder.withInstrument(instrument);
        return this;
    }

    public MarketOrderTestBuilder withNoOfItems(Integer noOfItems){
        builder.withNoOfItems(noOfItems);
        return this;
    }

    public MarketOrderTestBuilder withMinMaxValue(Money money){
        builder.withMinMaxValue(money);
        return this;
    }

    public MarketOrderTestBuilder withSide(Side side){
        builder.withSide(side);
        return this;
    }

    public MarketOrderTestBuilder withMarketPriceType(MarketPriceType marketPriceType){
        builder.withMarketPriceType(marketPriceType);
        return this;
    }

    public MarketOrderTestBuilder withAmount(BigDecimal amount){
        builder.withAmount(amount);
        return this;
    }

    public MarketOrderTestBuilder withInsertionTimestamp(Instant insertionTimestamp){
        builder.withInsertionTimestamp(insertionTimestamp);
        return this;
    }

    public MarketOrderTestBuilder withOrderBookId(String marketBookId){
        builder.withOrderBookId(marketBookId);
        return this;
    }


    @Override
    public MarketOrder build() {
        return builder.build();
    }
}
