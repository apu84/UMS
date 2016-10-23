package org.ums.manager;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.ums.configuration.UMSConfiguration;
import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.immutable.Student;
import org.ums.domain.model.immutable.UGRegistrationResult;
import org.ums.enums.CourseRegType;
import org.ums.message.MessageResource;

import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class StudentFileContentPermission extends AbstractSectionPermission {
  private static Logger mLogger = LoggerFactory.getLogger(StudentFileContentPermission.class);
  private UMSConfiguration mUMSConfiguration;
  private MessageResource mMessageResource;
  private StudentManager mStudentManager;
  private UGRegistrationResultManager mUGRegistrationResultManager;

  public StudentFileContentPermission(final UserManager pUserManager,
      final BearerAccessTokenManager pBearerAccessTokenManager,
      final UMSConfiguration pUMSConfiguration, final MessageResource pMessageResource,
      final StudentManager pStudentManager,
      final UGRegistrationResultManager pRegistrationResultManager,
      final CourseTeacherManager pCourseTeacherManager) {
    super(pBearerAccessTokenManager, pUserManager, pMessageResource, pCourseTeacherManager);
    mUMSConfiguration = pUMSConfiguration;
    mMessageResource = pMessageResource;
    mStudentManager = pStudentManager;
    mUGRegistrationResultManager = pRegistrationResultManager;
  }

  @Override
  protected String getStorageRoot() {
    return mUMSConfiguration.getStorageRoot();
  }

  @Override
  public Object list(String pPath, Map<String, String> pAdditionalParams, Domain pDomain,
      String... pRootPath) {
    // Student should only able to see course material of course he/she has registered
    String semesterName = pRootPath[0];
    String courseName = pRootPath[1];
    Student student;

    Object list = super.list(pPath, pAdditionalParams, pDomain, pRootPath);
    try {
      student = getStudent();
      Path targetDirectory = getQualifiedPath(pDomain, buildPath(pPath, pRootPath));
      String semesterIdString =
          getUserDefinedProperty(SEMESTER_ID, targetDirectory, getQualifiedPath(pDomain, pRootPath));
      String courseIdString =
          getUserDefinedProperty(COURSE_ID, targetDirectory, getQualifiedPath(pDomain, pRootPath));
      if(StringUtils.isEmpty(semesterIdString) || StringUtils.isEmpty(courseIdString)) {
        throw new Exception("Can not find semesterId and courseId for directory " + pPath);
      }
      Integer semesterId = Integer.parseInt(semesterIdString);
      List<UGRegistrationResult> registeredCourses =
          mUGRegistrationResultManager.getRegisteredCourseByStudent(semesterId, student.getId(),
              CourseRegType.REGULAR);

      boolean courseFound = false;
      Course course = null;
      for(UGRegistrationResult registeredCourse : registeredCourses) {
        if(registeredCourse.getCourse().getId().equalsIgnoreCase(courseIdString)) {
          courseFound = true;
          course = registeredCourse.getCourse();
          break;
        }
      }

      if(!courseFound) {
        throw new Exception(student.getId() + " has no registered course named " + courseName);
      }

      List<Map<String, Object>> folderList = (List<Map<String, Object>>) list;
      Iterator<Map<String, Object>> iterator = folderList.iterator();

      while(iterator.hasNext()) {
        String name = iterator.next().get("name").toString();
        Path path = getQualifiedPath(pDomain, buildPath(pPath, pRootPath));
        path = Paths.get(path.toString(), name);
        List<String> permittedSections =
            permittedSections(getUserDefinedProperty(OWNER, path), semesterId, course.getId());

        if(!hasPermission(path, permittedSections, student)) {
          iterator.remove();
        }
      }

    } catch(Exception e) {
      mLogger.error("Exception while listing folders", e);
      return error(mMessageResource.getMessage("folder.listing.failed"));
    }
    return addOwnerToken(list);
  }

  @Override
  public Map<String, Object> move(List<String> pItems, String pNewPath, Domain pDomain,
      String... pRootPath) {
    return error(mMessageResource.getMessage("folder.move.not.allowed"));
  }

  @Override
  public Map<String, Object> copy(List<String> pItems, String pNewPath, String pNewFileName,
      Domain pDomain, String... pRootPath) {
    return error(mMessageResource.getMessage("folder.copy.not.allowed"));
  }

  @Override
  public Map<String, Object> remove(List<String> pItems, Domain pDomain, String... pRootPath) {
    return error(mMessageResource.getMessage("folder.remove.not.allowed"));
  }

  @Override
  public Map<String, Object> rename(String pOldPath, String pNewPath, Domain pDomain,
      String... pRootPath) {
    return error(mMessageResource.getMessage("folder.rename.not.allowed"));
  }

  @Override
  public Map<String, Object> createFolder(String pNewPath, Map<String, String> pAdditionalParams,
      Domain pDomain, String... pRootPath) {
    return error(mMessageResource.getMessage("folder.creation.not.allowed"));
  }

  @Override
  public Map<String, Object> upload(Map<String, InputStream> pFileContent, String pPath,
      Domain pDomain, String... pRootPath) {
    Path uploadPath = getQualifiedPath(pDomain, buildPath(pPath, pRootPath));
    String folderType = getUserDefinedProperty(FOLDER_TYPE, uploadPath);

    if(!StringUtils.isEmpty(folderType)) {
      // Check for parent folder type if current folder type is studentAssignment
      if(folderType.equalsIgnoreCase(FOLDER_TYPE_STUDENT_ASSIGNMENT)) {
        uploadPath = uploadPath.getParent();
        pPath = pPath.substring(0, pPath.lastIndexOf("/"));
      }
      folderType = getUserDefinedProperty("type", uploadPath);

      if(!StringUtils.isEmpty(folderType) && folderType.equalsIgnoreCase("assignment")) {
        try {
          Date currentDate = new Date();
          Date startDate = mDateFormat.parse(getUserDefinedProperty("startDate", uploadPath));
          Date endDate = mDateFormat.parse(getUserDefinedProperty("endDate", uploadPath));

          if(currentDate.after(startDate) && currentDate.before(endDate)) {
            // create folder as studentId
            Student student = getStudent();
            String assignmentFolder = Paths.get(pPath, student.getId()).toString();
            super.createFolder(assignmentFolder, null, pDomain, pRootPath);
            addUserDefinedProperty(FOLDER_TYPE, FOLDER_TYPE_STUDENT_ASSIGNMENT,
                getQualifiedPath(pDomain, buildPath(assignmentFolder, pRootPath)));
            super.upload(pFileContent, assignmentFolder, pDomain, pRootPath);
            return success();

          }
          else {
            return error(mMessageResource.getMessage("assignment.upload.time.limit.exceed",
                startDate, endDate));
          }
        } catch(Exception e) {
          return error(mMessageResource.getMessage("file.upload.not.allowed", pPath));
        }
      }
    }
    return error(mMessageResource.getMessage("file.upload.not.allowed", pPath));
  }

  @Override
  public Map<String, Object> download(String pPath, String pToken, Domain pDomain,
      String... pRootPath) {
    // TODO: Check if student has permission
    return super.download(pPath, pToken, pDomain, pRootPath);
  }

  @Override
  public Map<String, Object> downloadAsZip(List<String> pItems, String pNewFileName, String pToken,
      Domain pDomain, String... pRootPath) {
    // TODO: Check if student has permission
    return super.downloadAsZip(pItems, pNewFileName, pToken, pDomain, pRootPath);
  }

  @Override
  public Map<String, Object> compress(List<String> pItems, String pNewPath, String pNewFileName,
      Domain pDomain, String... pRootPath) {
    return error(mMessageResource.getMessage("compress.not.allowed"));
  }

  protected Student getStudent() throws Exception {
    String studentId = SecurityUtils.getSubject().getPrincipal().toString();
    return mStudentManager.get(studentId);
  }
}
