package net.anatolich.subscriptions.subscription.domain;

import java.math.BigDecimal;
import java.util.Currency;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Money {
    @Column(nullable = false)
    private Currency currency;
    @Column(nullable = false)
    private BigDecimal amount;

    private Money(BigDecimal amount, Currency currency) {
        setAmount(amount);
        setCurrency(currency);
    }

    public static Money fee(BigDecimal amount, Currency currency) {
        return new Money(amount, currency);
    }

    private void setCurrency(Currency currency) {
        if (currency == null) {
            throw new IllegalArgumentException("currency must be set");
        }
        if (this.currency != null) {
            throw new IllegalStateException("currency cannot be changed");
        }
        this.currency = currency;
    }

    private void setAmount(BigDecimal amount) {
        if (amount == null) {
            throw new IllegalArgumentException("amount must be set");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("fee amount must be greater than 0");
        }
        if (this.amount != null) {
            throw new IllegalStateException("amount cannot be changed");
        }
        this.amount = amount;
    }
}
