package net.anatolich.subscriptions.profile.domain.service;

import java.util.Currency;
import net.anatolich.subscriptions.security.domain.model.UserId;
import net.anatolich.subscriptions.subscription.domain.service.PreferredCurrencyProvider;
import org.springframework.stereotype.Component;

@Component
public class DefaultPreferredCurrencyProvider implements PreferredCurrencyProvider {

    @Override
    public Currency preferredCurrencyOf(UserId user) {
        return Currency.getInstance("UAH");
    }
}
