package org.ums.academic.resource.fee.certificate;

import java.util.List;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.Student;
import org.ums.domain.model.immutable.User;
import org.ums.fee.FeeCategory;
import org.ums.fee.certificate.CertificateStatus;
import org.ums.fee.certificate.MutableCertificateStatus;
import org.ums.fee.payment.StudentPayment;
import org.ums.fee.payment.StudentPaymentManager;
import org.ums.formatter.DateFormat;

@Component
public class CertificateStatusBuilder implements Builder<CertificateStatus, MutableCertificateStatus> {
  @Autowired
  DateFormat mDateFormat;
  @Autowired
  StudentPaymentManager mStudentPaymentManager;

  @Override
  public void build(JsonObjectBuilder pBuilder, CertificateStatus pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId().toString());
    Student student = (Student) pLocalCache.cache(pReadOnly::getStudent, pReadOnly.getStudentId(), Student.class);
    Semester semester = (Semester) pLocalCache.cache(pReadOnly::getSemester, pReadOnly.getSemesterId(), Semester.class);
    FeeCategory feeCategory =
        (FeeCategory) pLocalCache.cache(pReadOnly::getFeeCategory, pReadOnly.getFeeCategoryId(), FeeCategory.class);
    if(!StringUtils.isEmpty(pReadOnly.getTransactionId())) {
      List<StudentPayment> payments =
          mStudentPaymentManager.getTransactionDetails(pReadOnly.getStudentId(), pReadOnly.getTransactionId());
      pBuilder.add("transactionId", pReadOnly.getTransactionId());
      pBuilder.add("transactionStatus", payments.get(0).getStatus().toString());
    }
    pBuilder.add("studentId", student.getId());
    pBuilder.add("studentName", student.getFullName());
    pBuilder.add("semesterName", semester.getName());
    pBuilder.add("certificateType", feeCategory.getDescription());
    if(!StringUtils.isEmpty(pReadOnly.getUserId())) {
      User user = (User) pLocalCache.cache(pReadOnly::getUser, pReadOnly.getUserId(), User.class);
      pBuilder.add("processedBy", user.getName());
      pBuilder.add("processedOn", mDateFormat.format(pReadOnly.getProcessedOn()));
    }
    pBuilder.add("status", pReadOnly.getStatus().getLabel());
    pBuilder.add("lastModified", pReadOnly.getLastModified());
  }

  @Override
  public void build(MutableCertificateStatus pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    Validate.notEmpty(pJsonObject.getString("id"));
    pMutable.setId(Long.parseLong(pJsonObject.getString("id")));
    pMutable.setStatus(CertificateStatus.Status.get(pJsonObject.getInt("status")));
    pMutable.setLastModified(pJsonObject.getString("lastModified"));
  }
}
