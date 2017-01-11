package org.ums.academic.resource.helper;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.academic.resource.SemesterResource;
import org.ums.builder.ClassRoomBuilder;
import org.ums.domain.model.immutable.*;
import org.ums.manager.*;
import org.ums.persistent.model.PersistentClassRoom;
import org.ums.cache.LocalCache;
import org.ums.resource.ResourceHelper;
import org.ums.domain.model.mutable.MutableClassRoom;

import javax.json.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.StringReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClassRoomResourceHelper extends ResourceHelper<ClassRoom, MutableClassRoom, Integer> {

  @Autowired
  private ClassRoomManager mManager;

  @Autowired
  private UserManager mUserManager;

  @Autowired
  private ClassRoomBuilder mBuilder;

  @Autowired
  private EmployeeManager mEmployeeManager;

  @Autowired
  private SemesterManager mSemesterManager;

  @Autowired
  private StudentManager mStudentManager;

  @Autowired
  private ProgramManager mProgramManager;

  @Override
  public ClassRoomManager getContentManager() {
    return mManager;
  }

  @Override
  public ClassRoomBuilder getBuilder() {
    return mBuilder;
  }

  public JsonObject getAll(final UriInfo pUriInfo) {
    List<ClassRoom> roomList = getContentManager().getAll();

    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for(ClassRoom room : roomList) {
      children.add(toJson(room, pUriInfo, localCache));
    }
    object.add("rows", children);
    object.add("page", 1);
    object.add("total", roomList.size());
    object.add("records", roomList.size());

    localCache.invalidate();
    return object.build();

  }

  public JsonObject getRooms(UriInfo pUriInfo) {

    List<ClassRoom> roomList = getContentManager().getAll();

    return buildClassRooms(roomList, pUriInfo);
  }

  private List<ClassRoom> getRoomsByDepartment(){
    String userId = SecurityUtils.getSubject().getPrincipal().toString();

    User user = mUserManager.get(userId);
    String employeeId = user.getEmployeeId();
    Employee employee = mEmployeeManager.get(employeeId);
    String deptId = employee.getDepartment().getId();
    List<Program> programs = mProgramManager
        .getAll()
        .stream()
        .filter(pProgram -> pProgram.getDepartmentId().equals(deptId))
        .collect(Collectors.toList());
    List<ClassRoom> roomList = getContentManager()
        .getAll()
        .stream()
        .filter(room-> room.getDeptId().equals(deptId))
        .sorted(Comparator.comparing(ClassRoom::getId))
        .collect(Collectors.toList());

    return roomList;
  }

  private static JsonObject jsonFromString(String jsonObjectStr) {

    JsonReader jsonReader = Json.createReader(new StringReader(jsonObjectStr));
    JsonObject object = jsonReader.readObject();
    jsonReader.close();

    return object;
  }

  @Override
  public Response post(final JsonObject pJsonObject, final UriInfo pUriInfo) {
    MutableClassRoom mutableClassRoom = new PersistentClassRoom();
    LocalCache localCache = new LocalCache();
    getBuilder().build(mutableClassRoom, pJsonObject, localCache);
    mutableClassRoom.commit(false);

    URI contextURI =
        pUriInfo.getBaseUriBuilder().path(SemesterResource.class)
            .path(SemesterResource.class, "get").build(mutableClassRoom.getId());
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);

    return builder.build();
  }

  @Override
  protected String getEtag(ClassRoom pReadonly) {
    return "";
  }

  public JsonObject buildClassRooms(final List<ClassRoom> pClassRooms, final UriInfo pUriInfo) {
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for(ClassRoom readOnly : pClassRooms) {
      children.add(toJson(readOnly, pUriInfo, localCache));
    }
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  public JsonObject getRoomsBasedOnRoutine(int pSemesterId ,UriInfo pUriInfo)
      {

    List<ClassRoom> roomList = new ArrayList<>();
    String userId = SecurityUtils.getSubject().getPrincipal().toString();

    User user = mUserManager.get(userId);

    if(user.getEmployeeId().equals("")){
      Student student = mStudentManager.get(user.getId());
      roomList = getContentManager().getRoomsBasedOnRoutine(pSemesterId,student.getProgram().getId());
    }
    else{
      Employee employee = mEmployeeManager.get(user.getEmployeeId());
      String deptId = employee.getDepartment().getId();
      List<Program> programs = mProgramManager.getAll().stream().filter(p-> p.getDepartmentId().equals(deptId)).collect(Collectors.toList());
      roomList = getContentManager().getRoomsBasedOnRoutine(pSemesterId,programs.get(0).getId());
    }

    return buildClassRooms(roomList, pUriInfo);
  }

  public JsonObject getByRoomNo(final String pRoomNo, final UriInfo pUriInfo) {
    ClassRoom room = getContentManager().getByRoomNo(pRoomNo);
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    children.add(toJson(room, pUriInfo, localCache));

    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  public JsonObject getByRoomId(final Integer pRoomId, final UriInfo pUriInfo) {
    ClassRoom room = getContentManager().get(pRoomId);
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    children.add(toJson(room, pUriInfo, localCache));

    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }
}
