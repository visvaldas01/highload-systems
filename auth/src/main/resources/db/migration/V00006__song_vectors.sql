ALTER TABLE songs
    ADD COLUMN vector1 FLOAT,
    ADD COLUMN vector2 FLOAT,
    ADD COLUMN vector3 FLOAT,
    ADD CONSTRAINT every CHECK (vector1 IS NULL and vector2 IS NULL and vector3 IS NULL or
                                vector1 IS NOT NULL and vector2 IS NOT NULL and vector3 IS NOT NULL);