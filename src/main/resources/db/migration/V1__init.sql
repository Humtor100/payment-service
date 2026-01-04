CREATE TABLE accounts (
                          id BIGSERIAL PRIMARY KEY,
                          user_id BIGINT NOT NULL,
                          balance NUMERIC(19, 2) NOT NULL,
                          currency VARCHAR(3) NOT NULL
);

CREATE TABLE transactions (
                              id BIGSERIAL PRIMARY KEY,
                              source_account_id BIGINT,
                              target_account_id BIGINT NOT NULL,
                              amount NUMERIC(19, 2) NOT NULL,
                              currency VARCHAR(3) NOT NULL,
                              status VARCHAR(30) NOT NULL,
                              created_at TIMESTAMP NOT NULL
);