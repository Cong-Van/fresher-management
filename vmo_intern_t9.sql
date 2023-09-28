drop database if exists fresher_management;

create database fresher_management;

use fresher_management;

create table freshers (
	id int auto_increment not null primary key,
	name varchar(100) not null,
	dob date not null,
	gender enum("Nam", "Nữ", "Other") not null,
    phone varchar(20) not null,
    email varchar(125) not null,
    position varchar(100) not null,
    language varchar(100) not null,
    joined_date date not null,
    graduated_date date,
    mark1 double,
    mark2 double,
    mark3 double,
    mark_avg double,
    center_id int
);

-- create table languages (
-- 	id int auto_increment not null primary key,
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
	id int auto_increment not null primary key,
    name varchar(255) not null,
    address varchar(255) not null,
    phone varchar(20) not null,
    created_date datetime not null,
    created_by varchar(100) not null,
    updated_date datetime,
    updated_by varchar(100) not null,
    description text
);

create table authorities (
	id int auto_increment not null primary key,
    name varchar(50) not null
);

create table users (
	id int auto_increment not null primary key,
    username varchar(100) not null,
    password varchar(68) not null,
    authority_id int not null,
    is_not_locked tinyint not null,
    foreign key(authority_id) references authorities(id)
);

insert into
authorities (name)
values ("ADMIN"), ("MANAGER");

insert into 
users 
(username, password, authority_id, is_not_locked)
values
("admin", "$2a$12$6ZSwyWD1UqhDLSkhrmXsKe3VzyhTY77x5uTwFVQ40xBCqUbozoRO6", 1, 1)