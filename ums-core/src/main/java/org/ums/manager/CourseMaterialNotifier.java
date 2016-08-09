package org.ums.manager;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ums.configuration.UMSConfiguration;
import org.ums.decorator.BinaryContentDecorator;
import org.ums.domain.model.immutable.CourseTeacher;
import org.ums.domain.model.immutable.UGRegistrationResult;
import org.ums.domain.model.immutable.User;
import org.ums.domain.model.mutable.MutableCourseTeacher;
import org.ums.domain.model.mutable.MutableUser;
import org.ums.message.MessageResource;
import org.ums.persistent.model.PersistentUser;
import org.ums.services.NotificationGenerator;
import org.ums.services.Notifier;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class CourseMaterialNotifier extends BinaryContentDecorator {
  private Logger mLogger = LoggerFactory.getLogger(CourseMaterialNotifier.class);
  private UserManager mUserManager;
  private NotificationGenerator mNotificationGenerator;
  private UGRegistrationResultManager mUGRegistrationResultManager;
  private UMSConfiguration mUMSConfiguration;
  private MessageResource mMessageResource;
  private static final String SEMESTER_ID = "semesterId";
  private static final String COURSE_ID = "courseId";

  public CourseMaterialNotifier(UserManager pUserManager,
                                NotificationGenerator pNotificationGenerator,
                                UGRegistrationResultManager pUGRegistrationResultManager,
                                UMSConfiguration pUMSConfiguration,
                                MessageResource pMessageResource) {
    mUserManager = pUserManager;
    mNotificationGenerator = pNotificationGenerator;
    mUGRegistrationResultManager = pUGRegistrationResultManager;
    mUMSConfiguration = pUMSConfiguration;
    mMessageResource = pMessageResource;
  }

  @Override
  public Map<String, Object> createFolder(String pNewPath, Map<String, String> pAdditionalParams, Domain pDomain, String... pRootPath) {
    Map<String, Object> folder = super.createFolder(pNewPath, pAdditionalParams, pDomain, pRootPath);
    Notifier notifier = new Notifier() {
      @Override
      public List<User> consumers() throws Exception {
        List<User> users = new ArrayList<>();

        if (pNewPath.lastIndexOf("/") == 0) {
          Path targetDirectory = getQualifiedPath(pDomain, buildPath(pNewPath, pRootPath));
          String semesterId = getUserDefinedProperty(SEMESTER_ID, targetDirectory);
          String courseId = getUserDefinedProperty(COURSE_ID, targetDirectory);
          List<UGRegistrationResult> studentList
              = mUGRegistrationResultManager.getByCourseSemester(Integer.parseInt(semesterId), courseId, 0);

          for (UGRegistrationResult registrationResult : studentList) {
            MutableUser studentUser = new PersistentUser();
            studentUser.setId(registrationResult.getStudentId());
            users.add(studentUser);
          }
        }

        return users;
      }

      @Override
      public User producer() throws Exception {
        return mUserManager.get(SecurityUtils.getSubject().getPrincipal().toString());
      }

      @Override
      public String notificationType() {
        return new StringBuilder("CM_").append(pRootPath[0]).append("_").append(pRootPath[1]).toString();
      }

      @Override
      public String payload() {
        try {
          User user = mUserManager.get(SecurityUtils.getSubject().getPrincipal().toString());
          return String.format("%s has uploaded course material for %s", user.getName(), pRootPath[1]);
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
    return folder;
  }

  @Override
  public Map<String, Object> createAssignmentFolder(String pNewPath, Date pStartDate, Date pEndDate, Domain pDomain,
                                                    String... pRootPath) {
    return super.createAssignmentFolder(pNewPath, pStartDate, pEndDate, pDomain, pRootPath);
  }

  @Override
  protected String getStorageRoot() {
    return mUMSConfiguration.getStorageRoot();
  }


}
