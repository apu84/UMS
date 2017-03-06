package org.ums.lock;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.mutable.MutableLastModifier;

public interface MutableLock extends Lock, Editable<String>, MutableIdentifier<String>,
    MutableLastModifier {
}
