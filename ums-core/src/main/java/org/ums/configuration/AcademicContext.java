package org.ums.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.ums.academic.tabulation.service.TabulationService;
import org.ums.academic.tabulation.service.TabulationServiceImpl;
import org.ums.cache.*;
import org.ums.cache.applications.AppConfigCache;
import org.ums.cache.applications.AppRulesCache;
import org.ums.cache.routine.RoutineCache;
import org.ums.cache.routine.RoutineConfigCache;
import org.ums.fee.semesterfee.SemesterAdmissionCache;
import org.ums.fee.semesterfee.SemesterAdmissionDao;
import org.ums.fee.semesterfee.SemesterAdmissionStatusManager;
import org.ums.generator.IdGenerator;
import org.ums.generator.JxlsGenerator;
import org.ums.generator.XlsGenerator;
import org.ums.manager.*;
import org.ums.manager.applications.AppConfigManager;
import org.ums.manager.applications.AppRulesManager;
import org.ums.manager.routine.RoutineConfigManager;
import org.ums.manager.routine.RoutineManager;
import org.ums.message.MessageResource;
import org.ums.persistent.dao.*;
import org.ums.persistent.dao.applications.PersistentAppConfigDao;
import org.ums.persistent.dao.applications.PersistentAppRulesDao;
import org.ums.persistent.model.PersistentAbsLateComingInfoDao;
import org.ums.persistent.dao.routine.PersistentRoutineConfigDao;
import org.ums.persistent.dao.routine.PersistentRoutineDao;
import org.ums.punishment.PersistentPunishmentDao;
import org.ums.punishment.PunishmentCache;
import org.ums.punishment.PunishmentManager;
import org.ums.punishment.authority.AuthorityCache;
import org.ums.punishment.authority.AuthorityManager;
import org.ums.punishment.authority.PersistentAuthorityDao;
import org.ums.punishment.offence.OffenceCache;
import org.ums.punishment.offence.OffenceManager;
import org.ums.punishment.offence.PersistentOffenceDao;
import org.ums.punishment.penalty.PenaltyCache;
import org.ums.punishment.penalty.PenaltyManager;
import org.ums.punishment.penalty.PersistentPenaltyDao;
import org.ums.readmission.ReadmissionApplicationDao;
import org.ums.readmission.ReadmissionApplicationManager;
import org.ums.result.gradesheet.GradeSheetDao;
import org.ums.result.gradesheet.GradeSheetManager;
import org.ums.result.legacy.LegacyTabulationDao;
import org.ums.result.legacy.LegacyTabulationManager;
import org.ums.services.academic.RemarksBuilder;
import org.ums.services.academic.RemarksBuilderImpl;
import org.ums.services.academic.StudentCarryCourseService;
import org.ums.statistics.JdbcTemplateFactory;
import org.ums.statistics.NamedParameterJdbcTemplateFactory;

@Configuration("academicConfig")
public class AcademicContext {
  @Autowired
  CacheFactory mCacheFactory;

  @Autowired
  JdbcTemplateFactory mTemplateFactory;

  @Autowired
  NamedParameterJdbcTemplateFactory mNamedParameterJdbcTemplateFactory;

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

  @Autowired
  StudentCarryCourseService mStudentCarryCourseService;

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
  RoutineConfigManager routineConfigManager() {
    RoutineConfigCache routineConfigCache = new RoutineConfigCache(mCacheFactory.getCacheManager());
    routineConfigCache.setManager(new PersistentRoutineConfigDao(mTemplateFactory.getJdbcTemplate(),
        mNamedParameterJdbcTemplateFactory.getNamedParameterJdbcTemplate(), mIdGenerator));
    return routineConfigCache;
  }

  @Bean
  RoutineManager routineManager() {
    RoutineCache routineCache = new RoutineCache(mCacheFactory.getCacheManager());
    routineCache.setManager(new PersistentRoutineDao(mTemplateFactory.getJdbcTemplate(),
        mNamedParameterJdbcTemplateFactory.getNamedParameterJdbcTemplate(), mIdGenerator));
    return routineCache;
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
    return new PersistentSeatPlanDao(mTemplateFactory.getJdbcTemplate(), mIdGenerator);
  }

  @Bean
  ApplicationCCIManager applicationCCIManager() {
    ApplicationCCICache applicationCCICache = new ApplicationCCICache(mCacheFactory.getCacheManager());
    applicationCCICache.setManager(new PersistentApplicationCCIDao(mTemplateFactory.getJdbcTemplate(), mIdGenerator));
    return applicationCCICache;
  }

