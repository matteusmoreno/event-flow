CREATE TABLE addresses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    zipcode VARCHAR(20),
    street VARCHAR(255),
    neighborhood VARCHAR(255),
    city VARCHAR(255),
    state VARCHAR(255)
);
