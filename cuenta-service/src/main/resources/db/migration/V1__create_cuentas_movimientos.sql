-- ── Tables ────────────────────────────────────────────────────────────────────

CREATE TABLE IF NOT EXISTS accounts (
                                        id                BIGSERIAL PRIMARY KEY,
                                        account_number    VARCHAR(20)   NOT NULL UNIQUE,
    account_type      VARCHAR(20)   NOT NULL CHECK (account_type IN ('SAVINGS', 'CHECKING')),
    initial_balance   DECIMAL(15,2) NOT NULL DEFAULT 0.00,
    available_balance DECIMAL(15,2) NOT NULL DEFAULT 0.00,
    status            BOOLEAN       NOT NULL DEFAULT TRUE,
    client_id         VARCHAR(50)   NOT NULL,
    created_at        TIMESTAMP DEFAULT NOW(),
    updated_at        TIMESTAMP DEFAULT NOW()
    );

CREATE TABLE IF NOT EXISTS movements (
                                         id              BIGSERIAL PRIMARY KEY,
                                         account_id      BIGINT        NOT NULL REFERENCES accounts(id),
    date            TIMESTAMP     NOT NULL DEFAULT NOW(),
    movement_type   VARCHAR(20)   NOT NULL,
    amount          DECIMAL(15,2) NOT NULL,
    balance         DECIMAL(15,2) NOT NULL,
    created_at      TIMESTAMP DEFAULT NOW()
    );

-- ── Indexes ───────────────────────────────────────────────────────────────────

CREATE UNIQUE INDEX IF NOT EXISTS idx_accounts_account_number
    ON accounts (account_number);

CREATE INDEX IF NOT EXISTS idx_accounts_client_id
    ON accounts (client_id);

CREATE INDEX IF NOT EXISTS idx_accounts_status
    ON accounts (status);

CREATE INDEX IF NOT EXISTS idx_accounts_client_status
    ON accounts (client_id, status);

CREATE INDEX IF NOT EXISTS idx_movements_account_id
    ON movements (account_id);

CREATE INDEX IF NOT EXISTS idx_movements_date
    ON movements (date);

CREATE INDEX IF NOT EXISTS idx_movements_account_date
    ON movements (account_id, date);