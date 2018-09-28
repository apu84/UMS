package org.ums.academic.resource.optCourse.optCourseHelper;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ums.academic.resource.optCourse.optCourseBuilder.OptOfferedGroupBuilder;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.Program;
import org.ums.domain.model.immutable.SubGroup;
import org.ums.domain.model.immutable.optCourse.*;
import org.ums.domain.model.mutable.optCourse.MutableOptOfferedGroup;
import org.ums.domain.model.mutable.optCourse.MutableOptOfferedGroupCourseMap;
import org.ums.domain.model.mutable.optCourse.MutableOptOfferedGroupSubGroupMap;
import org.ums.domain.model.mutable.optCourse.MutableOptOfferedSubGroupCourseMap;
import org.ums.enums.ProgramType;
import org.ums.enums.common.DepartmentType;
import org.ums.generator.IdGenerator;
import org.ums.manager.*;
import org.ums.manager.optCourse.*;
import org.ums.persistent.model.optCourse.PersistentOptOfferedGroup;
import org.ums.persistent.model.optCourse.PersistentOptOfferedGroupCourseMap;
import org.ums.persistent.model.optCourse.PersistentOptOfferedGroupSubGroupMap;
import org.ums.persistent.model.optCourse.PersistentOptOfferedSubGroupCourseMap;
import org.ums.report.optReports.CourseList;
import org.ums.report.optReports.OfferedOptCourseList;
import org.ums.report.optReports.SubGroupList;
import org.ums.resource.ResourceHelper;
import org.ums.usermanagement.user.UserManager;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Monjur-E-Morshed on 9/18/2018.
 */
