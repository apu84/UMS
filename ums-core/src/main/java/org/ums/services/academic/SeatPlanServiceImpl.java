package org.ums.services.academic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ums.domain.model.dto.ExamRoutineDto;
import org.ums.domain.model.immutable.*;
import org.ums.domain.model.mutable.MutableSeatPlan;
import org.ums.domain.model.mutable.MutableSeatPlanGroup;
import org.ums.enums.CourseRegType;
import org.ums.manager.*;
import org.ums.persistent.model.*;
import org.ums.response.type.GenericMessageResponse;
import org.ums.response.type.GenericResponse;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Monjur-E-Morshed Pc on 4/21/2016.
 */
@Service
public class SeatPlanServiceImpl implements SeatPlanService {
  private static final Logger mLogger = LoggerFactory.getLogger(SeatPlanServiceImpl.class);
  @Autowired
  ExamRoutineManager mExamRoutineManager;

  @Autowired
  SeatPlanGroupManager mSeatPlanGroupManager;

  @Autowired
  CourseManager mCourseManager;

  @Autowired
  SyllabusManager mSyllabusManager;

  @Autowired
  SubGroupManager mSubGroupManager;

  @Autowired
  SeatPlanManager mSeatPlanManager;

  @Autowired
  SpStudentManager mSpStudentManager;

  @Autowired
  ClassRoomManager mClassRoomManager;

  @Autowired
  StudentManager mStudentManager;

  @Autowired
  SubGroupCCIManager mSubGroupCCIManager;

  @Autowired
  ApplicationCCIManager mApplicationCCIManager;

  @Autowired
  UGRegistrationResultManager mUGRegistrationResultManager;

  @Autowired
  ProgramManager mProgramManager;

