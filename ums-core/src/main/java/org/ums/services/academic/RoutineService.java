package org.ums.services.academic;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ums.domain.model.immutable.ClassRoom;
import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.immutable.CourseTeacher;
import org.ums.domain.model.immutable.routine.Routine;
import org.ums.domain.model.mutable.MutableCourseTeacher;
import org.ums.domain.model.mutable.routine.MutableRoutine;
import org.ums.enums.routine.DayType;
import org.ums.generator.IdGenerator;
import org.ums.manager.*;
import org.ums.manager.routine.RoutineManager;
import org.ums.persistent.model.PersistentCourseTeacher;
import org.ums.persistent.model.routine.PersistentRoutine;
import org.ums.services.academic.helper.RoutineTime;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Monjur-E-Morshed on 06-Sep-18.
 */
@Service
public class RoutineService {

  @Autowired
  private RoutineManager mRoutineManager;
  @Autowired
  private SemesterManager mSemesterManager;
  @Autowired
  private ProgramManager mProgramManager;
  @Autowired
  private CourseManager mCourseManager;
  @Autowired
  private ClassRoomManager mClassRoomManager;
  @Autowired
  private CourseTeacherManager mCourseTeacherManager;
  @Autowired
  private IdGenerator mIdGenerator;

  private Map<Integer, RoutineTime> columnMapWithTime = new HashMap<>();
  private Map<String, ClassRoom> classRoomMapWithRoomNo;
  private Map<String, Course> courseMapWithCourseNo;
  private Map<String, List<MutableCourseTeacher>> courseIdMapWithCourseTeacher;

  @Transactional
  public void extractWorkBook(Workbook pWorkbook, Integer pSemesterId, Integer pProgramId) throws Exception,
      IOException, InvalidFormatException {

    List<MutableRoutine> routineList = new ArrayList<>();
    courseIdMapWithCourseTeacher = new HashMap<>();
    createClassRoomMapWithRoomNo();
    createCourseMapWithCourseNo(pSemesterId, pProgramId);
    removeCourseTeacher(pSemesterId, pProgramId);

    // todo replacing pWorkbookNumberOfSheets with 1 for testing. Remove it after full test.
    for(int i = 0; i < 1; i++) {
      Sheet sheet = pWorkbook.getSheetAt(i);
      System.out.println(sheet.getSheetName() + " ");
      int year = extractYearFromSheetName(sheet.getSheetName());
      int semester = extractSemesterFromSheetName(sheet.getSheetName());
      String section = extractSectionFromSheetName(sheet.getSheetName());
      String globalRoomNo = extractRoomNumberFromSheetName(sheet.getSheetName());
      extractRoutineInformationFromSheet(sheet, pSemesterId, pProgramId, routineList, year, semester, section,
          globalRoomNo);
    }
  }

  private void removeCourseTeacher(Integer pSemesterId, Integer pProgramId){
    List<MutableCourseTeacher> courseTeacherList = mCourseTeacherManager.getCourseTeacher(pSemesterId, pProgramId)
        .stream()
        .map(p-> (MutableCourseTeacher) p)
        .collect(Collectors.toList());

    mCourseTeacherManager.delete(courseTeacherList);
  }

  private void createClassRoomMapWithRoomNo() {
    List<ClassRoom> classRoomList = mClassRoomManager.getAll();
    classRoomMapWithRoomNo = new HashMap<>();
    for(ClassRoom room : classRoomList) {
      classRoomMapWithRoomNo.put(room.getRoomNo(), room);
    }
  }

  private void createCourseMapWithCourseNo(Integer pSemesterId, Integer pProgramId) {
    List<Course> courseList = mCourseManager.getBySemesterProgram(pSemesterId.toString(), pProgramId.toString());
    courseMapWithCourseNo = new HashMap<>();
    for(Course course : courseList) {
      courseMapWithCourseNo.put(course.getNo(), course);
    }
  }

  private String extractRoomNumberFromSheetName(String pSheetName) {
    String[] sheetNameArr = pSheetName.split("-");
    if(sheetNameArr.length == 3)
      return "";
    else
      return sheetNameArr[3];
  }

  private int extractYearFromSheetName(String pSheetName) {
    String[] sheetNameArr = pSheetName.split("-");
    return Integer.parseInt(sheetNameArr[0]);
  }

  private int extractSemesterFromSheetName(String pSheetName) {
    String[] sheetNameArr = pSheetName.split("-");
    return Integer.parseInt(sheetNameArr[1]);
  }

  private String extractSectionFromSheetName(String pSheetName) {
    String[] sheetNameArr = pSheetName.split("-");
    return sheetNameArr[2];
  }

