package org.ums.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.ums.cache.*;
import org.ums.fee.semesterfee.SemesterAdmissionCache;
import org.ums.fee.semesterfee.SemesterAdmissionDao;
import org.ums.fee.semesterfee.SemesterAdmissionStatusManager;
import org.ums.generator.IdGenerator;
import org.ums.generator.JxlsGenerator;
import org.ums.generator.XlsGenerator;
import org.ums.manager.*;
import org.ums.message.MessageResource;
import org.ums.persistent.dao.*;
import org.ums.readmission.ReadmissionApplicationDao;
import org.ums.readmission.ReadmissionApplicationManager;
import org.ums.services.academic.RemarksBuilder;
import org.ums.services.academic.RemarksBuilderImpl;
import org.ums.statistics.JdbcTemplateFactory;

@Configuration
public class AcademicContext {
  @Autowired
  CacheFactory mCacheFactory;

  @Autowired
  JdbcTemplateFactory mTemplateFactory;

  @Autowired
  IdGenerator mIdGenerator;

  @Autowired
  UMSConfiguration mUMSConfiguration;

  @Autowired
  @Qualifier("mongoTemplate")
  @Lazy
  MongoTemplate mMongoOperations;

  @Autowired
  CoreContext mCoreContext;

  @Autowired
  MessageResource mMessageResource;

  @Autowired
  @Qualifier("fileContentManager")
  BinaryContentManager<byte[]> mBinaryContentManager;

  @Bean
  FacultyManager facultyManager() {
    FacultyCache facultyCache = new FacultyCache(mCacheFactory.getCacheManager());
    facultyCache.setManager(new PersistentFacultyDao(mTemplateFactory.getJdbcTemplate()));
    return facultyCache;
  }

  @Bean
  ProgramTypeManager programTypeManager() {
    ProgramTypeCache programTypeCache = new ProgramTypeCache(mCacheFactory.getCacheManager());
    programTypeCache.setManager(new PersistentProgramTypeDao(mTemplateFactory.getJdbcTemplate()));
    return programTypeCache;
  }

  @Bean
  ProgramManager programManager() {
    ProgramCache programCache = new ProgramCache(mCacheFactory.getCacheManager());
    programCache.setManager(new PersistentProgramDao(mTemplateFactory.getJdbcTemplate()));
    return programCache;
  }

  @Bean
  DepartmentManager departmentManager() {
    DepartmentCache departmentCache = new DepartmentCache(mCacheFactory.getCacheManager());
    departmentCache.setManager(new PersistentDepartmentDao(mTemplateFactory.getJdbcTemplate()));
    return departmentCache;
  }

  @Bean
  SyllabusManager syllabusManager() {
    SyllabusCache syllabusCache = new SyllabusCache(mCacheFactory.getCacheManager());
    syllabusCache.setManager(new PersistentSyllabusDao(mTemplateFactory.getJdbcTemplate()));
    return syllabusCache;
  }

  @Bean
  CourseGroupManager courseGroupManager() {
    CourseGroupCache courseGroupCache = new CourseGroupCache(mCacheFactory.getCacheManager());
    courseGroupCache.setManager(new PersistentCourseGroupDao(mTemplateFactory.getJdbcTemplate()));
    return courseGroupCache;
  }

  @Bean
  CourseManager courseManager() {
    CourseCache courseCache = new CourseCache(mCacheFactory.getCacheManager());
    courseCache.setManager(new PersistentCourseDao(mTemplateFactory.getJdbcTemplate()));
    return courseCache;
  }

  @Bean
  TeacherManager teacherManager() {
    TeacherCache teacherCache = new TeacherCache(mCacheFactory.getCacheManager());
    teacherCache.setManager(new PersistentTeacherDao(mTemplateFactory.getJdbcTemplate()));
    return teacherCache;
  }

