package org.ums.domain.model.dto.notification;

/**
 * Created by Monjur-E-Morshed on 22-Apr-18.
 */
public class NotificationMessage {
  private String from;
  private String text;

  public NotificationMessage() {}

  public NotificationMessage(String pFrom, String pText) {
    from = pFrom;
    text = pText;
  }

  public String getFrom() {
    return from;
  }

  public void setFrom(String pFrom) {
    from = pFrom;
  }

  public String getText() {
    return text;
  }

  public void setText(String pText) {
    text = pText;
  }
}
