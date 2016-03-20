package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Mutable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.BearerAccessToken;

import java.util.Date;

public interface MutableBearerAccessToken extends Mutable, BearerAccessToken, MutableIdentifier<String> {
  void setUserId(final String pUserId);

  void setLastAccessedTime(final Date pDate);
}