  @Bean
  SemesterSyllabusMapManager semesterSyllabusMapManager() {
    SemesterSyllabusMapCache semesterSyllabusMapCache = new SemesterSyllabusMapCache(mCacheFactory.getCacheManager());
    semesterSyllabusMapCache.setManager(new PersistentSemesterSyllabusMapDao(mTemplateFactory.getJdbcTemplate(),
        syllabusManager()));
    return semesterSyllabusMapCache;
  }

  @Bean
  StudentRecordManager studentRecordManager() {
    StudentRecordCache studentRecordCache = new StudentRecordCache(mCacheFactory.getCacheManager());
    studentRecordCache.setManager(new PersistentStudentRecordDao(mTemplateFactory.getJdbcTemplate(), mIdGenerator));
    return studentRecordCache;
  }

  @Bean
  SemesterManager semesterManager() {
    SemesterCache semesterCache = new SemesterCache(mCacheFactory.getCacheManager());
    semesterCache.setManager(new PersistentSemesterDao(mTemplateFactory.getJdbcTemplate()));
    return semesterCache;
  }

  @Bean
  ClassRoomManager classRoomManager() {
    ClassRoomCache classRoomCache = new ClassRoomCache(mCacheFactory.getCacheManager());
    classRoomCache.setManager(new PersistentClassRoomDao(mTemplateFactory.getJdbcTemplate(), mIdGenerator));
    return classRoomCache;
  }

  @Bean
  SemesterWithDrawalManager semesterWithdrawalManager() {
    SemesterWithdrawalCache semesterWithdrawalCache = new SemesterWithdrawalCache(mCacheFactory.getCacheManager());
    semesterWithdrawalCache.setManager(new PersistentSemesterWithdrawalDao(mTemplateFactory.getJdbcTemplate(),
        mIdGenerator));
    return semesterWithdrawalCache;
  }

  @Bean
  SemesterWithdrawalLogManager semesterWithdrawalLogManager() {
    SemesterWithdrawalLogCache semesterWithdrawalLogCache =
        new SemesterWithdrawalLogCache(mCacheFactory.getCacheManager());
    semesterWithdrawalLogCache.setManager(new PersistentSemesterWithdrawalLogDao(mTemplateFactory.getJdbcTemplate(),
        mIdGenerator));
    return semesterWithdrawalLogCache;
  }

  @Bean
  SeatPlanPublishManager seatPlanPublishManager() {
    SeatPlanPublishCache seatPlanPublishCache = new SeatPlanPublishCache(mCacheFactory.getCacheManager());
    seatPlanPublishCache.setManager(new PersistentSeatPlanPublishDao(mTemplateFactory.getJdbcTemplate()));
    return seatPlanPublishCache;
  }

  @Bean
  SubGroupCCIManager subGroupCCIManager() {
    SubGroupCCICache subGroupCCICache = new SubGroupCCICache(mCacheFactory.getCacheManager());
    subGroupCCICache.setManager(new PersistentSubGroupCCIDao(mTemplateFactory.getJdbcTemplate()));
    return subGroupCCICache;
  }

  @Bean
  SubGroupManager subGroupManager() {
    SubGroupCache subGroupCache = new SubGroupCache(mCacheFactory.getCacheManager());
    subGroupCache.setManager(new PersistentSubGroupDao(mTemplateFactory.getJdbcTemplate()));
    return subGroupCache;
  }

  @Bean
  SeatPlanManager seatPlanManager() {
    SeatPlanCache seatPlanCache = new SeatPlanCache(mCacheFactory.getCacheManager());
    seatPlanCache.setManager(new PersistentSeatPlanDao(mTemplateFactory.getJdbcTemplate(), mIdGenerator));
    return seatPlanCache;
  }

  @Bean
  ApplicationCCIManager applicationCCIManager() {
    ApplicationCCICache applicationCCICache = new ApplicationCCICache(mCacheFactory.getCacheManager());
    applicationCCICache.setManager(new PersistentApplicationCCIDao(mTemplateFactory.getJdbcTemplate(), mIdGenerator));
    return applicationCCICache;
  }

