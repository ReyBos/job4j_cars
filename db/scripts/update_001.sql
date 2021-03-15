CREATE TABLE users(
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    login VARCHAR(50) NOT NULL,
    password VARCHAR(20) NOT NULL,
    phone VARCHAR(20) NOT NULL
);

CREATE TABLE car_engine_type(
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE car_model(
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE car_body_type(
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE car_transmission_box_type(
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE city (
   id SERIAL PRIMARY KEY,
   name TEXT NOT NULL
);

CREATE TABLE car(
    id SERIAL PRIMARY KEY,
    created TIMESTAMP NOT NULL,
    price INT NOT NULL,
    is_sold BOOLEAN NOT NULL,
    is_new BOOLEAN NOT NULL,
    mileage INT NOT NULL,
    is_broken BOOLEAN NOT NULL,
    description TEXT NOT NULL,
    user_id INT NOT NULL REFERENCES users(id),
    city_id INT NOT NULL REFERENCES city(id),
    car_model_id INT NOT NULL REFERENCES car_model(id),
    car_body_type_id INT NOT NULL REFERENCES car_body_type(id),
    car_engine_type_id INT NOT NULL REFERENCES car_engine_type(id),
    car_transmission_box_type_id INT NOT NULL REFERENCES car_transmission_box_type(id)
);

CREATE TABLE car_photo(
    id SERIAL PRIMARY KEY,
    car_id INT NOT NULL REFERENCES car(id)
);