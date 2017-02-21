package org.ums.domain.model.dto.library;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by Ifti on 16-Feb-17.
 */
public class PhysicalDescriptionDto implements Serializable {

  public String toString() {
    Gson gson = new Gson();
    return gson.toJson(this);
  }
}
