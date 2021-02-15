package net.anatolich.subscriptions.subscription.domain.service;

import java.util.Currency;
import net.anatolich.subscriptions.subscription.domain.model.Money;

/**
 * Convert monetary amounts of one currency into amounts of another currency.
 */
public interface CurrencyConverter {

    /**
     * Convert initial monetary value to the target currency.
     */
    Money convert(Money monetaryValue, Currency toCurrency);
}
