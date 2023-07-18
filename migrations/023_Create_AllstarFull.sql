CREATE TABLE AllstarFull
(
    playerID    VARCHAR(10) REFERENCES Master (playerID),
    yearID      INT,
    gameNum     INT,
    gameID      VARCHAR(20),
    teamID      VARCHAR(3) REFERENCES Teams (teamID),
    lgID        VARCHAR(2) REFERENCES Leagues (lgID),
    GP          INT,
    startingPos INT
);
