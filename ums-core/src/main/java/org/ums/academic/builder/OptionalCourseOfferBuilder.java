package org.ums.academic.builder;

import org.ums.cache.LocalCache;
import org.ums.domain.model.dto.ExamRoutineDto;
import org.ums.domain.model.mutable.MutableExamRoutine;
import org.ums.domain.model.mutable.MutableOptionalCourseOffer;
import org.ums.domain.model.readOnly.ExamRoutine;
import org.ums.domain.model.readOnly.OptionalCourseOffer;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;


public class OptionalCourseOfferBuilder  implements Builder<OptionalCourseOffer, MutableOptionalCourseOffer> {

  @Override
  public void build(JsonObjectBuilder pBuilder, OptionalCourseOffer pReadOnly, UriInfo pUriInfo, final LocalCache pLocalCache) throws Exception {

  }

  @Override
  public void build(MutableOptionalCourseOffer pMutable, JsonObject pJsonObject, final LocalCache pLocalCache) throws Exception {
  }
}
