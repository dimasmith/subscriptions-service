package net.anatolich.subscriptions.currency.domain;

import net.anatolich.subscriptions.subscription.domain.Money;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("exchange rate")
class ExchangeRateTest {

    @Test
    @DisplayName("convert amount from one currency to another")
    void convertAmount() {
        var rate = ExchangeRate.of("USD", "UAH", 27.5);
        var oneDollar = Money.of(1, "USD");
        var tenDollars = Money.of(10, "USD");

        Assertions.assertThat(rate.convert(oneDollar))
            .isEqualTo(Money.of(27.5, "UAH"));

        Assertions.assertThat(rate.convert(tenDollars))
            .isEqualTo(Money.of(275, "UAH"));
    }

    @Test
    @DisplayName("refuse conversion when money is not in source currency")
    void refuseConversionWhenSuppliedMonetaryValueIsNotInSourceCurrency() {
        var rate = ExchangeRate.of("USD", "UAH", 27.5);
        var euroValue = Money.of(10, "EUR");

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
            .as("money currency is different from the source currency")
            .isThrownBy(() -> rate.convert(euroValue));
    }
}
