package org.ums.common.academic.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.academic.dao.PersistentOptionalCourseApplicationDao;
import org.ums.domain.model.dto.OptionalCourseApplicationStatDto;

import javax.json.*;
import java.util.List;

@Component
public class OptionalCourseApplicationResourceHelper {

  @Autowired
  private PersistentOptionalCourseApplicationDao mManager;

  public PersistentOptionalCourseApplicationDao getContentManager() {
    return mManager;
  }

  public JsonObject getApplicationStatistics(final Integer pSemesterId,final Integer pProgramId) throws Exception {
    List<OptionalCourseApplicationStatDto> statList= mManager.getApplicationStatistics(pSemesterId, pProgramId,1,1);

    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    for (OptionalCourseApplicationStatDto stat : statList) {
      children.add(stat.toString());
    }
    object.add("entries", children);
    return object.build();


  }



}
