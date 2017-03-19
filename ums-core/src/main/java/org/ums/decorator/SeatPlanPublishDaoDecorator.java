package org.ums.decorator;

import org.ums.domain.model.immutable.SeatPlanPublish;
import org.ums.domain.model.mutable.MutableSeatPlanPublish;
import org.ums.manager.SeatPlanPublishManager;

import java.util.List;

/**
 * Created by My Pc on 8/2/2016.
 */
public class SeatPlanPublishDaoDecorator extends
    ContentDaoDecorator<SeatPlanPublish, MutableSeatPlanPublish, Integer, SeatPlanPublishManager> implements
    SeatPlanPublishManager {

  @Override
  public List<SeatPlanPublish> getBySemester(Integer pSemesterId) {
    return getManager().getBySemester(pSemesterId);
  }

  @Override
  public Integer checkBySemester(Integer pSemesterId) {
    return getManager().checkBySemester(pSemesterId);
  }

  @Override
  public Integer deleteBySemester(Integer pSemesterId) {
    return getManager().deleteBySemester(pSemesterId);
  }
}
