DROP TABLE IF EXISTS prices;

CREATE TABLE IF NOT EXISTS prices (
    price_list BIGINT PRIMARY KEY,
    brand_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    start_date TIMESTAMP NOT NULL,
    priority INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    end_date TIMESTAMP NOT NULL,
    currency VARCHAR(3) NOT NULL
);

INSERT INTO prices (price_list, brand_id, product_id, start_date, priority, price, end_date, currency)
VALUES
    (1, 1, 35455, '2020-06-14 00:00:00', 0, 35.50, '2020-12-31 23:59:59', 'EUR'),
    (2, 1, 35455, '2020-06-14 15:00:00', 1, 25.45, '2020-06-14 18:30:00', 'EUR'),
    (3, 1, 35455, '2020-06-15 00:00:00', 1, 30.50, '2020-06-15 11:00:00', 'EUR'),
    (4, 1, 35455, '2020-06-15 16:00:00', 1, 38.95, '2020-12-31 23:59:59', 'EUR'),
    (5, 1, 35455, '2020-06-14 10:00:00', 0, 20.00, '2020-06-14 16:00:00', 'EUR');