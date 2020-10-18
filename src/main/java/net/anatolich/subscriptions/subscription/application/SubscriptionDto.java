package net.anatolich.subscriptions.subscription.application;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import io.swagger.v3.oas.annotations.media.DiscriminatorMapping;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Currency;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Data;
import net.anatolich.subscriptions.subscription.domain.Cadence;
import net.anatolich.subscriptions.subscription.domain.Money;
import net.anatolich.subscriptions.subscription.domain.PaymentSchedule;

@Schema(
    name = "Subscription",
    description = "Subscription with recurring payments",
    discriminatorProperty = "cadence",
    discriminatorMapping = {
        @DiscriminatorMapping(value = "MONTHLY", schema = MonthlySubscriptionDto.class),
        @DiscriminatorMapping(value = "ANNUAL", schema = AnnualSubscriptionDto.class),
        @DiscriminatorMapping(value = "CUSTOM", schema = CustomSubscriptionDto.class)
    },
    anyOf = {MonthlySubscriptionDto.class, AnnualSubscriptionDto.class, CustomSubscriptionDto.class}
)
@JsonTypeInfo(
    use = Id.NAME,
    include = As.EXISTING_PROPERTY,
    property = "cadence"
)
@JsonSubTypes({
    @Type(value = MonthlySubscriptionDto.class, name = "MONTHLY"),
    @Type(value = AnnualSubscriptionDto.class, name = "ANNUAL"),
    @Type(value = CustomSubscriptionDto.class, name = "CUSTOM")
})
@Data
public abstract class SubscriptionDto {

    @Schema(description = "Type of the subscription.")
    @NotNull
    private Cadence cadence;

    @Schema(description = "Price you pay for the subscription.")
    @Valid
    @NotNull
    private MoneyDto price;

    public Money fee() {
        return Money.of(price.getAmount(), Currency.getInstance(price.getCurrency()));
    }

    public abstract PaymentSchedule schedule();
}
