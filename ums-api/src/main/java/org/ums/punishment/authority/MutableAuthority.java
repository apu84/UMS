package org.ums.punishment.authority;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.mutable.MutableLastModifier;

public interface MutableAuthority extends Authority, Editable<Long>, MutableLastModifier, MutableIdentifier<Long> {
}
