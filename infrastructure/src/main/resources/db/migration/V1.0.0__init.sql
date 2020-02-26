create table author
(
    author_id   int auto_increment comment '著者ID'
        primary key,
    author_name varchar(255) not null comment '著者名',
    description longtext     not null comment '著者紹介'
)
    comment '著者';

create table book
(
    book_id     int auto_increment comment '書籍ID'
        primary key,
    author_id   int       not null comment '著者ID',
    title       varchar(255) not null comment 'タイトル',
    description longtext     not null comment '概要',
    constraint book_author_author_id_fk
        foreign key (author_id) references author (author_id)
)
    comment '書籍';

