package org.ums.academic.resource.fee.dues;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.fee.UGFee;
import org.ums.fee.UGFeeManager;
import org.ums.fee.dues.MutableStudentDues;
import org.ums.fee.dues.PersistentStudentDues;
import org.ums.fee.dues.StudentDues;
import org.ums.fee.dues.StudentDuesManager;
import org.ums.fee.payment.MutableStudentPayment;
import org.ums.fee.payment.PersistentStudentPayment;
import org.ums.fee.payment.StudentPayment;
import org.ums.fee.payment.StudentPaymentManager;
import org.ums.manager.ContentManager;
import org.ums.manager.StudentManager;
import org.ums.resource.ResourceHelper;

@Component
public class StudentDuesHelper extends ResourceHelper<StudentDues, MutableStudentDues, Long> {
  @Autowired
  private StudentDuesBuilder mStudentDuesBuilder;
  @Autowired
  private StudentDuesManager mStudentDuesManager;
  @Autowired
  private UGFeeManager mUGFeeManager;
  @Autowired
  private StudentManager mStudentManager;
  @Autowired
  private StudentPaymentManager mStudentPaymentManager;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    Validate.notNull(pJsonObject);
    JsonArray entries = pJsonObject.getJsonArray("entries");
    LocalCache cache = new LocalCache();
    List<MutableStudentDues> duesList = new ArrayList<>();

    for(int i = 0; i < entries.size(); i++) {
      MutableStudentDues dues = new PersistentStudentDues();
      getBuilder().build(dues, entries.getJsonObject(i), cache);
      // If no amount is passed on
      if(dues.getAmount().compareTo(BigDecimal.ZERO) == 0) {
        List<UGFee> fees = mUGFeeManager
            .getLatestFee(dues.getStudent().getProgram().getFaculty().getId(),
                dues.getStudent().getCurrentEnrolledSemesterId())
            .stream().filter((fee) -> fee.getFeeCategoryId().equalsIgnoreCase(dues.getFeeCategoryId()))
            .collect(Collectors.toList());
        dues.setAmount(fees.get(0).getAmount());
      }
      duesList.add(dues);
    }

    if(duesList.size() > 0) {
      getContentManager().create(duesList);
    }
    return Response.ok().build();
  }

  JsonObject getDues(String pStudentId, UriInfo pUriInfo) {
    List<StudentDues> dues = mStudentDuesManager.getByStudent(pStudentId);
    return buildJsonResponse(dues, pUriInfo);
  }

  @Transactional
  Response payDues(String pStudentId, JsonObject pJsonObject, UriInfo pUriInfo) {
    Validate.notEmpty(pJsonObject.getJsonArray("entries"));
    JsonArray entries = pJsonObject.getJsonArray("entries");
    List<MutableStudentPayment> payments = new ArrayList<>();
    List<MutableStudentDues> mutableStudentDues = new ArrayList<>();
    for(int i = 0; i < entries.size(); i++) {
      Long id = Long.parseLong(entries.getString(i));
      StudentDues dues = mStudentDuesManager.get(id);
      validateDues(pStudentId, dues);
      mutableStudentDues.add(dues.edit());
      payments.add(createPayment(dues, pStudentId));
    }

    List<Long> paymentIds = mStudentPaymentManager.create(payments);
    // Update dues with created transactionId
    StudentPayment payment = mStudentPaymentManager.get(paymentIds.get(0));
    mutableStudentDues.forEach((due) -> {
      due.setTransactionId(payment.getTransactionId());
    });
    mStudentDuesManager.update(mutableStudentDues);
    return Response.ok().build();
  }

  Response updateDues(String pStudentId, JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    Validate.notEmpty(pJsonObject.getJsonArray("entries"));
    List<MutableStudentDues> updatedDues = readEntities(pJsonObject, PersistentStudentDues.class);
    List<StudentDues> latestDues = mStudentDuesManager.getByStudent(pStudentId);
    if(!isValidUpdateOfEntities(latestDues, updatedDues)) {
      return Response.status(Response.Status.PRECONDITION_FAILED).build();
    }
    mStudentDuesManager.update(updatedDues);
    return Response.ok().build();
  }

  private void validateDues(String studentId, StudentDues dues) {
    Assert.isTrue(dues.getStudentId().equalsIgnoreCase(studentId), "Invalid dues");
    Assert.isTrue(dues.getPayBefore().after(new Date()), "Expired date");
    if(!StringUtils.isEmpty(dues.getTransactionId())) {
      List<StudentPayment> payments = mStudentPaymentManager.getTransactionDetails(studentId, dues.getTransactionId());
      Assert.isTrue(payments.size() > 0, "Not valid transaction reference");
      StudentPayment payment = payments.get(0);
      Assert.isTrue(payment.getStatus() == StudentPayment.Status.EXPIRED
          || payment.getStatus() == StudentPayment.Status.REJECTED, "Already applied/ paid");
    }
  }

  private MutableStudentPayment createPayment(StudentDues pStudentDues, String pStudentId) {
    MutableStudentPayment payment = new PersistentStudentPayment();
    payment.setFeeCategoryId(pStudentDues.getFeeCategoryId());
    payment.setStudentId(pStudentId);
    // setting semester id to student's current semester, as there is no semester selection for dues
    payment.setSemesterId(mStudentManager.get(pStudentId).getSemesterId());
    payment.setAmount(pStudentDues.getAmount());
    payment.setTransactionValidTill(pStudentDues.getPayBefore());
    return payment;
  }

  @Override
  protected ContentManager<StudentDues, MutableStudentDues, Long> getContentManager() {
    return mStudentDuesManager;
  }

  @Override
  protected Builder<StudentDues, MutableStudentDues> getBuilder() {
    return mStudentDuesBuilder;
  }

  @Override
  protected String getETag(StudentDues pReadonly) {
    return pReadonly.getLastModified();
  }
}
