CREATE TABLE IF NOT EXISTS LenderSubmerchant (
    id BIGINT PRIMARY KEY,
    lender_name VARCHAR(255),
    sub_merchant_id VARCHAR(255) UNIQUE NOT NULL,
    environment VARCHAR(255)
);
