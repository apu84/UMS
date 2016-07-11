package org.ums.common.academic.resource.helper;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.common.ResourceHelper;
import org.ums.common.builder.Builder;
import org.ums.common.builder.SeatPlanGroupBuilder;
import org.ums.domain.model.immutable.SeatPlanGroup;
import org.ums.domain.model.mutable.MutableSeatPlanGroup;
import org.ums.manager.*;
import org.ums.persistent.model.PersistentProgram;
import org.ums.persistent.model.PersistentSeatPlanGroup;
import org.ums.persistent.model.PersistentSemester;
import org.ums.response.type.GenericResponse;
import org.ums.services.academic.SeatPlanService;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Monjur-E-Morshed on 4/21/2016.
 */
@Component
public class SeatPlanGroupResourceHelper extends ResourceHelper<SeatPlanGroup, MutableSeatPlanGroup, Integer> {

  @Autowired
  SeatPlanGroupManager mSeatPlanGroupManager;

  @Autowired
  SemesterManager mSemesterManager;

  @Autowired
  ProgramManager mProgramManager;

  @Autowired
  CourseManager mCourseManager;


  @Autowired
  SeatPlanGroupBuilder mBuilder;

  @Autowired
  SeatPlanService mSeatPlanService;


  public JsonObject getSeatPlanGroupBySemester(final int pSemesterId, int type, final int update, final Request pRequest, final UriInfo pUriInfo) throws Exception {

    GenericResponse<Map> genericResponse = null, previousResponse = null;

    /*
    * First, we will check whethere there is value or not in the database. Because, if there is no value and
    * we search by a semesterId, then, our program will be shown error or exception.
    * */
    List<SeatPlanGroup> seatPlanGroupListForCheckingIfThereIsValueOrNot = mSeatPlanGroupManager.getAll();
    List<SeatPlanGroup> seatPlanGroupListBySemesterAndType;



    /*variable seatPlanGroupForSemester will be used to sent the group information based on the semesterId
    * */
    List<SeatPlanGroup> seatPlanGroupForSemester;

    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    Boolean seatPlanGroupExistForTheSemesterAndType = false;

    if (seatPlanGroupListForCheckingIfThereIsValueOrNot.size() > 0) {
      seatPlanGroupListBySemesterAndType = mSeatPlanGroupManager.getGroupBySemester(pSemesterId, type);
      if (seatPlanGroupListBySemesterAndType.size() > 0) {
        seatPlanGroupExistForTheSemesterAndType = true;
      }
    }


    if (seatPlanGroupExistForTheSemesterAndType == true && update == 0) {

      //seatPlanGroupForSemester = mSeatPlanGroupManager.getGroupBySemester(pSemesterId,type);

      seatPlanGroupForSemester = mSeatPlanGroupManager.getGroupBySemesterTypeFromDb(pSemesterId, type);
      for (SeatPlanGroup seatPlanGroup : seatPlanGroupForSemester) {

        children.add(toJson(seatPlanGroup, pUriInfo, localCache));
      }

    } else {
      /*genericResponse = mSeatPlanService.generateGroup(pSemesterId,type);
      if (genericResponse != null ) {
        seatPlanGroupForSemester = mSeatPlanGroupManager.getGroupBySemester(pSemesterId,type);
        for(SeatPlanGroup seatPlanGroup: seatPlanGroupForSemester){

          children.add(toJson(seatPlanGroup,pUriInfo,localCache));
        }
      }
      else{
        genericResponse.setMessage(genericResponse.getMessage() + "\n" + previousResponse.getMessage());
      }*/

      List<SeatPlanGroup> seatplanGroups = mSeatPlanGroupManager.getGroupBySemester(pSemesterId, type);

      List<MutableSeatPlanGroup> mutableSeatPlanGroups = new ArrayList<>();

      for (SeatPlanGroup group : seatplanGroups) {
        MutableSeatPlanGroup mSeatPlanGroup = new PersistentSeatPlanGroup();
        PersistentSemester semester = new PersistentSemester();
        semester.setId(group.getSemester().getId());
        mSeatPlanGroup.setSemester(semester);
        PersistentProgram program = new PersistentProgram();
        program.setId(group.getProgram().getId());
        mSeatPlanGroup.setProgram(program);
        mSeatPlanGroup.setAcademicYear(group.getAcademicYear());
        mSeatPlanGroup.setAcademicSemester(group.getAcademicSemester());
        mSeatPlanGroup.setGroupNo(group.getGroupNo());
        mSeatPlanGroup.setExamType(group.getExamType());
        mSeatPlanGroup.setProgramShortName(group.getProgramName());
        mSeatPlanGroup.setTotalStudentNumber(group.getTotalStudentNumber());
        mutableSeatPlanGroups.add(mSeatPlanGroup);
      }

      mSeatPlanGroupManager.create(mutableSeatPlanGroups);

      seatPlanGroupForSemester = mSeatPlanGroupManager.getGroupBySemesterTypeFromDb(pSemesterId, type);
      for (SeatPlanGroup seatPlanGroup : seatPlanGroupForSemester) {

        children.add(toJson(seatPlanGroup, pUriInfo, localCache));
      }
    }

    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    throw new NotImplementedException("Post method not implemented for SeatPlanGroupResourceHelper");
  }

  @Override
  protected SeatPlanGroupManager getContentManager() {
    return mSeatPlanGroupManager;
  }

  @Override
  protected Builder<SeatPlanGroup, MutableSeatPlanGroup> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getEtag(SeatPlanGroup pReadonly) {
    return pReadonly.getLastUpdateDate();
  }
}
