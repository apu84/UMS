package org.ums.ems.createnew;

import org.ums.manager.ContentManager;

import java.util.List;

public interface EmployeeCreateRequestManager extends
    ContentManager<EmployeeCreateRequest, MutableEmployeeCreateRequest, String> {

  List<EmployeeCreateRequest> getAll(Integer pActionStatus);
}
