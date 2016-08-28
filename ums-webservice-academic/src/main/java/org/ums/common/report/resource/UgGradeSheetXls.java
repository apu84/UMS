package org.ums.common.report.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.common.Resource;
import org.ums.common.report.generator.UgGradeSheetGenerator;
import org.ums.domain.model.dto.StudentGradeDto;
import org.ums.dummy.webservice.DummyXLSGenerator;
import org.ums.enums.CourseType;
import org.ums.generator.XlsGenerator;
import org.ums.manager.ExamGradeManager;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by Ifti on 17-Jun-16.
 */
@Component
@Path("/gradeReport/xls/")
@Produces({"application/vnd.ms-excel"})
public class UgGradeSheetXls extends Resource {
  @Autowired
  UgGradeSheetGenerator mUgGradeSheetGenerator;
  @Autowired
  ExamGradeManager mExamGradeManager;

  @Autowired
  XlsGenerator xlsGenerator;

  @GET
  @Path("/semester/{semester-id}/courseid/{course-id}/examtype/{exam-type}/coursetype/{course-type}/role/{role}")
  public StreamingOutput get(final @Context Request pRequest,
                             final @PathParam("semester-id") Integer pSemesterId,
                             final @PathParam("course-id") String pCourseId,
                             final @PathParam("exam-type") Integer pExamTypeId,
                             final @PathParam("course-type") Integer pCourseType,
                             final @PathParam("role") String pRequestedRole) throws Exception {
    List<StudentGradeDto> gradeList = mExamGradeManager.getAllGrades(pSemesterId,pCourseId,pExamTypeId, CourseType.get(pCourseType));
    return new StreamingOutput() {
      public void write(OutputStream output) throws IOException, WebApplicationException {
        try {
          InputStream a=DummyXLSGenerator.class.getResourceAsStream("/report/xls/template/"+CourseType.get(pCourseType).toString().toLowerCase()+".xls");
          xlsGenerator.build(gradeList, output, a);
        } catch (Exception e) {
          throw new WebApplicationException(e);
        }
      }
    };
  }
}
