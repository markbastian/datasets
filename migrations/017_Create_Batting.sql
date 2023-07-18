CREATE TABLE Batting
(
    playerID VARCHAR(10) REFERENCES Master (playerID),
    yearID   INT REFERENCES Teams (yearID),
    stint    INT,
    teamID   VARCHAR(3) REFERENCES Teams (teamID),
    lgID     VARCHAR(2) REFERENCES Leagues (lgID),
    G        INT,
    AB       INT,
    R        INT,
    H        INT,
    "2B"     INT,
    "3B"     INT,
    HR       INT,
    RBI      INT,
    SB       INT,
    CS       INT,
    BB       INT,
    SO       INT,
    IBB      INT,
    HBP      INT,
    SH       INT,
    SF       INT,
    GIDP     INT
);
