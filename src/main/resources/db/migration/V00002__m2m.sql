CREATE TABLE songs_musicians
(
    songs_id     INTEGER REFERENCES songs ON DELETE CASCADE     NOT NULL,
    musicians_id INTEGER REFERENCES musicians ON DELETE CASCADE NOT NULL,
    PRIMARY KEY (songs_id, musicians_id)

);
CREATE TABLE albums_musicians
(
    albums_id    INTEGER REFERENCES albums ON DELETE CASCADE    NOT NULL,
    musicians_id INTEGER REFERENCES musicians ON DELETE CASCADE NOT NULL,
    PRIMARY KEY (albums_id, musicians_id)
);
CREATE TABLE songs_tags
(
    songs_id INTEGER REFERENCES songs ON DELETE CASCADE NOT NULL,
    tags_id  INTEGER REFERENCES tags ON DELETE CASCADE  NOT NULL,
    PRIMARY KEY (songs_id, tags_id)
);
CREATE TABLE albums_tags
(
    albums_id INTEGER REFERENCES albums ON DELETE CASCADE NOT NULL,
    tags_id   INTEGER REFERENCES tags ON DELETE CASCADE   NOT NULL,
    PRIMARY KEY (albums_id, tags_id)
);
CREATE TABLE musicians_tags
(
    musicians_id INTEGER REFERENCES musicians ON DELETE CASCADE NOT NULL,
    tags_id      INTEGER REFERENCES tags ON DELETE CASCADE      NOT NULL,
    PRIMARY KEY (musicians_id, tags_id)
);
CREATE TABLE songs_users
(
    songs_id INTEGER REFERENCES songs ON DELETE CASCADE NOT NULL,
    users_id INTEGER REFERENCES users ON DELETE CASCADE NOT NULL,
    PRIMARY KEY (songs_id, users_id)
);
CREATE TABLE albums_users
(
    albums_id INTEGER REFERENCES albums ON DELETE CASCADE NOT NULL,
    users_id  INTEGER REFERENCES users ON DELETE CASCADE  NOT NULL,
    PRIMARY KEY (albums_id, users_id)
);
CREATE TABLE musicians_users
(
    musicians_id INTEGER REFERENCES musicians ON DELETE CASCADE NOT NULL,
    users_id     INTEGER REFERENCES users ON DELETE CASCADE     NOT NULL,
    PRIMARY KEY (musicians_id, users_id)
);
CREATE TABLE albums_songs
(
    albums_id INTEGER REFERENCES albums ON DELETE CASCADE NOT NULL,
    songs_id INTEGER REFERENCES songs ON DELETE CASCADE NOT NULL
);