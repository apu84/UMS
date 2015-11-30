package org.ums.dummy.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.dummy.shared.dao.UserDao;
import org.ums.dummy.shared.model.User;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Component
@Path("/dummyUser")
@Produces("application/json")
public class DummyUserWebservice {
  @Autowired
  UserDao userDao;

  @GET
  @Path("/all")
  public JsonObject getAll() {
    List<User> users = userDao.getAll();
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    for (User user : users) {
      children.add(toJson(user));
    }
    object.add("users", children);
    return object.build();
  }

  @GET
  @Path("/{id}")
  public JsonObject getById(final @PathParam("id") int id) {
    User user = userDao.get(id);
    return toJson(user);
  }

  protected JsonObject toJson(User pUser) {
    JsonObjectBuilder builder = Json.createObjectBuilder();
    builder.add("userId", pUser.getUserId());
    builder.add("firstName", pUser.getFirstName());
    builder.add("lastName", pUser.getLastName());
    builder.add("gender", pUser.getGender());
    builder.add("employmentStatus", pUser.getEmploymentStatus());
    return builder.build();
  }
}
