package org.ums.solr.repository.document;

import com.google.common.collect.Lists;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;
import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.immutable.Employee;
import org.ums.domain.model.immutable.User;

import java.util.List;

@SolrDocument(solrCoreName = "ums")
public class EmployeeDocument implements SearchDocument<String> {
  @Id
  @Field
  private String id;
  // Need to define 'text' fields as list, as solr by default uses multivalued field for text type
  // field
  @Field("name_txt")
  private List<String> name;

  @Field("departmentShortName_txt")
  private List<String> departmentShortName;

  @Field("departmentLongName_txt")
  private List<String> departmentLongName;

  @Field("departmentId_s")
  private String departmentId;

  @Field("type_s")
  private String type = "Employee";

  public EmployeeDocument() {}

  public EmployeeDocument(final Employee pEmployee) {
    id = pEmployee.getId();
    name = Lists.newArrayList(pEmployee.getEmployeeName());
    Department department = pEmployee.getDepartment();
    departmentShortName = Lists.newArrayList(department.getShortName());
    departmentLongName = Lists.newArrayList(department.getLongName());
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
