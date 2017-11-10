package org.ums.payment;

import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.fee.FeeType;
import org.ums.fee.FeeTypeManager;
import org.ums.fee.payment.MutableStudentPayment;
import org.ums.fee.payment.StudentPayment;
import org.ums.fee.payment.StudentPaymentManager;
import org.ums.resource.ResourceHelper;

@Component
class StudentPaymentResourceHelper extends ResourceHelper<StudentPayment, MutableStudentPayment, Long> {
  @Autowired
  @Qualifier("studentPaymentBuilder")
  StudentPaymentBuilder mStudentPaymentBuilder;
  @Autowired
  StudentPaymentManager mStudentPaymentManager;
  @Autowired
  FeeTypeManager mFeeTypeManager;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    throw new NotImplementedException("Method not implemented");
  }

  @Override
  protected StudentPaymentManager getContentManager() {
    return mStudentPaymentManager;
  }

  @Override
  protected Builder<StudentPayment, MutableStudentPayment> getBuilder() {
    return mStudentPaymentBuilder;
  }

  @Override
  protected String getETag(StudentPayment pReadonly) {
    return pReadonly.getLastModified();
  }

  JsonObject getSemesterFeeStatus(String pStudentId, Integer pSemesterId, UriInfo pUriInfo) {
    FeeType feeType = mFeeTypeManager.get(FeeType.Types.SEMESTER_FEE.getId());
    List<StudentPayment> payments = getContentManager().getPayments(pStudentId, pSemesterId, feeType);
    return buildJsonResponse(payments, pUriInfo);
  }

  JsonObject getCertificateFeeStatus(String pStudentId, UriInfo pUriInfo) {
    // FeeType feeType = mFeeTypeManager.get(FeeType.Types.CERTIFICATE_FEE.getId());
    List<StudentPayment> payments = mStudentPaymentManager.getPayments(pStudentId);
    return buildJsonResponse(payments, pUriInfo);
  }

  JsonObject getDuesStatus(String pStudentId, UriInfo pUriInfo) {
    List<StudentPayment> payments = mStudentPaymentManager.getPayments(pStudentId, FeeType.Types.DUES.getId());
    return buildJsonResponse(payments, pUriInfo);
  }

  JsonObject getPenaltyStatus(String pStudentId, UriInfo pUriInfo) {
    List<StudentPayment> payments = mStudentPaymentManager.getPayments(pStudentId, FeeType.Types.PENALTY.getId());
    return buildJsonResponse(payments, pUriInfo);
  }

  JsonObject getByStudent(String pStudentId, UriInfo pUriInfo) {
    List<StudentPayment> payments = mStudentPaymentManager.getPayments(pStudentId);
    return buildJsonResponse(payments, pUriInfo);
  }
}
