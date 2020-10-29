package net.anatolich.subscriptions.subscription.infrastructure.rest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import net.anatolich.subscriptions.subscription.application.SubscriptionFee;

@Schema(name = "SubscriptionFee", description = "A subscription details for the single service.")
@Data
@Builder
public class SubscriptionFeePayload {
    @Schema(description = "the name of the service")
    private String service;
    @Schema(description = "fee in the original currency")
    private MoneyPayload originalFee;
    @Schema(description = "fee in preferred currency")
    private MoneyPayload convertedFee;

    public static SubscriptionFeePayload from(SubscriptionFee subscriptionFee) {
        return new SubscriptionFeePayload(
                subscriptionFee.name(),
                MoneyPayload.from(subscriptionFee.originalFee()),
                MoneyPayload.from(subscriptionFee.convertedFee())
        );
    }
}
