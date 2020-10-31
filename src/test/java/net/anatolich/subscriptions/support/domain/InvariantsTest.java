package net.anatolich.subscriptions.support.domain;

import static net.anatolich.subscriptions.support.domain.Invariants.checkValue;

import lombok.AllArgsConstructor;
import net.anatolich.subscriptions.support.domain.Invariants.StringInvariants;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("invariants")
class InvariantsTest {

    @Test
    @DisplayName("reject value when invariant fails")
    void rejectValueWhenInvariantFails() {
        final var stringNotBlank = StringInvariants.NOT_BLANK;
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> checkValue(null, stringNotBlank, "value must not be empty"))
            .withMessageContaining("value must not be empty");
    }

    @Test
    @DisplayName("accept value when invariant holds")
    void acceptValueWhenInvariantHolds() {
        final var stringNotBlank = StringInvariants.NOT_BLANK;
        Assertions.assertThatCode(() -> checkValue("alice", stringNotBlank, "value must not be empty"))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("prohibit change of existing non-empty state")
    void prohibitChangeOfExistingState() {
        final var value = new TestValue("value");

        Assertions.assertThatExceptionOfType(IllegalStateException.class)
            .isThrownBy(() -> value.setValue("new value"))
            .withMessageContaining("value cannot be changed");
    }

    @Test
    @DisplayName("accept change of empty state")
    void acceptChangeOfEmptyState() {
        final var newValue = "new value";
        final var valueHolder = new TestValue(null);

        Assertions.assertThatCode(() -> valueHolder.setValue(newValue))
            .doesNotThrowAnyException();
    }

    @AllArgsConstructor
    private static class TestValue {

        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            Invariants.checkImmutable(this.value, "value cannot be changed");
            this.value = value;
        }
    }
}
