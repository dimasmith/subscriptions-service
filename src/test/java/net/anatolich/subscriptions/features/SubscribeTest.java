package net.anatolich.subscriptions.features;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.dataset.CompareOperation;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import java.time.Month;
import net.anatolich.subscriptions.subscription.application.AnnualSubscriptionDto;
import net.anatolich.subscriptions.subscription.application.MoneyDto;
import net.anatolich.subscriptions.subscription.application.MonthlySubscriptionDto;
import net.anatolich.subscriptions.subscription.application.OnlineServiceDto;
import net.anatolich.subscriptions.subscription.application.SubscribeCommand;
import net.anatolich.subscriptions.subscription.application.SubscriptionManagementService;
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
        final var subscribeCommand = new SubscribeCommand(
            new OnlineServiceDto("Monthly Paid Service"),
            MonthlySubscriptionDto.of(MoneyDto.of(19.99, "USD")));

        subscriptions.subscribe(subscribeCommand);
    }

    @Test
    @DisplayName("create annual subscription")
    @ExpectedDataSet(value = "subscriptions/createAnnualSubscription-expected.yml",
        compareOperation = CompareOperation.CONTAINS)
    @WithMockUser("admin")
    void createAnnualSubscription() {
        final var subscribeCommand = new SubscribeCommand(
            new OnlineServiceDto("Annually Paid Service"),
            AnnualSubscriptionDto.of(MoneyDto.of(2499.00, "UAH"), Month.AUGUST));

        subscriptions.subscribe(subscribeCommand);
    }
}
