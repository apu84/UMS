package org.ums.fee;

public class PersistentFeeCategoryDao extends FeeCategoryDaoDecorator {
  String SELECT_ALL = "SELECT ID, NAME, DESCRIPTION, TYPE, LAST_MODIFIED FROM FEE_CATEGORY ";
  String UPDATE_ALL =
      "UPDATE FEE_CATEGORY SET NAME = ?, DESCRIPTION = ?, TYPE = ?, LAST_MODIFIED = "
          + getLastModifiedSql() + " ";
  String DELETE_ALL = "DELETE FROM FEE_CATEGORY ";
  String INSERT_ALL =
      "INSERT INTO FEE_CATEGORY(ID, NAME, DESCRIPTION, TYPE, LAST_MODIFIED) VALUES (?, ?, ?, ?, "
          + getLastModifiedSql() + ")";
}
