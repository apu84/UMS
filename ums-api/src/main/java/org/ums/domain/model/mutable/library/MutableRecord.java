package org.ums.domain.model.mutable.library;

import org.ums.domain.model.common.Mutable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.dto.library.ImprintDto;
import org.ums.domain.model.dto.library.PhysicalDescriptionDto;
import org.ums.domain.model.immutable.library.*;
import org.ums.domain.model.mutable.MutableLastModifier;
import org.ums.enums.common.Language;
import org.ums.enums.library.*;

import java.util.List;

/**
 * Created by Ifti on 16-Feb-17.
 */
public interface MutableRecord extends Record, Mutable, MutableLastModifier, MutableIdentifier<Long> {

  void setMfn(Long pMfn);

  void setLanguage(Language pLanguage);

  void setTitle(String pTitle);

  void setSubTitle(String pSubTitle);

  void setGmd(String pGmd);

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

  void setCallDate(String pCallDate);

  void setAuthorMark(String pAuthorMark);

  void setContributorList(List<MaterialContributor> pContributorList);

  void setContributorJsonString(String pContributorJsonString);

  void setImprint(ImprintDto pImprint);

  void setPhysicalDescription(PhysicalDescriptionDto pPhysicalDescription);

  void setMaterialType(MaterialType pMaterialType);

  void setRecordStatus(RecordStatus pRecordStatus);

  void setBookBindingType(BookBindingType pBookBindingType);

  void setAcquisitionType(AcquisitionType pAcquisitionType);

  void setKeyWords(String pKeywords);

  void setSubjectList(List<Subject> pSubjectList);

  void setSubjectJsonString(String pSubjectJsonString);

  void setNoteList(List<Note> pNoteList);

  void setNoteJsonString(String pNoteJsonString);

  void setDocumentalist(String pDocumentalist);

  void setEntryDate(String pEntryDate);

  void setLastUpdatedOn(String pLastUpdatedOn);

  void setLastUpdatedBy(String pLastUpdatedBy);

}
