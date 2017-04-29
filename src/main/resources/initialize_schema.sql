create table if not exists offices (
    id integer primary key,
    name string,
    address string
);

create table if not exists services (
    id integer primary key,
    name string,
    price real
);

create table if not exists employees (
    id integer primary key,
    first_name string,
    last_name string,
    patr_name string,
    dob date,
    phone_number string,
    hired_at date,
    salary real
);

create table if not exists orders (
    id integer primary key,
    first_name string,
    last_name string,
    patr_name string,
    phone_number string,
    order_date datetime,
    executed boolean,
    office_id integer,
    service_id integer,
    employee_id integer,

    foreign key (office_id) references offices(id),
    foreign key (service_id) references services(id),
    foreign key (employee_id) references employees(id)
);

create table if not exists articles (
    id integer primary key,
    order_id integer,
    name string,
    color integer,
    components string,

    foreign key (order_id) references orders(id)
);