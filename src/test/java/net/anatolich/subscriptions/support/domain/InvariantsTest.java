package net.anatolich.subscriptions.support.domain;

import static net.anatolich.subscriptions.support.domain.Invariants.checkValue;

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
}
