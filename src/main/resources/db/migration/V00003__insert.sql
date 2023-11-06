INSERT INTO tags (name)
VALUES ('New Wave'),
       ('Grunge'),
       ('Blues'),
       ('Glam Metal'),
       ('Gothic Rock'),
       ('Post-Punk'),
       ('Synthpop'),
       ('Glam Rock'),
       ('Punk Rock'),
       ('Alternative Rock'),
       ('Post-Rock'),
       ('Ambient'),
       ('Noise'),
       ('Noise Rock'),
       ('Classical'),
       ('Pop'),
       ('R&B'),
       ('Trap'),
       ('Trip Hop'),
       ('Dubstep'),
       ('Jazz'),
       ('20s'),
       ('30s'),
       ('40s'),
       ('50s'),
       ('60s'),
       ('70s'),
       ('80s'),
       ('90s');

INSERT INTO songs (name)
VALUES  ('Dust My Broom'),
        ('Material Girl'),
        ('Papa Don''t Preach'),
        ('Panorama'),
        ('Pyjamarama'),
        ('Afghanistan'),
        ('Всё идёт по плану'),
        ('Винтовка - это праздник'),
        ('Русское поле экспериментов'),
        ('Лесник'),
        ('Nightclubbing'),
        ('Love Will Tear Us Apart Again'),
        ('Bitches Brew'),
        ('Angel');

INSERT INTO albums (name)
VALUES ('The Complete Recordings'),
       ('True Blue'),
       ('Roxy Music'),
       ('Panorama'),
       ('Warlord'),
       ('Всё идёт по плану'),
       ('Поезд ушёл'),
       ('Русское поле экспериментов'),
       ('Король и Шут'),
       ('The Idiot'),
       ('Bitches Brew'),
       ('Mezzanine');

INSERT INTO musicians (name)
VALUES ('Robert Johnson'),
       ('Madonna'),
       ('Roxy Music'),
       ('The Cars'),
       ('Yung Lean'),
       ('Гражданская оборона'),
       ('Король и Шут'),
       ('Iggy Pop'),
       ('Miles Davis'),
       ('Massive Attack');

INSERT INTO users (login, password)
VALUES ('Irina', 'aaa'),
       ('Vsevolod', 'bbb'),
       ('Yaroslave', 'ccc');

INSERT INTO albums_musicians(albums_id, musicians_id)
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (4, 4),
       (5, 5),
       (6, 5),
       (7, 5),
       (8, 6),
       (9, 7),
       (10, 8),
       (11, 9),
       (12, 10);

INSERT INTO albums_tags(albums_id, tags_id)
VALUES (1, 3),
       (2, 7),
       (2, 16),
       (1, 23),
       (2, 28);

INSERT INTO songs_users(songs_id, users_id)
VALUES (1, 1);