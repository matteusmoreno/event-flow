CREATE TABLE credit_cards (
    id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    name VARCHAR(255) NOT NULL,
    number VARCHAR(255) NOT NULL,
    cvv VARCHAR(50) NOT NULL,
    expiration_date VARCHAR(10) NOT NULL,
    user_id BINARY(16) NOT NULL,
    created_at DATETIME,
    updated_at DATETIME,
    deleted_at DATETIME,
    active BOOLEAN,

    FOREIGN KEY (user_id) REFERENCES users(id)
);
