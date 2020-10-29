package net.anatolich.subscriptions.features;

import com.github.database.rider.core.api.dataset.CompareOperation;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import net.anatolich.subscriptions.subscription.application.SubscriptionManagementService;
import net.anatolich.subscriptions.subscription.infrastructure.rest.*;
import net.anatolich.subscriptions.support.dbrider.DatabaseRiderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import java.math.BigDecimal;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DatabaseRiderTest
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
