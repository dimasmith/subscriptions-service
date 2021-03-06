package net.anatolich.subscriptions.subscription.infrastructure.rest;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Month;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import net.anatolich.subscriptions.subscription.domain.model.Cadence;
import net.anatolich.subscriptions.subscription.domain.model.PaymentSchedule;

@Schema(name = "AnnualSubscription", description = "Service subscription with annual payments.")
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class AnnualSubscriptionPayload extends BaseSubscriptionPayload {

    @Schema(description = "the month when annual payment takes place")
    @NotNull
    private Month month;

    public AnnualSubscriptionPayload() {
        setCadence(Cadence.ANNUAL);
    }

    public static BaseSubscriptionPayload of(MoneyPayload price, Month month) {
        final var subscription = new AnnualSubscriptionPayload();
        subscription.setMonth(month);
        subscription.setPrice(price);
        return subscription;
    }

    @Override
    public PaymentSchedule schedule() {
        return PaymentSchedule.annual(month);
    }
}
