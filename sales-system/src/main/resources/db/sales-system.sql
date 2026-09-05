-- Database: sales_system
-- DROP DATABASE IF EXISTS sales_system;

CREATE DATABASE sales_system
    WITH
    OWNER = developer
    ENCODING = 'UTF8'
    LC_COLLATE = 'es_ES.UTF-8'
    LC_CTYPE = 'es_ES.UTF-8'
    LOCALE_PROVIDER = 'libc'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

GRANT TEMPORARY, CONNECT ON DATABASE sales_system TO PUBLIC;
GRANT ALL ON DATABASE sales_system TO developer;

-- Role: developer
-- DROP ROLE IF EXISTS developer;

CREATE ROLE developer WITH
    LOGIN
    SUPERUSER
    INHERIT
    CREATEDB
    CREATEROLE
    NOREPLICATION
    BYPASSRLS
    ENCRYPTED PASSWORD 'SCRAM-SHA-256$4096:B1s2tBdeXTxjSreiRVc8Qw==$lVBqxv3H9ObZbHe3ay5tgbTJDYHfnX9kKxISGOpRvSQ=:vnVZWE8UTx53oNKy39gVOhMlV8j832QFAG17IMEaZRE=';

COMMENT ON ROLE developer IS 'cerber0';



CREATE EXTENSION IF NOT EXISTS pgcrypto;


CREATE TABLE user_person(
    id_user_person SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(20) CHECK (role IN ('ADMIN','SELLER')),
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE supplier (
    id_supplier SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    contact VARCHAR(100),
    phone VARCHAR(20),
    email VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE product (
    id_product SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price NUMERIC(10,2) NOT NULL,
    stock INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    supplier_id INT REFERENCES supplier(id_supplier) ON DELETE SET NULL
);

CREATE TABLE sale (
    id_sale SERIAL PRIMARY KEY,
    user_id INT REFERENCES user_person(id_user_person),
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total NUMERIC(10,2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE sale_detail (
    id_sale_detail SERIAL PRIMARY KEY,
    sale_id INT REFERENCES sale(id_sale) ON DELETE CASCADE,
    product_id INT REFERENCES product(id_product),
    quantity INT NOT NULL,
    subtotal NUMERIC(10,2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE cash_closure (
    id_cash_closure SERIAL PRIMARY KEY,
    user_id INT REFERENCES user_person(id_user_person),
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    daily_total NUMERIC(10,2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE invoice (
    id_invoice SERIAL PRIMARY KEY,
    sale_id INT REFERENCES sale(id_sale),
    customer_rfc VARCHAR(13),
    business_name VARCHAR(150),
    address TEXT,
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE sale
    ADD CONSTRAINT fk_sale_user FOREIGN KEY (user_id) REFERENCES user_person(id_user_person) ON DELETE SET NULL;

ALTER TABLE sale_detail
    ADD CONSTRAINT fk_sale_detail_product FOREIGN KEY (product_id) REFERENCES product(id_product) ON DELETE SET NULL;

ALTER TABLE cash_closure
    ADD CONSTRAINT fk_cash_closure_user FOREIGN KEY (user_id) REFERENCES user_person(id_user_person) ON DELETE SET NULL;

ALTER TABLE invoice
    ADD CONSTRAINT fk_invoice_sales FOREIGN KEY (sale_id) REFERENCES sale(id_sale) ON DELETE CASCADE;

--admin0101
INSERT INTO user_person(id_user_person, "name", username, password_hash, "role", active, created_at, updated_at)
VALUES(1, 'Administrador', 'admin', '$2a$10$FWxvdNV3GJDYjVI2dFtMgOUqLIPhDk3y2zjCEqk5upImERzx/zdX.', 'ADMIN', true, '2026-08-30 22:23:35.104', '2026-08-30 22:23:35.104');