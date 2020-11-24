package net.anatolich.subscriptions.features;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.database.rider.core.api.dataset.CompareOperation;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import java.time.Month;
import net.anatolich.subscriptions.subscription.application.SubscriptionManagementService;
import net.anatolich.subscriptions.subscription.domain.MonetaryAmount;
import net.anatolich.subscriptions.subscription.infrastructure.rest.AnnualSubscriptionPayload;
import net.anatolich.subscriptions.subscription.infrastructure.rest.MoneyPayload;
import net.anatolich.subscriptions.subscription.infrastructure.rest.MonthlySubscriptionPayload;
import net.anatolich.subscriptions.subscription.infrastructure.rest.ServicePayload;
import net.anatolich.subscriptions.subscription.infrastructure.rest.SubscribeCommandPayload;
import net.anatolich.subscriptions.support.dbrider.DatabaseRiderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

@SpringBootTest
@DatabaseRiderTest
@DisplayName("subscribe")

class SubscribeTest {

    @Autowired
    private SubscriptionManagementService subscriptions;

    @Test
    @DisplayName("create monthly subscription")
    @DataSet(value = "subscriptions/exchangeRates-setup.yml", cleanBefore = true)
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
            .isGreaterThan(MonetaryAmount.of(0));
    }

    @Test
    @DisplayName("create annual subscription")
    @DataSet(value = "subscriptions/exchangeRates-setup.yml", cleanBefore = true)
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
            .isGreaterThan(MonetaryAmount.of(0));
    }
}
