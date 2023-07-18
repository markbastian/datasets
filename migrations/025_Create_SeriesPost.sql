CREATE TABLE SeriesPost
(
    yearID       INT,
    round        VARCHAR(10),
    teamIDwinner VARCHAR(3) REFERENCES Teams (teamID),
    lgIDwinner   VARCHAR(2) REFERENCES Leagues (lgID),
    teamIDloser  VARCHAR(3) REFERENCES Teams (teamID),
    lgIDloser    VARCHAR(2) REFERENCES Leagues (lgID),
    wins         INT,
    losses       INT,
    ties         INT
);
