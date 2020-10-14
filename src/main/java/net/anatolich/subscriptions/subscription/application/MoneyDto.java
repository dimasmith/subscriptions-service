package net.anatolich.subscriptions.subscription.application;

import java.math.BigDecimal;
import java.util.Currency;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoneyDto {
    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal amount;
    @NotNull
    private Currency currency;

    public static MoneyDto of(double amount, String currencyCode) {
        return new MoneyDto(BigDecimal.valueOf(amount), Currency.getInstance(currencyCode));
    }
}
