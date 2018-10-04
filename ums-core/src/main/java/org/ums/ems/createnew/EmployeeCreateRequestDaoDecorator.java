package org.ums.ems.createnew;

import org.ums.decorator.ContentDaoDecorator;

import java.util.List;

public class EmployeeCreateRequestDaoDecorator extends
    ContentDaoDecorator<EmployeeCreateRequest, MutableEmployeeCreateRequest, String, EmployeeCreateRequestManager>
    implements EmployeeCreateRequestManager {
  @Override
  public List<EmployeeCreateRequest> getAll(Integer pActionStatus) {
    return getManager().getAll(pActionStatus);
  }
}
