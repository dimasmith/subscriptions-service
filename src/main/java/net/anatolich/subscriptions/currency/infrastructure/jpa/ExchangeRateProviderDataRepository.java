package net.anatolich.subscriptions.currency.infrastructure.jpa;

import net.anatolich.subscriptions.currency.domain.model.ExchangeRateProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRateProviderDataRepository extends JpaRepository<ExchangeRateProvider, Long> {

    ExchangeRateProvider findByName(String name);
}
