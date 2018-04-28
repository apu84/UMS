package org.ums.accounts.resource.definitions.predefined.narration;

import org.apache.shiro.SecurityUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.domain.model.immutable.accounts.PredefinedNarration;
import org.ums.domain.model.mutable.accounts.MutablePredefinedNarration;
import org.ums.generator.IdGenerator;
import org.ums.manager.accounts.PredefinedNarrationManager;
import org.ums.persistent.model.accounts.PersistentPredefinedNarration;
import org.ums.resource.ResourceHelper;
import org.ums.usermanagement.user.User;
import org.ums.usermanagement.user.UserManager;

import javax.json.JsonArray;
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
public class PredefinedNarrationResourceHelper extends
    ResourceHelper<PredefinedNarration, MutablePredefinedNarration, Long> {
  @Autowired
  private PredefinedNarrationManager mPredefinedNarrationManager;
  @Autowired
  private UserManager mUserManager;
  @Autowired
  private IdGenerator mIdGenerator;
  @Autowired
  private PredefinedNarrationBuilder mPredefinedNarrationBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  public List<PredefinedNarration> createOrUpdate(JsonArray pJsonArray) {
    List<MutablePredefinedNarration> newList = new ArrayList<>();
    List<MutablePredefinedNarration> updatedList = new ArrayList<>();
    User user = mUserManager.get(SecurityUtils.getSubject().getPrincipal().toString());

    for(int i = 0; i < pJsonArray.size(); i++) {
      MutablePredefinedNarration predefinedNarration = new PersistentPredefinedNarration();
      mPredefinedNarrationBuilder.build(predefinedNarration, pJsonArray.getJsonObject(i));
      predefinedNarration.setModifiedBy(user.getEmployeeId());
      predefinedNarration.setModifiedDate(new Date());
      if(predefinedNarration.getId() == null) {
        predefinedNarration.setId(mIdGenerator.getNumericId());
        newList.add(predefinedNarration);
      }
      else {
        updatedList.add(predefinedNarration);
      }
    }

    if(newList.size() > 0)
      getContentManager().create(newList);
    if(updatedList.size() > 0)
      getContentManager().update(updatedList);

    return getContentManager().getAll();
  }

  @NotNull
  private List<MutablePredefinedNarration> getObjectFromJson(JsonArray pJsonArray) {
    List<MutablePredefinedNarration> pMutablePredefinedNarrations = new ArrayList<>();
    for(int i = 0; i < pJsonArray.size(); i++) {
      MutablePredefinedNarration narration = new PersistentPredefinedNarration();
      mPredefinedNarrationBuilder.build(narration, pJsonArray.getJsonObject(i));
      pMutablePredefinedNarrations.add(narration);
    }
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
