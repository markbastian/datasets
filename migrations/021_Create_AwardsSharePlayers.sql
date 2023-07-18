CREATE TABLE AwardsSharePlayers
(
    awardID    VARCHAR(100),
    yearID     INT,
    lgID       VARCHAR(2) REFERENCES Leagues (lgID),
    playerID   VARCHAR(10) REFERENCES Master (playerID),
    pointsWon  INT,
    pointsMax  INT,
    votesFirst INT
);
