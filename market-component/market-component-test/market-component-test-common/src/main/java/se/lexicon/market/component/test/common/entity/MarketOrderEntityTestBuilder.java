package se.lexicon.market.component.test.common.entity;

import se.lexicon.market.component.domain.Money;
import se.lexicon.market.component.domain.MarketPriceType;
import se.lexicon.market.component.domain.Side;
import se.lexicon.market.component.entity.MarketOrderEntity;
import com.so4it.common.util.object.Required;
import com.so4it.test.domain.AbstractTestBuilder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;

/**
 * @author Magnus Poromaa {@literal <mailto:magnus.poromaa@so4it.com/>}
 */
public class MarketOrderEntityTestBuilder extends AbstractTestBuilder<MarketOrderEntity> {

    private MarketOrderEntity.Builder builder;


    public MarketOrderEntityTestBuilder(MarketOrderEntity.Builder builder) {
        this.builder = Required.notNull(builder, "builder");
        this.builder
                //.withId("1111111111")
                .withSsn("1111111111")
                .withOrderId("1111111111-1")
                .withInsertionTimestamp(Instant.now())
                .withAmount(BigDecimal.TEN)
                .withInstrument("ABB")
                .withNoOfItems(100)
                .withMinMaxValue(Money.builder()
                        .withAmount((BigDecimal.valueOf(50d)))
                        .withCurrency(Currency.getInstance("SEK"))
                        .build())
                .withSide(Side.BUY)
                .withMarketPriceType(MarketPriceType.MARKET)
                .withOrderBookId("ABB/SEK")
                .withNoOfItemsToMatch(100)
                .withAllItemsMatched(false);
    }

    public MarketOrderEntityTestBuilder withSsn(String ssn){
        builder.withSsn(ssn);
        return this;
    }

    public MarketOrderEntityTestBuilder withOrderId(String orderId){
        builder.withOrderId(orderId);
        return this;
    }

    public MarketOrderEntityTestBuilder withAmount(BigDecimal amount){
        builder.withAmount(amount);
        return this;
    }

    public MarketOrderEntityTestBuilder withInstrument(String instrument){
        builder.withInstrument(instrument);
        return this;
    }

    public MarketOrderEntityTestBuilder withNoOfItems(Integer noOfItems){
        builder.withNoOfItems(noOfItems);
        return this;
    }

     public MarketOrderEntityTestBuilder withMinMaxValue(Money money){
        builder.withMinMaxValue(money);
        return this;
    }

    public MarketOrderEntityTestBuilder withSide(Side side){
        builder.withSide(side);
        return this;
    }
    public MarketOrderEntityTestBuilder withMarketPriceType(MarketPriceType marketPriceType){
        builder.withMarketPriceType(marketPriceType);
        return this;
    }

    public MarketOrderEntityTestBuilder withOrderBookId(String marketBookId){
        builder.withOrderBookId(marketBookId);
        return this;
    }

    public MarketOrderEntityTestBuilder withNoOfMatchedItems(Integer noOfMatchedItems){
        builder.withNoOfItemsToMatch(noOfMatchedItems);
        return this;
    }

    public MarketOrderEntityTestBuilder withAllItemsMatched(Boolean allItemsMatched){
        builder.withAllItemsMatched(allItemsMatched);
        return this;
    }


    public static MarketOrderEntityTestBuilder builder() {
        return new MarketOrderEntityTestBuilder(MarketOrderEntity.builder());
    }

    @Override
    public MarketOrderEntity build() {
        return builder.build();
    }
}
