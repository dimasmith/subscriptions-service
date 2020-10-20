package net.anatolich.subscriptions.features;

import java.time.Month;
import net.anatolich.subscriptions.subscription.application.MonthlyFee;
import net.anatolich.subscriptions.subscription.application.SubscriptionManagementService;
import net.anatolich.subscriptions.subscription.domain.Money;
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
@DisplayName("calculate monthly fee")
class CalculateMonthlyFeeTest {

    @Autowired
    private SubscriptionManagementService service;

    @Test
    @DisplayName("fee for multiple services")
    @WithMockUser("admin")
    void calculateFeeForAFewServices() {
        var dropboxSubscription = monthly("Dropbox", 19.99);
        var spotifySubscription = monthly("Spotify", 4.99);
        service.subscribe(
            dropboxSubscription.getService().getName(),
            dropboxSubscription.getSubscription().fee(),
            dropboxSubscription.getSubscription().schedule());
        service.subscribe(
            spotifySubscription.getService().getName(),
            spotifySubscription.getSubscription().fee(),
            spotifySubscription.getSubscription().schedule());

        MonthlyFee monthlyFee = service.calculateMonthlyFee(Month.MAY, 2021);

        Assertions.assertThat(monthlyFee.total())
            .isEqualTo(Money.of(24.98, "UAH"));
    }

    private SubscribeCommandPayload monthly(String serviceName, double price) {
        return SubscribeCommandPayload.builder()
            .service(ServicePayload.of(serviceName))
            .subscription(MonthlySubscriptionPayload.of(MoneyPayload.of(price, "UAH")))
            .build();
    }
}
