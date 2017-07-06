package org.ums.fee.dues;

import java.util.List;

import org.ums.filter.ListFilter;
import org.ums.manager.ContentManager;

public interface StudentDuesManager extends ContentManager<StudentDues, MutableStudentDues, Long> {
  List<StudentDues> getByStudent(String pStudentId);

  List<StudentDues> paginatedList(int itemsPerPage, int pageNumber, List<ListFilter> pFilters);

  enum FilterCriteria {
    STUDENT_ID,
    DUE_STATUS,
    DUE_TYPE
  }
}
