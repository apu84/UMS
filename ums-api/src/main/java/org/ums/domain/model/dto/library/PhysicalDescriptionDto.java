package org.ums.domain.model.dto.library;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by Ifti on 16-Feb-17.
 */
public class PhysicalDescriptionDto implements Serializable {

  private String pagination;
  private String illustrations;
  private String accompanyingMaterials;
  private String dimensions;
  private String paperQuality;

  public String getPagination() {
    return pagination;
  }

  public void setPagination(String pagination) {
    this.pagination = pagination;
  }

  public String getIllustrations() {
    return illustrations;
  }

  public void setIllustrations(String illustrations) {
    this.illustrations = illustrations;
  }

  public String getAccompanyingMaterials() {
    return accompanyingMaterials;
  }

  public void setAccompanyingMaterials(String accompanyingMaterials) {
    this.accompanyingMaterials = accompanyingMaterials;
  }

  public String getDimensions() {
    return dimensions;
  }

  public void setDimensions(String dimensions) {
    this.dimensions = dimensions;
  }

  public String getPaperQuality() {
    return paperQuality;
  }

  public void setPaperQuality(String paperQuality) {
    this.paperQuality = paperQuality;
  }

  public String toString() {
    Gson gson = new Gson();
    return gson.toJson(this);
  }
}
