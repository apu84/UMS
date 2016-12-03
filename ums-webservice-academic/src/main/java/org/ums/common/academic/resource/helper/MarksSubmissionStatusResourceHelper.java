package org.ums.common.academic.resource.helper;

import java.util.List;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.common.builder.MarksSubmissionStatusBuilder;
import org.ums.domain.model.immutable.MarksSubmissionStatus;
import org.ums.domain.model.mutable.MutableMarksSubmissionStatus;
import org.ums.manager.ContentManager;
import org.ums.manager.MarksSubmissionStatusManager;
import org.ums.resource.ResourceHelper;

@Component
public class MarksSubmissionStatusResourceHelper extends
    ResourceHelper<MarksSubmissionStatus, MutableMarksSubmissionStatus, Integer> {

  @Autowired
  MarksSubmissionStatusBuilder mBuilder;

  @Autowired
  MarksSubmissionStatusManager mMarksSubmissionStatusManager;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) {
    return null;
  }

  @Override
  protected ContentManager<MarksSubmissionStatus, MutableMarksSubmissionStatus, Integer> getContentManager() {
    return mMarksSubmissionStatusManager;
  }

  @Override
  protected Builder<MarksSubmissionStatus, MutableMarksSubmissionStatus> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getEtag(MarksSubmissionStatus pReadonly) {
    return pReadonly.getLastModified();
  }

  public JsonObject getMarksSubmissionStatus(int pProgramTypeId, int pSemesterId,
      final UriInfo pUriInfo) {
    List<MarksSubmissionStatus> statuses =
        mMarksSubmissionStatusManager.getByProgramType(pProgramTypeId, pSemesterId);
    return buildJsonResponse(statuses, pUriInfo);
  }

  public JsonObject getMarksSubmissionStatusByProgram(int pProgramId, int pSemesterId,
      final UriInfo pUriInfo) {
    List<MarksSubmissionStatus> statuses =
        mMarksSubmissionStatusManager.get(pProgramId, pSemesterId);
    return buildJsonResponse(statuses, pUriInfo);
  }
}
