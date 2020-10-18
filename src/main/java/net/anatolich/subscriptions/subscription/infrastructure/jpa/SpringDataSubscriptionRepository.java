package net.anatolich.subscriptions.subscription.infrastructure.jpa;

import java.time.Month;
import java.util.List;
import net.anatolich.subscriptions.security.domain.UserId;
import net.anatolich.subscriptions.subscription.domain.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataSubscriptionRepository extends JpaRepository<Subscription, Long> {

    @Query("select s from Subscription s join s.schedule.periods p where s.owner = :owner and p = :month")
    List<Subscription> findSubscriptionsByOwnerAndMonth(Month month, UserId owner);
}
