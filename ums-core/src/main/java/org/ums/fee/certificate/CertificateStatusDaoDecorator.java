package org.ums.fee.certificate;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.fee.FeeType;
import org.ums.filter.ListFilter;

import java.util.List;

public class CertificateStatusDaoDecorator extends
    ContentDaoDecorator<CertificateStatus, MutableCertificateStatus, Long, CertificateStatusManager> implements
    CertificateStatusManager {
  @Override
  public List<CertificateStatus> paginatedList(int itemsPerPage, int pageNumber) {
    return getManager().paginatedList(itemsPerPage, pageNumber);
  }

  @Override
  public List<CertificateStatus> paginatedFilteredList(int itemsPerPage, int pageNumber, List<ListFilter> pFilters,
      FeeType pFeeType) {
    return getManager().paginatedFilteredList(itemsPerPage, pageNumber, pFilters, pFeeType);
  }

  @Override
  public List<CertificateStatus> getByStudent(String pStudentId) {
    return getManager().getByStudent(pStudentId);
  }

  @Override
  public List<String> getByStudent(String pStudentId, String pFeeCategoryId) {
    return getManager().getByStudent(pStudentId, pFeeCategoryId);
  }

}
