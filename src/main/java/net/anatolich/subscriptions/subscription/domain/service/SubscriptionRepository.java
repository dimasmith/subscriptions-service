package net.anatolich.subscriptions.subscription.domain.service;

import java.time.Month;
import java.util.List;
import net.anatolich.subscriptions.security.domain.model.UserId;
import net.anatolich.subscriptions.subscription.domain.model.Subscription;

public interface SubscriptionRepository {

    Long add(Subscription subscription);

    List<Subscription> findSubscriptionsForMonth(Month month, UserId owner);
}
