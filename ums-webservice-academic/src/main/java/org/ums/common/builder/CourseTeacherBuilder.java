package org.ums.common.builder;

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

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class CourseTeacherBuilder implements Builder<CourseTeacher, MutableCourseTeacher> {
  @Autowired
  private CourseManager mCourseManager;
  @Autowired
  private TeacherManager mTeacherManager;
  @Autowired
  private SemesterManager mSemesterManager;

  @Override
  public void build(JsonObjectBuilder pBuilder, CourseTeacher pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) throws Exception {
    if (pReadOnly.getId() != null) {
      pBuilder.add("id", pReadOnly.getId());
    }

    Course course = (Course) pLocalCache.cache(() -> pReadOnly.getCourse(),
        pReadOnly.getCourseId(), Course.class);

    pBuilder.add("courseId", course.getId());
    pBuilder.add("courseNo", course.getNo());
    pBuilder.add("courseTitle", course.getTitle());
    pBuilder.add("courseCrHr", course.getCrHr());
    pBuilder.add("year", course.getYear());
    pBuilder.add("semester", course.getSemester());
    pBuilder.add("syllabusId", course.getSyllabusId());


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

    Syllabus syllabus = (Syllabus) pLocalCache.cache(() -> course.getSyllabus(),
        course.getSyllabusId(), Syllabus.class);

    Program program = (Program) pLocalCache.cache(() -> syllabus.getProgram(),
        syllabus.getProgramId(), Program.class);

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
  public void build(MutableCourseTeacher pMutable, JsonObject pJsonObject, LocalCache pLocalCache)
      throws Exception {
    if(pJsonObject.containsKey("id")) {
      pMutable.setId(pJsonObject.getInt("id"));
    }
    pMutable.setCourse(mCourseManager.get(pJsonObject.getString("courseId")));
    pMutable.setTeacher(mTeacherManager.get(pJsonObject.getString("teacherId")));
    pMutable
        .setSemester(mSemesterManager.get(Integer.parseInt(pJsonObject.getString("semesterId"))));
    if(pJsonObject.containsKey("section") && !StringUtils.isEmpty(pJsonObject.getString("section"))) {
      pMutable.setSection(pJsonObject.getString("section"));
    }

  }
}
