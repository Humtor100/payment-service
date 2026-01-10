CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(100) Not Null,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(10) NOT NULL
);