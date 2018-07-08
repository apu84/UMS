package org.ums.academic.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.AbsLateComingInfoBuilder;
import org.ums.builder.ApplicationCCIBuilder;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.AbsLateComingInfo;
import org.ums.domain.model.mutable.MutableAbsLateComingInfo;
import org.ums.manager.AbsLateComingInfoManager;
import org.ums.manager.ContentManager;
import org.ums.manager.SemesterManager;
import org.ums.manager.StudentManager;
import org.ums.persistent.dao.PersistentAbsLateComingInfo;
import org.ums.resource.ResourceHelper;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 7/1/2018.
 */
@Component
public class AbsLateComingInfoResourceHelper extends ResourceHelper<AbsLateComingInfo, MutableAbsLateComingInfo, Long> {
  @Autowired
  AbsLateComingInfoManager mAbsLateComingInfoManager;
  @Autowired
  StudentManager mStudentManager;
  @Autowired
  SemesterManager mSemesterManager;
  @Autowired
  AbsLateComingInfoBuilder mBuilder;
  @Autowired
  AbsLateComingInfoManager mManager;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    JsonArray entries = pJsonObject.getJsonArray("entries");
    LocalCache localCache = new LocalCache();
    JsonObject jsonObject = entries.getJsonObject(0);
    PersistentAbsLateComingInfo application = new PersistentAbsLateComingInfo();
    application.setSemesterId(11012017);
    getBuilder().build(application, jsonObject, localCache);
    try {
      mManager.create(application);
    } catch(Exception e) {
      e.printStackTrace();
    }
    URI contextURI = null;
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  @Override
  protected ContentManager<AbsLateComingInfo, MutableAbsLateComingInfo, Long> getContentManager() {
    return getContentManager();
  }

  @Override
  protected Builder<AbsLateComingInfo, MutableAbsLateComingInfo> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(AbsLateComingInfo pReadonly) {
    return null;
  }
}
