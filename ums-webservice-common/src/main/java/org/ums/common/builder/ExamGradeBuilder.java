package org.ums.common.builder;

import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.dto.ExamRoutineDto;
import org.ums.domain.model.immutable.ExamGrade;
import org.ums.domain.model.immutable.ExamRoutine;
import org.ums.domain.model.mutable.MutableExamGrade;
import org.ums.domain.model.mutable.MutableExamRoutine;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ikh on 4/30/2016.
 */
@Component
public class ExamGradeBuilder implements Builder<ExamGrade, MutableExamGrade> {

    @Override
    public void build(JsonObjectBuilder pBuilder, ExamGrade pReadOnly, UriInfo pUriInfo, final LocalCache pLocalCache) throws Exception {

    }

    @Override
    public void build(MutableExamGrade pMutable, JsonObject pJsonObject, final LocalCache pLocalCache) throws Exception {
    }
}
