package org.ums.services.academic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ums.domain.model.dto.ExamRoutineDto;
import org.ums.domain.model.immutable.*;
import org.ums.domain.model.mutable.MutableSeatPlan;
import org.ums.domain.model.mutable.MutableSeatPlanGroup;
import org.ums.manager.*;
import org.ums.persistent.model.*;
import org.ums.response.type.GenericMessageResponse;
import org.ums.response.type.GenericResponse;

import java.util.*;

/**
 * Created by Monjur-E-Morshed Pc on 4/21/2016.
 */
@Service
public class SeatPlanServiceImpl implements SeatPlanService {

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


  @Override
  @Transactional
  public GenericResponse<Map> generateSeatPlan(int pSemesterId, int pGroupNo, int pExamType) throws Exception {
    int numberOfSubGroups = mSubGroupManager.getSubGroupNumberOfAGroup(pSemesterId,pExamType,pGroupNo);

    Map<Integer,List<SpStudent>> subGroupWithStudents = getStudentsOfTheSubGroups(pSemesterId,pGroupNo,pExamType,numberOfSubGroups);



    List<ClassRoom> roomList = mClassRoomManager.getAll();
    List<MutableSeatPlan> totalSeatPlan = new ArrayList<>();

    for(ClassRoom room: roomList){
      if(room.isExamSeatPlan()){
        boolean subGroupEmpty=true;

        double partitionForTheSubGroup = room.getCapacity()/numberOfSubGroups;
        String[][] roomStructure = new String[room.getTotalRow()][room.getTotalColumn()];

        int numberOfStudents = (int)Math.ceil(partitionForTheSubGroup);

        for(int subGroup=1;subGroup<=numberOfSubGroups;subGroup++){
          List<SpStudent> studentsOfTheSubGroup = subGroupWithStudents.get(subGroup);
          if(studentsOfTheSubGroup.size()!=0){
            subGroupEmpty = false;
            for(SpStudent student: studentsOfTheSubGroup){
              int studentCounter=0;

              for(int roomRow=1;roomRow<=room.getTotalRow();roomRow++){

                for(int roomColumn=1;roomColumn<=room.getTotalColumn();roomColumn++){
                  if(roomStructure[roomRow][roomColumn]==null){
                    roomStructure[roomRow][roomColumn] = student.getId();
                    MutableSeatPlan seatPlan = new PersistentSeatPlan();
                    PersistentClassRoom classRoom = new PersistentClassRoom();
                    classRoom.setId(room.getId());
                    seatPlan.setClassRoom(classRoom);
                    seatPlan.setRowNo(roomRow);
                    seatPlan.setColumnNo(roomColumn);
                    PersistentSpStudent spStudent = new PersistentSpStudent();
                    spStudent.setId(student.getId());
                    seatPlan.setStudent(spStudent);
                    seatPlan.setExamType(pExamType);
                    PersistentSemester semester = new PersistentSemester();
                    semester.setId(pSemesterId);
                    seatPlan.setSemester(semester);
                    seatPlan.setGroupNo(pGroupNo);

                    totalSeatPlan.add(seatPlan);

                    studentsOfTheSubGroup.remove(student);

                    roomColumn+=2;

                    if(roomColumn>room.getTotalColumn()){
                      break;
                    }

                    studentCounter+=1;

                    if(studentCounter>numberOfStudents){
                      break;
                    }

                  }

                  roomRow+=2;

                  if(roomRow>room.getTotalRow()){
                    break;
                  }

                  if(studentCounter>numberOfStudents){
                    break;
                  }
                }
              }
            }
          }


          subGroupWithStudents.put(subGroup,studentsOfTheSubGroup);

        }

        if(subGroupEmpty){
          break;
        }

      }
    }

    mSeatPlanManager.create(totalSeatPlan);


    return new GenericMessageResponse(GenericResponse.ResponseType.SUCCESS);
  }





