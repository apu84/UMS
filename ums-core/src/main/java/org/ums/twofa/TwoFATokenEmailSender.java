package org.ums.twofa;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.ums.configuration.UMSConfiguration;

public class TwoFATokenEmailSender {
  private JavaMailSender mailSender;

  private VelocityEngine velocityEngine;

  private UMSConfiguration mUMSConfiguration;

  public TwoFATokenEmailSender(JavaMailSender pMailSender, VelocityEngine pVelocityEngine,
      UMSConfiguration pUMSConfiguration) {
    mailSender = pMailSender;
    velocityEngine = pVelocityEngine;
    mUMSConfiguration = pUMSConfiguration;
  }

  public void sendEmail(final String toEmail, final String fromEmail, final String subject, final String token) {
    MimeMessagePreparator preparator = (MimeMessage mimeMessage) -> {
      MimeMessageHelper message = null;
      try {
        message = new MimeMessageHelper(mimeMessage, true);
        message.setTo(toEmail);
        message.setFrom(new InternetAddress(fromEmail, "IUMS"));
        message.setSubject(subject);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("token", token);
        map.put("expiry", mUMSConfiguration.getTwoFATokenExpiry());

        String body =
            VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "html-templates/two-fa-token.vm", "UTF-8", map);
        message.setText(body, true);
      } catch(MessagingException | UnsupportedEncodingException e) {
        throw new RuntimeException(e);
      }

    };
    this.mailSender.send(preparator);
  }
}
