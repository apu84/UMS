package org.ums.services.academic;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ums.domain.model.mutable.routine.MutableRoutine;
import org.ums.manager.CourseManager;
import org.ums.manager.ProgramManager;
import org.ums.manager.SemesterManager;
import org.ums.manager.routine.RoutineManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 06-Sep-18.
 */
@Service
public class RoutineService {

  @Autowired
  private RoutineManager mRoutineManager;
  @Autowired
  private SemesterManager mSemesterManager;
  @Autowired
  private ProgramManager mProgramManager;
  @Autowired
  private CourseManager mCourseManager;

  public void extractWorkBook(Workbook pWorkbook, Integer pSemesterId, Integer pProgramId) throws Exception,
      IOException, InvalidFormatException {

    List<MutableRoutine> routineList = new ArrayList<>();

    int pWorkbookNumberOfSheets = pWorkbook.getNumberOfSheets();
    // replacing pWorkbookNumberOfSheets with 1 for testing.
    for(int i = 0; i < 1; i++) {
      Sheet sheet = pWorkbook.getSheetAt(i);
      System.out.println(sheet.getSheetName() + " ");
      int year = extractYearFromSheetName(sheet.getSheetName());
      int semester = extractSemesterFromSheetName(sheet.getSheetName());
      String section = extractSectionFromSheetName(sheet.getSheetName());
      String globalRoomNo = extractRoomNumberFromSheetName(sheet.getSheetName());

      // getting the row
      for(Row row : sheet) {
        for(Cell cell : row) {
          extractRoutineInformationFromRow(cell, routineList, year, semester, section, globalRoomNo);
        }
      }
    }
  }

  private String extractRoomNumberFromSheetName(String pSheetName) {
    String[] sheetNameArr = pSheetName.split("-");
    if(sheetNameArr.length == 3)
      return "";
    else
      return sheetNameArr[3];
  }

  private int extractYearFromSheetName(String pSheetName) {
    String[] sheetNameArr = pSheetName.split("-");
    return Integer.parseInt(sheetNameArr[0]);
  }

  private int extractSemesterFromSheetName(String pSheetName) {
    String[] sheetNameArr = pSheetName.split("-");
    return Integer.parseInt(sheetNameArr[1]);
  }

  private String extractSectionFromSheetName(String pSheetName) {
    String[] sheetNameArr = pSheetName.split("-");
    return sheetNameArr[2];
  }

  private void extractRoutineInformationFromRow(Cell pCell, List<MutableRoutine> pRoutineList, int pYear,
      int pSemester, String pSection, String pGlobalRoomNo) {
    if(pCell.getRowIndex() == 0) {

    }
  }
}
