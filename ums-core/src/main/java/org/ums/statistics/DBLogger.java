package org.ums.statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DBLogger implements QueryLogger {
  public static String QUERY_PARAM_PLACE_HOLDER = "\\?";
  @Autowired
  JdbcTemplate mJdbcTemplate;

  @Override
  public void log(String pQuery, Object[] pQueryParams, String pUserName) {

  }

  @Override
  public void log(String pQuery, String pUserName) {

  }

  protected String buildQuery(String pQuery, final Object[] pQueryParams) {
    if (pQuery.contains(QUERY_PARAM_PLACE_HOLDER)
        && pQueryParams.length > 0) {
      for (Object param : pQueryParams) {
        pQuery = pQuery.replaceFirst(QUERY_PARAM_PLACE_HOLDER, isString(param) ? "'" + param.toString() + "'" : param.toString());
      }
    }

    return pQuery;
  }

  private boolean isNumber(Object pObject) {
    return pObject instanceof Number;
  }

  private boolean isString(Object pObject) {
    return pObject instanceof String;
  }

}
