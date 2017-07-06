package org.ums.fee.certificate;

import org.ums.filter.ListFilter;
import org.ums.manager.ContentManager;

import java.util.List;

public interface CertificateStatusManager extends ContentManager<CertificateStatus, MutableCertificateStatus, Long> {
  List<CertificateStatus> paginatedList(int itemsPerPage, int pageNumber);

  List<CertificateStatus> paginatedFilteredList(int itemsPerPage, int pageNumber, List<ListFilter> pFilters);

  List<CertificateStatus> getByStudent(String pStudentId);

  enum FilterCriteria {
    STUDENT_ID,
    STATUS
  }
}
