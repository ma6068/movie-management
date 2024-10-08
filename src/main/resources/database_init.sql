CREATE TABLE actors (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    gender VARCHAR(255),
    born_date DATE
);

CREATE TABLE movies (
    imdb_id VARCHAR(50) NOT NULL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    year_created INT,
    genre VARCHAR(50),
    description TEXT
);

CREATE TABLE movie_pictures (
    movie_id VARCHAR(50),
    picture_url VARCHAR(255),
    FOREIGN KEY (movie_id) REFERENCES movies(imdb_id) ON DELETE CASCADE
);

CREATE TABLE actor_movie (
    actor_id BIGINT,
    movie_id VARCHAR(50),
    PRIMARY KEY (actor_id, movie_id),
    FOREIGN KEY (actor_id) REFERENCES actors(id) ON DELETE CASCADE,
    FOREIGN KEY (movie_id) REFERENCES movies(imdb_id) ON DELETE CASCADE
);
