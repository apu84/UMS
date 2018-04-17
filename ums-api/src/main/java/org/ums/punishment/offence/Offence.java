package org.ums.punishment.offence;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;

import java.io.Serializable;

public interface Offence extends Serializable, LastModifier, EditType<MutableOffence>, Identifier<Long> {
}
