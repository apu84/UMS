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
import java.util.Date;

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

    public String checkAndSendPasswordResetEmailToUser(final String pUserId) throws  Exception{

      User user =null;
      Date now = new Date();

      try{user=mUserManager.get(pUserId);}
        catch(Exception ex){}

      Date tokenInvalidDate = new Date(user.getPasswordTokenGenerateDateTime().getTime()+ (Constants.PASSWORD_RESET_TOKEN_LIFE * Constants.ONE_MINUTE_IN_MILLIS));
      Date tokenEmailInvalidDate = new Date(user.getPasswordTokenGenerateDateTime().getTime()+ (Constants.PASSWORD_RESET_TOKEN_EMAIL_LIFE* Constants.ONE_MINUTE_IN_MILLIS));

      ResponseDto response=new ResponseDto();
        if(StringUtils.isBlank(user.getPasswordResetToken()) || tokenInvalidDate.after(now)){
          mUserManager.setPasswordResetToken("token",pUserId);
          user=mUserManager.get(pUserId);
        }

      if(tokenEmailInvalidDate.before(now)) {
        System.out.println("Token already email. please try again after 5 minutes");
      }
      else {
        System.out.println("Send an password token email again.");
      }

      emailService.sendEmail("ifticse_kuet@hotmail.com", "ifticse_kuet@hotmail.com", "Reset Your IUMS Password");

      //if(user.getPasswordTokenEmailSendDateTime())


        //if(user.getPasswordResetToken()!=null && user.isPasswordResetTokenValid()){


       // }
//    if(user==null){
//          //Set proper message with 200 response code and KO status . STATUS_CODE,STATUS_MESSAGE,RESPONSE TYPE 200;
//          return Response.status(Response.Status.NOT_FOUND).build();
//        }
//        return null;
        return user.getPasswordTokenGenerateDateTime().toString();
      }

}
