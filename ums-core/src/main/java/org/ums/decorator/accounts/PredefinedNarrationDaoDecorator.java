package org.ums.decorator.accounts;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.accounts.PredefinedNarration;
import org.ums.domain.model.mutable.accounts.MutablePredefinedNarration;
import org.ums.manager.accounts.PredefinedNarrationManager;

/**
 * Created by Monjur-E-Morshed on 12-Jan-18.
 */
public class PredefinedNarrationDaoDecorator
    extends
    ContentDaoDecorator<PredefinedNarration, MutablePredefinedNarration, Long, PredefinedNarrationManager>
    implements
    PredefinedNarrationManager {
}
