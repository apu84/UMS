package org.ums.twofa;

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
import org.springframework.scheduling.annotation.Async;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.ums.configuration.UMSConfiguration;
import org.ums.domain.model.dto.OtpEmailDto;
import org.ums.formatter.DateFormat;
import org.ums.util.Constants;

public class TwoFATokenEmailSender {

  @Autowired
  @Qualifier("genericDateFormat12")
  DateFormat mDateFormat;
  @Autowired
  private JavaMailSender mailSender;
  @Autowired
  private VelocityEngine velocityEngine;

  public TwoFATokenEmailSender() {}

  @Async
  public void sendEmail(final String otp, final Date expireDate, final String toEmail, final String fromEmail,
      final String subject) {
    MimeMessagePreparator preparator = new MimeMessagePreparator() {
      public void prepare(MimeMessage mimeMessage) {
        MimeMessageHelper message = null;
        try {
          message = new MimeMessageHelper(mimeMessage, true);
          message.setTo(toEmail);
          message.setFrom(new InternetAddress(fromEmail, "IUMS"));
          message.setSubject(subject);

          OtpEmailDto model = new OtpEmailDto(otp, mDateFormat.format(expireDate));
          Map<String, Object> map = new HashMap<>();
          map.put("others", model);

          String body =
              VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "html-templates/otp-email.vm", "UTF-8", map);
          message.setText(body, true);

        } catch(MessagingException | UnsupportedEncodingException e) {
          throw new RuntimeException(e);
        }
      }
    };
    this.mailSender.send(preparator);
  }
}
