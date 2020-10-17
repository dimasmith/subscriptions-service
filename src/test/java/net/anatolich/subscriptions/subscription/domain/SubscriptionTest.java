package net.anatolich.subscriptions.subscription.domain;

import java.time.Month;
import java.util.EnumSet;
import net.anatolich.subscriptions.security.domain.UserId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("subscription")
class SubscriptionTest {

    @Test
    @DisplayName("belongs to user")
    void subscriptionBelongsToUser() {
        var subscription = Subscription
            .subscription("test", UserId.of("alex"),
                Money.of(10, "USD"), PaymentSchedule.monthly());

        Assertions.assertThat(subscription.belongsTo(UserId.of("alex")))
            .as("subscription belongs to alex")
            .isTrue();

        Assertions.assertThat(subscription.belongsTo(UserId.of("bob")))
            .as("subscription does not belong to bob")
            .isFalse();
    }

    @Test
    @DisplayName("monthly subscription is active for all month")
    void monthlySubscriptionIsActiveForAnyMonth() {
        var subscription = Subscription
            .subscription("test", UserId.of("alex"),
                Money.of(10, "USD"), PaymentSchedule.monthly());

        Assertions.assertThat(EnumSet.allOf(Month.class))
            .isNotEmpty()
            .allMatch(subscription::activeFor);
    }

    @Test
    @DisplayName("annual subscription is active only for payment month")
    void annualSubscriptionIsOnlyActiveForSelectedMonth() {
        Month paymentMonth = Month.APRIL;
        var nonPaymentMonth = EnumSet.complementOf(EnumSet.of(paymentMonth));
        var subscription = Subscription
            .subscription("test", UserId.of("alex"),
                Money.of(10, "USD"), PaymentSchedule.annual(paymentMonth));

        Assertions.assertThat(nonPaymentMonth)
            .isNotEmpty()
            .allMatch(month -> !subscription.activeFor(month));

        Assertions.assertThat(subscription.activeFor(paymentMonth))
            .isTrue();
    }

    @Test
    @DisplayName("custom subscription is active only for selected months")
    void customSubscriptionIsActiveOnlyForSelectedMonths() {
        var paymentMonths = EnumSet.of(Month.JANUARY, Month.MARCH, Month.MAY, Month.JULY, Month.SEPTEMBER, Month.NOVEMBER);
        var nonPaymentMonth = EnumSet.complementOf(paymentMonths);
        var subscription = Subscription
            .subscription("test", UserId.of("alex"),
                Money.of(10, "USD"), PaymentSchedule.custom(paymentMonths));

        Assertions.assertThat(paymentMonths)
            .as("custom subscription should be active")
            .isNotEmpty()
            .allMatch(subscription::activeFor);

        Assertions.assertThat(nonPaymentMonth)
            .as("custom subscription should not be active")
            .isNotEmpty()
            .allMatch(month -> !subscription.activeFor(month));


    }
}
