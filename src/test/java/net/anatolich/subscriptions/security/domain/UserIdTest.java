package net.anatolich.subscriptions.security.domain;

import net.anatolich.subscriptions.security.domain.model.UserId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("user ID")
class UserIdTest {

    @Test
    @DisplayName("username is case-insensitive")
    void usernameIsCaseInsensitive() {
        Assertions.assertThat(UserId.of("alex"))
            .as("username is case-insensitive")
            .isEqualTo(UserId.of("ALeX"));
    }

    @Test
    @DisplayName("user ids are equal for equal usernames")
    void equalityCheck() {
        Assertions.assertThat(UserId.of("alex"))
            .isEqualTo(UserId.of("alex"));

        Assertions.assertThat(UserId.of("alex"))
            .isNotEqualTo(UserId.of("alice"));
    }
}
