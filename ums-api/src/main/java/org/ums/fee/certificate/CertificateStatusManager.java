package org.ums.fee.certificate;

import org.ums.domain.model.immutable.Department;
import org.ums.fee.FeeCategory;
import org.ums.fee.FeeType;
import org.ums.filter.ListFilter;
import org.ums.manager.ContentManager;

import java.util.List;

public interface CertificateStatusManager extends ContentManager<CertificateStatus, MutableCertificateStatus, Long> {
  List<CertificateStatus> paginatedList(int itemsPerPage, int pageNumber);

  List<CertificateStatus> paginatedFilteredList(int itemsPerPage, int pageNumber, List<ListFilter> pFilters,
      FeeType pFeeType, Department pDepartment);

  List<CertificateStatus> getByStudent(String pStudentId);

  List<String> getByStudent(String pStudentId, String pFeeCategoryId);

  List<CertificateStatus> getByStatusAndFeeTypePaginated(int itemsPerPage, int pageNumber,
      CertificateStatus.Status pStatus, FeeType pFeeType);

  List<CertificateStatus> getByStatusAndFeeType(CertificateStatus.Status pStatus, FeeType pFeeType);

  List<CertificateStatus> getByStatusAndFeeCategory(CertificateStatus.Status pStatus, FeeCategory pFeeCategory);

  enum FilterCriteria {
    STUDENT_ID,
    STATUS,
    FEE_ID
  }
}
