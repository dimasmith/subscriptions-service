package net.anatolich.subscriptions.security.domain;

public interface UserProvider {

    /**
     * Provides an identifier of the current user.
     */
    UserId currentUser();
}
