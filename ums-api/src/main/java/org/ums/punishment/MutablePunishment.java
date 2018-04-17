package org.ums.punishment;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.mutable.MutableLastModifier;

public interface MutablePunishment extends Punishment, Editable<Long>, MutableLastModifier, MutableIdentifier<Long> {
}
