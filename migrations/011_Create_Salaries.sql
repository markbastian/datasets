CREATE TABLE IF NOT EXISTS Salaries(
    yearID   INT,
    teamID   VARCHAR(3) REFERENCES Teams (teamID),
    lgID     VARCHAR(2) REFERENCES Leagues (lgID),
    playerID VARCHAR(10) REFERENCES Master (playerID),
    salary INT
);
