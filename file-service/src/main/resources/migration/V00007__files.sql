CREATE TABLE files
(
    id   SERIAL,
    owner VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(50) NOT NULL,
    data OID NOT NULL,
    PRIMARY KEY (id)
);