package org.ums.common.academic.resource.helper;

import org.apache.commons.lang.NotImplementedException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.glassfish.jersey.server.Uri;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.common.ResourceHelper;
import org.ums.common.builder.ExamGradeBuilder;
import org.ums.domain.model.dto.GradeChartDataDto;
import org.ums.domain.model.dto.MarksSubmissionStatusDto;
import org.ums.domain.model.dto.StudentGradeDto;
import org.ums.domain.model.immutable.ExamGrade;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.User;
import org.ums.domain.model.mutable.MutableExamGrade;
import org.ums.enums.CourseMarksSubmissionStatus;
import org.ums.enums.CourseType;
import org.ums.enums.RecheckStatus;
import org.ums.enums.StudentMarksSubmissionStatus;
import org.ums.manager.ExamGradeManager;
import org.ums.manager.SemesterManager;
import org.ums.manager.UserManager;
import org.ums.persistent.model.PersistentExamGrade;

import javax.json.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.StringReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by ikh on 4/29/2016.
 */
@Component
public class GradeSubmissionResourceHelper extends ResourceHelper<ExamGrade, MutableExamGrade, Object> {

  @Autowired
  private ExamGradeManager mManager;

  @Autowired
  private ExamGradeBuilder mBuilder;

  @Autowired
  private UserManager mUserManager;