  @Override
  @Transactional
  public GenericResponse<Map> generateSeatPlan(int pSemesterId, int pGroupNo, int pExamType, String pExamDate) {
    int numberOfSubGroups;
    if(pGroupNo == 0) {
      numberOfSubGroups = mSubGroupCCIManager.checkSubGroupNumber(pSemesterId, pExamDate);
    }
    else {
      numberOfSubGroups = mSubGroupManager.getSubGroupNumberOfAGroup(pSemesterId, pExamType, pGroupNo);

    }

    if(pGroupNo == 0) {
      Integer numberOfSubGroupsWithZeroSubGroupNumber =
          mSubGroupCCIManager.checkForHalfFinishedSubGroup(pSemesterId, pExamDate);
      if(numberOfSubGroupsWithZeroSubGroupNumber > 0) {
        numberOfSubGroups -= 1;
      }
    }
    else {
      Integer numberOfSubGroupsWithZeroSubGroupNumber =
          mSubGroupManager.checkForHalfFinishedSubGroupsBySemesterGroupNoAndType(pSemesterId, pGroupNo, 1);
      if(numberOfSubGroupsWithZeroSubGroupNumber > 0) {
        numberOfSubGroups -= 1;
      }
    }

    /*
     * Map<String,List<Student>> studentsByProgramYearSemesterStatusList =
     * initiateStudentsBasedOnProgramYearSemesterStatus(); Map<String,List<Student>>
     * studentsByProgramYearSemesterStatusList2 =
     * initiateStudentsBasedOnProgramYearSemesterStatus();
     * 
     * Map<Integer,List<Student>> subGroupWithStudents =
     * getStudentsOfTheSubGroups(pSemesterId,pGroupNo
     * ,pExamType,pExamDate,numberOfSubGroups,studentsByProgramYearSemesterStatusList);
     * Map<Integer,List<Student>> tempSubGroupWithStudents
     * =getStudentsOfTheSubGroups(pSemesterId,pGroupNo
     * ,pExamType,pExamDate,numberOfSubGroups,studentsByProgramYearSemesterStatusList2);
     */

    Map<String, List<Student>> studentsByProgramYearSemesterStatusList = new HashMap<>();
    Map<String, List<Student>> studentsByProgramYearSemesterStatusList2 = new HashMap<>();

    Map<String, List<UGRegistrationResult>> studentsByProgramYearSemesterStatusListCCI = new HashMap<>();
    Map<String, List<UGRegistrationResult>> studentsByProgramYearSemesterStatusList2CCI = new HashMap<>();

    Map<Integer, List<Student>> subGroupWithStudents = new HashMap<>();
    Map<Integer, List<Student>> tempSubGroupWithStudents = new HashMap<>();

    Map<Integer, List<UGRegistrationResult>> subGroupWithStudentsCCI = new HashMap<>();
    Map<Integer, List<UGRegistrationResult>> tempSubGroupWithStudentsCCI = new HashMap<>();

    if(pGroupNo == 0) {
      studentsByProgramYearSemesterStatusListCCI = initiateStudentsForCCIBasedOnExamDate(pSemesterId, pExamDate);
      studentsByProgramYearSemesterStatusList2CCI = initiateStudentsForCCIBasedOnExamDate(pSemesterId, pExamDate);

      subGroupWithStudentsCCI =
          getStudentsOfTheSubGroupsCCI(pSemesterId, pGroupNo, pExamType, pExamDate, numberOfSubGroups,
              studentsByProgramYearSemesterStatusListCCI);
      tempSubGroupWithStudentsCCI =
          getStudentsOfTheSubGroupsCCI(pSemesterId, pGroupNo, pExamType, pExamDate, numberOfSubGroups,
              studentsByProgramYearSemesterStatusList2CCI);
    }
    else {
      studentsByProgramYearSemesterStatusList =
          initiateStudentsBasedOnProgramYearSemesterStatus(pGroupNo, pSemesterId, pExamType);
      studentsByProgramYearSemesterStatusList2 =
          initiateStudentsBasedOnProgramYearSemesterStatus(pGroupNo, pSemesterId, pExamType);

      subGroupWithStudents =
          getStudentsOfTheSubGroups(pSemesterId, pGroupNo, pExamType, pExamDate, numberOfSubGroups,
              studentsByProgramYearSemesterStatusList);
      tempSubGroupWithStudents =
          getStudentsOfTheSubGroups(pSemesterId, pGroupNo, pExamType, pExamDate, numberOfSubGroups,
              studentsByProgramYearSemesterStatusList2);
    }

    List<ClassRoom> roomList = mClassRoomManager.getAll();
    List<MutableSeatPlan> totalSeatPlan = new ArrayList<>();

    int roomCounter = 0;

    for(ClassRoom room : roomList) {

      roomCounter += 1;
      if(room.isExamSeatPlan()) {
        boolean subGroupEmpty = true;
        int capacity = room.getCapacity();
        String[][] roomStructure = new String[room.getTotalRow()][room.getTotalColumn()];

        // **********new Algorithm *************************

        int divider = numberOfSubGroups / 2;

        int evenRow = 1;
        int oddRow = divider + 1;
        int firstGroupAllZeroSizeCounter = 0;
        int secondGroupAllZeroSizeCounter = 0;
        List<Integer> firstGroupWithZeroSize = new ArrayList<>();
        List<Integer> secondGroupWithZeroSize = new ArrayList<>();
        if(pGroupNo == 0) {
          getSeatPlanStructureCCI(numberOfSubGroups, tempSubGroupWithStudentsCCI, room, roomStructure, divider,
              evenRow, oddRow, firstGroupWithZeroSize, secondGroupWithZeroSize);
        }
        else {
          getSeatPlanStructure(numberOfSubGroups, tempSubGroupWithStudents, room, roomStructure, divider, evenRow,
              oddRow, firstGroupWithZeroSize, secondGroupWithZeroSize);
        }

        // *********end of new Algorithm ******************

        Map<Student, String> studentsUsed = new HashMap<>();

        for(int roomColumn = 0; roomColumn < room.getTotalColumn(); roomColumn++) {
          for(int roomRow = 0; roomRow < room.getTotalRow(); roomRow++) {
            boolean columnBreak = false;

            for(int subGroup = 1; subGroup <= numberOfSubGroups; subGroup++) {
              if(roomStructure[roomRow][roomColumn] != null) {
                if(roomStructure[roomRow][roomColumn].equals(Integer.toString(subGroup))) {
                  if(pGroupNo == 0) {
                    if(subGroupWithStudentsCCI.get(subGroup) != null) {
                      List<UGRegistrationResult> studentsOfTheSubGroup = subGroupWithStudentsCCI.get(subGroup);
                      UGRegistrationResult ugStudent = studentsOfTheSubGroup.get(0);
                      Student student = mStudentManager.get(ugStudent.getStudentId());
                      roomStructure[roomRow][roomColumn] = student.getId();
                      MutableSeatPlan seatPlan = new PersistentSeatPlan();
                      PersistentClassRoom classRoom = new PersistentClassRoom();
                      classRoom.setId(room.getId());
                      seatPlan.setClassRoom(classRoom);
                      seatPlan.setRowNo(roomRow + 2);
                      seatPlan.setColumnNo(roomColumn + 1);
                      PersistentStudent spStudent = new PersistentStudent();
                      spStudent.setId(student.getId());
                      seatPlan.setStudent(spStudent);
                      seatPlan.setExamType(pExamType);
                      PersistentSemester semester = new PersistentSemester();
                      semester.setId(pSemesterId);
                      seatPlan.setSemester(semester);
                      seatPlan.setGroupNo(pGroupNo);
                      seatPlan.setCourseId(ugStudent.getCourse().getId());
                      if(pGroupNo == 0) {
                        seatPlan.setExamDate(pExamDate);
                        seatPlan.setApplicationType(student.getApplicationType());
                      }
                      totalSeatPlan.add(seatPlan);
                      studentsOfTheSubGroup.remove(0);
                      subGroupWithStudentsCCI.put(subGroup, studentsOfTheSubGroup);
                      break;
                    }
                  }
                  else {
                    if(subGroupWithStudents.get(subGroup) != null) {
                      List<Student> studentsOfTheSubGroup = subGroupWithStudents.get(subGroup);
                      Student student = studentsOfTheSubGroup.get(0);

                      roomStructure[roomRow][roomColumn] = student.getId();
                      MutableSeatPlan seatPlan = new PersistentSeatPlan();
                      PersistentClassRoom classRoom = new PersistentClassRoom();
                      classRoom.setId(room.getId());
                      seatPlan.setClassRoom(classRoom);
                      seatPlan.setRowNo(roomRow + 2);
                      seatPlan.setColumnNo(roomColumn + 1);
                      PersistentStudent spStudent = new PersistentStudent();
                      spStudent.setId(student.getId());
                      seatPlan.setStudent(spStudent);
                      seatPlan.setExamType(pExamType);
                      PersistentSemester semester = new PersistentSemester();
                      semester.setId(pSemesterId);
                      seatPlan.setSemester(semester);
                      seatPlan.setGroupNo(pGroupNo);
                      if(pGroupNo == 0) {
                        seatPlan.setExamDate(pExamDate);
                        seatPlan.setApplicationType(student.getApplicationType());
                      }
                      totalSeatPlan.add(seatPlan);
                      studentsOfTheSubGroup.remove(0);
                      subGroupWithStudents.put(subGroup, studentsOfTheSubGroup);
                      break;
                    }
                  }

                }
              }

            }

          }

        }

        int roomC = roomCounter;

        if((firstGroupWithZeroSize.size() + secondGroupWithZeroSize.size()) == numberOfSubGroups) {
          break;
        }

      }
    }

    /*
     * for(MutableSeatPlan seatPlan:totalSeatPlan){ mSeatPlanManager.create(seatPlan); }
     */
    try {
      if(pGroupNo == 0) {
        mSeatPlanManager.createSeatPlanForCCI(totalSeatPlan);
      }
      else {
        mSeatPlanManager.create(totalSeatPlan);
      }
    } catch(Exception ex) {
      mLogger.error(ex.getMessage());
      return new GenericMessageResponse(GenericResponse.ResponseType.ERROR);
    }

    return new GenericMessageResponse(GenericResponse.ResponseType.SUCCESS);
  }

