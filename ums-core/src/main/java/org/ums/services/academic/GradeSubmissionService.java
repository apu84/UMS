package org.ums.services.academic;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.domain.model.dto.StudentGradeDto;
import org.ums.domain.model.immutable.User;
import org.ums.enums.CourseMarksSubmissionStatus;
import org.ums.enums.RecheckStatus;
import org.ums.enums.StudentMarksSubmissionStatus;
import org.ums.manager.ExamGradeManager;
import org.ums.manager.UserManager;
import org.ums.util.Constants;

import javax.json.*;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;

@Component("gradeSubmissionService")
public class GradeSubmissionService {

  @Autowired
  private ExamGradeManager mManager;
  @Autowired
  private UserManager mUserManager;

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
          recheckAcceptedArrayBuilder.add(objectBuilder);
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
        throw new UnauthorizedException("Unauthorized Access Detected.");
    }
    if (!Arrays.asList(Constants.validRolesForGradeAccess).contains(role))
      throw new UnauthorizedException("Unauthorized Access Detected.");
    else
      return role;
  }

}
