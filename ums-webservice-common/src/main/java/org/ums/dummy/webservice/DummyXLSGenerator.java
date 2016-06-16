package org.ums.dummy.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.domain.model.immutable.Teacher;
import org.ums.generator.XlsGenerator;
import org.ums.manager.DepartmentManager;
import org.ums.manager.TeacherManager;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Component
@Path("/dummyXls")
@Produces({"application/vnd.ms-excel"})
public class DummyXLSGenerator {

  @Autowired
  XlsGenerator xlsGenerator;

  @Autowired
  TeacherManager mTeacherManager;

  @Autowired
  DepartmentManager mDepartmentManager;

  @GET
  public StreamingOutput get() throws Exception {
    List<Teacher> teacherList = mTeacherManager.getByDepartment(mDepartmentManager.get("05"));
    return new StreamingOutput() {
      public void write(OutputStream output) throws IOException, WebApplicationException {
        try {
          xlsGenerator.build(teacherList, output, DummyXLSGenerator.class.getResourceAsStream("/report/xls/template/teacher_list.xls"));
        } catch (Exception e) {
          throw new WebApplicationException(e);
        }
      }
    };
  }
}
