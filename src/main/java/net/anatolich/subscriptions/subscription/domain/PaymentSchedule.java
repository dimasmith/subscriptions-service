package net.anatolich.subscriptions.subscription.domain;

import java.time.Month;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    private void setCadence(Cadence cadence) {
        if (cadence == null) {
            throw new IllegalArgumentException("cadence must be set");
        }
        this.cadence = cadence;
    }

    private void setPeriods(Set<Month> periods) {
        if (periods == null) {
            throw new IllegalArgumentException("periods must be set");
        }
        if (periods.isEmpty()) {
            throw new IllegalArgumentException("at least one period must be selected");
        }
        this.periods = periods;
    }
}
