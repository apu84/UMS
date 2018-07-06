package org.ums.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.dto.library.ImprintDto;
import org.ums.domain.model.immutable.library.Record;
import org.ums.domain.model.mutable.library.MutableRecord;
import org.ums.enums.common.Language;
import org.ums.enums.library.GeneralMaterialDescription;
import org.ums.enums.library.JournalFrequency;
import org.ums.enums.library.MaterialType;
import org.ums.enums.library.RecordStatus;
import org.ums.manager.library.PublisherManager;
import org.ums.manager.library.RecordManager;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Ifti on 19-Feb-17.
 */
@Component
public class RecordBuilder implements Builder<Record, MutableRecord> {

  @Autowired
  RecordManager mRecordManager;

  @Autowired
  PublisherManager mPublisherManager;

  @Override
  public void build(final JsonObjectBuilder pBuilder, final Record pReadOnly, UriInfo pUriInfo,
      final LocalCache pLocalCache) {
    pBuilder.add("mfnNo", pReadOnly.getId().toString());
    pBuilder.add("language", pReadOnly.getLanguage() == null ? 101101 : pReadOnly.getLanguage().getId());
    pBuilder.add("materialType", pReadOnly.getMaterialType() == null ? 101101 : pReadOnly.getMaterialType().getId());
    pBuilder.add("materialTypeName", pReadOnly.getMaterialType() == null ? "" : pReadOnly.getMaterialType().getLabel());
    pBuilder.add("status", pReadOnly.getRecordStatus() == null ? 101101 : pReadOnly.getRecordStatus().getId());
    pBuilder.add("title", pReadOnly.getTitle());
    pBuilder.add("subTitle", pReadOnly.getSubTitle() == null ? "" : pReadOnly.getSubTitle());
    pBuilder.add("gmd", pReadOnly.getGmd() == null ? 101101 : pReadOnly.getGmd().getId());
    pBuilder.add("edition", pReadOnly.getEdition() == null ? "" : pReadOnly.getEdition());
    pBuilder.add("seriesTitle", pReadOnly.getSeriesTitle() == null ? "" : pReadOnly.getSeriesTitle());
    pBuilder.add("volumeNo", pReadOnly.getVolumeNo() == null ? "" : pReadOnly.getVolumeNo());
    pBuilder.add("volumeTitle", pReadOnly.getVolumeTitle() == null ? "" : pReadOnly.getVolumeTitle());
    pBuilder.add("changedTitle", pReadOnly.getChangedTitle() == null ? "" : pReadOnly.getChangedTitle());
    pBuilder.add("libraryLacks", pReadOnly.getLibraryLacks() == null ? "" : pReadOnly.getLibraryLacks());
    pBuilder.add("isbn", pReadOnly.getIsbn() == null ? "" : pReadOnly.getIsbn());
    pBuilder.add("issn", pReadOnly.getIssn() == null ? "" : pReadOnly.getIssn());
    pBuilder.add("corpAuthorMain", pReadOnly.getCorpAuthorMain() == null ? "" : pReadOnly.getCorpAuthorMain());
    pBuilder.add("corpSubBody", pReadOnly.getCorpSubBody() == null ? "" : pReadOnly.getCorpSubBody());
    pBuilder.add("corpCityCountry", pReadOnly.getCorpCityCountry() == null ? "" : pReadOnly.getCorpCityCountry());
    pBuilder.add("translateTitleEdition",
        pReadOnly.getTranslateTitleEdition() == null ? "" : pReadOnly.getTranslateTitleEdition());
    pBuilder.add("callNo", pReadOnly.getCallNo() == null ? "" : pReadOnly.getCallNo());
    pBuilder.add("callYear", pReadOnly.getCallYear());
    pBuilder.add("classNo", pReadOnly.getClassNo() == null ? "" : pReadOnly.getClassNo());
    pBuilder.add("callEdition", pReadOnly.getCallEdition() == null ? "" : pReadOnly.getCallEdition());
    pBuilder.add("callVolume", pReadOnly.getCallVolume() == null ? "" : pReadOnly.getCallVolume());
    pBuilder.add("authorMark", pReadOnly.getAuthorMark() == null ? "" : pReadOnly.getAuthorMark());
    pBuilder.add("keywords", pReadOnly.getKeyWords() == null ? "" : pReadOnly.getKeyWords());
    pBuilder.add("contributorJsonString",
        pReadOnly.getContributorJsonString() == null ? "" : pReadOnly.getContributorJsonString());
    pBuilder.add("subjectJsonString", pReadOnly.getSubjectJsonString() == null ? "" : pReadOnly.getSubjectJsonString());
    pBuilder.add("noteJsonString", pReadOnly.getNoteJsonString() == null ? "" : pReadOnly.getNoteJsonString());
    pBuilder.add("physicalDescriptionString",
        pReadOnly.getPhysicalDescriptionString() == null ? "" : pReadOnly.getPhysicalDescriptionString());
    JsonObjectBuilder object = Json.createObjectBuilder();
    object.add("placeOfPublication",
        (pReadOnly.getImprint() == null || pReadOnly.getImprint().getPlaceOfPublication() == null) ? "" : pReadOnly
            .getImprint().getPlaceOfPublication());
    object.add("yearOfPublication",
        (pReadOnly.getImprint() == null || pReadOnly.getImprint().getYearOfPublication() == null) ? 0 : pReadOnly
            .getImprint().getYearOfPublication());
    object.add("yearOfCopyRight",
        (pReadOnly.getImprint() == null || pReadOnly.getImprint().getYearOfCopyRight() == null) ? 0 : pReadOnly
            .getImprint().getYearOfCopyRight());
    object.add("publisher", (pReadOnly.getImprint() == null || pReadOnly.getImprint().getPublisherId() == null) ? ""
        : pReadOnly.getImprint().getPublisherId().toString());
    object.add("publisherName", (pReadOnly.getImprint() == null || pReadOnly.getImprint().getPublisher() == null) ? ""
        : pReadOnly.getImprint().getPublisher().getName());
    pBuilder.add("imprint", object);
    pBuilder.add("totalItems", pReadOnly.getTotalItems());
    pBuilder.add("totalAvailable", pReadOnly.getTotalAvailable());
    pBuilder.add("totalCheckedOut", pReadOnly.getTotalCheckedOut());
    pBuilder.add("totalOnHold", pReadOnly.getTotalOnHold());

  }