  private void getSeatPlanStructure(int pNumberOfSubGroups, Map<Integer, List<Student>> pTempSubGroupWithStudents,
      ClassRoom room, String[][] pRoomStructure, int pDivider, int pEvenRow, int pOddRow,
      List<Integer> pFirstGroupWithZeroSize, List<Integer> pSecondGroupWithZeroSize) {
    for(int j = 0; j < room.getTotalColumn(); j++) {

      for(int i = 0; i < room.getTotalRow(); i++) {

        if(j % 2 == 0) {
          if(pTempSubGroupWithStudents.get(pEvenRow).size() > 0) {
            if(i > 0) {
              if(pRoomStructure[i - 1][j].equals(Integer.toString(pEvenRow))) {
                if(i + 1 < room.getTotalRow()) {
                  pRoomStructure[i][j] = "";
                  i += 1;
                }
                if(i + 1 == room.getTotalRow()) {
                  pRoomStructure[i][j] = "";
                  break;
                }

              }
            }
            pRoomStructure[i][j] = Integer.toString(pEvenRow);
            List<Student> tempStudentOfTheSubgroup = pTempSubGroupWithStudents.get(pEvenRow);
            tempStudentOfTheSubgroup.remove(0);
            pTempSubGroupWithStudents.put(pEvenRow, tempStudentOfTheSubgroup);
            if(pEvenRow == pDivider) {
              pEvenRow = 1;
            }
            else {
              pEvenRow += 1;
            }
          }
          else {
            if(pSecondGroupWithZeroSize.size() == ((pDivider + 1) - 1)) {
              pRoomStructure[i][j] = "";
            }
            else {
              boolean alreadyInList = false;
              if(pSecondGroupWithZeroSize.size() == 0) {
                pSecondGroupWithZeroSize.add(pEvenRow);
              }
              else {
                if(pSecondGroupWithZeroSize.contains(pEvenRow)) {
                  alreadyInList = true;
                }
                else {
                  pSecondGroupWithZeroSize.add(pEvenRow);
                }
              }
              pEvenRow += 1;
              if(pEvenRow > pDivider) {
                pEvenRow = 1;
              }
              if(pRoomStructure[i][j] == null) {
                i -= 1;

              }

            }

          }

        }
        else {

          if(pTempSubGroupWithStudents.get(pOddRow).size() > 0) {

            if(i > 0) {
              if(pRoomStructure[i - 1][j].equals(Integer.toString(pOddRow))) {
                if(i + 1 < room.getTotalRow()) {
                  pRoomStructure[i][j] = "";

                  i += 1;

                }
                if(i + 1 == room.getTotalRow()) {
                  pRoomStructure[i][j] = "";
                  break;
                }
              }
            }

            pRoomStructure[i][j] = Integer.toString(pOddRow);
            List<Student> studentsOfTheSubGroup = pTempSubGroupWithStudents.get(pOddRow);
            studentsOfTheSubGroup.remove(0);
            pTempSubGroupWithStudents.put(pOddRow, studentsOfTheSubGroup);
            if(pOddRow >= pNumberOfSubGroups) {
              pOddRow = pDivider + 1;
            }
            else {
              pOddRow += 1;
            }
          }
          else {
            if(pFirstGroupWithZeroSize.size() == (pNumberOfSubGroups - pDivider)) {
              pRoomStructure[i][j] = "";
            }
            else {
              boolean alreadyInList = false;
              if(pFirstGroupWithZeroSize.size() == 0) {
                pFirstGroupWithZeroSize.add(pOddRow);
              }
              else {
                if(pFirstGroupWithZeroSize.contains(pOddRow)) {
                  alreadyInList = true;
                }
                else {
                  pFirstGroupWithZeroSize.add(pOddRow);
                }
              }
              pOddRow += 1;
              if(pOddRow > pNumberOfSubGroups) {
                pOddRow = pDivider + 1;
              }
              if(pRoomStructure[i][j] == null) {
                i -= 1;

              }

            }

          }

        }
      }
    }
  }

