package net.anatolich.subscriptions.security.domain.model;

public interface UserProvider {

    /**
     * Provides an identifier of the current user.
     */
    UserId currentUser();
}
