package org.ums.accounts.resource.definitions.financial.account.year;

import org.springframework.stereotype.Component;
import org.ums.domain.model.immutable.accounts.FinancialAccountYear;
import org.ums.enums.accounts.definitions.financial.account.year.FinancialAccountYearTransferType;
import org.ums.logs.GetLog;
import org.ums.resource.Resource;
import org.ums.util.UmsUtils;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 28-Dec-17.
 */
@Component
@Path("/account/definition/financialAccountYear")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class FinancialAccountYearResource extends MutableFinancialAccountyearResource {

  @GET
  @GetLog(message = "Requested for fetching all financial account year")
  @Path("/all")
  public List<FinancialAccountYear> getAll(@Context HttpServletRequest pHttpServletRequest) {
    List<FinancialAccountYear> years = mFinancialAccountYearManager.getAll();
    return years;
  }

  @GET
  @GetLog(message = "Requested for fetching the opened financial account year")
  @Path("/openedYear")
  public FinancialAccountYear getOpenedFinancialAccountYear(@Context HttpServletRequest pHttpServletRequest) {
    return mHelper.getContentManager().getOpenedFinancialAccountYear();
  }

  @GET
  @GetLog(message = "Requested for closing current financial account year and opening a new year.")
  @Path("/startDate/{startDate}/endDate/{endDate}/transferType/{transferType}")
  public List<FinancialAccountYear> closeCurrentYearAndCreateNewFinancialAccountYear(
      @Context HttpServletRequest pHttpServletRequest, @PathParam("startDate") String pStartDate,
      @PathParam("endDate") String pEndDate, @PathParam("transferType") Integer pTransferType) throws Exception {
    mHelper.closeAndCreateNewFinancialAccountYear(UmsUtils.convertToDate(pStartDate, "dd-MM-yyyy"),
        UmsUtils.convertToDate(pEndDate, "dd-MM-yyyy"), FinancialAccountYearTransferType.get(pTransferType));
    return mResourceContext.getResource(FinancialAccountYearResource.class).getAll(pHttpServletRequest);
  }

}
