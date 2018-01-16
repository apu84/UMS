package org.ums.accounts.resource.definitions.period.close;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.ums.domain.model.immutable.accounts.PeriodClose;
import org.ums.domain.model.mutable.accounts.MutablePeriodClose;
import org.ums.resource.Resource;

import javax.json.JsonArray;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 04-Jan-18.
 */
@Component
@Path("account/definition/period-close")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class PeriodCloseResource extends MutablePeriodCloseResource {

  @GET
  @Path("/year-type/{year-type}")
  public List<PeriodClose> getPeriodCloseList(final @Context Request pRequest, @PathParam("year-type") String pYearTYpe)
      throws Exception {
    List<PeriodClose> periodCloseList =
        pYearTYpe.equals("current") ? mHelper.getContentManager().getByCurrentYear() : mHelper.getContentManager()
            .getByPreviousYear();
    
    periodCloseList.sort((o1, o2) -> o1.getMonthId().compareTo(o2.getMonthId()));
    ObjectMapper mapper = new ObjectMapper();
    return periodCloseList;
  }

  @POST
  @Path("/save")
  public List<MutablePeriodClose> saveAndReturnUpdatedList(final JsonArray pJsonObject) throws Exception {

    return mHelper.saveAndReturnUpdatedList(pJsonObject);
  }
}
