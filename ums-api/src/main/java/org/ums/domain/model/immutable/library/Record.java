package org.ums.domain.model.immutable.library;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.dto.library.ImprintDto;
import org.ums.domain.model.mutable.library.MutableRecord;
import org.ums.enums.common.Language;
import org.ums.enums.library.GeneralMaterialDescription;
import org.ums.enums.library.JournalFrequency;
import org.ums.enums.library.MaterialType;
import org.ums.enums.library.RecordStatus;

import java.io.Serializable;
import java.util.Date;

public interface Record extends Serializable, EditType<MutableRecord>, LastModifier, Identifier<Long> {

  Language getLanguage();

  String getTitle();

  String getSubTitle();

  GeneralMaterialDescription getGmd();

  String getSeriesTitle();

  String getVolumeNo();

  String getVolumeTitle();

  String getSerialIssueNo();

  String getSerialNumber();

  String getSerialSpecial();

  String getLibraryLacks();

  String getChangedTitle();

  String getIsbn();

  String getIssn();

  String getCorpAuthorMain();

  String getCorpSubBody();

  String getCorpCityCountry();

  String getEdition();

  String getTranslateTitleEdition();

  JournalFrequency getFrequency();

  String getCallNo();

  String getClassNo();

  String getAuthorMark();

  Integer getCallYear();

  String getCallEdition();

  String getCallVolume();

  String getContributorJsonString();

  ImprintDto getImprint();

  String getPhysicalDescriptionString();

  MaterialType getMaterialType();

  RecordStatus getRecordStatus();

  String getKeyWords();

  String getSubjectJsonString();

  String getNoteJsonString();

  Integer getTotalItems();

  Integer getTotalAvailable();

  Integer getTotalCheckedOut();

  Integer getTotalOnHold();

  String getDocumentalist();

  Date getEntryDate();

  Date getLastUpdatedOn();

  String getLastUpdatedBy();
}
