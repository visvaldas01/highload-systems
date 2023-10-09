CREATE TABLE users (
    serial id PRIMARY KEY,
    varchar(64) login NOT NULL,
    varchar(64) password NOT NULL
)
CREATE TABLE tags (
    serial id PRIMARY KEY,
    varchar(64) name NOT NULL
)
CREATE TABLE musicians (
    serial id PRIMARY KEY,
    varchar(64) name NOT NULL,
    boolean enabled NOT NULL
)
CREATE TABLE songs ( 
    serial id PRIMARY KEY,
    varchar(64) name NOT NULL
)
CREATE TABLE albums (
    serial id PRIMARY KEY,
    varchar(64) name NOT NULL
)
CREATE TABLE scrobbles ( -- скорее всего, больше полей не будет
    serial id PRIMARY KEY,
    song_id INTEGER REFERENCES songs ON UPDATE CASCADE NOT NULL,
    timestamp scrobble_date DEFAULT now() NOT NULL -- UNIQUE nada?
)