  @Bean
  CourseTeacherManager courseTeacherManager() {
    CourseTeacherCache courseTeacherCache = new CourseTeacherCache(mCacheFactory.getCacheManager());
    courseTeacherCache.setManager(new PersistentCourseTeacherDao(mTemplateFactory.getJdbcTemplate(), mIdGenerator));
    return courseTeacherCache;
  }

  @Bean
  ExaminerManager examinerManager() {
    ExaminerCache examinerCache = new ExaminerCache(mCacheFactory.getCacheManager());
    examinerCache.setManager(new PersistentExaminerDao(mTemplateFactory.getJdbcTemplate(), mIdGenerator));
    return examinerCache;
  }

  @Bean
  SpStudentManager spStudentManager() {
    SpStudentCache spStudentCache = new SpStudentCache(mCacheFactory.getCacheManager());
    spStudentCache.setManager(new PersistentSpStudentDao(mTemplateFactory.getJdbcTemplate()));
    return spStudentCache;
  }

  @Bean
  SeatPlanGroupManager seatPlanGroupManager() {
    SeatPlanGroupCache seatPlanGroupCache = new SeatPlanGroupCache(mCacheFactory.getCacheManager());
    seatPlanGroupCache.setManager(new PersistentSeatPlanGroupDao(mTemplateFactory.getJdbcTemplate()));
    return seatPlanGroupCache;
  }

  @Bean
  SemesterEnrollmentManager semesterEnrollmentManager() {
    SemesterEnrollmentCache semesterEnrollmentCache = new SemesterEnrollmentCache(mCacheFactory.getCacheManager());
    semesterEnrollmentCache.setManager(new PersistentSemesterEnrollmentDao(mTemplateFactory.getJdbcTemplate(),
        mIdGenerator));
    return semesterEnrollmentCache;
  }

  @Bean
  EnrollmentFromToManager enrollmentFromToManager() {
    EnrollmentFromToCache enrollmentFromToCache = new EnrollmentFromToCache(mCacheFactory.getCacheManager());
    enrollmentFromToCache
        .setManager(new PersistentEnrollmentFromToDao(mTemplateFactory.getJdbcTemplate(), mIdGenerator));
    return enrollmentFromToCache;
  }

  @Bean
  ExamRoutineManager examRoutineManager() {
    return new PersistentExamRoutineDao(mTemplateFactory.getJdbcTemplate());
  }

  @Bean
  PersistentOptionalCourseApplicationDao persistentOptionalCourseApplicationDao() {
    return new PersistentOptionalCourseApplicationDao(mTemplateFactory.getJdbcTemplate());
  }

  @Bean
  PersistentSemesterWiseCrHrDao persistentSemesterWiseCrHrDao() {
    return new PersistentSemesterWiseCrHrDao(mTemplateFactory.getJdbcTemplate());
  }

  @Bean
  RoutineManager routineManager() {
    return new PersistentRoutineDao(mTemplateFactory.getJdbcTemplate(), mIdGenerator);
  }

  @Bean
  ExamGradeManager examGradeManager() {
    return new PersistentExamGradeDao(mTemplateFactory.getJdbcTemplate());
  }

  @Bean
  UGRegistrationResultManager registrationResultManager() {
    UGRegistrationResultAggregator resultAggregator =
        new UGRegistrationResultAggregator(equivalentCourseManager(), mCoreContext.taskStatusManager(),
            semesterManager());
    UGRegistrationResultCache registrationResultCache = new UGRegistrationResultCache(mCacheFactory.getCacheManager());
    registrationResultCache.setManager(new PersistentUGRegistrationResultDao(mTemplateFactory.getJdbcTemplate(),
        mIdGenerator));
    resultAggregator.setManager(registrationResultCache);
    return resultAggregator;
  }

  @Bean
  SeatPlanReportManager seatPlanReportManager() {
    return new PersistentSeatPlanReportDao(mTemplateFactory.getJdbcTemplate());
  }

