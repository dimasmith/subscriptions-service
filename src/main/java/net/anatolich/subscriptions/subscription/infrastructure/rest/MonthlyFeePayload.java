package net.anatolich.subscriptions.subscription.infrastructure.rest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.anatolich.subscriptions.subscription.application.MoneyDto;
import net.anatolich.subscriptions.subscription.application.MonthlyFee;

@Schema(name = "MonthlyFee", description = "Total monthly fee for the services.")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyFeePayload {

    @Schema(description = "total amount of money to be paid for services in a given month")
    private MoneyDto total;

    public static MonthlyFeePayload from(MonthlyFee monthlyFee) {
        return new MonthlyFeePayload(
            MoneyDto.from(monthlyFee.total())
        );
    }
}
