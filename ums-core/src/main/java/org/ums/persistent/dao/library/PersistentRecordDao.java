package org.ums.persistent.dao.library;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.library.ContributorDaoDecorator;
import org.ums.decorator.library.RecordDaoDecorator;
import org.ums.domain.model.dto.library.ImprintDto;
import org.ums.domain.model.immutable.library.Contributor;
import org.ums.domain.model.immutable.library.Record;
import org.ums.domain.model.mutable.MutableCourse;
import org.ums.domain.model.mutable.MutableSemester;
import org.ums.domain.model.mutable.library.MutableRecord;
import org.ums.enums.common.Language;
import org.ums.enums.library.*;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.common.PersistentCountry;
import org.ums.persistent.model.library.PersistentRecord;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Ifti on 19-Feb-17.
 */
public class PersistentRecordDao extends RecordDaoDecorator {
  static String SELECT_ALL =
      "Select MFN, LANGUAGE, TITLE,    SUB_TITLE, GMD, SERIES_TITLE, VOLUME_NO, VOLUME_TITLE, SERIAL_ISSUE_NO,  "
          + "   SERIAL_NUMBER, SERIAL_SPECIAL, LIBRARY_LACKS, CHANGED_TITLE, ISBN, ISSN,  "
          + "   CORP_AUTH_MAIN, CORP_SUB_BODY, CORP_CITY_COUNTRY, EDITION, TRANS_TITLE_EDITION, FREQUENCY,  "
          + "   CALL_NO, CLASS_NO, CALL_DATE, AUTHOR_MARK, PUBLISHER, PLACE_OF_PUBLICATION,  "
          + "   DATE_YEAR_OF_PUBLICATION, COPY_RIGHT_DATE, MATERIAL_TYPE, STATUS, BINDING_TYPE, ACQUISITION_TYPE,  "
          + "   KEYWORDS, DOCUMENTALIST, ENTRY_DATE, LAST_UPDATED_ON, LAST_UPDATED_BY,  CONTRIBUTORS, SUBJECTS, NOTES, LAST_MODIFIED "
          + "FROM RECORDS ";

  static String UPDATE_ONE =
      "UPDATE RECORDS SET LANGUAGE = ?, TITLE=?,  SUB_TITLE=?, GMD=?, SERIES_TITLE=?, VOLUME_NO=?, VOLUME_TITLE=?, SERIAL_ISSUE_NO=?, SERIAL_NUMBER=?, "
          + "   SERIAL_SPECIAL=?, LIBRARY_LACKS=?, CHANGED_TITLE=?, ISBN=?, ISSN=?, CORP_AUTH_MAIN=?, CORP_SUB_BODY=?, CORP_CITY_COUNTRY=?, EDITION=?, TRANS_TITLE_EDITION=?, "
          + "   FREQUENCY=?, CALL_NO=?, CLASS_NO=?, CALL_DATE=to_date(?,'dd-MM-YYYY'),  AUTHOR_MARK=?, PUBLISHER=?, PLACE_OF_PUBLICATION=?,DATE_YEAR_OF_PUBLICATION=?, "
          + "  COPY_RIGHT_DATE=to_date(?,'dd-MM-YYYY'), MATERIAL_TYPE=?, "
          + "  STATUS=?, BINDING_TYPE=?, ACQUISITION_TYPE=?,  KEYWORDS=?, CONTRIBUTORS=?, SUBJECTS=?, NOTES=?, LAST_UPDATED_ON=sysdate,  "
          + "  LAST_UPDATED_BY=?, LAST_MODIFIED=" + getLastModifiedSql();

