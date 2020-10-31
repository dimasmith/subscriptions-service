package net.anatolich.subscriptions.subscription.domain;

import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.anatolich.subscriptions.support.domain.ExtendedComparable;
import net.anatolich.subscriptions.support.domain.Invariants;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MonetaryAmount implements ExtendedComparable<MonetaryAmount> {

    public static final MonetaryAmount ZERO = MonetaryAmount.of(BigDecimal.ZERO);
    private BigDecimal amount;

    public MonetaryAmount(BigDecimal amount) {
        setAmount(amount);
    }

    public static MonetaryAmount of(BigDecimal amount) {
        return new MonetaryAmount(amount);
    }

    public static MonetaryAmount of(double amount) {
        return new MonetaryAmount(BigDecimal.valueOf(amount));
    }

    private void setAmount(BigDecimal amount) {
        Invariants.checkValue(amount, Objects::nonNull, "amount must be set");
        if (this.amount != null) {
            throw new IllegalStateException("amount cannot be changed");
        }
        this.amount = amount;
    }

    @Override
    public int compareTo(MonetaryAmount otherAmount) {
        return amount.compareTo(otherAmount.amount);
    }

    public MonetaryAmount add(MonetaryAmount other) {
        return MonetaryAmount.of(this.amount.add(other.amount));
    }

    public MonetaryAmount multiply(BigDecimal rate) {
        return MonetaryAmount.of(this.amount.multiply(rate));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MonetaryAmount otherAmount = (MonetaryAmount) o;
        return this.compare(otherAmount).isEqual();
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }
}
