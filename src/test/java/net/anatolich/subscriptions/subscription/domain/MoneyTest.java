package net.anatolich.subscriptions.subscription.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("money")
class MoneyTest {

    @Nested
    @DisplayName("equality")
    class CheckEquality {

        @Test
        @DisplayName("money with the same amount and currency are equal")
        void moneyEqualsWhenAmountsAndCurrenciesEquals() {
            var monetaryValue = Money.of(10, "USD");
            var otherMonetaryValue = Money.of(10, "USD");

            Assertions.assertThat(monetaryValue)
                .as("monetary values should be equal")
                .isEqualTo(otherMonetaryValue);
        }

        @Test
        @DisplayName("money with different amounts are not equal")
        void moneyWithDifferentAmountsArentEqual() {
            var monetaryValue = Money.of(10, "USD");
            var otherMonetaryValue = Money.of(10.01, "USD");

            Assertions.assertThat(monetaryValue)
                .as("monetary values should not be equal")
                .isNotEqualTo(otherMonetaryValue);
        }

        @Test
        @DisplayName("monetary values with different currencies are not equal")
        void moneyOfDifferentCurrenciesCantBeCompared() {
            var dollarValue = Money.of(10, "USD");
            var euroValue = Money.of(10, "EUR");

            Assertions.assertThat(dollarValue)
                .as("monetary values should not be equal")
                .isNotEqualTo(euroValue);
        }
    }

    @Nested
    @DisplayName("addition")
    class Addition {

        @Test
        @DisplayName("add monetary amounts of single currency")
        void addMonetaryAmountsOfSingleCurrency() {
            var monetaryValue = Money.of(10, "USD");
            var otherValue = Money.of(32, "USD");

            var sum = monetaryValue.add(otherValue);

            Assertions.assertThat(sum)
                .isEqualTo(Money.of(42, "USD"));
        }

        @Test
        @DisplayName("cannot add monetary values of different currencies")
        void name() {
            var dollarAmount = Money.of(10, "USD");
            var euroAmount = Money.of(32, "EUR");

            Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .as("it's illegal to add values of different currencies")
                .isThrownBy(() -> dollarAmount.add(euroAmount));
        }
    }
}
