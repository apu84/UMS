package org.ums.accounts.resource.definitions.financial.account.year;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.domain.model.immutable.accounts.FinancialAccountYear;
import org.ums.domain.model.mutable.accounts.MutableFinancialAccountYear;
import org.ums.enums.accounts.definitions.financial.account.year.FinancialAccountYearTransferType;
import org.ums.logs.GetLog;
import org.ums.logs.PostLog;
import org.ums.logs.PutLog;
import org.ums.manager.accounts.FinancialAccountYearManager;
import org.ums.persistent.model.accounts.PersistentFinancialAccountYear;
import org.ums.util.UmsUtils;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 28-Dec-17.
 */
public class MutableFinancialAccountyearResource {
  @Autowired
  protected FinancialAccountYearManager mFinancialAccountYearManager;
  @Autowired
  protected FinancialAccountYearResourceHelper mHelper;

  @Context
  protected ResourceContext mResourceContext;

  @POST
  @PostLog(message = "Requested for saving data")
  @Path("/save")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public List<FinancialAccountYear> saveAndReturnUpdatedList(@Context HttpServletRequest pHttpServletRequest,
      PersistentFinancialAccountYear pPersistentFinancialAccountYear) {
    MutableFinancialAccountYear year = pPersistentFinancialAccountYear;
    return mHelper.updateFinancialAccountYear(year);
  }

}