  @Override
  @Transactional
  public GenericResponse<Map> generateGroup(int pSemesterId, int pExamType) throws Exception {
    List<ExamRoutineDto> mExamRoutineList = mExamRoutineManager.getExamRoutine(pSemesterId,pExamType);


    Map<Integer,List<SeatPlanGroup>> groupWithProgramAndCourse = new HashMap<>();

    int group=0;
    String dateIndicatorForIteration = "";
    boolean foundOccurrence = false;
    int count=0;
    for(ExamRoutineDto examRoutineDto: mExamRoutineList){
      count+=1;

      if(group==0){
        group+=1;

        MutableSeatPlanGroup seatPlanGroup = storeCourseInfo(examRoutineDto,pSemesterId,pExamType,group);

        groupWithProgramAndCourse = setGroupAndProgramMap(seatPlanGroup,group,groupWithProgramAndCourse);

        dateIndicatorForIteration = examRoutineDto.getExamDate();

      }
      else{
        if(examRoutineDto.getExamDate().equals(dateIndicatorForIteration)){
          Map<Integer,List<SeatPlanGroup>> secondaryGroupAndProgramMap = groupWithProgramAndCourse;
          Course course = mCourseManager.get(examRoutineDto.getCourseId());
          Syllabus syllabus = mSyllabusManager.get(course.getSyllabusId());
          boolean foundOccurrenceWithSameDateTwice=false;

          for(SeatPlanGroup iterate:secondaryGroupAndProgramMap.get(group)){
            if( iterate.getProgram().getId()== syllabus.getProgramId() &&
                iterate.getAcademicYear() == course.getYear() &&
                iterate.getAcademicSemester()==course.getSemester()){
              foundOccurrenceWithSameDateTwice=true;
              break;
            }
          }

         if(foundOccurrenceWithSameDateTwice==false){
           MutableSeatPlanGroup seatPlanGroup = storeCourseInfo(examRoutineDto,pSemesterId,pExamType,group);

           List<SeatPlanGroup> seatPlanGroupList = groupWithProgramAndCourse.get(group);
           seatPlanGroupList.add(seatPlanGroup);
           groupWithProgramAndCourse.put(group,seatPlanGroupList);
         }
        }
        /*else  if(foundOccurrence && examRoutineDto.getExamDate().equals(dateIndicatorForIteration)){
          continue;
        }*/
        else{

          Course course = mCourseManager.get(examRoutineDto.getCourseId());
          Syllabus syllabus = mSyllabusManager.get(course.getSyllabusId());

          boolean foundMatch=false;
          for(int groupIteration=1;groupIteration<=groupWithProgramAndCourse.size();groupIteration++){
            List<SeatPlanGroup> seatPlanGroupListForFindingIfTheGroupIsPresent = groupWithProgramAndCourse.get(groupIteration);


            for(SeatPlanGroup seatPlanGroupForIterationForFinding: seatPlanGroupListForFindingIfTheGroupIsPresent){

              if( seatPlanGroupForIterationForFinding.getProgram().getId()== syllabus.getProgramId() &&
                  seatPlanGroupForIterationForFinding.getAcademicYear() == course.getYear() &&
                  seatPlanGroupForIterationForFinding.getAcademicSemester()==course.getSemester()
                  ){

                foundMatch=true;
                group=seatPlanGroupForIterationForFinding.getGroupNo();
                break;
              }
            }

            if(foundMatch){
              break;
            }
          }

          if(foundMatch==true){
            dateIndicatorForIteration = examRoutineDto.getExamDate();
            continue;
          }
          else{
            group = groupWithProgramAndCourse.size()+1;
            MutableSeatPlanGroup seatPlanGroup = storeCourseInfo(examRoutineDto,pSemesterId,pExamType,group);

            groupWithProgramAndCourse = setGroupAndProgramMap(seatPlanGroup,group,groupWithProgramAndCourse);

            dateIndicatorForIteration = examRoutineDto.getExamDate();
          }


        }
      }
    }

    return new GenericMessageResponse(GenericResponse.ResponseType.SUCCESS);
  }

  MutableSeatPlanGroup storeCourseInfo(ExamRoutineDto examRoutineDto,int pSemesterId,int pExamType,int group)throws Exception{

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


    seatPlanGroup.commit(false);

    return  seatPlanGroup;
  }

  Map<Integer,List<SeatPlanGroup>> setGroupAndProgramMap(SeatPlanGroup seatPlanGroup,int group,Map<Integer,List<SeatPlanGroup>> groupWithProgramAndCourseMap){

    Map<Integer,List<SeatPlanGroup>> groupWithProgramAndCourse = groupWithProgramAndCourseMap;
    if(groupWithProgramAndCourse.get(group)== null){
      List<SeatPlanGroup> seatPlanGroupList = new LinkedList<>();
      seatPlanGroupList.add(seatPlanGroup);

      groupWithProgramAndCourse.put(group,seatPlanGroupList);
    }
    else{
      List<SeatPlanGroup> seatPlanGroupList = groupWithProgramAndCourse.get(group);
      seatPlanGroupList.add(seatPlanGroup);
      groupWithProgramAndCourse.put(group,seatPlanGroupList);
    }

    return groupWithProgramAndCourse;
  }



  Map<Integer,List<SpStudent>> getStudentsOfTheSubGroups(int pSemesterId, int pGroupNo,int pExamType,int numberOfSubGroups)throws Exception{

    Map<Integer,List<SpStudent>> subGroupMap = new HashMap<>();

    for(int subGroupNumberIterator=1;subGroupNumberIterator<=numberOfSubGroups;subGroupNumberIterator++){
      List<SpStudent> studentsOfTheSubGroup = new LinkedList<>();
      List<SubGroup> subGroupMembers = mSubGroupManager.getSubGroupMembers(pSemesterId,pExamType,pGroupNo,subGroupNumberIterator);

      int counter=0;
      for(SubGroup member: subGroupMembers){
        SeatPlanGroup group = mSeatPlanGroupManager.get(member.getGroup().getId());
        List<SpStudent> studentsOfTheGroup = mSpStudentManager.getStudentByProgramYearSemesterStatus(group.getProgram().getId(),
            group.getAcademicYear(),
            group.getAcademicSemester(),
            1);
        if(counter==0){
          studentsOfTheSubGroup = studentsOfTheGroup;
        }else{
          studentsOfTheSubGroup.addAll(studentsOfTheGroup);
        }

      }

      subGroupMap.put(subGroupNumberIterator,studentsOfTheSubGroup);
    }
    return subGroupMap;
  }



}
