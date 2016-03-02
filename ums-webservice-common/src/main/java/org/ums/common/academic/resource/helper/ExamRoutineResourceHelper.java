package org.ums.common.academic.resource.helper;

import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.academic.builder.Builder;
import org.ums.academic.model.PersistentExamRoutine;
import org.ums.cache.LocalCache;
import org.ums.common.academic.resource.ResourceHelper;
import org.ums.domain.model.dto.ExamRoutineDto;
import org.ums.domain.model.mutable.MutableExamRoutine;
import org.ums.domain.model.readOnly.CourseTeacher;
import org.ums.domain.model.readOnly.ExamRoutine;
import org.ums.manager.ContentManager;
import org.ums.manager.ExamRoutineManager;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.json.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Arrays;
import java.util.List;

@Component
public class ExamRoutineResourceHelper extends ResourceHelper<ExamRoutine, MutableExamRoutine, Object> {

  @Autowired
  private ExamRoutineManager mManager;

  @Autowired
  private List<Builder<ExamRoutine, MutableExamRoutine>> mBuilders;

  @Override
  protected Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    throw new NotImplementedException();
  }

  @Override
  public ExamRoutineManager getContentManager() {
    return mManager;
  }

  @Override
  public List<Builder<ExamRoutine, MutableExamRoutine>> getBuilders() {
    return mBuilders;
  }

  @Override
  protected String getEtag(ExamRoutine pReadonly) {
    return null;
  }

  public JsonObject getExamRoutine(final Integer pSemesterId, final Integer pExamType) throws Exception {
    List<ExamRoutineDto> examRoutine = getContentManager().getExamRoutine(pSemesterId, pExamType);
   return buildJsonResponse(pSemesterId, pExamType,examRoutine);
  }

  protected JsonObject buildJsonResponse(final Integer pSemesterId, final Integer pExamType,final List<ExamRoutineDto> routineList) throws Exception {
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    String prevDateTime="",currDateTime= "";
    int prevProgram=0,currProgram= 0;
    int counter=0, dateTimeCounter=0,programCounter=0,courseCounter=0;
    String totalString="[";
    String dateTimeString="";String programString="";String courseString="";
    for (ExamRoutineDto routineDto : routineList) {
      routineDto.toString();
        currDateTime = routineDto.getExamDate()+routineDto.getExamTime();
        currProgram = routineDto.getProgramId();
        if(prevDateTime.equalsIgnoreCase(currDateTime) && counter!=0){
            if(prevProgram==currProgram && counter!=0){
              courseString+="{\"index\":"+courseCounter+",\"id\":\""+routineDto.getCourseId()+"\",\"no\":\""+routineDto.getCourseNumber()+"\",\"title\":\""+routineDto.getCourseTitle()+"\",\"year\":"+routineDto.getCourseYear()+",\"semester\":\""+routineDto.getCourseSemester()+"\",\"readOnly\":true},";
              courseCounter++;
            }
            else{
                    if(counter!=0){
                      if(courseString.length()>0)
                        courseString=courseString.substring(0,courseString.length()-1);
                      totalString+=dateTimeString+programString+courseString+"]},";
                      System.out.println(totalString);
                    }
              //dateTimeString="{\"index\":"+dateTimeCounter+",\"examDate\":\""+routineDto.getExamDate()+"\",\"examTime\":\""+routineDto.getExamTime()+"\",\"readOnly\":true,";
              dateTimeString="";
              courseCounter=0;
              programString="{\"index\":"+programCounter+",\"programId\":"+routineDto.getProgramId()+",\"programName\":\""+routineDto.getProgramName()+"\",\"readOnly\":true";
              courseString=",\"courses\":[{\"index\":"+courseCounter+",\"id\":\""+routineDto.getCourseId()+"\",\"no\":\""+routineDto.getCourseNumber()+"\",\"title\":\""+routineDto.getCourseTitle()+"\",\"year\":"+routineDto.getCourseYear()+",\"semester\":\""+routineDto.getCourseSemester()+"\",\"readOnly\":true},";
              programCounter++;
              courseCounter++;

            }
        }
      else{

              if(counter!=0){
                dateTimeCounter++;
                if(courseString.length()>0)
                  courseString=courseString.substring(0,courseString.length()-1);
                totalString+=dateTimeString+programString+courseString+"]}]},"; // first "]" is for ending the course array , second "}" is for ending the current program ,third  "]" is for ending the program array and the last '}' is for ending the current date time entry.
                System.out.println(totalString);
              }
          programCounter=0;
          courseCounter=0;
          dateTimeString="{\"index\":"+dateTimeCounter+",\"examDate\":\""+routineDto.getExamDate()+"\",\"examTime\":\""+routineDto.getExamTime()+"\",\"readOnly\":true,";
          programString="\"programs\":[{\"index\":"+programCounter+",\"programId\":"+routineDto.getProgramId()+",\"programName\":\""+routineDto.getProgramName()+"\",\"readOnly\":true";
          courseString=",\"courses\":[{\"index\":"+courseCounter+",\"id\":\""+routineDto.getCourseId()+"\",\"no\":\""+routineDto.getCourseNumber()+"\",\"title\":\""+routineDto.getCourseTitle()+"\",\"year\":"+routineDto.getCourseYear()+",\"semester\":\""+routineDto.getCourseSemester()+"\",\"readOnly\":true},";
          programCounter++;
          courseCounter++;
        }
      counter++;
      prevDateTime=currDateTime;
      prevProgram = currProgram;
     // children.add(routineDto.toString());
    }
    if(!dateTimeString.equalsIgnoreCase("")){
      if(courseString.length()>0)
        courseString=courseString.substring(0,courseString.length()-1);
      totalString+=dateTimeString+programString+courseString+"]}]}]";
    }
    System.out.println(totalString);


    object.add("entries", totalString);

    return object.build();
  }


public Response save(final JsonObject pJsonObject) throws Exception {
  PersistentExamRoutine mutable =new PersistentExamRoutine();
  for (Builder<ExamRoutine, MutableExamRoutine> builder : getBuilders()) {
    builder.build(mutable, pJsonObject, null);
  };
  mutable.delete();
  mutable.save();
  return Response.noContent().build();
}



}
