create table posts
(
    id        bigint       not null auto_increment,
    content   varchar(255) not null,
    created   varchar(20),
    updated   varchar(20),
    writer_id bigint       not null,

    primary key (id),
    foreign key (writer_id)
        references writers (id)
);