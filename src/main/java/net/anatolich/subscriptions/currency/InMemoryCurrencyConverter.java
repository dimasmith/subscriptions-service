package net.anatolich.subscriptions.currency;

import java.util.Currency;
import java.util.Set;
import net.anatolich.subscriptions.currency.domain.ExchangeRate;
import net.anatolich.subscriptions.subscription.domain.CurrencyConverter;
import net.anatolich.subscriptions.subscription.domain.Money;
import org.springframework.stereotype.Component;

@Component
public class InMemoryCurrencyConverter implements CurrencyConverter {

    private final Set<ExchangeRate> exchangeRates;

    public InMemoryCurrencyConverter() {
        this.exchangeRates = Set.of(
            ExchangeRate.of("USD", "UAH", 28.34),
            ExchangeRate.of("EUR", "UAH", 33.18),
            ExchangeRate.of("UAH", "USD", 1 / 28.34),
            ExchangeRate.of("UAH", "EUR", 1 / 33.18)
        );
    }

    @Override
    public Money convert(Money monetaryValue, Currency toCurrency) {
        if (monetaryValue.getCurrency().equals(toCurrency)) {
            return monetaryValue;
        }
        return exchangeRates.stream()
            .filter(rate -> rate.supportsConversion(monetaryValue.getCurrency(), toCurrency))
            .findFirst()
            .map(rate -> rate.convert(monetaryValue))
            .orElseThrow(() -> new IllegalStateException("unsupported exchange from {} to {}".formatted(
                monetaryValue.getCurrency(), toCurrency
            )));
    }
}
