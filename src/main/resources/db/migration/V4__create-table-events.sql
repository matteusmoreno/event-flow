CREATE TABLE events (
    id BINARY(16) PRIMARY KEY NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    address_id BIGINT NOT NULL,
    date DATETIME NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    ticket_available INT NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME,
    deleted_at DATETIME,
    active BOOLEAN NOT NULL,

    FOREIGN KEY (address_id) REFERENCES addresses(id)
);