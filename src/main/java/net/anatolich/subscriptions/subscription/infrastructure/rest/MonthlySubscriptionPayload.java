package net.anatolich.subscriptions.subscription.infrastructure.rest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import net.anatolich.subscriptions.subscription.domain.Cadence;
import net.anatolich.subscriptions.subscription.domain.PaymentSchedule;

@Schema(name = "MonthlySubscription", description = "The subscription you pay each month.")
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class MonthlySubscriptionPayload extends BaseSubscriptionPayload {

    public MonthlySubscriptionPayload() {
        setCadence(Cadence.MONTHLY);
    }

    private MonthlySubscriptionPayload(MoneyPayload price) {
        this();
        setPrice(price);
    }

    public static MonthlySubscriptionPayload of(MoneyPayload price) {
        return new MonthlySubscriptionPayload(price);
    }

    @Override
    public PaymentSchedule schedule() {
        return PaymentSchedule.monthly();
    }
}
