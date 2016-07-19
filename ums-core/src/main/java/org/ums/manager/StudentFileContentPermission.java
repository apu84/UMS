package org.ums.manager;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ums.configuration.UMSConfiguration;
import org.ums.decorator.BinaryContentDecorator;
import org.ums.domain.model.immutable.*;
import org.ums.message.MessageResource;

public class StudentFileContentPermission extends BinaryContentDecorator {
  private static Logger mLogger = LoggerFactory.getLogger(StudentFileContentPermission.class);
  private BearerAccessTokenManager mBearerAccessTokenManager;
  private UserManager mUserManager;
  private UMSConfiguration mUMSConfiguration;
  private MessageResource mMessageResource;
  private StudentManager mStudentManager;
  private SemesterManager mSemesterManager;
  private CourseManager mCourseManager;
  private SemesterSyllabusMapManager mSemesterSyllabusMapManager;

  public StudentFileContentPermission(final UserManager pUserManager,
                                      final BearerAccessTokenManager pBearerAccessTokenManager,
                                      final UMSConfiguration pUMSConfiguration,
                                      final MessageResource pMessageResource,
                                      final StudentManager pStudentManager,
                                      final SemesterManager pSemesterManager,
                                      final CourseManager pCourseManager,
                                      final SemesterSyllabusMapManager pSemesterSyllabusMapManager) {
    mBearerAccessTokenManager = pBearerAccessTokenManager;
    mUserManager = pUserManager;
    mUMSConfiguration = pUMSConfiguration;
    mMessageResource = pMessageResource;
    mStudentManager = pStudentManager;
    mSemesterManager = pSemesterManager;
    mCourseManager = pCourseManager;
    mSemesterSyllabusMapManager = pSemesterSyllabusMapManager;
  }

  @Override
  protected String getStorageRoot() {
    return mUMSConfiguration.getStorageRoot();
  }

  @Override
  public Object list(String pPath, Domain pDomain, String... pRootPath) {
    // Student should only able to see course material of course he/she has registered
    String semesterName = pRootPath[0];
    String courseName = pRootPath[1];
    try {
      Student student = getStudent();
      ProgramType programType = student.getProgram().getProgramType();
      Semester semester = mSemesterManager.getBySemesterName(semesterName, programType.getId());
      Syllabus syllabus = mSemesterSyllabusMapManager.getSyllabusForSemester(student.getProgramId(), semester.getId(),
          student.getCurrentYear(), student.getCurrentAcademicSemester());
      Course course = mCourseManager.getByCourseName(courseName, syllabus.getId());

    } catch (Exception e) {
      mLogger.error("Exception while listing folders", e);
    }
    return super.list(pPath, pDomain, pRootPath);
  }

  protected Student getStudent() throws Exception {
    String studentId = SecurityUtils.getSubject().getPrincipal().toString();
    return mStudentManager.get(studentId);
  }
}
