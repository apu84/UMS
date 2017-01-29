package org.ums.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ums.builder.Builder;
import org.ums.builder.PaymentInfoBuilder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.*;
import org.ums.domain.model.mutable.MutableAdmissionStudent;
import org.ums.domain.model.mutable.MutablePaymentInfo;
import org.ums.domain.model.mutable.MutableStudent;
import org.ums.enums.*;
import org.ums.formatter.DateFormat;
import org.ums.manager.*;
import org.ums.persistent.model.*;
import org.ums.resource.ResourceHelper;

import javax.json.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 25-Jan-17.
 */
@Component
public class PaymentInfoResourceHelper extends
    ResourceHelper<PaymentInfo, MutablePaymentInfo, Integer> {

  @Autowired
  private PaymentInfoManager mManager;

  @Autowired
  private SemesterManager mSemesterManager;

  @Autowired
  private StudentManager mStudentManager;

  @Autowired
  private AdmissionStudentManager mAdmissionStudentManager;

  @Autowired
  private PaymentInfoBuilder mBuilder;

  @Transactional
  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    JsonArray entries = pJsonObject.getJsonArray("entries");
    LocalCache localCache = new LocalCache();
    JsonObject jsonObject = entries.getJsonObject(0);
    PersistentPaymentInfo paymentInfos = new PersistentPaymentInfo();
    getBuilder().build(paymentInfos, jsonObject, localCache);
    getContentManager().create(paymentInfos);
    String semesterIdStr = paymentInfos.getSemester().getId().toString();
    semesterIdStr = semesterIdStr.substring(0, 2);
    AdmissionStudent admissionStudent =
        mAdmissionStudentManager.getAdmissionStudent(paymentInfos.getSemester().getId(),
            org.ums.enums.ProgramType.get(Integer.parseInt(semesterIdStr)),
            paymentInfos.getReferenceId());
    String studentId = generateStudentId(admissionStudent, paymentInfos.getPaymentType());

    updateAdmissionStudent(admissionStudent, studentId, paymentInfos.getPaymentType());

    saveIntoStudent(admissionStudent, studentId, paymentInfos.getPaymentType());

    URI contextURI = null;
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  private void updateAdmissionStudent(final AdmissionStudent pAdmissionStudent,
      final String pStudentId, final PaymentType pPaymentType) {

    PersistentAdmissionStudent mAdmissionStudent = new PersistentAdmissionStudent();
    mAdmissionStudent.setId(pAdmissionStudent.getReceiptId());
    mAdmissionStudent.setSemesterId(pAdmissionStudent.getSemester().getId());
    if(pPaymentType == PaymentType.ADMISSION_FEE) {
      mAdmissionStudent.setAllocatedProgramId(pAdmissionStudent.getProgramByMerit().getId());
      mAdmissionStudent.setStudentId(pStudentId);
      mAdmissionStudentManager.updateStudentsAllocatedProgram(mAdmissionStudent,
          MigrationStatus.NOT_MIGRATED);
    }
    else {
      mAdmissionStudent.setAllocatedProgramId(pAdmissionStudent.getProgramByTransfer().getId());
      mAdmissionStudent.setStudentId(pStudentId);
      mAdmissionStudent.setMigratedFrom(pAdmissionStudent.getStudentId());
      mAdmissionStudentManager.updateStudentsAllocatedProgram(mAdmissionStudent,
          MigrationStatus.MIGRATED);
    }
  }

  private void saveIntoStudent(final AdmissionStudent pAdmissionStudent, String pStudentId,
      PaymentType pPaymentType) throws Exception {
    MutableStudent student = new PersistentStudent();

    student.setId(pStudentId);
    student.setSemesterId(pAdmissionStudent.getSemester().getId());
    student.setFullName(pAdmissionStudent.getStudentName());
    if(pPaymentType == PaymentType.ADMISSION_FEE) {
      student.setProgramId(pAdmissionStudent.getProgramByMerit().getId());
      student.setDepartmentId(pAdmissionStudent.getProgramByMerit().getDepartment().getId());
    }
    else {
      student.setProgramId(pAdmissionStudent.getProgramByTransfer().getId());
      student.setDepartmentId(pAdmissionStudent.getProgramByTransfer().getDepartment().getId());
      mStudentManager
          .updateStudentsStatus(StudentStatus.MIGRATED, pAdmissionStudent.getStudentId());
    }

    student.setCurrentYear(1);
    student.setCurrentAcademicSemester(1);
    student.setCurrentEnrolledSemesterId(pAdmissionStudent.getSemester().getId());
    student.setEnrollmentType(Student.EnrollmentType.ACTUAL);
    student.setFatherName(pAdmissionStudent.getFatherName());
    student.setMotherName(pAdmissionStudent.getMotherName());
    SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yy");
    String dateInString = pAdmissionStudent.getBirthDate();
    student.setDateOfBirth(formatter.parse(dateInString));
    student.setGender(pAdmissionStudent.getGender().substring(0, 1));
    student.setStatus(0); // todo convert to enum
    mStudentManager.create(student);
  }

  public JsonObject getAdmissionPaymentInfo(final String pReceiptId, final int pSemesterId,
      final UriInfo mUriInfo) {
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    try {
      List<PaymentInfo> paymentInfos = getContentManager().getPaymentInfo(pReceiptId, pSemesterId);
      for(PaymentInfo paymentInfo : paymentInfos) {
        JsonObjectBuilder jsonObject = Json.createObjectBuilder();
        getBuilder()
            .build(jsonObject, paymentInfo, mUriInfo, localCache, PaymentType.ADMISSION_FEE);
        children.add(jsonObject);
      }
    } catch(EmptyResultDataAccessException e) {
      // do nothing
    }

    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  String generateStudentId(final AdmissionStudent pAdmissionStudent, PaymentType pPaymentType) {
    String semesterId = pAdmissionStudent.getSemester().getId().toString();
    String studentId = semesterId.substring(semesterId.length() - 2, semesterId.length());
    studentId = studentId + semesterId.substring(2, 4);
    Program program = new PersistentProgram();
    String deptId = "";
    if(pPaymentType == PaymentType.ADMISSION_FEE) {
      program = pAdmissionStudent.getProgramByMerit();
    }
    else {
      program = pAdmissionStudent.getProgramByTransfer();
    }

    if(program.getDepartment().getId().equals("07")) {
      String programId = program.getId().toString();
      deptId = programId.substring(programId.length() - 2, programId.length());
    }
    else {
      deptId = program.getDepartment().getId();
    }
    studentId = studentId + deptId;
    int totalEnrolledStudent =
        mStudentManager.getSize(pAdmissionStudent.getSemester().getId(), program.getId());
    String studentNumberStr = String.valueOf(totalEnrolledStudent + 1);

    if(studentNumberStr.length() == 1) {
      studentNumberStr = "00" + studentNumberStr;
    }
    else if(studentNumberStr.length() == 2) {
      studentNumberStr = "0" + studentNumberStr;
    }
    else {
      // do nothing
    }

    studentId = studentId + studentNumberStr;

    return studentId;
  }

  @Override
  protected PaymentInfoManager getContentManager() {
    return mManager;
  }

  @Override
  protected PaymentInfoBuilder getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(PaymentInfo pReadonly) {
    return pReadonly.getLastModified();
  }
}
