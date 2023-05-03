CREATE TABLE IF NOT EXISTS user_t (
    id INT NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    active BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS user_role_t (
    user_id INT NOT NULL PRIMARY KEY,
    roles VARCHAR(20) NOT NULL,
    foreign key (user_id) references user_t(id)
);

CREATE TABLE IF NOT EXISTS job
(
    id INT NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    function VARCHAR(58) NOT NULL
);

CREATE TABLE IF NOT EXISTS employee
(
    id INT NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    middle_name VARCHAR(50),
    job_id INT NOT NULL,
    hire_date DATE NOT NULL,
    salary DECIMAL(10,2) NOT NULL,
    working_email VARCHAR(150) NOT NULL,
    user_id int8 not null,
    foreign key (job_id) references job(id),
    foreign key (user_id) references user_t(id)
);

CREATE TABLE IF NOT EXISTS customer
(
    id INT NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(50),
    address VARCHAR(50),
    city VARCHAR(50),
    state CHAR(2),
    zip_code CHAR(5),
    area_code VARCHAR(3),
    phone_number VARCHAR(10),
    comments VARCHAR(255),
    user_id int8 not null,
    cart boolean default false,
    foreign key (user_id) references user_t(id)
);

CREATE TABLE IF NOT EXISTS product
(
    id INT NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name_p VARCHAR(255),
    description VARCHAR(255),
    image_url VARCHAR(255),
    price DECIMAL(10,2) NOT NULL
);

CREATE TABLE IF NOT EXISTS sales_order
(
    id INT NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    order_date DATE,
    customer_id INT NOT NULL,
    ship_date DATE,
    total DECIMAL(10,2),
    status VARCHAR(125) NOT NULL,
    cart boolean,
    foreign key (customer_id) references customer(id)
);

CREATE TABLE IF NOT EXISTS item
(
    id INT NOT NULL GENERATED ALWAYS AS IDENTITY,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    foreign key (product_id) references product(id),
    foreign key (order_id) references sales_order(id),
    primary key (order_id, id)
);
