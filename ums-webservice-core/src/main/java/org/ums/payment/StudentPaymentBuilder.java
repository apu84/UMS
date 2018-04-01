package org.ums.payment;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.Semester;
import org.ums.fee.FeeCategory;
import org.ums.fee.payment.MutableStudentPayment;
import org.ums.fee.payment.StudentPayment;
import org.ums.formatter.DateFormat;

@Component
class StudentPaymentBuilder implements Builder<StudentPayment, MutableStudentPayment> {
  @Autowired
  @Qualifier("genericDateFormat")
  DateFormat mDateFormat;

  @Override
  public void build(JsonObjectBuilder pBuilder, StudentPayment pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId().toString());
    pBuilder.add("amount", pReadOnly.getAmount());
    FeeCategory feeCategory =
        (FeeCategory) pLocalCache.cache(pReadOnly::getFeeCategory, pReadOnly.getFeeCategoryId(), FeeCategory.class);
    pBuilder.add("feeCategory", feeCategory.getDescription());
    pBuilder.add("transactionId", pReadOnly.getTransactionId());
    pBuilder.add("appliedOn", mDateFormat.format(pReadOnly.getAppliedOn()));
    pBuilder.add("status", pReadOnly.getStatus().toString());
    pBuilder.add("feeType", feeCategory.getFeeTypeId());
    pBuilder.add("feeTypeName", feeCategory.getType().getName());
    pBuilder.add("feeTypeDescription", feeCategory.getType().getDescription());
    pBuilder.add("lastModified", pReadOnly.getLastModified());
    Semester semester = (Semester) pLocalCache.cache(pReadOnly::getSemester, pReadOnly.getSemesterId(), Semester.class);
    pBuilder.add("semesterName", semester.getName());
  }

  @Override
  public void build(MutableStudentPayment pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    throw new UnsupportedOperationException();
  }
}
