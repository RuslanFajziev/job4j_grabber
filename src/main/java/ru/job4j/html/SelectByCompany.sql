CREATE TABLE company(
    id integer NOT NULL,
    name character varying,
    CONSTRAINT company_pkey PRIMARY KEY (id)
);

CREATE TABLE person(
    id integer NOT NULL,
    name character varying,
    company_id integer references company(id),
    CONSTRAINT person_pkey PRIMARY KEY (id)
);

select * from company;
select * from person;

-- add companies --
insert into company(id, name) values(3, 'Yandex');
insert into company(id, name) values(4, 'Google');
insert into company(id, name) values(5, 'MailRu');
-- add persons --
insert into person(id, name, company_id) values(1, 'Petrov', 3);
insert into person(id, name, company_id) values(2, 'Sidorov', 3);
insert into person(id, name, company_id) values(3, 'Kotovsky', 4);
insert into person(id, name, company_id) values(4, 'Bulba', 4);
insert into person(id, name, company_id) values(5, 'Kuzmenko', 5);
insert into person(id, name, company_id) values(6, 'Rybin', 5);
insert into person(id, name, company_id) values(7, 'Nazarenko', 5);
    -- 1. В одном запросе получить --
-- имена всех person, которые не состоят в компании с id = 5 --
-- название компании для каждого человека --
select pr.id, pr.name, cm.name as "company" from person as pr
join company as cm on cm.id = pr.company_id
where pr.company_id <> 5;
    -- 2. Необходимо выбрать название компании с максимальным количеством человек + количество человек в этой компании --
select * from
(select cm.name as "company", count(*) as "count" from person as pr
join company as cm on cm.id = pr.company_id
GROUP BY cm.name
ORDER BY count(*) DESC) as TopCompany
limit 1