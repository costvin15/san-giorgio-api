drop table if exists clients cascade;
drop table if exists payments cascade;

create table clients (
    client_id varchar(100) not null,
    primary key (client_id)
);

create table payments (
    amount_paid numeric(38,2),
    payment_value numeric(38,2),
    client varchar(100),
    payment_id varchar(100) not null,
    payment_status enum ('EXCEDEED','PARTIAL','TOTAL'),
    primary key (payment_id)
);

alter table if exists payments
    add constraint FK5i9s7iujfeyx4ulik5w7htbbh
    foreign key (client)
    references clients;