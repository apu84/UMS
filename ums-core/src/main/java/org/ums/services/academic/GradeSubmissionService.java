package org.ums.services.academic;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.domain.model.dto.MarksSubmissionStatusDto;
import org.ums.domain.model.dto.StudentGradeDto;
import org.ums.domain.model.immutable.MarksSubmissionStatus;
import org.ums.domain.model.immutable.Notification;
import org.ums.usermanagement.user.User;
import org.ums.enums.*;
import org.ums.exceptions.ValidationException;
import org.ums.manager.ExamGradeManager;
import org.ums.usermanagement.user.UserManager;
import org.ums.message.MessageResource;
import org.ums.services.NotificationGenerator;
import org.ums.services.Notifier;
import org.ums.services.UtilsService;
import org.ums.util.Constants;
import org.ums.util.UmsUtils;

import java.io.StringReader;
import java.util.*;

@Component("gradeSubmissionService")
public class GradeSubmissionService {
  private Logger mLogger = LoggerFactory.getLogger(GradeSubmissionService.class);
  @Autowired
  private ExamGradeManager mManager;
  @Autowired
  private UserManager mUserManager;
  @Autowired
  private UtilsService mUtilsService;
  @Autowired
  private NotificationGenerator mNotificationGenerator;

  public String getActingRoleForCourse(CourseMarksSubmissionStatus currentStatus) {
    String actingRole = "";
    switch(currentStatus) {
      case NOT_SUBMITTED:
      case REQUESTED_FOR_RECHECK_BY_SCRUTINIZER:
      case REQUESTED_FOR_RECHECK_BY_HEAD:
      case REQUESTED_FOR_RECHECK_BY_COE:
        actingRole = Constants.GRADE_PREPARER;
        break;
      case WAITING_FOR_SCRUTINY:
        actingRole = Constants.GRADE_SCRUTINIZER;
        break;
      case WAITING_FOR_HEAD_APPROVAL:
        actingRole = Constants.HEAD;
        break;
      case WAITING_FOR_COE_APPROVAL:
      case ACCEPTED_BY_COE:
        actingRole = Constants.COE;
        break;
      case WAITING_FOR_RECHECK_REQUEST_APPROVAL:
        actingRole = Constants.VC;
        break;
    }
    return actingRole;
  }

  public String getActorForCurrentUser(String userId, String requestedRole, int semesterId, String courseId) {
    String role = "Invalid";
    List<String> roleList;
    switch(requestedRole) {
      case "T":
        User user = mUserManager.get(userId);
        roleList = mManager.getRoleForTeacher(user.getEmployeeId(), semesterId, courseId);
        if(roleList.size() != 0)
          role = roleList.get(0);
        break;
      case "H":
        roleList = mManager.getRoleForHead(userId);
        if(roleList.size() != 0)
          role = roleList.get(0);
        break;
      case "C":
        roleList = mManager.getRoleForCoE(userId);
        if(roleList.size() != 0)
          role = roleList.get(0);
        break;
      case "V":
        roleList = mManager.getRoleForVC(userId);
        if(roleList.size() != 0)
          role = roleList.get(0);
        break;
      default:
        throw new ValidationException("Unauthorized Access Detected.");
    }
    if(!Arrays.asList(Constants.validRolesForGradeAccess).contains(role))
      throw new ValidationException("Unauthorized Access Detected.");
    else
      return role;
  }

  public void validatePartInfo(final Integer pTotalPart, final Integer pPartATotal, final Integer pPartBTotal) {
    if(pTotalPart != 1 && pTotalPart != 2)
      throw new ValidationException("Part Information is wrong.");

    if(pTotalPart == 1 && (pPartATotal == 0 || pPartATotal > 70)) {
      throw new ValidationException("Part Information is wrong.");
    }
    else if(pTotalPart == 2) {
      if(pPartATotal == 0 || pPartBTotal == 0 || pPartATotal + pPartBTotal != 70) {
        throw new ValidationException("Part Information is wrong.");
      }
    }
  }

