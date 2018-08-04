package org.ums.academic.resource.fee.certificate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ums.cache.LocalCache;
import org.ums.configuration.UMSConfiguration;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.Student;
import org.ums.domain.model.immutable.StudentRecord;
import org.ums.fee.*;
import org.ums.fee.certificate.*;
import org.ums.fee.payment.MutableStudentPayment;
import org.ums.fee.payment.PersistentStudentPayment;
import org.ums.fee.payment.StudentPayment;
import org.ums.fee.payment.StudentPaymentManager;
import org.ums.manager.CompanyManager;
import org.ums.manager.StudentManager;
import org.ums.manager.StudentRecordManager;
import org.ums.manager.accounts.AccountManager;
import org.ums.manager.applications.AppRulesManager;
import org.ums.persistent.model.accounts.PersistentAccountTransaction;
import org.ums.twofa.HttpClient;
import org.ums.util.UmsUtils;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
  @Autowired
  AppRulesManager mAppRulesManager;
  @Autowired
  CertificateStatusManager mCertificateStatusManager;
  @Autowired
  CertificateStatusLogManager mCertificateStatusLogManager;
  @Autowired
  HttpClient mHttpClient;
  @Autowired
  AccountManager mAccountManager;
  @Autowired
  CompanyManager mCompanyManager;
  @Autowired
  UMSConfiguration mUMSConfiguration;
  @Autowired
  CertificateFeeService mCertificateFeeService;

  JsonObject getAttendedSemesters(String pStudentId, UriInfo pUriInfo) {
    List<StudentRecord> studentRecords = mStudentRecordManager.getStudentRecord(pStudentId);
    studentRecords = studentRecords.stream()
        .filter((studentRecord) -> (studentRecord.getType() != StudentRecord.Type.DROPPED
            || studentRecord.getType() != StudentRecord.Type.WITHDRAWN)
            && (studentRecord.getStatus() == StudentRecord.Status.PASSED
            || studentRecord.getStatus() == StudentRecord.Status.FAILED))
        .collect(Collectors.toList());
    JsonObjectBuilder builder = Json.createObjectBuilder();
    JsonArrayBuilder entries = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for (StudentRecord studentRecord : studentRecords) {
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

  Response applyForCertificate(HttpServletRequest pHttpServletRequest, String pFeeCategoryId, String pStudentId, Integer pForSemesterId) throws Exception {
    FeeCategory category = mFeeCategoryManager.get(pFeeCategoryId);
    Student student = mStudentManager.get(pStudentId);
    boolean resolvedCertificateDependencies = resolvedAllDependencies(category, student);
    if (resolvedCertificateDependencies) {
      List<UGFee> fees = mUGFeeManager
          .getLatestFee(student.getProgram().getFacultyId(), student.getCurrentEnrolledSemesterId()).stream()
          .filter((fee) -> fee.getFeeCategory().getId().equalsIgnoreCase(category.getId())).collect(Collectors.toList());
      if (fees.size() > 0) {
        UGFee fee = fees.get(0);
        MutableStudentPayment payment = new PersistentStudentPayment();
        payment.setFeeCategoryId(fee.getFeeCategoryId());
        payment.setStudentId(pStudentId);
        payment.setSemesterId(pForSemesterId);
        payment.setAmount(fee.getAmount());
        if (fee.getAmount().equals(BigDecimal.ZERO))
          payment.setStatus(StudentPayment.Status.RECEIVED);
        else
          payment.setStatus(StudentPayment.Status.APPLIED);
        Date today = new Date();

        payment.setTransactionValidTill(UmsUtils.addDay(today, 10));
        Long paymentId = payment.create();
        if (fee.getAmount().equals(BigDecimal.ZERO) || (category.getType().getId() == FeeType.Types.REG_CERTIFICATE_FEE.getId() && payment.getStatus().getValue() == StudentPayment.Status.RECEIVED.getValue())) {
          StudentPayment studentPayment = mStudentPaymentManager.get(paymentId);
          insertIntoCertificateStatus(pStudentId, fee, studentPayment, today);
          return Response
              .status(Response.Status.OK)
              .build();
        } else {
          return createJournalEntry(payment, pStudentId, pHttpServletRequest);
        }

      } else {
        return Response
            .status(Response.Status.FORBIDDEN)
            .build();
      }
    } else {
      return Response
          .status(Response.Status.UNAUTHORIZED)
          .build();
    }

  }

  /*
   * String url="https://localhost/ums-webservice-account/account/definition/system-group-map/all";
   * String response= mHttpClient .getClient() .target(url) .request(MediaType.APPLICATION_JSON)
   * .header("Authorization", pHttpServletRequest.getHeader("authorization")) .get(String.class);
   * 
   * ObjectMapper mapper = new ObjectMapper(); List<PersistentSystemGroupMap> vouchers =
   * Arrays.asList(mapper.readValue(response, PersistentSystemGroupMap[].class));
   * 
   * 
   * int xxx=0;
   */

  @Transactional
  public Response createJournalEntry(StudentPayment pStudentPayment, String pStudentId,
      HttpServletRequest pHttpServletRequest) {
    List<PersistentAccountTransaction> journalEntries =
        mCertificateFeeService.createStudentPaymentJournalEntry(pStudentPayment, pStudentId);
    String url =
        mUMSConfiguration.getHost() + "/ums-webservice-account/account/general-ledger/transaction/journal-voucher/post";
    Response response =
        mHttpClient.getClient().target(url).request()
            .header("Authorization", pHttpServletRequest.getHeader("authorization")).post(Entity.json(journalEntries));
    return response;

  }

  private void postIntoJournalVoucher(StudentPayment pStudentPayment) throws Exception {
    List<PersistentAccountTransaction> accountTransactionList = new ArrayList<>();
    PersistentAccountTransaction accountTransaction = new PersistentAccountTransaction();

  }

  private void insertIntoCertificateStatus(String pStudentId, UGFee pFee, StudentPayment pPayment, Date pToday) {
    MutableCertificateStatus certificateStatus = new PersistentCertificateStatus();
    certificateStatus.setFeeCategoryId(pFee.getFeeCategoryId());
    certificateStatus.setTransactionId(pPayment.getTransactionId());
    certificateStatus.setStudentId(pStudentId);
    certificateStatus.setSemesterId(pPayment.getSemesterId());
    certificateStatus.setProcessedOn(pToday);
    certificateStatus.setUserId(pStudentId);
    certificateStatus.setStatus(CertificateStatus.Status.WAITING_FOR_HEAD_FORWARDING);
    Long certificateStatusId = mCertificateStatusManager.create(certificateStatus);

    MutableCertificateStatusLog log = new PersistentCertificateStatusLog();
    log.setCertificateStatusId(certificateStatusId);
    log.setStatus(certificateStatus.getStatus());
    log.setProcessedOn(pToday);
    log.setProcessedBy(certificateStatus.getUserId());
    mCertificateStatusLogManager.create(log);
  }

  // todo modify it based on log.
  private boolean resolvedAllDependencies(FeeCategory pCategory, Student pStudent) {
    /*
     * List<AppRules> appRulesList = mAppRulesManager.getDependencies(pCategory.getId());
     * List<String> resolvedFeeCategoryIds =
     * mCertificateStatusManager.getByStudent(pStudent.getId(), pCategory.getId());
     */
    // appRulesList.size() == resolvedFeeCategoryIds.size() ? true : false;
    return true;
  }

  void applyForCertificate(String pFeeCategoryId, String pStudentId) {
    FeeCategory category = mFeeCategoryManager.get(pFeeCategoryId);
    Student student = mStudentManager.get(pStudentId);
    boolean resolvedDependencies = resolvedAllDependencies(category, student);
    if (resolvedDependencies) {
      List<UGFee> fees = mUGFeeManager
          .getLatestFee(student.getProgram().getFacultyId(), student.getCurrentEnrolledSemesterId()).stream()
          .filter((fee) -> fee.getFeeCategory().getId().equalsIgnoreCase(category.getId())).collect(Collectors.toList());
      if (fees.size() > 0) {
        UGFee fee = fees.get(0);
        MutableStudentPayment payment = new PersistentStudentPayment();
        payment.setFeeCategoryId(fee.getFeeCategoryId());
        payment.setStudentId(pStudentId);
        payment.setSemesterId(student.getCurrentEnrolledSemesterId());
        payment.setAmount(fee.getAmount());
        if (fee.getAmount().equals(BigDecimal.ZERO))
          payment.setStatus(StudentPayment.Status.RECEIVED);
        else
          payment.setStatus(StudentPayment.Status.APPLIED);
        Date today = new Date();
        payment.setTransactionValidTill(UmsUtils.addDay(today, 10));
        Long paymentId = payment.create();
        if (fee.getAmount().equals(BigDecimal.ZERO) || (category.getType().getId() == FeeType.Types.REG_CERTIFICATE_FEE.getId() && payment.getStatus().getValue() == StudentPayment.Status.APPLIED.getValue())) {
          StudentPayment studentPayment = mStudentPaymentManager.get(paymentId);
          insertIntoCertificateStatus(pStudentId, fee, studentPayment, today);
        }
      }
    }
  }
}
