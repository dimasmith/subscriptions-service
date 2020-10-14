CREATE TABLE subscription
(
    id       BIGINT PRIMARY KEY,
    name     VARCHAR(100)   NOT NULL,
    currency VARCHAR(3)     NOT NULL,
    amount   DECIMAL(10, 2) NOT NULL,
    cadence  VARCHAR(100)   NOT NULL
);

CREATE TABLE subscription_periods
(
    subscription_id BIGINT       NOT NULL REFERENCES subscription (id),
    period_name     VARCHAR(100) NOT NULL,
    PRIMARY KEY (subscription_id, period_name)
);

