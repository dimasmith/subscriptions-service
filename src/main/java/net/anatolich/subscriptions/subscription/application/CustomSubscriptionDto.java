package net.anatolich.subscriptions.subscription.application;

import java.time.Month;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import net.anatolich.subscriptions.subscription.domain.Cadence;
import net.anatolich.subscriptions.subscription.domain.PaymentSchedule;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class CustomSubscriptionDto extends SubscriptionDto {
    @NotNull
    @NotEmpty
    private List<Month> months;

    public CustomSubscriptionDto() {
        setCadence(Cadence.CUSTOM);
    }

    @Override
    public PaymentSchedule schedule() {
        return PaymentSchedule.custom(months);
    }
}
