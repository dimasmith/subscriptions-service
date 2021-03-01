package net.anatolich.subscriptions.currency.domain.service;

import java.util.Currency;
import net.anatolich.subscriptions.subscription.domain.service.CurrencyConverter;
import net.anatolich.subscriptions.subscription.domain.model.Money;
import org.springframework.stereotype.Component;

@Component
public class DefaultCurrencyConverter implements CurrencyConverter {

    private final ExchangeRateProvidersRepository providers;

    public DefaultCurrencyConverter(ExchangeRateProvidersRepository providers) {
        this.providers = providers;
    }

    @Override
    public Money convert(Money monetaryValue, Currency toCurrency) {
        final var fromCurrency = monetaryValue.getCurrency();
        if (fromCurrency.equals(toCurrency)) {
            return monetaryValue;
        }
        final var exchangeRateProvider = providers.findByName("admin");
        final var exchangeRate = exchangeRateProvider.rateForConversion(fromCurrency, toCurrency)
            .orElseThrow(
                () -> new IllegalStateException("cannot convert from %s to %s".formatted(fromCurrency, toCurrency)));
        return exchangeRate.convert(monetaryValue);
    }
}
