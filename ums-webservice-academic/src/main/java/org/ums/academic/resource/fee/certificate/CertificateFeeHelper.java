package org.ums.academic.resource.fee.certificate;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.Student;
import org.ums.domain.model.immutable.StudentRecord;
import org.ums.fee.*;
import org.ums.fee.payment.MutableStudentPayment;
import org.ums.fee.payment.PersistentStudentPayment;
import org.ums.fee.payment.StudentPaymentManager;
import org.ums.manager.StudentManager;
import org.ums.manager.StudentRecordManager;
import org.ums.util.UmsUtils;

@Component
public class CertificateFeeHelper {
  @Autowired
  StudentRecordManager mStudentRecordManager;
  @Autowired
  FeeCategoryManager mFeeCategoryManager;
  @Autowired
  UGFeeManager mUGFeeManager;
  @Autowired
  StudentManager mStudentManager;
  @Autowired
  StudentPaymentManager mStudentPaymentManager;
  @Autowired
  FeeTypeManager mFeeTypeManager;

  JsonObject getAttendedSemesters(String pStudentId, UriInfo pUriInfo) {
    List<StudentRecord> studentRecords = mStudentRecordManager.getStudentRecord(pStudentId);
    studentRecords =
        studentRecords.stream().filter((studentRecord) -> studentRecord.getType() != StudentRecord.Type.DROPPED
            || studentRecord.getType() != StudentRecord.Type.WITHDRAWN).collect(Collectors.toList());
    JsonObjectBuilder builder = Json.createObjectBuilder();
    JsonArrayBuilder entries = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for(StudentRecord studentRecord : studentRecords) {
      JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
      Semester semester =
          (Semester) localCache.cache(studentRecord::getSemester, studentRecord.getSemesterId(), Semester.class);
      jsonObjectBuilder.add("semesterId", semester.getId());
      jsonObjectBuilder.add("semesterName", semester.getName());
      jsonObjectBuilder.add("year", studentRecord.getYear());
      jsonObjectBuilder.add("academicSemester", studentRecord.getAcademicSemester());
      entries.add(jsonObjectBuilder);
    }
    builder.add("entries", entries);
    return builder.build();
  }

  void applyForCertificate(String pFeeCategoryId, String pStudentId, Integer pForSemesterId, UriInfo pUriInfo) {
    FeeCategory category = mFeeCategoryManager.get(pFeeCategoryId);
    Student student = mStudentManager.get(pStudentId);
    List<UGFee> fees = mUGFeeManager
        .getLatestFee(student.getProgram().getFacultyId(), student.getCurrentEnrolledSemesterId()).stream()
        .filter((fee) -> fee.getFeeCategory().getId().equalsIgnoreCase(category.getId())).collect(Collectors.toList());
    if(fees.size() > 0) {
      UGFee fee = fees.get(0);
      MutableStudentPayment payment = new PersistentStudentPayment();
      payment.setFeeCategoryId(fee.getFeeCategoryId());
      payment.setStudentId(pStudentId);
      payment.setSemesterId(pForSemesterId);
      payment.setAmount(fee.getAmount());
      Date today = new Date();
      payment.setTransactionValidTill(UmsUtils.addDay(today, 10));
      payment.create();
    }
  }
}
