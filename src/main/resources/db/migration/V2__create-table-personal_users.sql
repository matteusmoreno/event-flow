CREATE TABLE personal_users (
    id BINARY(16) PRIMARY KEY NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    birth_date DATE NOT NULL,
    phone VARCHAR(20) UNIQUE,
    address_id BIGINT,
    email VARCHAR(120) NOT NULL UNIQUE,
    password VARCHAR(60) NOT NULL,
    created_at DATETIME,
    updated_at DATETIME,
    deleted_at DATETIME,
    active TINYINT(1) NOT NULL,

    FOREIGN KEY (address_id) REFERENCES addresses(id)
);
