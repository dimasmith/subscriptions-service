package net.anatolich.subscriptions.features;

import com.github.database.rider.core.api.dataset.DataSet;
import java.time.Month;
import net.anatolich.subscriptions.subscription.application.MonthlyFee;
import net.anatolich.subscriptions.subscription.application.SubscriptionManagementService;
import net.anatolich.subscriptions.subscription.domain.model.Money;
import net.anatolich.subscriptions.subscription.infrastructure.rest.MoneyPayload;
import net.anatolich.subscriptions.subscription.infrastructure.rest.MonthlySubscriptionPayload;
import net.anatolich.subscriptions.subscription.infrastructure.rest.ServicePayload;
import net.anatolich.subscriptions.subscription.infrastructure.rest.SubscribeCommandPayload;
import net.anatolich.subscriptions.support.dbrider.DatabaseRiderTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

@SpringBootTest
@DisplayName("calculate monthly fee")
@DatabaseRiderTest
class CalculateMonthlyFeeTest {

    @Autowired
    private SubscriptionManagementService service;

    @Test
    @DisplayName("fee for multiple services")
    @DataSet(value = "subscriptions/exchangeRates-setup.yml", cleanBefore = true)
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
            .as(monthlyFee.subscriptions().toString())
            .isEqualTo(Money.of(24.98, "UAH"));
    }

    private SubscribeCommandPayload monthly(String serviceName, double price) {
        return SubscribeCommandPayload.builder()
            .service(ServicePayload.of(serviceName))
            .subscription(MonthlySubscriptionPayload.of(MoneyPayload.of(price, "UAH")))
            .build();
    }
}
