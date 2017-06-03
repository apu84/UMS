package org.ums.academic.resource.fee.dues;

import java.math.BigDecimal;
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
import org.ums.fee.FeeCategory;
import org.ums.fee.dues.MutableStudentDues;
import org.ums.fee.dues.StudentDues;
import org.ums.fee.payment.StudentPayment;
import org.ums.fee.payment.StudentPaymentManager;
import org.ums.formatter.DateFormat;

@Component
public class StudentDuesBuilder implements Builder<StudentDues, MutableStudentDues> {
  @Autowired
  DateFormat mDateFormat;
  @Autowired
  StudentPaymentManager mStudentPaymentManager;

  @Override
  public void build(JsonObjectBuilder pBuilder, StudentDues pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId());
    pBuilder.add("studentId", pReadOnly.getStudentId());
    FeeCategory category =
        (FeeCategory) pLocalCache.cache(pReadOnly::getFeeCategory, pReadOnly.getFeeCategoryId(), FeeCategory.class);
    pBuilder.add("feeCategoryId", category.getId());
    pBuilder.add("feeCategoryName", category.getName());
    pBuilder.add("payBefore", mDateFormat.format(pReadOnly.getPayBefore()));
    if(!StringUtils.isEmpty(pReadOnly.getDescription())) {
      pBuilder.add("description", pReadOnly.getDescription());
    }
    pBuilder.add("amount", pReadOnly.getAmount());
    if(!StringUtils.isEmpty(pReadOnly.getTransactionId())) {
      List<StudentPayment> payments =
          mStudentPaymentManager.getTransactionDetails(pReadOnly.getStudentId(), pReadOnly.getTransactionId());
      pBuilder.add("transactionId", pReadOnly.getTransactionId());
      pBuilder.add("transactionStatus", payments.get(0).getStatus().toString());
    }
  }

  @Override
  public void build(MutableStudentDues pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    Validate.notEmpty(pJsonObject.getString("studentId"));
    Validate.notEmpty(pJsonObject.getString("feeCategoryId"));
    Validate.notEmpty(pJsonObject.getString("payBefore"));
    pMutable.setStudentId(pJsonObject.getString("studentId"));
    pMutable.setFeeCategoryId(pJsonObject.getString("feeCategoryId"));
    pMutable.setPayBefore(mDateFormat.parse(pJsonObject.getString("payBefore")));
    if(pJsonObject.containsKey("description")) {
      pMutable.setDescription(pJsonObject.getString("description"));
    }
    if(pJsonObject.containsKey("amount")) {
      pMutable.setAmount(new BigDecimal(pJsonObject.getString("amount")));
    }
  }
}
