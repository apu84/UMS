package org.ums.common.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.common.Resource;
import org.ums.manager.ClassRoomManager;

import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Created by Ifti on 26-Feb-16.
 */
@Component
@Path("/academic/examroutine")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class ExamRoutineResource  extends MutableExamRoutineResource {
  @Autowired
  @Qualifier("classRoomManager")
  ClassRoomManager mManager;


}
