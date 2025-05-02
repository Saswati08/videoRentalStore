CREATE TABLE video (
    id SERIAL PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    genre VARCHAR(50),
    available BOOLEAN DEFAULT TRUE
);
