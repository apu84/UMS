package org.ums.services.academic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ums.domain.model.dto.ExamRoutineDto;
import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.immutable.ExamRoutine;
import org.ums.domain.model.immutable.SeatPlanGroup;
import org.ums.domain.model.immutable.Syllabus;
import org.ums.domain.model.mutable.MutableSeatPlanGroup;
import org.ums.manager.*;
import org.ums.persistent.model.PersistentProgram;
import org.ums.persistent.model.PersistentSeatPlanGroup;
import org.ums.persistent.model.PersistentSemester;
import org.ums.response.type.GenericMessageResponse;
import org.ums.response.type.GenericResponse;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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


  @Override
  @Transactional
  public GenericResponse<Map> saveSeatPlan(int pSemesterId,int pExamType) throws Exception {
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
}
