package org.ums.common.academic.resource.helper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.academic.builder.Builder;
import org.ums.academic.model.PersistentClassRoom;
import org.ums.academic.model.PersistentSemester;
import org.ums.cache.LocalCache;
import org.ums.common.academic.resource.ResourceHelper;
import org.ums.common.academic.resource.SemesterResource;
import org.ums.domain.model.jqGrid.JqGridData;
import org.ums.domain.model.mutable.MutableClassRoom;
import org.ums.domain.model.mutable.MutableSemester;
import org.ums.domain.model.readOnly.ClassRoom;
import org.ums.domain.model.readOnly.Semester;
import org.ums.manager.ContentManager;

import javax.json.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.StringReader;
import java.net.URI;
import java.util.List;

@Component
public class ClassRoomResourceHelper extends ResourceHelper<ClassRoom, MutableClassRoom, Integer> {

  @Autowired
  @Qualifier("classRoomManager")
  private ContentManager<ClassRoom, MutableClassRoom, Integer> mManager;

  @Autowired
  private List<Builder<ClassRoom, MutableClassRoom>> mBuilders;

  @Override
  public ContentManager<ClassRoom, MutableClassRoom, Integer> getContentManager() {
    return mManager;
  }

  @Override
  public List<Builder<ClassRoom, MutableClassRoom>> getBuilders() {
    return mBuilders;
  }


  protected JsonObject getAll(final UriInfo pUriInfo) throws Exception {
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
    for (Builder<ClassRoom, MutableClassRoom> builder : mBuilders) {
      builder.build(mutableClassRoom, pJsonObject, localCache);
    }
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
}
