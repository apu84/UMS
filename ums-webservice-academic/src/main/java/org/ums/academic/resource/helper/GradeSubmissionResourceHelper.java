package org.ums.academic.resource.helper;

import java.io.StringReader;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.json.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang.NotImplementedException;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.ums.builder.ExamGradeBuilder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.dto.*;
import org.ums.domain.model.immutable.*;
import org.ums.domain.model.mutable.MutableExamGrade;
import org.ums.domain.model.mutable.MutableMarksSubmissionStatus;
import org.ums.enums.*;
import org.ums.enums.academic.GradeSubmissionColorCode;
import org.ums.manager.*;
import org.ums.persistent.model.PersistentExamGrade;
import org.ums.resource.ResourceHelper;
import org.ums.services.academic.GradeSubmissionService;
import org.ums.usermanagement.user.User;
import org.ums.usermanagement.user.UserManager;

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

  @Autowired
  private GradeSubmissionService gradeSubmissionService;

  @Autowired
  private EmployeeManager mEmployeeManager;

  @Autowired
  private MarksSubmissionStatusManager mMarksSubmissionStatusManager;

  @Autowired
  private ExamGradeManager mExamGradeManager;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) {
    throw new NotImplementedException("Post method is not implemented for GradeSubmissionResourceHelper");
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
  protected String getETag(ExamGrade pReadonly) {
    return null;
  }

  public JsonObject getGradeList(final String pRequestedRoleId, final Integer pSemesterId, final String pCourseId,
      final ExamType pExamType) {

    MarksSubmissionStatusDto marksSubmissionStatusDto =
        getContentManager().getMarksSubmissionStatus(pSemesterId, pCourseId, pExamType);
    String currentActor =
        gradeSubmissionService.getActorForCurrentUser(SecurityUtils.getSubject().getPrincipal().toString(),
            pRequestedRoleId, pSemesterId, pCourseId);

    JsonReader jsonReader = Json.createReader(new StringReader(marksSubmissionStatusDto.toString()));
    JsonObject jsonObject = jsonReader.readObject();
    JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
    jsonReader.close();
    objectBuilder.add("part_info", jsonObject);
    objectBuilder.add("current_actor", currentActor);
    objectBuilder.add("current_course_status", marksSubmissionStatusDto.getStatusId());

    prepareGradeGroups(objectBuilder,
        getContentManager().getAllGrades(pSemesterId, pCourseId, pExamType, marksSubmissionStatusDto.getCourseType()),
        CourseMarksSubmissionStatus.values()[marksSubmissionStatusDto.getStatusId()], currentActor);

    return objectBuilder.build();
  }

  public JsonObject enrich(JsonObject source, String key, String value) {
      JsonObjectBuilder builder = Json.createObjectBuilder();
      source.entrySet().
          forEach(e -> builder.add(e.getKey(), e.getValue()));
      builder.add(key, value);
      return builder.build();
    }

  void prepareGradeGroups(JsonObjectBuilder objectBuilder, List<StudentGradeDto> examGradeList,
      CourseMarksSubmissionStatus courseStatus, String currentActor) {

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
    JsonReader jsonReader;
    JsonObject jsonObject;

    for(StudentGradeDto gradeDto : examGradeList) {
      jsonReader = Json.createReader(new StringReader(gradeDto.toString()));
      jsonObject = jsonReader.readObject();

      if(jsonObject.get("partAAddiInfo") != null
          && (jsonObject.getString("partAAddiInfo").equals("Abs") || jsonObject.getString("partAAddiInfo")
              .equals("Rep"))) {
        jsonObject = enrich(jsonObject, "partA", jsonObject.getString("partAAddiInfo"));
      }
      if(jsonObject.get("partBAddiInfo") != null
          && (jsonObject.getString("partBAddiInfo").equals("Abs") || jsonObject.getString("partBAddiInfo")
              .equals("Rep"))) {
        jsonObject = enrich(jsonObject, "partB", jsonObject.getString("partBAddiInfo"));
      }

      jsonReader.close();
      gradeStatus = gradeDto.getStatus();

      if(gradeStatus == StudentMarksSubmissionStatus.NONE || gradeStatus == StudentMarksSubmissionStatus.SUBMIT
          && gradeDto.getRecheckStatusId() == 0 && currentActor.equalsIgnoreCase("preparer")) {
        noneAndSubmitArrayBuilder.add(jsonObject);
      }
      else if((gradeStatus == StudentMarksSubmissionStatus.SUBMITTED || gradeStatus == StudentMarksSubmissionStatus.SCRUTINIZE)
          && currentActor.equalsIgnoreCase("scrutinizer")) {
        scrutinizeCandidatesArrayBuilder.add(jsonObject);
      }
      else if((gradeStatus == StudentMarksSubmissionStatus.SCRUTINIZED || gradeStatus == StudentMarksSubmissionStatus.APPROVE)
          && currentActor.equalsIgnoreCase("head")) {
        approveCandidatesArrayBuilder.add(jsonObject);
      }
      else if((gradeStatus == StudentMarksSubmissionStatus.APPROVED || gradeStatus == StudentMarksSubmissionStatus.ACCEPT)
          && currentActor.equalsIgnoreCase("coe")) {
        acceptCandidatesArrayBuilder.add(jsonObject);
      }

      if((gradeStatus == StudentMarksSubmissionStatus.SUBMITTED || gradeStatus == StudentMarksSubmissionStatus.SCRUTINIZE))// &&
        // !currentActor.equalsIgnoreCase("scrutinizer")
        submittedArrayBuilder.add(jsonObject);
      else if((gradeStatus == StudentMarksSubmissionStatus.SCRUTINIZED || gradeStatus == StudentMarksSubmissionStatus.APPROVE)) // &&
        // !currentActor.equalsIgnoreCase("head")
        scrutinizedArrayBuilder.add(jsonObject);
      else if((gradeStatus == StudentMarksSubmissionStatus.APPROVED || gradeStatus == StudentMarksSubmissionStatus.ACCEPT)) // &&
        // !currentActor.equalsIgnoreCase("coe")
        approvedArrayBuilder.add(jsonObject);
      else if(gradeStatus == StudentMarksSubmissionStatus.ACCEPTED) {
        // acceptedArrayBuilder.add(object1);
        if(gradeDto.getRecheckStatus() == RecheckStatus.RECHECK_TRUE)
          recheckAcceptedArrayBuilder.add(jsonObject);
        else
          acceptedArrayBuilder.add(jsonObject);
      }
      if((courseStatus == CourseMarksSubmissionStatus.REQUESTED_FOR_RECHECK_BY_SCRUTINIZER
          || courseStatus == CourseMarksSubmissionStatus.REQUESTED_FOR_RECHECK_BY_HEAD || courseStatus == CourseMarksSubmissionStatus.REQUESTED_FOR_RECHECK_BY_COE)
          && gradeDto.getRecheckStatus() == RecheckStatus.RECHECK_TRUE) {
        recheckCandidatesArrayBuilder.add(jsonObject);
      }
    }

    objectBuilder.add("none_and_submit_grades", noneAndSubmitArrayBuilder);
    objectBuilder.add("scrutinize_candidates_grades", scrutinizeCandidatesArrayBuilder);
    objectBuilder.add("approve_candidates_grades", approveCandidatesArrayBuilder);
    objectBuilder.add("accept_candidates_grades", acceptCandidatesArrayBuilder);
    objectBuilder.add("recheck_candidates_grades", recheckCandidatesArrayBuilder);
    objectBuilder.add("submitted_grades", submittedArrayBuilder);
    objectBuilder.add("scrutinized_grades", scrutinizedArrayBuilder);
    objectBuilder.add("approved_grades", approvedArrayBuilder);
    objectBuilder.add("accepted_grades", acceptedArrayBuilder);
    objectBuilder.add("recheck_accepted_grades", recheckAcceptedArrayBuilder);
  }

  public JsonObject getGradeSubmissionStatus(final Integer pSemesterId, final Integer pExamType,
      final Integer pProgramId, final Integer pYearSemester, final String deptId, final String pUserRole,
      final int pStatus, final String pCourseNo) {
    User user = mUserManager.get(SecurityUtils.getSubject().getPrincipal().toString());
    Integer year = 0;
    Integer semester = 0;

    if(pYearSemester != 0) {
      year = Integer.valueOf((pYearSemester.toString()).charAt(0) + "");
      semester = Integer.valueOf((pYearSemester.toString()).charAt(1) + "");
    }
    List<MarksSubmissionStatusDto> examGradeStatusList =
        getContentManager().getMarksSubmissionStatus(pSemesterId, pExamType, pProgramId, year, semester,
            user.getEmployeeId(), deptId, pUserRole, pStatus, pCourseNo);
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    JsonReader jsonReader;
    JsonObject jsonObject;

    for(MarksSubmissionStatusDto statusDto : examGradeStatusList) {
      setSubmissionColorCode(pUserRole, user.getEmployeeId(), statusDto);
      jsonReader = Json.createReader(new StringReader(statusDto.toString()));
      jsonObject = jsonReader.readObject();
      jsonReader.close();
      children.add(jsonObject);
    }
    object.add("entries", children);
    return object.build();
  }

  private void setSubmissionColorCode(String pUserRole, String pUserId, MarksSubmissionStatusDto pStatus) {
    Date currentDate = new Date();
    if(pUserRole.equals("T")) {
      if(pUserId.equals(pStatus.getPreparerId())) {
        if(pStatus.getLastSubmissionDatePrep() != null) {
          if(currentDate.after(pStatus.getLastSubmissionDatePrep())) {
            if(pStatus.getStatus() == CourseMarksSubmissionStatus.NOT_SUBMITTED
                || pStatus.getStatus() == CourseMarksSubmissionStatus.REQUESTED_FOR_RECHECK_BY_SCRUTINIZER
                || pStatus.getStatus() == CourseMarksSubmissionStatus.REQUESTED_FOR_RECHECK_BY_HEAD
                || pStatus.getStatus() == CourseMarksSubmissionStatus.REQUESTED_FOR_RECHECK_BY_COE) {
              pStatus.setSubmissionColorCode(GradeSubmissionColorCode.TIMEOVER);
            }
            else {
              pStatus.setSubmissionColorCode(GradeSubmissionColorCode.SUBMITTED);
            }

          }
          else {
            pStatus.setSubmissionColorCode(GradeSubmissionColorCode.NONE);
          }
        }
        else {
          pStatus.setSubmissionColorCode(GradeSubmissionColorCode.NONE);
        }
      }

      if(pUserId.equals(pStatus.getScrutinizerId())) {
        if(pStatus.getLastSubmissionDateScr() != null) {
          if(currentDate.after(pStatus.getLastSubmissionDateScr())) {
            if(pStatus.getStatus() == CourseMarksSubmissionStatus.NOT_SUBMITTED
                || pStatus.getStatus() == CourseMarksSubmissionStatus.WAITING_FOR_SCRUTINY
                || pStatus.getStatus() == CourseMarksSubmissionStatus.REQUESTED_FOR_RECHECK_BY_SCRUTINIZER
                || pStatus.getStatus() == CourseMarksSubmissionStatus.REQUESTED_FOR_RECHECK_BY_HEAD
                || pStatus.getStatus() == CourseMarksSubmissionStatus.REQUESTED_FOR_RECHECK_BY_COE) {
              pStatus.setSubmissionColorCode(GradeSubmissionColorCode.TIMEOVER);
            }
            else {
              pStatus.setSubmissionColorCode(GradeSubmissionColorCode.SUBMITTED);
            }

          }
          else {
            pStatus.setSubmissionColorCode(GradeSubmissionColorCode.NONE);
          }
        }
        else {
          pStatus.setSubmissionColorCode(GradeSubmissionColorCode.NONE);
        }
      }
    }
    if(pUserRole.equals("H")) {
      if(pStatus.getLastSubmissionDateHead() != null) {
        if(currentDate.after(pStatus.getLastSubmissionDateHead())) {
          if(pStatus.getStatus() == CourseMarksSubmissionStatus.NOT_SUBMITTED
              || pStatus.getStatus() == CourseMarksSubmissionStatus.WAITING_FOR_SCRUTINY
              || pStatus.getStatus() == CourseMarksSubmissionStatus.WAITING_FOR_HEAD_APPROVAL
              || pStatus.getStatus() == CourseMarksSubmissionStatus.REQUESTED_FOR_RECHECK_BY_SCRUTINIZER
              || pStatus.getStatus() == CourseMarksSubmissionStatus.REQUESTED_FOR_RECHECK_BY_HEAD
              || pStatus.getStatus() == CourseMarksSubmissionStatus.REQUESTED_FOR_RECHECK_BY_COE) {
            pStatus.setSubmissionColorCode(GradeSubmissionColorCode.TIMEOVER);
          }
          else {
            pStatus.setSubmissionColorCode(GradeSubmissionColorCode.SUBMITTED);
          }

        }
        else {
          pStatus.setSubmissionColorCode(GradeSubmissionColorCode.NONE);
        }
      }
      else {
        pStatus.setSubmissionColorCode(GradeSubmissionColorCode.NONE);
      }
    }

  }

  // This method will only be used by Grade Sheet Preparer during saving or submitting grades.
  @Transactional
  public Response saveGradeSheet(final JsonObject pJsonObject) {
    List<StudentGradeDto> gradeList = getBuilder().build(pJsonObject);
    MarksSubmissionStatusDto requestedStatusDTO = new MarksSubmissionStatusDto();
    getBuilder().build(requestedStatusDTO, pJsonObject);

    String action = pJsonObject.getString("action");
    String userRole = pJsonObject.getString("role");
    String userId = SecurityUtils.getSubject().getPrincipal().toString();

    MarksSubmissionStatus marksSubmissionStatus =
        mMarksSubmissionStatusManager.get(requestedStatusDTO.getSemesterId(), requestedStatusDTO.getCourseId(),
            requestedStatusDTO.getExamType());

    String actingRoleForCurrentUser =
        gradeSubmissionService.getActorForCurrentUser(userId, userRole, marksSubmissionStatus.getSemesterId(),
            marksSubmissionStatus.getCourseId());

    if((marksSubmissionStatus.getCourse().getCourseType() == CourseType.THEORY)
        && (marksSubmissionStatus.getStatus() == CourseMarksSubmissionStatus.NOT_SUBMITTED)) {
      MutableMarksSubmissionStatus mutable = marksSubmissionStatus.edit();
      mutable.setTotalPart(requestedStatusDTO.getTotal_part());
      mutable.setPartATotal(requestedStatusDTO.getPart_a_total());
      mutable.setPartBTotal(requestedStatusDTO.getPart_b_total());
      mutable.update();

      marksSubmissionStatus =
          mMarksSubmissionStatusManager.get(requestedStatusDTO.getSemesterId(), requestedStatusDTO.getCourseId(),
              requestedStatusDTO.getExamType());
    }

    if(action.equalsIgnoreCase("submit")) {
      gradeSubmissionService.validateGradeSubmission(actingRoleForCurrentUser, requestedStatusDTO,
          marksSubmissionStatus, gradeList, action);

      MutableMarksSubmissionStatus mutable = marksSubmissionStatus.edit();
      mutable.setStatus(CourseMarksSubmissionStatus.WAITING_FOR_SCRUTINY);
      mutable.update();

      /*
       * marksSubmissionStatus =
       * mMarksSubmissionStatusManager.get(requestedStatusDTO.getSemesterId(),
       * requestedStatusDTO.getCourseId(), requestedStatusDTO.getExamType());
       */

      getContentManager().insertGradeLog(userId, actingRoleForCurrentUser, marksSubmissionStatus,
          CourseMarksSubmissionStatus.WAITING_FOR_SCRUTINY, gradeList);

      getContentManager().insertMarksSubmissionStatusLog(userId, actingRoleForCurrentUser, marksSubmissionStatus,
          CourseMarksSubmissionStatus.WAITING_FOR_SCRUTINY);

      String notificationConsumer =
          gradeSubmissionService.getUserIdForNotification(marksSubmissionStatus.getSemesterId(),
              marksSubmissionStatus.getCourseId(), CourseMarksSubmissionStatus.WAITING_FOR_SCRUTINY);

      gradeSubmissionService.sendNotification(notificationConsumer, marksSubmissionStatus.getCourse().getNo());
    }

    getContentManager().saveGradeSheet(marksSubmissionStatus, gradeList);
    Response.ResponseBuilder builder = Response.created(null);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  @Transactional
  public Response updateGradeStatus(final JsonObject pJsonObject) {

    String action = pJsonObject.getString("action");
    String userRole = pJsonObject.getString("role");
    String userId = SecurityUtils.getSubject().getPrincipal().toString();

    MarksSubmissionStatusDto requestedStatusDTO = new MarksSubmissionStatusDto();
    getBuilder().build(requestedStatusDTO, pJsonObject);

    MarksSubmissionStatus marksSubmissionStatus
        = mMarksSubmissionStatusManager.get(requestedStatusDTO.getSemesterId(),
        requestedStatusDTO.getCourseId(),
        requestedStatusDTO.getExamType());

    String actingRoleForCurrentUser
        = gradeSubmissionService.getActorForCurrentUser(userId,
        userRole,
        marksSubmissionStatus.getSemesterId(),
        marksSubmissionStatus.getCourseId());

    ArrayList<List<StudentGradeDto>> gradeList
        = getBuilder().buildForRecheckApproveGrade(action, actingRoleForCurrentUser, pJsonObject);

    CourseMarksSubmissionStatus nextStatus
        = gradeSubmissionService.getCourseMarksSubmissionNextStatus(actingRoleForCurrentUser, action,
        marksSubmissionStatus.getStatus());

    List<StudentGradeDto> recheckList = null;
    List<StudentGradeDto> approveList = null;

    //Need to improve this if else logic here....
    if (action.equals("save")) { // Scrutinizer, Head, CoE  Press the Save Button
      getContentManager().updateGradeStatus_Save(marksSubmissionStatus, gradeList.get(0), gradeList.get(1));
    } else {
      if (action.equals("recheck")) {  //Scrutinizer, Head, CoE Press the Recheck Button
        recheckList = gradeList.get(0);
        getContentManager().updateGradeStatus_Recheck(marksSubmissionStatus, gradeList.get(0), gradeList.get(1));
      } else if (action.equals("approve")) { //Scrutinizer, Head, CoE Press the Approve Button
        approveList = gradeList.get(1);
        getContentManager().updateGradeStatus_Approve(marksSubmissionStatus, gradeList.get(0), gradeList.get(1));
      } else if (action.equals("recheck_request_submit")) { // CoE Press the "Send Recheck Request to VC" Button
        recheckList = gradeList.get(0);
        getContentManager().updateGradeStatus_Recheck(marksSubmissionStatus, gradeList.get(0), gradeList.get(1));
      }

      MutableMarksSubmissionStatus mutable = marksSubmissionStatus.edit();
      mutable.setStatus(nextStatus);
      if(nextStatus == CourseMarksSubmissionStatus.REQUESTED_FOR_RECHECK_BY_COE) {
        mutable.setLastSubmissionDatePrep(requestedStatusDTO.getLastSubmissionDatePrep());
        mutable.setLastSubmissionDateScr(requestedStatusDTO.getLastSubmissionDateScr());
        mutable.setLastSubmissionDateHead(requestedStatusDTO.getLastSubmissionDateHead());
      }
      mutable.update();

      if (recheckList != null) recheckList.stream().forEach(g -> {
        g.setRecheckStatusId(1);
      });
      if (approveList != null) approveList.stream().forEach(g -> {
        g.setRecheckStatusId(0);
      });
      List<StudentGradeDto> allGradeList =
          Stream.concat(recheckList == null ? Stream.empty() : recheckList.stream(), approveList == null ? Stream.empty() : approveList.stream()).collect(Collectors.toList());

      gradeSubmissionService.validateGradeSubmission(actingRoleForCurrentUser, requestedStatusDTO, marksSubmissionStatus, allGradeList, action);

/*      marksSubmissionStatus
          = mMarksSubmissionStatusManager.get(requestedStatusDTO.getSemesterId(),
          requestedStatusDTO.getCourseId(),
          requestedStatusDTO.getExamType());*/

      String notificationConsumer = gradeSubmissionService.getUserIdForNotification(marksSubmissionStatus.getSemesterId(), marksSubmissionStatus.getCourseId(), nextStatus);

      if (!StringUtils.isEmpty(userId)) {
        gradeSubmissionService.sendNotification(notificationConsumer, marksSubmissionStatus.getCourse().getNo());
      }

      getContentManager().insertGradeLog(userId, actingRoleForCurrentUser, marksSubmissionStatus, nextStatus, allGradeList);
      getContentManager().insertMarksSubmissionStatusLog(userId, actingRoleForCurrentUser, marksSubmissionStatus, nextStatus);

    }

    Response.ResponseBuilder builder = Response.created(null);
    builder.status(Response.Status.CREATED);

    return builder.build();
  }

  @Transactional
  public Response recheckRequestApprove(final JsonObject pJsonObject) {

    String action = pJsonObject.getString("action");
    String userRole = pJsonObject.getString("role");
    String userId = SecurityUtils.getSubject().getPrincipal().toString();

    MarksSubmissionStatusDto requestedStatusDTO = new MarksSubmissionStatusDto();
    getBuilder().build(requestedStatusDTO, pJsonObject);

    MarksSubmissionStatus marksSubmissionStatus =
        mMarksSubmissionStatusManager.get(requestedStatusDTO.getSemesterId(), requestedStatusDTO.getCourseId(),
            requestedStatusDTO.getExamType());

    String actingRoleForCurrentUser =
        gradeSubmissionService.getActorForCurrentUser(userId, userRole, marksSubmissionStatus.getSemesterId(),
            marksSubmissionStatus.getCourseId());

    CourseMarksSubmissionStatus nextStatus =
        gradeSubmissionService.getCourseMarksSubmissionNextStatus(actingRoleForCurrentUser, action,
            marksSubmissionStatus.getStatus());

    // int current_course_status = pJsonObject.getInt("course_current_status");

    // Need to improve this if else logic here....
    if(action.equals("recheck_request_rejected")) {// VC sir Rejected the whole recheck request
      getContentManager().rejectRecheckRequest(marksSubmissionStatus);
    }
    else if(action.equals("recheck_request_approved")) { // VC Sir Approved the whole recheck
                                                         // request
      getContentManager().approveRecheckRequest(marksSubmissionStatus);
    }

    MutableMarksSubmissionStatus mutable = marksSubmissionStatus.edit();
    mutable.setStatus(nextStatus);
    mutable.update();

    // marksSubmissionStatus =
    // mMarksSubmissionStatusManager.get(requestedStatusDTO.getSemesterId(),
    // requestedStatusDTO.getCourseId(), requestedStatusDTO.getExamType());

    // Need to put log here....
    String notificationConsumer =
        gradeSubmissionService.getUserIdForNotification(marksSubmissionStatus.getSemesterId(),
            marksSubmissionStatus.getCourseId(), nextStatus);

    if(!userId.equals("")) {
      gradeSubmissionService.sendNotification(notificationConsumer, marksSubmissionStatus.getCourse().getNo());
    }

    Response.ResponseBuilder builder = Response.created(null);
    builder.status(Response.Status.CREATED);

    return builder.build();
  }

  public JsonObject getChartData(final Integer pSemesterId, final String pCourseId, final Integer pExamType,
      final Integer courseType) {

    User user = mUserManager.get(SecurityUtils.getSubject().getPrincipal().toString());
    List<GradeChartDataDto> examGradeStatusList =
        getContentManager().getChartData(pSemesterId, pCourseId, ExamType.get(pExamType), CourseType.get(courseType));

    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();

    JsonReader jsonReader;
    JsonObject object1;

    for(GradeChartDataDto chartDto : examGradeStatusList) {
      jsonReader = Json.createReader(new StringReader(chartDto.toString()));
      object1 = jsonReader.readObject();
      jsonReader.close();
      children.add(object1);
    }
    object.add("entries", children);

    return object.build();
  }

  public JsonObject getGradeSubmissionDeadline(final Integer pSemesterId, final ExamType pExamType,
      final String pExamDate, final CourseType pCourseType, final UriInfo pUriInfo) {
    String userId = SecurityUtils.getSubject().getPrincipal().toString();
    User user = mUserManager.get(userId);
    Employee employee = mEmployeeManager.get(user.getEmployeeId());
    List<MarksSubmissionStatusDto> marksSubmissionStatusDtoList = new ArrayList<>();
    /*
     * int size = getContentManager().checkSize(pSemesterId, pExamType, pExamDate);
     * 
     * if(size == 0) { getContentManager().createGradeSubmissionStatus(pSemesterId, pExamType,
     * pExamDate); marksSubmissionStatusDtoList =
     * getContentManager().getGradeSubmissionDeadLine(pSemesterId, pExamType, pExamDate,
     * employee.getDepartment().getId(), pCourseType); } else { marksSubmissionStatusDtoList =
     * mManager.getGradeSubmissionDeadLine(pSemesterId, pExamType, pExamDate, employee
     * .getDepartment().getId(), pCourseType); }
     */

    marksSubmissionStatusDtoList =
        mManager.getGradeSubmissionDeadLine(pSemesterId, pExamType, pExamDate, employee.getDepartment().getId(),
            pCourseType);

    Collections.sort(marksSubmissionStatusDtoList, new Comparator<MarksSubmissionStatusDto>() {

      @Override
      public int compare(MarksSubmissionStatusDto o1, MarksSubmissionStatusDto o2) {
        int c;
        c = o1.getProgramShortName().compareTo(o2.getProgramShortName());
        if(c == 0) {
          c = o1.getCourseNo().compareTo(o2.getCourseNo());
        }
        return c;
      }
    });

    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();

    for(MarksSubmissionStatusDto marksSubmissionStatusDto : marksSubmissionStatusDtoList) {
      PersistentExamGrade examGrade = new PersistentExamGrade();
      examGrade.setExamDate(marksSubmissionStatusDto.getExamDate());
      examGrade.setProgramShortName(marksSubmissionStatusDto.getProgramShortName());
      examGrade.setCourseId(marksSubmissionStatusDto.getCourseId());
      examGrade.setCourseNo(marksSubmissionStatusDto.getCourseNo());
      examGrade.setCourseTitle(marksSubmissionStatusDto.getCourseTitle());
      examGrade.setCourseCreditHour(marksSubmissionStatusDto.getCourseCreditHour());
      examGrade.setTotalStudents(marksSubmissionStatusDto.getTotalStudents());
      if(marksSubmissionStatusDto.getLastSubmissionDatePrep() != null) {
        examGrade.setLastSubmissionDatePrep(marksSubmissionStatusDto.getLastSubmissionDatePrep());
      }
      if(marksSubmissionStatusDto.getLastSubmissionDateScr() != null) {
        examGrade.setLastSubmissionDateScr(marksSubmissionStatusDto.getLastSubmissionDateScr());
      }
      if(marksSubmissionStatusDto.getLastSubmissionDateHead() != null) {
        examGrade.setLastSubmissionDateHead(marksSubmissionStatusDto.getLastSubmissionDateHead());
      }
      examGrade.setId(marksSubmissionStatusDto.getId());
      children.add(toJson(examGrade, pUriInfo, null));
    }

    object.add("entries", children);
    return object.build();
  }

  public Response updateGradeSubmissionDeadLine(JsonObject pJsonObject, UriInfo pUriInfo) {

    JsonArray entries = pJsonObject.getJsonArray("entries");
    // List<MutableMarksSubmissionStatus> deadlineList = new ArrayList<>();
    List<MutableMarksSubmissionStatus> deadlineList = new ArrayList<>();
    boolean isSemesterValid = true;
    for(int i = 0; i < entries.size(); i++) {
      JsonObject jsonObject = entries.getJsonObject(i);
      MutableExamGrade examGrade = new PersistentExamGrade();
      LocalCache localCache = new LocalCache();
      getBuilder().build(examGrade, jsonObject, localCache);

      if(i == 0) {
        Semester semester = mSemesterManager.get(examGrade.getSemesterId());
        if(semester.getStatus().getValue() != 1) {
          isSemesterValid = false;
          break;
        }
      }
      MutableMarksSubmissionStatus marksSubmissionStatus =
          mMarksSubmissionStatusManager
              .get(examGrade.getSemesterId(), examGrade.getCourseId(), examGrade.getExamType()).edit();
      marksSubmissionStatus.setLastSubmissionDateHead(examGrade.getLastSubmissionDateHead());
      marksSubmissionStatus.setLastSubmissionDatePrep(examGrade.getLastSubmissionDatePrep());
      marksSubmissionStatus.setLastSubmissionDateScr(examGrade.getLastSubmissionDateScr());
      deadlineList.add(marksSubmissionStatus);
    }

    if(isSemesterValid) {
      mMarksSubmissionStatusManager.update(deadlineList);
    }

    URI contextURI = null;
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  // Get Course-wise marks submission status log
  public JsonObject getMarksSubmissionLogs(final Integer pSemesterId, final String pCourseId, final Integer pExamType) {

    List<MarksSubmissionStatusLogDto> logList =
        getContentManager().getMarksSubmissionLogs(pSemesterId, pCourseId, pExamType);
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    JsonReader jsonReader;
    JsonObject jsonObject;

    for(MarksSubmissionStatusLogDto log : logList) {
      jsonReader = Json.createReader(new StringReader(log.toString()));
      jsonObject = jsonReader.readObject();
      jsonReader.close();
      children.add(jsonObject);
    }
    object.add("entries", children);
    return object.build();
  }

  // Get Student-wise log for Grade Submission
  public JsonObject getMarksLogs(final Integer pSemesterId, final String pCourseId, final ExamType pExamType,
      final String pStudentId) {
    MarksSubmissionStatus marksSubmissionStatus = mMarksSubmissionStatusManager.get(pSemesterId, pCourseId, pExamType);

    List<MarksLogDto> logList =
        getContentManager().getMarksLogs(pSemesterId, pCourseId, pExamType, pStudentId,
            marksSubmissionStatus.getCourse().getCourseType());
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    JsonReader jsonReader;
    JsonObject jsonObject;

    for(MarksLogDto log : logList) {
      jsonReader = Json.createReader(new StringReader(log.toString()));
      jsonObject = jsonReader.readObject();
      jsonReader.close();
      children.add(jsonObject);
    }
    object.add("entries", children);
    return object.build();
  }

  // Marks Submission Statistics
  public JsonObject getMarksSubmissionStat(Integer pProgramType, Integer pSemesterId, String pDeptId,
      Integer pExamType, String pStatus) throws Exception {
    List<MarksSubmissionStatDto> examGradeStatusList =
        getContentManager().getMarksSubmissionStat(pProgramType, pSemesterId, pDeptId, pExamType, pStatus);
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();

    JsonReader jsonReader;
    JsonObject object1;

    for(MarksSubmissionStatDto statDto : examGradeStatusList) {
      jsonReader = Json.createReader(new StringReader(statDto.toString()));
      object1 = jsonReader.readObject();
      jsonReader.close();
      children.add(object1);
    }
    object.add("entries", children);

    return object.build();
  }

}
