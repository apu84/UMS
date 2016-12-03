package org.ums.common.academic.resource.helper;

import org.apache.commons.lang.NotImplementedException;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.ums.cache.LocalCache;
import org.ums.common.builder.ExamGradeBuilder;
import org.ums.domain.model.dto.*;
import org.ums.domain.model.immutable.ExamGrade;
import org.ums.domain.model.immutable.MarksSubmissionStatus;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.User;
import org.ums.domain.model.mutable.MutableExamGrade;
import org.ums.domain.model.mutable.MutableMarksSubmissionStatus;
import org.ums.enums.CourseMarksSubmissionStatus;
import org.ums.enums.CourseType;
import org.ums.enums.ExamType;
import org.ums.manager.ExamGradeManager;
import org.ums.manager.MarksSubmissionStatusManager;
import org.ums.manager.SemesterManager;
import org.ums.manager.UserManager;
import org.ums.persistent.model.PersistentExamGrade;
import org.ums.resource.ResourceHelper;
import org.ums.services.academic.GradeSubmissionService;

import javax.json.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.StringReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class GradeSubmissionResourceHelper extends
    ResourceHelper<ExamGrade, MutableExamGrade, Object> {

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
  private MarksSubmissionStatusManager mMarksSubmissionStatusManager;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) {
    throw new NotImplementedException(
        "Post method is not implemented for GradeSubmissionResourceHelper");
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

  public JsonObject getGradeList(final String pRequestedRoleId, final Integer pSemesterId,
      final String pCourseId, final ExamType pExamType) {

    MarksSubmissionStatusDto marksSubmissionStatusDto =
        getContentManager().getMarksSubmissionStatus(pSemesterId, pCourseId, pExamType);
    String currentActor =
        gradeSubmissionService.getActorForCurrentUser(SecurityUtils.getSubject().getPrincipal()
            .toString(), pRequestedRoleId, pSemesterId, pCourseId);

    JsonReader jsonReader =
        Json.createReader(new StringReader(marksSubmissionStatusDto.toString()));
    JsonObject jsonObject = jsonReader.readObject();
    JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
    jsonReader.close();
    objectBuilder.add("part_info", jsonObject);
    objectBuilder.add("current_actor", currentActor);
    objectBuilder.add("current_course_status", marksSubmissionStatusDto.getStatusId());

    gradeSubmissionService.prepareGradeGroups(
        objectBuilder,
        getContentManager().getAllGrades(pSemesterId, pCourseId, pExamType,
            marksSubmissionStatusDto.getCourseType()),
        CourseMarksSubmissionStatus.values()[marksSubmissionStatusDto.getStatusId()], currentActor);

    return objectBuilder.build();
  }

  public JsonObject getGradeSubmissionStatus(final Integer pSemesterId, final Integer pExamType,
      final Integer pProgramId, final Integer pYearSemester, final String deptId,
      final String pUserRole, final int pStatus) {
    User user = mUserManager.get(SecurityUtils.getSubject().getPrincipal().toString());
    Integer year = 0;
    Integer semester = 0;

    if(pYearSemester != 0) {
      year = Integer.valueOf((pYearSemester.toString()).charAt(0) + "");
      semester = Integer.valueOf((pYearSemester.toString()).charAt(1) + "");
    }
    List<MarksSubmissionStatusDto> examGradeStatusList =
        getContentManager().getMarksSubmissionStatus(pSemesterId, pExamType, pProgramId, year,
            semester, user.getEmployeeId(), deptId, pUserRole, pStatus);
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    JsonReader jsonReader;
    JsonObject jsonObject;

    for(MarksSubmissionStatusDto statusDto : examGradeStatusList) {
      jsonReader = Json.createReader(new StringReader(statusDto.toString()));
      jsonObject = jsonReader.readObject();
      jsonReader.close();
      children.add(jsonObject);
    }
    object.add("entries", children);
    return object.build();
  }

  // This method will only be used by Grade Sheet Preparer during saving or submitting grades.
  @Transactional(rollbackFor = Exception.class)
  public Response saveGradeSheet(final JsonObject pJsonObject) {
    List<StudentGradeDto> gradeList = getBuilder().build(pJsonObject);
    MarksSubmissionStatusDto requestedStatusDTO = new MarksSubmissionStatusDto();
    getBuilder().build(requestedStatusDTO, pJsonObject);

    String action = pJsonObject.getString("action");
    String userRole = pJsonObject.getString("role");
    String userId = SecurityUtils.getSubject().getPrincipal().toString();

    MarksSubmissionStatus marksSubmissionStatus =
        mMarksSubmissionStatusManager.get(requestedStatusDTO.getSemesterId(),
            requestedStatusDTO.getCourseId(), requestedStatusDTO.getExamType());

    String actingRoleForCurrentUser =
        gradeSubmissionService.getActorForCurrentUser(userId, userRole,
            marksSubmissionStatus.getSemesterId(), marksSubmissionStatus.getCourseId());

    if((marksSubmissionStatus.getCourse().getCourseType() == CourseType.THEORY)
        && (marksSubmissionStatus.getStatus() == CourseMarksSubmissionStatus.NOT_SUBMITTED)) {
      MutableMarksSubmissionStatus mutable = marksSubmissionStatus.edit();
      mutable.setTotalPart(requestedStatusDTO.getTotal_part());
      mutable.setPartATotal(requestedStatusDTO.getPart_a_total());
      mutable.setPartBTotal(requestedStatusDTO.getPart_b_total());
      mutable.commit(true);

      marksSubmissionStatus =
          mMarksSubmissionStatusManager.get(requestedStatusDTO.getSemesterId(),
              requestedStatusDTO.getCourseId(), requestedStatusDTO.getExamType());
    }

    if(action.equalsIgnoreCase("submit")) {
      gradeSubmissionService.validateGradeSubmission(actingRoleForCurrentUser, requestedStatusDTO,
          marksSubmissionStatus, gradeList, action);

      MutableMarksSubmissionStatus mutable = marksSubmissionStatus.edit();
      mutable.setStatus(CourseMarksSubmissionStatus.WAITING_FOR_SCRUTINY);
      mutable.commit(true);

      /*
       * marksSubmissionStatus =
       * mMarksSubmissionStatusManager.get(requestedStatusDTO.getSemesterId(),
       * requestedStatusDTO.getCourseId(), requestedStatusDTO.getExamType());
       */

      getContentManager().insertGradeLog(userId, actingRoleForCurrentUser, marksSubmissionStatus,
          CourseMarksSubmissionStatus.WAITING_FOR_SCRUTINY, gradeList);

      getContentManager().insertMarksSubmissionStatusLog(userId, actingRoleForCurrentUser,
          marksSubmissionStatus, CourseMarksSubmissionStatus.WAITING_FOR_SCRUTINY);

      String notificationConsumer =
          gradeSubmissionService
              .getUserIdForNotification(marksSubmissionStatus.getSemesterId(),
                  marksSubmissionStatus.getCourseId(),
                  CourseMarksSubmissionStatus.WAITING_FOR_SCRUTINY);

      gradeSubmissionService.sendNotification(notificationConsumer, marksSubmissionStatus
          .getCourse().getNo());
    }

    getContentManager().saveGradeSheet(marksSubmissionStatus, gradeList);
    Response.ResponseBuilder builder = Response.created(null);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  @Transactional(rollbackFor = Exception.class)
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
      mutable.commit(true);

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

      getContentManager().insertGradeLog(userId, actingRoleForCurrentUser, marksSubmissionStatus, CourseMarksSubmissionStatus.WAITING_FOR_SCRUTINY, allGradeList);
      getContentManager().insertMarksSubmissionStatusLog(userId, actingRoleForCurrentUser, marksSubmissionStatus, nextStatus);

    }

    Response.ResponseBuilder builder = Response.created(null);
    builder.status(Response.Status.CREATED);

    return builder.build();
  }

  @Transactional(rollbackFor = Exception.class)
  public Response recheckRequestApprove(final JsonObject pJsonObject) {

    String action = pJsonObject.getString("action");
    String userRole = pJsonObject.getString("role");
    String userId = SecurityUtils.getSubject().getPrincipal().toString();

    MarksSubmissionStatusDto requestedStatusDTO = new MarksSubmissionStatusDto();
    getBuilder().build(requestedStatusDTO, pJsonObject);

    MarksSubmissionStatus marksSubmissionStatus =
        mMarksSubmissionStatusManager.get(requestedStatusDTO.getSemesterId(),
            requestedStatusDTO.getCourseId(), requestedStatusDTO.getExamType());

    String actingRoleForCurrentUser =
        gradeSubmissionService.getActorForCurrentUser(userId, userRole,
            marksSubmissionStatus.getSemesterId(), marksSubmissionStatus.getCourseId());

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
    mutable.commit(true);

    // marksSubmissionStatus =
    // mMarksSubmissionStatusManager.get(requestedStatusDTO.getSemesterId(),
    // requestedStatusDTO.getCourseId(), requestedStatusDTO.getExamType());

    // Need to put log here....
    String notificationConsumer =
        gradeSubmissionService.getUserIdForNotification(marksSubmissionStatus.getSemesterId(),
            marksSubmissionStatus.getCourseId(), nextStatus);

    if(!userId.equals("")) {
      gradeSubmissionService.sendNotification(notificationConsumer, marksSubmissionStatus
          .getCourse().getNo());
    }

    Response.ResponseBuilder builder = Response.created(null);
    builder.status(Response.Status.CREATED);

    return builder.build();
  }

  public JsonObject getChartData(final Integer pSemesterId, final String pCourseId,
      final Integer pExamType, final Integer courseType) {

    User user = mUserManager.get(SecurityUtils.getSubject().getPrincipal().toString());
    List<GradeChartDataDto> examGradeStatusList =
        getContentManager().getChartData(pSemesterId, pCourseId, ExamType.get(pExamType),
            CourseType.get(courseType));

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
      final String pExamDate, final UriInfo pUriInfo) {

    List<MarksSubmissionStatusDto> marksSubmissionStatusDtoList = new ArrayList<>();
    int size = getContentManager().checkSize(pSemesterId, pExamType, pExamDate);

    if(size == 0) {
      getContentManager().insertGradeSubmissionDeadLineInfo(pSemesterId, pExamType, pExamDate);
      marksSubmissionStatusDtoList =
          getContentManager().getGradeSubmissionDeadLine(pSemesterId, pExamType, pExamDate);
    }
    else {
      marksSubmissionStatusDtoList =
          mManager.getGradeSubmissionDeadLine(pSemesterId, pExamType, pExamDate);
    }

    Collections.sort(marksSubmissionStatusDtoList, new Comparator<MarksSubmissionStatusDto>() {

      @Override
      public int compare(MarksSubmissionStatusDto o1, MarksSubmissionStatusDto o2) {
        int c;
        c = o1.getProgramShortname().compareTo(o2.getProgramShortname());
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
      examGrade.setProgramShortName(marksSubmissionStatusDto.getProgramShortname());
      examGrade.setCourseId(marksSubmissionStatusDto.getCourseId());
      examGrade.setCourseNo(marksSubmissionStatusDto.getCourseNo());
      examGrade.setCourseTitle(marksSubmissionStatusDto.getCourseTitle());
      examGrade.setCourseCreditHour(marksSubmissionStatusDto.getCourseCreditHour());
      examGrade.setTotalStudents(marksSubmissionStatusDto.getTotalStudents());
      if(marksSubmissionStatusDto.getLastSubmissionDate() != null) {
        examGrade.setLastSubmissionDate(marksSubmissionStatusDto.getLastSubmissionDate());
      }
      examGrade.setId(marksSubmissionStatusDto.getId());
      children.add(toJson(examGrade, pUriInfo, null));
    }

    object.add("entries", children);
    return object.build();
  }

  public Response updateGradeSubmissionDeadLine(JsonObject pJsonObject, UriInfo pUriInfo) {

    JsonArray entries = pJsonObject.getJsonArray("entries");
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

      MarksSubmissionStatus marksSubmissionStatus =
          mMarksSubmissionStatusManager.get(examGrade.getId());
      MutableMarksSubmissionStatus mutable = marksSubmissionStatus.edit();
      mutable.setLastSubmissionDate(examGrade.getLastSubmissionDate());
      deadlineList.add(mutable);
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
  public JsonObject getMarksSubmissionLogs(final Integer pSemesterId, final String pCourseId,
      final Integer pExamType) {

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
  public JsonObject getMarksLogs(final Integer pSemesterId, final String pCourseId,
      final ExamType pExamType, final String pStudentId) {
    MarksSubmissionStatus marksSubmissionStatus =
        mMarksSubmissionStatusManager.get(pSemesterId, pCourseId, pExamType);

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
  public JsonObject getMarksSubmissionStat(Integer pProgramType, Integer pSemesterId,
      String pDeptId, Integer pExamType, String pStatus) throws Exception {
    List<MarksSubmissionStatDto> examGradeStatusList =
        getContentManager().getMarksSubmissionStat(pProgramType, pSemesterId, pDeptId, pExamType,
            pStatus);
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
