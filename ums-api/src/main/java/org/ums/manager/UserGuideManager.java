package org.ums.manager;

import org.ums.domain.model.immutable.UserGuide;
import org.ums.domain.model.mutable.MutableUserGuide;

import java.util.List;

/**
 * Created by Ifti on 17-Dec-16.
 */
public interface UserGuideManager extends ContentManager<UserGuide, MutableUserGuide, Integer> {

  List<UserGuide> getUserGuideList(Integer pRoleId, String pUserId);

}
