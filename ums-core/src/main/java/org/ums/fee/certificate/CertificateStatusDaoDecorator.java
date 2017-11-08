package org.ums.fee.certificate;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.Department;
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
      FeeType pFeeType, Department pDepartment) {
    return getManager().paginatedFilteredList(itemsPerPage, pageNumber, pFilters, pFeeType, pDepartment);
  }

  @Override
  public List<CertificateStatus> getByStudent(String pStudentId) {
    return getManager().getByStudent(pStudentId);
  }

  @Override
  public List<String> getByStudent(String pStudentId, String pFeeCategoryId) {
    return getManager().getByStudent(pStudentId, pFeeCategoryId);
  }

  @Override
  public List<CertificateStatus> getByStatusAndFeeTypePaginated(int itemsPerPage, int pageNumber,
      CertificateStatus.Status pStatus, FeeType pFeeType) {
    return getManager().getByStatusAndFeeTypePaginated(itemsPerPage, pageNumber, pStatus, pFeeType);
  }

  @Override
  public List<CertificateStatus> getByStatusAndFeeType(CertificateStatus.Status pStatus, FeeType pFeeType) {
    return getManager().getByStatusAndFeeType(pStatus, pFeeType);
  }
}
