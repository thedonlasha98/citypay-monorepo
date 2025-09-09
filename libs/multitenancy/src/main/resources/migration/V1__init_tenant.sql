create table if not exists tenant (
    id varchar(100) primary key,
    jdbc_url text not null,
    username text not null,
    password text not null,
    active boolean not null default true
);
