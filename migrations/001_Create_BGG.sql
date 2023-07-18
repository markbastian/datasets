CREATE TABLE IF NOT EXISTS BGG
(
    ID                INT PRIMARY KEY,
    Name              VARCHAR(255),
    YearPublished     INT,
    MinPlayers        INT,
    MaxPlayers        INT,
    PlayTime          INT,
    MinAge            INT,
    UsersRated        INT,
    RatingAverage     DECIMAL(4, 2),
    BGGRank           INT,
    ComplexityAverage DECIMAL(4, 2),
    OwnedUsers        INT,
    Mechanics         VARCHAR(1000),
    Domains           VARCHAR(100)
);