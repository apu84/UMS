package org.ums.solr.repository.document;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;
import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.immutable.Employee;
import org.ums.domain.model.immutable.User;

@SolrDocument(solrCoreName = "ums")
public class EmployeeDocument implements SearchDocument<String> {
  @Id
  @Indexed
  private String id;

  @Field("name_txt")
  private String name;

  @Field("departmentShortName_txt")
  private String departmentShortName;

  @Field("departmentLongName_txt")
  private String departmentLongName;

  @Field("departmentId_s")
  private String departmentId;

  @Field("type_s")
  private String type = "Employee";

  public EmployeeDocument() {}

  public EmployeeDocument(final Employee pEmployee) {
    id = pEmployee.getId();
    name = pEmployee.getEmployeeName();
    Department department = pEmployee.getDepartment();
    departmentShortName = department.getShortName();
    departmentLongName = department.getLongName();
    departmentId = department.getId();
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public String getType() {
    return type;
  }
}