  private void validateGradeSubmissionDeadline(int pSemesterId, String pCourseId, ExamType pExamType, String pRole,
      Date pLastDateForPreparer, Date pLastDateForScrutinizer, Date pLastDateForHead, Date pLastDateForCoE)
      throws ValidationException {
    Date currentDate = new Date();
    currentDate = UmsUtils.modifyTimeToZeroSecondOfTheClock(currentDate);
    Set<Integer> statusList;
    Integer recheckStatus;
    Date overriddenDeadline;
    switch(pRole) {
      case Constants.GRADE_PREPARER:
        if(pLastDateForPreparer == null) {
          return;
        }
        statusList = new HashSet<>(Arrays.asList(2, 4, 6));
        recheckStatus = mManager.getOverriddenDeadline(pSemesterId, pCourseId, pExamType, statusList);
        overriddenDeadline =
            recheckStatus == null ? pLastDateForPreparer : getOverriddenDeadline(recheckStatus,
                pLastDateForScrutinizer, pLastDateForHead, pLastDateForCoE);
        if(overriddenDeadline != null && currentDate.after(overriddenDeadline)) {
          String courseInfo =
              String.format("SemesterId: %d; Course Id: %s; ExamType: %s; Role: %s; Date: %s", pSemesterId, pCourseId,
                  pExamType.getLabel(), pRole, overriddenDeadline.toString());
          throw new ValidationException("Grade Submission Deadline is Over. " + courseInfo);
        }
        break;
      case Constants.GRADE_SCRUTINIZER:
        if(pLastDateForScrutinizer == null) {
          return;
        }
        statusList = new HashSet<>(Arrays.asList(4, 6));
        recheckStatus = mManager.getOverriddenDeadline(pSemesterId, pCourseId, pExamType, statusList);
        overriddenDeadline =
            recheckStatus == null ? pLastDateForScrutinizer : getOverriddenDeadline(recheckStatus,
                pLastDateForScrutinizer, pLastDateForHead, pLastDateForCoE);
        if(overriddenDeadline != null && currentDate.after(overriddenDeadline)) {
          String courseInfo =
              String.format("SemesterId: %d; Course Id: %s; ExamType: %s; Role: %s; Date: %s", pSemesterId, pCourseId,
                  pExamType.getLabel(), pRole, overriddenDeadline.toString());
          throw new ValidationException("Grade Submission Deadline is Over. " + courseInfo);
        }
        break;
      case Constants.HEAD:
        if(pLastDateForHead == null) {
          return;
        }
        statusList = new HashSet<>(Arrays.asList(6));
        recheckStatus = mManager.getOverriddenDeadline(pSemesterId, pCourseId, pExamType, statusList);
        overriddenDeadline =
            recheckStatus == null ? pLastDateForHead : getOverriddenDeadline(recheckStatus, pLastDateForScrutinizer,
                pLastDateForHead, pLastDateForCoE);
        if(overriddenDeadline != null && currentDate.after(overriddenDeadline)) {
          String courseInfo =
              String.format("SemesterId: %d; Course Id: %s; ExamType: %s; Role: %s; Date: %s", pSemesterId, pCourseId,
                  pExamType.getLabel(), pRole, overriddenDeadline.toString());
          throw new ValidationException("Grade Submission Deadline is Over. " + courseInfo);
        }
        break;
    }

  }

  private Date getOverriddenDeadline(Integer courseMarksSubmissionStatus, Date pLastDateForScrutinizer,
      Date pLastDateForHead, Date pLastDateForCoE) {
    switch(courseMarksSubmissionStatus) {
      case 2:
        return pLastDateForScrutinizer;
      case 4:
        return pLastDateForHead;
      case 6:
        return pLastDateForCoE;
    }
    return null;
  }

