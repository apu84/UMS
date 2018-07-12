package org.ums.academic.resource.teacher.assigned;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.CourseTeacher;
import org.ums.domain.model.mutable.MutableCourseTeacher;
import org.ums.generator.IdGenerator;
import org.ums.manager.CourseTeacherManager;
import org.ums.persistent.model.PersistentCourseTeacher;

import javax.json.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.*;

@Component
public class CourseTeacherResourceHelper extends
    AbstractAssignedTeacherResourceHelper<CourseTeacher, MutableCourseTeacher, Long, CourseTeacherManager> {
  @Autowired
  @Qualifier("courseTeacherManager")
  CourseTeacherManager mCourseTeacherManager;

  @Autowired
  IdGenerator mIdGenerator;

  @Autowired
  private CourseTeacherBuilder mBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) {
    modifyContent(pJsonObject);
    Response.ResponseBuilder builder = Response.ok();
    return builder.build();
  }

  public JsonArray createOrUpdateCourseTeacher(JsonArray pJsonArray, UriInfo pUriInfo) {
    List<MutableCourseTeacher> courseTeacherList = new ArrayList<>();
    Set<MutableCourseTeacher> newCourseTeacherDistinctList = new HashSet<>();
    Set<MutableCourseTeacher> existingCourseTeacherDistinctList = new HashSet<>();
    for(int i = 0; i < pJsonArray.size(); i++) {
      LocalCache localCache = new LocalCache();
      JsonObject jsonObject = pJsonArray.getJsonObject(i);
      MutableCourseTeacher courseTeacher = new PersistentCourseTeacher();
      getBuilder().build(courseTeacher, jsonObject, localCache);
      if(courseTeacher.getId() == null) {
        courseTeacher.setId(mIdGenerator.getNumericId());
        newCourseTeacherDistinctList.add(courseTeacher);
      }
      else {
        existingCourseTeacherDistinctList.add(courseTeacher);
      }
    }
    if(newCourseTeacherDistinctList.size() > 0)
      getContentManager().create(Collections.list(Collections.enumeration(newCourseTeacherDistinctList)));
    if(existingCourseTeacherDistinctList.size() > 0)
      getContentManager().update(Collections.list(Collections.enumeration(existingCourseTeacherDistinctList)));

    newCourseTeacherDistinctList.addAll(existingCourseTeacherDistinctList);
    courseTeacherList.addAll(newCourseTeacherDistinctList);

    return getCourseTeacher(courseTeacherList.get(0).getCourse().getOfferedToProgramId(), courseTeacherList.get(0)
        .getSemester().getId(), courseTeacherList.get(0).getSection(), courseTeacherList.get(0).getCourse().getYear(),
        courseTeacherList.get(0).getCourse().getSemester(), pUriInfo);
  }

  public Response delete(JsonArray pJsonArray, UriInfo pUriInfo) throws Exception {
    List<MutableCourseTeacher> teacherList = new ArrayList<>();
    for(int i = 0; i < pJsonArray.size(); i++) {
      LocalCache localCache = new LocalCache();
      JsonObject jsonObject = pJsonArray.getJsonObject(i);
      MutableCourseTeacher courseTeacher = new PersistentCourseTeacher();
      getBuilder().build(courseTeacher, jsonObject, localCache);
      teacherList.add(courseTeacher);
    }
    getContentManager().delete(teacherList);
    return Response.ok().build();
  }

  public JsonArray getCourseTeacher(Integer pSemesterId, String pCourseId, String pSection, UriInfo pUriInfo) {
    List<CourseTeacher> courseTeacherList = getContentManager().getCourseTeacher(pSemesterId, pCourseId, pSection);
    JsonArrayBuilder courseTeacherArrayBuilder = Json.createArrayBuilder();
    for(CourseTeacher courseTeacher : courseTeacherList) {
      LocalCache localCache = new LocalCache();
      JsonObjectBuilder courseTeacherObjectBuilder = Json.createObjectBuilder();
      getBuilder().build(courseTeacherObjectBuilder, courseTeacher, pUriInfo, localCache);
      courseTeacherArrayBuilder.add(courseTeacherObjectBuilder);
    }
    return courseTeacherArrayBuilder.build();
  }

  public JsonArray getCourseTeacher(Integer pProgramId, Integer pSemesterId, String pSection, int pYear, int pSemester,
      UriInfo pUriInfo) {
    List<CourseTeacher> courseTeacherList =
        getContentManager().getCourseTeacher(pProgramId, pSemesterId, pSection, pYear, pSemester);
    JsonArrayBuilder courseTeacherArrayBuilder = Json.createArrayBuilder();
    for(CourseTeacher courseTeacher : courseTeacherList) {
      LocalCache localCache = new LocalCache();
      JsonObjectBuilder courseTeacherObjectBuilder = Json.createObjectBuilder();
      getBuilder().build(courseTeacherObjectBuilder, courseTeacher, pUriInfo, localCache);
      courseTeacherArrayBuilder.add(courseTeacherObjectBuilder);
    }
    return courseTeacherArrayBuilder.build();
  }

  @Override
  protected CourseTeacherManager getContentManager() {
    return mCourseTeacherManager;
  }

  @Override
  protected CourseTeacherBuilder getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(CourseTeacher pReadonly) {
    return pReadonly.getLastModified();
  }

  protected void modifyContent(JsonObject pJsonObject) {
    LocalCache localCache = new LocalCache();
    JsonArray entries = pJsonObject.getJsonArray("entries");

    for(int i = 0; i < entries.size(); i++) {
      JsonObject jsonObject = entries.getJsonObject(i);
      String updateType = jsonObject.getString("updateType");
      MutableCourseTeacher mutableCourseTeacher = new PersistentCourseTeacher();
      getBuilder().build(mutableCourseTeacher, jsonObject, localCache);

      switch(updateType) {
        case "insert":
          mutableCourseTeacher.create();
          break;
        case "update":
          CourseTeacher courseTeacher = mCourseTeacherManager.get(mutableCourseTeacher.getId());
          MutableCourseTeacher updateCourseTeacher = courseTeacher.edit();
          updateCourseTeacher.setTeacher(mutableCourseTeacher.getTeacher());
          updateCourseTeacher.setSemester(mutableCourseTeacher.getSemester());
          updateCourseTeacher.setSection(mutableCourseTeacher.getSection());
          updateCourseTeacher.setCourse(mutableCourseTeacher.getCourse());
          updateCourseTeacher.update();
          break;
        case "delete":
          CourseTeacher teacher = mCourseTeacherManager.get(mutableCourseTeacher.getId());
          MutableCourseTeacher deleteCourseTeacher = teacher.edit();
          deleteCourseTeacher.delete();
          break;
      }
    }

  }
}
