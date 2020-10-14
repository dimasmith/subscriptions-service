package net.anatolich.subscriptions.subscription.application;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Data;
import net.anatolich.subscriptions.subscription.domain.Cadence;
import net.anatolich.subscriptions.subscription.domain.Money;
import net.anatolich.subscriptions.subscription.domain.PaymentSchedule;

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
    @NotNull
    private Cadence cadence;
    @Valid
    @NotNull
    private MoneyDto price;

    public Money fee() {
        return Money.fee(price.getAmount(), price.getCurrency());
    }

    public abstract PaymentSchedule schedule();
}