  @Bean
  ExpelledInformationManager expelledInformationManager() {
    ExpelledInformationCache expelledInformationCache = new ExpelledInformationCache(mCacheFactory.getCacheManager());
    expelledInformationCache.setManager(new PersistentExpelledInformationDao(mTemplateFactory.getJdbcTemplate(),
        mIdGenerator));
    return expelledInformationCache;
  }

  @Bean
  StudentsExamAttendantInfoManager studentsExamAttendantInfoManager() {
    StudentsExamAttendantInfoCache studentsExamAttendantInfoCache =
        new StudentsExamAttendantInfoCache(mCacheFactory.getCacheManager());
    studentsExamAttendantInfoCache.setManager(new PersistentStudentsExamAttendantInfoDao(mTemplateFactory
        .getJdbcTemplate(), mIdGenerator));
    return studentsExamAttendantInfoCache;
  }

  @Bean
  AbsLateComingInfoManager absLateComingInfoManager() {
    AbsLateComingInfoCache absLateComingInfoCache = new AbsLateComingInfoCache(mCacheFactory.getCacheManager());
    absLateComingInfoCache.setManager(new PersistentAbsLateComingInfoDao(mTemplateFactory.getJdbcTemplate(),
        mIdGenerator));
    return absLateComingInfoCache;
  }

  @Bean
  QuestionCorrectionManager questionCorrectionManager() {
    QuestionCorrectionInfoCache questionCorrectionInfoCache =
        new QuestionCorrectionInfoCache(mCacheFactory.getCacheManager());
    questionCorrectionInfoCache.setManager(new PersistentQuestionCorrectionDao(mTemplateFactory.getJdbcTemplate(),
        mIdGenerator));
    return questionCorrectionInfoCache;
  }

  @Bean
  ApplicationTESManager applicationTESManager() {
    ApplicationTESCache applicationTESCache = new ApplicationTESCache((mCacheFactory.getCacheManager()));
    applicationTESCache.setManager(new PersistentApplicationTESDao(mTemplateFactory.getJdbcTemplate(),
        mNamedParameterJdbcTemplateFactory.getNamedParameterJdbcTemplate(), mIdGenerator));
    return applicationTESCache;
  }

  @Bean
  ApplicationTesQuestionManager applicationTesQuestionManager() {
    ApplicationTesQuestionCache applicationTesQuestionCache =
        new ApplicationTesQuestionCache((mCacheFactory.getCacheManager()));
    applicationTesQuestionCache.setManager(new PersistentApplicationTesQuestionDao(mTemplateFactory.getJdbcTemplate(),
        mIdGenerator));
    return applicationTesQuestionCache;
  }

  @Bean
  ApplicationTesSetQuestionManager applicationTesSetQuestionManager() {
    ApplicationTesSetQuestionCache applicationTesSetQuestionCache =
        new ApplicationTesSetQuestionCache((mCacheFactory.getCacheManager()));
    applicationTesSetQuestionCache.setManager(new PersistentApplicationTesSetQuestionDao(mTemplateFactory
        .getJdbcTemplate(), mIdGenerator));
    return applicationTesSetQuestionCache;
  }

  @Bean
  ApplicationTesSelectedCourseManager applicationTesSelectedCourseManager() {
    ApplicationTesSelectedCoursesCache applicationTesSelectedCoursesCache =
        new ApplicationTesSelectedCoursesCache((mCacheFactory.getCacheManager()));
    applicationTesSelectedCoursesCache.setManager(new PersistentApplicationTesSelectedCoursesDao(mTemplateFactory
        .getJdbcTemplate(), mIdGenerator));
    return applicationTesSelectedCoursesCache;
  }

  @Bean
  EmpExamAttendanceManager empExamAttendanceManager() {
    EmpExamAttendanceCache empExamAttendanceCache = new EmpExamAttendanceCache((mCacheFactory.getCacheManager()));
    empExamAttendanceCache.setManager(new PersistentEmpExamAttendanceDao(mTemplateFactory.getJdbcTemplate(),
        mIdGenerator));
    return empExamAttendanceCache;
  }

  @Bean
  EmpExamInvigilatorDateManager empExamInvigilatorDateManager() {
    EmpExamInvigilatorDateCache empExamInvigilatorDateCache =
        new EmpExamInvigilatorDateCache((mCacheFactory.getCacheManager()));
    empExamInvigilatorDateCache.setManager(new PersistentEmpExamInvigilatorDateDao(mTemplateFactory.getJdbcTemplate(),
        mIdGenerator));
    return empExamInvigilatorDateCache;
  }

