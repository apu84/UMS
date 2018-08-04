create table routine_config(
  id number,
  program_id number,
  semester_id number,
  day_from number,
  day_to number,
  start_time timestamp,
  end_time timestamp,
  duration number,
  last_modified varchar2(18)
);