package net.anatolich.subscriptions.subscription.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Month;
import net.anatolich.subscriptions.subscription.domain.Money;
import net.anatolich.subscriptions.subscription.domain.PaymentSchedule;
import net.anatolich.subscriptions.subscription.support.SubscriptionTestConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Import(SubscriptionTestConfiguration.class)
@WithMockUser
@Transactional
class CalculateFeesTest {

    @Autowired
    private SubscriptionManagementService subscriptions;

    @Test
    void calculateTotalFeeInPreferredCurrency() {
        subscriptions.subscribe("Dropbox", Money.of(20, "UAH"), PaymentSchedule.monthly());
        subscriptions.subscribe("Spotify", Money.of(5, "USD"), PaymentSchedule.monthly());
        var monthlyFee = subscriptions.calculateMonthlyFee(Month.MAY, 2020);

        assertThat(monthlyFee.total())
            .isEqualTo(Money.of(30, "UAH"));
    }

    @Test
    void provideServiceDetailsInCalculation() {
        subscriptions.subscribe("Spotify", Money.of(10, "USD"), PaymentSchedule.monthly());
        var monthlyFee = subscriptions.calculateMonthlyFee(Month.MAY, 2020);

        assertThat(monthlyFee.subscriptions())
            .containsOnly(new SubscriptionFee("Spotify", Money.of(10, "USD"), Money.of(20, "UAH")));
    }

    @Test
    void includeAllServicesInSummary() {
        subscriptions.subscribe("Spotify", Money.of(10, "USD"), PaymentSchedule.monthly());
        subscriptions.subscribe("Dropbox", Money.of(30, "UAH"), PaymentSchedule.monthly());
        var monthlyFee = subscriptions.calculateMonthlyFee(Month.MAY, 2020);

        assertThat(monthlyFee.subscriptions())
            .containsExactlyInAnyOrder(
                new SubscriptionFee("Spotify", Money.of(10, "USD"), Money.of(20, "UAH")),
                new SubscriptionFee("Dropbox", Money.of(30, "UAH"), Money.of(30, "UAH"))
            );
    }

    @Test
    void calculateFeeOnlyForServicesHavingPaymentsThisMonth() {
        subscriptions.subscribe("Spotify", Money.of(10, "UAH"), PaymentSchedule.monthly());
        subscriptions.subscribe("Dropbox", Money.of(20, "UAH"), PaymentSchedule.monthly());
        subscriptions.subscribe("Office", Money.of(30, "UAH"), PaymentSchedule.annual(Month.APRIL));
        subscriptions.subscribe("Insurance", Money.of(40, "UAH"), PaymentSchedule.custom(Month.APRIL, Month.JUNE));

        var monthlyFee = subscriptions.calculateMonthlyFee(Month.MAY, 2020);

        assertThat(monthlyFee.total())
            .isEqualTo(Money.of(30, "UAH"));

        assertThat(monthlyFee.subscriptions()).extracting(SubscriptionFee::name)
            .containsExactlyInAnyOrder("Spotify", "Dropbox");
    }
}
