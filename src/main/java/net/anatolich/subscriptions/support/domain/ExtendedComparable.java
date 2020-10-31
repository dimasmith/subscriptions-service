package net.anatolich.subscriptions.support.domain;

import java.util.function.Predicate;

public interface ExtendedComparable<T> extends Comparable<T> {

    default Comparison compare(T other) {
        return Comparison.of(compareTo(other));
    }

    enum Comparison {
        LOWER, EQUAL, HIGHER;

        public boolean isLower() {
            return is(LOWER);
        }

        public static <T extends ExtendedComparable<T>> Predicate<T> lowerThan(T other) {
            return t -> t.compare(other).isLower();
        }

        public boolean isLowerOrEqual() {
            return isLower() || isEqual();
        }

        public static <T extends ExtendedComparable<T>> Predicate<T> lowerOrEqualThan(T other) {
            return t -> t.compare(other).isLowerOrEqual();
        }

        public boolean isEqual() {
            return is(EQUAL);
        }

        public static <T extends ExtendedComparable<T>> Predicate<T> equalTo(T other) {
            return t -> t.compare(other).isEqual();
        }

        public boolean isHigher() {
            return is(HIGHER);
        }

        public static <T extends ExtendedComparable<T>> Predicate<T> higherThan(T other) {
            return t -> t.compare(other).isHigher();
        }

        public boolean isHigherOrEqual() {
            return isHigher() || isEqual();
        }

        public static <T extends ExtendedComparable<T>> Predicate<T> higherOrEqualThan(T other) {
            return t -> t.compare(other).isHigherOrEqual();
        }

        public boolean is(Comparison comparison) {
            return this == comparison;
        }

        private static Comparison of(int compareTo) {
            if (compareTo < 0) {
                return LOWER;
            }
            if (compareTo > 0) {
                return HIGHER;
            }
            return EQUAL;
        }
    }
}
