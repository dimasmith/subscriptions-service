package net.anatolich.subscriptions.subscription.infrastructure.jpa;

import net.anatolich.subscriptions.subscription.domain.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataSubscriptionRepository extends JpaRepository<Subscription, Long> {

}
