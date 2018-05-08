package org.ums.accounts.resource.definitions.system.group.map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.immutable.accounts.SystemGroupMap;
import org.ums.domain.model.mutable.accounts.MutableSystemGroupMap;
import org.ums.generator.IdGenerator;
import org.ums.manager.CompanyManager;
import org.ums.manager.accounts.SystemGroupMapManager;
import org.ums.resource.ResourceHelper;
import org.ums.resource.helper.UserResourceHelper;
import org.ums.usermanagement.user.UserManager;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Date;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 28-Apr-18.
 */
@Component
public class SystemGroupMapResourceHelper extends ResourceHelper<SystemGroupMap, MutableSystemGroupMap, String> {

  Logger mLogger = LoggerFactory.getLogger(SystemGroupMapResourceHelper.class);

  @Autowired
  private SystemGroupMapManager mSystemGroupMapManager;
  @Autowired
  private CompanyManager mCompanyManager;
  @Autowired
  private UserManager mUserManager;
  @Autowired
  private UserResourceHelper mUserResourceHelper;
  @Autowired
  private IdGenerator mIdGenerator;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  public List<SystemGroupMap> getAllSystemGroupMapByCompany() {
    // todo get user's company
    Company company = mCompanyManager.getDefaultCompany();
    List<SystemGroupMap> systemGroupMapList = mSystemGroupMapManager.getAllByCompany(company);
    return systemGroupMapList;
  }

  public MutableSystemGroupMap save(MutableSystemGroupMap pMutableSystemGroupMap) throws Exception {
    if(pMutableSystemGroupMap.getGroupType() == null || pMutableSystemGroupMap.getGroup() == null)
      throw new Exception("All fields are not properly filled");
    pMutableSystemGroupMap.setId(mIdGenerator.getAlphaNumericId());
    pMutableSystemGroupMap.setCompanyId(mCompanyManager.getDefaultCompany().getId());
    pMutableSystemGroupMap.setModifiedBy(mUserResourceHelper.getLoggedUser().getEmployeeId());
    pMutableSystemGroupMap.setModifiedDate(new Date());
    mSystemGroupMapManager.create(pMutableSystemGroupMap);
    return pMutableSystemGroupMap;
  }

  public SystemGroupMap get(String pId) {
    return mSystemGroupMapManager.get(pId);
  }

  public void update(MutableSystemGroupMap pMutableSystemGroupMap) {
    pMutableSystemGroupMap.setModifiedBy(mUserResourceHelper.getLoggedUser().getEmployeeId());
    pMutableSystemGroupMap.setModifiedDate(new Date());
    mSystemGroupMapManager.update(pMutableSystemGroupMap);
  }

  @Override
  protected SystemGroupMapManager getContentManager() {
    return mSystemGroupMapManager;
  }

  @Override
  protected Builder<SystemGroupMap, MutableSystemGroupMap> getBuilder() {
    return null;
  }

  @Override
  protected String getETag(SystemGroupMap pReadonly) {
    return pReadonly.getLastModified();
  }
}
