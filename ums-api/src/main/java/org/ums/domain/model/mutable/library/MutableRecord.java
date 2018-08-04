package org.ums.domain.model.mutable.library;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.dto.library.ImprintDto;
import org.ums.domain.model.immutable.library.Record;
import org.ums.domain.model.mutable.MutableLastModifier;
import org.ums.enums.common.Language;
import org.ums.enums.library.GeneralMaterialDescription;
import org.ums.enums.library.JournalFrequency;
import org.ums.enums.library.MaterialType;
import org.ums.enums.library.RecordStatus;

import java.util.Date;

/**
 * Created by Ifti on 16-Feb-17.
 */
public interface MutableRecord extends Record, Editable<Long>, MutableLastModifier, MutableIdentifier<Long> {

  void setLanguage(Language pLanguage);

  void setTitle(String pTitle);

  void setSubTitle(String pSubTitle);

  void setGmd(GeneralMaterialDescription pGmd);

  void setSeriesTitle(String pSeriesTitle);

  void setVolumeNo(String pVolumeNo);

  void setVolumeTitle(String pVolumeTitle);

  void setSerialIssueNo(String pSerialIssueNo);

  void setSerialNumber(String pSerialNumber);

  void setSerialSpecial(String pSerialSpecial);

  void setLibraryLacks(String pLibraryLacks);

  void setChangedTitle(String pChangedTitle);

  void setIsbn(String pIsbn);

  void setIssn(String pIssn);

  void setCorpAuthorMain(String pCorpAuthorMain);

  void setCorpSubBody(String pCorpSubBody);

  void setCorpCityCountry(String pCorpCitCountry);

  void setEdition(String pEdition);

  void setTranslateTitleEdition(String pTranslateTitleEdition);

  void setFrequency(JournalFrequency pFrequency);

  void setCallNo(String pCallNo);

  void setClassNo(String pClassNo);

  void setAuthorMark(String pAuthorMark);

  void setCallYear(Integer pCallDate);

  void setCallEdition(String pCallEdition);

  void setCallVolume(String pCallVolume);

  void setContributorJsonString(String pContributorJsonString);

  void setImprint(ImprintDto pImprint);

  void setPhysicalDescriptionString(String pPhysicalDescriptionString);

  void setMaterialType(MaterialType pMaterialType);

  void setRecordStatus(RecordStatus pRecordStatus);

  void setKeyWords(String pKeywords);

  void setSubjectJsonString(String pSubjectJsonString);

  void setNoteJsonString(String pNoteJsonString);

  void setTotalItems(Integer pTotalItems);

  void setTotalAvailable(Integer pTotalAvailable);

  void setTotalCheckedOut(Integer pTotalCheckedOut);

  void setTotalOnHold(Integer pTotalOnHold);

  void setDocumentalist(String pDocumentalist);

  void setEntryDate(Date pEntryDate);

  void setLastUpdatedOn(Date pLastUpdatedOn);

  void setLastUpdatedBy(String pLastUpdatedBy);

}
