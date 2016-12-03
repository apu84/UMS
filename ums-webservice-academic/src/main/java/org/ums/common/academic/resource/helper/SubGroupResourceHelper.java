package org.ums.common.academic.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.common.academic.resource.SubGroupResource;
import org.ums.common.builder.SubGroupBuilder;
import org.ums.domain.model.immutable.SubGroup;
import org.ums.domain.model.mutable.MutableSubGroup;
import org.ums.manager.SeatPlanManager;
import org.ums.manager.SubGroupManager;
import org.ums.persistent.model.PersistentSeatPlanGroup;
import org.ums.persistent.model.PersistentSemester;
import org.ums.persistent.model.PersistentSubGroup;
import org.ums.resource.ResourceHelper;

import javax.json.*;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by My Pc on 5/5/2016.
 */

@Component
public class SubGroupResourceHelper extends ResourceHelper<SubGroup, MutableSubGroup, Integer> {

  @Autowired
  private SubGroupManager mManager;

  @Autowired
  private SeatPlanManager mSeatPlanManager;

  @Autowired
  private SubGroupBuilder mBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) {
    MutableSubGroup mutableSubGroup = new PersistentSubGroup();
    LocalCache localCache = new LocalCache();
    getBuilder().build(mutableSubGroup, pJsonObject, localCache);
    mutableSubGroup.commit(false);
    URI contextURI =
        pUriInfo.getBaseUriBuilder().path(SubGroupResource.class)
            .path(SubGroupResource.class, "get").build(mutableSubGroup.getId());
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  public JsonObject getByGroupNo(final int groupNo, final Request pRequest, final UriInfo pUriInfo) {
    List<SubGroup> subGroups = getContentManager().getByGroupNo(groupNo);
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for(SubGroup subGroup : subGroups) {
      children.add(toJson(subGroup, pUriInfo, localCache));
    }
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  public JsonObject getBySemesterAndGroupNO(final int pSemesterId, final int pGroupNo,
      final int pType, final Request pRequest, final UriInfo pUriInfo) {
    List<SubGroup> subGroups =
        getContentManager().getBySemesterGroupNoAndType(pSemesterId, pGroupNo, pType);
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for(SubGroup subGroup : subGroups) {
      children.add(toJson(subGroup, pUriInfo, localCache));
    }
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  public Response deleteBySemesterAndGroup(final int pSemesterId, final int pGroupNo,
      final int pType) {
    int delete = getContentManager().deleteBySemesterGroupAndType(pSemesterId, pGroupNo, pType);
    return Response.noContent().build();
  }

  public Response save(final int pSemesterId, final int pGroupNo, final int pType,
      final JsonObject pJsonObject) {

    int checkIfSubGroupExist = mManager.checkBySemesterGroupNoAndType(pSemesterId, pGroupNo, pType);

    if(checkIfSubGroupExist > 0) {
      mManager.deleteBySemesterGroupAndType(pSemesterId, pGroupNo, pType);
      mSeatPlanManager.deleteBySemesterGroupExamType(pSemesterId, pGroupNo, pType);
    }

    List<MutableSubGroup> subGroups = new ArrayList<>();

    JsonArray entries = pJsonObject.getJsonArray("entries");
    for(int i = 0; i < entries.size(); i++) {
      JsonObject jsonObject = entries.getJsonObject(i);
      // if(jsonObject.getInt("position")!=1){
      PersistentSubGroup subGroup = new PersistentSubGroup();
      PersistentSemester semester = new PersistentSemester();
      semester.setId(pSemesterId);
      subGroup.setSemester(semester);
      subGroup.setGroupNo(pGroupNo);
      subGroup.setExamType(pType);
      subGroup.setSubGroupNo(jsonObject.getInt("subGroupNo"));
      subGroup.setPosition(jsonObject.getInt("position"));
      PersistentSeatPlanGroup group = new PersistentSeatPlanGroup();
      group.setId(jsonObject.getInt("groupId"));
      group.setGroupNo(pGroupNo);
      subGroup.setGroup(group);
      subGroup.setGroupId(jsonObject.getInt("groupId"));
      subGroup.setStudentNumber(jsonObject.getInt("studentNumber"));
      subGroups.add(subGroup);
      // }

    }

    mManager.create(subGroups);

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
