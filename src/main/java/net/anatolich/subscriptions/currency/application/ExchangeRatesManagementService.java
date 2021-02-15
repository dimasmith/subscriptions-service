package net.anatolich.subscriptions.currency.application;

import java.util.Collection;
import java.util.Set;
import net.anatolich.subscriptions.currency.domain.model.ExchangeRate;
import net.anatolich.subscriptions.currency.domain.service.ExchangeRateProvidersRepository;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExchangeRatesManagementService {

    private final ExchangeRateProvidersRepository rateProviders;

    public ExchangeRatesManagementService(ExchangeRateProvidersRepository rateProviders) {
        this.rateProviders = rateProviders;
    }

    @Transactional
    @Secured("ROLE_ADMIN")
    public void updateAdminExchangeRates(Collection<ExchangeRate> newRates) {
        final var adminRates = rateProviders.findByName("admin");
        adminRates.updateRates(newRates);
    }

    @Transactional(readOnly = true)
    public Set<ExchangeRate> seeExchangeRates() {
        final var adminRates = rateProviders.findByName("admin");
        return adminRates.rates();
    }
}
