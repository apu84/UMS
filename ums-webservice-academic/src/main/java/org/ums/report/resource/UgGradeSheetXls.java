package org.ums.report.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.domain.model.dto.StudentGradeDto;
import org.ums.enums.CourseType;
import org.ums.enums.ExamType;
import org.ums.generator.XlsGenerator;
import org.ums.manager.ExamGradeManager;
import org.ums.resource.Resource;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ifti on 17-Jun-16.
 */
@Component
@Path("/gradeReport/xls/")
@Produces({"application/vnd.ms-excel"})
public class UgGradeSheetXls extends Resource {

  @Autowired
  ExamGradeManager mExamGradeManager;

  @Autowired
  XlsGenerator xlsGenerator;

  @GET
  @Path("/semester/{semester-id}/courseid/{course-id}/examtype/{exam-type}/coursetype/{course-type}/role/{role}/totalpart/{total-part}")
  public StreamingOutput get(final @Context Request pRequest, final @PathParam("semester-id") Integer pSemesterId,
      final @PathParam("course-id") String pCourseId, final @PathParam("exam-type") Integer pExamTypeId,
      final @PathParam("course-type") Integer pCourseType, final @PathParam("role") String pRequestedRole,
      final @PathParam("total-part") String pTotalPart) {
    NumberFormat nf = new DecimalFormat("##.#");
    List<StudentGradeDto> gradeList =
        mExamGradeManager.getAllGrades(pSemesterId, pCourseId, ExamType.get(pExamTypeId), CourseType.get(pCourseType));
    List<StudentGradeDto> modifiedGradeList = new ArrayList<>();
    for(StudentGradeDto gradeDto : gradeList) {
      if(gradeDto.getPartAAddiInfo() == null) {
        gradeDto.setPartAAddiInfo(String.valueOf(gradeDto.getPartA() == null ? "" : nf.format(gradeDto.getPartA())));
      }
      if(gradeDto.getPartBAddiInfo() == null) {
        gradeDto.setPartBAddiInfo(String.valueOf(gradeDto.getPartB() == null ? "" : nf.format(gradeDto.getPartB())));
      }
      modifiedGradeList.add(gradeDto);
    }
    return new StreamingOutput() {
      public void write(OutputStream output) throws IOException, WebApplicationException {
        try {
          String fileName = CourseType.get(pCourseType).getLabel();
          if(CourseType.get(pCourseType) == CourseType.THEORY) {
            fileName = CourseType.get(pCourseType).getLabel() + "_" + pTotalPart + "Part";
          }
          InputStream a = UgGradeSheetXls.class.getResourceAsStream("/report/xls/template/" + fileName + ".xls");
          xlsGenerator.build(modifiedGradeList, output, a);
        } catch(Exception e) {
          throw new WebApplicationException(e);
        }
      }
    };
  }
}
