package org.ums.ems.createnew;

import org.ums.decorator.ContentDaoDecorator;

public class EmployeeCreateRequestDaoDecorator extends
    ContentDaoDecorator<EmployeeCreateRequest, MutableEmployeeCreateRequest, String, EmployeeCreateRequestManager>
    implements EmployeeCreateRequestManager {
}
