package org.ums.manager;

import org.springframework.util.StringUtils;
import org.ums.domain.model.immutable.CourseTeacher;
import org.ums.domain.model.immutable.Student;
import org.ums.domain.model.immutable.User;
import org.ums.message.MessageResource;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSectionPermission extends BaseFileContentPermission {
  protected CourseTeacherManager mCourseTeacherManager;

  public AbstractSectionPermission(final BearerAccessTokenManager pBearerAccessTokenManager,
      final UserManager pUserManager, final MessageResource pMessageResource,
      final CourseTeacherManager pCourseTeacherManager) {
    super(pBearerAccessTokenManager, pUserManager, pMessageResource);
    mCourseTeacherManager = pCourseTeacherManager;
  }

  protected boolean hasPermission(final Path pTargetPath, final List<String> pSections, final Student pStudent) {
    String type = getUserDefinedProperty(FOLDER_TYPE, pTargetPath);

    if(!StringUtils.isEmpty(type)) {
      switch(type) {
        case FOLDER_TYPE_ASSIGNMENT:
          boolean hasSectionPermission = false;
          for(String section : pSections) {
            // need to check optional courses section as well
            if((!StringUtils.isEmpty(pStudent.getTheorySection()) && !StringUtils.isEmpty(pStudent
                .getSessionalSection()))
                && (section.equalsIgnoreCase(pStudent.getTheorySection()) || section.equalsIgnoreCase(pStudent
                    .getSessionalSection()))) {
              hasSectionPermission = true;
            }
          }
          return hasSectionPermission;

        case FOLDER_TYPE_STUDENT_ASSIGNMENT:
          return pTargetPath.getFileName().toString().equalsIgnoreCase(pStudent.getId());
      }
    }
    return true;
  }

  protected List<String> permittedSections(final String pOwner, final Integer pSemesterId, final String pCourseId) {
    User creator = mUserManager.get(pOwner);
    List<CourseTeacher> courseTeacherSections =
        mCourseTeacherManager.getAssignedSections(pSemesterId, pCourseId, creator.getEmployeeId());
    List<String> sectionList = new ArrayList<>();
    for(CourseTeacher courseTeacher : courseTeacherSections) {
      sectionList.add(courseTeacher.getSection());
    }
    return sectionList;
  }
}
