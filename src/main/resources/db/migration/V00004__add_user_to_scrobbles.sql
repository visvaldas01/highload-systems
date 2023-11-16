DROP TABLE scrobbles;

CREATE TABLE scrobbles
(
    id      serial PRIMARY KEY,
    song_id INTEGER REFERENCES songs ON UPDATE CASCADE NOT NULL,
    user_id INTEGER REFERENCES users ON UPDATE CASCADE NOT NULL,
    date    timestamp DEFAULT now()                    NOT NULL
);