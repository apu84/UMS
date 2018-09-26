package org.ums.domain.model.mutable.optCourse;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.optCourse.OptOfferedGroupSubGroupMap;
import org.ums.domain.model.mutable.MutableLastModifier;

/**
 * Created by Monjur-E-Morshed on 9/18/2018.
 */
public interface MutableOptOfferedGroupSubGroupMap extends OptOfferedGroupSubGroupMap, Editable<Long>,
    MutableLastModifier, MutableIdentifier<Long> {
  void setId(Long pId);

  void setGroupId(Long pGroupId);

  void setSubGroupId(Long pSubGroupId);

  void setSubGroupName(String pSubGroupName);

  void setGroupName(String pGroupName);
}
