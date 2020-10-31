package net.anatolich.subscriptions.subscription.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.time.Month;
import java.util.Currency;
import java.util.EnumSet;
import net.anatolich.subscriptions.security.domain.UserId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("subscription")
class SubscriptionTest {

    @Test
    @DisplayName("belongs to user")
    void subscriptionBelongsToUser() {
        var subscription = Subscription
            .subscription("test", UserId.of("alex"),
                Money.of(10, "USD"), PaymentSchedule.monthly());

        assertThat(subscription.belongsTo(UserId.of("alex")))
            .as("subscription belongs to alex")
            .isTrue();

        assertThat(subscription.belongsTo(UserId.of("bob")))
            .as("subscription does not belong to bob")
            .isFalse();
    }

    @Test
    @DisplayName("monthly subscription is active for all month")
    void monthlySubscriptionIsActiveForAnyMonth() {
        var subscription = Subscription
            .subscription("test", UserId.of("alex"),
                Money.of(10, "USD"), PaymentSchedule.monthly());

        assertThat(EnumSet.allOf(Month.class))
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

        assertThat(nonPaymentMonth)
            .isNotEmpty()
            .allMatch(month -> !subscription.activeFor(month));

        assertThat(subscription.activeFor(paymentMonth))
            .isTrue();
    }

    @Test
    @DisplayName("custom subscription is active only for selected months")
    void customSubscriptionIsActiveOnlyForSelectedMonths() {
        var paymentMonths = EnumSet
            .of(Month.JANUARY, Month.MARCH, Month.MAY, Month.JULY, Month.SEPTEMBER, Month.NOVEMBER);
        var nonPaymentMonth = EnumSet.complementOf(paymentMonths);
        var subscription = Subscription
            .subscription("test", UserId.of("alex"),
                Money.of(10, "USD"), PaymentSchedule.custom(paymentMonths));

        assertThat(paymentMonths)
            .as("custom subscription should be active")
            .isNotEmpty()
            .allMatch(subscription::activeFor);

        assertThat(nonPaymentMonth)
            .as("custom subscription should not be active")
            .isNotEmpty()
            .allMatch(month -> !subscription.activeFor(month));
    }

    @Nested
    @DisplayName("invariants")
    class InvariantChecks {

        private final String name = "Service";
        private final UserId owner = UserId.of("alice");
        private final Money fee = Money.of(45, "UAH");
        private final PaymentSchedule schedule = PaymentSchedule.monthly();

        @Test
        @DisplayName("require non-empty service name")
        void requireServiceName() {
            assertThatExceptionOfType(IllegalArgumentException.class)
                .as("null service name is not allowed")
                .isThrownBy(() -> Subscription.subscription(null, owner, fee, schedule));

            assertThatExceptionOfType(IllegalArgumentException.class)
                .as("empty service name is not allowed")
                .isThrownBy(() -> Subscription.subscription("", owner, fee, schedule));
        }

        @Test
        @DisplayName("require service fee")
        void requireFee() {
            assertThatExceptionOfType(IllegalArgumentException.class)
                .as("fee must be specified")
                .isThrownBy(() -> Subscription.subscription(name, owner, null, schedule))
                .withMessageContaining("fee must be set");
        }

        @Test
        @DisplayName("fee amount must be at least 0.01")
        void requireMinimalFeeAmount() {
            final Money zeroFee = Money.zero(Currency.getInstance("USD"));
            assertThatExceptionOfType(IllegalArgumentException.class)
                .as("fee must be at least 0.01")
                .isThrownBy(() -> Subscription.subscription(name, owner, zeroFee, schedule))
                .withMessageContaining("fee amount is too small");

            final Money negativeFee = Money.of(-0.01, "USD");
            assertThatExceptionOfType(IllegalArgumentException.class)
                .as("fee must be positive")
                .isThrownBy(() -> Subscription.subscription(name, owner, negativeFee, schedule))
                .withMessageContaining("fee amount is too small");
        }

        @Test
        @DisplayName("require schedule")
        void requireSchedule() {
            assertThatExceptionOfType(IllegalArgumentException.class)
                .as("schedule must be set")
                .isThrownBy(() -> Subscription.subscription(name, owner, fee, null))
                .withMessageContaining("schedule must be set");
        }

        @Test
        @DisplayName("require owner")
        void requireOwner() {
            assertThatExceptionOfType(IllegalArgumentException.class)
                .as("owner must be set")
                .isThrownBy(() -> Subscription.subscription(name, null, fee, schedule))
                .withMessageContaining("owner must be set");
        }
    }
}
