package se.lexicon.market.api.mapper;

import se.lexicon.market.component.domain.Money;
import com.so4it.common.util.Mapper;

import java.math.BigDecimal;
import java.util.Currency;

public final class MoneyApiMapper {

    private static final int SCALE = 1_000_000_000;

    private MoneyApiMapper() {
    }

    public static Money map(com.google.type.Money money) {
        return money == com.google.type.Money.getDefaultInstance() ? null : Mapper.of(money, Money::builder)
                .map(money::getCurrencyCode, Currency::getInstance, Money.Builder::withCurrency)
                .map(() -> money, MoneyApiMapper::mapAmount, Money.Builder::withAmount)
                .build(Money.Builder::build);
    }

    private static BigDecimal mapAmount(com.google.type.Money money) {
        int fractionDigits = Currency.getInstance(money.getCurrencyCode()).getDefaultFractionDigits();
        fractionDigits = fractionDigits == -1 ? 0 : fractionDigits;
        return BigDecimal.valueOf(getUnscaledValue(money))
                .divide(BigDecimal.valueOf(SCALE), fractionDigits, BigDecimal.ROUND_HALF_UP);
    }

    private static long getUnscaledValue(com.google.type.Money money) {
        return SCALE * money.getUnits() + money.getNanos();
    }

    public static com.google.type.Money map(Money money) {
        return money == null ? com.google.type.Money.getDefaultInstance() : Mapper.of(money, com.google.type.Money::newBuilder)
                .map(money::getCurrency, Currency::getCurrencyCode, com.google.type.Money.Builder::setCurrencyCode)
                .map(money::getAmount, MoneyApiMapper::mapUnits, com.google.type.Money.Builder::setUnits)
                .map(money::getAmount, MoneyApiMapper::mapNanos, com.google.type.Money.Builder::setNanos)
                .build(com.google.type.Money.Builder::build);
    }

    private static long mapUnits(BigDecimal bigDecimal) {
        return bigDecimal.toBigInteger().intValueExact();
    }

    private static int mapNanos(BigDecimal bigDecimal) {
        return bigDecimal.remainder(BigDecimal.ONE).multiply(BigDecimal.valueOf(SCALE)).intValueExact();
    }
}
