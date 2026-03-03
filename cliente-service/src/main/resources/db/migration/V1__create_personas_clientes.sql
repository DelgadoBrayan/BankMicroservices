-- ── Tables ────────────────────────────────────────────────────────────────────

CREATE TABLE IF NOT EXISTS persons (
                                       id             BIGSERIAL PRIMARY KEY,
                                       name           VARCHAR(100) NOT NULL,
    gender         VARCHAR(20),
    age            INT,
    identification VARCHAR(20) NOT NULL UNIQUE,
    address        VARCHAR(200),
    phone          VARCHAR(20),
    created_at     TIMESTAMP DEFAULT NOW(),
    updated_at     TIMESTAMP DEFAULT NOW()
    );

CREATE TABLE IF NOT EXISTS clients (
                                       id             BIGSERIAL PRIMARY KEY,
                                       person_id      BIGINT NOT NULL REFERENCES persons(id) ON DELETE CASCADE,
    client_id      VARCHAR(50) NOT NULL UNIQUE,
    password       VARCHAR(100) NOT NULL,
    status         BOOLEAN NOT NULL DEFAULT TRUE,
    created_at     TIMESTAMP DEFAULT NOW(),
    updated_at     TIMESTAMP DEFAULT NOW()
    );

-- ── Indexes ───────────────────────────────────────────────────────────────────

CREATE UNIQUE INDEX IF NOT EXISTS idx_persons_identification
    ON persons (identification);

CREATE UNIQUE INDEX IF NOT EXISTS idx_clients_client_id
    ON clients (client_id);

CREATE INDEX IF NOT EXISTS idx_clients_person_id
    ON clients (person_id);

CREATE INDEX IF NOT EXISTS idx_clients_status
    ON clients (status);