package net.anatolich.subscriptions.currency.infrastructure.jpa;

import net.anatolich.subscriptions.currency.domain.model.ExchangeRateProvider;
import net.anatolich.subscriptions.currency.domain.service.ExchangeRateProvidersRepository;
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
