package org.ums.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.domain.model.immutable.Semester;
import org.ums.enums.SemesterStatus;
import org.ums.manager.SemesterManager;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Path("/academic/semester")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class SemesterResource extends MutableSemesterResource {
  @Autowired
  SemesterManager mManager;

  @GET
  @Path("/all")
  public JsonObject getAll() throws Exception {
    return mResourceHelper.getAll(mUriInfo);
  }

  @GET
  @Path("/enrolledSemesters")
  public JsonObject getStudentRecord(final @Context Request pRequest) {
    return mResourceHelper.getStudentEnrolledSemesters(pRequest, mUriInfo);
  }

  @GET
  @Path("/program-type/{program-type}/limit/{list-limit}/status/{status}")
  public JsonObject getSemesterList(final @Context Request pRequest, final @PathParam("program-type") int pProgramType,
      final @PathParam("list-limit") int pListLimit, final @PathParam("status") int pSemesterStatus) {
    List<Semester> semesters = new ArrayList<>();
    semesters = fetchSemesters(pProgramType, pListLimit, pSemesterStatus);
    return mResourceHelper.buildSemesters(semesters, mUriInfo);
  }

  private List<Semester> fetchSemesters(@PathParam("program-type") int pProgramType, @PathParam("list-limit") int pListLimit, @PathParam("status") int pSemesterStatus) {
    List<Semester> semesters;
    if(SemesterStatus.FETCH_ALL_WITH_NEWLY_CREATED.getId()==pSemesterStatus){
      semesters = mManager.getSemesters(pProgramType, pListLimit);
    }
    else if(SemesterStatus.FETCH_ALL.getId()== pSemesterStatus){
      semesters = mManager.getSemesters(pProgramType, pListLimit).stream()
          .filter(s->s.getStatus().getValue()!=SemesterStatus.NEWLY_CREATED.getId())
          .collect(Collectors.toList());
    }else {
      semesters = mManager.getSemesters(pProgramType, pListLimit).stream()
          .filter(s->s.getStatus().getValue()==SemesterStatus.NEWLY_CREATED.getId())
          .collect(Collectors.toList());
    }
    return semesters;
  }

  @GET
  @Path(PATH_PARAM_OBJECT_ID)
  public Response get(final @Context Request pRequest, final @PathParam("object-id") int pObjectId) throws Exception {
    return mResourceHelper.get(pObjectId, pRequest, mUriInfo);
  }

}