  @Bean
  UGTheoryMarksManager theoryMarksManager() {
    return new PersistentUGTheoryMarksDao(mTemplateFactory.getJdbcTemplate(), mIdGenerator);
  }

  @Bean
  UGSessionalMarksManager sessionalMarksManager() {
    return new PersistentUGSessionalMarksDao(mTemplateFactory.getJdbcTemplate(), mIdGenerator);
  }

  @Bean
  XlsGenerator xlsGenerator() {
    return new JxlsGenerator();
  }

  @Bean
  @Lazy
  BinaryContentManager<byte[]> courseMaterialFileManagerForTeacher() {
    FileContentPermission fileContentPermission =
        new FileContentPermission(mCoreContext.userManager(), mCoreContext.bearerAccessTokenManager(),
            mUMSConfiguration, mMessageResource);
    CourseMaterialNotifier notifier =
        new CourseMaterialNotifier(mCoreContext.userManager(), mCoreContext.notificationGenerator(),
            registrationResultManager(), mUMSConfiguration, mMessageResource, courseTeacherManager(),
            mCoreContext.bearerAccessTokenManager());
    fileContentPermission.setManager(notifier);
    notifier.setManager(mBinaryContentManager);
    return fileContentPermission;
  }

  @Bean
  @Lazy
  BinaryContentManager<byte[]> courseMaterialFileManagerForStudent() {
    StudentFileContentPermission fileContentPermission =
        new StudentFileContentPermission(mCoreContext.userManager(), mCoreContext.bearerAccessTokenManager(),
            mUMSConfiguration, mMessageResource, mCoreContext.studentManager(), registrationResultManager(),
            courseTeacherManager());
    fileContentPermission.setManager(mBinaryContentManager);
    return fileContentPermission;
  }

  @Bean
  EquivalentCourseManager equivalentCourseManager() {
    EquivalentCourseCache equivalentCourseCache = new EquivalentCourseCache(mCacheFactory.getCacheManager());
    equivalentCourseCache.setManager(new EquivalentCourseDao(mTemplateFactory.getJdbcTemplate(), mIdGenerator));
    return equivalentCourseCache;
  }

  @Bean
  MarksSubmissionStatusManager marksSubmissionStatusManager() {
    MarksSubmissionStatusCache cache = new MarksSubmissionStatusCache(mCacheFactory.getCacheManager());
    MarksSubmissionStatusAggregator aggregator = new MarksSubmissionStatusAggregator();
    cache.setManager(aggregator);
    aggregator.setManager(new PersistentMarkSubmissionStatusDao(mTemplateFactory.getJdbcTemplate(), mIdGenerator));
    return cache;
  }

  @Bean
  ResultPublishManager resultPublishManager() {
    ResultPublishValidator validator = new ResultPublishValidator(marksSubmissionStatusManager());
    ResultPublishImpl resultPublish = new ResultPublishImpl();
    validator.setManager(resultPublish);
    resultPublish.setManager(new ResultPublishDao(mTemplateFactory.getJdbcTemplate(), mIdGenerator));
    return validator;
  }

  @Bean
  ClassAttendanceManager classAttendanceManager() {
    return new PersistentClassAttendanceDao(mTemplateFactory.getJdbcTemplate());
  }

  @Bean
  ReadmissionApplicationManager readmissionApplicationManager() {
    return new ReadmissionApplicationDao(mTemplateFactory.getJdbcTemplate(), mIdGenerator);
  }

  @Bean
  SemesterAdmissionStatusManager semesterAdmissionStatusManager() {
    SemesterAdmissionCache cache = new SemesterAdmissionCache(mCacheFactory.getCacheManager());
    cache.setManager(new SemesterAdmissionDao(mTemplateFactory.getJdbcTemplate(), mIdGenerator));
    return cache;
  }

  @Bean
  RemarksBuilder remarkBuilder() {
    return new RemarksBuilderImpl();
  }
}
