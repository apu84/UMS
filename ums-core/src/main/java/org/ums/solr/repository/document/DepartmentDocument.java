package org.ums.solr.repository.document;

import java.util.List;

import com.google.common.collect.Lists;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.ums.domain.model.immutable.Department;

public class DepartmentDocument {
  @Id
  @Field
  private String id;
  @Field("shortName_txt")
  private List<String> shortName;
  @Field("longName_txt")
  private List<String> longName;

  public DepartmentDocument() {}

  public DepartmentDocument(final Department pDepartment) {
    id = pDepartment.getId();
    shortName = Lists.newArrayList(pDepartment.getShortName());
    longName = Lists.newArrayList(pDepartment.getLongName());
  }
}