  public void validateGradeSubmission(String actingRoleForCurrentUser, MarksSubmissionStatusDto requestedStatusDTO,
      MarksSubmissionStatus actualStatus, List<StudentGradeDto> gradeList, String operation) {
    // operation can be either submit OR save
    // Checking the role, requested from client side is valid for the user who is trying to do some
    // operation on grades
    // Role Validation
    mLogger.debug("[{}] Course current status: {}", SecurityUtils.getSubject().getPrincipal().toString(), actualStatus
        .getStatus().getLabel());
    String actualActingRole = getActingRoleForCourse(actualStatus.getStatus());
    if(!actingRoleForCurrentUser.equalsIgnoreCase(actualActingRole)) {
      mLogger.debug("[{}] Current user's role: {}, Role should be: {}", SecurityUtils.getSubject().getPrincipal()
          .toString(), actingRoleForCurrentUser, actualActingRole);
      throw new ValidationException("Sorry, you are not allowed for this operation.");
    }
    // Deadline && Part Info Validation
    if(operation.equals("submit")) {
      // actualStatus.getStatus() == CourseMarksSubmissionStatus.NOT_SUBMITTED &&
      validateGradeSubmissionDeadline(actualStatus.getSemesterId(), actualStatus.getCourseId(),
          actualStatus.getExamType(), actualActingRole, actualStatus.getLastSubmissionDatePrep(),
          actualStatus.getLastSubmissionDateScr(), actualStatus.getLastSubmissionDateHead(),
          actualStatus.getLastSubmissionDateCoe());
      if(actualStatus.getCourse().getCourseType() == CourseType.THEORY)
        validatePartInfo(requestedStatusDTO.getTotal_part(), requestedStatusDTO.getPart_a_total(),
            requestedStatusDTO.getPart_b_total());
    }
    // Submitted Grade Validation
    validateSubmittedGrades(actualStatus, gradeList);
  }

  private void validateSubmittedGrades(MarksSubmissionStatus actualStatus, List<StudentGradeDto> gradeList) {

    // Validate all the grades been submitted or not
    int totalStudents = mManager.getTotalStudentCount(actualStatus);
    if(actualStatus.getStatus() == CourseMarksSubmissionStatus.NOT_SUBMITTED && gradeList.size() != totalStudents) {
      throw new ValidationException("Wrong number of grades been submitted.");
    }

    // || partInfo.getStatus()==CourseMarksSubmissionStatus.REQUESTED_FOR_RECHECK_BY_SCRUTINIZER
    // || partInfo.getStatus()==CourseMarksSubmissionStatus.REQUESTED_FOR_RECHECK_BY_HEAD ||
    // partInfo.getStatus()==CourseMarksSubmissionStatus.REQUESTED_FOR_RECHECK_BY_COE
    if(actualStatus.getStatus() == CourseMarksSubmissionStatus.NOT_SUBMITTED) {
      boolean hasError = false;
      for(StudentGradeDto gradeDTO : gradeList) {
        try {
          hasError = validateMarks(hasError, gradeDTO, actualStatus);
          if(hasError)
            break;
        } catch(Exception ex) {
          throw new RuntimeException(ex);
        }
      }
      // gradeList.forEach((gradeDTO) -> {
      // try {
      // validateMarks(hasError, gradeDTO, actualStatusDTO);
      // } catch (Exception ex) {
      // throw new RuntimeException(ex);
      // }
      // }
      // );

      if(hasError == true) {
        throw new ValidationException("Grade validation failed.");
      }
    }
  }

  private Boolean validateMarks(Boolean hasError, StudentGradeDto gradeDTO, MarksSubmissionStatus partInfo) {
    if(partInfo.getCourse().getCourseType() == CourseType.THEORY) {
      hasError = validateQuiz(hasError, gradeDTO.getQuiz(), gradeDTO.getRegType());
      hasError = validateClassPerformance(hasError, gradeDTO.getClassPerformance(), gradeDTO.getRegType());
      hasError = validatePartA(hasError, gradeDTO.getPartA(), partInfo.getPartATotal(), gradeDTO.getRegType());
      hasError =
          validatePartB(hasError, gradeDTO.getPartB(), partInfo.getTotalPart(), partInfo.getPartBTotal(),
              gradeDTO.getRegType());
      hasError = validateTheoryTotal(hasError, gradeDTO);
    }
    else if(partInfo.getCourse().getCourseType() == CourseType.SESSIONAL
        || partInfo.getCourse().getCourseType() == CourseType.THESIS_PROJECT) {
      hasError = validateSessionalTotal(hasError, gradeDTO);
    }
    hasError = validateGradeLetter(hasError, gradeDTO);
    if(hasError) {
      mLogger.info("User: {}, Student Id: {}, Course Id:{}, Semester Id: {}, Quiz: {}, RegType:{}, "
          + "Class Performance:{}, Part-A: {}, Part-B:{}, Part-A Total:{}, Part-B Total:{}, Total:{}, Course Type:{}",
          SecurityUtils.getSubject().getPrincipal().toString(), gradeDTO.getStudentId(), partInfo.getCourseId(),
          partInfo.getSemesterId(), gradeDTO.getQuiz(), gradeDTO.getRegType().getLabel(),
          gradeDTO.getClassPerformance(), gradeDTO.getPartA(), gradeDTO.getPartB(), partInfo.getPartATotal(),
          partInfo.getPartBTotal(), gradeDTO.getTotal(), partInfo.getCourse().getCourseType().getLabel());
    }
    return hasError;
  }

