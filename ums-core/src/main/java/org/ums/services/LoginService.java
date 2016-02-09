package org.ums.services;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.realm.AuthorizingRealm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.ums.domain.model.dto.ResponseDto;
import org.ums.domain.model.mutable.MutableCourse;
import org.ums.domain.model.mutable.MutableUser;
import org.ums.domain.model.readOnly.User;
import org.ums.manager.ContentManager;
import org.ums.manager.UserManager;
import org.ums.util.Constants;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Service
public class LoginService {
//  @Autowired
//  @Qualifier("jdbcRealm")
//  AuthorizingRealm mAuthenticationRealm;

  @Autowired
  @Qualifier("userManager")
  private UserManager mUserManager;

  @Autowired
  private PasswordService mPasswordService;

  @Autowired
  private EmailService emailService;

    public ResponseDto checkAndSendPasswordResetEmailToUser(final String pUserId) throws  Exception{

      ResponseDto response=new ResponseDto();
      User user =null;
      String token=UUID.randomUUID().toString();
      Date now = new Date();
      Date tokenInvalidDate =null;
      Date tokenEmailInvalidDate =null;

      try{user=mUserManager.get(pUserId);}
        catch(Exception ex){

          response.setCode("KO");
          response.setMessage("UserId does not exist.");
          return response;
        }

      if(user.getPasswordTokenGenerateDateTime()!=null) {
         tokenInvalidDate = new Date(user.getPasswordTokenGenerateDateTime().getTime() + (Constants.PASSWORD_RESET_TOKEN_LIFE * Constants.ONE_MINUTE_IN_MILLIS));
         tokenEmailInvalidDate = new Date(user.getPasswordTokenGenerateDateTime().getTime() + (Constants.PASSWORD_RESET_TOKEN_EMAIL_LIFE * Constants.ONE_MINUTE_IN_MILLIS));
      }

        if(StringUtils.isBlank(user.getPasswordResetToken()) || tokenInvalidDate.after(now) || user.getPasswordTokenGenerateDateTime()==null){
          mUserManager.setPasswordResetToken(mPasswordService.encryptPassword(token),pUserId);
          user=mUserManager.get(pUserId);
        }

      if(user.getPasswordTokenGenerateDateTime()!=null && tokenEmailInvalidDate.before(now)) {
        System.out.println("Token already email. please try again after 5 minutes");
      }
      else {
        System.out.println("Send an password token email again.");
      }
      //ToDo: Need to check whether the user has an email address in the database
      emailService.setUser(user);
      emailService.sendEmail("ifticse_kuet@hotmail.com", "ifticse_kuet@hotmail.com", "Reset Your IUMS Password");

      response.setCode("OK");
      return response;

      }

}
