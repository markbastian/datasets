CREATE TABLE Fielding
(
    playerID VARCHAR(10) REFERENCES Master (playerID),
    yearID   INT REFERENCES Teams (yearID),
    stint    INT,
    teamID   VARCHAR(3) REFERENCES Teams (teamID),
    lgID     VARCHAR(2) REFERENCES Leagues (lgID),
    POS      VARCHAR(10),
    G        INT,
    GS       INT,
    InnOuts  INT,
    PO       INT,
    A        INT,
    E        INT,
    DP       INT,
    PB       INT,
    WP       INT,
    SB       INT,
    CS       INT,
    ZR       INT
);