  private Boolean validateQuiz(Boolean error, Double quiz, CourseRegType regType) {
    if((regType != CourseRegType.CARRY && regType != CourseRegType.SPECIAL_CARRY) && quiz > 20) {
      error = Boolean.TRUE;
    }
    return error;
  }

  private Boolean validateClassPerformance(Boolean error, Double classPerf, CourseRegType regType) {
    if((regType != CourseRegType.CARRY && regType != CourseRegType.SPECIAL_CARRY) && classPerf > 10) {
      error = Boolean.TRUE;
    }
    return error;
  }

  private Boolean validatePartA(Boolean error, Double partA, Integer partAMax, CourseRegType regType) {
    if(partA > partAMax) {
      error = Boolean.TRUE;
    }
    return error;
  }

  private Boolean validatePartB(Boolean error, Double partB, Integer totalPartCount, Integer partBMax,
      CourseRegType regType) {
    if(totalPartCount == 2 && (partB > partBMax)) {
      error = Boolean.TRUE;
    }
    if(totalPartCount == 1 && (partB != null && partB > 0)) {
      error = Boolean.TRUE;
    }
    return error;
  }

  private Boolean validateTheoryTotal(Boolean error, StudentGradeDto gradeDTO) {
    // System.out.println(gradeDTO.getStudentId());
    // if(gradeDTO.getStudentId().equalsIgnoreCase("120206075")) {
    // System.out.println("--");
    // }
    double aQuiz = gradeDTO.getQuiz() == null ? 0 : gradeDTO.getQuiz();
    double aCPerformance = gradeDTO.getClassPerformance() == null ? 0 : gradeDTO.getClassPerformance();
    double aPartA = gradeDTO.getPartA() == null ? 0 : gradeDTO.getPartA();
    double aPartB = gradeDTO.getPartB() == null ? 0 : gradeDTO.getPartB();

    double calculatedTotal = Math.round(aQuiz + aCPerformance + aPartA + aPartB);
    double calculatedTotalForCarry = Math.round((calculatedTotal / 70) * 100);

    if(gradeDTO.getTotal() > 100
        || (gradeDTO.getTotal() != calculatedTotal && (gradeDTO.getRegType() != CourseRegType.CARRY && gradeDTO
            .getRegType() != CourseRegType.SPECIAL_CARRY))
        || (gradeDTO.getTotal() != calculatedTotalForCarry && (gradeDTO.getRegType() == CourseRegType.CARRY || gradeDTO
            .getRegType() == CourseRegType.SPECIAL_CARRY))) {
      error = Boolean.TRUE;
    }
    return error;
  }

  private Boolean validateSessionalTotal(Boolean error, StudentGradeDto gradeDTO) {
    if(gradeDTO.getTotal() > 100) {
      error = Boolean.TRUE;
    }
    return error;
  }

  private Boolean validateGradeLetter(Boolean error, StudentGradeDto gradeDTO) {
    if(!mUtilsService.getGradeLetter(UmsUtils.round(gradeDTO.getTotal()), gradeDTO.getRegType()).equalsIgnoreCase(
        gradeDTO.getGradeLetter())) {
      error = Boolean.TRUE;
    }
    return error;
  }

