package se.lexicon.market.component.domain;

import com.so4it.common.domain.DomainClass;
import com.so4it.common.util.object.Required;
import com.so4it.common.util.object.ValueObject;

import java.math.BigDecimal;
import java.util.Currency;

@DomainClass
public final class Money extends ValueObject {
    private static final long serialVersionUID = 1L;

    private BigDecimal amount;
    private Currency currency;

    private Money() {}
    private Money(Builder builder) {
        this.amount = Required.notNull(builder.amount, "amount");
        this.currency = Required.notNull(builder.currency, "currency");
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    protected Object[] getIdFields() {
        return new Object[] { amount, currency };
    }

    public static Builder builder() {
        return new Builder();
    }

    public final static class Builder implements com.so4it.common.builder.Builder<Money> {

        private BigDecimal amount;
        private Currency currency;

        private Builder() { }

        public Builder withAmount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Builder withCurrency(Currency currency) {
            this.currency = currency;
            return this;
        }

        @Override
        public Money build() {
            return new Money(this);
        }
    }
}
