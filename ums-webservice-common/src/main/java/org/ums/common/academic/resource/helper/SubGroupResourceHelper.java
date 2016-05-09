package org.ums.common.academic.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.common.academic.resource.ResourceHelper;
import org.ums.common.academic.resource.SubGroupResource;
import org.ums.common.builder.Builder;
import org.ums.common.builder.SubGroupBuilder;
import org.ums.domain.model.immutable.SubGroup;
import org.ums.domain.model.mutable.MutableSubGroup;
import org.ums.manager.SubGroupManager;
import org.ums.persistent.model.PersistentSubGroup;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

/**
 * Created by My Pc on 5/5/2016.
 */

@Component
public class SubGroupResourceHelper extends ResourceHelper<SubGroup,MutableSubGroup,Integer>{

  @Autowired
  private SubGroupManager mManager;

  @Autowired
  private SubGroupBuilder mBuilder;


  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    MutableSubGroup mutableSubGroup = new PersistentSubGroup();
    LocalCache localCache = new LocalCache();
    getBuilder().build(mutableSubGroup,pJsonObject,localCache);
    mutableSubGroup.commit(false);
    URI contextURI = pUriInfo.getBaseUriBuilder().path(SubGroupResource.class).path(SubGroupResource.class,"get").build(mutableSubGroup.getId());
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }


  public JsonObject getByGroupNo(final int groupNo, final Request pRequest,final UriInfo pUriInfo) throws Exception{
    List<SubGroup> subGroups = getContentManager().getByGroupNo(groupNo);
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for(SubGroup subGroup: subGroups){
      children.add(toJson(subGroup,pUriInfo,localCache));
    }
    object.add("entries",children);
    localCache.invalidate();
    return object.build();
  }

  public JsonObject getBySemesterAndGroupNO(final int pSemesterId,final int pGroupNo,final int pType,final Request pRequest,final UriInfo pUriInfo) throws Exception{
    List<SubGroup> subGroups = getContentManager().getBySemesterGroupNoAndType(pSemesterId,pGroupNo, pType);
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for(SubGroup subGroup: subGroups){
      children.add(toJson(subGroup,pUriInfo,localCache));
    }
    object.add("entries",children);
    localCache.invalidate();
    return object.build();
  }

  public Response deleteBySemesterAndGroup(final int pSemesterId,final int pGroupNo)throws Exception{
    int delete = getContentManager().deleteBySemesterAndGroup(pSemesterId,pGroupNo);
    return Response.noContent().build();
  }


  @Override
  protected SubGroupManager getContentManager() {
    return mManager;
  }

  @Override
  protected Builder<SubGroup, MutableSubGroup> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getEtag(SubGroup pReadonly) {
    return pReadonly.getLastModified();
  }
}
