package org.ums.common.academic.resource.helper;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.academic.builder.Builder;
import org.ums.academic.builder.OptionalCourseApplicationBuilder;
import org.ums.academic.dao.PersistentOptionalCourseApplicationDao;
import org.ums.academic.model.PersistentExamRoutine;
import org.ums.domain.model.dto.ExamRoutineDto;
import org.ums.domain.model.dto.OptCourseStudentDto;
import org.ums.domain.model.dto.OptionalCourseApplicationStatDto;
import org.ums.domain.model.mutable.MutableExamRoutine;
import org.ums.domain.model.readOnly.Course;
import org.ums.domain.model.readOnly.ExamRoutine;

import javax.json.*;
import javax.ws.rs.core.Response;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class OptionalCourseApplicationResourceHelper {

  @Autowired
  private PersistentOptionalCourseApplicationDao mManager;

  @Autowired
  private OptionalCourseApplicationBuilder mBuilder;


  public PersistentOptionalCourseApplicationDao getContentManager() {
    return mManager;
  }

  public OptionalCourseApplicationBuilder getBuilder() {
    return mBuilder;
  }



  public JsonObject getApplicationStatistics(final Integer pSemesterId,final Integer pProgramId) throws Exception {
    List<OptionalCourseApplicationStatDto> statList= mManager.getApplicationStatistics(pSemesterId, pProgramId,1,1);

    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    for (OptionalCourseApplicationStatDto stat : statList) {
      children.add(stat.toString());
    }
    object.add("entries", children);
    return object.build();
  }


  public Response saveApprovedAndApplicationCourses(final Integer pSemesterId,final Integer pProgramId,int pYear,int pSemester, final JsonObject pJsonObject) throws Exception {
    OptionalCourseApplicationBuilder builder=getBuilder();
    List<Course> approvedCourseList=new ArrayList<>();
    builder.build(approvedCourseList, pJsonObject,"approved");

    List<Course> callForApplicationCourseList=new ArrayList<>();
    builder.build(callForApplicationCourseList, pJsonObject,"callForApplication");


    mManager.deleteApplicationCourses(pSemesterId, pProgramId, pYear, pSemester);
    mManager.insertApplicationCourses(pSemesterId, pProgramId, pYear, pSemester, callForApplicationCourseList);
    mManager.insertApprovedCourses(pSemesterId,pProgramId,pYear,pSemester,approvedCourseList);

    return Response.noContent().build();
  }

  public JsonObject getStudentList(int pSemesterId,String pCourseId, String pStatus) throws Exception {
    List<OptCourseStudentDto> studentList = getContentManager().getStudentList(pSemesterId,pCourseId,pStatus);
    Gson gson = new Gson();
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();

    JsonReader jsonReader;
    JsonObject object1;



    for (OptCourseStudentDto student : studentList) {
      jsonReader = Json.createReader(new StringReader(student.toString()));
      object1 = jsonReader.readObject();
      jsonReader.close();
      children.add(object1);
    }
    object.add("entries", children);
    return object.build();
  }

}
