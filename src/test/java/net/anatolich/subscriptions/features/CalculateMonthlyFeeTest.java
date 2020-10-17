package net.anatolich.subscriptions.features;

import java.time.Month;
import net.anatolich.subscriptions.subscription.application.MoneyDto;
import net.anatolich.subscriptions.subscription.application.MonthlyFee;
import net.anatolich.subscriptions.subscription.application.MonthlySubscriptionDto;
import net.anatolich.subscriptions.subscription.application.OnlineServiceDto;
import net.anatolich.subscriptions.subscription.application.SubscribeCommand;
import net.anatolich.subscriptions.subscription.application.SubscriptionManagementService;
import net.anatolich.subscriptions.subscription.domain.Money;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

@SpringBootTest
@DisplayName("calculate monthly fee")
class CalculateMonthlyFeeTest {

    @Autowired
    private SubscriptionManagementService service;

    @Test
    @DisplayName("fee for multiple services")
    @WithMockUser("admin")
    void calculateFeeForAFewServices() {
        service.subscribe(monthly("Dropbox", 19.99));
        service.subscribe(monthly("Spotify", 4.99));

        MonthlyFee monthlyFee = service.calculateMonthlyFee(Month.MAY, 2021);

        Assertions.assertThat(monthlyFee.total())
            .isEqualTo(Money.of(24.98, "USD"));
    }

    private SubscribeCommand monthly(String serviceName, double price) {
        return SubscribeCommand.builder()
            .service(OnlineServiceDto.of(serviceName))
            .subscription(MonthlySubscriptionDto.of(MoneyDto.of(price, "USD")))
            .build();
    }
}
