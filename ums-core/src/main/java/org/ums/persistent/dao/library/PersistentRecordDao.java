package org.ums.persistent.dao.library;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.library.RecordDaoDecorator;
import org.ums.domain.model.dto.library.ImprintDto;
import org.ums.domain.model.immutable.library.Record;
import org.ums.domain.model.mutable.library.MutableRecord;
import org.ums.enums.common.Language;
import org.ums.enums.library.GeneralMaterialDescription;
import org.ums.enums.library.JournalFrequency;
import org.ums.enums.library.MaterialType;
import org.ums.enums.library.RecordStatus;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.library.PersistentRecord;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Ifti on 19-Feb-17.
 */
public class PersistentRecordDao extends RecordDaoDecorator {
  static String SELECT_ALL =
      "Select MFN, LANGUAGE, TITLE, SUB_TITLE, GMD, SERIES_TITLE, VOLUME_NO, VOLUME_TITLE, SERIAL_ISSUE_NO,  "
          + "   SERIAL_NUMBER, SERIAL_SPECIAL, LIBRARY_LACKS, CHANGED_TITLE, ISBN, ISSN,  "
          + "   CORP_AUTH_MAIN, CORP_SUB_BODY, CORP_CITY_COUNTRY, EDITION, TRANS_TITLE_EDITION, FREQUENCY,  "
          + "   CALL_NO, CLASS_NO, CALL_YEAR, CALL_EDITION, CALL_VOLUME, AUTHOR_MARK, PUBLISHER, PLACE_OF_PUBLICATION,  "
          + "   YEAR_OF_PUBLICATION, YEAR_OF_COPY_RIGHT, YEAR_OF_REPRINT, MATERIAL_TYPE, STATUS,  "
          + "   KEYWORDS, DOCUMENTALIST, ENTRY_DATE, LAST_UPDATED_ON, LAST_UPDATED_BY, CONTRIBUTORS,PHYSICAL_DESC, SUBJECTS, "
          + " NOTES, TOTAL_ITEMS, TOTAL_AVAILABLE, TOTAL_CHECKED_OUT, TOTAL_ON_HOLD, LAST_MODIFIED " + "FROM RECORDS ";

  static String UPDATE_ONE =
      "UPDATE RECORDS SET LANGUAGE = ?, TITLE=?,  SUB_TITLE=?, GMD=?, SERIES_TITLE=?, VOLUME_NO=?, VOLUME_TITLE=?, SERIAL_ISSUE_NO=?, SERIAL_NUMBER=?, "
          + "   SERIAL_SPECIAL=?, LIBRARY_LACKS=?, CHANGED_TITLE=?, ISBN=?, ISSN=?, CORP_AUTH_MAIN=?, CORP_SUB_BODY=?, CORP_CITY_COUNTRY=?, EDITION=?, TRANS_TITLE_EDITION=?, "
          + "   FREQUENCY=?, CALL_NO=?, CLASS_NO=?, CALL_YEAR=?, CALL_EDITION=?, CALL_VOLUME=?, AUTHOR_MARK=?, PUBLISHER=?, PLACE_OF_PUBLICATION=?,YEAR_OF_PUBLICATION=?, "
          + "  YEAR_OF_COPY_RIGHT=?, YEAR_OF_REPRINT=?, MATERIAL_TYPE=?, "
          + "  STATUS=?, KEYWORDS=?, CONTRIBUTORS=?, SUBJECTS=?, PHYSICAL_DESC = ?, NOTES=?, TOTAL_ITEMS = ?, TOTAL_AVAILABLE = ?,"
          + " TOTAL_CHECKED_OUT = ?, TOTAL_ON_HOLD = ?, LAST_UPDATED_ON=sysdate"
          + ",  "
          + "  LAST_UPDATED_BY=?, LAST_MODIFIED=" + getLastModifiedSql();