  static String INSERT_ONE =
      "INSERT INTO RECORDS(MFN, LANGUAGE, TITLE, SUB_TITLE, GMD, SERIES_TITLE,  VOLUME_NO, VOLUME_TITLE, SERIAL_ISSUE_NO,SERIAL_NUMBER, "
          + "   SERIAL_SPECIAL, LIBRARY_LACKS, CHANGED_TITLE, ISBN, ISSN, CORP_AUTH_MAIN, CORP_SUB_BODY, CORP_CITY_COUNTRY, EDITION, TRANS_TITLE_EDITION, "
          + "   FREQUENCY, CALL_NO, CLASS_NO, CALL_DATE,  AUTHOR_MARK, PUBLISHER, PLACE_OF_PUBLICATION,DATE_YEAR_OF_PUBLICATION, COPY_RIGHT_DATE, MATERIAL_TYPE, "
          + "    STATUS, BINDING_TYPE, ACQUISITION_TYPE,  KEYWORDS, DOCUMENTALIST,CONTRIBUTORS, SUBJECTS, NOTES,  ENTRY_DATE, LAST_UPDATED_ON, LAST_UPDATED_BY, LAST_MODIFIED)  "
          + "  VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,"
          + " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
          + " ?, ?, to_date(?,'dd-MM-YYYY'), ?, ?, ?, ?, to_date(?,'dd-MM-YYYY'), ?, ?, "
          + " ?, ?, ?, ?, ?, ?, ?, "
          + " sysdate, sysdate, ?, " + getLastModifiedSql() + ")";

  private JdbcTemplate mJdbcTemplate;
  public IdGenerator mIdGenerator;

  public PersistentRecordDao(final JdbcTemplate pJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public Record get(final Long pId) {
    String query = SELECT_ALL + " Where MFN = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new PersistentRecordDao.RecordRowMapper());
  }

  @Override
  public int update(final MutableRecord pRecord) {
    String query = UPDATE_ONE + "   Where MFN= ? ";
    return mJdbcTemplate.update(query, pRecord.getLanguage().getId(), pRecord.getTitle(), pRecord.getSubTitle(),
        pRecord.getGmd(), pRecord.getSeriesTitle(), pRecord.getVolumeNo(), pRecord.getVolumeTitle(), pRecord
            .getSerialIssueNo(), pRecord.getSerialNumber(), pRecord.getSerialSpecial(), pRecord.getLibraryLacks(),
        pRecord.getChangedTitle(), pRecord.getIsbn(), pRecord.getIssn(), pRecord.getCorpAuthorMain(), pRecord
            .getCorpSubBody(), pRecord.getCorpCityCountry(), pRecord.getEdition(), pRecord.getTranslateTitleEdition(),
        pRecord.getFrequency() == null ? Types.NULL : pRecord.getFrequency().getId(), pRecord.getCallNo(), pRecord
            .getClassNo(), pRecord.getCallDate(), pRecord.getAuthorMark(), pRecord.getImprint().getPublisher().getId(),
        pRecord.getImprint().getPlaceOfPublication(), pRecord.getImprint().getDateOfPublication(), pRecord.getImprint()
            .getCopyRightDate(), pRecord.getMaterialType().getId(), pRecord.getRecordStatus().getId(), pRecord
            .getBookBindingType().getId(), pRecord.getAcquisitionType().getId(), pRecord.getKeyWords(), pRecord
            .getContributorJsonString(), pRecord.getSubjectJsonString(), pRecord.getNoteJsonString(), pRecord
            .getLastUpdatedBy(), pRecord.getMfn());
  }

  @Override
  public Long create(final MutableRecord pRecord) {
    Long id = mIdGenerator.getNumericId();
    pRecord.setMfn(id);
    mJdbcTemplate.update(INSERT_ONE, pRecord.getMfn(), pRecord.getLanguage().getId(), pRecord.getTitle(), pRecord
        .getSubTitle(), pRecord.getGmd(), pRecord.getSeriesTitle(), pRecord.getVolumeNo(), pRecord.getVolumeTitle(),
        pRecord.getSerialIssueNo(), pRecord.getSerialNumber(), pRecord.getSerialSpecial(), pRecord.getLibraryLacks(),
        pRecord.getChangedTitle(), pRecord.getIsbn(), pRecord.getIssn(), pRecord.getCorpAuthorMain(), pRecord
            .getCorpSubBody(), pRecord.getCorpCityCountry(), pRecord.getEdition(), pRecord.getTranslateTitleEdition(),
        pRecord.getFrequency() == null ? Types.NULL : pRecord.getFrequency().getId(), pRecord.getCallNo(), pRecord
            .getClassNo(), pRecord.getCallDate(), pRecord.getAuthorMark(), pRecord.getImprint().getPublisher().getId(),
        pRecord.getImprint().getPlaceOfPublication(), pRecord.getImprint().getDateOfPublication(), pRecord.getImprint()
            .getCopyRightDate(), pRecord.getMaterialType().getId(), pRecord.getRecordStatus().getId(), pRecord
            .getBookBindingType().getId(), pRecord.getAcquisitionType().getId(), pRecord.getKeyWords(), pRecord
            .getDocumentalist(), pRecord.getContributorJsonString(), pRecord.getSubjectJsonString(), pRecord
            .getNoteJsonString(), pRecord.getLastUpdatedBy());

    return id;
  }

  @Override
  public List<Record> getAll() {
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query, new PersistentRecordDao.RecordRowMapper());
  }

