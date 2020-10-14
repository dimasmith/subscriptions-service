package net.anatolich.subscriptions.subscription.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.TableGenerator;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"name", "fee"})
public class Subscription {

    @Id @GeneratedValue(generator = "subscriptionSequence")
    @TableGenerator(name = "subscriptionSequence", table = "subscription_sequence")
    private Long id;
    @Column(nullable = false, length = 100)
    private String name;
    private Money fee;
    private PaymentSchedule schedule;

    private Subscription(String name, Money fee, PaymentSchedule schedule) {
        setName(name);
        setFee(fee);
        setSchedule(schedule);
    }

    public static Subscription subscription(String name, Money fee, PaymentSchedule schedule) {
        return new Subscription(name, fee, schedule);
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
}
