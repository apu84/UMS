package org.ums.common.academic.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.common.academic.resource.SemesterResource;
import org.ums.common.builder.ClassRoomBuilder;
import org.ums.domain.model.immutable.ClassRoom;
import org.ums.domain.model.mutable.MutableClassRoom;
import org.ums.manager.ClassRoomManager;
import org.ums.persistent.model.PersistentClassRoom;
import org.ums.resource.ResourceHelper;

import javax.json.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.StringReader;
import java.net.URI;
import java.util.List;

@Component
public class ClassRoomResourceHelper extends ResourceHelper<ClassRoom, MutableClassRoom, Integer> {

  @Autowired
  private ClassRoomManager mManager;

  @Autowired
  private ClassRoomBuilder mBuilder;

  @Override
  public ClassRoomManager getContentManager() {
    return mManager;
  }

  @Override
  public ClassRoomBuilder getBuilder() {
    return mBuilder;
  }


  public JsonObject getAll(final UriInfo pUriInfo) throws Exception {
    List<ClassRoom> roomList = getContentManager().getAll();

    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for (ClassRoom room : roomList) {
      children.add(toJson(room, pUriInfo, localCache));
    }
    object.add("rows", children);
    object.add("page", 1);
    object.add("total", roomList.size());
    object.add("records", roomList.size());


    localCache.invalidate();
    return object.build();


  }

  private static JsonObject jsonFromString(String jsonObjectStr) {

    JsonReader jsonReader = Json.createReader(new StringReader(jsonObjectStr));
    JsonObject object = jsonReader.readObject();
    jsonReader.close();

    return object;
  }

  @Override
  public Response post(final JsonObject pJsonObject, final UriInfo pUriInfo) throws Exception {
    MutableClassRoom mutableClassRoom = new PersistentClassRoom();
    LocalCache localCache = new LocalCache();
    getBuilder().build(mutableClassRoom, pJsonObject, localCache);
    mutableClassRoom.commit(false);

    URI contextURI = pUriInfo.getBaseUriBuilder().path(SemesterResource.class).path(SemesterResource.class, "get").build(mutableClassRoom.getId());
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);

    return builder.build();
  }

  @Override
  protected String getEtag(ClassRoom pReadonly) {
    return "";
  }

  public JsonObject buildClassRooms(final List<ClassRoom> pClassRooms, final UriInfo pUriInfo) throws Exception {
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for (ClassRoom readOnly : pClassRooms) {
      children.add(toJson(readOnly, pUriInfo, localCache));
    }
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  public JsonObject getByRoomNo(final String pRoomNo,final UriInfo pUriInfo) throws Exception{
    ClassRoom room = getContentManager().getByRoomNo(pRoomNo);
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    children.add(toJson(room,pUriInfo,localCache));

    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }


  public JsonObject getByRoomId(final Integer pRoomId,final UriInfo pUriInfo) throws Exception{
    ClassRoom room = getContentManager().get(pRoomId);
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    children.add(toJson(room,pUriInfo,localCache));

    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }
}
