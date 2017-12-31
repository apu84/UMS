package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Otp;

import java.util.Date;

public interface MutableOtp extends Otp, Editable<Long>, MutableIdentifier<Long>, MutableLastModifier {

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
