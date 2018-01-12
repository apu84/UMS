package org.ums.accounts.resource.definitions.period.close;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.domain.model.mutable.accounts.MutablePeriodClose;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.accounts.PersistentPeriodClose;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 04-Jan-18.
 */
public class MutablePeriodCloseResource {
  @Autowired
  protected PeriodCloseReosurceHelper mHelper;
  @Autowired
  protected IdGenerator mIdGenerator;

  @POST
  @Path("/save")
  public List<MutablePeriodClose> saveAndReturnUpdatedList(List<MutablePeriodClose> pPersistentPeriodCloses, final @Context Request pRequest){
    return mHelper.saveAndReturnUpdatedList(pPersistentPeriodCloses);
  }
}
