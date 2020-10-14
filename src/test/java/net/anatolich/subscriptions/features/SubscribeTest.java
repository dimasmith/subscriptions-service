package net.anatolich.subscriptions.features;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Month;
import net.anatolich.subscriptions.subscription.application.AnnualSubscriptionDto;
import net.anatolich.subscriptions.subscription.application.MoneyDto;
import net.anatolich.subscriptions.subscription.application.MonthlySubscriptionDto;
import net.anatolich.subscriptions.subscription.application.OnlineServiceDto;
import net.anatolich.subscriptions.subscription.application.SubscribeCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJson;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJson
@DisplayName("subscribe")
class SubscribeTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper json;

    @Test
    @DisplayName("create monthly subscription")
    void createMonthlySubscription() throws Exception {
        final var subscribeCommand = new SubscribeCommand(
            new OnlineServiceDto("Monthly Paid Service"),
            MonthlySubscriptionDto.of(MoneyDto.of(19.99, "USD")));

        mockMvc.perform(post("/v1/subscriptions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json.writeValueAsBytes(subscribeCommand)))
            .andExpect(status().isCreated());

    }

    @Test
    @DisplayName("create annual subscription")
    void createAnnualSubscription() throws Exception {
        final var subscribeCommand = new SubscribeCommand(
            new OnlineServiceDto("Annually Paid Service"),
            AnnualSubscriptionDto.of(MoneyDto.of(2499.00, "UAH"), Month.AUGUST));

        mockMvc.perform(post("/v1/subscriptions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json.writeValueAsBytes(subscribeCommand)))
            .andExpect(status().isCreated());

    }
}
