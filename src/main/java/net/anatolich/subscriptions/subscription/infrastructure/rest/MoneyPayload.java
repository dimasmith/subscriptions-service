package net.anatolich.subscriptions.subscription.infrastructure.rest;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.anatolich.subscriptions.subscription.domain.model.Money;
import org.hibernate.validator.constraints.Length;

@Schema(name = "Money", description = "Monetary value with amount and currency code.")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoneyPayload {

    @Schema(description = "amount of money")
    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal amount;

    @Schema(description = "currency code from the ISO 4217")
    @NotNull
    @NotBlank
    @Length(min = 3, max = 3)
    private String currency;

    public static MoneyPayload of(double amount, String currencyCode) {
        return new MoneyPayload(BigDecimal.valueOf(amount), currencyCode);
    }

    public static MoneyPayload from(Money monetaryValue) {
        return new MoneyPayload(monetaryValue.getAmount().getAmount(), monetaryValue.getCurrency().getCurrencyCode());
    }
}
