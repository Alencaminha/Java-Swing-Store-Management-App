CREATE DATABASE swing_store_database;
USE swing_store_database;

CREATE TABLE User(
    username VARCHAR(255),
	full_name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    access_level VARCHAR(255) NOT NULL,
    PRIMARY KEY(username)
);

CREATE TABLE Product(
	id INTEGER AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL UNIQUE,
    category VARCHAR(255),
    price VARCHAR(255) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE Sale(
    id INTEGER AUTO_INCREMENT,
    total_cost FLOAT NOT NULL,
    seller_username VARCHAR(255),
    date DATE NOT NULL,
    PRIMARY KEY(id)
);

-- DROP TABLE User;
-- DROP TABLE Product;
-- DROP TABLE Sale;

INSERT INTO User VALUES("admin", "ADMIN", "test", "ADMINTEST@hotmail.com", "Administrator");
INSERT INTO User VALUES("acaminha", "Alexandre", "test123", "alexandrencaminha@gmail.com", "Manager");
INSERT INTO User VALUES("cacaminha", "Caio", "test123", "caioncaminha@gmail.com", "Attendant");

INSERT INTO Product VALUES(null, "Cheeseburger", "Burger", "9,99");
INSERT INTO Product VALUES(null, "Coca-Cola", "Drink", "3,99");

SELECT * FROM User;
SELECT * FROM Product;
SELECT * FROM Sale;
