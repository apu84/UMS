package org.ums.academic.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.builder.QuestionCorrectionBuilder;
import org.ums.domain.model.immutable.QuestionCorrectionInfo;
import org.ums.domain.model.mutable.MutableQuestionCorrectionInfo;
import org.ums.manager.ApplicationCCIManager;
import org.ums.manager.ContentManager;
import org.ums.manager.QuestionCorrectionManager;
import org.ums.manager.StudentManager;
import org.ums.resource.ResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Monjur-E-Morshed on 7/11/2018.
 */
@Component
public class QuestionCorrectionResourceHelper extends
    ResourceHelper<QuestionCorrectionInfo, MutableQuestionCorrectionInfo, Long> {

  @Autowired
  QuestionCorrectionManager mQuestionCorrectionManager;

  @Autowired
  StudentManager mStudentManager;

  @Autowired
  QuestionCorrectionBuilder mBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  @Override
  protected ContentManager<QuestionCorrectionInfo, MutableQuestionCorrectionInfo, Long> getContentManager() {
    return mQuestionCorrectionManager;
  }

  @Override
  protected Builder<QuestionCorrectionInfo, MutableQuestionCorrectionInfo> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(QuestionCorrectionInfo pReadonly) {
    return null;
  }
}
