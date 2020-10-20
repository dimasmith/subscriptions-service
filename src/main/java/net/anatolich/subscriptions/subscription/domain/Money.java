package net.anatolich.subscriptions.subscription.domain;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Embeddable
@Getter
@Setter(AccessLevel.PROTECTED)
@ToString
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

    public static Money of(BigDecimal amount, Currency currency) {
        return new Money(amount, currency);
    }

    public static Money of(double amount, String currencyCode) {
        return new Money(
            BigDecimal.valueOf(amount),
            Currency.getInstance(currencyCode)
        );
    }

    public static Money zero(Currency currency) {
        return of(BigDecimal.ZERO, currency);
    }

    public Money add(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("cannot add monetary values in different currencies");
        }
        return new Money(amount.add(other.amount), currency);
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
        if (this.amount != null) {
            throw new IllegalStateException("amount cannot be changed");
        }
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Money)) {
            return false;
        }
        final Money money = (Money) o;
        return currency.equals(money.currency) &&
            amount.compareTo(money.amount) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, amount);
    }
}