  @Override
  @Transactional
  public GenericResponse<Map> generateGroup(int pSemesterId, int pExamType) {
    List<ExamRoutineDto> mExamRoutineList = mExamRoutineManager.getExamRoutine(pSemesterId, pExamType);

    Map<Integer, List<SeatPlanGroup>> groupWithProgramAndCourse = new HashMap<>();

    int group = 0;
    String dateIndicatorForIteration = "";
    boolean foundOccurrence = false;
    int count = 0;
    for(ExamRoutineDto examRoutineDto : mExamRoutineList) {
      count += 1;

      if(group == 0) {
        group += 1;

        MutableSeatPlanGroup seatPlanGroup = storeCourseInfo(examRoutineDto, pSemesterId, pExamType, group);

        groupWithProgramAndCourse = setGroupAndProgramMap(seatPlanGroup, group, groupWithProgramAndCourse);

        dateIndicatorForIteration = examRoutineDto.getExamDate();

      }
      else {
        if(examRoutineDto.getExamDate().equals(dateIndicatorForIteration)) {
          Map<Integer, List<SeatPlanGroup>> secondaryGroupAndProgramMap = groupWithProgramAndCourse;
          Course course = mCourseManager.get(examRoutineDto.getCourseId());
          boolean foundOccurrenceWithSameDateTwice = false;

          for(SeatPlanGroup iterate : secondaryGroupAndProgramMap.get(group)) {
            if(iterate.getProgram().getId() == course.getOfferedToProgramId()
                && iterate.getAcademicYear() == course.getYear()
                && iterate.getAcademicSemester() == course.getSemester()) {
              foundOccurrenceWithSameDateTwice = true;
              break;
            }
          }

          if(foundOccurrenceWithSameDateTwice == false) {
            MutableSeatPlanGroup seatPlanGroup = storeCourseInfo(examRoutineDto, pSemesterId, pExamType, group);

            List<SeatPlanGroup> seatPlanGroupList = groupWithProgramAndCourse.get(group);
            seatPlanGroupList.add(seatPlanGroup);
            groupWithProgramAndCourse.put(group, seatPlanGroupList);
          }
        }
        /*
         * else if(foundOccurrence &&
         * examRoutineDto.getExamDate().equals(dateIndicatorForIteration)){ continue; }
         */
        else {

          Course course = mCourseManager.get(examRoutineDto.getCourseId());
          boolean foundMatch = false;
          for(int groupIteration = 1; groupIteration <= groupWithProgramAndCourse.size(); groupIteration++) {
            List<SeatPlanGroup> seatPlanGroupListForFindingIfTheGroupIsPresent =
                groupWithProgramAndCourse.get(groupIteration);

            for(SeatPlanGroup seatPlanGroupForIterationForFinding : seatPlanGroupListForFindingIfTheGroupIsPresent) {

              if(seatPlanGroupForIterationForFinding.getProgram().getId() == course.getOfferedToProgramId()
                  && seatPlanGroupForIterationForFinding.getAcademicYear() == course.getYear()
                  && seatPlanGroupForIterationForFinding.getAcademicSemester() == course.getSemester()) {

                foundMatch = true;
                group = seatPlanGroupForIterationForFinding.getGroupNo();
                break;
              }
            }

            if(foundMatch) {
              break;
            }
          }

          if(foundMatch == true) {
            dateIndicatorForIteration = examRoutineDto.getExamDate();
            continue;
          }
          else {
            group = groupWithProgramAndCourse.size() + 1;
            MutableSeatPlanGroup seatPlanGroup = storeCourseInfo(examRoutineDto, pSemesterId, pExamType, group);

            groupWithProgramAndCourse = setGroupAndProgramMap(seatPlanGroup, group, groupWithProgramAndCourse);

            dateIndicatorForIteration = examRoutineDto.getExamDate();
          }

        }
      }
    }

    return new GenericMessageResponse(GenericResponse.ResponseType.SUCCESS);
  }

