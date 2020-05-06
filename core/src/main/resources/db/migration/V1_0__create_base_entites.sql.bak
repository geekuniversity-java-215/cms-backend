DROP TABLE IF EXISTS customer cascade ;
CREATE TABLE customer (
     id bigserial,
     first_name varchar (30),
     last_name varchar (30),
     email varchar (50),
     phone_number VARCHAR (15),
     PRIMARY KEY (id)
);

DROP TABLE IF EXISTS courier cascade ;
CREATE TABLE courier (
      id bigserial,
      first_name varchar (30),
      last_name varchar (30),
      email varchar (50),
      phone_number VARCHAR (15),
      PRIMARY KEY (id)
);

DROP TABLE IF EXISTS delivery_order cascade ;
CREATE TABLE delivery_order (
      id bigserial,
      courier_id bigInt,
      customer_id bigInt,
      PRIMARY KEY (id),
      foreign key (courier_id) references courier (id),
      foreign key (customer_id) references customer (id)
);

