package org.ums.filter;

import java.util.Date;
import java.util.Optional;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.Validate;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.StringUtils;
import org.ums.message.MessageResource;
import org.ums.services.PasswordResetEmailService;
import org.ums.token.TokenBuilder;
import org.ums.usermanagement.user.MutableUser;
import org.ums.usermanagement.user.User;
import org.ums.usermanagement.user.UserManager;

public class ForgotPassword extends AbstractPathMatchingFilter {
  @Autowired
  private UserManager mUserManager;

  @Autowired
  private MessageResource mMessageResource;

  @Autowired
  TokenBuilder mTokenBuilder;

  @Autowired
  PasswordResetEmailService mPasswordResetEmailService;

  @Autowired
  @Qualifier("emailSender")
  String mEmailSender;

  @Override
  protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
    JSONObject jsonObject = getRequestJson((HttpServletRequest) request);
    Validate.notNull(jsonObject.get("recoverBy"), "Recover by is empty");
    String recoverBy = jsonObject.get("recoverBy").toString();

    User user = null;
    if(recoverBy.equals("userId")) {
      if(jsonObject.get("userId") == null || !mUserManager.exists(jsonObject.get("userId").toString())) {
        return sendError(mMessageResource.getMessage("user.not.exists"), response);
      }
      user = mUserManager.get(jsonObject.get("userId").toString());
    }
    else if(recoverBy.equals("email")) {
      Validate.notNull(jsonObject.get("email"), "Email is empty");
      String email = jsonObject.get("email").toString();
      Optional<User> userOptional = mUserManager.getByEmail(email);
      if(!userOptional.isPresent()) {
        return sendError(mMessageResource.getMessage("email.not.exists"), response);
      }
      user = userOptional.get();
    }

    if(user != null && !StringUtils.isEmpty(user.getEmail())) {
      MutableUser mutableUser = user.edit();
      String prToken = mTokenBuilder.passwordResetToken(user.getId());
      mutableUser.setPasswordResetToken(prToken);
      mutableUser.setPasswordTokenGenerateDateTime(new Date());
      mutableUser.update();

      mPasswordResetEmailService.sendEmail(user.getEmail(), mEmailSender,
          mMessageResource.getMessage("reset.password.email.subject"), prToken);
    }
    else {
      return sendError(mMessageResource.getMessage("email.not.exists"), response);
    }
    return false;
  }
}
