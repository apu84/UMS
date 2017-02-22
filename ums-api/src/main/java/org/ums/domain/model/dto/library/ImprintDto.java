package org.ums.domain.model.dto.library;

import com.google.gson.Gson;
import org.ums.domain.model.immutable.library.Publisher;

import java.io.Serializable;

/**
 * Created by Ifti on 16-Feb-17.
 */
public class ImprintDto implements Serializable {
  private Publisher publisher;
  private String placeOfPublication;
  private String dateOfPublication;
  private String copyRightDate;

  public Publisher getPublisher() {
    return publisher;
  }

  public void setPublisher(Publisher publisher) {
    this.publisher = publisher;
  }

  public String getPlaceOfPublication() {
    return placeOfPublication;
  }

  public void setPlaceOfPublication(String placeOfPublication) {
    this.placeOfPublication = placeOfPublication;
  }

  public String getDateOfPublication() {
    return dateOfPublication;
  }

  public void setDateOfPublication(String dateOfPublication) {
    this.dateOfPublication = dateOfPublication;
  }

  public String getCopyRightDate() {
    return copyRightDate;
  }

  public void setCopyRightDate(String copyRightDate) {
    this.copyRightDate = copyRightDate;
  }

  public String toString() {
    Gson gson = new Gson();
    return gson.toJson(this);
  }
}
