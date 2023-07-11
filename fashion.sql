use fashion;
/* Create table  ------Admin_User */
create table admin_user
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
is_available varchar(20) DEFAULT 'Available',
foreign key(category)references category(category_name)
);
alter table category 
rename COLUMN Category_name To category_name;

/* Create table  ------Category */
create table category(
id int(10)not null auto_increment primary key,
category_name varchar(20)not null unique key,
is_available varchar(20) DEFAULT 'Available'
);

insert into category values(id,'Saree','Available');
insert into category values(id,'Kurti','Available');
insert into category values(id,'Kurta Sets','Available');
insert into category values(id,'Material','Available');
insert into category values(id,'Other Ethnic','Available');
insert into category values(id,'Men Top Wear','Available');
insert into category values(id,'Men Bottom wear','Available');
insert into category values(id,'Men Ethnic Wear','Available');
insert into category values(id,'kidz',"Available");
insert into category values(id,'Women Bottom wear',"Available");
insert into category (category_name,is_available)values('Western','Available');
update category set is_active=1 where id=9;
select * from category;
/* Create table  ------wish-list */
create table wish_list(id int(10)not null auto_increment primary key,
customer_id int(20)not null,
product_id int(20)not null,
product_name varchar(30)not null,
price int(20)not null,
size varchar(20)not null,
category varchar(20)not null,
is_available varchar(20) DEFAULT "Available",
foreign key(customer_id)references admin_user(id),
foreign key(product_id)references product(id),
foreign key(product_name)references product(name)
 );
 
/* Create table  ------orders */
create table orders(id int(10)not null auto_increment primary key,
customer_id int(20)not null,
product_id int(20)not null,
product_name varchar(30)not null,
price int(20)not null,
size varchar(20)not null,
category varchar(10)not null,
quantity int(20)not null,
is_available varchar(20) DEFAULT "Available",
foreign key(customer_id)references admin_user(id),
foreign key(product_id)references product(id),
foreign key(product_name)references product(name)
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
    product_name varchar(30)not null,
    price int(20)not null,
    size varchar(20)not null,
    product_type varchar(20)not null,
    quantity int(10)not null,
    total_amount int(10)not null,
   is_available varchar(20) DEFAULT "Available",
    foreign key(customer_id)references admin_user(id),
	foreign key(order_id)references orders(id),
    foreign key(product_id)references product(id),
    foreign key(product_name)references product(name)
   );
   
select id ,username ,email ,
phone_number ,gender ,`is_active`
from admin_user;

select id,name,price,category,size,
quantity,fabric,gender,image,`is_available`
from product;

select id,order_id,customer_id ,
product_id ,price,size,product_type,
quantity,total_amount,is_available from cart;
    
select id,customer_id,product_id,price,
size,category,is_available  from wish_list;
  
select id,customer_id,product_id,
price,size,category,quantity,
is_available from orders;

select id,order_id,
  amount,payment_type,
  Date from payment;
  
select id,category_name,
is_available from category;
      
  drop table register;
  drop table cart;
  drop table category;
  drop table product;
  drop table wish_list;
  drop table orders;
  drop table payment;
  
  /*-------------------------Audit---------------------------------------------------- */
  use fashion;
/*Audit Table ----Register----*/
DELIMITER $$
CREATE TRIGGER admin_user_insert
BEFORE INSERT ON admin_user
FOR EACH ROW
BEGIN
   IF NEW.id < 0 THEN
      SET NEW.id = 0;
   END IF;
END;
$$
DELIMITER ;

CREATE TABLE admin_user_aud(
`id` INT NOT NULL,
`action` VARCHAR(10) NOT NULL,
`aud_timestamp` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
DELIMITER $$
CREATE TRIGGER admin_user_audit_insert
AFTER INSERT ON admin_user
FOR EACH ROW
BEGIN
INSERT INTO admin_user_aud(id, `action`, aud_timestamp)
VALUES ( NEW.id, 'INSERT', NOW());
END;
$$
DELIMITER ;
DELIMITER $$

CREATE TRIGGER admin_user_audit_delete
AFTER UPDATE ON admin_user
FOR EACH ROW
BEGIN
IF NEW.is_active = 0 THEN  -- If is_active is set to 0, consider it as a DELETE operation
INSERT INTO admin_user(id, `action`, aud_timestamp)
VALUES ( OLD.id, 'DELETE', NOW());
ELSE
INSERT INTO admin_user_aud( id, `action`, aud_timestamp)
VALUES (NEW.id,'UPDATE', NOW());
END IF;
END;
$$
DELIMITER ;

drop table register_aud;
drop table product_aud;
SELECT * FROM admin_user_aud;
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
IF NEW.is_available = 'Not Available' THEN  -- If is_active is set to 0, consider it as a DELETE operation
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
IF NEW.is_available = 'Not Available' THEN  -- If is_active is set to 0, consider it as a DELETE operation
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
IF NEW.is_available = 'Not Available' THEN  -- If is_active is set to 0, consider it as a DELETE operation
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
IF NEW.is_available = 'Not Available' THEN  -- If is_active is set to 0, consider it as a DELETE operation
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
/*Audit Table ----Category----*/
DELIMITER $$
CREATE TRIGGER category_insert
BEFORE INSERT ON cart
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
IF NEW.is_available = 'Not Available' THEN  -- If is_active is set to 0, consider it as a DELETE operation
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