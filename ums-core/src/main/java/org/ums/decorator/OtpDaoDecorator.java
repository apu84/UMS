package org.ums.decorator;

import org.ums.domain.model.immutable.Otp;
import org.ums.domain.model.mutable.MutableOtp;
import org.ums.manager.OtpManager;

public class OtpDaoDecorator extends ContentDaoDecorator<Otp, MutableOtp, Long, OtpManager> implements OtpManager {

  @Override
  public Otp get(String pUserId, String pType, String pOtp) {
    return getManager().get(pUserId, pType, pOtp);
  }

  @Override
  public Otp get(String pUserId, String pType) {
    return getManager().get(pUserId, pType);
  }
}
