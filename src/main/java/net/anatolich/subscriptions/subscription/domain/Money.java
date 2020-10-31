package net.anatolich.subscriptions.subscription.domain;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.anatolich.subscriptions.support.domain.Invariants;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
@ToString
public class Money {

    @Column(nullable = false)
    private Currency currency;
    @Column(nullable = false)
    private MonetaryAmount amount;

    private Money(MonetaryAmount amount, Currency currency) {
        setAmount(amount);
        setCurrency(currency);
    }

    public static Money of(MonetaryAmount amount, Currency currency) {
        return new Money(amount, currency);
    }

    public static Money of(BigDecimal amount, Currency currency) {
        return new Money(MonetaryAmount.of(amount), currency);
    }

    public static Money of(double amount, String currencyCode) {
        return new Money(
            MonetaryAmount.of(amount),
            Currency.getInstance(currencyCode)
        );
    }

    public static Money zero(Currency currency) {
        return of(MonetaryAmount.ZERO, currency);
    }

    public Money add(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("cannot add monetary values in different currencies");
        }
        return new Money(amount.add(other.amount), currency);
    }

    public Money convertToCurrency(Currency targetCurrency, BigDecimal rate) {
        return of(getAmount().multiply(rate), targetCurrency);
    }

    private void setCurrency(Currency currency) {
        Invariants.checkValue(currency, Objects::nonNull, "currency must be set");
        Invariants.checkImmutable(this.currency, "currency cannot be changed");
        this.currency = currency;
    }

    private void setAmount(MonetaryAmount amount) {
        Invariants.checkValue(amount, Objects::nonNull, "amount must be set");
        Invariants.checkImmutable(this.amount, "amount cannot be changed");
        this.amount = amount;
    }
}
