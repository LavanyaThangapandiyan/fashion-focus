use fashion;

/* Create table  ------Register */
create table register
(id int(10)not null auto_increment primary key,
username text(20)not null,
email varchar(20)unique key,
password text(20)not null,
phone_number varchar(10)not null unique,gender varchar(10)not null,`is_active` boolean default true);

/* Create table  ------Product */
create table product(
id int(10)not null auto_increment primary key,
name varchar(20)unique key,
price int(10)not null,
category varchar(100)not null, 
size varchar(10)not null,
quantity int(10)not null,
fabric varchar(10)not null,
gender varchar(10)not null,
image blob not null,
`is_available` boolean default true,
foreign key(category)references category(name)
);

/* Create table  ------Category */
create table category(
id int(10)not null auto_increment primary key,
name varchar(20)not null unique key,
quantity int(10)not null,
`is_available` boolean default true
);


/* Create table  ------wish-list */
create table wish_list(id int(10)not null auto_increment primary key,
customer_id int(20)not null,
product_id int(20)not null,
price int(20)not null,
size varchar(20)not null,
category varchar(20)not null,
`status` boolean default true,
foreign key(customer_id)references register(id),
foreign key(product_id)references product(id)
 );
 
/* Create table  ------orders */
create table orders(id int(10)not null auto_increment primary key,
customer_id int(20)not null,
product_id int(20)not null,
price int(20)not null,
size varchar(20)not null,
category varchar(10)not null,
quantity int(20)not null,
`is_available` boolean default true,
foreign key(customer_id)references register(id),
foreign key(product_id)references product(id)
 );
 
 /* Create table  ------Payment */
  create table payment(
  id int(10)not null auto_increment primary key,
  order_id int(20)not null,
  amount int(10)not null,
  payment_type varchar(10)not null,
  Date date not null,
  foreign key(order_id)references orders(id)
  );
  
/* Create table  ------Cart */
   create table cart(
   id int(10)not null auto_increment primary key,
    order_id int(20)not null,
    customer_id int(20)not null,
    product_id int(20)not null,
    price int(20)not null,
    size varchar(20)not null,
    product_type varchar(20)not null,
    quantity int(10)not null,
    total_amount int(10)not null,
    status boolean default true,
    foreign key(customer_id)references register(id),
	foreign key(order_id)references orders(id),
    foreign key(product_id)references product(id)
   );
   
select id ,username ,email ,
phone_number ,gender ,`is_active`
from register;

select id,name,price,category,size,
quantity,fabric,gender,image,`is_available`
from product;

select id,order_id,customer_id ,
product_id ,price,size,product_type,
quantity,total_amount,status from cart;
    
select id,customer_id,product_id,price,
size,category,`status`  from wish_list;
  
select id,customer_id,product_id,
price,size,category,quantity,
`is_active` from orders;

select id,order_id,
  amount,payment_type,
  Date from payment;
  
select id,name,
quantity,is_available from category;
      
  drop table register;
  drop table cart;
  drop table product;
  drop table wish_list;
  drop table orders;
  drop table payment;
  
  /*-------------------------Audit---------------------------------------------------- */
  use fashion;
/*Audit Table ----Register----*/
DELIMITER $$
CREATE TRIGGER register_insert
BEFORE INSERT ON register
FOR EACH ROW
BEGIN
   IF NEW.id < 0 THEN
      SET NEW.id = 0;
   END IF;
END;
$$
DELIMITER ;

