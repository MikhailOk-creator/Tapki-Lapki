CREATE TABLE IF NOT EXISTS user_t (
    id int8 not null generated always as identity primary key,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    real_name VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    active BOOLEAN NOT NULL
);