DROP TABLE salesorder
DROP TABLE customer
DROP TABLE address
DROP TABLE item
DROP TABLE product_range
DROP TABLE employee
CREATE TABLE employee ( employee_id INTEGER PRIMARY KEY, firstname VARCHAR(256), lastname VARCHAR(256), address VARCHAR(256), city VARCHAR(256), postcode VARCHAR(256), salary NUMERIC(10,2), company_id INTEGER )
CREATE TABLE product_range ( product_range_id INTEGER PRIMARY KEY, name VARCHAR(256))
CREATE TABLE item ( item_id INTEGER PRIMARY KEY, name VARCHAR(256), price NUMERIC(10,2), product_range_id INTEGER,  FOREIGN KEY (product_range_id) REFERENCES product_range)
CREATE TABLE address (address_id INTEGER PRIMARY KEY, address VARCHAR(40), street VARCHAR(40), city  VARCHAR(25), state CHAR(2),  zipcode VARCHAR(10), country VARCHAR(20))
CREATE TABLE customer (customer_id INTEGER PRIMARY KEY, name VARCHAR(30), telephone VARCHAR(20), address_id INTEGER, FOREIGN KEY (address_id) REFERENCES address)
CREATE TABLE salesorder (order_id INTEGER PRIMARY KEY,customer_id INTEGER, employee_id INTEGER, item_id INTEGER, order_date DATE, ship_date DATE, payment NUMERIC(10,2), FOREIGN KEY (customer_id) REFERENCES customer, FOREIGN KEY (employee_id) REFERENCES employee, FOREIGN KEY (item_id) REFERENCES item)
INSERT INTO employee (employee_id, firstname, lastname, address, city, postcode, salary, company_id) VALUES (1, 'Jane', 'Doe', '1 Main St', 'Springfield', '12345', 50000.00, 1)
INSERT INTO product_range (product_range_id, name) VALUES (1, 'General')
INSERT INTO item (item_id, name, price, product_range_id) VALUES (1, 'Widget', 9.99, 1)
INSERT INTO address (address_id, address, street, city, state, zipcode, country) VALUES (1, '2 Oak Ave', 'Oak Ave', 'Shelbyville', 'IL', '62000', 'US')
INSERT INTO customer (customer_id, name, telephone, address_id) VALUES (1, 'Acme Corp', '555-0100', 1)
INSERT INTO salesorder (order_id, customer_id, employee_id, item_id, order_date, ship_date, payment) VALUES (1, 1, 1, 1, '2024-01-01', '2024-01-05', 9.99)