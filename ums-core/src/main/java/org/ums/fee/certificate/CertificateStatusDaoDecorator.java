package org.ums.fee.certificate;

import java.util.List;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.filter.ListFilter;

public class CertificateStatusDaoDecorator extends
    ContentDaoDecorator<CertificateStatus, MutableCertificateStatus, Long, CertificateStatusManager> implements
    CertificateStatusManager {
  @Override
  public List<CertificateStatus> paginatedList(int itemsPerPage, int pageNumber) {
    return getManager().paginatedList(itemsPerPage, pageNumber);
  }

  @Override
  public List<CertificateStatus> paginatedFilteredList(int itemsPerPage, int pageNumber, List<ListFilter> pFilters) {
    return getManager().paginatedFilteredList(itemsPerPage, pageNumber, pFilters);
  }

  @Override
  public List<CertificateStatus> getByStudent(String pStudentId) {
    return getManager().getByStudent(pStudentId);
  }
}
