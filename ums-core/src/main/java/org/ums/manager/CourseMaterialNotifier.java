package org.ums.manager;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.ums.configuration.UMSConfiguration;
import org.ums.domain.model.immutable.Notification;
import org.ums.domain.model.immutable.UGRegistrationResult;
import org.ums.usermanagement.user.User;
import org.ums.enums.CourseRegType;
import org.ums.message.MessageResource;
import org.ums.services.NotificationGenerator;
import org.ums.services.Notifier;
import org.ums.usermanagement.user.UserManager;

public class CourseMaterialNotifier extends AbstractSectionPermission {
  private Logger mLogger = LoggerFactory.getLogger(CourseMaterialNotifier.class);
  private NotificationGenerator mNotificationGenerator;
  private UGRegistrationResultManager mUGRegistrationResultManager;
  private UMSConfiguration mUMSConfiguration;
  private static final String SEMESTER_ID = "semesterId";
  private static final String COURSE_ID = "courseId";

  public CourseMaterialNotifier(UserManager pUserManager, NotificationGenerator pNotificationGenerator,
      UGRegistrationResultManager pUGRegistrationResultManager, UMSConfiguration pUMSConfiguration,
      MessageResource pMessageResource, CourseTeacherManager pCourseTeacherManager,
      BearerAccessTokenManager pBearerAccessTokenManager) {
    super(pBearerAccessTokenManager, pUserManager, pMessageResource, pCourseTeacherManager);
    mNotificationGenerator = pNotificationGenerator;
    mUGRegistrationResultManager = pUGRegistrationResultManager;
    mUMSConfiguration = pUMSConfiguration;
  }

  @Override
  public Map<String, Object> createFolder(String pNewPath, Map<String, String> pAdditionalParams, Domain pDomain,
      String... pRootPath) {
    Map<String, Object> folder = super.createFolder(pNewPath, pAdditionalParams, pDomain, pRootPath);
    Notifier notifier = new Notifier() {
      String producer = SecurityUtils.getSubject().getPrincipal().toString();

      @Override
      public List<String> consumers() {
        List<String> users = new ArrayList<>();

        if(pNewPath.lastIndexOf("/") == 0) {
          Path targetDirectory = getQualifiedPath(pDomain, buildPath(pNewPath, pRootPath));
          String semesterId = getUserDefinedProperty(SEMESTER_ID, targetDirectory);
          String courseId = getUserDefinedProperty(COURSE_ID, targetDirectory);
          List<UGRegistrationResult> studentList =
              mUGRegistrationResultManager.getByCourseSemester(Integer.parseInt(semesterId), courseId,
                  CourseRegType.REGULAR);

          for(UGRegistrationResult registrationResult : studentList) {
            users.add(registrationResult.getStudentId());
          }
        }

        return users;
      }

      @Override
      public String producer() {
        return producer;
      }

      @Override
      public String notificationType() {
        return new StringBuilder(Notification.Type.COURSE_MATERIAL.getValue()).append("_").append(pRootPath[0])
            .append("_").append(pRootPath[1]).toString();
      }

      @Override
      public String payload() {
        User user = mUserManager.get(producer);
        return mMessageResource.getMessage("course.material.uploaded", user.getName(), pNewPath, pRootPath[1]);
      }
    };
    mNotificationGenerator.notify(notifier);
    return folder;
  }

  @Override
  public Map<String, Object> createAssignmentFolder(final String pNewPath, final Date pStartDate, final Date pEndDate,
      final Map<String, String> pAdditionalParams, final Domain pDomain, final String... pRootPath) {
    Map<String, Object> assignmentFolderResponse =
        super.createAssignmentFolder(pNewPath, pStartDate, pEndDate, pAdditionalParams, pDomain, pRootPath);
    final Path targetDirectory = getQualifiedPath(pDomain, buildPath(pNewPath, pRootPath));
    final String semesterIdString = getUserDefinedProperty(SEMESTER_ID, targetDirectory);
    final String courseId = getUserDefinedProperty(COURSE_ID, targetDirectory);
    final String owner = getUserDefinedProperty(OWNER, targetDirectory);

    Notifier notifier = new Notifier() {
      @Override
      public List<String> consumers() {
        List<String> users = new ArrayList<>();
        if(StringUtils.isEmpty(semesterIdString) || StringUtils.isEmpty(courseId) || StringUtils.isEmpty(owner)) {
          throw new RuntimeException("Can not find required semesterId or courseId or owner of the file");
        }

        Integer semesterId = Integer.parseInt(semesterIdString);
        List<UGRegistrationResult> studentList =
            mUGRegistrationResultManager.getByCourseSemester(semesterId, courseId, CourseRegType.REGULAR);
        List<String> sections = permittedSections(owner, semesterId, courseId);

        for(UGRegistrationResult registrationResult : studentList) {
          if(hasPermission(targetDirectory, sections, registrationResult.getStudent())) {
            users.add(registrationResult.getStudentId());
          }
        }

        return users;
      }

      @Override
      public String producer() {
        return SecurityUtils.getSubject().getPrincipal().toString();
      }

      @Override
      public String notificationType() {
        return new StringBuilder(Notification.Type.COURSE_ASSIGNMENT.getValue()).append("_").append(pRootPath[0])
            .append("_").append(pRootPath[1]).toString();
      }

      @Override
      public String payload() {
        try {
          User user = mUserManager.get(SecurityUtils.getSubject().getPrincipal().toString());
          return mMessageResource.getMessage("assignment.directory.created", user.getName(), pNewPath, pRootPath[1],
              getUserDefinedProperty(START_DATE, targetDirectory), getUserDefinedProperty(END_DATE, targetDirectory));
        } catch(Exception e) {
          mLogger.error("Exception while looking building payload for assignment: ", e);
        }
        return null;
      }
    };
    mNotificationGenerator.notify(notifier);
    return assignmentFolderResponse;
  }

  @Override
  protected String getStorageRoot() {
    return mUMSConfiguration.getStorageRoot();
  }

}
