package net.anatolich.subscriptions.subscription.application;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.anatolich.subscriptions.subscription.domain.Money;
import org.hibernate.validator.constraints.Length;

@Schema(name = "Money", description = "Monetary value with amount and currency code.")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoneyDto {

    @Schema(description = "amount of money")
    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal amount;

    @Schema(description = "currency code from the ISO 4217")
    @NotNull
    @NotBlank
    @Length(min = 3, max = 3)
    private String currency;

    public static MoneyDto of(double amount, String currencyCode) {
        return new MoneyDto(BigDecimal.valueOf(amount), currencyCode);
    }

    public static MoneyDto from(Money monetaryValue) {
        return new MoneyDto(monetaryValue.getAmount(), monetaryValue.getCurrency().getCurrencyCode());
    }
}
