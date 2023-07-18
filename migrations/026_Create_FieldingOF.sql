CREATE TABLE FieldingOF
(
    playerID VARCHAR(10) REFERENCES Master (playerID),
    yearID   INT,
    stint    INT,
    Glf      INT,
    Gcf      INT,
    Grf      INT
);
