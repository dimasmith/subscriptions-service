package net.anatolich.subscriptions.subscription.domain.model;

import java.time.Month;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.anatolich.subscriptions.support.domain.Invariants;
import net.anatolich.subscriptions.support.domain.Invariants.CollectionsInvariants;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
public class PaymentSchedule {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Cadence cadence;
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @Column(name = "period_name", nullable = false)
    private Set<Month> periods = new HashSet<>();

    protected PaymentSchedule(Cadence cadence, Set<Month> periods) {
        setCadence(cadence);
        setPeriods(periods);
    }

    public static PaymentSchedule monthly() {
        return new PaymentSchedule(
            Cadence.MONTHLY,
            EnumSet.allOf(Month.class)
        );
    }

    public static PaymentSchedule annual(Month paymentMonth) {
        return new PaymentSchedule(
            Cadence.ANNUAL,
            Set.of(paymentMonth)
        );
    }

    public static PaymentSchedule custom(Collection<Month> months) {
        return new PaymentSchedule(
            Cadence.CUSTOM,
            Set.copyOf(months)
        );
    }

    public static PaymentSchedule custom(Month firstMonth, Month secondMonth, Month... otherMonth) {
        var months = new HashSet<>(Set.of(firstMonth, secondMonth));
        months.addAll(Set.of(otherMonth));
        return new PaymentSchedule(
            Cadence.CUSTOM,
            months
        );
    }

    public boolean activeFor(Month month) {
        return periods.contains(month);
    }

    private void setCadence(Cadence cadence) {
        Invariants.checkValue(cadence, Objects::nonNull, "cadence must be set");
        this.cadence = cadence;
    }

    private void setPeriods(Set<Month> periods) {
        Invariants.checkValue(periods, CollectionsInvariants.NOT_EMPTY, "at least one period must be selected");
        this.periods = periods;
    }
}
