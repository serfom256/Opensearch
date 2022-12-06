drop table if exists data;
create table data
(
    id   int primary key not null auto_increment,
    data blob,
    idx  varchar(1000)
);
create index id_idx  on data(id) using HASH;

drop table if exists search_config;
create table search_config
(
    id       serial primary key,
    name     varchar(100),
    property varchar(100)
);
