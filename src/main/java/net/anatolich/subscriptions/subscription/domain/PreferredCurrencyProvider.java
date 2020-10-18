package net.anatolich.subscriptions.subscription.domain;

import java.util.Currency;
import net.anatolich.subscriptions.security.domain.UserId;

/**
 * Provides a base currency for the user.
 */
public interface PreferredCurrencyProvider {

    /**
     * Provides a base currency in which user wants to receive totals.
     */
    Currency preferredCurrencyOf(UserId user);
}
