package org.ums.academic.resource.student.gradesheet;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.resource.ResourceHelper;
import org.ums.result.gradesheet.GradeSheetManager;
import org.ums.result.gradesheet.GradesheetModel;
import org.ums.result.gradesheet.MutableGradesheetModel;

@Component
public class GradeSheetResourceHelper extends ResourceHelper<GradesheetModel, MutableGradesheetModel, Long> {
  @Autowired
  GradeSheetManager mGradeSheetManager;

  @Autowired
  GradeSheetBuilder mGradeSheetBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    throw new NoSuchMethodError("Not Implemented");
  }

  @Override
  protected GradeSheetManager getContentManager() {
    return mGradeSheetManager;
  }

  @Override
  protected Builder<GradesheetModel, MutableGradesheetModel> getBuilder() {
    return mGradeSheetBuilder;
  }

  @Override
  protected String getETag(GradesheetModel pReadonly) {
    return null;
  }

  JsonObject getGradesheet(final String pStudentId, final Integer pSemesterId, final UriInfo pUriInfo) {
    GradesheetModel gradesheetModel = getContentManager().get(pStudentId, pSemesterId);
    LocalCache localCache = new LocalCache();
    JsonObject object = toJson(gradesheetModel, pUriInfo, localCache);
    localCache.invalidate();
    return object;
  }
}
