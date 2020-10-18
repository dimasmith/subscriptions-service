package net.anatolich.subscriptions.currency.infrastructure.rest;

import static java.util.Comparator.comparing;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import net.anatolich.subscriptions.currency.application.ExchangeRatesManagementService;
import net.anatolich.subscriptions.currency.domain.ExchangeRate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@OpenAPIDefinition(
    info = @Info(title = "Exchange Rates", description = "Manage currency exchange rates.")
)
@RestController
@RequestMapping(path = "/v1/exchange-rates")
public class ExchangeRatesEndpoint {

    private final ExchangeRatesManagementService exchangeRates;

    public ExchangeRatesEndpoint(ExchangeRatesManagementService exchangeRates) {
        this.exchangeRates = exchangeRates;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateExchangeRates(@Valid @RequestBody UpdateExchangeRatesPayload payload) {
        final Collection<ExchangeRate> newRates = payload.toRates();
        exchangeRates.updateAdminExchangeRates(newRates);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ExchangeRateViewPayload> getExchangeRates() {
        return exchangeRates.seeExchangeRates().stream()
            .map(ExchangeRateViewPayload::from)
            .sorted(comparing(ExchangeRateViewPayload::getFrom).thenComparing(ExchangeRateViewPayload::getTo))
            .collect(Collectors.toList());
    }
}
