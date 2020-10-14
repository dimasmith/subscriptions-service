package net.anatolich.subscriptions.subscription.infrastructure.jpa;

import net.anatolich.subscriptions.subscription.domain.Subscription;
import net.anatolich.subscriptions.subscription.domain.SubscriptionRepository;
import org.springframework.stereotype.Component;

@Component
public class JpaSubscriptionRepository implements SubscriptionRepository {

    private final SpringDataSubscriptionRepository repository;

    public JpaSubscriptionRepository(SpringDataSubscriptionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Long add(Subscription subscription) {
        return repository.save(subscription).id();
    }
}
