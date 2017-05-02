package org.ums.academic.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.builder.DepartmentSelectionDeadlineBuilder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.DepartmentSelectionDeadline;
import org.ums.domain.model.mutable.MutableDepartmentSelectionDeadline;
import org.ums.manager.ContentManager;
import org.ums.manager.DepartmentSelectionDeadlineManager;
import org.ums.persistent.model.PersistentDepartmentSelectionDeadline;
import org.ums.resource.ResourceHelper;

import javax.json.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 27-Apr-17.
 */
@Component
public class DepartmentSelectionDeadlineResourceHelper extends
    ResourceHelper<DepartmentSelectionDeadline, MutableDepartmentSelectionDeadline, Integer> {

  @Autowired
  private DepartmentSelectionDeadlineManager mDepartmentSelectionDeadlineManager;

  @Autowired
  private DepartmentSelectionDeadlineBuilder mDepartmentSelectionDeadlineBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    List<MutableDepartmentSelectionDeadline> newDeadlines = new ArrayList<>();
    List<MutableDepartmentSelectionDeadline> existingDeadlines = new ArrayList<>();
    JsonArray entries = pJsonObject.getJsonArray("entries");
    LocalCache localCache = new LocalCache();
    for(int i = 0; i < entries.size(); i++) {
      PersistentDepartmentSelectionDeadline deadline = new PersistentDepartmentSelectionDeadline();
      getBuilder().build(deadline, entries.getJsonObject(i), localCache);
      if(deadline.getId() != 0)
        existingDeadlines.add(deadline);
      else
        newDeadlines.add(deadline);
    }

    if(existingDeadlines.size() > 0)
      getContentManager().update(existingDeadlines);
    if(newDeadlines.size() > 0)
      getContentManager().create(newDeadlines);
    URI contextURI = null;
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  @Override
  public Response delete(Integer pObjectId) throws Exception {
    return super.delete(pObjectId);
  }

  public JsonObject getDeadline(final int pSemesterId, final String pQuota, final String pUnit, final UriInfo pUriInfo) {
    List<DepartmentSelectionDeadline> deadlines = new ArrayList<>();
    try {
      deadlines = getContentManager().getDeadline(pSemesterId, pUnit, pQuota);
    } catch(Exception e) {
      // continue
    }

    return jsonCreator(pUriInfo, deadlines);
  }

  private JsonObject jsonCreator(UriInfo pUriInfo, List<DepartmentSelectionDeadline> pDeadlines) {
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for(DepartmentSelectionDeadline deadline : pDeadlines) {
      JsonObjectBuilder jsonObject = Json.createObjectBuilder();
      getBuilder().build(jsonObject, deadline, pUriInfo, localCache);
      children.add(jsonObject);
    }
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  @Override
  protected DepartmentSelectionDeadlineManager getContentManager() {
    return mDepartmentSelectionDeadlineManager;
  }

  @Override
  protected Builder<DepartmentSelectionDeadline, MutableDepartmentSelectionDeadline> getBuilder() {
    return mDepartmentSelectionDeadlineBuilder;
  }

  @Override
  protected String getETag(DepartmentSelectionDeadline pReadonly) {
    return pReadonly.getLastModified();
  }
}
