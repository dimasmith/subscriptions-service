package net.anatolich.subscriptions.security.domain;

import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Value class to hold an identifier of the current user.
 */
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
@ToString
public class UserId {
    private String username;

    private UserId(String username) {
        setUsername(username);
    }

    public static UserId of(String username) {
        return new UserId(username);
    }

    private void setUsername(String username) {
        if (username == null) {
            throw new IllegalArgumentException("username must not be null");
        }
        if (username.isBlank()) {
            throw new IllegalArgumentException("username must not be empty");
        }
        if (this.username != null) {
            throw new IllegalStateException("username must not be changed");
        }
        this.username = username.toLowerCase();
    }
}
