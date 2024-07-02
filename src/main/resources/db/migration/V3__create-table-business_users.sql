CREATE TABLE business_users (
    id BINARY(16) PRIMARY KEY NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    address_id BIGINT NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    password VARCHAR(60) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP NULL,
    active BOOLEAN NOT NULL,

    FOREIGN KEY (address_id) REFERENCES addresses(id)
);