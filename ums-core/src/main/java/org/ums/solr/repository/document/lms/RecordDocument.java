package org.ums.solr.repository.document.lms;

import com.google.common.collect.Lists;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;
import org.ums.domain.model.immutable.library.Record;
import org.ums.enums.library.MaterialType;
import org.ums.solr.repository.document.SearchDocument;
import org.ums.util.UmsUtils;

import java.util.List;

/**
 * Created by Ifti on 15-Apr-17.
 */
@SolrDocument(solrCoreName = "ums")
public class RecordDocument implements SearchDocument<String> {
  public static final String DOCUMENT_TYPE = "Record";
  // @Autowired
  // PublisherManager mPublisherManager;

  @Id
  @Field
  private String id;

  @Field("title_txt")
  private List<String> title;

  @Field("cTitle_s")
  private String cTitle;

  @Field("whitespaceRemovedTitle_s")
  private String whitespaceRemovedTitle;

  @Field("onlyAlphaNumericTitle_s")
  private String onlyAlphaNumericTitle;

  @Field("onlyAlphaTitle_s")
  private String onlyAlphaTitle;

  @Field("type_s")
  private String type = DOCUMENT_TYPE;

  @Field("materialType_txt")
  private List<String> materialType;

  @Field("seriesTitle_txt")
  private List<String> seriesTitle;

  @Field("volumeNo_txt")
  private List<String> volumeNo;

  @Field("volumeTitle_txt")
  private List<String> volumeTitle;

  @Field("changedTitle_txt")
  private List<String> changedTitle;

  @Field("isbn_txt")
  private List<String> isbn;

  @Field("issn_txt")
  private List<String> issn;

  @Field("corpAuthorMain_txt")
  private List<String> corpAuthorMain;

  @Field("corpAuthorBody_txt")
  private List<String> corpAuthorBody;

  @Field("corpCityCountry_txt")
  private List<String> corpCityCountry;

  @Field("callNo_txt")
  private List<String> callNo;

  @Field("cCallNo_s")
  private String cCallNo;

  @Field("publisher_txt")
  private List<String> publisher;

  @Field("status_txt")
  private List<String> status;

  @Field("bindingType_txt")
  private List<String> bindingType;

  @Field("keywords_txt")
  private List<String> keywords;

  @Field("contributors_txt")
  private List<String> contributors;

  @Field("subjects_txt")
  private String[] subjects;

  /*
   * @Field(child = true) private List<ContributorDocument> contributorDocument;
   */

  public RecordDocument() {}

  public RecordDocument(final Record pRecord) {
    id = pRecord.getMfn().toString();
    title = Lists.newArrayList(pRecord.getTitle());
    cTitle = pRecord.getTitle();
    whitespaceRemovedTitle = pRecord.getTitle().replaceAll(" ", "");
    onlyAlphaNumericTitle = pRecord.getTitle().replaceAll("[^a-zA-Z0-9]+", "");
    onlyAlphaTitle = pRecord.getTitle().replaceAll("[^a-zA-Z]+", "");
    materialType = Lists.newArrayList(MaterialType.get(pRecord.getMaterialType().getId()).getLabel());
    seriesTitle = Lists.newArrayList(pRecord.getSeriesTitle());
    volumeTitle = Lists.newArrayList(pRecord.getVolumeTitle());
    volumeNo = Lists.newArrayList(pRecord.getVolumeNo());
    changedTitle = Lists.newArrayList(pRecord.getChangedTitle());
    isbn = Lists.newArrayList(pRecord.getIsbn());
    issn = Lists.newArrayList(pRecord.getIssn());
    corpAuthorMain = Lists.newArrayList(pRecord.getCorpAuthorMain());
    corpAuthorBody = Lists.newArrayList(pRecord.getCorpSubBody());
    corpCityCountry = Lists.newArrayList(pRecord.getCorpCityCountry());
    callNo = Lists.newArrayList(pRecord.getCallNo());
    cCallNo = pRecord.getCallNo();
    // publisher = mPublisherManager.get(pRecord.getPublisherId()).getName();
    status = Lists.newArrayList(pRecord.getRecordStatus().getLabel());
    keywords = Lists.newArrayList(pRecord.getKeyWords());

    subjects = UmsUtils.convertJsonStringToStringArray(pRecord.getSubjectJsonString(), "subject");

    /*
     * aa.add(new ContributorDocument("Co-Author", "morshed")); aa.add(new
     * ContributorDocument("Author", "kawsur")); List<ContributorDocument> aa = new ArrayList<>();
     * // String contributorString = "[{\"viewOrder\":1,\"id\":\"2\",\"role\":\"2\"}]";
     * contributorDocument = aa;
     * 
     * // contributors = Lists.newArrayList(pRecord.getContributorJsonString()); contributors =
     * Arrays.asList(new String[] {"kawsur", "morshed"});// Lists.newArrayList({""});
     */
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public String getType() {
    return type;
  }
}
