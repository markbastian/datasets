CREATE TABLE IF NOT EXISTS Master
(
    playerID     VARCHAR(10) PRIMARY KEY,
    birthYear    INT,
    birthMonth   INT,
    birthDay     INT,
    birthCountry VARCHAR(50),
    birthState   VARCHAR(50),
    birthCity    VARCHAR(50),
    deathYear    INT,
    deathMonth   INT,
    deathDay     INT,
    deathCountry VARCHAR(50),
    deathState   VARCHAR(50),
    deathCity    VARCHAR(50),
    nameFirst    VARCHAR(50),
    nameLast     VARCHAR(50),
    nameGiven    VARCHAR(100),
    weight       INT,
    height       INT,
    bats         VARCHAR(1),
    throws       VARCHAR(1),
    debut        DATE,
    finalGame    DATE,
    retroID      VARCHAR(10),
    bbrefID      VARCHAR(10)
);
