package org.ums.accounts.resource.definitions.system.account.map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ums.domain.model.immutable.accounts.SystemAccountMap;
import org.ums.domain.model.mutable.accounts.MutableSystemAccountMap;
import org.ums.generator.IdGenerator;
import org.ums.manager.CompanyManager;
import org.ums.manager.accounts.SystemAccountMapManager;
import org.ums.resource.helper.UserResourceHelper;
import org.ums.util.Utils;

import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 04-Jun-18.
 */
@Component
public class SystemAccountMapResourceHelper {
  @Autowired
  private SystemAccountMapManager mSystemAccountMapManager;
  @Autowired
  private IdGenerator mIdGenerator;
  @Autowired
  private UserResourceHelper mUserResourceHelper;
  @Autowired
  private CompanyManager mCompanyManager;

  public List<SystemAccountMap> getAll() {
    try {
      return mSystemAccountMapManager.getAll(Utils.getCompany());
    } catch(EmptyResultDataAccessException e) {
      e.printStackTrace();
      return null;
    }
  }

  public SystemAccountMap getById(Long pId) {
    return mSystemAccountMapManager.get(pId);
  }

  @Transactional
  public Long create(MutableSystemAccountMap pMutableSystemAccountMap) {

    pMutableSystemAccountMap.setModifiedBy(mUserResourceHelper.getLoggedUser().getEmployeeId());
    pMutableSystemAccountMap.setCompanyId(Utils.getCompany().getId());
    if(pMutableSystemAccountMap.getId() == null) {
      pMutableSystemAccountMap.setId(mIdGenerator.getNumericId());
      mSystemAccountMapManager.create(pMutableSystemAccountMap);
    }
    else {
      mSystemAccountMapManager.update(pMutableSystemAccountMap);
    }
    return pMutableSystemAccountMap.getId();
  }

  @Transactional
  public Response delete(MutableSystemAccountMap pMutableSystemAccountMap) {
    pMutableSystemAccountMap.delete();
    URI contextURI = null;
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.ACCEPTED);
    return builder.build();
  }
}
