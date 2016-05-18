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
    Map<Integer,List<SpStudent>> tempSubGroupWithStudents =getStudentsOfTheSubGroups(pSemesterId,pGroupNo,pExamType,numberOfSubGroups);




    List<ClassRoom> roomList = mClassRoomManager.getAll();
    List<MutableSeatPlan> totalSeatPlan = new ArrayList<>();

    int roomCounter=0;

    for(ClassRoom room: roomList){


      roomCounter+=1;
      if(room.isExamSeatPlan()){
        boolean subGroupEmpty=true;
        int capacity = room.getCapacity();
        String[][] roomStructure = new String[room.getTotalRow()][room.getTotalColumn()];


        //**********new Algorithm *************************


        int divider = numberOfSubGroups/2;



        int evenRow=1;
        int oddRow= divider+1;
        int firstGroupAllZeroSizeCounter=0;
        int secondGroupAllZeroSizeCounter=0;
        List<Integer> firstGroupWithZeroSize = new ArrayList<>();
        List<Integer> secondGroupWithZeroSize = new ArrayList<>();
        for(int j=0;j<room.getTotalColumn();j++){

          for(int i=0;i<room.getTotalRow();i++){

            if(j%2==0){
              if(tempSubGroupWithStudents.get(evenRow).size()>0){
                if(i>0){
                  if(roomStructure[i-1][j].equals(Integer.toString(evenRow))){
                    if(i+1<room.getTotalRow()){
                      roomStructure[i][j]="";
                      i+=1;
                    }
                    if(i+1==room.getTotalRow()){
                      roomStructure[i][j]="";
                      break;
                    }

                  }
                }
                roomStructure[i][j] = Integer.toString(evenRow);
                List<SpStudent> tempStudentOfTheSubgroup = tempSubGroupWithStudents.get(evenRow);
                tempStudentOfTheSubgroup.remove(0);
                tempSubGroupWithStudents.put(evenRow,tempStudentOfTheSubgroup);
                if(evenRow==divider){
                  evenRow=1;
                }else{
                  evenRow+=1;
                }
              }else{
                if(secondGroupWithZeroSize.size()==((divider+1)-1)){
                  roomStructure[i][j]="";
                }
                else{
                  boolean alreadyInList = false;
                  if(secondGroupWithZeroSize.size()==0){
                    secondGroupWithZeroSize.add(evenRow);
                  }else{
                    if(secondGroupWithZeroSize.contains(evenRow)){
                      alreadyInList = true;
                    }else{
                      secondGroupWithZeroSize.add(evenRow);
                    }
                  }
                  evenRow+=1;
                  if(evenRow>divider){
                    evenRow=1;
                  }
                  if(roomStructure[i][j]==null){
                    i-=1;

                  }


                }

              }

            }else{

              if(tempSubGroupWithStudents.get(oddRow).size()>0){

                if(i>0){
                  if(roomStructure[i-1][j].equals(Integer.toString(oddRow))) {
                    if(i+1<room.getTotalRow()){
                      roomStructure[i][j]="";

                      i+=1;

                    }
                    if(i+1==room.getTotalRow()){
                      roomStructure[i][j]="";
                      break;
                    }
                  }
                }

                roomStructure[i][j] = Integer.toString(oddRow);
                List<SpStudent> studentsOfTheSubGroup = tempSubGroupWithStudents.get(oddRow);
                studentsOfTheSubGroup.remove(0);
                tempSubGroupWithStudents.put(oddRow,studentsOfTheSubGroup);
                if(oddRow>=numberOfSubGroups){
                  oddRow = divider+1;
                }else{
                  oddRow+=1;
                }
              }else{
                if(firstGroupWithZeroSize.size()==(numberOfSubGroups-divider)){
                  roomStructure[i][j]="";
                }
                else{
                  boolean alreadyInList = false;
                  if(firstGroupWithZeroSize.size()==0){
                    firstGroupWithZeroSize.add(oddRow);
                  }else{
                    if(firstGroupWithZeroSize.contains(oddRow)){
                      alreadyInList = true;
                    }else{
                      firstGroupWithZeroSize.add(oddRow);
                    }
                  }
                  oddRow+=1;
                  if(oddRow>numberOfSubGroups){
                    oddRow=divider+1;
                  }
                  if(roomStructure[i][j]==null){
                    i-=1;

                  }


                }

              }

            }
          }
        }

        //*********end of new Algorithm ******************


      /*  int numberOfStudents;
        List<Integer> tempSubGroupHolderGreaterThanMinimal=new ArrayList<>();


        if (room.getCapacity() % numberOfSubGroups == 0) {
          numberOfStudents = room.getCapacity()/numberOfSubGroups;
        }else{
          float partitionForTheSubGroup = (room.getTotalColumn()*room.getTotalRow())/numberOfSubGroups;
          numberOfStudents = (int)Math.ceil(partitionForTheSubGroup+1);

        }

        int numberOfStudentsLessThanAverage = 0;
        int numberOfStudentsHigherThanAverage=0;
        for(int subGroupIteratorForStudentNumber=1;subGroupIteratorForStudentNumber<=numberOfSubGroups;subGroupIteratorForStudentNumber++){
            if(subGroupWithStudents.get(subGroupIteratorForStudentNumber).size()>numberOfStudents){
              tempSubGroupHolderGreaterThanMinimal.add(subGroupIteratorForStudentNumber);
            }else{
              numberOfStudentsLessThanAverage += subGroupWithStudents.get(subGroupIteratorForStudentNumber).size();
            }
        }


        if(tempSubGroupHolderGreaterThanMinimal.size()<6){
          int leftTotalSpace = room.getCapacity()-numberOfStudentsLessThanAverage;
          if(leftTotalSpace% tempSubGroupHolderGreaterThanMinimal.size()==0){
            numberOfStudents = leftTotalSpace/tempSubGroupHolderGreaterThanMinimal.size();
          }
          else{
            float partitioner = leftTotalSpace/tempSubGroupHolderGreaterThanMinimal.size();
            numberOfStudents = (int) partitioner +1;
          }
        }
        int subGroupCounter=0;
        int totalStudentOfTheSubGroupInRoom=0;
        int studentPerSubGroupCounter=0;
        int seatAllocationCounter=0;
        while(true){
          boolean noMoreMembers=false;
          boolean noSpace=false;
          for(int row=0;row<room.getTotalRow();row++){
            boolean incraseRowNumberByOne=false;
            for(int col=0;col<room.getTotalColumn();col++){
              if(roomStructure[row][col]==null){
                if(subGroupCounter==0){
                  subGroupCounter=1;
                  for(int subGroupNumberIterator=subGroupCounter;subGroupNumberIterator<=numberOfSubGroups;subGroupNumberIterator++){
                    if(subGroupWithStudents.get(subGroupNumberIterator)!=null){
                      int sizeOfTheSubGroup = subGroupWithStudents.get(subGroupNumberIterator).size();
                      if(sizeOfTheSubGroup>0){
                        if(sizeOfTheSubGroup>numberOfStudents){
                          totalStudentOfTheSubGroupInRoom=numberOfStudents;
                        }
                        else{
                          totalStudentOfTheSubGroupInRoom=sizeOfTheSubGroup;
                        }
                        subGroupCounter=subGroupNumberIterator;
                        break;
                      }else{
                        if(subGroupNumberIterator==numberOfSubGroups){
                          noMoreMembers=true;
                          break;
                        }
                    }

                    }
                  }

                  roomStructure[row][col]=Integer.toString(subGroupCounter);
                  seatAllocationCounter+=1;
                  col+=1;
                  studentPerSubGroupCounter+=1;

                  if(subGroupCounter>=numberOfSubGroups && studentPerSubGroupCounter>=totalStudentOfTheSubGroupInRoom){
                    break;
                  }
                  if(studentPerSubGroupCounter>=totalStudentOfTheSubGroupInRoom){
                    for(int subGroupNumberIterator=subGroupCounter;subGroupNumberIterator<=numberOfSubGroups;subGroupNumberIterator++){
                      if(subGroupWithStudents.get(subGroupNumberIterator)!=null){
                        int sizeOfTheSubGroup = subGroupWithStudents.get(subGroupNumberIterator).size();
                        if(sizeOfTheSubGroup>0){
                          if(sizeOfTheSubGroup>numberOfStudents){
                            totalStudentOfTheSubGroupInRoom=numberOfStudents;
                          }
                          else{
                            totalStudentOfTheSubGroupInRoom=sizeOfTheSubGroup;
                          }
                          subGroupCounter=subGroupNumberIterator;
                          break;
                        }else{
                          if(subGroupNumberIterator==numberOfSubGroups){
                            noMoreMembers=true;
                            break;
                          }
                        }
                      }

                    }
                  }
                  if((col+1)>=room.getTotalColumn()){
                    incraseRowNumberByOne=true;
                    break;
                  }
                }else{
                  roomStructure[row][col]=Integer.toString(subGroupCounter);
                  seatAllocationCounter+=1;
                  col+=1;

                  studentPerSubGroupCounter+=1;

                  if(subGroupCounter>=numberOfSubGroups && studentPerSubGroupCounter>=totalStudentOfTheSubGroupInRoom){
                    break;
                  }
                  if(studentPerSubGroupCounter>=totalStudentOfTheSubGroupInRoom){
                    subGroupCounter+=1;
                    for(int subGroupNumberIterator=subGroupCounter;subGroupNumberIterator<=numberOfSubGroups;subGroupNumberIterator++){
                      int sizeOfTheSubGroup = subGroupWithStudents.get(subGroupNumberIterator).size();
                      if(sizeOfTheSubGroup>0){
                        if(sizeOfTheSubGroup>numberOfStudents){
                          totalStudentOfTheSubGroupInRoom=numberOfStudents;
                        }
                        else{
                          totalStudentOfTheSubGroupInRoom=sizeOfTheSubGroup;
                        }
                        subGroupCounter=subGroupNumberIterator;
                        break;
                      }
                    }
                    studentPerSubGroupCounter=0;
                  }
                  if((col+1)>=room.getTotalColumn()){
                    incraseRowNumberByOne=true;
                    break;
                  }

                }
              }
            }
            if(noMoreMembers){
              break;
            }
            if(subGroupCounter>=numberOfSubGroups && studentPerSubGroupCounter>=totalStudentOfTheSubGroupInRoom){
              break;
            }

            if(incraseRowNumberByOne){
              row+=1;
              if((row+1)>=room.getTotalRow()){
                if(seatAllocationCounter>=room.getCapacity()){
                  noSpace = true;
                }
                break;

              }
            }
          }
          if(noMoreMembers){
            break;
          }
          if(subGroupCounter>=numberOfSubGroups && studentPerSubGroupCounter>=totalStudentOfTheSubGroupInRoom){
            break;
          }
          if(noSpace){
            break;
          }

        }
*/

        for(int roomColumn=0;roomColumn<room.getTotalColumn();roomColumn++){
              for(int roomRow=0;roomRow<room.getTotalRow();roomRow++){
                boolean columnBreak=false;


                  for(int subGroup=1;subGroup<=numberOfSubGroups;subGroup++){
                    if(roomStructure[roomRow][roomColumn]!=null){
                      if(roomStructure[roomRow][roomColumn].equals(Integer.toString(subGroup))){
                        if(subGroupWithStudents.get(subGroup)!=null){
                          List<SpStudent> studentsOfTheSubGroup = subGroupWithStudents.get(subGroup);
                          SpStudent student = studentsOfTheSubGroup.get(0);
                          roomStructure[roomRow][roomColumn] = student.getId();
                          MutableSeatPlan seatPlan = new PersistentSeatPlan();
                          PersistentClassRoom classRoom = new PersistentClassRoom();
                          classRoom.setId(room.getId());
                          seatPlan.setClassRoom(classRoom);
                          seatPlan.setRowNo(roomRow+1);
                          seatPlan.setColumnNo(roomColumn+1);
                          PersistentSpStudent spStudent = new PersistentSpStudent();
                          spStudent.setId(student.getId());
                          seatPlan.setStudent(spStudent);
                          seatPlan.setExamType(pExamType);
                          PersistentSemester semester = new PersistentSemester();
                          semester.setId(pSemesterId);
                          seatPlan.setSemester(semester);
                          seatPlan.setGroupNo(pGroupNo);

                          totalSeatPlan.add(seatPlan);
                          studentsOfTheSubGroup.remove(0);
                          subGroupWithStudents.put(subGroup,studentsOfTheSubGroup);
                          break;
                        }


                      }
                    }

                  }

                }


              }


        int roomC=roomCounter;

        if((firstGroupWithZeroSize.size()+secondGroupWithZeroSize.size())==numberOfSubGroups){
          break;
        }

      }
    }

    /*for(MutableSeatPlan seatPlan:totalSeatPlan){
      mSeatPlanManager.create(seatPlan);
    }*/

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
          counter+=1;
        }else{
          studentsOfTheSubGroup.addAll(studentsOfTheGroup);
          counter+=1;
        }

      }

      subGroupMap.put(subGroupNumberIterator,studentsOfTheSubGroup);
    }
    return subGroupMap;
  }



}
