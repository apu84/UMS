package org.ums.persistent.dao.registrar;

import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.springframework.jdbc.core.JdbcTemplate;
import org.ums.decorator.registrar.ServiceInformationDetailDaoDecorator;
import org.ums.persistent.model.registrar.PersistentServiceInformationDetail;

public class PersistentServiceInformationDetailDao extends ServiceInformationDetailDaoDecorator {

  private JdbcTemplate mJdbcTemplate;

  public PersistentServiceInformationDetailDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }
}