  private void getSeatPlanStructureCCI(int pNumberOfSubGroups,
      Map<Integer, List<UGRegistrationResult>> pTempSubGroupWithStudents, ClassRoom room, String[][] pRoomStructure,
      int pDivider, int pEvenRow, int pOddRow, List<Integer> pFirstGroupWithZeroSize,
      List<Integer> pSecondGroupWithZeroSize) {
    for(int j = 0; j < room.getTotalColumn(); j++) {

      for(int i = 0; i < room.getTotalRow(); i++) {

        if(j % 2 == 0) {
          if(pTempSubGroupWithStudents.get(pEvenRow).size() > 0) {
            if(i > 0) {
              if(pRoomStructure[i - 1][j].equals(Integer.toString(pEvenRow))) {
                if(i + 1 < room.getTotalRow()) {
                  pRoomStructure[i][j] = "";
                  i += 1;
                }
                if(i + 1 == room.getTotalRow()) {
                  pRoomStructure[i][j] = "";
                  break;
                }

              }
            }
            pRoomStructure[i][j] = Integer.toString(pEvenRow);
            List<UGRegistrationResult> tempStudentOfTheSubgroup = pTempSubGroupWithStudents.get(pEvenRow);
            tempStudentOfTheSubgroup.remove(0);
            pTempSubGroupWithStudents.put(pEvenRow, tempStudentOfTheSubgroup);
            if(pEvenRow == pDivider) {
              pEvenRow = 1;
            }
            else {
              pEvenRow += 1;
            }
          }
          else {
            if(pSecondGroupWithZeroSize.size() == ((pDivider + 1) - 1)) {
              pRoomStructure[i][j] = "";
            }
            else {
              boolean alreadyInList = false;
              if(pSecondGroupWithZeroSize.size() == 0) {
                pSecondGroupWithZeroSize.add(pEvenRow);
              }
              else {
                if(pSecondGroupWithZeroSize.contains(pEvenRow)) {
                  alreadyInList = true;
                }
                else {
                  pSecondGroupWithZeroSize.add(pEvenRow);
                }
              }
              pEvenRow += 1;
              if(pEvenRow > pDivider) {
                pEvenRow = 1;
              }
              if(pRoomStructure[i][j] == null) {
                i -= 1;

              }

            }

          }

        }
        else {

          if(pTempSubGroupWithStudents.get(pOddRow).size() > 0) {

            if(i > 0) {
              if(pRoomStructure[i - 1][j].equals(Integer.toString(pOddRow))) {
                if(i + 1 < room.getTotalRow()) {
                  pRoomStructure[i][j] = "";

                  i += 1;

                }
                if(i + 1 == room.getTotalRow()) {
                  pRoomStructure[i][j] = "";
                  break;
                }
              }
            }

            pRoomStructure[i][j] = Integer.toString(pOddRow);
            List<UGRegistrationResult> studentsOfTheSubGroup = pTempSubGroupWithStudents.get(pOddRow);
            studentsOfTheSubGroup.remove(0);
            pTempSubGroupWithStudents.put(pOddRow, studentsOfTheSubGroup);
            if(pOddRow >= pNumberOfSubGroups) {
              pOddRow = pDivider + 1;
            }
            else {
              pOddRow += 1;
            }
          }
          else {
            if(pFirstGroupWithZeroSize.size() == (pNumberOfSubGroups - pDivider)) {
              pRoomStructure[i][j] = "";
            }
            else {
              boolean alreadyInList = false;
              if(pFirstGroupWithZeroSize.size() == 0) {
                pFirstGroupWithZeroSize.add(pOddRow);
              }
              else {
                if(pFirstGroupWithZeroSize.contains(pOddRow)) {
                  alreadyInList = true;
                }
                else {
                  pFirstGroupWithZeroSize.add(pOddRow);
                }
              }
              pOddRow += 1;
              if(pOddRow > pNumberOfSubGroups) {
                pOddRow = pDivider + 1;
              }
              if(pRoomStructure[i][j] == null) {
                i -= 1;

              }

            }

          }

        }
      }
    }
  }

