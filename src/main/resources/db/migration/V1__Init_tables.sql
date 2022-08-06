CREATE TABLE if NOT EXISTS users
(
    user_id     INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    user_name   VARCHAR NOT NULL,
    middle_name VARCHAR,
    last_name   VARCHAR NOT NULL,
    email       VARCHAR UNIQUE,
    password    VARCHAR NOT NULL
);


CREATE TABLE if NOT EXISTS boxes
(
    box_id           INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    box_name         VARCHAR NOT NULL,
    start_time       TIME,
    end_time         TIME,
    time_factor      FLOAT   DEFAULT (1.0),
    twenty_four_hour BOOLEAN DEFAULT (false)
);


CREATE TABLE if NOT EXISTS service_type
(
    service_type_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    service_name    VARCHAR NOT NULL UNIQUE,
    duration        TIME    NOT NULL,
    price           BIGINT  NOT NULL
);


CREATE TABLE if NOT EXISTS order_status
(
    status_id   INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    status_name VARCHAR UNIQUE NOT NULL
);


CREATE TABLE if NOT EXISTS orders
(
    order_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    date     DATE NOT NULL,
    time     TIME NOT NULL
);


CREATE TABLE if NOT EXISTS confirmations
(
    confirmation_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    expire_at       TIMESTAMP NOT NULL,
    token           VARCHAR,
    confirmed       BOOLEAN DEFAULT (false)
);


CREATE TABLE if NOT EXISTS roles
(
    role_id   INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    role_name VARCHAR UNIQUE NOT NULL
);


CREATE TABLE if NOT EXISTS discounts
(
    discount_id  INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    min_discount INTEGER DEFAULT (0),
    max_discount INTEGER DEFAULT (0)
)