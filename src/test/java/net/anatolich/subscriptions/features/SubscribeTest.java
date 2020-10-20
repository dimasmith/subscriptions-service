package net.anatolich.subscriptions.features;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.dataset.CompareOperation;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import java.math.BigDecimal;
import java.time.Month;
import net.anatolich.subscriptions.subscription.application.SubscriptionManagementService;
import net.anatolich.subscriptions.subscription.infrastructure.rest.AnnualSubscriptionPayload;
import net.anatolich.subscriptions.subscription.infrastructure.rest.MoneyPayload;
import net.anatolich.subscriptions.subscription.infrastructure.rest.MonthlySubscriptionPayload;
import net.anatolich.subscriptions.subscription.infrastructure.rest.ServicePayload;
import net.anatolich.subscriptions.subscription.infrastructure.rest.SubscribeCommandPayload;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

@SpringBootTest
@DBRider
@DBUnit(columnSensing = true, caseSensitiveTableNames = true)
@DisplayName("subscribe")
class SubscribeTest {
    @Autowired
    private SubscriptionManagementService subscriptions;

    @Test
    @DisplayName("create monthly subscription")
    @ExpectedDataSet(value = "subscriptions/createMonthlySubscription-expected.yml",
        compareOperation = CompareOperation.CONTAINS)
    @WithMockUser("admin")
    void createMonthlySubscription() {
        final var subscribeCommand = new SubscribeCommandPayload(
            new ServicePayload("Monthly Paid Service"),
            MonthlySubscriptionPayload.of(MoneyPayload.of(19.99, "USD")));

        subscriptions.subscribe(subscribeCommand.getService().getName(),
            subscribeCommand.getSubscription().fee(), subscribeCommand.getSubscription().schedule());

        assertThat(subscriptions.calculateMonthlyFee(Month.MAY, 2020).total().getAmount())
            .isGreaterThan(BigDecimal.ZERO);
    }

    @Test
    @DisplayName("create annual subscription")
    @ExpectedDataSet(value = "subscriptions/createAnnualSubscription-expected.yml",
        compareOperation = CompareOperation.CONTAINS)
    @WithMockUser("admin")
    void createAnnualSubscription() {
        final var subscribeCommand = new SubscribeCommandPayload(
            new ServicePayload("Annually Paid Service"),
            AnnualSubscriptionPayload.of(MoneyPayload.of(2499.00, "UAH"), Month.AUGUST));

        subscriptions.subscribe(subscribeCommand.getService().getName(),
            subscribeCommand.getSubscription().fee(), subscribeCommand.getSubscription().schedule());

        assertThat(subscriptions.calculateMonthlyFee(Month.AUGUST, 2020).total().getAmount())
            .isGreaterThan(BigDecimal.ZERO);
    }
}
