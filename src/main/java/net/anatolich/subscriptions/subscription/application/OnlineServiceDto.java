package net.anatolich.subscriptions.subscription.application;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OnlineServiceDto {
    @NotBlank(message = "please provide the service name")
    private String name;
}
