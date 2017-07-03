package org.ums.domain.model.immutable.common;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.common.MutableRelationType;

import java.io.Serializable;

public interface RelationType extends Serializable, LastModifier, EditType<MutableRelationType>, Identifier<Integer> {

  String getRelationType();
}
