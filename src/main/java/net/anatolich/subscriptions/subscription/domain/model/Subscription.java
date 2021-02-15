package net.anatolich.subscriptions.subscription.domain.model;

import static net.anatolich.subscriptions.support.domain.ExtendedComparable.Comparison.higherOrEqualThan;

import java.time.Month;
import java.util.Objects;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.TableGenerator;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.anatolich.subscriptions.security.domain.model.UserId;
import net.anatolich.subscriptions.support.domain.BaseEntity;
import net.anatolich.subscriptions.support.domain.Invariants;
import net.anatolich.subscriptions.support.domain.Invariants.StringInvariants;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"name", "fee", "owner"})
@SuppressWarnings("java:S2160") // equals and hash code comes from BaseEntity intentionally
public class Subscription extends BaseEntity<Subscription, Long> {

    private static final MonetaryAmount LOWEST_FEE_AMOUNT = MonetaryAmount.of(0.01);

    @Id
    @GeneratedValue(generator = "subscriptionSequence")
    @TableGenerator(name = "subscriptionSequence", table = "subscription_sequence")
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Embedded
    @AttributeOverride(
        name = "username",
        column = @Column(name = "owner", length = 50, nullable = false, updatable = false)
    )
    private UserId owner;
    @Embedded
    private Money fee;
    @Embedded
    private PaymentSchedule schedule;

    private Subscription(String name, UserId owner, Money fee, PaymentSchedule schedule) {
        setName(name);
        setFee(fee);
        setSchedule(schedule);
        setOwner(owner);
    }

    public static Subscription subscription(String name, UserId owner, Money fee, PaymentSchedule schedule) {
        return new Subscription(name, owner, fee, schedule);
    }

    @Override
    public Long id() {
        return id;
    }

    public Money fee() {
        return fee;
    }

    public String service() {
        return name;
    }

    public boolean activeFor(Month month) {
        return schedule.activeFor(month);
    }

    public boolean belongsTo(UserId owner) {
        return this.owner.equals(owner);
    }

    private void setName(String name) {
        Invariants.checkValue(name, StringInvariants.NOT_BLANK, "name must be set");
        this.name = name;
    }

    private void setFee(Money fee) {
        Invariants.checkValue(fee, Objects::nonNull, "fee must be set");
        Invariants.checkValue(fee.getAmount(), higherOrEqualThan(LOWEST_FEE_AMOUNT), "fee amount is too small");
        this.fee = fee;
    }

    private void setSchedule(PaymentSchedule schedule) {
        Invariants.checkValue(schedule, Objects::nonNull, "schedule must be set");
        this.schedule = schedule;
    }

    private void setOwner(UserId owner) {
        Invariants.checkValue(owner, Objects::nonNull, "owner must be set");
        this.owner = owner;
    }
}
