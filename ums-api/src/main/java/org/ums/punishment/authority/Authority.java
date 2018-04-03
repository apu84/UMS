package org.ums.punishment.authority;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;

import java.io.Serializable;

public interface Authority extends Serializable, LastModifier, EditType<MutableAuthority>, Identifier<Long> {
}
