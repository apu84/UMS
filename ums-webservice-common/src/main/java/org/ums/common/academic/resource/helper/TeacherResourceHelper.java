package org.ums.common.academic.resource.helper;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.common.ResourceHelper;
import org.ums.common.builder.TeacherBuilder;
import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.immutable.Teacher;
import org.ums.domain.model.mutable.MutableTeacher;
import org.ums.manager.DepartmentManager;
import org.ums.manager.TeacherManager;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Component
public class TeacherResourceHelper extends ResourceHelper<Teacher, MutableTeacher, String> {
  @Autowired
  TeacherManager mManager;

  @Autowired
  DepartmentManager mDepartmentManager;

  @Autowired
  private TeacherBuilder mBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    throw new NotImplementedException("Post method not implemented for TeacherResourceHelper");
  }

  @Override
  protected TeacherManager getContentManager() {
    return mManager;
  }

  @Override
  protected TeacherBuilder getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getEtag(Teacher pReadonly) {
    return pReadonly.getLastModified();
  }

  public JsonObject getByDepartment(final String pDepartmentId, final UriInfo pUriInfo) throws Exception {
    Department department = mDepartmentManager.get(pDepartmentId);
    List<Teacher> teachers = getContentManager().getByDepartment(department);

    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for (Teacher teacher : teachers) {
      children.add(toJson(teacher, pUriInfo, localCache));
    }
    object.add("entries", children);
    localCache.invalidate();

    return object.build();
  }
}
