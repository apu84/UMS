package org.ums.academic.resource.helper;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.builder.ExpelledInformationBuilder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.dto.ExamRoutineDto;
import org.ums.domain.model.immutable.ExamRoutine;
import org.ums.domain.model.immutable.ExpelledInformation;
import org.ums.domain.model.immutable.Student;
import org.ums.domain.model.immutable.UGRegistrationResult;
import org.ums.domain.model.mutable.MutableExpelledInformation;
import org.ums.manager.*;
import org.ums.persistent.model.PersistentExpelledInformation;
import org.ums.resource.ResourceHelper;

import javax.json.*;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Monjur-E-Morshed on 5/27/2018.
 */
@Component
public class ExpelledInformationResourceHelper extends
    ResourceHelper<ExpelledInformation, MutableExpelledInformation, Long> {
  @Autowired
  ExpelledInformationManager mManager;
  @Autowired
  ExpelledInformationBuilder mBuilder;
  @Autowired
  UGRegistrationResultManager mUGRegistrationResultManager;
  @Autowired
  SemesterManager mSemesterManager;
  @Autowired
  CourseManager mCourseManager;
  @Autowired
  ExamRoutineManager mExamRoutineManager;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    List<MutableExpelledInformation> applications = new ArrayList<>();
    JsonArray entries = pJsonObject.getJsonArray("entries");
    LocalCache localCache = new LocalCache();
    JsonObject jsonObject = entries.getJsonObject(0);
    PersistentExpelledInformation application = new PersistentExpelledInformation();
    application.setSemesterId(11012017);
    getBuilder().build(application, jsonObject, localCache);

    mManager.create(application);
    URI contextURI = null;
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  public JsonObject getCourseList(final String pStudentId, final Integer pExamType, final Request pRequest,
      final UriInfo pUriInfo) {
    List<ExamRoutineDto> examRoutineList = mExamRoutineManager.getExamRoutine(11012017, pExamType);
    Map<String, String> examRoutineMapWithCourseId = examRoutineList
            .stream()
            .collect(Collectors.toMap(e->e.getCourseId(), e->e.getExamDate()));
    List<ExpelledInformation> expelledInfo=getContentManager().getAll();

    List<UGRegistrationResult> registeredTheoryCourseList =
        mUGRegistrationResultManager.getRegisteredTheoryCourseByStudent(pStudentId, 11012017, pExamType);

    List<MutableExpelledInformation> mutableExpelledInformationList = new ArrayList<>();
    for(UGRegistrationResult registrationResult : registeredTheoryCourseList) {
      MutableExpelledInformation expelledInformation =new PersistentExpelledInformation();
      expelledInformation.setCourseId(registrationResult.getCourseId());
      expelledInformation.setCourseNo(mCourseManager.get(registrationResult.getCourseId()).getNo());
      expelledInformation.setCourseTitle(mCourseManager.get(registrationResult.getCourseId()).getTitle());
      expelledInformation.setExamDate(examRoutineMapWithCourseId.get(registrationResult.getCourseId()));
      expelledInformation.setStatus(expelledInfo.stream().filter(a->a.getSemesterId()==11012017 && a.getCourseId().equals(registrationResult.getCourseId()) && a.getStudentId().equals(pStudentId)
               && a.getExamType()==pExamType).collect(Collectors.toList()).size()==1 ? 1:0);
      mutableExpelledInformationList.add(expelledInformation);
    }

    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for(MutableExpelledInformation app : mutableExpelledInformationList) {
      children.add(toJson(app, pUriInfo, localCache));
    }
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  @Override
  protected ContentManager<ExpelledInformation, MutableExpelledInformation, Long> getContentManager() {
    return mManager;
  }

  @Override
  protected Builder<ExpelledInformation, MutableExpelledInformation> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(ExpelledInformation pReadonly) {
    return null;
  }
}
