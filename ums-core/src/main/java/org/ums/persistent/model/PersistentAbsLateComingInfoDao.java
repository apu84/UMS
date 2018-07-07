package org.ums.persistent.model;

import org.springframework.jdbc.core.JdbcTemplate;
import org.ums.decorator.AbsLateComingInfoDaoDecorator;
import org.ums.generator.IdGenerator;

/**
 * Created by Monjur-E-Morshed on 7/1/2018.
 */
public class PersistentAbsLateComingInfoDao extends AbsLateComingInfoDaoDecorator {
  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentAbsLateComingInfoDao(JdbcTemplate pJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

}
