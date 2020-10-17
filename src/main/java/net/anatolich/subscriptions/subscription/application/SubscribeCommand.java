package net.anatolich.subscriptions.subscription.application;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "SubscribeCommand", description = "A command to subscribe to the service.")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscribeCommand {
    @Valid
    @NotNull
    private OnlineServiceDto service;
    @Valid
    @NotNull
    private SubscriptionDto subscription;
}
