package org.ums.domain.model.immutable.optCourse;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.optCourse.MutableOptOfferedGroupSubGroupMap;

import java.io.Serializable;

/**
 * Created by Monjur-E-Morshed on 9/18/2018.
 */
public interface OptOfferedGroupSubGroupMap extends Serializable, LastModifier,
    EditType<MutableOptOfferedGroupSubGroupMap>, Identifier<Long> {
  Long getId();

  Long getGroupId();

  Long getSubGroupId();

  String getSubGroupName();

  String getGroupName();

}
