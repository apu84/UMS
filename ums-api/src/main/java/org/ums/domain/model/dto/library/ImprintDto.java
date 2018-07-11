package org.ums.domain.model.dto.library;

import com.google.gson.Gson;
import org.ums.domain.model.immutable.library.Publisher;

import java.io.Serializable;

/**
 * Created by Ifti on 16-Feb-17.
 */
public class ImprintDto implements Serializable {
  private Publisher publisher;
  private Long publisherId;
  private String placeOfPublication;
  private Integer yearOfPublication;
  private Integer yearOfCopyRight;
  private Integer yearOfReprint;

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

  public Integer getYearOfPublication() {
    return yearOfPublication;
  }

  public void setYearOfPublication(Integer yearOfPublication) {
    this.yearOfPublication = yearOfPublication;
  }

  public Integer getYearOfCopyRight() {
    return yearOfCopyRight;
  }

  public void setYearOfCopyRight(Integer yearOfCopyRight) {
    this.yearOfCopyRight = yearOfCopyRight;
  }

  public Long getPublisherId() {
    return publisherId;
  }

  public void setPublisherId(Long publisherId) {
    this.publisherId = publisherId;
  }

  public Integer getYearOfReprint() {
    return yearOfReprint;
  }

  public void setYearOfReprint(Integer yearOfReprint) {
    this.yearOfReprint = yearOfReprint;
  }

  public String toString() {
    Gson gson = new Gson();
    return gson.toJson(this);
  }
}
