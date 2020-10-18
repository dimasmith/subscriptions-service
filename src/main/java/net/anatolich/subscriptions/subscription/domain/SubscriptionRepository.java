package net.anatolich.subscriptions.subscription.domain;

import java.time.Month;
import java.util.List;
import net.anatolich.subscriptions.security.domain.UserId;

public interface SubscriptionRepository {

    Long add(Subscription subscription);

    List<Subscription> findSubscriptionsForMonth(Month month, UserId owner);
}
