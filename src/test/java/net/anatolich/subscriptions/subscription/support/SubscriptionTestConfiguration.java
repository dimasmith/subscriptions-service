package net.anatolich.subscriptions.subscription.support;

import java.util.Currency;
import net.anatolich.subscriptions.subscription.domain.CurrencyConverter;
import net.anatolich.subscriptions.subscription.domain.PreferredCurrencyProvider;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

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
