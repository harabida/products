CREATE TABLE IF NOT EXISTS product (
id serial not null primary key,
code varchar(50) NULL,
name varchar(50) NULL,
description varchar(255) NULL,
price DECIMAL(10,2) NOT NULL,
quantity INT NOT NULL,
inventory_status varchar(50) NULL,
category varchar(50) NULL,
image varchar(255) NULL,
rating INT NULL

);

