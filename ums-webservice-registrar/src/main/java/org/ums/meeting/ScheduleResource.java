package org.ums.meeting;

import org.springframework.stereotype.Component;
import javax.ws.rs.Path;

@Component
@Path("meeting/schedule")
public class ScheduleResource extends MutableScheduleResource {
}
