create table post(
    id serial primary key,
    name varchar(2000),
    description varchar(10000),
    link varchar(2000) unique,
    created timestamp
);