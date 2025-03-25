CREATE TABLE IF NOT EXISTS Users (
                                    id BIGINT PRIMARY KEY,
                                    username VARCHAR(255) NOT NULL,
                                    password VARCHAR(255) NOT NULL
    );
INSERT INTO Users (id , username , password)
VALUES (1, 'Tanya', '$2y$12$X6WQ9yWpHLZcUtifpaDte.OYUHGULVxQ9L8evFiZDcdcWBCx.Uiby');