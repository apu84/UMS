package org.ums.manager.accounts;

import org.ums.domain.model.mutable.accounts.MutablePredefinedNarration;
import org.ums.manager.ContentManager;
import org.ums.domain.model.immutable.accounts.PredefinedNarration;

public interface PredefinedNarrationManager extends
    ContentManager<PredefinedNarration, MutablePredefinedNarration, Long> {
}
