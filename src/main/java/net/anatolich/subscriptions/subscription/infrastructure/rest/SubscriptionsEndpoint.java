package net.anatolich.subscriptions.subscription.infrastructure.rest;

import javax.validation.Valid;
import net.anatolich.subscriptions.subscription.application.SubscribeCommand;
import net.anatolich.subscriptions.subscription.application.SubscriptionManagementService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1/subscriptions")
public class SubscriptionsEndpoint {

    private final SubscriptionManagementService subscriptions;

    public SubscriptionsEndpoint(SubscriptionManagementService subscriptions) {
        this.subscriptions = subscriptions;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void subscribe(@Valid @RequestBody SubscribeCommand subscribeCommand) {
        subscriptions.subscribe(subscribeCommand);
    }
}
