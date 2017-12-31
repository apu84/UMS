package org.ums.twofa;

import java.util.Date;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.mutable.MutableLastModifier;
import org.ums.usermanagement.user.User;

public interface MutableTwoFAToken extends TwoFAToken, Editable<Long>, MutableIdentifier<Long>, MutableLastModifier {

  void setState(String pState);

  void setToken(String pToken);

  void setTokenExpiry(Date pTokenExpiry);

  void setUser(User pUser);

  void setUserId(String pUserId);
}
