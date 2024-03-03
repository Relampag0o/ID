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


INSERT INTO product (id, name, price) VALUES ('sanMiguel1', 'San Miguel', 2.80);
INSERT INTO tablee (id, name) VALUES ('table1', 'Table 1');

select * from tablee;