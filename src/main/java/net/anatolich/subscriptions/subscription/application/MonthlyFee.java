package net.anatolich.subscriptions.subscription.application;

import net.anatolich.subscriptions.subscription.domain.Money;

import java.util.List;

public record MonthlyFee(Money total, List<SubscriptionFee> subscriptions) {

}
