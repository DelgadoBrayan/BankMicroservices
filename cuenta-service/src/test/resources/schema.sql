CREATE TABLE IF NOT EXISTS accounts (
                                        id                BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        account_number    VARCHAR(20)   NOT NULL UNIQUE,
    account_type      VARCHAR(30)   NOT NULL,
    initial_balance   DECIMAL(15,2) NOT NULL DEFAULT 0.00,
    available_balance DECIMAL(15,2) NOT NULL DEFAULT 0.00,
    status            BOOLEAN       NOT NULL DEFAULT TRUE,
    client_id         VARCHAR(50)   NOT NULL,
    created_at        TIMESTAMP     DEFAULT NOW(),
    updated_at        TIMESTAMP     DEFAULT NOW()
    );

CREATE TABLE IF NOT EXISTS movements (
                                         id              BIGINT AUTO_INCREMENT PRIMARY KEY,
                                         account_id      BIGINT         NOT NULL,
                                         date            TIMESTAMP      NOT NULL DEFAULT NOW(),
    movement_type   VARCHAR(20)    NOT NULL,
    amount          DECIMAL(15,2)  NOT NULL,
    balance         DECIMAL(15,2)  NOT NULL,
    created_at      TIMESTAMP      DEFAULT NOW()
    );