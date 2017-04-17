package org.ums.resource;

import org.springframework.stereotype.Component;
import org.ums.domain.model.mutable.registrar.MutablePublicationInformation;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Component
@Path("registrar/employee/publication")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class PublicationInformationResource extends MutablePublicationInformationResource {
}
