package org.ums.punishment.penalty;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;

import java.io.Serializable;

public interface Penalty extends Serializable, LastModifier, EditType<MutablePenalty>, Identifier<Long> {
}
