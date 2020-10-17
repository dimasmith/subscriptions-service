package net.anatolich.subscriptions.subscription.domain;

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
import net.anatolich.subscriptions.security.domain.UserId;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"name", "fee", "owner"})
public class Subscription {

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

    public Long id() {
        return id;
    }

    private void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name must be set");
        }
        this.name = name;
    }

    private void setFee(Money fee) {
        if (fee == null) {
            throw new IllegalArgumentException("fee must be set");
        }
        this.fee = fee;
    }

    private void setSchedule(PaymentSchedule schedule) {
        if (schedule == null) {
            throw new IllegalArgumentException("schedule must be set");
        }
        this.schedule = schedule;
    }

    public void setOwner(UserId owner) {
        if (owner == null) {
            throw new IllegalArgumentException("owner must be set");
        }
        this.owner = owner;
    }
}
