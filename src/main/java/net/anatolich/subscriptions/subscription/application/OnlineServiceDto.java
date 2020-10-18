package net.anatolich.subscriptions.subscription.application;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "Service", description = "Online service you are paying for")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OnlineServiceDto {
    @NotBlank(message = "please provide the service name")
    @Schema(description = "the name of the service")
    private String name;

    public static OnlineServiceDto of(String serviceName) {
        return new OnlineServiceDto(serviceName);
    }
}
