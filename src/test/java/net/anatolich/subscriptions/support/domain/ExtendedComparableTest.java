package net.anatolich.subscriptions.support.domain;

import lombok.Value;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("comparable objects")
class ExtendedComparableTest {

    private final ComparableNumber minusOne = ComparableNumber.of(-1);
    private final ComparableNumber zero = ComparableNumber.of(0);
    private final ComparableNumber one = ComparableNumber.of(1);

    @Nested
    @DisplayName("predicates")
    class CheckPredicates {

        @Test
        @DisplayName("higher of equal")
        void higherOrEqualPredicate() {
            Assertions.assertThat(ExtendedComparable.Comparison.higherOrEqualThan(zero))
                .accepts(zero, one)
                .rejects(minusOne);
        }

        @Test
        @DisplayName("higher")
        void higherPredicate() {
            Assertions.assertThat(ExtendedComparable.Comparison.higherThan(zero))
                .accepts(one)
                .rejects(zero, minusOne);
        }

        @Test
        @DisplayName("equal")
        void equalPredicate() {
            Assertions.assertThat(ExtendedComparable.Comparison.equalTo(zero))
                .accepts(zero)
                .rejects(one, minusOne);
        }

        @Test
        @DisplayName("lower")
        void lowerPredicate() {
            Assertions.assertThat(ExtendedComparable.Comparison.lowerThan(zero))
                .accepts(minusOne)
                .rejects(zero, one);
        }

        @Test
        @DisplayName("lower or equal")
        void lowerOrEqualPredicate() {
            Assertions.assertThat(ExtendedComparable.Comparison.lowerOrEqualThan(zero))
                .accepts(minusOne, zero)
                .rejects(one);
        }
    }

    @Value
    private static class ComparableNumber implements ExtendedComparable<ComparableNumber> {
        int value;

        static ComparableNumber of(int value) {
            return new ComparableNumber(value);
        }

        @Override
        public int compareTo(ExtendedComparableTest.ComparableNumber other) {
            return Integer.compare(value, other.value);
        }
    }
}
