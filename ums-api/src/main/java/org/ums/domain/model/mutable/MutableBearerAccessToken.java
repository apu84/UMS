package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.BearerAccessToken;
import org.ums.usermanagement.user.User;

import java.util.Date;

public interface MutableBearerAccessToken extends Editable<String>, BearerAccessToken, MutableIdentifier<String>,
    MutableLastModifier {
  void setUserId(final String pUserId);

  void setUser(final User pUser);

  void setLastAccessedTime(final Date pDate);

  void setRefreshToken(final String pRefreshToken);
}