  static String INSERT_ONE =
      "INSERT INTO RECORDS(MFN, LANGUAGE, TITLE, SUB_TITLE, GMD, SERIES_TITLE,  VOLUME_NO, VOLUME_TITLE, SERIAL_ISSUE_NO,SERIAL_NUMBER, "
          + "   SERIAL_SPECIAL, LIBRARY_LACKS, CHANGED_TITLE, ISBN, ISSN, CORP_AUTH_MAIN, CORP_SUB_BODY, CORP_CITY_COUNTRY, EDITION, TRANS_TITLE_EDITION, "
          + "   FREQUENCY, CALL_NO, CLASS_NO, CALL_YEAR, CALL_EDITION, CALL_VOLUME,  AUTHOR_MARK, PUBLISHER, PLACE_OF_PUBLICATION,YEAR_OF_PUBLICATION, YEAR_OF_COPY_RIGHT, YEAR_OF_REPRINT, MATERIAL_TYPE, "
          + "    STATUS, KEYWORDS, DOCUMENTALIST,CONTRIBUTORS, SUBJECTS,PHYSICAL_DESC, NOTES, TOTAL_ITEMS, TOTAL_AVAILABLE, TOTAL_CHECKED_OUT, TOTAL_ON_HOLD,  ENTRY_DATE, LAST_UPDATED_ON, LAST_UPDATED_BY, LAST_MODIFIED)  "
          + "  VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,"
          + " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
          + " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
          + " ?, ?, ?, ?, ?, ?, ?, 0, 0, 0, 0,"
          + " "
          + "sysdate "
          + ", "
          + "sysdate , ?, " + getLastModifiedSql() + ")";

  static String DELETE_ONE = "DELETE FROM RECORDS ";

  static String GET_MFN = "SELECT SQN_RECORD.NEXTVAL FROM DUAL";

  private JdbcTemplate mJdbcTemplate;
  public IdGenerator mIdGenerator;

  public PersistentRecordDao(final JdbcTemplate pJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public Record get(final Long pId) {
    String query = SELECT_ALL + " WHERE MFN = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new PersistentRecordDao.RecordRowMapper());
  }

  @Override
  public int update(final MutableRecord pRecord) {
    String query = UPDATE_ONE + "   WHERE MFN= ? ";
    return mJdbcTemplate.update(query, pRecord.getLanguage() == null ? null : pRecord.getLanguage().getId(), pRecord
        .getTitle(), pRecord.getSubTitle(), pRecord.getGmd() == null ? null : pRecord.getGmd().getId(), pRecord
        .getSeriesTitle(), pRecord.getVolumeNo(), pRecord.getVolumeTitle(), pRecord.getSerialIssueNo(), pRecord
        .getSerialNumber(), pRecord.getSerialSpecial(), pRecord.getLibraryLacks(), pRecord.getChangedTitle(), pRecord
        .getIsbn(), pRecord.getIssn(), pRecord.getCorpAuthorMain(), pRecord.getCorpSubBody(), pRecord
        .getCorpCityCountry(), pRecord.getEdition(), pRecord.getTranslateTitleEdition(),
        pRecord.getFrequency() == null ? null : pRecord.getFrequency().getId(), pRecord.getCallNo(), pRecord
            .getClassNo(), pRecord.getCallYear(), pRecord.getCallEdition(), pRecord.getCallVolume(), pRecord
            .getAuthorMark(), pRecord.getImprint().getPublisher() == null ? null : pRecord.getImprint().getPublisher()
            .getId(), pRecord.getImprint().getPlaceOfPublication(), pRecord.getImprint().getYearOfPublication(),
        pRecord.getImprint().getYearOfCopyRight(), pRecord.getImprint().getYearOfReprint(),
        pRecord.getMaterialType() == null ? null : pRecord.getMaterialType().getId(),
        pRecord.getRecordStatus() == null ? null : pRecord.getRecordStatus().getId(), pRecord.getKeyWords(), pRecord
            .getContributorJsonString(), pRecord.getSubjectJsonString(), pRecord.getPhysicalDescriptionString(),
        pRecord.getNoteJsonString(), pRecord.getTotalItems(), pRecord.getTotalAvailable(),
        pRecord.getTotalCheckedOut(), pRecord.getTotalOnHold(),
        pRecord.getLastUpdatedBy() == null ? "" : pRecord.getLastUpdatedBy(), pRecord.getId());
  }

  private Long getMfn() {
    return mJdbcTemplate.queryForObject(GET_MFN, Long.class);
  }

  @Override
  public Long create(final MutableRecord pRecord) {
    pRecord.setId(getMfn());
    mJdbcTemplate.update(INSERT_ONE, pRecord.getId(), pRecord.getLanguage().getId(), pRecord.getTitle(), pRecord
        .getSubTitle(), pRecord.getGmd().getId(), pRecord.getSeriesTitle(), pRecord.getVolumeNo(), pRecord
        .getVolumeTitle(), pRecord.getSerialIssueNo(), pRecord.getSerialNumber(), pRecord.getSerialSpecial(), pRecord
        .getLibraryLacks(), pRecord.getChangedTitle(), pRecord.getIsbn(), pRecord.getIssn(), pRecord
        .getCorpAuthorMain(), pRecord.getCorpSubBody(), pRecord.getCorpCityCountry(), pRecord.getEdition(), pRecord
        .getTranslateTitleEdition(), pRecord.getFrequency() == null ? null : pRecord.getFrequency().getId(), pRecord
        .getCallNo(), pRecord.getClassNo(), pRecord.getCallYear(), pRecord.getCallEdition(), pRecord.getCallVolume(),
        pRecord.getAuthorMark(), pRecord.getImprint().getPublisher() == null ? null : pRecord.getImprint()
            .getPublisher().getId(), pRecord.getImprint().getPlaceOfPublication(), pRecord.getImprint()
            .getYearOfPublication(), pRecord.getImprint().getYearOfCopyRight(),
        pRecord.getImprint().getYearOfReprint(), pRecord.getMaterialType().getId(), pRecord.getRecordStatus().getId(),
        pRecord.getKeyWords(), pRecord.getDocumentalist(), pRecord.getContributorJsonString(), pRecord
            .getSubjectJsonString(), pRecord.getPhysicalDescriptionString(), pRecord.getNoteJsonString(), pRecord
            .getLastUpdatedBy());

    return pRecord.getId();
  }

  @Override
  public List<Record> getAll() {
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query, new PersistentRecordDao.RecordRowMapper());
  }

