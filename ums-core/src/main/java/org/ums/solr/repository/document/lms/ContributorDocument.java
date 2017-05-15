package org.ums.solr.repository.document.lms;

import com.google.common.collect.Lists;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.immutable.library.Contributor;
import org.ums.enums.library.ContributorRole;

import java.util.List;
import java.util.UUID;

/**
 * Created by Ifti on 23-Apr-17.
 */
public class ContributorDocument {
  @Id
  @Field
  private String id;
  @Field("roleName_txt")
  private List<String> roleName;
  @Field("contributorName_txt")
  private List<String> contributorName;

  public ContributorDocument() {}

  // public ContributorDocument(final ContributorRole pRole, final Contributor pContributor) {
  public ContributorDocument(final String pRole, final String pContributor) {
    UUID uuid = UUID.randomUUID();

    String randomUUIDString = uuid.toString();
    id = randomUUIDString;
    roleName = Lists.newArrayList(pRole);
    contributorName = Lists.newArrayList(pContributor);
  }
}
