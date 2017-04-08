package org.ums.fee;

import java.io.Serializable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;

public interface FeeType extends Serializable, EditType<MutableFeeType>, LastModifier, Identifier<Integer> {

  String getDescription();
}
