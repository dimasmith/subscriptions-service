package net.anatolich.subscriptions.subscription.domain;

import java.util.Currency;

/**
 * Convert monetary amounts of one currency into amounts of another currency.
 */
public interface CurrencyConverter {

    /**
     * Convert initial monetary value to the target currency.
     */
    Money convert(Money monetaryValue, Currency toCurrency);
}
