INSERT INTO tag_groups (name)
VALUES ('Time'),
       ('Country'),
       ('Language'),
       ('Genre'),
       ('Mood');

INSERT INTO tags (name, tag_group_id)
VALUES ('New Wave', 4),
       ('Grunge', 4),
       ('Blues', 4),
       ('Glam Metal', 4),
       ('Gothic Rock', 4),
       ('Post-Punk', 4),
       ('Synthpop', 4),
       ('Glam Rock', 4),
       ('Punk Rock', 4),
       ('Alternative Rock', 4),
       ('Post-Rock', 4),
       ('Ambient', 4),
       ('Noise', 4),
       ('Noise Rock', 4),
       ('Classical', 4),
       ('Pop', 4),
       ('R&B', 4),
       ('Trap', 4),
       ('Trip Hop', 4),
       ('Dubstep', 4),
       ('Jazz', 4),
       ('20s', 1),
       ('30s', 1),
       ('40s', 1),
       ('50s', 1),
       ('60s', 1),
       ('70s', 1),
       ('80s', 1),
       ('90s', 1),
       ('Finland', 2),
       ('Spain', 2),
       ('Lithuania', 2);

INSERT INTO songs (name)
VALUES ('Dust My Broom'),
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