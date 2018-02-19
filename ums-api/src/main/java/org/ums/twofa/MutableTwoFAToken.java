package org.ums.twofa;

import java.util.Date;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.mutable.MutableLastModifier;
import org.ums.usermanagement.user.User;

public interface MutableTwoFAToken extends TwoFAToken, Editable<Long>, MutableIdentifier<Long>, MutableLastModifier {

  void setUser(User pUser);

  void setUserId(String pUserId);

  void setType(String pType);

  void setUsed(boolean pIsUsed);

  void setGeneratedOn(Date pGeneratedOn);

  void setExpiredOn(Date pExpiredOn);

  void setUsedOn(Date pUsedOn);

  void setTryCount(int pTryCount);

  void setOtp(String pOtp);

  void setLastModified(String pLastModified);

}
