package net.anatolich.subscriptions.subscription.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("monetary amount")
class MonetaryAmountTest {

    @Nested
    @DisplayName("equality")
    class Compare {

        private final MonetaryAmount one = MonetaryAmount.of(1.0);
        private final MonetaryAmount oneWithTwoZeroes = MonetaryAmount.of(1.00);

        @Test
        @DisplayName("equal by amount regardless of scale")
        void equalByAmountRegardlessOfScale() {
            Assertions.assertThat(one)
                .as("monetary amount must be equal to itself")
                .isEqualTo(oneWithTwoZeroes);
        }
    }

    @Nested
    @DisplayName("arithmetics")
    class Arithmetics {

        private final MonetaryAmount two = MonetaryAmount.of(2.0);
        private final MonetaryAmount three = MonetaryAmount.of(3.0);

        @Test
        @DisplayName("add two amounts")
        void addTwoAmounts() {
            final var sum = two.add(three);

            Assertions.assertThat(sum)
                .isEqualTo(MonetaryAmount.of(5.0));
        }

        @Test
        @DisplayName("multiply amount")
        void multiply() {
            final var product = two.multiply(three.getAmount());

            Assertions.assertThat(product)
                .isEqualTo(MonetaryAmount.of(6.0));
        }
    }
}