  @Override
  public void build(final MutableRecord pMutable, final JsonObject pJsonObject, final LocalCache pLocalCache) {
    try {
      if(pJsonObject.containsKey("frequency"))
        pMutable.setFrequency(pJsonObject.getInt("frequency") == 101101 ? null : JournalFrequency.get(pJsonObject
            .getInt("frequency")));
    } catch(Exception ex) {
      pMutable.setFrequency(null);
    }
    pMutable.setLanguage(Language.get(pJsonObject.getInt("language")));
    pMutable.setTitle(pJsonObject.getString("title"));
    if(pJsonObject.containsKey("subTitle"))
      pMutable.setSubTitle(pJsonObject.getString("subTitle"));

    if(pJsonObject.containsKey("gmd"))
      pMutable.setGmd(GeneralMaterialDescription.get(pJsonObject.getInt("gmd")));
    if(pJsonObject.containsKey("seriesTitle"))
      pMutable.setSeriesTitle(pJsonObject.getString("seriesTitle"));
    if(pJsonObject.containsKey("volumeNo"))
      pMutable.setVolumeNo(pJsonObject.getString("volumeNo"));
    if(pJsonObject.containsKey("volumeTitle"))
      pMutable.setVolumeTitle(pJsonObject.getString("volumeTitle"));

    if(pJsonObject.containsKey("serialIssueNo"))
      pMutable.setSerialIssueNo(pJsonObject.getString("serialIssueNo"));
    if(pJsonObject.containsKey("serialNumber"))
      pMutable.setSerialNumber(pJsonObject.getString("serialNumber"));
    if(pJsonObject.containsKey("serialSpecial"))
      pMutable.setSerialSpecial(pJsonObject.getString("serialSpecial"));
    if(pJsonObject.containsKey("libraryLacks"))
      pMutable.setLibraryLacks(pJsonObject.getString("libraryLacks"));
    if(pJsonObject.containsKey("changedTitle"))
      pMutable.setChangedTitle(pJsonObject.getString("changedTitle"));
    if(pJsonObject.containsKey("isbn"))
      pMutable.setIsbn(pJsonObject.getString("isbn"));
    if(pJsonObject.containsKey("issn"))
      pMutable.setIssn(pJsonObject.getString("issn"));
    if(pJsonObject.containsKey("corpAuthorMain"))
      pMutable.setCorpAuthorMain(pJsonObject.getString("corpAuthorMain"));
    if(pJsonObject.containsKey("corpSubBody"))
      pMutable.setCorpSubBody(pJsonObject.getString("corpSubBody"));
    if(pJsonObject.containsKey("corpCityCountry"))
      pMutable.setCorpCityCountry(pJsonObject.getString("corpCityCountry"));
    if(pJsonObject.containsKey("edition"))
      pMutable.setEdition(pJsonObject.getString("edition"));
    if(pJsonObject.containsKey("translateTitleEdition"))
      pMutable.setTranslateTitleEdition(pJsonObject.getString("translateTitleEdition"));

    pMutable.setCallNo(pJsonObject.getString("callNo"));
    pMutable.setClassNo(pJsonObject.getString("classNo"));
    if(pJsonObject.containsKey("callYear"))
      pMutable.setCallYear(pJsonObject.getInt("callYear"));
    pMutable.setAuthorMark(pJsonObject.getString("authorMark"));
    if(pJsonObject.containsKey("callEdition"))
      pMutable.setCallEdition(pJsonObject.getString("callEdition"));
    if(pJsonObject.containsKey("callVolume"))
      pMutable.setCallVolume(pJsonObject.getString("callVolume"));

    ImprintDto imprintDto = new ImprintDto();
    JsonObject imprintObject = pJsonObject.getJsonObject("imprint");
    if(imprintObject.containsKey("publisher") && !imprintObject.getString("publisher").equals("0")
        && !imprintObject.getString("publisher").equals(""))
      imprintDto.setPublisher(mPublisherManager.get(Long.valueOf(imprintObject.getString("publisher"))));

    if(imprintObject.containsKey("placeOfPublication"))
      imprintDto.setPlaceOfPublication(imprintObject.getString("placeOfPublication"));
    if(imprintObject.containsKey("yearOfPublication"))
      imprintDto.setYearOfPublication(imprintObject.getInt("yearOfPublication"));
    if(imprintObject.containsKey("yearOfCopyRight"))
      imprintDto.setYearOfCopyRight(imprintObject.getInt("yearOfCopyRight"));

    pMutable.setImprint(imprintDto);
    if(pJsonObject.containsKey("materialType"))
      pMutable.setMaterialType(MaterialType.get(pJsonObject.getInt("materialType")));
    if(pJsonObject.containsKey("status"))
      pMutable.setRecordStatus(RecordStatus.get(pJsonObject.getInt("status")));
    if(pJsonObject.containsKey("keywords"))
      pMutable.setKeyWords(pJsonObject.getString("keywords"));
    if(pJsonObject.containsKey("contributorJsonString"))
      pMutable.setContributorJsonString(pJsonObject.getString("contributorJsonString"));
    pMutable.setSubjectJsonString(pJsonObject.getString("subjectJsonString"));
    pMutable.setNoteJsonString(pJsonObject.getString("noteJsonString"));
    if(pJsonObject.containsKey("physicalDescriptionString"))
      pMutable.setPhysicalDescriptionString(pJsonObject.getString("physicalDescriptionString"));
  }
}
