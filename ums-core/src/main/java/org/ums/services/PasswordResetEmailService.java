package org.ums.services;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.ums.domain.model.dto.ResetPasswordEmailDto;
import org.ums.formatter.DateFormat;

@Component("passwordResetEmailService")
public class PasswordResetEmailService {
  @Autowired
  private JavaMailSender mailSender;
  @Autowired
  private VelocityEngine velocityEngine;
  @Autowired
  DateFormat mDateFormat;
  @Autowired
  @Qualifier("host")
  String mHost;

  public void sendEmail(final String userId, final String toEmail, final String fromEmail, final String subject,
      final String token) {
    MimeMessagePreparator preparator = new MimeMessagePreparator() {
      public void prepare(MimeMessage mimeMessage) {
        MimeMessageHelper message = null;
        try {
          message = new MimeMessageHelper(mimeMessage, true);
          message.setTo(toEmail);
          message.setFrom(new InternetAddress(fromEmail, "IUMS"));
          message.setSubject(subject);

          ResetPasswordEmailDto model = new ResetPasswordEmailDto();
          model.setId(userId);
          model.setUmsRootUrl(String.format("%s/ums-web/login", mHost));
          model.setUmsForgotPasswordUrl(String.format("%s/ums-web/login/?forgot-password.ums", mHost));
          String passwordResetUrl = String.format("%s/ums-web/login/reset-password.html?pr_token=$$TOKEN$$", mHost);
          passwordResetUrl = passwordResetUrl.replace("$$TOKEN$$", token);
          model.setUmsResetPasswordUrl(passwordResetUrl);
          model.setForgotPasswordRequestDateTime(mDateFormat.format(new Date()));

          Map<String, Object> map = new HashMap<String, Object>();
          map.put("others", model);

          String body =
              VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "html-templates/password-reset-email.vm",
                  "UTF-8", map);
          message.setText(body, true);
        } catch(MessagingException | UnsupportedEncodingException e) {
          throw new RuntimeException(e);
        }
      }
    };
    this.mailSender.send(preparator);
  }

}
