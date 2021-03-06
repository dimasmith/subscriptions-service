package net.anatolich.subscriptions.subscription.infrastructure.rest;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.time.LocalDate;
import java.time.Month;
import javax.validation.Valid;
import net.anatolich.subscriptions.subscription.application.SubscriptionManagementService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@OpenAPIDefinition(info = @Info(description = "Manage subscriptions to services."))
@RestController
@RequestMapping(path = "/v1/subscriptions")
public class SubscriptionsEndpoint {

    private final SubscriptionManagementService subscriptions;

    public SubscriptionsEndpoint(SubscriptionManagementService subscriptions) {
        this.subscriptions = subscriptions;
    }

    @Operation(
        summary = "Subscribe to the service for a fee",
        responses = {
            @ApiResponse(responseCode = "201", description = "Subscribed")
        }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void subscribe(@Valid @RequestBody SubscribeCommandPayload subscribeCommand) {
        var serviceName = subscribeCommand.getService().getName();
        var fee = subscribeCommand.getSubscription().fee();
        var schedule = subscribeCommand.getSubscription().schedule();
        subscriptions.subscribe(serviceName, fee, schedule);
    }

    @Operation(
        summary = "Calculate the total fee for the current month.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Calculated")
        }
    )
    @GetMapping(path = "/fee")
    public MonthlyFeePayload calculateMonthlyFeeForThisMonth() {
        var today = LocalDate.now();
        final Month currentMonth = today.getMonth();
        final int currentYear = today.getYear();
        var monthlyFee = subscriptions.calculateMonthlyFee(currentMonth, currentYear);
        return MonthlyFeePayload.from(monthlyFee);
    }

    @Operation(
        summary = "Calculate the total fee for the selected month and year.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Calculated")
        }
    )
    @GetMapping(path = "/fee/{year}-{month}")
    public MonthlyFeePayload calculateMonthlyFeeForSelectedMonth(
        @PathVariable(name = "year") int year,
        @PathVariable(name = "month") Month month) {
        var monthlyFee = subscriptions.calculateMonthlyFee(month, year);
        return MonthlyFeePayload.from(monthlyFee);
    }
}
