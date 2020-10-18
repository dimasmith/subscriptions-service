package net.anatolich.subscriptions.currency.domain;

public interface ExchangeRateProvidersRepository {

    ExchangeRateProvider findByName(String name);
}
