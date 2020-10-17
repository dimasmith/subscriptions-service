package net.anatolich.subscriptions.subscription.application;

import net.anatolich.subscriptions.subscription.domain.Money;

public class MonthlyFee {

    private final Money total;

    private MonthlyFee(Money total) {
        this.total = total;
    }

    public static MonthlyFee withTotal(Money total) {
        return new MonthlyFee(total);
    }

    public Money total() {
        return total;
    }
}
