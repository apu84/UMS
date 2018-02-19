package org.ums.domain.model.immutable.library;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.dto.library.ImprintDto;
import org.ums.domain.model.dto.library.PhysicalDescriptionDto;
import org.ums.domain.model.mutable.library.MutableRecord;
import org.ums.enums.common.Language;
import org.ums.enums.library.*;

import java.io.Serializable;
import java.util.List;

public interface Record extends Serializable, EditType<MutableRecord>, LastModifier, Identifier<Long> {

  Long getMfn();

  Language getLanguage();

  String getTitle();

  String getSubTitle();

  String getGmd();

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

  String getCallDate();

  String getCallEdition();

  String getCallVolume();

  String getAuthorMark();

  List<MaterialContributor> getContributorList();

  String getContributorJsonString();

  ImprintDto getImprint();

  PhysicalDescriptionDto getPhysicalDescription();

  String getPhysicalDescriptionString();

  MaterialType getMaterialType();

  RecordStatus getRecordStatus();

  BookBindingType getBookBindingType();

  AcquisitionType getAcquisitionType();

  String getKeyWords();

  List<Subject> getSubjectList();

  String getSubjectJsonString();

  List<Note> getNoteList();

  String getNoteJsonString();

  String getDocumentalist();

  String getEntryDate();

  String getLastUpdatedOn();

  Long getPublisherId();

  String getLastUpdatedBy();

  int getTotalItems();

  int getTotalAvailable();

  int getTotalCheckedOut();

  int getTotalOnHold();
}
