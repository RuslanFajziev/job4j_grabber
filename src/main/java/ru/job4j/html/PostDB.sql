CREATE DATABASE jobs WITH OWNER = postgres ENCODING = 'UTF8' CONNECTION LIMIT = -1;

create table post(
    id serial primary key,
    name varchar(255),
    link varchar(255) UNIQUE,
    text text,
    created timestamp
);

--ALTER TABLE post ADD UNIQUE (link);
--
--select column_name, column_default, data_type, colum_type from information_schema.columns
--where table_catalog = 'jobs' and
--table_name = 'post';