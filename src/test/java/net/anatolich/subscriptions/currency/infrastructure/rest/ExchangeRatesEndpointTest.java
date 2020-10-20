package net.anatolich.subscriptions.currency.infrastructure.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Currency;
import java.util.List;
import java.util.Set;
import net.anatolich.subscriptions.currency.application.ExchangeRatesManagementService;
import net.anatolich.subscriptions.currency.domain.ExchangeRate;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@DisplayName("subscriptions endpoint")
@WebMvcTest(controllers = ExchangeRatesEndpoint.class)
class ExchangeRatesEndpointTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper json;
    @MockBean
    private ExchangeRatesManagementService exchangeRates;
    @Captor
    private ArgumentCaptor<Collection<ExchangeRate>> ratesCaptor;

    @Test
    @DisplayName("get exchange rates")
    @WithMockUser
    void getExchangeRates() throws Exception {
        when(exchangeRates.seeExchangeRates())
            .thenReturn(Set.of(
                ExchangeRate.of("UAH", "USD", 0.04),
                ExchangeRate.of("EUR", "UAH", 30.0)
            ));

        mockMvc.perform(get("/v1/exchange-rates").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.[*].from", Matchers.containsInAnyOrder("UAH", "EUR")))
            .andExpect(jsonPath("$.[*].to", Matchers.containsInAnyOrder("UAH", "USD")))
            .andExpect(jsonPath("$.[*].rate", Matchers.containsInAnyOrder(0.04, 30.0)));
    }

    @Test
    @DisplayName("update exchange rates")
    @WithMockUser
    void updateExchangeRates() throws Exception {
        UpdateExchangeRatesPayload payload = new UpdateExchangeRatesPayload();
        payload.setExchangeRates(List.of(
            new ExchangeRatePayload("UAH", "USD", 0.04),
            new ExchangeRatePayload("USD", "UAH", 25.0)
        ));

        mockMvc.perform(put("/v1/exchange-rates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json.writeValueAsBytes(payload)))
            .andExpect(status().isOk());

        verify(exchangeRates).updateAdminExchangeRates(ratesCaptor.capture());
        var exchangeRates = ratesCaptor.getValue();

        assertThat(exchangeRates)
            .anySatisfy(exchangeRate -> {
                assertThat(exchangeRate.getSourceCurrency()).isEqualTo(Currency.getInstance("UAH"));
                assertThat(exchangeRate.getTargetCurrency()).isEqualTo(Currency.getInstance("USD"));
                assertThat(exchangeRate.getRate()).isEqualByComparingTo(BigDecimal.valueOf(0.04));
            })
            .anySatisfy(exchangeRate -> {
                assertThat(exchangeRate.getSourceCurrency()).isEqualTo(Currency.getInstance("USD"));
                assertThat(exchangeRate.getTargetCurrency()).isEqualTo(Currency.getInstance("UAH"));
                assertThat(exchangeRate.getRate()).isEqualByComparingTo(BigDecimal.valueOf(25.0));
            });
    }
}
