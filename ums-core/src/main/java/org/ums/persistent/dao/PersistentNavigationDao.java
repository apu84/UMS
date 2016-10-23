package org.ums.persistent.dao;

import com.google.common.collect.Lists;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.ums.persistent.model.PersistentNavigation;
import org.ums.decorator.NavigationDaoDecorator;
import org.ums.domain.model.mutable.MutableNavigation;
import org.ums.domain.model.immutable.Navigation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public class PersistentNavigationDao extends NavigationDaoDecorator {
  String SELECT_ALL =
      "SELECT NAVIGATION_ID, MENU_TITLE, PERMISSION, LOCATION, PARENT_MENU, ICON_IMG_CLASS, ICON_COLOR_CLASS, VIEW_ORDER, STATUS, LAST_MODIFIED FROM MAIN_NAVIGATION ";
  String INSERT_ALL =
      "INSERT INTO MAIN_NAVIGATION (MENU_TITLE, PERMISSION, LOCATION, PARENT_MENU, ICON_IMG_CLASS, ICON_COLOR_CLASS, VIEW_ORDER, STATUS, LAST_MODIFIED) "
          + "VALUES (?, ?, ?, ?, ?, ?,?, ?, " + getLastModifiedSql() + ")";
  String UPDATE_ALL =
      "UPDATE MAIN_NAVIGATION SET MENU_TITLE = ?, PERMISSION = ?, LOCATION = ?, PARENT_MENU = ?, "
          + "ICON_IMG_CLASS=?, ICON_COLOR_CLASS=?,  VIEW_ORDER = ?, STATUS = ?, LAST_MODIFIED = "
          + getLastModifiedSql() + " ";

  String DELETE_ALL = "DELETE FROM MAIN_NAVIGATION ";

  private JdbcTemplate mJdbcTemplate;

  public PersistentNavigationDao(JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public int update(MutableNavigation pMutable) throws Exception {
    String query = UPDATE_ALL + "WHERE NAVIGATION_ID = ?";
    return mJdbcTemplate.update(query, pMutable.getId());
  }

  @Override
  public int delete(MutableNavigation pMutable) throws Exception {
    String query = DELETE_ALL + "WHERE NAVIGATION_ID = ?";
    return mJdbcTemplate.update(query, pMutable.getId());
  }

  @Override
  public int create(MutableNavigation pMutable) throws Exception {
    return mJdbcTemplate.update(INSERT_ALL);
  }

  @Override
  public Navigation get(Integer pId) throws Exception {
    String query = SELECT_ALL + "WHERE NAVIGATION_ID = ? ORDER BY VIEW_ORDER";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new NavigationMapper());
  }

  @Override
  public List<Navigation> getAll() throws Exception {
    String query = SELECT_ALL + "ORDER BY PARENT_MENU ASC";
    return mJdbcTemplate.query(query, new NavigationMapper());
  }

  @Override
  public List<Navigation> getByPermissions(Set<String> pPermissions) {
    if(pPermissions.size() > 0) {
      String query =
          SELECT_ALL
              + "WHERE PERMISSION IN (:permissions) ORDER BY PARENT_MENU ASC, VIEW_ORDER ASC";
      NamedParameterJdbcTemplate namedParameterJdbcTemplate =
          new NamedParameterJdbcTemplate(mJdbcTemplate.getDataSource());
      return namedParameterJdbcTemplate.query(query,
          Collections.singletonMap("permissions", pPermissions), new NavigationMapper());
    }
    else {
      return Lists.newArrayList();
    }
  }

  @Override
  public List<Navigation> getByPermissionsId(Set<Integer> pPermissionIds) {
    String query =
        SELECT_ALL
            + "WHERE NAVIGATION_ID IN (:permissions) ORDER BY PARENT_MENU ASC, VIEW_ORDER ASC";
    NamedParameterJdbcTemplate namedParameterJdbcTemplate =
        new NamedParameterJdbcTemplate(mJdbcTemplate.getDataSource());
    return namedParameterJdbcTemplate.query(query,
        Collections.singletonMap("permissions", pPermissionIds), new NavigationMapper());
  }

  class NavigationMapper implements RowMapper<Navigation> {
    @Override
    public Navigation mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableNavigation navigation = new PersistentNavigation();
      navigation.setId(rs.getInt("NAVIGATION_ID"));
      navigation.setTitle(rs.getString("MENU_TITLE"));
      navigation.setPermission(rs.getString("PERMISSION"));
      navigation.setParentId(rs.getInt("PARENT_MENU"));
      navigation.setViewOrder(rs.getInt("VIEW_ORDER"));
      navigation.setLocation(rs.getString("LOCATION"));
      navigation.setIconImgClass(rs.getString("ICON_IMG_CLASS"));
      navigation.setIconColorClass(rs.getString("ICON_COLOR_CLASS"));
      navigation.setActive(rs.getBoolean("STATUS"));
      navigation.setLastModified(rs.getString("LAST_MODIFIED"));
      AtomicReference<Navigation> atomicReference = new AtomicReference<>(navigation);
      return atomicReference.get();
    }
  }
}
