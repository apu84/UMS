package org.ums.common.academic.resource.helper;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.common.ResourceHelper;
import org.ums.common.builder.ExamRoutineBuilder;
import org.ums.domain.model.dto.ExamRoutineDto;
import org.ums.domain.model.immutable.ExamRoutine;
import org.ums.domain.model.immutable.SeatPlanGroup;
import org.ums.domain.model.mutable.MutableExamRoutine;
import org.ums.manager.ExamRoutineManager;
import org.ums.manager.SeatPlanGroupManager;
import org.ums.manager.SubGroupManager;
import org.ums.persistent.model.PersistentExamRoutine;
import org.ums.cache.LocalCache;
import javax.json.JsonArrayBuilder;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import javax.ws.rs.core.Request;
@Component
public class ExamRoutineResourceHelper extends ResourceHelper<ExamRoutine, MutableExamRoutine, Object> {

  @Autowired
  private ExamRoutineManager mManager;

  @Autowired
  private ExamRoutineBuilder mBuilder;

  @Autowired
  private SeatPlanGroupManager mSeatPlanGroupManager;

  @Autowired
  private SubGroupManager mSubGroupManager;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    throw new NotImplementedException("Post method not implemented for ExamRoutineResourceHelper");
  }

  @Override
  public ExamRoutineManager getContentManager() {
    return mManager;
  }

  @Override
  public ExamRoutineBuilder getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getEtag(ExamRoutine pReadonly) {
    return null;
  }

  public JsonObject getExamRoutine(final Integer pSemesterId, final Integer pExamType) throws Exception {
    List<ExamRoutineDto> examRoutine = getContentManager().getExamRoutine(pSemesterId, pExamType);
    return buildJsonResponse(pSemesterId, pExamType, examRoutine);
  }

  public JsonObject getExamRoutineForCCI(Integer pSemesterId, Integer pExamType, final Request pRequest, final UriInfo pUriInfo) throws Exception{
    List<ExamRoutineDto> examRoutine = getContentManager().getExamRoutineForApplicationCCI(pSemesterId,pExamType);
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for(ExamRoutineDto exam: examRoutine){
      PersistentExamRoutine ex = new PersistentExamRoutine();
      ex.setExamDate( exam.getExamDate());
      ex.setTotalStudent(exam.getTotalStudent());
      ex.setExamDateOriginal(exam.getExamDateOriginal());
      ExamRoutine exRegular = ex;
      children.add(toJson(exRegular, pUriInfo, localCache));

    }

    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }
  protected JsonObject buildJsonResponse(final Integer pSemesterId, final Integer pExamType, final List<ExamRoutineDto> routineList) throws Exception {
    JsonObjectBuilder object = Json.createObjectBuilder();
    String prevDateTime = "", currDateTime = "";
    int prevProgram = 0, currProgram = 0;
    int counter = 0, dateTimeCounter = 0, programCounter = 0, courseCounter = 0;
    String totalString = "[";
    String dateTimeString = "";
    String programString = "";
    String courseString = "";
    for (ExamRoutineDto routineDto : routineList) {
      routineDto.toString();
      currDateTime = routineDto.getExamDate() + routineDto.getExamTime();
      currProgram = routineDto.getProgramId();
      if (prevDateTime.equalsIgnoreCase(currDateTime) && counter != 0) {
        if (prevProgram == currProgram && counter != 0) {
          courseString += "{\"index\":" + courseCounter + ",\"id\":\"" + routineDto.getCourseId() + "\",\"no\":\"" + routineDto.getCourseNumber() + "\",\"title\":\"" + routineDto.getCourseTitle() + "\",\"year\":" + routineDto.getCourseYear() + ",\"semester\":\"" + routineDto.getCourseSemester() + "\",\"readOnly\":true},";
          courseCounter++;
        } else {
          if (counter != 0) {
            if (courseString.length() > 0)
              courseString = courseString.substring(0, courseString.length() - 1);
            totalString += dateTimeString + programString + courseString + "]},";
            System.out.println(totalString);
          }
          //dateTimeString="{\"index\":"+dateTimeCounter+",\"examDate\":\""+routineDto.getExamDate()+"\",\"examTime\":\""+routineDto.getExamTime()+"\",\"readOnly\":true,";
          dateTimeString = "";
          courseCounter = 0;
          programString = "{\"index\":" + programCounter + ",\"programId\":" + routineDto.getProgramId() + ",\"programName\":\"" + routineDto.getProgramName() + "\",\"readOnly\":true";
          courseString = ",\"courses\":[{\"index\":" + courseCounter + ",\"id\":\"" + routineDto.getCourseId() + "\",\"no\":\"" + routineDto.getCourseNumber() + "\",\"title\":\"" + routineDto.getCourseTitle() + "\",\"year\":" + routineDto.getCourseYear() + ",\"semester\":\"" + routineDto.getCourseSemester() + "\",\"readOnly\":true},";
          programCounter++;
          courseCounter++;

        }
      } else {

        if (counter != 0) {
          dateTimeCounter++;
          if (courseString.length() > 0)
            courseString = courseString.substring(0, courseString.length() - 1);
          totalString += dateTimeString + programString + courseString + "]}]},"; // first "]" is for ending the course array , second "}" is for ending the current program ,third  "]" is for ending the program array and the last '}' is for ending the current date time entry.
          System.out.println(totalString);
        }
        programCounter = 0;
        courseCounter = 0;
        dateTimeString = "{\"index\":" + dateTimeCounter + ",\"examDate\":\"" + routineDto.getExamDate() + "\",\"examTime\":\"" + routineDto.getExamTime() + "\",\"readOnly\":true,";
        programString = "\"programs\":[{\"index\":" + programCounter + ",\"programId\":" + routineDto.getProgramId() + ",\"programName\":\"" + routineDto.getProgramName() + "\",\"readOnly\":true";
        courseString = ",\"courses\":[{\"index\":" + courseCounter + ",\"id\":\"" + routineDto.getCourseId() + "\",\"no\":\"" + routineDto.getCourseNumber() + "\",\"title\":\"" + routineDto.getCourseTitle() + "\",\"year\":" + routineDto.getCourseYear() + ",\"semester\":\"" + routineDto.getCourseSemester() + "\",\"readOnly\":true},";
        programCounter++;
        courseCounter++;
      }
      counter++;
      prevDateTime = currDateTime;
      prevProgram = currProgram;
      // children.add(routineDto.toString());
    }
    if (!dateTimeString.equalsIgnoreCase("")) {
      if (courseString.length() > 0)
        courseString = courseString.substring(0, courseString.length() - 1);
      totalString += dateTimeString + programString + courseString + "]}]}]";
    } else
      totalString = totalString.substring(0,totalString.length()-1) +"]}"+ "]";

    System.out.println(totalString);


    object.add("entries", totalString);

    return object.build();
  }


  public Response save(final JsonObject pJsonObject,int pSemesterId,int pExamType) throws Exception {
    //check if the group for the exam is already created, if created, the group will be deleted
    // commented temporary and will be release later
    /*
    List<SeatPlanGroup> groups = mSeatPlanGroupManager.getGroupBySemester(pSemesterId,pExamType);
    if(groups.size()>0){
      mSeatPlanGroupManager.deleteBySemesterAndExamType(pSemesterId,pExamType);
      for(int i=1;i<=3;i++){
        mSubGroupManager.deleteBySemesterGroupAndType(pSemesterId,i,pExamType);
      }
    }
    */
    PersistentExamRoutine mutable = new PersistentExamRoutine();
    getBuilder().build(mutable, pJsonObject, null);
    mutable.delete();
    mutable.save();
    return Response.noContent().build();
  }


}
