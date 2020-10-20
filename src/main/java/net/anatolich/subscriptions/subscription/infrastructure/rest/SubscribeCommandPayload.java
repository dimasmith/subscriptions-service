package net.anatolich.subscriptions.subscription.infrastructure.rest;

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
public class SubscribeCommandPayload {
    @Valid
    @NotNull
    private ServicePayload service;
    @Valid
    @NotNull
    private BaseSubscriptionPayload subscription;
}
