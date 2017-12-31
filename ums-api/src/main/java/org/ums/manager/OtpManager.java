package org.ums.manager;

import org.ums.domain.model.immutable.Otp;
import org.ums.domain.model.mutable.MutableOtp;

public interface OtpManager extends ContentManager<Otp, MutableOtp, Long> {

  Otp get(String pUserId, String pType, String pOtp);

  Otp get(String pUserId, String pType);
}
