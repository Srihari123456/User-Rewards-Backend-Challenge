
CREATE SEQUENCE IF NOT EXISTS REWARD_SEQUENCE
START WITH 1 -- The initial value for the sequence
INCREMENT BY 1 -- The increment value for each next value
MINVALUE 1 -- The minimum value for the sequence
MAXVALUE 9999999999-- No maximum value (you can specify a maximum if needed)
CYCLE -- Restart from the minimum value when the maximum is reached, or NO CYCLE to stop
CACHE 10; -- The number of values cached in memory (optional, but can improve performance)


DROP TABLE IF EXISTS REWARD_INFO;
CREATE TABLE REWARD_INFO (
    REWARD_ID INT DEFAULT NEXTVAL('REWARD_SEQUENCE') PRIMARY KEY,
    PAYER VARCHAR(255),
    POINTS INT,
    TIMESTAMP_DETAILS TIMESTAMP 
);
