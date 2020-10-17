ALTER TABLE subscription
    ADD COLUMN owner VARCHAR(50) NOT NULL REFERENCES users (username);

UPDATE subscription
SET owner = 'admin'
WHERE owner IS NULL;