@Component
public class OptOfferedGroupResourceHelper extends ResourceHelper<OptOfferedGroup, MutableOptOfferedGroup, Long> {
  @Autowired
  OptOfferedGroupManager mOptOfferedGroupManager;
  @Autowired
  OptOfferedGroupBuilder mOptOfferedGroupBuilder;
  @Autowired
  SemesterManager mSemesterManager;
  @Autowired
  IdGenerator mIdGenerator;
  @Autowired
  OptOfferedGroupCourseMapManager mOptOfferedGroupCourseMapManager;
  @Autowired
  OptOfferedGroupSubGroupMapManager mOptOfferedGroupSubGroupMapManager;
  @Autowired
  OptOfferedSubGroupCourseMapManager mOptOfferedSubGroupCourseMapManager;
  @Autowired
  ProgramManager mProgramManager;
  @Autowired
  UserManager mUserManager;
  @Autowired
  EmployeeManager mEmployeeManager;
  @Autowired
  CourseManager mCourseManager;
  @Autowired
  OptSeatAllocationManager mOptSeatAllocationManager;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  @Transactional
  public Response addInfo(Integer pProgramId, Integer pYear, Integer pSemester,
      List<OfferedOptCourseList> offeredCourseList) {
    Integer semesterId = mSemesterManager.getActiveSemester(ProgramType.UG.getValue()).getId();
    List<OfferedOptCourseList> savedInDb = getOptOfferedCourseList(semesterId, pProgramId, pYear, pSemester);
    if(savedInDb.size()> 0) {
      // optGroup
      List<MutableOptOfferedGroup> deleteOptOfferedGroup = new ArrayList<>();
      for(int i = 0; i < savedInDb.size(); i++) {
        MutableOptOfferedGroup app = new PersistentOptOfferedGroup();
        app.setId(savedInDb.get(i).groupId);
        deleteOptOfferedGroup.add(app);
      }
      mOptOfferedGroupManager.delete(deleteOptOfferedGroup);
      // optGroupCourseMap
      if(pProgramId!=110500) {
        List<MutableOptOfferedGroupCourseMap> deleteOptOfferedGroupCourseMap = new ArrayList<>();
        for(int i = 0; i < savedInDb.size(); i++) {
          for(int j = 0; j < savedInDb.get(i).courses.size(); j++) {
            MutableOptOfferedGroupCourseMap app = new PersistentOptOfferedGroupCourseMap();
            app.setGroupId(savedInDb.get(i).groupId);
            app.setCourseId(savedInDb.get(i).courses.get(j).getId());
            deleteOptOfferedGroupCourseMap.add(app);
          }
        }
        mOptOfferedGroupCourseMapManager.delete(deleteOptOfferedGroupCourseMap);
      }
      // optGroupSubGroupMap
      if(pProgramId==110500) {
        List<MutableOptOfferedGroupSubGroupMap> deleteOptOfferedGroupSubGroupMap = new ArrayList<>();
        for(int i = 0; i < savedInDb.size(); i++) {
          for(int j = 0; j < savedInDb.get(i).subGrpCourses.size(); j++) {
            MutableOptOfferedGroupSubGroupMap app = new PersistentOptOfferedGroupSubGroupMap();
            app.setGroupId(savedInDb.get(i).groupId);
            app.setSubGroupId(savedInDb.get(i).subGrpCourses.get(j).groupId);
            deleteOptOfferedGroupSubGroupMap.add(app);
          }
        }
        mOptOfferedGroupSubGroupMapManager.delete(deleteOptOfferedGroupSubGroupMap);
        // optSubGroupCourseMap
        List<MutableOptOfferedSubGroupCourseMap> deleteOptOfferedSubGroupCourseMap = new ArrayList<>();
        for(int i = 0; i < savedInDb.size(); i++) {
          for(int j = 0; j < savedInDb.get(i).subGrpCourses.size(); j++) {
            for(int k = 0; k < savedInDb.get(i).subGrpCourses.get(j).courses.size(); k++) {
              MutableOptOfferedSubGroupCourseMap app = new PersistentOptOfferedSubGroupCourseMap();
              app.setSubGroupId(savedInDb.get(i).subGrpCourses.get(j).groupId);
              app.setCourseId(savedInDb.get(i).subGrpCourses.get(j).courses.get(k).getId());
              deleteOptOfferedSubGroupCourseMap.add(app);
            }

          }
        }
        mOptOfferedSubGroupCourseMapManager.delete(deleteOptOfferedSubGroupCourseMap);
      }


    }


      String userId = SecurityUtils.getSubject().getPrincipal().toString(); String
      deptId=mEmployeeManager.get(userId).getDepartment().getId();
      List<Program> programList=mProgramManager.getProgramByDepartmentId(DepartmentType.EEE.getId());

      List<MutableOptOfferedGroup> optGroupList = optGroup(pProgramId, pYear, pSemester,semesterId,
      offeredCourseList); mOptOfferedGroupManager.create(optGroupList);

      List<OptOfferedGroup>
      grpList=mOptOfferedGroupManager.getBySemesterId(semesterId,pProgramId,pYear,pSemester);
      Map<String,Long>
      groupNameIdMap=grpList.stream().collect(Collectors.toMap(e->e.getGroupName(),e->e.getId()));
      if(pProgramId!=110500){
        List<MutableOptOfferedGroupCourseMap> optGroupCourseIdMapList = getMutableOptOfferedGroupCourseMaps(offeredCourseList, groupNameIdMap);
      mOptOfferedGroupCourseMapManager.create(optGroupCourseIdMapList);
      }

      if(pProgramId==110500) {
        List<MutableOptOfferedGroupSubGroupMap> groupSubGroupMapList = getMutableOptOfferedGroupSubGroupMaps(offeredCourseList, groupNameIdMap);
      mOptOfferedGroupSubGroupMapManager.create(groupSubGroupMapList);

      List<OptOfferedGroupSubGroupMap> grpSubGrpList = mOptOfferedGroupSubGroupMapManager.getBySemesterId(semesterId, pProgramId, pYear, pSemester);
      Map<String, Long> groupSubGroupNameIdMap = grpSubGrpList.stream().collect(Collectors.toMap(e -> e.getSubGroupName(), e -> e.getSubGroupId()));
      List<MutableOptOfferedSubGroupCourseMap> subGroupCourseMap = getMutableOptOfferedSubGroupCourseMaps(offeredCourseList,
      groupSubGroupNameIdMap); mOptOfferedSubGroupCourseMapManager.create(subGroupCourseMap);
      }

  URI contextURI = null;
  Response.ResponseBuilder builder = Response.created(contextURI);
  builder.status(Response.Status.CREATED);
  return builder.build();
  }

