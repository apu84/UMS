package org.ums.domain.model.dto;

import com.google.gson.Gson;

/**
 * Created by Ifti on 12-Jun-16.
 */
public class GradeChartDataDto {

  private String gradeLetter;
  private int total;
  private String color;

  public String getGradeLetter() {
    return gradeLetter;
  }

  public void setGradeLetter(String gradeLetter) {
    this.gradeLetter = gradeLetter;
  }

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public String toString() {
    Gson gson = new Gson();
    return gson.toJson(this);
  }
}
