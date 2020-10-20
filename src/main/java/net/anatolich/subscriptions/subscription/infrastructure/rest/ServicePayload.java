package net.anatolich.subscriptions.subscription.infrastructure.rest;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "Service", description = "Online service you are paying for")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicePayload {
    @NotBlank(message = "please provide the service name")
    @Schema(description = "the name of the service")
    private String name;

    public static ServicePayload of(String serviceName) {
        return new ServicePayload(serviceName);
    }
}
