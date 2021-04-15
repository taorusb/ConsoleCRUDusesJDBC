create table writers
(
    id         bigint not null auto_increment,
    first_name varchar(30),
    last_name  varchar(30),
    region_id  bigint not null,
    role       varchar(30),

    primary key (id),
    foreign key (region_id)
        references regions (id)
);