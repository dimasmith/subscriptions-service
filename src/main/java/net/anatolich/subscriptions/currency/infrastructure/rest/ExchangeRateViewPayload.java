package net.anatolich.subscriptions.currency.infrastructure.rest;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.anatolich.subscriptions.currency.domain.ExchangeRate;

@Schema(name = "ExchangeRateView", description = "exchange rate of two currencies")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRateViewPayload {
    @Schema(description = "source currency of exchange rate")
    private String from;
    @Schema(description = "target currency of exchange rate")
    private String to;
    @Schema(description = "conversion rate between source and target currency")
    private double rate;
    private LocalDateTime updatedOn;

    public static ExchangeRateViewPayload from(ExchangeRate exchangeRate) {
        return new ExchangeRateViewPayload(
            exchangeRate.getSourceCurrency().getCurrencyCode(),
            exchangeRate.getTargetCurrency().getCurrencyCode(),
            exchangeRate.getRate().doubleValue(),
            exchangeRate.getUpdatedOn()
        );
    }
}
