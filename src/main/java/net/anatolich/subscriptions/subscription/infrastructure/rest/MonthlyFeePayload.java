package net.anatolich.subscriptions.subscription.infrastructure.rest;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.anatolich.subscriptions.subscription.application.MonthlyFee;

@Schema(name = "MonthlyFee", description = "Total monthly fee for the services.")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyFeePayload {

    @Schema(description = "total amount of money to be paid for services in a given month")
    private MoneyPayload total;
    @Schema(description = "services you paying for this month")
    private List<SubscriptionFeePayload> subscriptions;

    public static MonthlyFeePayload from(MonthlyFee monthlyFee) {

        return new MonthlyFeePayload(
            MoneyPayload.from(monthlyFee.total()),
            monthlyFee.subscriptions().stream().map(SubscriptionFeePayload::from).collect(Collectors.toList())
        );
    }
}
