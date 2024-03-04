use restaurant;


-- Drop the table_product table
DROP TABLE IF EXISTS table_product;

-- Drop the tablee table
DROP TABLE IF EXISTS tablee;

-- Drop the product table
DROP TABLE IF EXISTS product;

-- Recreate the product table
CREATE TABLE product (
    id VARCHAR(255) NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL
);

-- Recreate the tablee table with the id column as VARCHAR(255)
CREATE TABLE tablee (
    id VARCHAR(255) NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Recreate the table_product table
CREATE TABLE table_product (
    tablee_id VARCHAR(255),
    product_id VARCHAR(255),
    FOREIGN KEY (tablee_id) REFERENCES tablee(id),
    FOREIGN KEY (product_id) REFERENCES product(id)
);

CREATE TABLE Report (
    id VARCHAR(255) NOT NULL PRIMARY KEY,
    tableId VARCHAR(255) NOT NULL,
    productId VARCHAR(255) NOT NULL,
    quantity INT NOT NULL,
    totalPrice DECIMAL(10, 2) NOT NULL,
    transactionTime TIMESTAMP NOT NULL,
    FOREIGN KEY (tableId) REFERENCES tablee(id),
    FOREIGN KEY (productId) REFERENCES product(id)
);


INSERT INTO product (id, name, price) VALUES ('sanMiguel1', 'San Miguel', 2.80);
INSERT INTO product (id, name, price) VALUES ('alhambra1', 'Alhambra', 3.50);

INSERT INTO tablee (id, name) VALUES ('table1', 'Table 1');
INSERT INTO tablee (id, name) VALUES ('table2', 'Table 2');

ALTER TABLE tablee ADD total DECIMAL(10, 2) NOT NULL DEFAULT 0.0;

select * from tablee;