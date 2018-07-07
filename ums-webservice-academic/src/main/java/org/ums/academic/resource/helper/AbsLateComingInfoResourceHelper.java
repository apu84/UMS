package org.ums.academic.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.AbsLateComingInfoBuilder;
import org.ums.builder.ApplicationCCIBuilder;
import org.ums.builder.Builder;
import org.ums.domain.model.immutable.AbsLateComingInfo;
import org.ums.domain.model.mutable.MutableAbsLateComingInfo;
import org.ums.manager.AbsLateComingInfoManager;
import org.ums.manager.ContentManager;
import org.ums.manager.SemesterManager;
import org.ums.manager.StudentManager;
import org.ums.resource.ResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Monjur-E-Morshed on 7/1/2018.
 */
@Component
public class AbsLateComingInfoResourceHelper extends ResourceHelper<AbsLateComingInfo, MutableAbsLateComingInfo, Long> {
  @Autowired
  AbsLateComingInfoManager mAbsLateComingInfoManager;
  @Autowired
  StudentManager mStudentManager;
  @Autowired
  SemesterManager mSemesterManager;
  @Autowired
  AbsLateComingInfoBuilder mBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  @Override
  protected ContentManager<AbsLateComingInfo, MutableAbsLateComingInfo, Long> getContentManager() {
    return getContentManager();
  }

  @Override
  protected Builder<AbsLateComingInfo, MutableAbsLateComingInfo> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(AbsLateComingInfo pReadonly) {
    return null;
  }
}