  private void extractRoutineInformationFromSheet(Sheet pSheet, Integer pSemesterId, Integer pProgramId,
      List<MutableRoutine> pRoutineList, int pYear, int pSemester, String pSection, String pGlobalRoomNo) {

    if(columnMapWithTime.isEmpty()) {
      Row row = pSheet.getRow(0);
      createColumnAndTimeMap(row);
    }
    else {
      // todo change 6 to 5 when there is two weekends.
      for(int i = 1; i <= 6; i++) {
        Row row = pSheet.getRow(i);
        extractRoutineInformationFromRow(row, pSemesterId, pProgramId, pRoutineList, pYear, pSemester, pSection,
            pGlobalRoomNo);
      }
    }
  }

  private void extractRoutineInformationFromRow(Row pRow, Integer pSemesterId, Integer pProgramId,
      List<MutableRoutine> pRoutineList, int pYear, int pSemester, String pSection, String pGlobalRoomNo) {
    String dayName = "";
    for(int i = 0; i < 13; i++) {
      if(i == 0) {
        dayName = pRow.getCell(i).toString();
      }
      else if(pRow.getCell(i) != null && pRow.getCell(i).toString().length() != 0) {
        List<MutableRoutine> cellRoutineList = new ArrayList<>();
        System.out.println("----Cell----> " + i);
        System.out.println(pRow.getCell(i).toString());
        String[] cellStrings = pRow.getCell(i).toString().split("\n");
        for(int k = 0; k < cellStrings.length; k++) {
          String[] courseStrings = cellStrings[k].split("|");
          for(String courseString : courseStrings) {
            MutableRoutine routine = new PersistentRoutine();
            courseString.replaceAll("\\[", "").replaceAll("\\]", "");
            String[] courseRoutineInfo = courseString.split(" ");
            routine.setCourseId(courseMapWithCourseNo.get(courseRoutineInfo[0]).getId());
            routine.setSection(courseRoutineInfo[1]);
            routine.setRoomId(classRoomMapWithRoomNo.get(courseRoutineInfo[2]).getId());
            routine.setSemesterId(pSemesterId);
            routine.setDay(DayType.getByLabel(dayName).getValue());
            routine.setProgramId(pProgramId);
            routine.setAcademicYear(pYear);
            routine.setAcademicSemester(pSemester);
            RoutineTime routineTime = columnMapWithTime.get(pRow.getCell(i).getColumnIndex());
            routine.setStartTime(routineTime.getStartTime());
            routine.setEndTime(routineTime.getEndTime());
            cellRoutineList.add(routine);
          }
          pRoutineList.addAll(cellRoutineList);
          char[] nextCellCharacterArray = cellStrings[k + 1].toCharArray();
          if(nextCellCharacterArray[0] == '[')
            extractCourseTeacherInfo(pSemesterId, pProgramId, cellRoutineList, cellStrings[k + 1]);
        }
      }

    }
  }

  private void extractCourseTeacherInfo(Integer pSemesterId, Integer pProgramId, List<MutableRoutine> pRoutineList,
      String cellCourseTeacherString) {
    cellCourseTeacherString.replaceAll("\\[", "").replaceAll("\\]", "");
    String[] courseTeacherListBasedOnCourse = cellCourseTeacherString.split("|");
    for(int i = 0; i < pRoutineList.size(); i++) {
      String[] courseTeacher = courseTeacherListBasedOnCourse[i].split(",");

    }
  }

  private void insertOrUpdateCourseTeacher(Integer pSemesterId, Integer pProgramId, Routine pRoutine,
      String[] courseTeacherIdArray) {
    if(!courseIdMapWithCourseTeacher.containsKey(pRoutine.getCourseId() + pRoutine.getSection())) {
      List<MutableCourseTeacher> courseTeacherList = new ArrayList<>();
      for(int i = 0; i < courseTeacherIdArray.length; i++) {
        String teacherId = courseTeacherIdArray[i];
        MutableCourseTeacher courseTeacher = new PersistentCourseTeacher();
        courseTeacher.setId(mIdGenerator.getNumericId());
        courseTeacher.setSection(pRoutine.getSection());
        courseTeacher.setTeacherId(teacherId);
        courseTeacherList.add(courseTeacher);
      }

      courseIdMapWithCourseTeacher.put(pRoutine.getCourseId() + pRoutine.getSection(), courseTeacherList);
    }
  }

  private void createColumnAndTimeMap(Row pRow) {
    for(int i = 1; i <= 12; i++) {
      String cellValue = pRow.getCell(i).getStringCellValue();
      String[] cellItems = cellValue.split(" - ");
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
      RoutineTime routineTime = new RoutineTime();

      routineTime.setStartTime(LocalTime.parse(cellItems[0], formatter));
      routineTime.setEndTime(LocalTime.parse(cellItems[1], formatter));
      columnMapWithTime.put(pRow.getCell(i).getColumnIndex(), routineTime);
    }
  }
}
