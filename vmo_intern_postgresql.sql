-- Database: vmo_intern

-- DROP DATABASE IF EXISTS vmo_intern;

create table freshers (
	id serial primary key,
	name varchar(100) not null,
	dob date not null,
	gender varchar(5) check (gender in ('Nam', 'Nữ', 'Other')) not null,
    phone varchar(20) not null,
    email varchar(125) not null,
	position varchar(100) not null,
    language varchar(100) not null,
    mark1 int,
    mark2 int,
    mark3 int,
    mark_avg double precision,
    center_id int
);

-- create table languages (
-- 	id serial primary key,
--     name varchar(100) not null,
--     position varchar(100) not null
-- );

-- create table fresher_languages (
--     fresher_id int not null,
--     language_id int not null,
--     foreign key(fresher_id) references freshers(id),
--     foreign key(language_id) references languages(id)
-- );

create table centers (
	id serial primary key,
    name varchar(255) not null,
    address varchar(255) not null,
    phone varchar(20) not null,
    description text
);

create table users (
	id serial primary key,
    username varchar(100) not null,
    password varchar(68) not null,
    authority varchar(100) not null
);

insert into 
users 
(username, password, authority)
values
('admin', '$2a$12$6ZSwyWD1UqhDLSkhrmXsKe3VzyhTY77x5uTwFVQ40xBCqUbozoRO6', 'ROLE_ADMIN')