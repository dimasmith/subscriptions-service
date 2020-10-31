package net.anatolich.subscriptions.subscription.application;

import net.anatolich.subscriptions.subscription.domain.Money;

public record SubscriptionFee(String name, Money originalFee, Money convertedFee) {

}
