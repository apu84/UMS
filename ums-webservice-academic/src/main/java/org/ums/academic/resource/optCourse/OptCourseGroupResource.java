package org.ums.academic.resource.optCourse;

import org.springframework.stereotype.Component;
import org.ums.domain.model.mutable.optCourse.MutableOptCourseGroup;
import org.ums.resource.Resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Created by Monjur-E-Morshed on 8/29/2018.
 */
@Component
@Path("academic/optCourseGroup")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class OptCourseGroupResource extends MutableOptCourseGroupResource {
}
