package org.ums.solr.repository.document.lms;

import com.google.common.collect.Lists;
import org.apache.solr.client.solrj.beans.Field;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
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
import org.ums.util.UmsUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ifti on 15-Apr-17.
 */
@SolrDocument(solrCoreName = "ums")
public class RecordDocument implements SearchDocument<String> {

  // @Autowired
  // PublisherManager mPublisherManager;

  @Id
  @Field
  private String id;

  @Field("title_txt")
  private List<String> title;

  @Field("type_s")
  private String type = "Record";

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

  @Field("publisher_txt")
  private List<String> publisher;

  @Field("status_txt")
  private List<String> status;

  @Field("bindingType_txt")
  private List<String> bindingType;

  @Field("acquisitionType_txt")
  private List<String> acquisitionType;

  @Field("keywords_txt")
  private List<String> keywords;

  @Field("contributors_txt")
  private List<String> contributors;

  @Field("subjects_txt")
  private String[] subjects;

  public RecordDocument() {}

  public RecordDocument(final Record pRecord) {
    id = pRecord.getMfn().toString();
    title = Lists.newArrayList(pRecord.getTitle());
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
    // publisher = mPublisherManager.get(pRecord.getPublisherId()).getName();
    status = Lists.newArrayList(pRecord.getRecordStatus().getLabel());
    bindingType = Lists.newArrayList(pRecord.getBookBindingType().getLabel());
    acquisitionType = Lists.newArrayList(pRecord.getAcquisitionType().getLabel());
    keywords = Lists.newArrayList(pRecord.getKeyWords());
    contributors = Lists.newArrayList(pRecord.getContributorJsonString());
    subjects = UmsUtils.convertJsonStringToStringArray(pRecord.getSubjectJsonString(), "subject");
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
