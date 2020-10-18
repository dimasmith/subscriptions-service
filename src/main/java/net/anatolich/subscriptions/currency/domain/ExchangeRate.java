package net.anatolich.subscriptions.currency.domain;

import java.math.BigDecimal;
import java.util.Currency;
import lombok.EqualsAndHashCode;
import lombok.Value;
import net.anatolich.subscriptions.subscription.domain.Money;

@Value
@EqualsAndHashCode(of = {"sourceCurrency", "targetCurrency"})
public class ExchangeRate {

    Currency sourceCurrency;
    Currency targetCurrency;
    BigDecimal rate;

    public static ExchangeRate of(String sourceCurrency, String targetCurrency, double rate) {
        return new ExchangeRate(
            Currency.getInstance(sourceCurrency),
            Currency.getInstance(targetCurrency),
            BigDecimal.valueOf(rate)
        );
    }

    /**
     * Convert monetary value into the target currency.
     *
     * @throws IllegalArgumentException when monetary value currency is not equal to source currency
     */
    public Money convert(Money monetaryValue) {
        if (!supportsConversion(monetaryValue.getCurrency(), targetCurrency)) {
            throw new IllegalArgumentException("supplied monetary value currency differs from the source currency");
        }
        return Money.of(monetaryValue.getAmount().multiply(rate), targetCurrency);
    }

    public boolean supportsConversion(Currency from, Currency to) {
        return sourceCurrency.equals(from) && targetCurrency.equals(to);
    }
}
