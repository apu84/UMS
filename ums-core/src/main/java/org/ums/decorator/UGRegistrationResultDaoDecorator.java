package org.ums.decorator;

import org.ums.domain.model.immutable.UGRegistrationResult;
import org.ums.domain.model.mutable.MutableUGRegistrationResult;
import org.ums.manager.UGRegistrationResultManager;

public class UGRegistrationResultDaoDecorator
    extends ContentDaoDecorator<UGRegistrationResult, MutableUGRegistrationResult, Integer, UGRegistrationResultManager>
    implements UGRegistrationResultManager {
}
