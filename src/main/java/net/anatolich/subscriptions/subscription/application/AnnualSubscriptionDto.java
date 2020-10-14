package net.anatolich.subscriptions.subscription.application;

import java.time.Month;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import net.anatolich.subscriptions.subscription.domain.Cadence;
import net.anatolich.subscriptions.subscription.domain.PaymentSchedule;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class AnnualSubscriptionDto extends SubscriptionDto {
    @NotNull
    private Month month;

    public AnnualSubscriptionDto() {
        setCadence(Cadence.ANNUAL);
    }

    public static SubscriptionDto of(MoneyDto price, Month month) {
        final var subscription = new AnnualSubscriptionDto();
        subscription.setMonth(month);
        subscription.setPrice(price);
        return subscription;
    }

    @Override
    public PaymentSchedule schedule() {
        return PaymentSchedule.annual(month);
    }
}
