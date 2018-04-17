package org.ums.punishment.penalty;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.mutable.MutableLastModifier;

public interface MutablePenalty extends Penalty, Editable<Long>, MutableLastModifier, MutableIdentifier<Long> {
}
