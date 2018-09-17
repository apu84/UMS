package org.ums.services.academic;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.RequiredAnnotationBeanPostProcessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ums.domain.model.immutable.ClassRoom;
import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.immutable.CourseTeacher;
import org.ums.domain.model.immutable.routine.Routine;
import org.ums.domain.model.immutable.routine.RoutineConfig;
import org.ums.domain.model.mutable.MutableCourse;
import org.ums.domain.model.mutable.MutableCourseTeacher;
import org.ums.domain.model.mutable.routine.MutableRoutine;
import org.ums.enums.CourseType;
import org.ums.enums.ProgramType;
import org.ums.enums.routine.DayType;
import org.ums.generator.IdGenerator;
import org.ums.manager.*;
import org.ums.manager.routine.RoutineConfigManager;
import org.ums.manager.routine.RoutineManager;
import org.ums.persistent.model.PersistentCourseTeacher;
import org.ums.persistent.model.routine.PersistentRoutine;
import org.ums.services.academic.helper.RoutineTime;

import java.io.IOException;
import java.security.SecureRandom;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalUnit;
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
  @Autowired
  private RoutineConfigManager mRoutineConfigManager;

  private Map<Integer, RoutineTime> columnMapWithTime = new HashMap<>();
  private Map<String, ClassRoom> classRoomMapWithRoomNo;
  private Map<String, Course> courseMapWithCourseNo;
  private Map<String, List<MutableCourseTeacher>> courseIdMapWithCourseTeacher;
  private RoutineConfig routineConfig;

  @Transactional
  public void extractWorkBook(Workbook pWorkbook, Integer pSemesterId, Integer pProgramId) throws Exception,
      IOException, InvalidFormatException {

    List<MutableRoutine> routineList = new ArrayList<>();
    courseIdMapWithCourseTeacher = new HashMap<>();
    routineConfig =
        mRoutineConfigManager.get(pSemesterId,
            ProgramType.get(mProgramManager.get(pProgramId).getProgramType().getId()));
    createClassRoomMapWithRoomNo();
    createCourseMapWithCourseNo(pSemesterId, pProgramId);
    removeRoutine(pSemesterId, pProgramId);
    removeCourseTeacher(pSemesterId, pProgramId);

    for(int i = 0; i < 4; i++) {
      Sheet sheet = pWorkbook.getSheetAt(i);
      System.out.println(sheet.getSheetName() + " ");
      int year = extractYearFromSheetName(sheet.getSheetName());
      int semester = extractSemesterFromSheetName(sheet.getSheetName());
      String section = extractSectionFromSheetName(sheet.getSheetName());
      String globalRoomNo = extractRoomNumberFromSheetName(sheet.getSheetName());
      extractRoutineInformationFromSheet(sheet, pSemesterId, pProgramId, routineList, year, semester, section,
          globalRoomNo);
    }

    mRoutineManager.create(routineList);
    insertCourseTeacher(pSemesterId, pProgramId);
  }

  public void insertIntoRoutine(List<MutableRoutine> pRoutineList) {
    List<MutableRoutine> routineList = new ArrayList<>();
    for(int i = 0; i < pRoutineList.size(); i++) {
      MutableRoutine routine = pRoutineList.get(i);
      Long id = mIdGenerator.getNumericId();
      System.out.println("****************");
      System.out.println(id);
      routine.setId(id);
      routineList.add(routine);
    }

    mRoutineManager.create(routineList);
  }

  @Transactional
  public void insertCourseTeacher(Integer pSemesterId, Integer pProgramId) {
    List<MutableCourseTeacher> courseTeacherList = new ArrayList<>();
    for(Map.Entry<String, List<MutableCourseTeacher>> entry : courseIdMapWithCourseTeacher.entrySet()) {
      courseTeacherList.addAll(entry.getValue());
    }

    mCourseTeacherManager.create(courseTeacherList);
  }

  @Transactional
  public void removeCourseTeacher(Integer pSemesterId, Integer pProgramId){
    List<MutableCourseTeacher> courseTeacherList = mCourseTeacherManager.getCourseTeacher(pSemesterId, pProgramId)
        .stream()
        .map(p-> (MutableCourseTeacher) p)
        .collect(Collectors.toList());

    mCourseTeacherManager.delete(courseTeacherList);
  }

  @Transactional
  public void removeRoutine(Integer pSemesterId, Integer pProgramId){
    List<MutableRoutine> routineList = mRoutineManager.getRoutineBySemesterAndProgram(pSemesterId, pProgramId)
        .parallelStream()
        .map(p-> (MutableRoutine) p)
        .collect(Collectors.toList());

    mRoutineManager.delete(routineList);
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

    if(columnMapWithTime.size() < 12) {
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
        extractExcelCell(pRow, pSemesterId, pProgramId, pRoutineList, pYear, pSemester, pSection, pGlobalRoomNo,
            dayName, i);
      }

    }
  }

  private void extractExcelCell(Row pRow, Integer pSemesterId, Integer pProgramId, List<MutableRoutine> pRoutineList,
      int pYear, int pSemester, String pSection, String pGlobalRoomNo, String pDayName, int pI) {
    String[] cellStrings = pRow.getCell(pI).toString().split("\n");
    Random random = new Random();
    int groupId = random.nextInt(10000);
    RoutineTime routineTime = columnMapWithTime.get(pRow.getCell(pI).getColumnIndex());
    for(int k = 0; k < cellStrings.length; k++) {
      List<MutableRoutine> cellRoutineList = new ArrayList<>();
      String[] courseStrings = cellStrings[k].split("\\|");
      LocalTime startTime = routineTime.getStartTime();

      for(String courseString : courseStrings) {
        MutableRoutine routine = new PersistentRoutine();
        courseString = courseString.replaceAll("\\[", "").replaceAll("\\]", "");
        if(courseString.equalsIgnoreCase("")) {
          startTime = startTime.plusMinutes(routineConfig.getDuration());
        }
        else {
          String[] courseRoutineInfo = courseString.split(" ");
          assignRoutineData(pSemesterId, pProgramId, pYear, pSemester, pSection, pGlobalRoomNo, pDayName, groupId,
              startTime, routine, courseRoutineInfo);

          startTime = startTime.plusMinutes(routineConfig.getDuration());
          cellRoutineList.add(routine);
        }

      }
      pRoutineList.addAll(cellRoutineList);
      if((k + 1) < cellStrings.length) {
        char[] nextCellCharacterArray = cellStrings[k + 1].toCharArray();
        if(nextCellCharacterArray[0] == '[') {
          extractCourseTeacherInfo(pSemesterId, pProgramId, cellRoutineList, cellStrings[k + 1]);
          k = k + 1;
        }
      }

    }
  }

  private void assignRoutineData(Integer pSemesterId, Integer pProgramId, int pYear, int pSemester, String pSection,
      String pGlobalRoomNo, String pDayName, int pGroupId, LocalTime pStartTime, MutableRoutine pRoutine,
      String[] pCourseRoutineInfo) {
    pRoutine.setSlotGroup(pGroupId);
    pRoutine.setId(mIdGenerator.getNumericId());
    pRoutine.setCourseId(courseMapWithCourseNo.get(pCourseRoutineInfo[0] + " " + pCourseRoutineInfo[1]).getId());
    if(pRoutine.getCourse().getCourseType().equals(CourseType.SESSIONAL)) {
      pRoutine.setSection(pCourseRoutineInfo[2]);
      pRoutine.setRoomId((4 <= pCourseRoutineInfo.length) ? classRoomMapWithRoomNo.get(pCourseRoutineInfo[3]).getId()
          : classRoomMapWithRoomNo.get(pGlobalRoomNo).getId());
    }
    else {
      pRoutine.setSection(pSection);
      pRoutine.setRoomId((3 <= pCourseRoutineInfo.length) ? classRoomMapWithRoomNo.get(pCourseRoutineInfo[2]).getId()
          : classRoomMapWithRoomNo.get(pGlobalRoomNo).getId());
    }

    pRoutine.setSemesterId(pSemesterId);
    pRoutine.setDay(DayType.getByLabel(pDayName).getValue());
    pRoutine.setProgramId(pProgramId);
    pRoutine.setAcademicYear(pYear);
    pRoutine.setAcademicSemester(pSemester);
    pRoutine.setStartTime(pStartTime);
    if(pRoutine.getCourse().getCourseType().equals(CourseType.SESSIONAL))
      pRoutine.setEndTime(pRoutine.getStartTime().plusMinutes(routineConfig.getDuration() * 3));
    else
      pRoutine.setEndTime(pStartTime.plusMinutes(routineConfig.getDuration()));
  }

  private void extractCourseTeacherInfo(Integer pSemesterId, Integer pProgramId, List<MutableRoutine> pRoutineList,
      String cellCourseTeacherString) {
    cellCourseTeacherString = cellCourseTeacherString.replaceAll("\\[", "").replaceAll("\\]", "");
    String[] courseTeacherListBasedOnCourse = cellCourseTeacherString.split("\\|");
    for(int i = 0; i < pRoutineList.size(); i++) {
      if(!courseTeacherListBasedOnCourse[i].equalsIgnoreCase("")) {
        String[] courseTeacher = courseTeacherListBasedOnCourse[i].split(",");
        insertOrUpdateCourseTeacher(pSemesterId, pProgramId, pRoutineList.get(i), courseTeacher);
      }

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
        courseTeacher.setCourseId(pRoutine.getCourseId());
        courseTeacher.setSection(pRoutine.getSection());
        courseTeacher.setSemesterId(pSemesterId);
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
