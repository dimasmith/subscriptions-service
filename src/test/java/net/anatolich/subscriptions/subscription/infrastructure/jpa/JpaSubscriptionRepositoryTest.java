package net.anatolich.subscriptions.subscription.infrastructure.jpa;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import java.time.Month;
import net.anatolich.subscriptions.security.domain.UserId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@DisplayName("jpa implementation of subscriptions repository")
@SpringBootTest
@DBRider
@DBUnit(caseSensitiveTableNames = true)
class JpaSubscriptionRepositoryTest {

    @Autowired
    private JpaSubscriptionRepository repository;

    @Test
    @DisplayName("all found subscriptions are active for the selected month")
    @DataSet(value = "subscriptions/findSubscriptionsByMonth-setup.yml", cleanAfter = true)
    void foundSubscriptionsAreActiveForSelectedMonths() {
        var month = Month.MAY;
        var user = UserId.of("admin");
        var subscriptions = repository.findSubscriptionsForMonth(month, user);

        Assertions.assertThat(subscriptions)
            .isNotEmpty()
            .allMatch(subscription -> subscription.activeFor(month));
    }

    @Test
    @DisplayName("all found subscriptions belongs to specified owner")
    @DataSet(value = "subscriptions/findSubscriptionsByMonth-setup.yml", cleanAfter = true)
    void foundSubscriptionsBelongsToSpecifiedOwner() {
        var month = Month.MAY;
        var owner = UserId.of("admin");
        var subscriptions = repository.findSubscriptionsForMonth(month, owner);

        Assertions.assertThat(subscriptions)
            .isNotEmpty()
            .allMatch(subscription -> subscription.belongsTo(owner));
    }
}