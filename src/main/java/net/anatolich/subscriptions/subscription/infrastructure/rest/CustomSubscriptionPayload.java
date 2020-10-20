package net.anatolich.subscriptions.subscription.infrastructure.rest;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Month;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import net.anatolich.subscriptions.subscription.domain.Cadence;
import net.anatolich.subscriptions.subscription.domain.PaymentSchedule;

@Schema(name = "CustomSubscription", description = "A subscription that happens only on certain month of the year."
    + " E.g. quarterly subscription or semi-annual subscription")
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class CustomSubscriptionPayload extends BaseSubscriptionPayload {

    @Schema(description = "names of months when you should pay for your subscriptions")
    @NotNull
    @NotEmpty
    private List<Month> months;

    public CustomSubscriptionPayload() {
        setCadence(Cadence.CUSTOM);
    }

    @Override
    public PaymentSchedule schedule() {
        return PaymentSchedule.custom(months);
    }
}