  MutableSeatPlanGroup storeCourseInfo(ExamRoutineDto examRoutineDto, int pSemesterId, int pExamType, int group) {

    Course course = mCourseManager.get(examRoutineDto.getCourseId());

    MutableSeatPlanGroup seatPlanGroup = new PersistentSeatPlanGroup();
    PersistentSemester semester = new PersistentSemester();
    semester.setId(pSemesterId);
    seatPlanGroup.setSemester(semester);

    PersistentProgram program = new PersistentProgram();
    program.setId(examRoutineDto.getProgramId());
    seatPlanGroup.setProgram(program);

    seatPlanGroup.setAcademicYear(course.getYear());
    seatPlanGroup.setAcademicSemester(course.getSemester());

    seatPlanGroup.setGroupNo(group);

    seatPlanGroup.setExamType(pExamType);

    seatPlanGroup.create();

    return seatPlanGroup;
  }

  Map<Integer, List<SeatPlanGroup>> setGroupAndProgramMap(SeatPlanGroup seatPlanGroup, int group,
      Map<Integer, List<SeatPlanGroup>> groupWithProgramAndCourseMap) {

    Map<Integer, List<SeatPlanGroup>> groupWithProgramAndCourse = groupWithProgramAndCourseMap;
    if(groupWithProgramAndCourse.get(group) == null) {
      List<SeatPlanGroup> seatPlanGroupList = new LinkedList<>();
      seatPlanGroupList.add(seatPlanGroup);

      groupWithProgramAndCourse.put(group, seatPlanGroupList);
    }
    else {
      List<SeatPlanGroup> seatPlanGroupList = groupWithProgramAndCourse.get(group);
      seatPlanGroupList.add(seatPlanGroup);
      groupWithProgramAndCourse.put(group, seatPlanGroupList);
    }

    return groupWithProgramAndCourse;
  }

