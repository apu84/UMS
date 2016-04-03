package org.ums.common.academic.resource.helper;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.common.academic.resource.MutableSemesterWithdrawalResource;
import org.ums.common.academic.resource.ResourceHelper;
import org.ums.common.academic.resource.RoutineResource;
import org.ums.common.academic.resource.SemesterWithdrawalResource;
import org.ums.common.builder.Builder;
import org.ums.common.builder.SemesterWithdrawalBuilder;
import org.ums.domain.model.immutable.SemesterWithdrawal;
import org.ums.domain.model.mutable.MutableRoutine;
import org.ums.domain.model.mutable.MutableSemesterWithdrawal;
import org.ums.manager.ContentManager;
import org.ums.manager.SemesterWithDrawalManager;
import org.ums.persistent.model.PersistentRoutine;
import org.ums.persistent.model.PersistentSemesterWithdrawal;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

@Component
public class SemesterWithdrawalResourceHelper extends ResourceHelper<SemesterWithdrawal,MutableSemesterWithdrawal,Integer> {

  @Autowired
  SemesterWithDrawalManager mManager;

  @Autowired
  SemesterWithdrawalBuilder mBuilder;




  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    MutableSemesterWithdrawal mutableSemesterWithdrawal = new PersistentSemesterWithdrawal();
    LocalCache localCache = new LocalCache();
    getBuilder().build(mutableSemesterWithdrawal, pJsonObject, localCache);
    mutableSemesterWithdrawal.commit(false);
    URI contextURI = pUriInfo.getBaseUriBuilder().path(SemesterWithdrawalResource.class).path(MutableSemesterWithdrawalResource.class, "get").build(mutableSemesterWithdrawal.getId());
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  @Override
  public SemesterWithDrawalManager getContentManager() {
    return mManager;
  }

  @Override
  public Builder<SemesterWithdrawal, MutableSemesterWithdrawal> getBuilder() {
    return mBuilder;
  }

  @Override
  public  String getEtag(SemesterWithdrawal pReadonly) {
    return pReadonly.getLastModified();
  }
}
