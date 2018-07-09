package org.ums.solr.repository.document.lms;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.context.ApplicationContext;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.library.Record;
import org.ums.enums.library.MaterialType;
import org.ums.manager.library.ContributorManager;
import org.ums.manager.library.PublisherManager;
import org.ums.solr.repository.document.SearchDocument;
import org.ums.util.UmsUtils;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Ifti on 15-Apr-17.
 */
@SolrDocument(solrCoreName = "ums")
public class RecordDocument implements SearchDocument<String> {
  public static final String DOCUMENT_TYPE = "Record";

  private static PublisherManager sPublisherManager;
  private static ContributorManager sContributorManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sPublisherManager = applicationContext.getBean("publisherManager", PublisherManager.class);
    sContributorManager = applicationContext.getBean("contributorManager", ContributorManager.class);
  }

  @Id
  @Field
  private String id;

  @Field("language_s")
  private String language;

  @Field("title_t")
  private ArrayList<String> title;

  @Field("alphaNumericTitle_s")
  private String alphaNumericTitle;

  @Field("subTitle_t")
  private ArrayList<String> subTitle;

  @Field("type_s")
  private String type = DOCUMENT_TYPE;

  @Field("materialType_s")
  private String materialType;

  @Field("seriesTitle_t")
  private ArrayList<String> seriesTitle;

  @Field("corpAuthorMain_s")
  private String corpAuthorMain;

  @Field("callNo_s")
  private String callNo;

  @Field("publisher_txt")
  private ArrayList<String> publishers;

  @Field("status_s")
  private String status;

  @Field("contributors_txt")
  private ArrayList<String> contributors;

  @Field("subjects_txt")
  private ArrayList<String> subjects;

  @Field("yearOfPublication_i")
  private int yearOfPublication;

  private String[] contributorArray;

  /*
   * @Field(child = true) private List<ContributorDocument> contributorDocument;
   */

  public RecordDocument() {}

  public RecordDocument(final Record pRecord) {
    id = pRecord.getId().toString();
    if(pRecord.getLanguage() != null) {
      language = pRecord.getLanguage().getLabel();
    }
    title = new ArrayList<String>(Arrays.asList(pRecord.getTitle()));
    alphaNumericTitle = pRecord.getTitle().replaceAll("[^a-zA-Z0-9]+", "");
    if(pRecord.getSubTitle() != null) {
      subTitle = new ArrayList<String>(Arrays.asList(pRecord.getSubTitle()));
    }
    materialType = MaterialType.get(pRecord.getMaterialType().getId()).getLabel();
    if(pRecord.getSeriesTitle() != null) {
      seriesTitle = new ArrayList<String>(Arrays.asList(pRecord.getSeriesTitle()));
    }
    corpAuthorMain = pRecord.getCorpAuthorMain() == null ? "" : pRecord.getCorpAuthorMain();
    callNo = pRecord.getCallNo();
    status = pRecord.getRecordStatus().getLabel();
    subjects =
        new ArrayList<String>(Arrays.asList(UmsUtils.convertJsonStringToStringArray(pRecord.getSubjectJsonString(),
            "subject")));

    if(pRecord.getContributorJsonString() != null) {
      contributorArray = UmsUtils.convertJsonStringToStringArray(pRecord.getContributorJsonString(), "id");

      for(int i = 0; i < contributorArray.length; i++) {
        contributorArray[i] = (sContributorManager.get(Long.parseLong(contributorArray[i])).getFullName());
      }

      contributors = new ArrayList<String>(Arrays.asList(contributorArray));
    }

    if(pRecord.getImprint().getPublisherId() != null && pRecord.getImprint().getPublisherId() != 0) {
      publishers =
          new ArrayList<String>(Arrays.asList((sPublisherManager.get(pRecord.getImprint().getPublisherId()).getName())));
    }

    yearOfPublication =
        pRecord.getImprint().getYearOfPublication() == null ? 0 : pRecord.getImprint().getYearOfPublication();

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