  Map<Integer, List<Student>> getStudentsOfTheSubGroups(int pSemesterId, int pGroupNo, int pExamType, String examDate,
      int numberOfSubGroups, Map<String, List<Student>> studentsByProgramYearSemesterStatusList) {

    Map<Integer, List<Student>> subGroupMap = new HashMap<>();

    for(int subGroupNumberIterator = 1; subGroupNumberIterator <= numberOfSubGroups; subGroupNumberIterator++) {
      List<Student> studentsOfTheSubGroup = new LinkedList<>();
      if(examDate.equals("null")) {
        List<SubGroup> subGroupMembers =
            mSubGroupManager.getSubGroupMembers(pSemesterId, pExamType, pGroupNo, subGroupNumberIterator);

        int counter = 0;
        for(SubGroup member : subGroupMembers) {
          SeatPlanGroup group = mSeatPlanGroupManager.get(member.getGroupId());
          String key = group.getProgram().getId() + "" + group.getAcademicYear() + "" + group.getAcademicSemester();
          List<Student> studentsOfTheGroup = new ArrayList<>();
          List<Student> existingStudents = studentsByProgramYearSemesterStatusList.get(key);

          int studentCounter = 0;
          int totalExistingStudentSize = existingStudents.size();
          for(int m = 0; m < totalExistingStudentSize; m++) {
            Student existingStudent = existingStudents.get(0);
            studentsOfTheGroup.add(existingStudent);
            existingStudents.remove(0);
            studentCounter += 1;
            if(studentCounter == member.getStudentNumber()) {
              break;
            }
          }
          studentsByProgramYearSemesterStatusList.put(key, existingStudents);
          if(counter == 0) {
            studentsOfTheSubGroup = studentsOfTheGroup;
            counter += 1;
          }
          else {
            studentsOfTheSubGroup.addAll(studentsOfTheGroup);
            counter += 1;
          }

        }

        subGroupMap.put(subGroupNumberIterator, studentsOfTheSubGroup);
      }
      else {

        List<SubGroupCCI> subGroupMembers = mSubGroupCCIManager.getBySemesterAndExamDate(pSemesterId, examDate);

        int counter = 0;
        for(SubGroupCCI member : subGroupMembers) {

          // String key = member.getId()+member.getCourseId();
          List<Student> studentsOfTheGroup = new ArrayList<>();
          List<Student> existingStudents = studentsByProgramYearSemesterStatusList.get(member.getCourseId());

          int studentCounter = 0;
          int totalExistingStudentSize = existingStudents.size();
          for(int m = 0; m < totalExistingStudentSize; m++) {
            Student existingStudent = existingStudents.get(0);
            studentsOfTheGroup.add(existingStudent);
            existingStudents.remove(0);
            studentCounter += 1;
            if(studentCounter == member.getTotalStudent()) {
              break;
            }
          }
          studentsByProgramYearSemesterStatusList.put(member.getCourseId(), existingStudents);
          if(counter == 0) {
            studentsOfTheSubGroup = studentsOfTheGroup;
            counter += 1;
          }
          else {
            studentsOfTheSubGroup.addAll(studentsOfTheGroup);
            counter += 1;
          }

        }

        subGroupMap.put(subGroupNumberIterator, studentsOfTheSubGroup);
      }
    }
    return subGroupMap;
  }

