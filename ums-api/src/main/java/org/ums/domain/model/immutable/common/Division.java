package org.ums.domain.model.immutable.common;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.common.MutableDivision;

import java.io.Serializable;

public interface Division extends Serializable, LastModifier, EditType<MutableDivision>, Identifier<String> {

  String getDivisionId();

  String getDivisionName();
}
