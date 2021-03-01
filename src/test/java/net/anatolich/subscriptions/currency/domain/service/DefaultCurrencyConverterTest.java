package net.anatolich.subscriptions.currency.domain.service;

import java.util.Currency;
import java.util.Set;
import net.anatolich.subscriptions.currency.domain.model.ExchangeRate;
import net.anatolich.subscriptions.currency.domain.model.ExchangeRateProvider;
import net.anatolich.subscriptions.subscription.domain.model.Money;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@DisplayName("default currency converter")
class DefaultCurrencyConverterTest {

    private final ExchangeRateProvidersRepository rateProviders = Mockito.mock(ExchangeRateProvidersRepository.class);
    private final DefaultCurrencyConverter converter = new DefaultCurrencyConverter(rateProviders);

    @BeforeEach
    void setUp() {
        var rateProvider = new ExchangeRateProvider();
        rateProvider.updateRates(Set.of(ExchangeRate.of("USD", "EUR", 0.5)));
        Mockito.when(rateProviders.findByName("admin"))
            .thenReturn(rateProvider);
    }

    @Test
    @DisplayName("convert money to the same currency does not change amount")
    void convertCurrencyToItselfReturnsTheSameAmount() {
        final Money tenDollars = Money.of(10, "USD");
        var convertedAmount = converter.convert(tenDollars, Currency.getInstance("USD"));

        Assertions.assertThat(convertedAmount)
            .isEqualTo(tenDollars);
    }

    @Test
    @DisplayName("convert currency")
    void convertCurrency() {
        final Money tenDollars = Money.of(10, "USD");
        var amountInEuro = converter.convert(tenDollars, Currency.getInstance("EUR"));

        Assertions.assertThat(amountInEuro)
            .isEqualTo(Money.of(5, "EUR"));
    }

    @Test
    @DisplayName("convert unsupported currency throws an exception")
    void convertUnsupportedCurrency() {
        final Money tenEuro = Money.of(10, "EUR");
        var usd = Currency.getInstance("USD");

        Assertions.assertThatExceptionOfType(IllegalStateException.class)
            .isThrownBy(() -> converter.convert(tenEuro, usd));
    }
}
