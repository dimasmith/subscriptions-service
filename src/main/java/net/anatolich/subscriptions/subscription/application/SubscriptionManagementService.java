package net.anatolich.subscriptions.subscription.application;

import java.time.Month;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import net.anatolich.subscriptions.security.domain.UserId;
import net.anatolich.subscriptions.security.domain.UserProvider;
import net.anatolich.subscriptions.subscription.domain.CurrencyConverter;
import net.anatolich.subscriptions.subscription.domain.Money;
import net.anatolich.subscriptions.subscription.domain.PreferredCurrencyProvider;
import net.anatolich.subscriptions.subscription.domain.Subscription;
import net.anatolich.subscriptions.subscription.domain.SubscriptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class SubscriptionManagementService {

    private final SubscriptionRepository subscriptions;
    private final UserProvider userProvider;
    private final PreferredCurrencyProvider preferredCurrencyProvider;
    private final CurrencyConverter currencyConverter;

    public SubscriptionManagementService(
        SubscriptionRepository subscriptions,
        UserProvider userProvider,
        PreferredCurrencyProvider preferredCurrencyProvider,
        CurrencyConverter currencyConverter) {
        this.subscriptions = subscriptions;
        this.userProvider = userProvider;
        this.preferredCurrencyProvider = preferredCurrencyProvider;
        this.currencyConverter = currencyConverter;
    }

    @Transactional
    public void subscribe(SubscribeCommand subscribeCommand) {
        var currentUser = userProvider.currentUser();
        log.info("subscribing {} to {}", currentUser, subscribeCommand);
        final var subscription = Subscription.subscription(
            subscribeCommand.getService().getName(),
            currentUser,
            subscribeCommand.getSubscription().fee(),
            subscribeCommand.getSubscription().schedule()
        );
        final var subscriptionId = subscriptions.add(subscription);
        log.info("subscribed to {} with id {}", subscribeCommand, subscriptionId);
    }

    @Transactional(readOnly = true)
    public MonthlyFee calculateMonthlyFee(Month month, int year) {
        log.info("calculating monthly fee for {} of {}", month, year);
        final UserId owner = userProvider.currentUser();
        var preferredCurrency = preferredCurrencyProvider.preferredCurrencyOf(owner);
        List<Subscription> activeSubscriptions = subscriptions.findSubscriptionsForMonth(month, owner);
        var total = activeSubscriptions.stream()
            .map(Subscription::fee)
            .map(fee -> currencyConverter.convert(fee, preferredCurrency))
            .reduce(Money::add)
            .orElse(Money.zero(preferredCurrency));

        return MonthlyFee.withTotal(total);
    }

}
