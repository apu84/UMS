package org.ums.services.academic;

import org.jetbrains.annotations.Mutable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ums.domain.model.immutable.SeatPlanGroup;
import org.ums.domain.model.immutable.SubGroup;
import org.ums.domain.model.mutable.MutableSubGroup;
import org.ums.manager.SeatPlanGroupManager;
import org.ums.manager.SubGroupManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by Monjur-E-Morshed on 16-Aug-18.
 */
@Service
public class SeatPlanSubGroupService {
  @Autowired
  SubGroupManager mSubGroupManager;
  @Autowired
  SeatPlanGroupManager mSeatPlanGroupManager;

  @Transactional
  public void updateSeatPlanSubGroup(List<SeatPlanGroup> pPreviousSeatPlanGroup, List<SeatPlanGroup> pNewSeatPlanGroup,int pSemesterId,  int pExamType){
    Map<String, Integer> previousSeatPlanGroupMap = pPreviousSeatPlanGroup
        .parallelStream()
        .collect(Collectors.toMap(p->p.getProgramId()+""+p.getAcademicYear()+""+p.getAcademicSemester()+""+p.getGroupNo(), p->p.getId()));
    Map<String, Integer> newSeatPlanGroupMap = pNewSeatPlanGroup
        .parallelStream()
        .collect(Collectors.toMap(p->p.getProgramId()+""+p.getAcademicYear()+""+p.getAcademicSemester()+""+p.getGroupNo(), p->p.getId()));

    Map<Integer, Integer> previousGroupIdMapWithNewId = new HashMap<>();

    for(Map.Entry<String, Integer> entry : previousSeatPlanGroupMap.entrySet()){
      previousGroupIdMapWithNewId.put(previousSeatPlanGroupMap.get(entry.getKey()), newSeatPlanGroupMap.get(entry.getKey()));
    }

    List<SubGroup> subGroupList = mSubGroupManager.getBySemesterAndExamType(pSemesterId, pExamType);
    List<MutableSubGroup> mutableSubGroupList = new ArrayList<>();

    for(SubGroup subGroup: subGroupList){
      MutableSubGroup mutableSubGroup = (MutableSubGroup) subGroup;
      mutableSubGroup.setGroupId(previousGroupIdMapWithNewId.get(subGroup.getGroupId()));
      mutableSubGroupList.add(mutableSubGroup);
    }

    if(subGroupList.size()>0)
    mSubGroupManager.update(mutableSubGroupList);
  }
}
