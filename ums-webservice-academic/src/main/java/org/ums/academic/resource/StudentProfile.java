package org.ums.academic.resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.ums.academic.resource.helper.StudentProfileHelper;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

@Component
@Path("/academic/student/profile")
public class StudentProfile extends Resource {
  @Autowired
  @Qualifier("StudentProfileHelper")
  StudentProfileHelper mResourceHelper;

  @PUT
  public Response updateProfile(final @Context Request pRequest,
      final @HeaderParam(HEADER_IF_MATCH) String pIfMatchHeader, final JsonObject pJsonObject)
      throws Exception {
    String studentId = "";
    Subject subject = SecurityUtils.getSubject();
    if(subject != null) {
      studentId = subject.getPrincipal().toString();
    }
    if(StringUtils.isEmpty(studentId)) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }
    return mResourceHelper.put(studentId, pRequest, pIfMatchHeader, pJsonObject);
  }

  @GET
  public Response getProfile(final @Context Request pRequest) throws Exception {
    String studentId = "";
    Subject subject = SecurityUtils.getSubject();
    if(subject != null) {
      studentId = subject.getPrincipal().toString();
    }
    if(StringUtils.isEmpty(studentId)) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }
    return mResourceHelper.get(studentId, pRequest, mUriInfo);
  }
}
