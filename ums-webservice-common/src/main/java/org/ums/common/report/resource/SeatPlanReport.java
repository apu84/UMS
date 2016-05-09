package org.ums.common.report.resource;

import org.springframework.stereotype.Component;
import org.ums.common.Resource;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Created by My Pc on 5/9/2016.
 */

@Component
@Path("/seatPlanReport")
@Produces({"applciation/pdf"})
public class SeatPlanReport extends Resource{

}
