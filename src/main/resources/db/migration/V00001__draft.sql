CREATE TABLE users
(
    id       serial PRIMARY KEY,
    login    varchar(64) NOT NULL,
    password varchar(64) NOT NULL
);
CREATE TABLE tag_groups
(
    id   serial PRIMARY KEY,
    name varchar(64) NOT NULL
);
CREATE TABLE tags
(
    id           serial PRIMARY KEY,
    name         varchar(64)                                     NOT NULL,
    tag_group_id INTEGER REFERENCES tag_groups ON UPDATE CASCADE NOT NULL
);
CREATE TABLE musicians
(
    id   serial PRIMARY KEY,
    name varchar(64) NOT NULL
);
CREATE TABLE songs
(
    id   serial PRIMARY KEY,
    name varchar(64) NOT NULL
);
CREATE TABLE albums
(
    id   serial PRIMARY KEY,
    name varchar(64) NOT NULL
);
CREATE TABLE scrobbles
(                                                                     -- скорее всего, больше полей не будет
    id            serial PRIMARY KEY,
    song_id       INTEGER REFERENCES songs ON UPDATE CASCADE NOT NULL,
    scrobble_date timestamp DEFAULT now()                    NOT NULL -- UNIQUE nada?
);
