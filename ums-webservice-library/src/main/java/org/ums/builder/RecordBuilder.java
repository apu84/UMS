package org.ums.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.dto.library.ImprintDto;
import org.ums.domain.model.immutable.library.Record;
import org.ums.domain.model.mutable.library.MutableRecord;
import org.ums.enums.common.Language;
import org.ums.enums.library.*;
import org.ums.manager.library.PublisherManager;
import org.ums.manager.library.RecordManager;
import org.ums.persistent.model.library.PersistentPublisher;
import org.ums.persistent.model.library.PersistentRecord;

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
    // pBuilder.add("id", pReadOnly.getId());
    // pBuilder.add("firstName", pReadOnly.getFirstName());
    // pBuilder.add("middleName", pReadOnly.getMiddleName());
    // pBuilder.add("lastName", pReadOnly.getLastName());
    // pBuilder.add("shortName", pReadOnly.getShortName());
    // pBuilder.add("gender", pReadOnly.getGender());
    // pBuilder.add("address", pReadOnly.getAddress());
    // pBuilder.add("countryId", pReadOnly.getCountryId());

  }

  @Override
  public void build(final MutableRecord pMutable, final JsonObject pJsonObject, final LocalCache pLocalCache) {

    try {

      pMutable.setBookBindingType(pJsonObject.getInt("bindingType") == 101101 ? null : BookBindingType.get(pJsonObject
          .getInt("bindingType")));
      pMutable.setAcquisitionType(pJsonObject.getInt("acqType") == 101101 ? null : AcquisitionType.get(pJsonObject
          .getInt("acqType")));
      pMutable.setFrequency(pJsonObject.getInt("frequency") == 101101 ? null : JournalFrequency.get(pJsonObject
          .getInt("frequency")));
      pJsonObject.getInt("mfn");
    } catch(Exception ex) {
      pMutable.setFrequency(null);
    }

    pMutable.setLanguage(Language.get(pJsonObject.getInt("language")));
    pMutable.setTitle(pJsonObject.getString("title"));
    pMutable.setSubTitle(pJsonObject.getString("subTitle"));
    pMutable.setGmd(pJsonObject.getString("gmd"));
    pMutable.setSeriesTitle(pJsonObject.getString("seriesTitle"));
    pMutable.setVolumeNo(pJsonObject.getString("volumeNo"));
    pMutable.setVolumeTitle(pJsonObject.getString("volumeTitle"));
    pMutable.setSerialIssueNo(pJsonObject.getString("serialIssueNo"));
    pMutable.setSerialNumber(pJsonObject.getString("serialNumber"));

    pMutable.setSerialSpecial(pJsonObject.getString("serialSpecial"));
    pMutable.setLibraryLacks(pJsonObject.getString("libraryLacks"));
    pMutable.setChangedTitle(pJsonObject.getString("changedTitle"));
    pMutable.setIsbn(pJsonObject.getString("isbn"));
    pMutable.setIssn(pJsonObject.getString("issn"));
    pMutable.setCorpAuthorMain(pJsonObject.getString("corpAuthorMain"));
    pMutable.setCorpSubBody(pJsonObject.getString("corpSubBody"));
    pMutable.setCorpCityCountry(pJsonObject.getString("corpCityCountry"));
    pMutable.setEdition(pJsonObject.getString("edition"));
    pMutable.setTranslateTitleEdition(pJsonObject.getString("translateTitleEdition"));

    pMutable.setCallNo(pJsonObject.getString("callNo"));
    pMutable.setClassNo(pJsonObject.getString("classNo"));
    pMutable.setCallDate(pJsonObject.getString("callDate"));
    pMutable.setAuthorMark(pJsonObject.getString("authorMark"));

    ImprintDto imprintDto = new ImprintDto();
    JsonObject imprintObject = (JsonObject) (pJsonObject.get("imprint"));
    imprintDto.setPublisher(mPublisherManager.get(Long.valueOf(imprintObject.getString("publisher"))));
    imprintDto.setPlaceOfPublication(imprintObject.getString("placeOfPublication"));
    imprintDto.setDateOfPublication(imprintObject.getString("yearDateOfPublication"));
    imprintDto.setCopyRightDate(imprintObject.getString("copyRightDate"));

    pMutable.setImprint(imprintDto);
    pMutable.setMaterialType(MaterialType.get(pJsonObject.getInt("materialType")));
    pMutable.setRecordStatus(RecordStatus.get(pJsonObject.getInt("status")));
    pMutable.setKeyWords(pJsonObject.getString("keywords"));

    if(pJsonObject.containsKey("contributorJsonString"))
      pMutable.setContributorJsonString(pJsonObject.getString("contributorJsonString"));
    pMutable.setSubjectJsonString(pJsonObject.getString("subjectJsonString"));
    pMutable.setNoteJsonString(pJsonObject.getString("noteJsonString"));
  }
}
