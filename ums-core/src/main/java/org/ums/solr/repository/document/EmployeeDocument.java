package org.ums.solr.repository.document;

import com.google.common.collect.Lists;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.ums.domain.model.immutable.Employee;

import java.util.List;

public class EmployeeDocument implements SearchDocument<String> {
  public static final String DOCUMENT_TYPE = "Employee";
  @Id
  @Field
  private String id;
  // Need to define 'text' fields as list, as solr by default uses multivalued field for text type
  // field
  @Field("name_txt")
  private List<String> name;

  @Field("type_s")
  private String type = DOCUMENT_TYPE;

  // @Field("dynamicMappedField_*")
  // private Map<String, String> dynamicMappedFieldValues;

  @Field(child = true)
  private DepartmentDocument departmentDocument;

  public EmployeeDocument() {}

  public EmployeeDocument(final Employee pEmployee) {
    id = pEmployee.getId();
    name = Lists.newArrayList(pEmployee.getPersonalInformation().getFullName());
    departmentDocument = new DepartmentDocument(pEmployee.getDepartment());
    // dynamicMappedFieldValues = new HashMap<>();
    // dynamicMappedFieldValues.put("key1", "value1");
    // dynamicMappedFieldValues.put("key2", "value2");
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
