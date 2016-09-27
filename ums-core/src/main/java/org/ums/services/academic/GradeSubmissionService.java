package org.ums.services.academic;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.domain.model.dto.MarksSubmissionStatusDto;
import org.ums.domain.model.dto.StudentGradeDto;
import org.ums.domain.model.immutable.Notification;
import org.ums.domain.model.immutable.User;
import org.ums.enums.*;
import org.ums.exceptions.ValidationException;
import org.ums.manager.ExamGradeManager;
import org.ums.manager.UserManager;
import org.ums.message.MessageResource;
import org.ums.services.NotificationGenerator;
import org.ums.services.Notifier;
import org.ums.services.UtilsService;
import org.ums.util.Constants;

import javax.json.*;
import java.io.StringReader;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component("gradeSubmissionService")
public class GradeSubmissionService {
  private Logger mLogger = LoggerFactory.getLogger(GradeSubmissionService.class);
  @Autowired
  private ExamGradeManager mManager;
  @Autowired
  private UserManager mUserManager;
  @Autowired
  private MessageResource mMessageResource;

  @Autowired
  private UtilsService mUtilsService;

  @Autowired
  private NotificationGenerator mNotificationGenerator;



  public void prepareGradeGroups(JsonObjectBuilder objectBuilder,List<StudentGradeDto> examGradeList,CourseMarksSubmissionStatus courseStatus,String currentActor ){

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

    for (StudentGradeDto gradeDto : examGradeList) {
      jsonReader = Json.createReader(new StringReader(gradeDto.toString()));
      jsonObject = jsonReader.readObject();
      jsonReader.close();
      gradeStatus = gradeDto.getStatus();

      if (gradeStatus == StudentMarksSubmissionStatus.NONE || gradeStatus == StudentMarksSubmissionStatus.SUBMIT && gradeDto.getRecheckStatusId() == 0 && currentActor.equalsIgnoreCase("preparer"))
        noneAndSubmitArrayBuilder.add(jsonObject);
      else if ((gradeStatus == StudentMarksSubmissionStatus.SUBMITTED || gradeStatus == StudentMarksSubmissionStatus.SCRUTINIZE) && currentActor.equalsIgnoreCase("scrutinizer"))
        scrutinizeCandidatesArrayBuilder.add(jsonObject);
      else if ((gradeStatus == StudentMarksSubmissionStatus.SCRUTINIZED || gradeStatus == StudentMarksSubmissionStatus.APPROVE) && currentActor.equalsIgnoreCase("head"))
        approveCandidatesArrayBuilder.add(jsonObject);
      else if ((gradeStatus == StudentMarksSubmissionStatus.APPROVED || gradeStatus == StudentMarksSubmissionStatus.ACCEPT) && currentActor.equalsIgnoreCase("coe"))
        acceptCandidatesArrayBuilder.add(jsonObject);

      if ((gradeStatus == StudentMarksSubmissionStatus.SUBMITTED || gradeStatus == StudentMarksSubmissionStatus.SCRUTINIZE))//&& !currentActor.equalsIgnoreCase("scrutinizer")
        submittedArrayBuilder.add(jsonObject);
      else if ((gradeStatus == StudentMarksSubmissionStatus.SCRUTINIZED || gradeStatus == StudentMarksSubmissionStatus.APPROVE)) // && !currentActor.equalsIgnoreCase("head")
        scrutinizedArrayBuilder.add(jsonObject);
      else if ((gradeStatus == StudentMarksSubmissionStatus.APPROVED || gradeStatus == StudentMarksSubmissionStatus.ACCEPT)) //&& !currentActor.equalsIgnoreCase("coe")
        approvedArrayBuilder.add(jsonObject);
      else if (gradeStatus == StudentMarksSubmissionStatus.ACCEPTED) {
        //acceptedArrayBuilder.add(object1);
        if (gradeDto.getRecheckStatus() == RecheckStatus.RECHECK_TRUE)
          recheckAcceptedArrayBuilder.add(jsonObject);
        else
          acceptedArrayBuilder.add(jsonObject);
      }
      if ((courseStatus == CourseMarksSubmissionStatus.REQUESTED_FOR_RECHECK_BY_SCRUTINIZER || courseStatus == CourseMarksSubmissionStatus.REQUESTED_FOR_RECHECK_BY_HEAD
          || courseStatus == CourseMarksSubmissionStatus.REQUESTED_FOR_RECHECK_BY_COE) && gradeDto.getRecheckStatus() == RecheckStatus.RECHECK_TRUE) {
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

  public String getActingRoleForCourse(CourseMarksSubmissionStatus currentStatus){
    String actingRole="";
    switch(currentStatus){
      case NOT_SUBMITTED:
      case REQUESTED_FOR_RECHECK_BY_SCRUTINIZER:
      case REQUESTED_FOR_RECHECK_BY_HEAD:
      case REQUESTED_FOR_RECHECK_BY_COE:
        actingRole=Constants.GRADE_PREPARER;
        break;
      case WAITING_FOR_SCRUTINY:
        actingRole= Constants.GRADE_SCRUTINIZER;
        break;
      case WAITING_FOR_HEAD_APPROVAL:
        actingRole= Constants.HEAD;
        break;
      case WAITING_FOR_COE_APPROVAL:
      case ACCEPTED_BY_COE:
        actingRole= Constants.COE;
        break;
      case WAITING_FOR_RECHECK_REQUEST_APPROVAL:
        actingRole = Constants.VC;
        break;
    }
    return actingRole;
  }
  public String getActorForCurrentUser(String userId, String requestedRole, int semesterId, String courseId) throws Exception {
    String role = "Invalid";
    List<String> roleList;
    switch (requestedRole) {
      case "T":
        User user = mUserManager.get(userId);
        roleList = mManager.getRoleForTeacher(user.getEmployeeId(), semesterId, courseId);
        if (roleList.size() != 0)
          role = roleList.get(0);
        break;
      case "H":
        roleList = mManager.getRoleForHead(userId);
        if (roleList.size() != 0)
          role = roleList.get(0);
        break;
      case "C":
        roleList = mManager.getRoleForCoE(userId);
        if (roleList.size() != 0)
          role = roleList.get(0);
        break;
      case "V":
        roleList = mManager.getRoleForVC(userId);
        if (roleList.size() != 0)
          role = roleList.get(0);
        break;
      default:
        throw new ValidationException("Unauthorized Access Detected.");
    }
    if (!Arrays.asList(Constants.validRolesForGradeAccess).contains(role))
      throw new ValidationException("Unauthorized Access Detected.");
    else
      return role;
  }

  public void validatePartInfo(final Integer pTotalPart, final Integer pPartATotal, final Integer pPartBTotal) throws Exception{
    if(pTotalPart!=1 && pTotalPart!=2)
      throw new Exception("Part Information is wrong.");

    if(pTotalPart==1 && (pPartATotal==0 || pPartATotal>70)){
      throw new Exception("Part Information is wrong.");
    }
    else if(pTotalPart==2){
      if(pPartATotal==0||pPartBTotal==0 || pPartATotal+pPartBTotal!=70){
        throw new Exception("Part Information is wrong.");
      }
    }
  }

  public void validateGradeSubmissionDeadline(String lastDateOfSubmission) throws  Exception{

    if(lastDateOfSubmission==null || lastDateOfSubmission.equalsIgnoreCase("")) return;
    try{
      SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
      Date currentDate = new Date();
      Date deadLine = sdf.parse(lastDateOfSubmission+" 23:59:59");
      if(currentDate.compareTo(deadLine)>0){
        throw new ValidationException("Grade Submission Deadline is Over.");
      }
    }catch(ParseException ex){
      ex.printStackTrace();
    }

  }

  public void validateGradeSubmission(String actingRoleForCurrentUser,MarksSubmissionStatusDto requestedStatusDTO,MarksSubmissionStatusDto actualStatusDTO,
                                      List<StudentGradeDto> gradeList,String operation) throws Exception {

    //operation can be either submit OR save
    //Checking the role, requested from client side is valid for the user who is trying to do some operation on grades
    //Role Validation
    String actualActingRole=getActingRoleForCourse(actualStatusDTO.getStatus());
    if(!actingRoleForCurrentUser.equalsIgnoreCase(actualActingRole)){
      throw new ValidationException("Sorry, you are not allowed for this operation.");
    }

    //Deadline && Part Info Validation
    if(actualStatusDTO.getStatus()==CourseMarksSubmissionStatus.NOT_SUBMITTED && operation.equals("submit")) {
      validateGradeSubmissionDeadline(actualStatusDTO.getLastSubmissionDate());
      if(actualStatusDTO.getCourseType()==CourseType.THEORY)
        validatePartInfo(requestedStatusDTO.getTotal_part(), requestedStatusDTO.getPart_a_total(), requestedStatusDTO.getPart_b_total());
    }

    //Submitted Grade Validation
    validateSubmittedGrades(actualStatusDTO,gradeList );

  }
  public void validateSubmittedGrades(MarksSubmissionStatusDto actualStatusDTO,List<StudentGradeDto> gradeList ) throws Exception {

    //Validate all the grades been submitted or not
    int totalStudents = mManager.getTotalStudentCount(actualStatusDTO);
    if (actualStatusDTO.getStatus()==CourseMarksSubmissionStatus.NOT_SUBMITTED   && gradeList.size() != totalStudents ) {
      throw new ValidationException("Wrong number of grades been submitted.");
    }

    //|| partInfo.getStatus()==CourseMarksSubmissionStatus.REQUESTED_FOR_RECHECK_BY_SCRUTINIZER
    //|| partInfo.getStatus()==CourseMarksSubmissionStatus.REQUESTED_FOR_RECHECK_BY_HEAD  || partInfo.getStatus()==CourseMarksSubmissionStatus.REQUESTED_FOR_RECHECK_BY_COE
    if(actualStatusDTO.getStatus()==CourseMarksSubmissionStatus.NOT_SUBMITTED  ){
      boolean error = false;
      gradeList.forEach((gradeDTO) -> {
            try {
              validateMarks(error, gradeDTO, actualStatusDTO);
            } catch (Exception ex) {
              throw new RuntimeException(ex);
            }
          }
      );
    }

  }

  private boolean validateMarks(boolean error,StudentGradeDto gradeDTO,MarksSubmissionStatusDto partInfo) throws Exception{
    if(partInfo.getCourseType()==CourseType.THEORY) {
      error = error && validateQuiz(error, gradeDTO.getQuiz(), gradeDTO.getRegType());
      error = error && validateClassPerformance(error, gradeDTO.getClassPerformance(), gradeDTO.getRegType());
      error = error && validatePartA(error, gradeDTO.getPartA(), partInfo.getPart_a_total(), gradeDTO.getRegType());
      error = error && validatePartB(error, gradeDTO.getPartB(), partInfo.getTotal_part(), partInfo.getPart_b_total(), gradeDTO.getRegType());
      error = error && validateTheoryTotal(error, gradeDTO);
    }
    else if(partInfo.getCourseType()==CourseType.SESSIONAL) {
      error = error && validateSessionalTotal(error, gradeDTO);
    }
    error = error && validateGradeLetter(error,gradeDTO);
    return error;
  }
  private boolean validateQuiz(boolean error,Float quiz,CourseRegType regType){
    if(quiz>20 && regType==CourseRegType.REGULAR){
      error = error && Boolean.FALSE;
    }
    return error;
  }
  private boolean validateClassPerformance(boolean error,Float classPerf,CourseRegType regType){
    if(classPerf>20 && regType==CourseRegType.REGULAR){
      error = error && Boolean.FALSE;
    }
    return error;
  }
  private boolean validatePartA(boolean error,Float partA,Integer partAMax,CourseRegType regType){
    if(partA>partAMax){
      error = error && Boolean.FALSE;
    }
    return error;
  }
  private boolean validatePartB(boolean error,Float partB,Integer totalPartCount,Integer partBMax,CourseRegType regType){
    if(totalPartCount==2 && (partB>partBMax)){
      error = error && Boolean.FALSE;
    }
    if(totalPartCount==1 && (partB>0)){
      error = error && Boolean.FALSE;
    }
    return error;
  }

  private boolean validateTheoryTotal(boolean error,StudentGradeDto gradeDTO){
    if(gradeDTO.getTotal()>100 ||  gradeDTO.getTotal() != gradeDTO.getQuiz() +gradeDTO.getClassPerformance() +gradeDTO.getPartA() + gradeDTO.getPartB()){
      error = error && Boolean.FALSE;
    }
    return error;
  }

  private boolean validateSessionalTotal(boolean error,StudentGradeDto gradeDTO){
    if(gradeDTO.getTotal()>100){
      error = error && Boolean.FALSE;
    }
    return error;
  }

  private boolean validateGradeLetter(boolean error,StudentGradeDto gradeDTO) throws  Exception{
    if(mUtilsService.getGradeLetter(Math.round(gradeDTO.getTotal()),gradeDTO.getRegType()).equalsIgnoreCase(gradeDTO.getGradeLetter())) {
      error = error && Boolean.FALSE;
    }
    return error;
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

  public String getUserIdForNotification(Integer pSemesterId,String pCourseId,CourseMarksSubmissionStatus pNextStatus) throws  Exception{
    String userId="";
    Map RoleMap=  mManager.getUserRoleList(pSemesterId,pCourseId);
    switch(pNextStatus){
      case WAITING_FOR_SCRUTINY :
        userId=RoleMap.get("Scrutinizer").toString();
        break;
      case WAITING_FOR_HEAD_APPROVAL :
        userId=RoleMap.get("Head").toString();
        break;
      case WAITING_FOR_COE_APPROVAL:
        userId=RoleMap.get("CoE").toString();
        break;
      case REQUESTED_FOR_RECHECK_BY_SCRUTINIZER:
      case REQUESTED_FOR_RECHECK_BY_HEAD:
      case REQUESTED_FOR_RECHECK_BY_COE:
        userId=RoleMap.get("Preparer").toString();
        break;
      case WAITING_FOR_RECHECK_REQUEST_APPROVAL:
        userId=RoleMap.get("VC").toString();
        break;
    }

    return userId;
  }


  public void sendNotification(String userId,String courseNumber){
    Notifier notifier = new Notifier() {
      @Override
      public List<String> consumers() throws Exception {
        List<String> users = new ArrayList<>();
        users.add(userId);
        return users;
      }

      @Override
      public String producer() throws Exception {
        return SecurityUtils.getSubject().getPrincipal().toString();
      }

      @Override
      public String notificationType() {
        return new StringBuilder(Notification.Type.GRADE_SUBMISSION.getValue()).toString();
      }

      @Override
      public String payload() {
        try {
          return "Grades for Course Number : "+courseNumber+" is waiting for your approval.";
        } catch (Exception e) {
          mLogger.error("Exception while looking for user: ", e);
        }
        return null;
      }
    };
    try {
      mNotificationGenerator.notify(notifier);
    } catch (Exception e) {
      mLogger.error("Failed to generate notification", e);
    }
  }


}
