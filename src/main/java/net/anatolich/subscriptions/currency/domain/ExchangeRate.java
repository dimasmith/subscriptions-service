package net.anatolich.subscriptions.currency.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.anatolich.subscriptions.subscription.domain.Money;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = {"sourceCurrency", "targetCurrency"})
@ToString
public class ExchangeRate {

    private Currency sourceCurrency;
    private Currency targetCurrency;
    private BigDecimal rate;
    private LocalDateTime updatedOn;

    public static ExchangeRate of(String sourceCurrency, String targetCurrency, double rate) {
        return new ExchangeRate(
            Currency.getInstance(sourceCurrency),
            Currency.getInstance(targetCurrency),
            BigDecimal.valueOf(rate),
            LocalDateTime.now()
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
