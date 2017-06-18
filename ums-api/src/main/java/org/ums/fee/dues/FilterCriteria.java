package org.ums.fee.dues;

public interface FilterCriteria {
  Criteria getCriteria();

  Object getValue();

  enum Criteria {
    STUDENT_ID,
    STUDENT_NAME,
    DEPARTMENT,
    YEAR,
    ACADEMIC_SEMESTER,
    DUE_STATUS,
    DUE_TYPE
  }
}
