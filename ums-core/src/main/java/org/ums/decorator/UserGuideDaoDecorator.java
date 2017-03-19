package org.ums.decorator;

import org.ums.domain.model.immutable.MarksSubmissionStatus;
import org.ums.domain.model.immutable.UserGuide;
import org.ums.domain.model.mutable.MutableMarksSubmissionStatus;
import org.ums.domain.model.mutable.MutableUserGuide;
import org.ums.enums.ExamType;
import org.ums.manager.MarksSubmissionStatusManager;
import org.ums.manager.UserGuideManager;

import java.util.List;

/**
 * Created by Ifti on 17-Dec-16.
 */
public class UserGuideDaoDecorator extends ContentDaoDecorator<UserGuide, MutableUserGuide, Integer, UserGuideManager>
    implements UserGuideManager {
  @Override
  public List<UserGuide> getUserGuideList(Integer pRoleId, String pUserId) {
    return getManager().getUserGuideList(pRoleId, pUserId);
  }

  @Override
  public UserGuide getUserGuide(Integer pNavigationId) {
    return getManager().getUserGuide(pNavigationId);
  }
}
