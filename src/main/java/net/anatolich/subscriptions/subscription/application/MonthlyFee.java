package net.anatolich.subscriptions.subscription.application;

import java.util.List;
import net.anatolich.subscriptions.subscription.domain.model.Money;

public record MonthlyFee(Money total, List<SubscriptionFee> subscriptions) {

}
