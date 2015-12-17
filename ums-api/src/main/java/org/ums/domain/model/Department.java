package org.ums.domain.model;


import java.io.Serializable;

public interface Department extends Serializable {
  int getId() throws Exception;

  String getShortName() throws Exception;

  String getLongName() throws Exception;

  int getType() throws Exception;
}
