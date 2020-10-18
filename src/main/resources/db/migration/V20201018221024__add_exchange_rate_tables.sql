CREATE TABLE exchange_rate_provider
(
    id    BIGINT PRIMARY KEY,
    name  VARCHAR(32) UNIQUE  NOT NULL,
    title VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE exchange_rate_provider_rates
(
    exchange_rate_provider_id BIGINT     NOT NULL REFERENCES exchange_rate_provider (id),
    source_currency           VARCHAR(3) NOT NULL,
    target_currency           VARCHAR(3) NOT NULL,
    rate                      DECIMAL(10,5)    NOT NULL,
    updated_on                DATETIME   NOT NULL DEFAULT now(),
    PRIMARY KEY (exchange_rate_provider_id, source_currency, target_currency)
);

INSERT INTO exchange_rate_provider(id, name, title)
VALUES (1, 'admin', 'Administration');

INSERT INTO exchange_rate_provider_rates (exchange_rate_provider_id, source_currency, target_currency, rate)
VALUES (1, 'USD', 'UAH', 28.0);
INSERT INTO exchange_rate_provider_rates (exchange_rate_provider_id, source_currency, target_currency, rate)
VALUES (1, 'UAH', 'USD', 0.0357);
INSERT INTO exchange_rate_provider_rates (exchange_rate_provider_id, source_currency, target_currency, rate)
VALUES (1, 'EUR', 'UAH', 33.0);
INSERT INTO exchange_rate_provider_rates (exchange_rate_provider_id, source_currency, target_currency, rate)
VALUES (1, 'UAH', 'EUR', 0.0303);
