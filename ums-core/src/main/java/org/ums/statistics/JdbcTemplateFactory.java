package org.ums.statistics;

import org.springframework.jdbc.core.JdbcTemplate;

public interface JdbcTemplateFactory {
  JdbcTemplate getJdbcTemplate();
}
