CREATE TABLE question
(
    id int PRIMARY KEY AUTO_INCREMENT,
    title varchar(50),
    description text,
    gmt_create bigint,
    gmt_modified bigint,
    creator int,
    comment_count int NOT NULL DEFAULT '0',
    view_count int NOT NULL DEFAULT '0',
    like_count int NOT NULL DEFAULT '0',
    tag varchar(255)
);