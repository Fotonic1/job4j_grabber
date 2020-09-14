create table post(
    id serial,
    name varchar(2000),
    description varchar(10000),
    link varchar(2000),
    created timestamp,
    primary key (id, link)
);