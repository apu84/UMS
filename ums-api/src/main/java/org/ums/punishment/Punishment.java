package org.ums.punishment;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;

import java.io.Serializable;

public interface Punishment extends Serializable, LastModifier, EditType<MutablePunishment>, Identifier<Long> {
}
