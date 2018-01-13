package org.ums.accounts.resource.definitions.period.close;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.domain.model.immutable.accounts.PeriodClose;
import org.ums.domain.model.mutable.accounts.MutablePeriodClose;
import org.ums.generator.IdGenerator;
import org.ums.manager.accounts.PeriodCloseManager;
import org.ums.resource.ResourceHelper;
import org.ums.usermanagement.user.User;
import org.ums.usermanagement.user.UserManager;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 04-Jan-18.
 */
@Component
public class PeriodCloseReosurceHelper extends ResourceHelper<PeriodClose, MutablePeriodClose, Long> {
  @Autowired
  private PeriodCloseManager mPeriodCloseManager;
  @Autowired
  private IdGenerator mIdGenerator;
  @Autowired
  private UserManager mUserManager;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  public List<MutablePeriodClose> saveAndReturnUpdatedList(final List<MutablePeriodClose> pMutablePeriodCloses){
    List<MutablePeriodClose> updatedList = new ArrayList<>();
    List<MutablePeriodClose> newList = new ArrayList<>();
    User user = mUserManager.get(SecurityUtils.getSubject().getPrincipal().toString());
    pMutablePeriodCloses.forEach(p->{
      p.setModifiedBy(user.getEmployeeId());
      p.setModifiedDate(new Date());
      if(p.getId()==null){
        p.setId(mIdGenerator.getNumericId());
        newList.add(p);
      }else{
        updatedList.add(p);
      }

    });
    if(updatedList.size()>0)
      getContentManager().update(updatedList);
    if(newList.size()>0)
      getContentManager().create(newList);

    return pMutablePeriodCloses;
  }

  @Override
  protected PeriodCloseManager getContentManager() {
    return mPeriodCloseManager;
  }

  @Override
  protected Builder<PeriodClose, MutablePeriodClose> getBuilder() {
    return null;
  }

  @Override
  protected String getETag(PeriodClose pReadonly) {
    return null;
  }
}