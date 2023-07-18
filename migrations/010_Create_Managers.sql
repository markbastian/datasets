CREATE TABLE IF NOT EXISTS Managers
(
    playerID VARCHAR(10) REFERENCES Master (playerID),
    yearID   INT,
    teamID   VARCHAR(3) REFERENCES Teams (teamID),
    lgID     VARCHAR(2) REFERENCES Leagues (lgID),
    inseason INT,
    G        INT,
    W        INT,
    L        INT,
    rank     INT,
    plyrMgr  VARCHAR(1)
);
