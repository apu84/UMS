package org.ums.academic.resource.teacher.assigned;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.*;
import org.ums.domain.model.mutable.MutableCourseTeacher;
import org.ums.manager.CourseManager;
import org.ums.manager.SemesterManager;
import org.ums.manager.TeacherManager;

@Component
public class CourseTeacherBuilder implements Builder<CourseTeacher, MutableCourseTeacher> {
  @Autowired
  private CourseManager mCourseManager;
  @Autowired
  private TeacherManager mTeacherManager;
  @Autowired
  private SemesterManager mSemesterManager;

  @Override
  public void build(JsonObjectBuilder pBuilder, CourseTeacher pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    if (pReadOnly.getId() != null && pReadOnly.getId() != 0) {
      pBuilder.add("id", pReadOnly.getId().toString());
    }

    Course course = (Course) pLocalCache.cache(() -> pReadOnly.getCourse(),
        pReadOnly.getCourseId(), Course.class);

    pBuilder.add("courseType", course.getCourseType().getId());
    pBuilder.add("courseId", course.getId());
    pBuilder.add("courseNo", course.getNo());
    pBuilder.add("courseTitle", course.getTitle());
    pBuilder.add("courseCrHr", course.getCrHr());
    pBuilder.add("year", course.getYear());
    pBuilder.add("semester", course.getSemester());
    //pBuilder.add("syllabusId", course.getSyllabusId());


    if (!StringUtils.isEmpty(pReadOnly.getTeacherId())) {
      Teacher teacher = (Teacher) pLocalCache.cache(() -> pReadOnly.getTeacher(),
          pReadOnly.getTeacherId(), Teacher.class);

      pBuilder.add("teacherId", teacher.getId());
      pBuilder.add("teacherName", teacher.getName());
    }
    if (pReadOnly.getSection() != null) {
      pBuilder.add("section", pReadOnly.getSection());
    }

    Department department = (Department) pLocalCache.cache(() -> course.getOfferedBy(),
        course.getOfferedDepartmentId(), Department.class);

    Program program = (Program) pLocalCache.cache(() -> course.getOfferedToProgram(),
        course.getOfferedDepartmentId(), Program.class);

    pBuilder.add("programName", program.getShortName());

    Department offeredToDepartment = (Department) pLocalCache.cache(() -> program.getDepartment(),
        program.getDepartmentId(), Department.class);

    pBuilder.add("courseOfferedByDepartmentId", department.getId());
    pBuilder.add("courseOfferedByDepartmentName", department.getShortName());
    pBuilder.add("courseOfferedToDepartmentId", offeredToDepartment.getId());
    pBuilder.add("courseOfferedToDepartmentName", offeredToDepartment.getShortName());
    pBuilder.add("semesterName", pReadOnly.getSemester().getName());
  }

  @Override
  public void build(MutableCourseTeacher pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    if(pJsonObject.containsKey("id")) {
      pMutable.setId(Long.parseLong(pJsonObject.getString("id")));
    }
    pMutable.setCourse(mCourseManager.get(pJsonObject.getString("courseId")));
    pMutable.setTeacher(mTeacherManager.get(pJsonObject.getString("teacherId")));
    JsonValue semesterIdObject = pJsonObject.get("semesterId");
    if(semesterIdObject.getValueType().toString().equalsIgnoreCase("integer")) {
      pMutable.setSemester(mSemesterManager.get(pJsonObject.getInt("semesterId")));
    }
    else if(semesterIdObject.getValueType().toString().equalsIgnoreCase("string")) {
      pMutable.setSemester(mSemesterManager.get(Integer.parseInt(pJsonObject.getString("semesterId"))));
    }
    if(pJsonObject.containsKey("section") && !StringUtils.isEmpty(pJsonObject.getString("section"))) {
      pMutable.setSection(pJsonObject.getString("section"));
    }

  }
}
