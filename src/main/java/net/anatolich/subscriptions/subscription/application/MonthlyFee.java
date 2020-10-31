package net.anatolich.subscriptions.subscription.application;

import java.util.List;
import net.anatolich.subscriptions.subscription.domain.Money;

public record MonthlyFee(Money total, List<SubscriptionFee> subscriptions) {

}
