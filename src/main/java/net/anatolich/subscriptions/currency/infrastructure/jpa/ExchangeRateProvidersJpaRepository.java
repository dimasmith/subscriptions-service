package net.anatolich.subscriptions.currency.infrastructure.jpa;

import net.anatolich.subscriptions.currency.domain.ExchangeRateProvider;
import net.anatolich.subscriptions.currency.domain.ExchangeRateProvidersRepository;
import org.springframework.stereotype.Component;

@Component
public class ExchangeRateProvidersJpaRepository implements ExchangeRateProvidersRepository {

    private final ExchangeRateProviderDataRepository repository;

    public ExchangeRateProvidersJpaRepository(
        ExchangeRateProviderDataRepository repository) {
        this.repository = repository;
    }

    @Override
    public ExchangeRateProvider findByName(String name) {
        return repository.findByName(name);
    }
}
