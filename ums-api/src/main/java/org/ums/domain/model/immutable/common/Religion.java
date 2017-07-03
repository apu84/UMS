package org.ums.domain.model.immutable.common;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.common.MutableReligion;

import java.io.Serializable;

public interface Religion extends Serializable, LastModifier, EditType<MutableReligion>, Identifier<Integer> {

  String getReligion();
}
