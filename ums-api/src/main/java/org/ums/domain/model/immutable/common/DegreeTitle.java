package org.ums.domain.model.immutable.common;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.common.MutableDegreeTitle;
import org.ums.enums.common.DegreeLevel;

import java.io.Serializable;

public interface DegreeTitle extends Serializable, LastModifier, EditType<MutableDegreeTitle>, Identifier<Integer> {

  String getTitle();

  DegreeLevel getDegreeLevel();

  Integer getDegreeLevelId();;
}
