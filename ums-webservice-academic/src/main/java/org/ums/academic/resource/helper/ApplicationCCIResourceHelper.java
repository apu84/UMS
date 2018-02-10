package org.ums.academic.resource.helper;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Persistent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ums.academic.resource.ApplicationCCIResource;
import org.ums.builder.ApplicationCCIBuilder;
import org.ums.builder.Builder;
import org.ums.builder.UGRegistrationResultBuilder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.ApplicationCCI;
import org.ums.domain.model.immutable.Employee;
import org.ums.domain.model.immutable.Student;
import org.ums.domain.model.immutable.UGRegistrationResult;
import org.ums.domain.model.mutable.MutableApplicationCCI;
import org.ums.enums.ApplicationType;
import org.ums.fee.FeeCategory;
import org.ums.fee.FeeCategoryManager;
import org.ums.fee.UGFee;
import org.ums.fee.UGFeeManager;
import org.ums.fee.payment.MutableStudentPayment;
import org.ums.fee.payment.PersistentStudentPayment;
import org.ums.fee.payment.StudentPayment;
import org.ums.fee.payment.StudentPaymentManager;
import org.ums.generator.IdGenerator;
import org.ums.manager.ApplicationCCIManager;
import org.ums.manager.EmployeeManager;
import org.ums.manager.StudentManager;
import org.ums.manager.UGRegistrationResultManager;
import org.ums.persistent.model.PersistentApplicationCCI;
import org.ums.persistent.model.PersistentUGRegistrationResult;
import org.ums.resource.ResourceHelper;
import org.ums.services.academic.ApplicationCCIService;
import org.ums.usermanagement.user.User;
import org.ums.usermanagement.user.UserManager;
import org.ums.util.UmsUtils;

import javax.json.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by My Pc on 7/14/2016.
 */
@Component
public class ApplicationCCIResourceHelper extends ResourceHelper<ApplicationCCI, MutableApplicationCCI, Long> {

  @Autowired
  ApplicationCCIManager mManager;

  @Autowired
  StudentManager mStudentManager;

  @Autowired
  UGRegistrationResultManager mResultManager;

  @Autowired
  ApplicationCCIBuilder mBuilder;

  @Autowired
  UGRegistrationResultBuilder mResultBuilder;

  @Autowired
  ApplicationCCIService mApplicationCCIService;

  @Autowired
  UGRegistrationResultResourceHelper mResultHelper;

  @Autowired
  EmployeeManager mEmployeeManager;

  @Autowired
  UserManager mUserManager;

  @Autowired
  IdGenerator mIdGenerator;

  @Autowired
  StudentPaymentManager mStudentPaymentManager;

  @Autowired
  UGFeeManager mUgFeeManager;

