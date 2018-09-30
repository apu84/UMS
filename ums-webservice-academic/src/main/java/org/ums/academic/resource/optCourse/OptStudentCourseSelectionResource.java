package org.ums.academic.resource.optCourse;

import org.springframework.stereotype.Component;
import org.ums.domain.model.mutable.optCourse.MutableOptStudentCourseSelection;
import org.ums.resource.Resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Created by Monjur-E-Morshed on 9/27/2018.
 */
@Component
@Path("academic/optStudentCourseSelection")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class OptStudentCourseSelectionResource extends MutableOptStudentCourseSelectionResource {
}
