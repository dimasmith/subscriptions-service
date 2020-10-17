ALTER TABLE subscription
    ADD COLUMN owner VARCHAR(50) NOT NULL REFERENCES users (username);

