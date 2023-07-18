CREATE TABLE IF NOT EXISTS AwardsManagers(
    playerID VARCHAR(10) REFERENCES Master (playerID),
    awardID   VARCHAR(30),
    yearID   INT,
    lgID     VARCHAR(2) REFERENCES Leagues (lgID),
    tie   VARCHAR(1),
    notes VARCHAR);
