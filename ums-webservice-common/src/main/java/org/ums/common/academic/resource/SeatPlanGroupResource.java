package org.ums.common.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.common.Resource;
import org.ums.common.builder.SeatPlanGroupBuilder;
import org.ums.domain.model.immutable.EnrollmentFromTo;
import org.ums.domain.model.immutable.SeatPlanGroup;
import org.ums.domain.model.immutable.SemesterEnrollment;
import org.ums.manager.ProgramManager;
import org.ums.manager.SeatPlanGroupManager;
import org.ums.manager.SemesterManager;
import org.ums.response.type.GenericResponse;
import org.ums.services.academic.SeatPlanService;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.Map;

/**
 * Created by My Pc on 4/21/2016.
 */

@Component
@Path("/academic/seatPlanGroup")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class SeatPlanGroupResource extends MutableSeatPlanGroupResource{



  @GET
  @Path("/semester/{semester-id}/type/{type}/update/{update}")
  public JsonObject getSemesterList(final @Context Request pRequest,
                                    final @PathParam("semester-id") int pSemesterId,final @PathParam("type") int type,final @PathParam("update") int update) throws Exception {



    return mResourceHelper.getSeatPlanGroupBySemester(pSemesterId,type,update,pRequest,mUriInfo);
  }



}
