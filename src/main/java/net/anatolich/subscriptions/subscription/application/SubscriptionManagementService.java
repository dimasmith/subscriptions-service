package net.anatolich.subscriptions.subscription.application;

import lombok.extern.slf4j.Slf4j;
import net.anatolich.subscriptions.subscription.domain.Subscription;
import net.anatolich.subscriptions.subscription.domain.SubscriptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class SubscriptionManagementService {

    private final SubscriptionRepository subscriptions;

    public SubscriptionManagementService(SubscriptionRepository subscriptions) {
        this.subscriptions = subscriptions;
    }

    @Transactional
    public void subscribe(SubscribeCommand subscribeCommand) {
        log.info("subscribing to {}", subscribeCommand);
        final var subscription = Subscription.subscription(
            subscribeCommand.getService().getName(),
            subscribeCommand.getSubscription().fee(),
            subscribeCommand.getSubscription().schedule()
        );
        final var subscriptionId = subscriptions.add(subscription);
        log.info("subscribed to {} with id {}", subscribeCommand, subscriptionId);
    }
}
