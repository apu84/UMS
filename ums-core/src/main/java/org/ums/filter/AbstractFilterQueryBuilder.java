package org.ums.filter;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractFilterQueryBuilder {
  private List<String> filterList = new ArrayList<>();
  private List<Object> params = new ArrayList<>();

  public AbstractFilterQueryBuilder(List<ListFilter> pFilter) {
    pFilter.forEach((filter) -> {
      getQueryString(filter);
      params.add(filter.getFilterValue());
    });
  }

  public String getQuery() {
    return filterList.size() > 0 ? " WHERE " + StringUtils.join(filterList, " AND ") : "";
  }

  public List<Object> getParameters() {
    return params;
  }

  protected abstract String getQueryString(final ListFilter pFilter);
}
