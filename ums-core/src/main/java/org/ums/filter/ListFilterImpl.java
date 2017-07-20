package org.ums.filter;

public class ListFilterImpl implements ListFilter {
  private String mFilterName;
  private Object mFilterValue;

  public ListFilterImpl(String pFilterName, Object pFilterValue) {
    mFilterName = pFilterName;
    mFilterValue = pFilterValue;
  }

  @Override
  public String getFilterName() {
    return mFilterName;
  }

  @Override
  public Object getFilterValue() {
    return mFilterValue;
  }
}
