package org.ums.manager;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ums.configuration.UMSConfiguration;
import org.ums.decorator.BinaryContentDecorator;
import org.ums.domain.model.immutable.*;
import org.ums.message.MessageResource;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class StudentFileContentPermission extends BinaryContentDecorator {
  private static Logger mLogger = LoggerFactory.getLogger(StudentFileContentPermission.class);
  //TODO: Remove BearerAccessTokenManager and UserManager if not required
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
      //TODO: Check all the courses of all enrolled semester of a Student
      Syllabus syllabus = mSemesterSyllabusMapManager.getSyllabusForSemester(student.getProgramId(), semester.getId(),
          student.getCurrentYear(), student.getCurrentAcademicSemester());

      Course course = mCourseManager.getByCourseNo(courseName, syllabus.getId());

    } catch (Exception e) {
      mLogger.error("Exception while listing folders", e);
      return error(mMessageResource.getMessage("folder.listing.failed"));
    }
    return super.list(pPath, pDomain, pRootPath);
  }

  @Override
  public Map<String, Object> move(List<String> pItems, String pNewPath, Domain pDomain, String... pRootPath) {
    return error(mMessageResource.getMessage("folder.move.not.allowed"));
  }

  @Override
  public Map<String, Object> copy(List<String> pItems, String pNewPath, String pNewFileName, Domain pDomain, String... pRootPath) {
    return error(mMessageResource.getMessage("folder.copy.not.allowed"));
  }

  @Override
  public Map<String, Object> remove(List<String> pItems, Domain pDomain, String... pRootPath) {
    return error(mMessageResource.getMessage("folder.remove.not.allowed"));
  }

  @Override
  public Map<String, Object> rename(String pOldPath, String pNewPath, Domain pDomain, String... pRootPath) {
    return error(mMessageResource.getMessage("folder.rename.not.allowed"));
  }

  @Override
  public Map<String, Object> createFolder(String pNewPath, Domain pDomain, String... pRootPath) {
    return error(mMessageResource.getMessage("folder.creation.not.allowed"));
  }

  @Override
  public Map<String, Object> upload(Map<String, InputStream> pFileContent, String pPath, Domain pDomain, String... pRootPath) {
    return error(mMessageResource.getMessage("folder.upload.not.allowed"));
  }

  @Override
  public Map<String, Object> download(String pPath, String pToken, Domain pDomain, String... pRootPath) {
    //TODO: Check if student has permission
    return super.download(pPath, pToken, pDomain, pRootPath);
  }

  @Override
  public Map<String, Object> downloadAsZip(List<String> pItems, String pNewFileName, String pToken, Domain pDomain, String... pRootPath) {
    //TODO: Check if student has permission
    return super.downloadAsZip(pItems, pNewFileName, pToken, pDomain, pRootPath);
  }

  protected Student getStudent() throws Exception {
    String studentId = SecurityUtils.getSubject().getPrincipal().toString();
    return mStudentManager.get(studentId);
  }
}
