package net.anatolich.subscriptions.subscription.application;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
