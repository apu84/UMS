package org.ums.domain.model;

public interface MutableDepartment extends Mutable, Department, MutableLastModifier, MutableIdentifier<String> {
  void setLongName(final String pLongName) throws Exception;

  void setShortName(final String pShortName) throws Exception;

  void setType(final int pType) throws Exception;
}
