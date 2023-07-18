CREATE TABLE IF NOT EXISTS Titanic
(
    PassengerId INT NOT NULL PRIMARY KEY,
    Survived    INT,
    Pclass      INT,
    Name        VARCHAR(255),
    Sex         VARCHAR(10),
    Age         INT,
    SibSp       INT,
    Parch       INT,
    Ticket      VARCHAR(20),
    Fare        DECIMAL(8, 2),
    Cabin       VARCHAR(10),
    Embarked    VARCHAR(1)
);