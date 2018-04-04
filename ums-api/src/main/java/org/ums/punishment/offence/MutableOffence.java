package org.ums.punishment.offence;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.mutable.MutableLastModifier;

public interface MutableOffence extends Offence, Editable<Long>, MutableLastModifier, MutableIdentifier<Long> {
}
