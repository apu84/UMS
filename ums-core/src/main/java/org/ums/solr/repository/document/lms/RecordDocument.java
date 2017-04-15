package org.ums.solr.repository.document.lms;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;
import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.immutable.Employee;
import org.ums.domain.model.immutable.library.Record;
import org.ums.enums.library.MaterialType;
import org.ums.manager.library.PublisherManager;
import org.ums.solr.repository.document.SearchDocument;

/**
 * Created by Ifti on 15-Apr-17.
 */
@SolrDocument(solrCoreName = "ums")
public class RecordDocument implements SearchDocument<String> {

  @Autowired
  PublisherManager mPublisherManager;

  @Id
  @Indexed
  private String id;

  @Field("title_txt")
  private String title;

  @Field("type_s")
  private String type = "Record";

  @Field("materialType_txt")
  private String materialType;

  @Field("seriesTitle_txt")
  private String seriesTitle;

  @Field("volumeNo_txt")
  private String volumeNo;

  @Field("volumeTitle_txt")
  private String volumeTitle;

  @Field("changedTitle_txt")
  private String changedTitle;

  @Field("isbn_txt")
  private String isbn;

  @Field("issn_txt")
  private String issn;

  @Field("corpAuthorMain_txt")
  private String corpAuthorMain;

  @Field("corpAuthorBody_txt")
  private String corpAuthorBody;

  @Field("corpCityCountry_txt")
  private String corpCityCountry;

  @Field("callNo_txt")
  private String callNo;

  @Field("publisher_txt")
  private String publisher;

  @Field("status_txt")
  private String status;

  @Field("bindingType_text")
  private String bindingType;

  @Field("acquisitionType_txt")
  private String acquisitionType;

  @Field("keywords_txt")
  private String keywords;

  @Field("contributors_txt")
  private String contributors;

  @Field("subjects_txt")
  private String subjects;

  public RecordDocument() {}

  public RecordDocument(final Record pRecord) {
    id = pRecord.getMfn().toString();
    title = pRecord.getTitle();
    materialType = MaterialType.get(pRecord.getMaterialType().getId()).getLabel();
    seriesTitle = pRecord.getSeriesTitle();
    volumeTitle = pRecord.getVolumeTitle();
    volumeNo = pRecord.getVolumeNo();
    changedTitle = pRecord.getChangedTitle();
    isbn = pRecord.getIsbn();
    issn = pRecord.getIssn();
    corpAuthorMain = pRecord.getCorpAuthorMain();
    corpAuthorBody = pRecord.getCorpSubBody();
    corpCityCountry = pRecord.getCorpCityCountry();
    callNo = pRecord.getCallNo();
//    publisher = mPublisherManager.get(pRecord.getPublisherId()).getName();
    status = pRecord.getRecordStatus().getLabel();
    bindingType = pRecord.getBookBindingType().getLabel();
    acquisitionType = pRecord.getAcquisitionType().getLabel();
    keywords = pRecord.getKeyWords();
    contributors = pRecord.getContributorJsonString();
    subjects = pRecord.getSubjectJsonString();

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
