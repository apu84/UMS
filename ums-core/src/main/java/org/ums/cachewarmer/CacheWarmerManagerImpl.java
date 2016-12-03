package org.ums.cachewarmer;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.cache.CacheFactory;
import org.ums.configuration.UMSConfiguration;
import org.ums.domain.model.immutable.Examiner;
import org.ums.domain.model.mutable.MutableExaminer;
import org.ums.manager.*;

@Component
public class CacheWarmerManagerImpl implements CacheWarmerManager {
  private static final Logger mLogger = LoggerFactory.getLogger(CacheWarmerManagerImpl.class);

  @Autowired
  UMSConfiguration mUMSConfiguration;

  @Autowired
  CacheFactory mCacheFactory;

  @Autowired
  DepartmentManager mDepartmentManager;

  @Autowired
  RoleManager mRoleManager;

  @Autowired
  UserManager mUserManager;

  @Autowired
  PermissionManager mPermissionManager;

  @Autowired
  BearerAccessTokenManager mBearerAccessTokenManager;

  @Autowired
  AdditionalRolePermissionsManager mAdditionalRolePermissionsManager;

  @Autowired
  NavigationManager mNavigationManager;

  @Autowired
  EmployeeManager mEmployeeManager;

  @Autowired
  ProgramTypeManager mProgramTypeManager;

  @Autowired
  ProgramManager mProgramManager;

  @Autowired
  SemesterManager mSemesterManager;

  @Autowired
  SyllabusManager mSyllabusManager;

  @Autowired
  CourseGroupManager mCourseGroupManager;

  @Autowired
  CourseManager mCourseManager;

  @Autowired
  EquivalentCourseManager mEquivalentCourseManager;

  @Autowired
  TeacherManager mTeacherManager;

  @Autowired
  CourseTeacherManager mCourseTeacherManager;

  @Autowired
  AssignedTeacherManager<Examiner, MutableExaminer, Integer> mExaminerManager;

  @Autowired
  StudentManager mStudentManager;

  @Autowired
  StudentRecordManager mStudentRecordManager;

  @Autowired
  ClassRoomManager mClassRoomManager;

  @Autowired
  MarksSubmissionStatusManager mMarksSubmissionStatusManager;

  @Autowired
  @Qualifier("backendSecurityManager")
  SecurityManager mSecurityManager;

  private CacheWarmer mCacheWarmer;

  @Override
  public void warm() {
    warm(false);
  }

  @Override
  public void warm(boolean force) {
    if(mUMSConfiguration.isEnableCacheWarmer() && login()) {
      CacheManager cacheManager = mCacheFactory.getCacheManager();
      if(force || cacheManager.get(WARMER_KEY) == null
          || ((CacheWarmer) cacheManager.get(WARMER_KEY)).getState() == CacheWarmer.State.NONE) {

        mCacheWarmer = new CacheWarmer(CacheWarmer.State.IN_PROGRESS);

        cacheManager.put(WARMER_KEY, mCacheWarmer);

        try {
          // start warming up
          mLogger.info("Started warming up cache");
          mDepartmentManager.getAll();
          mRoleManager.getAll();
          mPermissionManager.getAll();
          mBearerAccessTokenManager.getAll();
          mAdditionalRolePermissionsManager.getAll();
          mNavigationManager.getAll();
          mEmployeeManager.getAll();
          mProgramTypeManager.getAll();
          mProgramManager.getAll();
          mSemesterManager.getAll();
          mSyllabusManager.getAll();
          mCourseGroupManager.getAll();
          mEquivalentCourseManager.getAll();
          mTeacherManager.getAll();
          mCourseTeacherManager.getAll();
          mExaminerManager.getAll();
          mStudentManager.getAll();
          mStudentRecordManager.getAll();
          mClassRoomManager.getAll();
          mMarksSubmissionStatusManager.getAll();
          mCourseManager.getAll();
          mUserManager.getAll();

          mCacheWarmer = new CacheWarmer(CacheWarmer.State.WARMED);
          cacheManager.put(WARMER_KEY, mCacheWarmer);
          mLogger.info("Cache warm up finish");

        } catch(Exception e) {
          mCacheWarmer = new CacheWarmer(CacheWarmer.State.NONE);
          cacheManager.put(WARMER_KEY, mCacheWarmer);
          mLogger.error("Failed to warm up cache properly, will now fallback to initial state");
        }
      }
    }
  }

  protected boolean login() {
    SecurityUtils.setSecurityManager(mSecurityManager);
    Subject subject = SecurityUtils.getSubject();
    UsernamePasswordToken token =
        new UsernamePasswordToken(mUMSConfiguration.getBackendUser(),
            mUMSConfiguration.getBackendUserPassword());

    try {
      // Authenticate the subject
      subject.login(token);
      return true;
    } catch(Exception e) {
      mLogger.error("Exception whiile login using back end user ", e);
    }
    return false;
  }
}
