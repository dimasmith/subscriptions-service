package net.anatolich.subscriptions.subscription.support;


import java.math.BigDecimal;
import java.util.Currency;
import net.anatolich.subscriptions.subscription.domain.CurrencyConverter;
import net.anatolich.subscriptions.subscription.domain.Money;

public class TestCurrencyConverter implements CurrencyConverter {

    private static final Currency UAH = Currency.getInstance("UAH");
    private static final Currency USD = Currency.getInstance("USD");

    @Override
    public Money convert(Money monetaryValue, Currency toCurrency) {
        if (monetaryValue.getCurrency().equals(toCurrency)) {
            return monetaryValue;
        }
        if (monetaryValue.getCurrency().equals(UAH) && toCurrency.equals(USD)) {
            return Money.of(monetaryValue.getAmount().multiply(BigDecimal.valueOf(0.5)), USD);
        }
        if (monetaryValue.getCurrency().equals(USD) && toCurrency.equals(UAH)) {
            return Money.of(monetaryValue.getAmount().multiply(BigDecimal.valueOf(2.0)), UAH);
        }
        throw new IllegalStateException("unsupported currency");
    }
}
