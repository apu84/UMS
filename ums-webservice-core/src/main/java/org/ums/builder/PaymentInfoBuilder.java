package org.ums.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.AdmissionStudent;
import org.ums.domain.model.immutable.PaymentInfo;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.Student;
import org.ums.domain.model.mutable.MutablePaymentInfo;
import org.ums.enums.PaymentMode;
import org.ums.enums.PaymentType;
import org.ums.manager.AdmissionStudentManager;
import org.ums.manager.SemesterManager;
import org.ums.manager.StudentManager;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Monjur-E-Morshed on 25-Jan-17.
 */
@Component
public class PaymentInfoBuilder implements Builder<PaymentInfo, MutablePaymentInfo> {

  @Autowired
  StudentManager mStudentManager;

  @Autowired
  AdmissionStudentManager mAdmissionStudentManager;

  @Autowired
  SemesterManager mSemesterManager;

  @Override
  public void build(JsonObjectBuilder pBuilder, PaymentInfo pReadOnly, UriInfo pUriInfo,
      LocalCache pLocalCache) {

  }

  public void build(JsonObjectBuilder pBuilder, PaymentInfo pReadOnly, UriInfo pUriInfo,
      LocalCache pLocalCache, PaymentType pPaymentType) {
    pBuilder.add("id", pReadOnly.getId());

    JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
    if(pPaymentType == PaymentType.ADMISSION_FEE || pPaymentType == PaymentType.MIGRATION_FEE) {
      AdmissionStudent admissionStudent = mAdmissionStudentManager.get(pReadOnly.getReferenceId());
      pBuilder.add("receiptId", admissionStudent.getReceiptId());
      pBuilder.add("studentName", admissionStudent.getStudentName());
      pBuilder.add("unit", admissionStudent.getUnit());
    }
    else {
      Student student = mStudentManager.get(pReadOnly.getReferenceId());
      pBuilder.add("studentId", student.getId());
      pBuilder.add("studentName", student.getFullName());
      // add related objects.
    }

    pBuilder.add("semesterId", pReadOnly.getSemesterId());
    pBuilder.add("semesterName", pReadOnly.getSemester().getName());
    pBuilder.add("paymentType", pReadOnly.getPaymentType().getId());
    pBuilder.add("paymentDate", pReadOnly.getPaymentDate());
    pBuilder.add("paymentMode", pReadOnly.getPaymentMode().getId());

  }

  @Override
  public void build(MutablePaymentInfo pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    pMutable.setReferenceId(pJsonObject.getString("referenceId"));
    pMutable.setSemesterId(pJsonObject.getInt("semesterId"));
    pMutable.setPaymentType(PaymentType.get(pJsonObject.getInt("paymentType")));
    pMutable.setAmount(pJsonObject.getInt("amount"));
    pMutable.setPaymentMode(PaymentMode.get(pJsonObject.getInt("paymentMode")));
  }
}
