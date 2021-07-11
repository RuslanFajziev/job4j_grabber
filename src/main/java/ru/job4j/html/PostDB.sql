CREATE DATABASE post_jobs WITH OWNER = postgres ENCODING = 'UTF8' CONNECTION LIMIT = -1;

create table post(
    id int,
    title varchar(255),
    link varchar(255),
    description varchar(255),
    created timestamp
);