CREATE TABLE register_aud(
`id` INT NOT NULL,
`action` VARCHAR(10) NOT NULL,
`aud_timestamp` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
DELIMITER $$
CREATE TRIGGER register_audit_insert
AFTER INSERT ON register
FOR EACH ROW
BEGIN
INSERT INTO register_aud(id, `action`, aud_timestamp)
VALUES ( NEW.id, 'INSERT', NOW());
END;
$$
DELIMITER ;
DELIMITER $$

CREATE TRIGGER register_audit_delete
AFTER UPDATE ON register
FOR EACH ROW
BEGIN
IF NEW.is_active = 0 THEN  -- If is_active is set to 0, consider it as a DELETE operation
INSERT INTO register(id, `action`, aud_timestamp)
VALUES ( OLD.id, 'DELETE', NOW());
ELSE
INSERT INTO register_aud( id, `action`, aud_timestamp)
VALUES (NEW.id,'UPDATE', NOW());
END IF;
END;
$$
DELIMITER ;

drop table register_aud;
drop table product_aud;
SELECT * FROM register_aud;
SELECT * FROM product_aud;
/*--------------------------------------------------------------------------------------------------*/
/*Audit Table ----Product----*/
DELIMITER $$
CREATE TRIGGER product_insert
BEFORE INSERT ON product
FOR EACH ROW
BEGIN
   IF NEW.id < 0 THEN
      SET NEW.id = 0;
   END IF;
END;
$$
DELIMITER ;

CREATE TABLE product_aud(
`id` INT NOT NULL,
`action` VARCHAR(10) NOT NULL,
`aud_timestamp` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
DELIMITER $$
CREATE TRIGGER product_audit_insert
AFTER INSERT ON product
FOR EACH ROW
BEGIN
INSERT INTO product_aud(id, `action`, aud_timestamp)
VALUES ( NEW.id, 'INSERT', NOW());
END;
$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER product_audit_delete
AFTER UPDATE ON product
FOR EACH ROW
BEGIN
IF NEW.is_available = 0 THEN  -- If is_active is set to 0, consider it as a DELETE operation
INSERT INTO product_aud(id, `action`, aud_timestamp)
VALUES ( OLD.id, 'DELETE', NOW());
ELSE
INSERT INTO product_aud(id, `action`, aud_timestamp)
VALUES (NEW.id, 'UPDATE', NOW());
END IF;
END;
$$
DELIMITER ;
SELECT * FROM product_aud;
/*----------------------------------------------------------------------------------------------------*/
/*Audit Table ----Wish List----*/
DELIMITER $$
CREATE TRIGGER wish_list_insert
BEFORE INSERT ON wish_list
FOR EACH ROW
BEGIN
   IF NEW.id < 0 THEN
      SET NEW.id = 0;
   END IF;
END;
$$
DELIMITER ;

CREATE TABLE wish_list_aud(
`id` INT NOT NULL,
`action` VARCHAR(10) NOT NULL,
`aud_timestamp` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
DELIMITER $$
CREATE TRIGGER wish_list_audit_insert
AFTER INSERT ON wish_list
FOR EACH ROW
BEGIN
INSERT INTO wish_list(id, `action`, aud_timestamp)
VALUES ( NEW.id, 'INSERT', NOW());
END;
$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER wish_list_audit_delete
AFTER UPDATE ON wish_list
FOR EACH ROW
BEGIN
IF NEW.status = 0 THEN  -- If is_active is set to 0, consider it as a DELETE operation
INSERT INTO wish_list_aud(id, `action`, aud_timestamp)
VALUES ( OLD.id, 'DELETE', NOW());
ELSE
INSERT INTO wish_list_aud(id, `action`, aud_timestamp)
VALUES (NEW.id, 'UPDATE', NOW());
END IF;
END;
$$
DELIMITER ;
SELECT * FROM wish_list_aud;

/*----------------------------------------------------------------------------------------------------------------*/
/*Audit Table ----Payment----*/
DELIMITER $$
CREATE TRIGGER payment_insert
BEFORE INSERT ON payment
FOR EACH ROW
BEGIN
   IF NEW.id < 0 THEN
      SET NEW.id = 0;
   END IF;
END;
$$
DELIMITER ;
CREATE TABLE payment_aud(
`id` INT NOT NULL,
`action` VARCHAR(10) NOT NULL,
`aud_timestamp` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
DELIMITER $$
CREATE TRIGGER payment_audit_insert
AFTER INSERT ON payment
FOR EACH ROW
BEGIN
INSERT INTO payment(id, `action`, aud_timestamp)
VALUES ( NEW.id, 'INSERT', NOW());
END;
$$
DELIMITER ;
SELECT * FROM payment_aud;

/*----------------------------------------------------------------------------------------------------------------*/
/*Audit Table ----Orders----*/
DELIMITER $$
CREATE TRIGGER orders_insert
BEFORE INSERT ON orders
FOR EACH ROW
BEGIN
   IF NEW.id < 0 THEN
      SET NEW.id = 0;
   END IF;
END;
$$
DELIMITER ;
CREATE TABLE orders_aud(
`id` INT NOT NULL,
`action` VARCHAR(10) NOT NULL,
`aud_timestamp` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
DELIMITER $$
CREATE TRIGGER orders_audit_insert
AFTER INSERT ON orders
FOR EACH ROW
BEGIN
INSERT INTO orders(id, `action`, aud_timestamp)
VALUES ( NEW.id, 'INSERT', NOW());
END;
$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER orders_audit_delete
AFTER UPDATE ON orders
FOR EACH ROW
BEGIN
IF NEW.is_available = 0 THEN  -- If is_active is set to 0, consider it as a DELETE operation
INSERT INTO orders_aud(id, `action`, aud_timestamp)
VALUES ( OLD.id, 'DELETE', NOW());
ELSE
INSERT INTO orders_aud(id, `action`, aud_timestamp)
VALUES (NEW.id, 'UPDATE', NOW());
END IF;
END;
$$
DELIMITER ;
SELECT * FROM orders_aud;

/*-----------------------------------------------------------------------------------------------------------*/
/*Audit Table ----Cart----*/
DELIMITER $$
CREATE TRIGGER cart_insert
BEFORE INSERT ON cart
FOR EACH ROW
BEGIN
   IF NEW.id < 0 THEN
      SET NEW.id = 0;
   END IF;
END;
$$
DELIMITER ;
CREATE TABLE cart_aud(
`id` INT NOT NULL,
`action` VARCHAR(10) NOT NULL,
`aud_timestamp` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
DELIMITER $$
CREATE TRIGGER cart_audit_insert
AFTER INSERT ON cart
FOR EACH ROW
BEGIN
INSERT INTO cart(id, `action`, aud_timestamp)
VALUES ( NEW.id, 'INSERT', NOW());
END;
$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER cart_audit_delete
AFTER UPDATE ON cart
FOR EACH ROW
BEGIN
IF NEW.status = 0 THEN  -- If is_active is set to 0, consider it as a DELETE operation
INSERT INTO cart_aud(id, `action`, aud_timestamp)
VALUES ( OLD.id, 'DELETE', NOW());
ELSE
INSERT INTO cart_aud(id, `action`, aud_timestamp)
VALUES (NEW.id, 'UPDATE', NOW());
END IF;
END;
$$
DELIMITER ;
SELECT * FROM cart_aud;
/*----------------------------------------------------------------------------------------------------*/
/*Audit Table ----Orders----*/
DELIMITER $$
CREATE TRIGGER category_insert
BEFORE INSERT ON category
FOR EACH ROW
BEGIN
   IF NEW.id < 0 THEN
      SET NEW.id = 0;
   END IF;
END;
$$
DELIMITER ;
CREATE TABLE category_aud(
`id` INT NOT NULL,
`action` VARCHAR(10) NOT NULL,
`aud_timestamp` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
DELIMITER $$
CREATE TRIGGER category_audit_insert
AFTER INSERT ON category
FOR EACH ROW
BEGIN
INSERT INTO category(id, `action`, aud_timestamp)
VALUES ( NEW.id, 'INSERT', NOW());
END;
$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER category_audit_delete
AFTER UPDATE ON category
FOR EACH ROW
BEGIN
IF NEW.is_available = 0 THEN  -- If is_active is set to 0, consider it as a DELETE operation
INSERT INTO category_aud(id, `action`, aud_timestamp)
VALUES ( OLD.id, 'DELETE', NOW());
ELSE
INSERT INTO category_aud(id, `action`, aud_timestamp)
VALUES (NEW.id, 'UPDATE', NOW());
END IF;
END;
$$
DELIMITER ;
SELECT * FROM category_aud;




  
  
  
  
  
  
 



