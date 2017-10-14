package org.ums.microservice.configuration;

import org.apache.shiro.mgt.SecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.ums.cache.CacheFactory;
import org.ums.configuration.KafkaProducerConfig;
import org.ums.configuration.UMSConfiguration;
import org.ums.configuration.UMSContext;
import org.ums.fee.payment.StudentPaymentManager;
import org.ums.lock.LockManager;
import org.ums.manager.*;
import org.ums.manager.library.RecordManager;
import org.ums.manager.meeting.AgendaResolutionManager;
import org.ums.microservice.instance.cachewarmer.CacheWarmerManagerImpl;
import org.ums.microservice.instance.consumeindex.ConsumeIndex;
import org.ums.microservice.instance.consumeindex.ConsumeIndexJobImpl;
import org.ums.microservice.instance.paymentvalidator.PaymentValidator;
import org.ums.microservice.instance.paymentvalidator.PaymentValidatorJob;
import org.ums.solr.indexer.manager.IndexConsumerManager;
import org.ums.solr.indexer.manager.IndexManager;
import org.ums.solr.indexer.resolver.EntityResolverFactory;
import org.ums.usermanagement.permission.AdditionalRolePermissionsManager;
import org.ums.usermanagement.permission.PermissionManager;
import org.ums.usermanagement.role.RoleManager;
import org.ums.usermanagement.user.UserManager;

@Configuration
@EnableAsync
@EnableScheduling
@ComponentScan(basePackages = "org.ums")
@Import({UMSContext.class})
@ImportResource({"classpath*:services-context.xml", "classpath*:spring-config-shiro.xml"})
public class ServiceContext {
  @Autowired
  private SecurityManager mSecurityManager;
  @Autowired
  private StudentPaymentManager mStudentPaymentManager;
  @Autowired
  private UMSConfiguration mUMSConfiguration;
  @Autowired
  private IndexManager mIndexManager;
  @Autowired
  private IndexConsumerManager mIndexConsumerManager;
  @Autowired
  private LockManager mLockManager;
  @Autowired
  private EntityResolverFactory mEntityResolverFactory;
  @Autowired
  private CacheFactory mCacheFactory;
  @Autowired
  private DepartmentManager mDepartmentManager;
  @Autowired
  private RoleManager mRoleManager;
  @Autowired
  private PermissionManager mPermissionManager;
  @Autowired
  private BearerAccessTokenManager mBearerAccessTokenManager;
  @Autowired
  private AdditionalRolePermissionsManager mAdditionalRolePermissionsManager;
  @Autowired
  private NavigationManager mNavigationManager;
  @Autowired
  private EmployeeManager mEmployeeManager;
  @Autowired
  private ProgramTypeManager mProgramTypeManager;
  @Autowired
  private ProgramManager mProgramManager;
  @Autowired
  private SemesterManager mSemesterManager;
  @Autowired
  private SyllabusManager mSyllabusManager;
  @Autowired
  private CourseGroupManager mCourseGroupManager;
  @Autowired
  private EquivalentCourseManager mEquivalentCourseManager;
  @Autowired
  private TeacherManager mTeacherManager;
  @Autowired
  private CourseTeacherManager mCourseTeacherManager;
  @Autowired
  private ExaminerManager mExaminerManager;
  @Autowired
  private StudentManager mStudentManager;
  @Autowired
  private StudentRecordManager mStudentRecordManager;
  @Autowired
  private ClassRoomManager mClassRoomManager;
  @Autowired
  private CourseManager mCourseManager;
  @Autowired
  private MarksSubmissionStatusManager mMarksSubmissionStatusManager;
  @Autowired
  private UserManager mUserManager;
  @Autowired
  private RecordManager mRecordManager;
  @Autowired
  AgendaResolutionManager mAgendaResolutionManager;

  @Bean
  PaymentValidator paymentValidator() {
    return new PaymentValidatorJob(mStudentPaymentManager, mSecurityManager, mUMSConfiguration);
  }

  @Bean
  ConsumeIndex consumeIndex() {
    return new ConsumeIndexJobImpl(mIndexManager, mIndexConsumerManager, mEntityResolverFactory, mLockManager,
        mSecurityManager, mUMSConfiguration);
  }

  @Bean
  CacheWarmerManager cacheWarmerManager() {
    return new CacheWarmerManagerImpl(mSecurityManager, mUMSConfiguration, mDepartmentManager, mRoleManager,
        mPermissionManager, mBearerAccessTokenManager, mAdditionalRolePermissionsManager, mNavigationManager,
        mEmployeeManager, mProgramTypeManager, mProgramManager, mSemesterManager, mSyllabusManager,
        mCourseGroupManager, mEquivalentCourseManager, mTeacherManager, mCourseTeacherManager, mExaminerManager,
        mStudentManager, mStudentRecordManager, mClassRoomManager, mCourseManager, mMarksSubmissionStatusManager,
        mUserManager, mRecordManager);
    //, mAgendaResolutionManager
  }
}
