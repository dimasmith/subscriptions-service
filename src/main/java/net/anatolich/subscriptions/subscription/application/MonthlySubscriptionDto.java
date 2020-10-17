package net.anatolich.subscriptions.subscription.application;

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
public class MonthlySubscriptionDto extends SubscriptionDto {

    public MonthlySubscriptionDto() {
        setCadence(Cadence.MONTHLY);
    }

    private MonthlySubscriptionDto(MoneyDto price) {
        this();
        setPrice(price);
    }

    public static MonthlySubscriptionDto of(MoneyDto price) {
        return new MonthlySubscriptionDto(price);
    }

    @Override
    public PaymentSchedule schedule() {
        return PaymentSchedule.monthly();
    }
}
