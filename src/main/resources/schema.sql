CREATE TABLE ITEM
(
    code INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    unitPrice INT NOT NULL,
    count INT NOT NULL,
    IsPr INT,
    RecordDate DATETIME,
    PRIMARY KEY(code)
);