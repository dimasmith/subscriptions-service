package net.anatolich.subscriptions.profile;

import java.util.Currency;
import net.anatolich.subscriptions.security.domain.UserId;
import net.anatolich.subscriptions.subscription.domain.PreferredCurrencyProvider;
import org.springframework.stereotype.Component;

@Component
public class StubPreferredCurrencyProvider implements PreferredCurrencyProvider {

    @Override
    public Currency preferredCurrencyOf(UserId user) {
        return Currency.getInstance("UAH");
    }
}
