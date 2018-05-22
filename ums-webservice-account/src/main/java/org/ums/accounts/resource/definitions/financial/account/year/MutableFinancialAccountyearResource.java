package org.ums.accounts.resource.definitions.financial.account.year;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.domain.model.immutable.accounts.FinancialAccountYear;
import org.ums.domain.model.mutable.accounts.MutableFinancialAccountYear;
import org.ums.manager.accounts.FinancialAccountYearManager;
import org.ums.persistent.model.accounts.PersistentFinancialAccountYear;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 28-Dec-17.
 */
public class MutableFinancialAccountyearResource {
  @Autowired
  FinancialAccountYearManager mFinancialAccountYearManager;
  @Autowired
  FinancialAccountYearResourceHelper mHelper;

  @POST
  @Path("/save")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public List<FinancialAccountYear> saveAndReturnUpdatedList(
      PersistentFinancialAccountYear pPersistentFinancialAccountYear) {
    MutableFinancialAccountYear year = pPersistentFinancialAccountYear;
    return mHelper.updateFinancialAccountYear(year);
  }

  @PUT
  @Path("/closeType/{closeType}/startDate/{startDate}/endDate/{endDate}/transferType/{transferType}")
  public List<FinancialAccountYear> closeCurrentYearAndCreateNewFinancialAccountYear(
      @PathParam("closeType") String pCloseType, @PathParam("startDate") String pStartDate,
      @PathParam("endDate") String pEndDate, @PathParam("transferType") Integer pTransferType) {
    return null;
  }
}
