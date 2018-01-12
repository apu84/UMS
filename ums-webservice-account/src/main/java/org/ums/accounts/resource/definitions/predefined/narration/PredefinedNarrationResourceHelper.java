package org.ums.accounts.resource.definitions.predefined.narration;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.domain.model.immutable.accounts.PredefinedNarration;
import org.ums.domain.model.mutable.accounts.MutablePredefinedNarration;
import org.ums.generator.IdGenerator;
import org.ums.manager.ContentManager;
import org.ums.manager.accounts.PredefinedNarrationManager;
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
 * Created by Monjur-E-Morshed on 13-Jan-18.
 */
@Component
public class PredefinedNarrationResourceHelper extends ResourceHelper<PredefinedNarration, MutablePredefinedNarration, Long> {
  @Autowired
  private PredefinedNarrationManager mPredefinedNarrationManager;
  @Autowired
  private UserManager mUserManager;
  @Autowired
  private IdGenerator mIdGenerator;
  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  public List<MutablePredefinedNarration> createOrUpdate(List<MutablePredefinedNarration> pMutablePredefinedNarrations){
    List<MutablePredefinedNarration> newList = new ArrayList<>();
    List<MutablePredefinedNarration> updatedList = new ArrayList<>();
    User user = mUserManager.get(SecurityUtils.getSubject().getPrincipal().toString());
    pMutablePredefinedNarrations.forEach(n->{
      n.setModifiedBy(user.getEmployeeId());
      n.setModifiedDate(new Date());
      if(n.getId()==null){
        n.setId(mIdGenerator.getNumericId());
        newList.add(n);
      }else{
        updatedList.add(n);
      }
    });
    if(newList.size()>0)
      getContentManager().create(newList);
    if(updatedList.size()>0)
      getContentManager().update(updatedList);

    return pMutablePredefinedNarrations;
  }

  @Override
  protected PredefinedNarrationManager getContentManager() {
    return mPredefinedNarrationManager;
  }

  @Override
  protected Builder<PredefinedNarration, MutablePredefinedNarration> getBuilder() {
    return null;
  }

  @Override
  protected String getETag(PredefinedNarration pReadonly) {
    return pReadonly.getModifiedDate().toString();
  }
}
