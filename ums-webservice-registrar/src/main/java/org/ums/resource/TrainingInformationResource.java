package org.ums.resource;

import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Component
@Path("registrar/employee/training")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class TrainingInformationResource extends MutableTrainingInformationResource {
}
