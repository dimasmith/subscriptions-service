package net.anatolich.subscriptions.currency.infrastructure.rest;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.anatolich.subscriptions.currency.domain.model.ExchangeRate;
import org.hibernate.validator.constraints.Length;

@Schema(name = "ExchangeRate", description = "exchange rate of two currencies")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRatePayload {

    @Schema(description = "source currency of exchange rate")
    @NotNull
    @NotEmpty
    @Length(min = 3, max = 3)
    private String from;
    @Schema(description = "target currency of exchange rate")
    @NotNull
    @NotEmpty
    @Length(min = 3, max = 3)
    private String to;
    @Schema(description = "conversion rate between source and target currency")
    @NotNull
    private double rate;

    public ExchangeRate toExchangeRate() {
        return ExchangeRate.of(from, to, rate);
    }
}