  class RecordRowMapper implements RowMapper<Record> {
    @Override
    public Record mapRow(ResultSet resultSet, int i) throws SQLException {

      PersistentRecord record = new PersistentRecord();
      record.setId(resultSet.getLong("MFN"));
      record.setMfn(resultSet.getLong("MFN"));
      record.setLanguage(Language.get(resultSet.getInt("LANGUAGE")));
      record.setTitle(resultSet.getString("TITLE"));
      record.setSubTitle(resultSet.getString("SUB_TITLE"));
      record.setGmd(resultSet.getString("GMD"));
      record.setSeriesTitle(resultSet.getString("SERIES_TITLE"));

      record.setVolumeNo(resultSet.getString("VOLUME_NO"));
      record.setVolumeTitle(resultSet.getString("VOLUME_TITLE"));
      record.setSerialIssueNo(resultSet.getString("SERIAL_ISSUE_NO"));
      record.setSerialNumber(resultSet.getString("SERIAL_NUMBER"));
      record.setSerialSpecial(resultSet.getString("SERIAL_SPECIAL"));
      record.setLibraryLacks(resultSet.getString("LIBRARY_LACKS"));
      record.setChangedTitle(resultSet.getString("CHANGED_TITLE"));
      record.setIsbn(resultSet.getString("ISBN"));
      record.setIssn(resultSet.getString("ISSN"));
      record.setCorpAuthorMain(resultSet.getString("CORP_AUTH_MAIN"));
      record.setCorpSubBody(resultSet.getString("CORP_SUB_BODY"));
      record.setCorpCityCountry(resultSet.getString("CORP_CITY_COUNTRY"));
      record.setEdition(resultSet.getString("EDITION"));
      record.setTranslateTitleEdition(resultSet.getString("TRANS_TITLE_EDITION"));
      record.setFrequency(JournalFrequency.get(resultSet.getInt("FREQUENCY")));
      record.setCallNo(resultSet.getString("CALL_NO"));
      record.setClassNo(resultSet.getString("CLASS_NO"));
      record.setCallDate(resultSet.getString("CALL_DATE"));
      record.setAuthorMark(resultSet.getString("AUTHOR_MARK"));

      ImprintDto imprintDto = new ImprintDto();
      record.setPublisherId(resultSet.getLong("PUBLISHER"));
      imprintDto.setPlaceOfPublication(resultSet.getString("PLACE_OF_PUBLICATION"));
      imprintDto.setDateOfPublication(resultSet.getString("DATE_YEAR_OF_PUBLICATION"));
      imprintDto.setCopyRightDate(resultSet.getString("COPY_RIGHT_DATE"));
      record.setImprint(imprintDto);

      record.setMaterialType(MaterialType.get(resultSet.getInt("MATERIAL_TYPE")));
      record.setRecordStatus(RecordStatus.get(resultSet.getInt("STATUS")));
      record.setBookBindingType(BookBindingType.get(resultSet.getInt("BINDING_TYPE")));
      record.setAcquisitionType(AcquisitionType.get(resultSet.getInt("ACQUISITION_TYPE")));
      record.setKeyWords(resultSet.getString("KEYWORDS"));
      record.setDocumentalist(resultSet.getString("DOCUMENTALIST"));
      record.setEntryDate(resultSet.getString("ENTRY_DATE"));

      record.setLastUpdatedOn(resultSet.getString("LAST_UPDATED_ON"));
      record.setLastUpdatedBy(resultSet.getString("LAST_UPDATED_BY"));
      record.setLastModified(resultSet.getString("LAST_MODIFIED"));

      record.setContributorJsonString(resultSet.getString("CONTRIBUTORS"));
      record.setSubjectJsonString(resultSet.getString("SUBJECTS"));
      record.setNoteJsonString(resultSet.getString("NOTES"));

      AtomicReference<Record> atomicReference = new AtomicReference<>(record);
      return atomicReference.get();
    }
  }

}
