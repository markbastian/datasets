CREATE TABLE ManagersHalf
(
    playerID VARCHAR(10) REFERENCES Master (playerID),
    yearID   INT REFERENCES Teams (yearID),
    teamID   VARCHAR(3) REFERENCES Teams (teamID),
    lgID     VARCHAR(2) REFERENCES Leagues (lgID),
    inseason INT,
    half     INT,
    G        INT,
    W        INT,
    L        INT,
    rank     INT
);