  @Bean
  EmpExamReserveDateManager empExamReserveDateManager() {
    EmpExamReserveDateCache empExamReserveDateCache = new EmpExamReserveDateCache((mCacheFactory.getCacheManager()));
    empExamReserveDateCache.setManager(new PersistentEmpExamReserveDateDao(mTemplateFactory.getJdbcTemplate(),
        mIdGenerator));
    return empExamReserveDateCache;
  }

  @Bean
  CourseTeacherManager courseTeacherManager() {
    CourseTeacherCache courseTeacherCache = new CourseTeacherCache(mCacheFactory.getCacheManager());
    courseTeacherCache.setManager(new PersistentCourseTeacherDao(mTemplateFactory.getJdbcTemplate(),
        mNamedParameterJdbcTemplateFactory.getNamedParameterJdbcTemplate(), mIdGenerator));
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
  ExamGradeManager examGradeManager() {
    return new PersistentExamGradeDao(mTemplateFactory.getJdbcTemplate());
  }

  @Bean
  UGRegistrationResultManager registrationResultManager() {
    UGRegistrationResultAggregator resultAggregator =
        new UGRegistrationResultAggregator(equivalentCourseManager(), mCoreContext.taskStatusManager(),
            semesterManager(), mStudentCarryCourseService);
    UGRegistrationResultCache registrationResultCache = new UGRegistrationResultCache(mCacheFactory.getCacheManager());
    registrationResultCache.setManager(new PersistentUGRegistrationResultDao(mTemplateFactory.getJdbcTemplate(),
        mNamedParameterJdbcTemplateFactory.getNamedParameterJdbcTemplate(), mIdGenerator));
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

  @Bean
  AppConfigManager appConfigManager() {
    AppConfigCache cache = new AppConfigCache(mCacheFactory.getCacheManager());
    cache.setManager(new PersistentAppConfigDao(mTemplateFactory.getJdbcTemplate(), mIdGenerator));
    return cache;
  }

  @Bean
  AppRulesManager appRulesManager() {
    AppRulesCache cache = new AppRulesCache(mCacheFactory.getCacheManager());
    cache.setManager(new PersistentAppRulesDao(mTemplateFactory.getJdbcTemplate(), mIdGenerator));
    return cache;
  }

  @Bean
  TabulationService tabulationService() {
    return new TabulationServiceImpl(registrationResultManager(), semesterManager(), studentRecordManager(),
        mCoreContext.studentManager(), courseManager(), programManager());
  }

  @Bean
  LegacyTabulationManager legacyTabulationManager() {
    return new LegacyTabulationDao(mTemplateFactory.getJdbcTemplate());
  }

  @Bean
  OffenceManager offenceManager() {
    OffenceCache offenceCache = new OffenceCache(mCacheFactory.getCacheManager());
    offenceCache.setManager(new PersistentOffenceDao(mTemplateFactory.getJdbcTemplate(), mIdGenerator));
    return offenceCache;
  }

  @Bean
  PenaltyManager penaltyManager() {
    PenaltyCache penaltyCache = new PenaltyCache(mCacheFactory.getCacheManager());
    penaltyCache.setManager(new PersistentPenaltyDao(mTemplateFactory.getJdbcTemplate(), mIdGenerator));
    return penaltyCache;
  }

  @Bean
  AuthorityManager authorityManager() {
    AuthorityCache authorityCache = new AuthorityCache(mCacheFactory.getCacheManager());
    authorityCache.setManager(new PersistentAuthorityDao(mTemplateFactory.getJdbcTemplate(), mIdGenerator));
    return authorityCache;
  }

  @Bean
  PunishmentManager punishmentManager() {
    PunishmentCache punishmentCache = new PunishmentCache(mCacheFactory.getCacheManager());
    punishmentCache.setManager(new PersistentPunishmentDao(mTemplateFactory.getJdbcTemplate(), mIdGenerator));
    return punishmentCache;
  }

  @Bean
  GradeSheetManager gradeSheetManager() {
    return new GradeSheetDao(mCoreContext.studentManager(), studentRecordManager(), registrationResultManager(),
        mIdGenerator, mCoreContext.taskStatusManager(), marksSubmissionStatusManager());
  }
}
