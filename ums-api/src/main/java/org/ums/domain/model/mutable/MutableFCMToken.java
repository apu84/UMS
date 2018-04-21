package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.FCMToken;

import java.util.Date;

public interface MutableFCMToken extends FCMToken, Editable<Long>, MutableLastModifier, MutableIdentifier<Long> {

  void setUserId(final String pUserId);

  void setFCMToken(final String pFCMToken);

  void setTokenDeletedOn(final Date pTokenDeletedOn);
}
