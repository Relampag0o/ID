use restaurant;



CREATE TABLE product (
    id VARCHAR(255) NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL
);

CREATE TABLE tablee (
    id VARCHAR(255) NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);


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

-- Insertar productos
INSERT INTO product (id, name, price) VALUES ('sanMiguel1', 'San Miguel', 2.80);
INSERT INTO product (id, name, price) VALUES ('alhambra1', 'Alhambra', 3.50);
INSERT INTO product (id, name, price) VALUES ('cheristoff1', 'Cheristoff', 3.00);
INSERT INTO product (id, name, price) VALUES ('coronita1', 'Coronita', 2.50);
INSERT INTO product (id, name, price) VALUES ('paella1', 'Paella', 10.00);
INSERT INTO product (id, name, price) VALUES ('fabada1', 'Fabada', 8.00);
INSERT INTO product (id, name, price) VALUES ('bacalao1', 'Bacalao Dorado', 12.00);
INSERT INTO product (id, name, price) VALUES ('tortilla1', 'Tortilla', 6.00);
INSERT INTO product (id, name, price) VALUES ('salmorejo1', 'Salmorejo', 5.00);
INSERT INTO product (id, name, price) VALUES ('almejas1', 'Almejas', 15.00);
INSERT INTO product (id, name, price) VALUES ('pulpo1', 'Pulpo', 14.00);
INSERT INTO product (id, name, price) VALUES ('albondigas1', 'Albóndigas', 9.00);
INSERT INTO product (id, name, price) VALUES ('cocacola1', 'Coca Cola', 2.00);
INSERT INTO product (id, name, price) VALUES ('fantaNaranja1', 'Fanta Naranja', 2.00);
INSERT INTO product (id, name, price) VALUES ('fantaLimon1', 'Fanta Limón', 2.00);
INSERT INTO product (id, name, price) VALUES ('pepsi1', 'Pepsi', 2.00);

-- Insertar mesas
INSERT INTO tablee (id, name) VALUES ('table1', 'Table 1');
INSERT INTO tablee (id, name) VALUES ('table2', 'Table 2');
INSERT INTO tablee (id, name) VALUES ('table3', 'Table 3');
INSERT INTO tablee (id, name) VALUES ('table4', 'Table 4');
INSERT INTO tablee (id, name) VALUES ('table5', 'Table 5');
INSERT INTO tablee (id, name) VALUES ('table6', 'Table 6');
INSERT INTO tablee (id, name) VALUES ('table7', 'Table 7');
INSERT INTO tablee (id, name) VALUES ('table8', 'Table 8');
INSERT INTO tablee (id, name) VALUES ('table9', 'Table 9');
INSERT INTO tablee (id, name) VALUES ('table10', 'Table 10');
INSERT INTO tablee (id, name) VALUES ('table11', 'Table 11');
INSERT INTO tablee (id, name) VALUES ('table12', 'Table 12');

ALTER TABLE tablee ADD total DECIMAL(10, 2) NOT NULL DEFAULT 0.0;
ALTER TABLE Report DROP COLUMN total;

select * from tablee;
select * from product;
select * from report;

use restaurant;
show columns from tablee;

select * from table_product;


	 # QUERY TO GENERATE TABLE HISTORIC

SELECT
    product.*,
    COUNT(product_id) as quantity,
    COUNT(product_id) * product.price as total_price
FROM
    product
INNER JOIN
    table_product ON product.id = table_product.product_id
WHERE
    table_product.tablee_id = "table1"
GROUP BY
    product.id;
    use restaurant;

    # QUERY TO GENERATE HISTORIC OF ALL TABLES

SELECT
    t.id AS table_id,
    t.name AS table_name,
    p.id AS product_id,
    p.name AS product_name,
    SUM(r.quantity) AS product_quantity,
    SUM(r.totalPrice) AS product_total_price
FROM
    Report r
INNER JOIN
    tablee t ON r.tableId = t.id
INNER JOIN
    product p ON r.productId = p.id
GROUP BY
    t.id,
    p.id;

    ALTER TABLE table_product DROP INDEX tablee_id;
ALTER TABLE table_product ADD COLUMN quantity INT;

SELECT product.*, COUNT(product_id) as quantity FROM product INNER JOIN table_product ON product.id = table_product.product_id WHERE table_product.tablee_id = 'table1' GROUP BY product.id;

    select * from table_product;


SELECT product.*, COUNT(product_id) as quantity FROM product INNER JOIN table_product ON product.id = table_product.product_id WHERE table_product.tablee_id = 'table1' GROUP BY product.id, table_product.tablee_id;