package org.ums.common.academic.resource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.academic.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.common.Resource;
import org.ums.domain.model.mutable.MutableEnrollmentFromTo;
import org.ums.domain.model.readOnly.EnrollmentFromTo;
import org.ums.domain.model.readOnly.SemesterEnrollment;
import org.ums.manager.EnrollmentFromToManager;
import org.ums.manager.SemesterEnrollmentManager;
import org.ums.manager.StudentRecordManager;
import org.ums.services.academic.EnrollmentService;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Component
@Path("/academic/studentEnrollment")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class StudentEnrollmentResource extends Resource {
  @Autowired
  EnrollmentFromToManager mEnrollmentFromToManager;
  @Autowired
  SemesterEnrollmentManager mSemesterEnrollmentManager;
  @Autowired
  StudentRecordManager mStudentRecordManager;
  @Autowired
  EnrollmentService mEnrollmentService;
  @Autowired
  @Qualifier("getEnrollmentFromToBuilder")
  Builder<EnrollmentFromTo, MutableEnrollmentFromTo> mBuilder;


  @GET
  @Path("enrollment-type/{enrollment-type}/program-id/{program-id}/semester-id/{semester-id}")
  public JsonObject getSemesterList(final @Context Request pRequest,
                                    final @PathParam("enrollment-type") int pEnrollmentType,
                                    final @PathParam("program-id") int pProgramId,
                                    final @PathParam("semester-id") int pSemesterId) throws Exception {

    List<EnrollmentFromTo> enrollmentFromToList = mEnrollmentFromToManager.getEnrollmentFromTo(pProgramId);
    List<SemesterEnrollment> semesterEnrollmentList
        = mSemesterEnrollmentManager.getEnrollmentStatus(SemesterEnrollment.Type.get(pEnrollmentType), pProgramId, pSemesterId);

    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();

    for (EnrollmentFromTo enrollmentFromTo : enrollmentFromToList) {
      JsonObjectBuilder jsonObjectBuilder = toJson(enrollmentFromTo, mUriInfo, localCache);
      if (semesterEnrollmentList != null) {
        for (SemesterEnrollment semesterEnrollment : semesterEnrollmentList) {
          if (semesterEnrollment.getProgram().getId().intValue() == enrollmentFromTo.getProgram().getId().intValue()) {
            jsonObjectBuilder.add("type", semesterEnrollment.getType().getValue());
          }
        }
      }
      children.add(jsonObjectBuilder.build());
    }
    object.add("entries", children);
    localCache.invalidate();

    return object.build();
  }


  protected JsonObjectBuilder toJson(final EnrollmentFromTo pObject, final UriInfo pUriInfo, final LocalCache pLocalCache) throws Exception {
    JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
    mBuilder.build(jsonObjectBuilder, pObject, pUriInfo, pLocalCache);
    return jsonObjectBuilder;
  }

}
