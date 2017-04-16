package org.ums.solr.indexer.resolver;

import org.ums.domain.model.immutable.Employee;
import org.ums.manager.EmployeeManager;
import org.ums.solr.indexer.model.Index;
import org.ums.solr.repository.EmployeeRepository;
import org.ums.solr.repository.converter.SimpleConverter;
import org.ums.solr.repository.document.EmployeeDocument;

public class EmployeeResolver implements EntityResolver {
  private EmployeeManager mEmployeeManager;
  private EmployeeRepository mEmployeeRepository;

  public EmployeeResolver(EmployeeManager pEmployeeManager, EmployeeRepository pEmployeeRepository) {
    this.mEmployeeManager = pEmployeeManager;
    this.mEmployeeRepository = pEmployeeRepository;
  }

  @Override
  public String getEntityType() {
    return "employee";
  }

  @Override
  public void resolve(Index pIndex) {
    if(!pIndex.isDeleted()) {
      Employee employee = mEmployeeManager.get(pIndex.getEntityId());
      SimpleConverter<Employee, EmployeeDocument> converter =
          new SimpleConverter<>(Employee.class, EmployeeDocument.class);
      EmployeeDocument employeeDocument = converter.convert(employee);
      mEmployeeRepository.save(employeeDocument);
    }
    else {
      mEmployeeRepository.delete(pIndex.getEntityId());
    }
  }
}
