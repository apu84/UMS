package org.ums.academic.resource;

import org.springframework.stereotype.Component;
import org.ums.domain.model.mutable.MutableEmpExamAttendance;
import org.ums.resource.Resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Created by Monjur-E-Morshed on 7/27/2018.
 */
@Component
@Path("academic/empExamAttendance")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class EmpExamAttendanceResource extends MutableEmpExamAttendanceResource {

}
