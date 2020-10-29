package net.anatolich.subscriptions.subscription.support;

import net.anatolich.subscriptions.subscription.domain.CurrencyConverter;
import net.anatolich.subscriptions.subscription.domain.PreferredCurrencyProvider;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.Currency;

@TestConfiguration
public class SubscriptionTestConfiguration {

    @Bean
    public CurrencyConverter currencyConverter() {
        return new TestCurrencyConverter();
    }

    @Bean
    public PreferredCurrencyProvider preferredCurrencyProvider() {
        return user -> Currency.getInstance("UAH");
    }
}
