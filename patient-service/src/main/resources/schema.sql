DROP DATABASE IF EXISTS patient_db;
CREATE DATABASE patient_db;

DROP TYPE IF EXISTS gender CASCADE;
CREATE TYPE gender AS ENUM ('M', 'F');

DROP TABLE IF EXISTS patient_info;

CREATE TABLE patient_info
(
    id            INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    first_name    VARCHAR(30) NOT NULL,
    last_name     VARCHAR(60) NOT NULL,
    date_of_birth DATE        NOT NULL,
    gender        VARCHAR(1)  NOT NULL,
    CONSTRAINT chk_gender CHECK (gender IN ('M', 'F')),
    address       VARCHAR(255),
    zip_code      VARCHAR(5),
    phone         VARCHAR(25),
    height        INTEGER,
    weight        INTEGER,
    smoker        BOOLEAN
);

INSERT INTO patient_info (last_name, first_name, date_of_birth, gender, address, phone) VALUES
                         ('Test', 'TestNone', '1966-12-31', 'F', '1 Brookside St', '100-222-333'),
                         ('Test', 'TestBorderline', '1945-06-25', 'M', '2 High St', '200-333-4444'),
                         ('Test', 'TestInDanger', '2004-06-18', 'M', '1 Brookside St', '300-444-5555'),
                         ('Test', 'TestEarlyOnset', '2002-06-28', 'F', '1 Brookside St', '400-555-6666');