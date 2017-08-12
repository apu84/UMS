package org.ums.meeting;

import org.springframework.stereotype.Component;
import org.ums.resource.Resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Component
@Path("meeting/schedule")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class ScheduleResource extends MutableScheduleResource {
}
