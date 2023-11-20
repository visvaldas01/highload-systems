ALTER TABLE users
    RENAME COLUMN login TO username;

CREATE TABLE roles
(
    id   SERIAL,
    name VARCHAR(50) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);
--
UPDATE users
SET password = '$2a$10$gh1NJRt7IXfmKb5fXP0pmOwSuTWwXcrFIENAdXi/2vCsFhZcerhVS'
WHERE username = 'Irina';
--
UPDATE users
SET password = '$2a$10$1DNFb8gj41fnPrQNezmNue.mgCMeHtxCkvcmFlNTRJUJ/iE3MT4Ti'
WHERE username = 'Vsevolod';

UPDATE users
SET password = '$2a$10$z80fTf6kZpI8lHp.zTMGve/K2yDg5zinCG3ERFx4aY37lnqKbgqgK'
WHERE username = 'Yaroslave';

CREATE TABLE users_roles
(
    user_id BIGINT NOT NULL,
    role_id INT    NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (role_id) REFERENCES roles (id)
);

INSERT INTO roles (name)
VALUES ('ROLE_USER'),
       ('ROLE_ADMIN');

INSERT INTO users_roles (user_id, role_id)
VALUES (1, 1),
       (3, 2);