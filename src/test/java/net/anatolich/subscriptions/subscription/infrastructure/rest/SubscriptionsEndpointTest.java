package net.anatolich.subscriptions.subscription.infrastructure.rest;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Month;
import java.util.List;
import net.anatolich.subscriptions.subscription.application.MonthlyFee;
import net.anatolich.subscriptions.subscription.application.SubscriptionFee;
import net.anatolich.subscriptions.subscription.application.SubscriptionManagementService;
import net.anatolich.subscriptions.subscription.domain.Money;
import net.anatolich.subscriptions.subscription.domain.PaymentSchedule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@DisplayName("subscriptions endpoint")
@WebMvcTest(controllers = SubscriptionsEndpoint.class)
class SubscriptionsEndpointTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper json;
    @MockBean
    private SubscriptionManagementService subscriptions;

    @Test
    @DisplayName("create subscription")
    @WithMockUser
    void createSubscription() throws Exception {
        SubscribeCommandPayload payload = new SubscribeCommandPayload();
        payload.setService(new ServicePayload("Service"));
        payload.setSubscription(MonthlySubscriptionPayload.of(MoneyPayload.of(100.0, "USD")));

        mockMvc.perform(
            post("/v1/subscriptions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.writeValueAsBytes(payload)))
            .andExpect(status().isCreated());

        verify(subscriptions).subscribe(
            "Service",
            Money.of(100.0, "USD"),
            PaymentSchedule.monthly()
        );
    }

    @Test
    @DisplayName("send invalid creation payload")
    @WithMockUser
    void sendInvalidCreationPayload() throws Exception {
        SubscribeCommandPayload payload = new SubscribeCommandPayload();
        payload.setService(null); // missing service
        payload.setSubscription(MonthlySubscriptionPayload.of(MoneyPayload.of(100, "USD")));

        mockMvc.perform(
            post("/v1/subscriptions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.writeValueAsBytes(payload)))
            .andExpect(status().isBadRequest());

        verify(subscriptions, never()).subscribe(anyString(), any(Money.class), any(PaymentSchedule.class));
    }

    @Test
    @DisplayName("calculate fee for the current month")
    @WithMockUser
    void calculateFeeForTheCurrentMonth() throws Exception {
        when(subscriptions.calculateMonthlyFee(any(Month.class), anyInt()))
            .thenReturn(new MonthlyFee(Money.of(100, "USD"),
                List.of(new SubscriptionFee(
                    "Dropbox",
                    Money.of(200, "UAH"),
                    Money.of(100, "USD")))));

        mockMvc.perform(
            get("/v1/subscriptions/fee").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("total.amount", equalTo(100.0)))
            .andExpect(jsonPath("total.currency", equalTo("USD")))
            .andExpect(jsonPath("subscriptions[0].service", equalTo("Dropbox")))
            .andExpect(jsonPath("subscriptions[0].originalFee.amount", equalTo(200.0)));
    }

    @Test
    @DisplayName("calculate fee for the particular month")
    @WithMockUser
    void calculateFeeForTheParticularMonth() throws Exception {
        when(subscriptions.calculateMonthlyFee(Month.MAY, 2020))
            .thenReturn(new MonthlyFee(Money.of(100, "USD"), List.of()));

        mockMvc.perform(
            get("/v1/subscriptions/fee/2020-MAY").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("total.amount", equalTo(100.0)))
            .andExpect(jsonPath("total.currency", equalTo("USD")));
    }
}
