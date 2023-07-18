CREATE TABLE HallOfFame
(
    playerID    VARCHAR(10) REFERENCES Master (playerID),
    yearid      INT,
    votedBy     VARCHAR(50),
    ballots     INT,
    needed      INT,
    votes       INT,
    inducted    VARCHAR(1),
    category    VARCHAR(50),
    needed_note VARCHAR(100)
);
