package net.anatolich.subscriptions.subscription.application;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import net.anatolich.subscriptions.subscription.domain.Cadence;
import net.anatolich.subscriptions.subscription.domain.PaymentSchedule;

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
