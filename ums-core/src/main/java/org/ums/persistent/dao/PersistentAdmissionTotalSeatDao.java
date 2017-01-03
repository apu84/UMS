package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.ums.decorator.AdmissionTotalSeatDaoDecorator;
import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.AdmissionTotalSeat;
import org.ums.domain.model.mutable.MutableAdmissionTotalSeat;
import org.ums.manager.AdmissionTotalSeatManager;

/**
 * Created by Monjur-E-Morshed on 03-Jan-17.
 */
public class PersistentAdmissionTotalSeatDao extends AdmissionTotalSeatDaoDecorator {

  private JdbcTemplate mJdbcTemplate;

  public PersistentAdmissionTotalSeatDao(JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }


}
