package net.anatolich.subscriptions.subscription.application;

import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import net.anatolich.subscriptions.security.domain.UserId;
import net.anatolich.subscriptions.security.domain.UserProvider;
import net.anatolich.subscriptions.subscription.domain.CurrencyConverter;
import net.anatolich.subscriptions.subscription.domain.Money;
import net.anatolich.subscriptions.subscription.domain.PaymentSchedule;
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
    public void subscribe(String serviceName, Money fee, PaymentSchedule schedule) {
        var currentUser = userProvider.currentUser();
        log.info("subscribing {} to {}", currentUser, serviceName);
        final var subscription = Subscription.subscription(
            serviceName, currentUser, fee, schedule);
        final var subscriptionId = subscriptions.add(subscription);
        log.info("subscribed to {} with id {}", serviceName, subscriptionId);
    }

    @Transactional(readOnly = true)
    public MonthlyFee calculateMonthlyFee(Month month, int year) {
        log.info("calculating monthly fee for {} of {}", month, year);
        final UserId owner = userProvider.currentUser();
        var preferredCurrency = preferredCurrencyProvider.preferredCurrencyOf(owner);
        final List<Subscription> activeSubscriptions = subscriptions.findSubscriptionsForMonth(month, owner);

        var subscriptionFees = activeSubscriptions.stream()
                .map(subscription -> new SubscriptionFee(
                        subscription.service(),
                        subscription.fee(),
                        currencyConverter.convert(subscription.fee(), preferredCurrency)
                ))
                .collect(Collectors.toList());

        var total = subscriptionFees.stream()
            .map(SubscriptionFee::convertedFee)
            .reduce(Money::add)
            .orElse(Money.zero(preferredCurrency));

        return new MonthlyFee(total, subscriptionFees);
    }

}
