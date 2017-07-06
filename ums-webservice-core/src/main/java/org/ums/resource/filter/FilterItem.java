package org.ums.resource.filter;

import java.util.ArrayList;
import java.util.List;

public class FilterItem {
  private String mLabel;
  private String mValue;
  private Type mType;
  private List<FilterItemOption> mOptions;

  public FilterItem(String pLabel, String pValue, Type pType) {
    mLabel = pLabel;
    mValue = pValue;
    mType = pType;
    mOptions = new ArrayList<>();
  }

  public String getLabel() {
    return mLabel;
  }

  public String getValue() {
    return mValue;
  }

  public Type getType() {
    return mType;
  }

  public void addOption(String pLabel, Object pValue) {
    mOptions.add(new FilterItemOption(pLabel, pValue));
  }

  public List<FilterItemOption> getOptions() {
    return mOptions;
  }

  public enum Type {
    INPUT,
    SELECT,
    DATE
  }

  public class FilterItemOption {
    private String mLabel;
    private Object mValue;

    FilterItemOption(String pLabel, Object pValue) {
      mLabel = pLabel;
      mValue = pValue;
    }

    public String getLabel() {
      return mLabel;
    }

    public Object getValue() {
      return mValue;
    }
  }
}