  public List<OfferedOptCourseList> getOptOfferedCourseList(Integer pSemesterId,Integer pProgramId,Integer pYear,Integer pSemester){
    List<OptOfferedGroup> grpList=mOptOfferedGroupManager.getBySemesterId(pSemesterId,pProgramId,pYear,pSemester);
    List<OptOfferedGroupCourseMap> groupCourseMap=mOptOfferedGroupCourseMapManager.getInfo(pSemesterId,pProgramId,pYear,pSemester);
    Map<Long,List<OptOfferedGroupCourseMap>> map=groupCourseMap.stream().collect(Collectors.groupingBy(OptOfferedGroupCourseMap::getGroupId));
    Map<Long,List<CourseList>> tempCourseList=  new HashMap<>();
    for(Map.Entry<Long,List<OptOfferedGroupCourseMap>> entry: map.entrySet()){
      Long groupId=entry.getKey();
      List<CourseList> courseList= new ArrayList<>();
      for(int i=0;i<entry.getValue().size();i++){
        String courseId=entry.getValue().get(i).getCourseId();
        CourseList app= new CourseList(courseId,
                mCourseManager.get(courseId).getTitle(),
                mCourseManager.get(courseId).getNo(),
                mCourseManager.get(courseId).getCrHr(),
                mCourseManager.get(courseId).getCourseType().getLabel().toUpperCase(),
                mCourseManager.get(courseId).getYear(),
                mCourseManager.get(courseId).getSemester(),
                mCourseManager.get(courseId).getPairCourseId(),
                0);
        courseList.add(app);
      }
      tempCourseList.put(groupId,courseList);
    }
    //Sub Group portion
    List<OptOfferedGroupSubGroupMap> grpSubGrpList = mOptOfferedGroupSubGroupMapManager.getBySemesterId(pSemesterId, pProgramId, pYear, pSemester);
    Map<Long,List<OptOfferedGroupSubGroupMap>> subGrpMap=grpSubGrpList.stream().collect(Collectors.groupingBy(OptOfferedGroupSubGroupMap::getGroupId));
    List<OptOfferedSubGroupCourseMap> subGroupCourseMap=mOptOfferedSubGroupCourseMapManager.getSubGroupCourses(pSemesterId,pProgramId,pYear,pSemester);
    Map<Long,List<OptOfferedSubGroupCourseMap>> subGrpMapWithCourseId=subGroupCourseMap.stream().collect(Collectors.groupingBy(OptOfferedSubGroupCourseMap::getSubGroupId));
    //Map for CourseList
    Map<Long,List<CourseList>> tempSubGroupCourseList=  new HashMap<>();
    for(Map.Entry<Long,List<OptOfferedSubGroupCourseMap>> entry:subGrpMapWithCourseId.entrySet()){
      Long groupId=entry.getKey();
      List<CourseList> courseList= new ArrayList<>();
      for(int i=0;i<entry.getValue().size();i++){
        String courseId=entry.getValue().get(i).getCourseId();
        CourseList app= new CourseList(courseId,
                mCourseManager.get(courseId).getTitle(),
                mCourseManager.get(courseId).getNo(),
                mCourseManager.get(courseId).getCrHr(),
                mCourseManager.get(courseId).getCourseType().getLabel().toUpperCase(),
                mCourseManager.get(courseId).getYear(),
                mCourseManager.get(courseId).getSemester(),
                mCourseManager.get(courseId).getPairCourseId(),
                0);
        courseList.add(app);
      }
      tempSubGroupCourseList.put(groupId,courseList);

    }
    //Group && sub Group object Map
    Map<Long, Integer> groupSeatNumMap= new HashMap<>();
    List<OptSeatAllocation> optSeatAllocation = mOptSeatAllocationManager.getInfoBySemesterId(pSemesterId, pProgramId, pYear, pSemester);
    groupSeatNumMap = optSeatAllocation.stream().collect(Collectors.toMap(e -> e.getGroupID(), e -> e.getSeatNumber(), (o, n) -> n));

    Map<Long,List<SubGroupList>> subGroupCourseList= new HashMap<>();
    for(Map.Entry<Long,List<OptOfferedGroupSubGroupMap>> entry:subGrpMap.entrySet()){
      Long grpId=entry.getKey();
      List<SubGroupList> subGroupList= new ArrayList<>();
      for(int i=0;i<entry.getValue().size();i++){
        SubGroupList app = new SubGroupList();
        app.setSubGroupId(entry.getValue().get(i).getSubGroupId());
        app.setSubGroupName(entry.getValue().get(i).getSubGroupName());
        app.setTotalApplied(0);
        app.setTotalSeats(groupSeatNumMap.get(entry.getValue().get(i).getSubGroupId()));
        app.setCourses(tempSubGroupCourseList.get(entry.getValue().get(i).getSubGroupId()));
        subGroupList.add(app);
      }
      subGroupCourseList.put(grpId,subGroupList);
    }
    //End of Sub group


    List<OfferedOptCourseList> newList= new ArrayList<>();
    for(int i=0;i<grpList.size();i++){
      OfferedOptCourseList app = new OfferedOptCourseList();
      app.setGroupId(grpList.get(i).getId());
      app.setGroupName(grpList.get(i).getGroupName());
      app.setProgramId(grpList.get(i).getProgramId());
      app.setYear(grpList.get(i).getYear());
      app.setSemester(grpList.get(i).getSemester());
      app.setTotalSeats(groupSeatNumMap.get(grpList.get(i).getId()));
      app.setMandatory(grpList.get(i).getIsMandatory()==1 ?true:false);
      app.setCourses(tempCourseList.get(grpList.get(i).getId()));
      app.setSubGrpCourses(subGroupCourseList.get(grpList.get(i).getId()));
      newList.add(app);
    }

    return newList;
  }

