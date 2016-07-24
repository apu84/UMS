package org.ums.manager;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.ums.configuration.UMSConfiguration;
import org.ums.decorator.BinaryContentDecorator;
import org.ums.domain.model.immutable.*;
import org.ums.message.MessageResource;

import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Iterator;
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
    Student student;
    try {
      student = getStudent();
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

    Object list = super.list(pPath, pDomain, pRootPath);

    Path path = getQualifiedPath(pDomain, buildPath(pPath, pRootPath));

    String type = getUserDefinedProperty("type", path);

    if (!StringUtils.isEmpty(type) && type.equalsIgnoreCase("assignment")) {
      List<Map<String, Object>> folderList = (List<Map<String, Object>>) list;
      Iterator<Map<String, Object>> iterator = folderList.iterator();
      while (iterator.hasNext()) {
        String name = iterator.next().get("name").toString();
        if (!name.equalsIgnoreCase(student.getId())) {
          iterator.remove();
        }
      }
    }

    return list;
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
    Path uploadPath = getQualifiedPath(pDomain, buildPath(pPath, pRootPath));
    String folderType = getUserDefinedProperty("type", uploadPath);

    if (!StringUtils.isEmpty(folderType)) {
      //Check for parent folder type if current folder type is studentAssignment
      if (folderType.equalsIgnoreCase("studentAssignment")) {
        uploadPath = uploadPath.getParent();
        pPath = pPath.substring(0, pPath.lastIndexOf("/"));
      }
      folderType = getUserDefinedProperty("type", uploadPath);

      if (!StringUtils.isEmpty(folderType) && folderType.equalsIgnoreCase("assignment")) {
        try {
          Date currentDate = new Date();
          Date startDate = mDateFormat.parse(getUserDefinedProperty("startDate", uploadPath));
          Date endDate = mDateFormat.parse(getUserDefinedProperty("endDate", uploadPath));

          if (currentDate.after(startDate) && currentDate.before(endDate)) {
            //create folder as studentId
            Student student = getStudent();
            String assignmentFolder = Paths.get(pPath, student.getId()).toString();
            super.createFolder(assignmentFolder, pDomain, pRootPath);
            addUserDefinedProperty("type", "studentAssignment", getQualifiedPath(pDomain, buildPath(assignmentFolder, pRootPath)));
            super.upload(pFileContent, assignmentFolder, pDomain, pRootPath);
            return success();

          } else {
            error(mMessageResource.getMessage("assignment.upload.time.limit.exceed", pPath));
          }
        } catch (Exception e) {
          return error(mMessageResource.getMessage("file.upload.not.allowed", pPath));
        }
      }
    }
    return error(mMessageResource.getMessage("file.upload.not.allowed", pPath));
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
