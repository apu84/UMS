CREATE TABLE routine_config
(
    id NUMBER PRIMARY KEY,
    semester_id NUMBER,
    program_type NUMBER,
    day_from NUMBER,
    day_to NUMBER,
    start_time TIMESTAMP,
    end_time TIMESTAMP,
    duration NUMBER,
    last_modified VARCHAR2(18)
);