package org.ums.solr.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.ums.solr.repository.document.EmployeeDocument;

public interface EmployeeRepository extends CustomSolrCrudRepository<EmployeeDocument, String> {
  List<EmployeeDocument> findByCustomQuery(String searchTerm, Pageable pageable);

  List<EmployeeDocument> findByDepartment(String searchTerm, Pageable pageable);
}
