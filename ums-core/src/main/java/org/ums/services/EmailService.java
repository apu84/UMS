package org.ums.services;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.ums.domain.model.dto.ResetPasswordEmailDto;
import org.ums.usermanagement.user.User;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component("emailService")
public class EmailService {

  @Autowired
  private JavaMailSender mailSender;
  @Autowired
  private PasswordService mPasswordService;
  @Autowired
  private VelocityEngine velocityEngine;

  private User user;

  public void setUser(User user) {
    this.user = user;
  }

  public void sendEmail(final String toEmailAddresses, final String fromEmailAddress, final String subject) {
    sendEmail(toEmailAddresses, fromEmailAddress, subject, null, null);
  }

  public void sendEmailWithAttachment(final String toEmailAddresses, final String fromEmailAddress,
      final String subject, final String attachmentPath, final String attachmentName) {
    sendEmail(toEmailAddresses, fromEmailAddress, subject, attachmentPath, attachmentName);
  }

  private void sendEmail(final String toEmailAddresses, final String fromEmailAddress, final String subject,
      final String attachmentPath, final String attachmentName) {
    MimeMessagePreparator preparator = new MimeMessagePreparator() {
      public void prepare(MimeMessage mimeMessage) {
        MimeMessageHelper message = null;
        try {
          message = new MimeMessageHelper(mimeMessage, true);
          message.setTo(toEmailAddresses);
          message.setFrom(new InternetAddress(fromEmailAddress, "IUMS"));
          message.setSubject(subject);

          SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy  HH:mm");// dd/MM/yyyy
          Date now = new Date();
          String strDate = sdfDate.format(now);

          ResetPasswordEmailDto others = new ResetPasswordEmailDto();
          others.setUmsRootUrl("http://iums.aust.edu/ums-web/login");
          others.setUmsForgotPasswordUrl("http://iums.aust.edu/ums-web/login/?forgot-password.ums");
          String abc = "http://iums.aust.edu/ums-web/login/reset-password.html?pr_token=$$TOKEN$$&uid=$$USER_ID$$";
          abc = abc.replace("$$TOKEN$$", user.getPasswordResetToken());
          abc = abc.replace("$$USER_ID$$", user.getId());

          others.setUmsResetPasswordUrl(abc);
          others.setForgotPasswordRequestDateTime(strDate);

          Map model = new HashMap();
          model.put("user", user);
          model.put("others", others);

          String body =
              VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "html-templates/password-reset-email.vm",
                  "UTF-8", model);
          message.setText(body, true);
          if(!StringUtils.isBlank(attachmentPath)) {
            FileSystemResource file = new FileSystemResource(attachmentPath);
            message.addAttachment(attachmentName, file);
          }
        } catch(MessagingException | UnsupportedEncodingException e) {
          throw new RuntimeException(e);
        }
      }
    };
    this.mailSender.send(preparator);
  }

}
