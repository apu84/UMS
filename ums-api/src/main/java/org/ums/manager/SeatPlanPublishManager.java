package org.ums.manager;

import org.ums.domain.model.immutable.SeatPlanPublish;
import org.ums.domain.model.mutable.MutableSeatPlanPublish;

import java.util.List;

/**
 * Created by My Pc on 8/2/2016.
 */
public interface SeatPlanPublishManager extends ContentManager<SeatPlanPublish, MutableSeatPlanPublish, Integer> {
  List<SeatPlanPublish> getBySemester(Integer pSemesterId);

  Integer checkBySemester(Integer pSemesterId);

  Integer deleteBySemester(Integer pSemesterId);
}