  @Autowired
  FeeCategoryManager mFeeCategoryManager;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) {
    /*
     * Validator validate = new ApplicationCCIValidator(); validate.validate(pJsonObject);
     */

    String studentId = SecurityUtils.getSubject().getPrincipal().toString();
    Student student = mStudentManager.get(studentId);
    List<ApplicationCCI> aplicationListAll = getContentManager().getAll();
    if(aplicationListAll.size() > 0) {
      mManager.deleteByStudentId(studentId);
    }

    List<MutableApplicationCCI> applications = new ArrayList<>();

    JsonArray entries = pJsonObject.getJsonArray("entries");

    for(int i = 0; i < entries.size(); i++) {
      LocalCache localCache = new LocalCache();
      JsonObject jsonObject = entries.getJsonObject(i);
      PersistentApplicationCCI application = new PersistentApplicationCCI();
      getBuilder().build(application, jsonObject, localCache);
      applications.add(application);
    }

    mManager.create(applications);
    URI contextURI = null;
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  // Bank Payment Approval
  public Response cciApproval(JsonObject pJsonObject, UriInfo pUriInfo) {
    List<MutableApplicationCCI> applications = new ArrayList<>();
    JsonArray entries = pJsonObject.getJsonArray("entries");
    for(int i = 0; i < entries.size(); i++) {
      LocalCache localCache = new LocalCache();
      JsonObject jsonObject = entries.getJsonObject(i);
      System.out.println("--------" + jsonObject);
      PersistentApplicationCCI application = new PersistentApplicationCCI();
      getBuilder().build(application, jsonObject, localCache);
      applications.add(application);
    }
    mManager.update(applications);
    URI contextURI = null;
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  public Response appliedAndApproved(final String studentId, final Integer semesterId, JsonObject pJsonObject,
      UriInfo pUriInfo) {
    List<MutableApplicationCCI> applications = new ArrayList<>();
    JsonArray entries = pJsonObject.getJsonArray("entries");
    for(int i = 0; i < entries.size(); i++) {
      LocalCache localCache = new LocalCache();
      JsonObject jsonObject = entries.getJsonObject(i);
      System.out.println("--------" + jsonObject);
      PersistentApplicationCCI application = new PersistentApplicationCCI();
      getBuilder().build(application, jsonObject, localCache);
      application.setStudentId(studentId);
      application.setSemesterId(semesterId);
      applications.add(application);
    }
    mManager.update(applications);
    URI contextURI = null;
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  @Transactional
  public JsonObject saveAndReturn(JsonObject pJsonObject, UriInfo pUriInfo) {

    String studentId = SecurityUtils.getSubject().getPrincipal().toString();
    Student student = mStudentManager.get(studentId);

    List<MutableApplicationCCI> applications = new ArrayList<>();
    List<PersistentApplicationCCI> persistentApplicationCCIs = new ArrayList<>();

    JsonArray entries = pJsonObject.getJsonArray("entries");


    List<UGRegistrationResult> results =
        mResultManager.getCarryClearanceImprovementCoursesByStudent(student.getCurrentEnrolledSemester().getId(), studentId);
      Map<String, UGRegistrationResult> courseIdMapWithUgRegistrationResult=results
              .stream()
              .collect(Collectors.toMap(UGRegistrationResult::getCourseId, Function.identity()));

      retrieveObjectFromJson(applications, persistentApplicationCCIs, entries, courseIdMapWithUgRegistrationResult);

      List<PersistentApplicationCCI> applicationAfterValidationByService =
        persistentApplicationCCIs;
      mManager.create(applications);
    List<MutableStudentPayment> studentPayments = new ArrayList<>();
    applications.forEach(application->{
      if(!application.getApplicationType().equals(ApplicationType.CLEARANCE)){
        MutableStudentPayment studentPayment = new PersistentStudentPayment();
        FeeCategory.Categories feeCategoryId = application.getApplicationType().equals(ApplicationType.CARRY)? FeeCategory.Categories.CARRY:FeeCategory.Categories.IMPROVEMENT;
        FeeCategory feeCategory = mFeeCategoryManager.getByFeeId(feeCategoryId.toString());
        List<FeeCategory> feeCategories = new ArrayList<>();
        feeCategories.add(feeCategory);
//      List<UGFee> fees = mUgFeeManager.getFee(
//              application.getStudent().getProgram().getFacultyId(),
//              application.getStudent().getSemester().getId(),
//              feeCategories);
        UGFee fee = mUgFeeManager.getFee(application.getStudent().getProgram().getFacultyId(),
                application.getStudent().getSemester().getId(),feeCategory);
        studentPayment.setFeeCategoryId(feeCategory.getId());
        studentPayment.setStatus(StudentPayment.Status.APPLIED);
        studentPayment.setSemesterId(application.getSemester().getId());
        studentPayment.setStudentId(application.getStudent().getId());
        studentPayment.setAmount(fee.getAmount());
        String transactionIIIID = application.getTransactionID();
        studentPayment.setTransactionId(application.getTransactionID());
        studentPayment.setTransactionValidTill(UmsUtils.addDay(new Date(), 10));
        studentPayments.add(studentPayment);
      }

    });
    //int x=studentPayments.size();

    mStudentPaymentManager.create(studentPayments);
      JsonObjectBuilder object = Json.createObjectBuilder();
      JsonArrayBuilder children = Json.createArrayBuilder();
      LocalCache localCache = new LocalCache();
      List<UGRegistrationResult> resultForWorkingAsResponse = new ArrayList<>();



      applicationAfterValidationByService.forEach(a->{
          if(courseIdMapWithUgRegistrationResult.containsKey(a.getCourseId()))
          {
              PersistentUGRegistrationResult regResult  = (PersistentUGRegistrationResult) courseIdMapWithUgRegistrationResult.get(a.getCourseId());
              regResult.setMessage(a.getMessage());
              resultForWorkingAsResponse.add(regResult);
          }
      });


    object.add("entries", children);
    localCache.invalidate();
    return mResultHelper.getResultForApplicationCCIOfCarryClearanceAndImprovement(resultForWorkingAsResponse, pUriInfo);
  }

  private void retrieveObjectFromJson(List<MutableApplicationCCI> applications,
      List<PersistentApplicationCCI> persistentApplicationCCIs, JsonArray entries,
      Map<String, UGRegistrationResult> courseIdMapWithUgRegistrationResult) {
    for(int i = 0; i < entries.size(); i++) {
      LocalCache localCache = new LocalCache();
      JsonObject jsonObject = entries.getJsonObject(i);
      PersistentApplicationCCI application = new PersistentApplicationCCI();
      getBuilder().build(application, jsonObject, localCache);
      if(courseIdMapWithUgRegistrationResult.containsKey(application.getCourseId()))
        application.setExamDate(courseIdMapWithUgRegistrationResult.get(application.getCourseId()).getExamDate());
      if(!application.getApplicationType().equals(ApplicationType.CLEARANCE))
        application.setTransactionID(mIdGenerator.getAlphaNumericId());
      applications.add(application);

      persistentApplicationCCIs.add(application);
    }
  }

  public Response deleteByStudentId(UriInfo pUriInfo) {
    String studentId = SecurityUtils.getSubject().getPrincipal().toString();
    getContentManager().deleteByStudentId(studentId);
    URI contextURI =
        pUriInfo.getBaseUriBuilder().path(ApplicationCCIResource.class).path(ApplicationCCIResource.class, "get")
            .build();
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  // CarryApprovaByHeadMethod
  public JsonObject getApplicationCarryForHeadsApproval(final String pApprovalStatus,final Request pRequest, final UriInfo pUriInfo){
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    String userId = SecurityUtils.getSubject().getPrincipal().toString();
    User loggedUser = mUserManager.get(userId);
    Employee loggedEmployee = mEmployeeManager.get(loggedUser.getEmployeeId());
    String empDeptId=loggedEmployee.getDepartment().getId();
    //(pApprovalStatus, loggedEmployee.getId())
    List<ApplicationCCI> applications =getContentManager().getApplicationCarryForHeadsApproval(pApprovalStatus,empDeptId);
    applications.forEach(a-> children.add(toJson(a, pUriInfo, localCache)));
    object.add("entries", children);
    localCache.invalidate();
    return object.build();

  }

  // AllcarryFor Applied && Approved Info
  public JsonObject getApplicationCarryForHeadsApprovalAndAppiled(final String studentId,final Integer semesterid,final Request pRequest, final UriInfo pUriInfo){
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    List<ApplicationCCI> applications =getContentManager().getApplicationCarryForHeadsApprovalAndAppiled(studentId,semesterid);
    applications.forEach(a-> children.add(toJson(a, pUriInfo, localCache)));
    object.add("entries", children);
    localCache.invalidate();
    return object.build();

  }

  // getByStudentId
  public JsonObject getByStudentId(final String approvalStatus,final String studentId,final Request pRequest, final UriInfo pUriInfo){
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    Student student = mStudentManager.get(studentId);
    LocalCache localCache = new LocalCache();
    List<ApplicationCCI> applications =getContentManager().getByStudentId(approvalStatus,studentId,student.getCurrentEnrolledSemester().getId(),student.getDepartmentId());
    applications.forEach(a-> children.add(toJson(a, pUriInfo, localCache)));
    object.add("entries", children);
    localCache.invalidate();
    return object.build();

  }

  // Total carry From UG_Registration Result
  public JsonObject getTotalCarry(final String studentId, final Integer semesterid, final Request pRequest, final UriInfo pUriInfo){
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    List<ApplicationCCI> applications =getContentManager().getTotalCarry(studentId,semesterid);
    applications.forEach(a-> children.add(toJson(a, pUriInfo, localCache)));
    object.add("entries", children);
    localCache.invalidate();
    return object.build();

  }

  public JsonObject getApplicationCCIInfoForStudent(final Request pRequest, final UriInfo pUriInfo) {
    String studentId = SecurityUtils.getSubject().getPrincipal().toString();
    Student student = mStudentManager.get(studentId);
//    List<ApplicationCCI> applictionAll = getContentManager().getAll();
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
      List<ApplicationCCI> applications =
              getContentManager().getByStudentIdAndSemester(studentId, student.getCurrentEnrolledSemester().getId());
      applications.forEach(a-> children.add(toJson(a, pUriInfo, localCache)));
      /*for(ApplicationCCI app : applications) {
          children.add(toJson(app, pUriInfo, localCache));
      }*/
  /*  if(applictionAll.size() > 0) {

    }
    else {
      List<ApplicationCCI> applications = getContentManager().getAll();
      for(ApplicationCCI app : applications) {
        children.add(toJson(app, pUriInfo, localCache));
      }
    }*/

    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  public JsonObject getApplicationCCIForSeatPlanViewingOfStudent(final UriInfo pUriInfo) {
    String studentId = SecurityUtils.getSubject().getPrincipal().toString();
    Student student = mStudentManager.get(studentId);
    List<ApplicationCCI> applications =
        getContentManager().getByStudentIdAndSemesterForSeatPlanView(studentId, student.getCurrentEnrolledSemesterId());

    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for(ApplicationCCI app : applications) {
      children.add(toJson(app, pUriInfo, localCache));
    }
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  // limit
  public Integer getApplicationCCIForImprovementLimit(final Request pRequest, final UriInfo pUriInfo) {
    String studentId = SecurityUtils.getSubject().getPrincipal().toString();
    List<ApplicationCCI> applications = getContentManager().getApplicationCCIForImprovementLimit(studentId);
    return applications.get(0).getImprovementLimit();
  }

  public JsonObject getApplicationCCIForSeatPlan(final Integer pSemesterId, final String pExamDate,
      final Request pRequest, final UriInfo pUriInfo) {

    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    List<ApplicationCCI> applications = getContentManager().getBySemesterAndExamDate(pSemesterId, pExamDate);

    for(ApplicationCCI app : applications) {
      children.add(toJson(app, pUriInfo, localCache));
    }
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  @Override
  protected ApplicationCCIManager getContentManager() {
    return mManager;
  }

  @Override
  protected Builder<ApplicationCCI, MutableApplicationCCI> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(ApplicationCCI pReadonly) {
    return pReadonly.getApplicationDate();
  }

}
