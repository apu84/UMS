package org.ums.academic.dao;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created by Ifti on 06-Mar-16.
 */
  public class PersistentOptionalCourseOfferDao extends OptionalCourseOfferDaoDecorator {
  private JdbcTemplate mJdbcTemplate;

  public PersistentOptionalCourseOfferDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

}
