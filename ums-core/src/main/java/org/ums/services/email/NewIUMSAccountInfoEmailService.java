package org.ums.services.email;

import org.apache.commons.collections.map.HashedMap;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.ums.domain.model.dto.NewIUMSAccountDto;
import org.ums.formatter.DateFormat;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@Component("newIUMSAccountInfoEmailService")
public class NewIUMSAccountInfoEmailService {

  @Autowired
  private JavaMailSender mailSender;

  @Autowired
  private VelocityEngine velocityEngine;

  @Autowired
  @Qualifier("genericDateFormat")
  DateFormat mDateFormat;

  @Autowired
  @Qualifier("host")
  String mHost;

  @Async
  public void sendEmail(final String name, final String userId, final String password, final String toEmail,
      final String fromEmail, final String subject) {
    MimeMessagePreparator preparator = new MimeMessagePreparator() {
      @Override
      public void prepare(MimeMessage mimeMessage) throws Exception {
        MimeMessageHelper messageHelper = null;

        try {
          messageHelper = new MimeMessageHelper(mimeMessage, true);
          messageHelper.setTo(toEmail);
          messageHelper.setFrom(new InternetAddress(fromEmail, "IUMS"));
          messageHelper.setSubject(subject);

          NewIUMSAccountDto model = new NewIUMSAccountDto(name, userId, password);
          Map<String, Object> map = new HashMap<>();
          map.put("others", model);

          String body =
              VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "html-templates/new-IUMS-account.vm",
                  "UTF-8", map);
          messageHelper.setText(body, true);
        } catch(MessagingException | UnsupportedEncodingException e) {
          throw new RuntimeException(e);
        }
      }
    };
    this.mailSender.send(preparator);
  }
}
