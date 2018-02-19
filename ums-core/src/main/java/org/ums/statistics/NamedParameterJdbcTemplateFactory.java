package org.ums.statistics;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * Created by Monjur-E-Morshed on 24-Dec-17.
 */
public interface NamedParameterJdbcTemplateFactory {
  NamedParameterJdbcTemplate getNamedParameterJdbcTemplate();

  NamedParameterJdbcTemplate getLmsNamedParameterJdbcTemplate();

  NamedParameterJdbcTemplate getAccountNamedParameterJdbcTemplate();
}
