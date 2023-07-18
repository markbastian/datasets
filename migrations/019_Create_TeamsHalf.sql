CREATE TABLE TeamsHalf
(
    yearID INT REFERENCES Teams (yearID),
    lgID   VARCHAR(2) REFERENCES Leagues (lgID),
    teamID VARCHAR(3) REFERENCES Teams (teamID),
    Half   INT,
    divID  VARCHAR(10) REFERENCES Divisions (divID),
    DivWin VARCHAR(1),
    Rank   INT,
    G      INT,
    W      INT,
    L      INT
);
