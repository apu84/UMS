package org.ums.academic.resource.optCourse.optCourseHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.academic.resource.optCourse.optCourseBuilder.OptOfferedGroupBuilder;
import org.ums.builder.Builder;
import org.ums.domain.model.immutable.optCourse.OptOfferedGroup;
import org.ums.domain.model.mutable.optCourse.MutableOptOfferedGroup;
import org.ums.domain.model.mutable.optCourse.MutableOptOfferedGroupCourseMap;
import org.ums.domain.model.mutable.optCourse.MutableOptOfferedGroupSubGroupMap;
import org.ums.domain.model.mutable.optCourse.MutableOptOfferedSubGroupCourseMap;
import org.ums.enums.ProgramType;
import org.ums.generator.IdGenerator;
import org.ums.manager.ContentManager;
import org.ums.manager.SemesterManager;
import org.ums.manager.optCourse.OptOfferedGroupManager;
import org.ums.persistent.model.optCourse.PersistentOptOfferedGroup;
import org.ums.persistent.model.optCourse.PersistentOptOfferedGroupCourseMap;
import org.ums.persistent.model.optCourse.PersistentOptOfferedGroupSubGroupMap;
import org.ums.persistent.model.optCourse.PersistentOptOfferedSubGroupCourseMap;
import org.ums.report.optReports.OfferedOptCourseList;
import org.ums.resource.ResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

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

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  public Response addInfo(Integer pProgramId, Integer pYear, Integer pSemester,
      List<OfferedOptCourseList> offeredCourseList) {
    for(int i = 0; i < offeredCourseList.size(); i++) {
      offeredCourseList.get(i).setGroupId(Long.parseLong(mIdGenerator.getAlphaNumericId()));
    }

    List<MutableOptOfferedGroup> optGroupList = optGroup(pProgramId, pYear, pSemester, offeredCourseList);
    List<MutableOptOfferedGroupCourseMap> optGroupCourseIdMapList = new ArrayList<>();
    for(int i = 0; i < offeredCourseList.size(); i++) {
      for(int j = 0; j < offeredCourseList.get(i).courses.size(); j++) {
        MutableOptOfferedGroupCourseMap app = new PersistentOptOfferedGroupCourseMap();
        // app.setGroupId(Long.parseLong(String.valueOf(offeredCourseList.get(i).groupId)));
        app.setCourseId(offeredCourseList.get(i).courses.get(j).getId());
        optGroupCourseIdMapList.add(app);
      }
    }
    List<MutableOptOfferedGroupSubGroupMap> groupSubGroupMapList = new ArrayList<>();
    for(int i = 0; i < offeredCourseList.size(); i++) {
      for(int j = 0; j < offeredCourseList.get(i).subGrpCourses.size(); j++) {
        MutableOptOfferedGroupSubGroupMap app = new PersistentOptOfferedGroupSubGroupMap();
        app.setSubGroupName(offeredCourseList.get(i).subGrpCourses.get(j).groupName);
        // app.setGroupId(offeredCourseList.get(i).groupId);
        groupSubGroupMapList.add(app);
      }
    }
    List<MutableOptOfferedSubGroupCourseMap> subGroupCourseMap = new ArrayList<>();
    for(int i = 0; i < offeredCourseList.size(); i++) {
      for(int j = 0; j < offeredCourseList.get(i).subGrpCourses.size(); j++) {
        for(int k = 0; k < offeredCourseList.get(i).subGrpCourses.get(j).courses.size(); k++) {
          MutableOptOfferedSubGroupCourseMap app = new PersistentOptOfferedSubGroupCourseMap();
          app.setCourseId(offeredCourseList.get(i).subGrpCourses.get(j).courses.get(k).getId());
          subGroupCourseMap.add(app);
        }

      }
    }

    return null;
  }

  private List<MutableOptOfferedGroup> optGroup(Integer pProgramId, Integer pYear, Integer pSemester,
      List<OfferedOptCourseList> offeredCourseList) {
    List<MutableOptOfferedGroup> optGroup = new ArrayList<>();
    for(int i = 0; i < offeredCourseList.size(); i++) {
      MutableOptOfferedGroup app = new PersistentOptOfferedGroup();
      app.setGroupName(offeredCourseList.get(i).groupName);
      app.setIsMandatory(offeredCourseList.get(i).isMandatory == true ? 1 : 0);
      app.setSemesterId(mSemesterManager.getActiveSemester(ProgramType.UG.getValue()).getId());
      app.setProgramId(pProgramId);
      app.setYear(pYear);
      app.setSemester(pSemester);
      optGroup.add(app);
    }
    return optGroup;
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
