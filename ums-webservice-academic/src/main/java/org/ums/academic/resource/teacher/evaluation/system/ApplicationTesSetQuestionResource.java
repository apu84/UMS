package org.ums.academic.resource.teacher.evaluation.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.manager.ApplicationTesSetQuestionManager;
import org.ums.resource.Resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Created by Monjur-E-Morshed on 4/26/2018.
 */
@Component
@Path("academic/applicationTesSetQuestions")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class ApplicationTesSetQuestionResource extends MutableApplicationTesSetQuestionResource {
  @Autowired
  ApplicationTesSetQuestionManager mApplicationTesSetQuestionManager;
}
