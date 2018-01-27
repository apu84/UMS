package org.ums.academic.tabulation.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.ums.academic.tabulation.model.TabulationEntryModel;
import org.ums.academic.tabulation.model.TabulationReportModel;
import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.StudentRecord;
import org.ums.enums.CourseType;
import org.ums.manager.*;
import org.ums.tabulation.TabulationCourseModel;

public class TabulationServiceImpl implements TabulationService {
  private UGRegistrationResultManager mResultManager;
  private SemesterManager mSemesterManager;
  private StudentRecordManager mStudentRecordManager;
  private StudentManager mStudentManager;
  private CourseManager mCourseManager;
  private ProgramManager mProgramManager;

  public TabulationServiceImpl(UGRegistrationResultManager pResultManager, SemesterManager pSemesterManager,
      StudentRecordManager pStudentRecordManager, StudentManager pStudentManager, CourseManager pCourseManager,
      ProgramManager pProgramManager) {
    mResultManager = pResultManager;
    mSemesterManager = pSemesterManager;
    mStudentRecordManager = pStudentRecordManager;
    mStudentManager = pStudentManager;
    mCourseManager = pCourseManager;
    mProgramManager = pProgramManager;
  }

  @Override
  public TabulationReportModel getTabulation(int pProgramId, int pSemesterId, int pYear, int pAcademicSemester) {
    Map<String, TabulationCourseModel> resultList =
        mResultManager.getResultForTabulation(pProgramId, pSemesterId, pYear, pAcademicSemester);
    List<TabulationEntryModel> entries = new ArrayList<>();
    for(String studentId : resultList.keySet()) {
      TabulationEntryModel entry = new TabulationEntryModel();
      entry.setStudent(mStudentManager.get(studentId));
      entry.setRegularCourseList(resultList.get(studentId).getRegularCoursesForCurrentSemester());
      entry.setClearanceCourseList(resultList.get(studentId).getClearanceCoursesForCurrentSemester());
      entry.setCarryCourseList(resultList.get(studentId).getCarryCoursesForCurrentSemester());
      entry.setFailedCoursesCurrentSemesterList(resultList.get(studentId).getFailedCoursesForCurrentSemester());
      entry.setFailedCoursesPreviousSemesterList(resultList.get(studentId).getFailedCourseForPreviousSemester());
      StudentRecord studentRecord = mStudentRecordManager.getStudentRecord(studentId, pSemesterId);
      entry.setCgpa(studentRecord.getCGPA());
      entry.setGpa(studentRecord.getGPA());
      entry.setCumulativeCrHr(studentRecord.getTotalCompletedCrHr());
      entry.setCumulativeGradePoints(studentRecord.getTotalCompletedGradePoints());
      entry.setPresentCompletedCrHr(studentRecord.getCompletedCrHr());
      entry.setPresentCompletedGradePoints(studentRecord.getCompletedGradePoints());
      entry.setPreviousSemesterCompletedCrHr(entry.getCumulativeCrHr() - entry.getPresentCompletedCrHr());
      entry.setPreviousSemesterCompletedGradePoints(
          entry.getCumulativeGradePoints() - entry.getPresentCompletedGradePoints());
      entry.setRemarks(studentRecord.getTabulationSheetRemarks());
      entries.add(entry);
    }
    TabulationReportModel reportModel = new TabulationReportModel();
    reportModel.setTabulationEntries(entries);
    reportModel.setSemester(mSemesterManager.get(pSemesterId));
    reportModel.setYear(pYear);
    reportModel.setAcademicSemester(pAcademicSemester);
    reportModel.setDepartment(mProgramManager.get(pProgramId).getDepartment());
    List<Course> courseList =
        mCourseManager.getSemesterWiseCourseList(pProgramId, pSemesterId, pYear, pAcademicSemester);
    reportModel.setTheoryCourses(courseList.stream().filter((pCourse -> pCourse.getCourseType() == CourseType.THEORY))
        .collect(Collectors.toList()));
    reportModel
        .setSessionalCourses(courseList.stream().filter((pCourse -> pCourse.getCourseType() == CourseType.SESSIONAL
            || pCourse.getCourseType() == CourseType.THESIS_PROJECT)).collect(Collectors.toList()));
    return reportModel;
  }
}
