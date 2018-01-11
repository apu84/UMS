package org.ums.accounts.resource.definitions.period.close.month;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.domain.model.immutable.accounts.Month;
import org.ums.manager.accounts.MonthManager;
import org.ums.resource.Resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 11-Jan-18.
 */
@Component
@Path("account/definition/account")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class MonthResource {

  @Autowired
  private MonthManager mMonthManager;

  @GET
  @Path("/all")
  public List<Month> getAllMonths() {
    return mMonthManager.getAll();
  }
}