  public List<MutableOptOfferedSubGroupCourseMap> getMutableOptOfferedSubGroupCourseMaps(
      List<OfferedOptCourseList> offeredCourseList, Map<String, Long> groupSubGroupNameIdMap) {
    List<MutableOptOfferedSubGroupCourseMap> subGroupCourseMap = new ArrayList<>();
    for(int i = 0; i < offeredCourseList.size(); i++) {
      for(int j = 0; j < offeredCourseList.get(i).subGrpCourses.size(); j++) {
        for(int k = 0; k < offeredCourseList.get(i).subGrpCourses.get(j).courses.size(); k++) {
          MutableOptOfferedSubGroupCourseMap app = new PersistentOptOfferedSubGroupCourseMap();
          app.setSubGroupId(groupSubGroupNameIdMap.get(offeredCourseList.get(i).subGrpCourses.get(j).groupName));
          app.setCourseId(offeredCourseList.get(i).subGrpCourses.get(j).courses.get(k).getId());
          subGroupCourseMap.add(app);
        }

      }
    }
    return subGroupCourseMap;
  }

  public List<MutableOptOfferedGroupSubGroupMap> getMutableOptOfferedGroupSubGroupMaps(
      List<OfferedOptCourseList> offeredCourseList, Map<String, Long> groupNameIdMap) {
    List<MutableOptOfferedGroupSubGroupMap> groupSubGroupMapList = new ArrayList<>();
    for(int i = 0; i < offeredCourseList.size(); i++) {
      for(int j = 0; j < offeredCourseList.get(i).subGrpCourses.size(); j++) {
        MutableOptOfferedGroupSubGroupMap app = new PersistentOptOfferedGroupSubGroupMap();
        app.setGroupId(groupNameIdMap.get(offeredCourseList.get(i).getGroupName()));
        app.setSubGroupName(offeredCourseList.get(i).subGrpCourses.get(j).groupName);
        groupSubGroupMapList.add(app);
      }
    }
    return groupSubGroupMapList;
  }

  public List<MutableOptOfferedGroupCourseMap> getMutableOptOfferedGroupCourseMaps(
      List<OfferedOptCourseList> offeredCourseList, Map<String, Long> groupNameIdMap) {
    List<MutableOptOfferedGroupCourseMap> optGroupCourseIdMapList = new ArrayList<>();
    for(int i = 0; i < offeredCourseList.size(); i++) {
      for(int j = 0; j < offeredCourseList.get(i).courses.size(); j++) {
        MutableOptOfferedGroupCourseMap app = new PersistentOptOfferedGroupCourseMap();
        app.setGroupId(groupNameIdMap.get(offeredCourseList.get(i).getGroupName()));
        app.setCourseId(offeredCourseList.get(i).courses.get(j).getId());
        optGroupCourseIdMapList.add(app);
      }
    }
    return optGroupCourseIdMapList;
  }

  private List<MutableOptOfferedGroup> optGroup(Integer pProgramId, Integer pYear, Integer pSemester,
      Integer pSemesterId, List<OfferedOptCourseList> offeredCourseList) {
    List<MutableOptOfferedGroup> optGroup = new ArrayList<>();
    for(int i = 0; i < offeredCourseList.size(); i++) {
      MutableOptOfferedGroup app = new PersistentOptOfferedGroup();
      app.setGroupName(offeredCourseList.get(i).groupName);
      app.setIsMandatory(offeredCourseList.get(i).isMandatory == true ? 1 : 0);
      app.setSemesterId(pSemesterId);
      app.setProgramId(pProgramId);
      app.setYear(pYear);
      app.setSemester(pSemester);
      optGroup.add(app);
    }
    return optGroup;
  }

  public JsonObject getGroupInfo(Integer pSemesterId, Integer pProgramId, Integer pYear, Integer pSemester,
      final UriInfo pUriInfo) {
    List<OptOfferedGroup> optOfferedGroup =
        mOptOfferedGroupManager.getBySemesterId(pSemesterId, pProgramId, pYear, pSemester);
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for(OptOfferedGroup app : optOfferedGroup) {
      children.add(toJson(app, pUriInfo, localCache));
    }
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  @Override
  protected ContentManager<OptOfferedGroup, MutableOptOfferedGroup, Long> getContentManager() {
    return mOptOfferedGroupManager;
  }

  @Override
  protected Builder<OptOfferedGroup, MutableOptOfferedGroup> getBuilder() {
    return mOptOfferedGroupBuilder;
  }

  @Override
  protected String getETag(OptOfferedGroup pReadonly) {
    return null;
  }
}
