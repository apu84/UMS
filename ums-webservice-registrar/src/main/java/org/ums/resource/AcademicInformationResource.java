package org.ums.resource;

import org.springframework.stereotype.Component;
import org.ums.domain.model.mutable.registrar.MutableExperienceInformation;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Component
@Path("registrar/employee/academic")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class AcademicInformationResource extends MutableAcademicInformationResource {

}
