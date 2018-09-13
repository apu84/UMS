package org.ums.domain.model.dto.library;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Ifti on 29-Apr-17.
 */
public class FilterDto {

  private String searchType;
  private String basicQueryField;
  private String basicQueryTerm;
  private List<HashMap<String, String>> advancedSearchFilter;

  public String getSearchType() {
    return searchType;
  }

  public void setSearchType(String searchType) {
    this.searchType = searchType;
  }

  public String getBasicQueryField() {
    return basicQueryField;
  }

  public void setBasicQueryField(String basicQueryField) {
    this.basicQueryField = basicQueryField;
  }

  public String getBasicQueryTerm() {
    return basicQueryTerm;
  }

  public void setBasicQueryTerm(String basicQueryTerm) {
    this.basicQueryTerm = basicQueryTerm;
  }

  public List<HashMap<String, String>> getAdvancedSearchFilter() {
    return advancedSearchFilter;
  }

  public void setAdvancedSearchFilter(List<HashMap<String, String>> advancedSearchFilter) {
    this.advancedSearchFilter = advancedSearchFilter;
  }
}
