package net.anatolich.subscriptions.currency.infrastructure.rest;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import net.anatolich.subscriptions.currency.domain.ExchangeRate;

@Schema(name = "UpdateExchangeRates", description = "command to update exchange rates")
@Data
public class UpdateExchangeRatesPayload {

    @Valid
    @NotNull
    @NotEmpty
    private List<ExchangeRatePayload> exchangeRates;

    public Collection<ExchangeRate> toRates() {
        return exchangeRates.stream()
            .map(ExchangeRatePayload::toExchangeRate)
            .collect(Collectors.toSet());
    }
}
