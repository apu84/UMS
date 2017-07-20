package org.ums.fee.dues;

import java.util.List;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.filter.ListFilter;

public class StudentDuesDaoDecorator extends
    ContentDaoDecorator<StudentDues, MutableStudentDues, Long, StudentDuesManager> implements StudentDuesManager {
  @Override
  public List<StudentDues> getByStudent(String pStudentId) {
    return getManager().getByStudent(pStudentId);
  }

  @Override
  public List<StudentDues> paginatedList(int itemsPerPage, int pageNumber, List<ListFilter> pFilters) {
    return getManager().paginatedList(itemsPerPage, pageNumber, pFilters);
  }
}
