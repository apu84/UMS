package org.ums.common.academic.resource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.common.Resource;
import org.ums.common.builder.EnrollmentFromToBuilder;
import org.ums.domain.model.immutable.EnrollmentFromTo;
import org.ums.domain.model.immutable.SemesterEnrollment;
import org.ums.manager.EnrollmentFromToManager;
import org.ums.manager.SemesterEnrollmentManager;
import org.ums.manager.StudentRecordManager;
import org.ums.response.type.GenericResponse;
import org.ums.services.academic.EnrollmentService;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import java.text.DateFormat;
import java.util.List;
import java.util.Map;

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
  EnrollmentFromToBuilder mBuilder;
  @Autowired
  DateFormat mDateFormat;
  @GET
  @Path("/program/{program-id}/semester/{semester-id}")
  public JsonObject getSemesterList(final @Context Request pRequest,
                                    final @PathParam("program-id") int pProgramId,
                                    final @PathParam("semester-id") int pSemesterId) throws Exception {

    List<EnrollmentFromTo> enrollmentFromToList = mEnrollmentFromToManager.getEnrollmentFromTo(pProgramId);
    List<SemesterEnrollment> semesterEnrollmentList = mSemesterEnrollmentManager.getEnrollmentStatus(pProgramId, pSemesterId);

    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();

    for (EnrollmentFromTo enrollmentFromTo : enrollmentFromToList) {
      JsonObjectBuilder jsonObjectBuilder = toJson(enrollmentFromTo, mUriInfo, localCache);
      if (semesterEnrollmentList != null) {
        for (SemesterEnrollment semesterEnrollment : semesterEnrollmentList) {
          if (semesterEnrollment.getProgram().getId().intValue() == enrollmentFromTo.getProgram().getId().intValue()
              && semesterEnrollment.getYear().intValue() == enrollmentFromTo.getToYear().intValue()
              && semesterEnrollment.getAcademicSemester().intValue() == enrollmentFromTo.getToSemester().intValue()) {
            jsonObjectBuilder.add("type", semesterEnrollment.getType().getValue());
            jsonObjectBuilder.add("enrollmentDate", mDateFormat.format(semesterEnrollment.getEnrollmentDate()));
          }
        }
      }
      children.add(jsonObjectBuilder.build());
    }
    object.add("entries", children);
    localCache.invalidate();

    return object.build();
  }

  @POST
  @Path("/enroll/{enrollment-type}/program/{program-id}/semester/{semester-id}")
  public GenericResponse<Map> enrollSemester(final @Context Request pRequest,
                                 final @PathParam("enrollment-type") int pEnrollmentType,
                                 final @PathParam("program-id") int pProgramId,
                                 final @PathParam("semester-id") int pSemesterId) throws Exception {
    return mEnrollmentService.saveEnrollment(SemesterEnrollment.Type.get(pEnrollmentType), pSemesterId, pProgramId);
  }

  protected JsonObjectBuilder toJson(final EnrollmentFromTo pObject, final UriInfo pUriInfo, final LocalCache pLocalCache) throws Exception {
    JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
    mBuilder.build(jsonObjectBuilder, pObject, pUriInfo, pLocalCache);
    return jsonObjectBuilder;
  }

}
