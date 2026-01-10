ALTER table accounts
add constraint fk_accounts_users
FOREIGN KEY (user_id)
REFERENCES users(id);

CREATE INDEX idx_accounts_user_id ON accounts(user_id);