  @Autowired
  private SemesterManager mSemesterManager;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    throw new NotImplementedException("Post method is not implemented for GradeSubmissonResourceHelper");
  }

  @Override
  public ExamGradeManager getContentManager() {
    return mManager;
  }

  @Override
  public ExamGradeBuilder getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getEtag(ExamGrade pReadonly) {
    return null;
  }


  public JsonObject getGradeList(final String pRequestedRoleId, final Integer pSemesterId, final String pCourseId, final Integer pExamType) throws Exception {

    MarksSubmissionStatusDto marksSubmissionStatusDto = getContentManager().getMarksSubmissionStatus(pSemesterId, pCourseId, pExamType);
    JsonReader jsonReader = Json.createReader(new StringReader(marksSubmissionStatusDto.toString()));
    JsonObject object1 = jsonReader.readObject();
    JsonObjectBuilder object = Json.createObjectBuilder();
    jsonReader.close();
    object.add("part_info", object1);


    List<StudentGradeDto> examGradeList = getContentManager().getAllGrades(pSemesterId, pCourseId, pExamType, marksSubmissionStatusDto.getCourseType());
    String currentActor = getActorForCurrentUser(SecurityUtils.getSubject().getPrincipal().toString(), pRequestedRoleId, pSemesterId, pCourseId);
    object.add("current_actor", currentActor);
    object.add("current_course_status", marksSubmissionStatusDto.getStatusId());


    JsonArrayBuilder noneAndSubmitArrayBuilder = Json.createArrayBuilder();
    JsonArrayBuilder scrutinizeCandidatesArrayBuilder = Json.createArrayBuilder();
    JsonArrayBuilder approveCandidatesArrayBuilder = Json.createArrayBuilder();
    JsonArrayBuilder acceptCandidatesArrayBuilder = Json.createArrayBuilder();
    JsonArrayBuilder recheckCandidatesArrayBuilder = Json.createArrayBuilder();

    JsonArrayBuilder submittedArrayBuilder = Json.createArrayBuilder();
    JsonArrayBuilder scrutinizedArrayBuilder = Json.createArrayBuilder();
    JsonArrayBuilder approvedArrayBuilder = Json.createArrayBuilder();
    JsonArrayBuilder acceptedArrayBuilder = Json.createArrayBuilder();

    JsonArrayBuilder recheckAcceptedArrayBuilder = Json.createArrayBuilder();

    StudentMarksSubmissionStatus gradeStatus;
    CourseMarksSubmissionStatus courseStatus;

    for (StudentGradeDto gradeDto : examGradeList) {
      jsonReader = Json.createReader(new StringReader(gradeDto.toString()));
      object1 = jsonReader.readObject();
      jsonReader.close();
      gradeStatus = gradeDto.getStatus();
      courseStatus = CourseMarksSubmissionStatus.values()[marksSubmissionStatusDto.getStatusId()];


      if (gradeStatus == StudentMarksSubmissionStatus.NONE || gradeStatus == StudentMarksSubmissionStatus.SUBMIT && gradeDto.getRecheckStatusId() == 0 && currentActor.equalsIgnoreCase("preparer"))
        noneAndSubmitArrayBuilder.add(object1);
      else if ((gradeStatus == StudentMarksSubmissionStatus.SUBMITTED || gradeStatus == StudentMarksSubmissionStatus.SCRUTINIZE) && currentActor.equalsIgnoreCase("scrutinizer"))
        scrutinizeCandidatesArrayBuilder.add(object1);
      else if ((gradeStatus == StudentMarksSubmissionStatus.SCRUTINIZED || gradeStatus == StudentMarksSubmissionStatus.APPROVE) && currentActor.equalsIgnoreCase("head"))
        approveCandidatesArrayBuilder.add(object1);
      else if ((gradeStatus == StudentMarksSubmissionStatus.APPROVED || gradeStatus == StudentMarksSubmissionStatus.ACCEPT) && currentActor.equalsIgnoreCase("coe"))
        acceptCandidatesArrayBuilder.add(object1);

      if ((gradeStatus == StudentMarksSubmissionStatus.SUBMITTED || gradeStatus == StudentMarksSubmissionStatus.SCRUTINIZE))//&& !currentActor.equalsIgnoreCase("scrutinizer")
        submittedArrayBuilder.add(object1);
      else if ((gradeStatus == StudentMarksSubmissionStatus.SCRUTINIZED || gradeStatus == StudentMarksSubmissionStatus.APPROVE)) // && !currentActor.equalsIgnoreCase("head")
        scrutinizedArrayBuilder.add(object1);
      else if ((gradeStatus == StudentMarksSubmissionStatus.APPROVED || gradeStatus == StudentMarksSubmissionStatus.ACCEPT)) //&& !currentActor.equalsIgnoreCase("coe")
        approvedArrayBuilder.add(object1);
      else if (gradeStatus == StudentMarksSubmissionStatus.ACCEPTED) {
        //acceptedArrayBuilder.add(object1);
        if (gradeDto.getRecheckStatus() == RecheckStatus.RECHECK_TRUE)
          recheckAcceptedArrayBuilder.add(object1);
        else
          acceptedArrayBuilder.add(object1);
      }
//            else if(gradeStatus == StudentMarksSubmissionStatus.ACCEPTED )
//                acceptedArrayBuilder.add(object1);

      if ((courseStatus == CourseMarksSubmissionStatus.REQUESTED_FOR_RECHECK_BY_SCRUTINIZER || courseStatus == CourseMarksSubmissionStatus.REQUESTED_FOR_RECHECK_BY_HEAD
          || courseStatus == CourseMarksSubmissionStatus.REQUESTED_FOR_RECHECK_BY_COE) && gradeDto.getRecheckStatus() == RecheckStatus.RECHECK_TRUE) {
        recheckCandidatesArrayBuilder.add(object1);
      }
    }

    object.add("none_and_submit_grades", noneAndSubmitArrayBuilder);


    object.add("scrutinize_candidates_grades", scrutinizeCandidatesArrayBuilder);
    object.add("approve_candidates_grades", approveCandidatesArrayBuilder);
    object.add("accept_candidates_grades", acceptCandidatesArrayBuilder);
    object.add("recheck_candidates_grades", recheckCandidatesArrayBuilder);

    object.add("submitted_grades", submittedArrayBuilder);
    object.add("scrutinized_grades", scrutinizedArrayBuilder);
    object.add("approved_grades", approvedArrayBuilder);
    object.add("accepted_grades", acceptedArrayBuilder);
    object.add("recheck_accepted_grades", recheckAcceptedArrayBuilder);


    return object.build();
  }


  public JsonObject getGradeSubmissionStatus(final Integer pSemesterId, final Integer pExamType, final String deptId, final String pUserRole,final int pStatus) throws Exception {

    String userId = "";
    Subject subject = SecurityUtils.getSubject();
    if (subject != null) {
      userId = subject.getPrincipal().toString();
    }
    User user = mUserManager.get(userId);
    List<MarksSubmissionStatusDto> examGradeStatusList = getContentManager().getMarksSubmissionStatus(pSemesterId, pExamType, user.getEmployeeId(), deptId, pUserRole,pStatus);

    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();

    JsonReader jsonReader;
    JsonObject object1;

    for (MarksSubmissionStatusDto statusDto : examGradeStatusList) {
      jsonReader = Json.createReader(new StringReader(statusDto.toString()));
      object1 = jsonReader.readObject();
      jsonReader.close();
      children.add(object1);
    }
    object.add("entries", children);

    return object.build();
  }


  public Response saveGradeSheet(final JsonObject pJsonObject) throws Exception {

    //Should do the validation
    //. Is he the right person
    //. have the time period to do the operation
    //. other validations...
    List<StudentGradeDto> gradeList = getBuilder().build(pJsonObject);
    MarksSubmissionStatusDto partInfoDto = new MarksSubmissionStatusDto();
    getBuilder().build(partInfoDto, pJsonObject);

    String action = pJsonObject.getString("action");


    if (partInfoDto.getCourseType() == CourseType.THEORY) {
      int aa = getContentManager().updatePartInfo(partInfoDto.getSemesterId(), partInfoDto.getCourseId(), partInfoDto.getExamType(), partInfoDto.getTotal_part(), partInfoDto.getPart_a_total(), partInfoDto.getPart_b_total());
    }
    boolean updateStatus = getContentManager().saveGradeSheet(partInfoDto.getSemesterId(), partInfoDto.getCourseId(), partInfoDto.getExamType(), partInfoDto.getCourseType(), gradeList);

    if (action.equalsIgnoreCase("submit")) {
      //do the status update work here....
      int bb = getContentManager().updateCourseMarksSubmissionStatus(partInfoDto.getSemesterId(), partInfoDto.getCourseId(), partInfoDto.getExamType(), partInfoDto.getCourseType(), CourseMarksSubmissionStatus.WAITING_FOR_SCRUTINY);
    }

    Response.ResponseBuilder builder = Response.created(null);
    builder.status(Response.Status.CREATED);

    return builder.build();
  }

  public Response updateGradeStatus(final JsonObject pJsonObject) throws Exception {

    //Should do the validation
    //. Is he the right person
    //. have the time period to do the operation
    //. other validations...
    String action = pJsonObject.getString("action");
    String actor = pJsonObject.getString("actor");
    int current_course_status = pJsonObject.getInt("course_current_status");

    ArrayList<List<StudentGradeDto>> gradeList = getBuilder().buildForRecheckApproveGrade(action, actor, pJsonObject);
    MarksSubmissionStatusDto partInfoDto = new MarksSubmissionStatusDto();
    getBuilder().build(partInfoDto, pJsonObject);


    boolean updateStatus;
    //Need to improve this if else logic here....
    if (action.equals("save"))  // Scrutinizer, Head, CoE  Press the Save Button
      updateStatus = getContentManager().updateGradeStatus_Save(partInfoDto.getSemesterId(), partInfoDto.getCourseId(), partInfoDto.getExamType(), partInfoDto.getCourseType(), gradeList.get(0), gradeList.get(1));
    else if (action.equals("recheck")) {  //Scrutnizer, Head, CoE Press the Recheck Button
      updateStatus = getContentManager().updateGradeStatus_Recheck(partInfoDto.getSemesterId(), partInfoDto.getCourseId(), partInfoDto.getExamType(), partInfoDto.getCourseType(), gradeList.get(0), gradeList.get(1));
      int bb = getContentManager().updateCourseMarksSubmissionStatus(partInfoDto.getSemesterId(), partInfoDto.getCourseId(), partInfoDto.getExamType(), partInfoDto.getCourseType(), getCourseMarksSubmissionNextStatus(actor, action, CourseMarksSubmissionStatus.values()[current_course_status]));
    } else if (action.equals("approve")) { //Scrutnizer, Head, CoE Press the Approve Button
      updateStatus = getContentManager().updateGradeStatus_Approve(partInfoDto.getSemesterId(), partInfoDto.getCourseId(), partInfoDto.getExamType(), partInfoDto.getCourseType(), gradeList.get(0), gradeList.get(1));
      int bb = getContentManager().updateCourseMarksSubmissionStatus(partInfoDto.getSemesterId(), partInfoDto.getCourseId(), partInfoDto.getExamType(), partInfoDto.getCourseType(), getCourseMarksSubmissionNextStatus(actor, action, CourseMarksSubmissionStatus.values()[current_course_status]));
    } else if (action.equals("recheck_request_submit")) { // CoE Press the "Send Recheck Requst to VC" Button
      updateStatus = getContentManager().updateGradeStatus_Recheck(partInfoDto.getSemesterId(), partInfoDto.getCourseId(), partInfoDto.getExamType(), partInfoDto.getCourseType(), gradeList.get(0), gradeList.get(1));
      int bb = getContentManager().updateCourseMarksSubmissionStatus(partInfoDto.getSemesterId(), partInfoDto.getCourseId(), partInfoDto.getExamType(), partInfoDto.getCourseType(), getCourseMarksSubmissionNextStatus(actor, action, CourseMarksSubmissionStatus.values()[current_course_status]));
    }

    Response.ResponseBuilder builder = Response.created(null);
    builder.status(Response.Status.CREATED);

    return builder.build();
  }

  public Response recheckRequestApprove(final JsonObject pJsonObject) throws Exception {

    //Should do the validation
    //. Is he the right person
    //. have the time period to do the operation

    String action = pJsonObject.getString("action");
    String actor = pJsonObject.getString("actor");

    MarksSubmissionStatusDto partInfoDto = new MarksSubmissionStatusDto();
    getBuilder().build(partInfoDto, pJsonObject);

    int current_course_status = pJsonObject.getInt("course_current_status");

    boolean updateStatus;
    //Need to improve this if else logic here....
    if (action.equals("recheck_request_rejected")) {// VC sir Rejected the whole recheck request
      int a = getContentManager().rejectRecheckRequest(partInfoDto.getSemesterId(), partInfoDto.getCourseId(), partInfoDto.getExamType(), partInfoDto.getCourseType());
    } else if (action.equals("recheck_request_approved")) {  // VC Sir Approved the whole recheck request
      int b = getContentManager().approveRecheckRequest(partInfoDto.getSemesterId(), partInfoDto.getCourseId(), partInfoDto.getExamType(), partInfoDto.getCourseType());
    }
    int bb = getContentManager().updateCourseMarksSubmissionStatus(partInfoDto.getSemesterId(), partInfoDto.getCourseId(), partInfoDto.getExamType(), partInfoDto.getCourseType(), getCourseMarksSubmissionNextStatus(actor, action, CourseMarksSubmissionStatus.values()[current_course_status]));

    Response.ResponseBuilder builder = Response.created(null);
    builder.status(Response.Status.CREATED);

    return builder.build();
  }

  public CourseMarksSubmissionStatus getCourseMarksSubmissionNextStatus(String actor, String action, CourseMarksSubmissionStatus currentStatus) {
    CourseMarksSubmissionStatus nextStatus = null;
    if (actor.equalsIgnoreCase("scrutinizer")) {
      if (action.equalsIgnoreCase("recheck") && currentStatus == CourseMarksSubmissionStatus.WAITING_FOR_SCRUTINY)
        nextStatus = CourseMarksSubmissionStatus.REQUESTED_FOR_RECHECK_BY_SCRUTINIZER;
      else if (action.equalsIgnoreCase("approve") && currentStatus == CourseMarksSubmissionStatus.WAITING_FOR_SCRUTINY)
        nextStatus = CourseMarksSubmissionStatus.WAITING_FOR_HEAD_APPROVAL;
    } else if (actor.equalsIgnoreCase("head")) {
      if (action.equalsIgnoreCase("recheck") && currentStatus == CourseMarksSubmissionStatus.WAITING_FOR_HEAD_APPROVAL)
        nextStatus = CourseMarksSubmissionStatus.REQUESTED_FOR_RECHECK_BY_HEAD;
      else if (action.equalsIgnoreCase("approve") && currentStatus == CourseMarksSubmissionStatus.WAITING_FOR_HEAD_APPROVAL)
        nextStatus = CourseMarksSubmissionStatus.WAITING_FOR_COE_APPROVAL;
    } else if (actor.equalsIgnoreCase("coe")) {
      if (action.equalsIgnoreCase("recheck") && currentStatus == CourseMarksSubmissionStatus.WAITING_FOR_COE_APPROVAL)
        nextStatus = CourseMarksSubmissionStatus.REQUESTED_FOR_RECHECK_BY_COE;
      else if (action.equalsIgnoreCase("approve") && currentStatus == CourseMarksSubmissionStatus.WAITING_FOR_COE_APPROVAL)
        nextStatus = CourseMarksSubmissionStatus.ACCEPTED_BY_COE;
      else if (action.equalsIgnoreCase("recheck_request_submit") && currentStatus == CourseMarksSubmissionStatus.ACCEPTED_BY_COE)
        nextStatus = CourseMarksSubmissionStatus.WAITING_FOR_RECHECK_REQUEST_APPROVAL;
    } else if (actor.equalsIgnoreCase("vc")) {
      if (action.equalsIgnoreCase("recheck_request_rejected") && currentStatus == CourseMarksSubmissionStatus.WAITING_FOR_RECHECK_REQUEST_APPROVAL)
        nextStatus = CourseMarksSubmissionStatus.ACCEPTED_BY_COE;
      else if (action.equalsIgnoreCase("recheck_request_approved") && currentStatus == CourseMarksSubmissionStatus.WAITING_FOR_RECHECK_REQUEST_APPROVAL)
        nextStatus = CourseMarksSubmissionStatus.REQUESTED_FOR_RECHECK_BY_COE;
    }

    return nextStatus;
  }

  public String getActorForCurrentUser(String userId, String requestedRole, int semesterId, String courseId) throws Exception {
    if (requestedRole.equalsIgnoreCase("T")) {
      User user = mUserManager.get(userId);
      List<String> roleList = mManager.getRoleForTeacher(user.getEmployeeId(), semesterId, courseId);
      if (roleList.size() == 0)
        return "Invalid";
      else
        return roleList.get(0);
    } else if (requestedRole.equalsIgnoreCase("H")) {
      List<String> roleList = mManager.getRoleForHead(userId);
      if (roleList.size() == 0)
        return "Invalid";
      else
        return roleList.get(0);
    } else if (requestedRole.equalsIgnoreCase("C")) {
      List<String> roleList = mManager.getRoleForCoE(userId);
      if (roleList.size() == 0)
        return "Invalid";
      else
        return roleList.get(0);
    } else if (requestedRole.equalsIgnoreCase("V")) {
      List<String> roleList = mManager.getRoleForVC(userId);
      if (roleList.size() == 0)
        return "Invalid";
      else
        return roleList.get(0);
    }
    return "Invalid";
  }

  public JsonObject getChartData(final Integer pSemesterId, final String pCourseId, final Integer pExamType, final Integer courseType) throws Exception {

    String userId = "";
    Subject subject = SecurityUtils.getSubject();
    if (subject != null) {
      userId = subject.getPrincipal().toString();
    }
    User user = mUserManager.get(userId);
    //Need to check User Role
    //Need to check Course Type - Theory or Sessional
    List<GradeChartDataDto> examGradeStatusList = getContentManager().getChartData(pSemesterId, pCourseId, pExamType, CourseType.get(courseType));

    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();

    JsonReader jsonReader;
    JsonObject object1;

    for (GradeChartDataDto chartDto : examGradeStatusList) {
      jsonReader = Json.createReader(new StringReader(chartDto.toString()));
      object1 = jsonReader.readObject();
      jsonReader.close();
      children.add(object1);
    }
    object.add("entries", children);

    return object.build();
  }

  public JsonObject getGradeSubmissionDeadline(final Integer pSemesterId, final Integer pExamType, final String pExamDate, final UriInfo pUriInfo) throws Exception{

    List<MarksSubmissionStatusDto> marksSubmissionStatusDtoList = new ArrayList<>();
    Integer size = getContentManager().checkSize(pSemesterId,pExamType,pExamDate);

    if(size==0){
      getContentManager().insertGradeSubmissionDeadLineInfo(pSemesterId,pExamType,pExamDate);
      marksSubmissionStatusDtoList = getContentManager().getGradeSubmissionDeadLine(pSemesterId,pExamType,pExamDate);
    }else{
      marksSubmissionStatusDtoList = mManager.getGradeSubmissionDeadLine(pSemesterId,pExamType,pExamDate);
    }


    Collections.sort(marksSubmissionStatusDtoList, new Comparator<MarksSubmissionStatusDto>() {

      @Override
      public int compare(MarksSubmissionStatusDto o1, MarksSubmissionStatusDto o2) {
        int c;
        c = o1.getProgramShortname().compareTo(o2.getProgramShortname());
        if(c==0)
          c=o1.getCourseNo().compareTo(o2.getCourseNo());
        return c;
      }
    });

    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();

    for(MarksSubmissionStatusDto marksSubmissionStatusDto: marksSubmissionStatusDtoList){
      PersistentExamGrade examGrade= new PersistentExamGrade();
      examGrade.setExamDate(marksSubmissionStatusDto.getExamDate());
      examGrade.setProgramShortName(marksSubmissionStatusDto.getProgramShortname());
      examGrade.setCourseId(marksSubmissionStatusDto.getCourseId());
      examGrade.setCourseNo(marksSubmissionStatusDto.getCourseNo());
      examGrade.setCourseTitle(marksSubmissionStatusDto.getCourseTitle());
      examGrade.setCourseCreditHour(marksSubmissionStatusDto.getCourseCreditHour());
      examGrade.setTotalStudents(marksSubmissionStatusDto.getTotalStudents());
      if(marksSubmissionStatusDto.getLastSubmissionDate()!=null){
        examGrade.setLastSubmissionDate(marksSubmissionStatusDto.getLastSubmissionDate());
      }
      ExamGrade immutableExamGrade = examGrade;
      children.add(toJson(immutableExamGrade,pUriInfo,localCache));
    }

    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }


  public Response updateGradeSubmissionDeadLine(JsonObject pJsonObject,UriInfo pUriInfo) throws Exception{

    JsonArray entries = pJsonObject.getJsonArray("entries");
    List<MarksSubmissionStatusDto> marksSubmissionStatusDtos = new ArrayList<>();


    boolean isSemesterValid=true;
    for(int i=0;i<entries.size();i++){
      JsonObject jsonObject = entries.getJsonObject(i);
      MutableExamGrade examGrade = new PersistentExamGrade();
      LocalCache  localCache = new LocalCache();
      getBuilder().build(examGrade,jsonObject,localCache);
      if(i==0){
        Semester semester = mSemesterManager.get(examGrade.getSemesterId());
        if(semester.getStatus().getValue()!=1 ){
          isSemesterValid=false;
          break;
        }
      }

      MarksSubmissionStatusDto marksSubmissionStatusDto = new MarksSubmissionStatusDto();
      marksSubmissionStatusDto.setExamDate(examGrade.getExamDate());
      marksSubmissionStatusDto.setSemesterId(examGrade.getSemesterId());
      marksSubmissionStatusDto.setExamType(examGrade.getExamTypeId());
      marksSubmissionStatusDto.setCourseId(examGrade.getCourseId());
      marksSubmissionStatusDtos.add(marksSubmissionStatusDto);
    }

    if(isSemesterValid){
      getContentManager().updateForGradeSubmissionDeadLine(marksSubmissionStatusDtos);

    }


    URI contextURI = null;
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

}