  public CourseMarksSubmissionStatus getCourseMarksSubmissionNextStatus(String actor, String action,
      CourseMarksSubmissionStatus currentStatus) {
    CourseMarksSubmissionStatus nextStatus = null;
    if(actor.equalsIgnoreCase("scrutinizer")) {
      if(action.equalsIgnoreCase("recheck") && currentStatus == CourseMarksSubmissionStatus.WAITING_FOR_SCRUTINY)
        nextStatus = CourseMarksSubmissionStatus.REQUESTED_FOR_RECHECK_BY_SCRUTINIZER;
      else if(action.equalsIgnoreCase("approve") && currentStatus == CourseMarksSubmissionStatus.WAITING_FOR_SCRUTINY)
        nextStatus = CourseMarksSubmissionStatus.WAITING_FOR_HEAD_APPROVAL;
    }
    else if(actor.equalsIgnoreCase("head")) {
      if(action.equalsIgnoreCase("recheck") && currentStatus == CourseMarksSubmissionStatus.WAITING_FOR_HEAD_APPROVAL)
        nextStatus = CourseMarksSubmissionStatus.REQUESTED_FOR_RECHECK_BY_HEAD;
      else if(action.equalsIgnoreCase("approve")
          && currentStatus == CourseMarksSubmissionStatus.WAITING_FOR_HEAD_APPROVAL)
        nextStatus = CourseMarksSubmissionStatus.WAITING_FOR_COE_APPROVAL;
    }
    else if(actor.equalsIgnoreCase("coe")) {
      if(action.equalsIgnoreCase("recheck") && currentStatus == CourseMarksSubmissionStatus.WAITING_FOR_COE_APPROVAL)
        nextStatus = CourseMarksSubmissionStatus.REQUESTED_FOR_RECHECK_BY_COE;
      else if(action.equalsIgnoreCase("approve")
          && currentStatus == CourseMarksSubmissionStatus.WAITING_FOR_COE_APPROVAL)
        nextStatus = CourseMarksSubmissionStatus.ACCEPTED_BY_COE;
      else if(action.equalsIgnoreCase("recheckAccepted")
          && currentStatus == CourseMarksSubmissionStatus.ACCEPTED_BY_COE)
        nextStatus = CourseMarksSubmissionStatus.REQUESTED_FOR_RECHECK_BY_COE;
    }
    else if(actor.equalsIgnoreCase("vc")) {
      if(action.equalsIgnoreCase("recheck_request_rejected")
          && currentStatus == CourseMarksSubmissionStatus.WAITING_FOR_RECHECK_REQUEST_APPROVAL)
        nextStatus = CourseMarksSubmissionStatus.ACCEPTED_BY_COE;
      else if(action.equalsIgnoreCase("recheck_request_approved")
          && currentStatus == CourseMarksSubmissionStatus.WAITING_FOR_RECHECK_REQUEST_APPROVAL)
        nextStatus = CourseMarksSubmissionStatus.REQUESTED_FOR_RECHECK_BY_COE;
    }

    return nextStatus;
  }

  public String getUserIdForNotification(Integer pSemesterId, String pCourseId, CourseMarksSubmissionStatus pNextStatus) {
    String userId = "";
    Map RoleMap = mManager.getUserRoleList(pSemesterId, pCourseId);
    switch(pNextStatus) {
      case WAITING_FOR_SCRUTINY:
        userId = RoleMap.get("Scrutinizer").toString();
        break;
      case WAITING_FOR_HEAD_APPROVAL:
        userId = RoleMap.get("Head").toString();
        break;
      case WAITING_FOR_COE_APPROVAL:
        userId = RoleMap.get("CoE").toString();
        break;
      case REQUESTED_FOR_RECHECK_BY_SCRUTINIZER:
      case REQUESTED_FOR_RECHECK_BY_HEAD:
      case REQUESTED_FOR_RECHECK_BY_COE:
        userId = RoleMap.get("Preparer").toString();
        break;
      case WAITING_FOR_RECHECK_REQUEST_APPROVAL:
        userId = RoleMap.get("VC").toString();
        break;
    }

    return userId;
  }

  public void sendNotification(String userId, String courseNumber) {
    String producer = SecurityUtils.getSubject().getPrincipal().toString();
    Notifier notifier = new Notifier() {
      @Override
      public List<String> consumers() {
        List<String> users = new ArrayList<>();
        users.add(userId);
        return users;
      }

      @Override
      public String producer() {
        mLogger.info("Notification Producer for '{}' : {}", notificationType(), producer);
        return producer;
      }

      @Override
      public String notificationType() {
        return new StringBuilder(Notification.Type.GRADE_SUBMISSION.getValue()).toString();
      }

      @Override
      public String payload() {
        try {
          return "Grades for Course Number : " + courseNumber + " is waiting for your approval.";
        } catch(Exception e) {
          mLogger.error("Exception while looking for user: ", e);
        }
        return null;
      }
    };
    try {
      mNotificationGenerator.notify(notifier);
    } catch(Exception e) {
      mLogger.error("Failed to generate notification", e);
    }
  }

}
