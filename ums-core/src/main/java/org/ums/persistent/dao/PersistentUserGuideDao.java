package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.UserGuideDaoDecorator;
import org.ums.domain.model.immutable.UserGuide;
import org.ums.domain.model.mutable.MutableUserGuide;
import org.ums.persistent.model.PersistentUserGuide;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Ifti on 17-Dec-16.
 */
public class PersistentUserGuideDao extends UserGuideDaoDecorator {

  String SELECT_ALL =
      "Select * From USER_GUIDE Where Navigation_Id in "
          + "(     "
          + "-- For Exact Pattern  \n"
          + "Select Navigation_id From MAIN_NAVIGATION Where Permission  "
          + "    in "
          + "    ( "
          + "        WITH DATA AS "
          + "          (  "
          + "            SELECT permissions str from PERMISSIONS where role_id=? "
          + "            Union "
          + "            SELECT permissions str from PERMISSIONS where role_id in "
          + "            (Select role_id From ADDITIONAL_ROLE_PERMISSIONS Where User_Id='eee_iums' and Valid_From>=sysdate and Valid_To<=sysdate) "
          + "            Union "
          + "            SELECT permissions str from ADDITIONAL_ROLE_PERMISSIONS where  User_Id='eee_iums' and Valid_From>=sysdate and Valid_To<=sysdate "
          + "                     "
          + "          ) "
          + "        SELECT trim(regexp_substr(str, '[^,]+', 1, LEVEL)) str "
          + "        FROM DATA "
          + "        CONNECT BY instr(str, ',', 1, LEVEL - 1) > 0 "
          + "    ) "
          + "Union "
          + "-- For Like Pattern \n"
          + "Select navigation_id from MAIN_NAVIGATION Where "
          + "    Permission like "
          + "    ( "
          + "    select REPLACE(str, '*', '%') from ( "
          + "    WITH DATA AS "
          + "                  ( SELECT permissions str from PERMISSIONS where role_id=?  "
          + "                    Union "
          + "                    SELECT permissions str from PERMISSIONS where role_id in "
          + "                    (Select role_id From ADDITIONAL_ROLE_PERMISSIONS Where User_Id=? and Valid_From>=sysdate and Valid_To<=sysdate) "
          + "                    Union "
          + "                    SELECT permissions str from ADDITIONAL_ROLE_PERMISSIONS where  User_Id=? and Valid_From>=sysdate and Valid_To<=sysdate "
          + "                  ) "
          + "                SELECT trim(regexp_substr(str, '[^,]+', 1, LEVEL)) str "
          + "                FROM DATA "
          + "                CONNECT BY instr(str, ',', 1, LEVEL - 1) > 0 "
          + "                ) where str  like '%:*%' " + "    ) " + ") ";

  private JdbcTemplate mJdbcTemplate;

  public PersistentUserGuideDao(JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public List<UserGuide> getUserGuideList(Integer pRoleId, String pUserId) {
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query, new Object[] {pRoleId, pRoleId, pUserId, pUserId},
        new UserGuideRowMapper());
  }

  @Override
  public UserGuide getUserGuide(Integer pNavigationId) {
    String query = "Select * From User_Guide Where Navigation_Id=?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pNavigationId},
        new UserGuideRowMapper());
  }

  private class UserGuideRowMapper implements RowMapper<UserGuide> {
    @Override
    public UserGuide mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableUserGuide userGuide = new PersistentUserGuide();
      userGuide.setId(rs.getInt("ID"));
      userGuide.setGuideId(rs.getInt("ID"));
      userGuide.setNavigationId(rs.getInt("NAVIGATION_ID"));
      userGuide.setManualTitle(rs.getString("MANUAL_TITLE"));
      userGuide.setPdfFilePath(rs.getString("PDF_FILE_PATH"));
      userGuide.setHtmlContent(rs.getString("HTML_CONTENT"));
      userGuide.setViewOrder(rs.getInt("VIEW_ORDER"));
      userGuide.setVisibility(rs.getInt("VISIBILITY"));
      AtomicReference<UserGuide> atomicReference = new AtomicReference<>(userGuide);
      return atomicReference.get();
    }
  }
}
