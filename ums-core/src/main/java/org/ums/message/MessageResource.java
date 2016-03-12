package org.ums.message;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessageResource implements MessageSourceAware {
  private MessageSource mMessageSource;

  private Locale mDefaultLocale = Locale.US;

  @Override
  public void setMessageSource(MessageSource pMessageSource) {
    mMessageSource = pMessageSource;
  }

  public String getMessage(String code, Object... args) {
    return mMessageSource.getMessage(code, args, mDefaultLocale);
  }
}
