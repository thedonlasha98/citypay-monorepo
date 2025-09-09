create table if not exists balance (
    id bigserial primary key,
    address varchar(100),
    asset varchar(20) not null,
    balance numeric not null default 0
);