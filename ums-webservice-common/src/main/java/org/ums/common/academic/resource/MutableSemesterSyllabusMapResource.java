package org.ums.common.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.common.Resource;
import org.ums.common.academic.resource.helper.SemesterSyllabusMapResourceHelper;
import org.ums.common.academic.resource.helper.StudentResourceHelper;
import org.ums.common.academic.resource.helper.SyllabusResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

/**
 * Created by Ifti on 08-Jan-16.
 */
public class MutableSemesterSyllabusMapResource extends Resource {

  @Autowired
  SemesterSyllabusMapResourceHelper  mResourceHelper;

}