  Map<Integer, List<UGRegistrationResult>> getStudentsOfTheSubGroupsCCI(int pSemesterId, int pGroupNo, int pExamType,
      String examDate, int numberOfSubGroups,
      Map<String, List<UGRegistrationResult>> studentsByProgramYearSemesterStatusList) {
    List<SubGroupCCI> subGroupMembers = mSubGroupCCIManager.getBySemesterAndExamDate(pSemesterId, examDate);
    Map<Integer, List<UGRegistrationResult>> subGroupNumberWithStudentsMap = new HashMap<>();
    Integer numberOfZeroValuedSubGroups = mSubGroupCCIManager.checkForHalfFinishedSubGroup(pSemesterId, examDate);

    /*
     * TO do: the employee or the user can see partial exam seat plan, that means, he/she can save
     * some portions of the sub groups and then check the seat plan. So, there must be a provision
     * for checking. For that process, check if there is any sub group number with value '0', if,
     * then, the sub group number must be omitted in the loop.
     */
    for(SubGroupCCI member : subGroupMembers) {
      List<UGRegistrationResult> students = studentsByProgramYearSemesterStatusList.get(member.getCourseId());
      for(int i = 1; i <= numberOfSubGroups; i++) {
        if(i == member.getSubGroupNo()) {
          if(subGroupNumberWithStudentsMap.get(i) != null) {
            List<UGRegistrationResult> studentsOfTheSubGroup = subGroupNumberWithStudentsMap.get(i);
            for(int j = 0; j < member.getTotalStudent(); j++) {
              UGRegistrationResult spStudent = students.get(0);
              students.remove(0);
              studentsOfTheSubGroup.add(spStudent);
            }
            // studentsOfTheSubGroup.addAll(students); //check
            subGroupNumberWithStudentsMap.put(i, studentsOfTheSubGroup);
          }
          else {
            List<UGRegistrationResult> studentsOfTheSubGroup = new ArrayList<>();
            for(int j = 0; j < member.getTotalStudent(); j++) {
              UGRegistrationResult spStudent1 = students.get(0);
              students.remove(0);
              studentsOfTheSubGroup.add(spStudent1);
            }

            subGroupNumberWithStudentsMap.put(i, studentsOfTheSubGroup);
          }

          break;
        }

      }

      studentsByProgramYearSemesterStatusList.put(member.getCourseId(), students);

    }
    return subGroupNumberWithStudentsMap;
  }

  Map<String, List<UGRegistrationResult>> initiateStudentsForCCIBasedOnExamDate(int pSemester, String pExamDate) {
    Map<String, List<Student>> studentMap = new HashMap<>();
    List<UGRegistrationResult>  ugRegistrationResults = mUGRegistrationResultManager.getCCI(pSemester, pExamDate) ;
    Map<String, List<UGRegistrationResult>> courseIdMapUgRegistratoionResult =  ugRegistrationResults
        .stream()
        .collect(Collectors.groupingBy(a-> a.getCourse().getId()));

    Map<String, Integer> studentIdMapUgRegistrationResult = new HashMap<>();
    int counter=0;
    for(UGRegistrationResult ug: ugRegistrationResults){
      studentIdMapUgRegistrationResult.put(ug.getStudent().getId(), ug.getType().getId());
    }


    for(Map.Entry<String, List<UGRegistrationResult>> entry: courseIdMapUgRegistratoionResult.entrySet()){
      List<UGRegistrationResult> ugRegistrationResultList = entry.getValue();
      List<Student> students = new ArrayList<>();
      for(UGRegistrationResult ugResult: ugRegistrationResultList){
        PersistentStudent student = (PersistentStudent) ugResult.getStudent();
        student.setApplicationType(studentIdMapUgRegistrationResult.get(student.getId()));
        students.add(student);
      }
      studentMap.put(entry.getKey(), students);
    }


    return courseIdMapUgRegistratoionResult;
  }

  Map<String, List<Student>> initiateStudentsBasedOnProgramYearSemesterStatus(int pGroupNo, int pSemesterId,
      int pExamType) {

    Map<String, List<Student>> studentInfoMap = new HashMap<>();

    List<Student> allStudents = mStudentManager.getRegisteredStudents(pGroupNo, pSemesterId, pExamType);

    for(Student student : allStudents) {
      String keyWithProgramYearSemesterStatus =
          student.getProgram().getId() + "" + student.getCurrentYear() + "" + student.getCurrentAcademicSemester();
      if(studentInfoMap.size() == 0 || studentInfoMap.get(keyWithProgramYearSemesterStatus) == null) {
        List<Student> studentList = new ArrayList<>();

        studentList.add(student);
        studentInfoMap.put(keyWithProgramYearSemesterStatus, studentList);
      }
      else {
        List<Student> studentList = studentInfoMap.get(keyWithProgramYearSemesterStatus);
        studentList.add(student);
        studentInfoMap.put(keyWithProgramYearSemesterStatus, studentList);
      }
    }
    return studentInfoMap;
  }

}
