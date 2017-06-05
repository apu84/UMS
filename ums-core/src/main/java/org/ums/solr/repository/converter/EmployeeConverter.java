package org.ums.solr.repository.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.ums.domain.model.immutable.Employee;
import org.ums.solr.repository.document.EmployeeDocument;

// Sample converter, Only when @SimpleConverter is not sufficient
@Component
public class EmployeeConverter implements Converter<Employee, EmployeeDocument> {
  @Override
  public EmployeeDocument convert(Employee pSource) {
    return new EmployeeDocument(pSource);
  }

  @Override
  public List<EmployeeDocument> convert(List<Employee> pSourceList) {
    return pSourceList.stream().map(user -> convert(user)).collect(Collectors.toList());
  }
}
