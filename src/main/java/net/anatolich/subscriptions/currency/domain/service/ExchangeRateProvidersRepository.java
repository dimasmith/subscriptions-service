package net.anatolich.subscriptions.currency.domain.service;

import net.anatolich.subscriptions.currency.domain.model.ExchangeRateProvider;

public interface ExchangeRateProvidersRepository {

    ExchangeRateProvider findByName(String name);
}