  @Override
  public int delete(final MutableRecord pRecord) {
    String query = DELETE_ONE + " WHERE MFN = ?";
    return mJdbcTemplate.update(query, pRecord.getId());
  }

  class RecordRowMapper implements RowMapper<Record> {
    @Override
    public Record mapRow(ResultSet resultSet, int i) throws SQLException {

      PersistentRecord record = new PersistentRecord();
      record.setId(resultSet.getLong("MFN"));
      record.setLanguage(Language.get(resultSet.getInt("LANGUAGE")));
      record.setTitle(resultSet.getString("TITLE"));
      record.setSubTitle(resultSet.getString("SUB_TITLE"));
      record.setGmd(GeneralMaterialDescription.get(resultSet.getInt("GMD")));
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
      record.setCallYear(resultSet.getInt("CALL_YEAR"));
      record.setCallEdition(resultSet.getString("CALL_EDITION"));
      record.setCallVolume(resultSet.getString("CALL_VOLUME"));
      record.setAuthorMark(resultSet.getString("AUTHOR_MARK"));
      ImprintDto imprintDto = new ImprintDto();
      imprintDto.setPublisherId(resultSet.getLong("PUBLISHER"));
      imprintDto.setPlaceOfPublication(resultSet.getString("PLACE_OF_PUBLICATION"));
      imprintDto.setYearOfPublication(resultSet.getInt("YEAR_OF_PUBLICATION"));
      imprintDto.setYearOfCopyRight(resultSet.getInt("YEAR_OF_COPY_RIGHT"));
      imprintDto.setYearOfReprint(resultSet.getInt("YEAR_OF_REPRINT"));
      record.setImprint(imprintDto);
      record.setPhysicalDescriptionString(resultSet.getString("PHYSICAL_DESC"));
      record.setMaterialType(MaterialType.get(resultSet.getInt("MATERIAL_TYPE")));
      record.setRecordStatus(RecordStatus.get(resultSet.getInt("STATUS")));
      record.setKeyWords(resultSet.getString("KEYWORDS"));
      record.setContributorJsonString(resultSet.getString("CONTRIBUTORS"));
      record.setPhysicalDescriptionString(resultSet.getString("PHYSICAL_DESC"));
      record.setSubjectJsonString(resultSet.getString("SUBJECTS"));
      record.setNoteJsonString(resultSet.getString("NOTES"));
      record.setTotalItems(resultSet.getInt("TOTAL_ITEMS"));
      record.setTotalAvailable(resultSet.getInt("TOTAL_AVAILABLE"));
      record.setTotalCheckedOut(resultSet.getInt("TOTAL_CHECKED_OUT"));
      record.setTotalOnHold(resultSet.getInt("TOTAL_ON_HOLD"));
      record.setDocumentalist(resultSet.getString("DOCUMENTALIST"));
      record.setEntryDate(resultSet.getDate("ENTRY_DATE"));
      record.setLastUpdatedOn(resultSet.getDate("LAST_UPDATED_ON"));
      record.setLastUpdatedBy(resultSet.getString("LAST_UPDATED_BY"));
      record.setLastModified(resultSet.getString("LAST_MODIFIED"));

      AtomicReference<Record> atomicReference = new AtomicReference<>(record);
      return atomicReference.get();
    }
  }

}
