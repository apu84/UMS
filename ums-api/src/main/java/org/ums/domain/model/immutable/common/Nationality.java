package org.ums.domain.model.immutable.common;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.common.MutableNationality;

import java.io.Serializable;

public interface Nationality extends Serializable, LastModifier, EditType<MutableNationality>, Identifier<Integer> {

  String getNationality();
}
