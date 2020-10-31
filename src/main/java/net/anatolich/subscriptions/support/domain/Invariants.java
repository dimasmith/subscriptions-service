package net.anatolich.subscriptions.support.domain;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Predicate;

public class Invariants {

    private Invariants() {
    }

    public static <T> void checkValue(T value, Predicate<T> predicate, String message) {
        if (!predicate.test(value)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static class StringInvariants {

        private StringInvariants() {
        }

        public static final Predicate<String> NOT_NULL = Objects::nonNull;
        public static final Predicate<String> NOT_BLANK = NOT_NULL.and(Predicate.not(String::isBlank));
    }

    public static class CollectionsInvariants {

        private CollectionsInvariants() {
        }

        public static final Predicate<Collection<?>> NOT_NULL = Objects::nonNull;
        public static final Predicate<Collection<?>> NOT_EMPTY = NOT_NULL.and(Predicate.not(Collection::isEmpty));
    }
}
