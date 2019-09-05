package se.lexicon.market.component.domain;

import com.so4it.annotation.Allowed;
import com.so4it.common.util.object.Required;
import com.so4it.common.util.object.ValueObject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * @author Magnus Poromaa {@literal <mailto:magnus.poromaa@so4it.com/>}
 */
public class MarketOrder extends ValueObject implements Serializable {

    private static final long serialVersionUID = 2L;

    @Allowed(types = {Allowed.Type.NULLABLE})
    private String id;

    private String ssn;

    private String orderId;

    private BigDecimal amount;

    // Timestamp from core system when the market was made
    private Instant insertionTimestamp;

    // original market
    private String instrument;

    private Integer noOfItems;

    private Money minMaxValue; //Money minValue if SELL, maxValue if BUY;

    private Side side;         // Sell or Buy

    private MarketPriceType marketPriceType; // BUY/SELL at MARKET or LIMITED price

    private String orderBookId;

    private MarketOrder() {
    }

    private MarketOrder(Builder builder) {
        this.id = builder.id;
        this.ssn = Required.notNull(builder.ssn,"ssn");
        this.orderId = Required.notNull(builder.orderId,"orderId");
        this.amount = Required.notNull(builder.amount,"amount");
        this.instrument = Required.notNull(builder.instrument,"instrument");
        this.noOfItems = Required.notNull(builder.noOfItems,"noOfItems");
        this.minMaxValue = Required.notNull(builder.minMaxValue,"minMaxValue");
        this.side = Required.notNull(builder.side,"side");
        this.marketPriceType = Required.notNull(builder.marketPriceType,"marketPriceType");
        this.insertionTimestamp = Required.notNull(builder.insertionTimestamp,"insertionTimestamp");
        this.orderBookId = Required.notNull(builder.orderBookId,"orderBookId");
    }


    public String getId() {
        return id;
    }

    public String getSsn() {
        return ssn;
    }

    public String getOrderId() { return orderId; }

    public String getOrderBookId() {
        return orderBookId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Instant getInsertionTimestamp() { return insertionTimestamp; }

    public String getInstrument() {
        return instrument;
    }

    public Integer getNoOfItems() {
        return noOfItems;
    }

    public Money getMinMaxValue() {
        return minMaxValue;
    }

    public Side getSide() {
        return side;
    }

    public MarketPriceType getMarketPriceType() { return marketPriceType; }

    @Override
    protected Object[] getIdFields() {
        return new Object[]{amount, ssn, insertionTimestamp, instrument, noOfItems, minMaxValue, side, marketPriceType};
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder implements com.so4it.common.builder.Builder<MarketOrder>{

        private String id;

        private String ssn;

        private String orderId;

        private BigDecimal amount;

        private String instrument;

        private Integer noOfItems;

        private Money minMaxValue;

        private Side side;

        private MarketPriceType marketPriceType;

        private String orderBookId;

        private Instant insertionTimestamp;

        public Builder withId(String id){
            this.id = id;
            return this;
        }

        public Builder withSsn(String ssn){
            this.ssn = ssn;
            return this;
        }

        public Builder withOrderId(String orderId){
            this.orderId = orderId;
            return this;
        }

        public Builder withAmount(BigDecimal amount){
            this.amount = amount;
            return this;
        }

        public Builder withInstrument(String instrument){
            this.instrument = instrument;
            return this;
        }

        public Builder withNoOfItems(Integer noOfItems){
            this.noOfItems = noOfItems;
            return this;
        }

        public Builder withMinMaxValue(Money minMaxValue){
            this.minMaxValue = minMaxValue;
            return this;
        }

        public Builder withSide(Side side){
            this.side = side;
            return this;
        }

        public Builder withMarketPriceType(MarketPriceType marketPriceType){
            this.marketPriceType = marketPriceType;
            return this;
        }

        public Builder withInsertionTimestamp(Instant insertionTimestamp){
            this.insertionTimestamp = insertionTimestamp;
            return this;
        }

        public Builder withOrderBookId(String orderBookId){
            this.orderBookId = orderBookId;
            return this;
        }

        @Override
        public MarketOrder build() {
            return new MarketOrder(this);
        }
    }
}
