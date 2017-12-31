CREATE TABLE DB_IUMS.TWO_FA_TOKEN
(
  id             NUMBER,
  state          VARCHAR2(25),
  token          VARCHAR2(50),
  user_id        VARCHAR2(20),
  last_modified  VARCHAR2(18),
  token_expiry   DATE
)
