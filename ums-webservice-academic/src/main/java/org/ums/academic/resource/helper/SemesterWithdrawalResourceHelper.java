package org.ums.academic.resource.helper;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.academic.resource.SemesterWithdrawalResource;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.builder.SemesterWithdrawalBuilder;
import org.ums.domain.model.immutable.SemesterWithdrawal;
import org.ums.domain.model.mutable.MutableSemesterWithdrawal;
import org.ums.manager.SemesterWithDrawalManager;
import org.ums.persistent.model.PersistentSemesterWithdrawal;
import org.ums.resource.ResourceHelper;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

@Component
public class SemesterWithdrawalResourceHelper extends
    ResourceHelper<SemesterWithdrawal, MutableSemesterWithdrawal, Long> {

  @Autowired
  SemesterWithDrawalManager mManager;

  @Autowired
  SemesterWithdrawalBuilder mBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) {
    MutableSemesterWithdrawal mutableSemesterWithdrawal = new PersistentSemesterWithdrawal();
    LocalCache localCache = new LocalCache();
    getBuilder().build(mutableSemesterWithdrawal, pJsonObject, localCache);
    mutableSemesterWithdrawal.commit(false);
    URI contextURI =
        pUriInfo.getBaseUriBuilder().path(SemesterWithdrawalResource.class)
            .path(SemesterWithdrawalResource.class, "get").build("0");
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  public JsonObject getStudentRecord(final int semesterId, final int year, final int academicSemester,
      Request pRequest, final UriInfo pUriInfo) {
    String mStudentId = SecurityUtils.getSubject().getPrincipal().toString();
    SemesterWithdrawal semesterWithdrawal =
        getContentManager().getStudentsRecord(mStudentId, semesterId, year, academicSemester);
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();

    children.add(toJson(semesterWithdrawal, pUriInfo, localCache));
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  public JsonObject getRoutineByDeptForEmployee(final String deptId, final Request pRequest, final UriInfo pUriInfo) {
    List<SemesterWithdrawal> semesterWithdrawals = getContentManager().getByDeptForEmployee(deptId);
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for(SemesterWithdrawal semesterWithdrawal : semesterWithdrawals) {
      children.add(toJson(semesterWithdrawal, pUriInfo, localCache));
    }
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
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
  public String getETag(SemesterWithdrawal pReadonly) {
    return pReadonly.getLastModified();
  }
}
