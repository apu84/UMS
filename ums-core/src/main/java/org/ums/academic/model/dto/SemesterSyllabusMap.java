package org.ums.academic.model.dto;

import org.ums.domain.model.dto.MutableSemesterSyllabusMapDto;
import org.ums.domain.model.dto.SemesterSyllabusMapDto;
import org.ums.domain.model.regular.Program;
import org.ums.domain.model.regular.Semester;

/**
 * Created by User on 1/16/2016.
 */
public class SemesterSyllabusMap implements MutableSemesterSyllabusMapDto, SemesterSyllabusMapDto {

  private Semester mSemester;
  private Semester mCopySemester;
  private Program mProgram;

  @Override
  public void setAcademicSemester(Semester semester) {
    mSemester = semester;
  }

  @Override
  public void setProgram(Program program) {
    mProgram = program;
  }

  @Override
  public void setCopySemester(Semester pSemester) throws Exception {
    mCopySemester = pSemester;
  }

  @Override
  public Semester getCopySemester() {
    return mCopySemester;
  }

  @Override
  public Semester getAcademicSemester() {
    return mSemester;
  }

  @Override
  public Program getProgram() {
    return mProgram;
  }
}
