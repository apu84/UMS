package org.ums.manager;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ums.decorator.BinaryContentDecorator;
import org.ums.domain.model.immutable.CourseTeacher;
import org.ums.domain.model.immutable.User;
import org.ums.domain.model.mutable.MutableCourseTeacher;
import org.ums.services.NotificationGenerator;
import org.ums.services.Notifier;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class CourseMaterialNotifier extends BinaryContentDecorator {
  private Logger mLogger = LoggerFactory.getLogger(CourseMaterialNotifier.class);
  private UserManager mUserManager;
  private NotificationGenerator mNotificationGenerator;
  private AssignedTeacherManager<CourseTeacher, MutableCourseTeacher, Integer> mCourseTeacherManager;
  private static final String SEMESTER_ID = "semesterId";
  private static final String COURSE_ID = "courseId";

  @Override
  public Map<String, Object> createFolder(String pNewPath, Domain pDomain, String... pRootPath) {
    Map<String, Object> folder = super.createFolder(pNewPath, pDomain, pRootPath);
    Notifier notifier = new Notifier() {
      @Override
      public List<User> consumers() throws Exception {
        User user = mUserManager.get(SecurityUtils.getSubject().getPrincipal().toString());
        String employeeId = user.getEmployeeId();
       /* if (pNewPath.lastIndexOf("/") == 0) {
          String semesterId = getUserDefinedProperty(SEMESTER_ID, )
        }*/
        return null;
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

    mNotificationGenerator.notify(notifier);

    return folder;
  }

  @Override
  public Map<String, Object> createAssignmentFolder(String pNewPath, Date pStartDate, Date pEndDate, Domain pDomain,
                                                    String... pRootPath) {
    return super.createAssignmentFolder(pNewPath, pStartDate, pEndDate, pDomain, pRootPath);
  }

  @Override
  protected String getStorageRoot() {
    return null;
  }


}
