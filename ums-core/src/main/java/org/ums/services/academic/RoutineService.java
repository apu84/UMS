package org.ums.services.academic;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Iterator;

/**
 * Created by Monjur-E-Morshed on 06-Sep-18.
 */
@Service
public class RoutineService {

  public void extractWorkBook(Workbook pWorkbook, Integer pSemesterId, Integer pProgramId) throws Exception,
      IOException, InvalidFormatException {
    int pWorkbookNumberOfSheets = pWorkbook.getNumberOfSheets();

    for(int i = 0; i < pWorkbookNumberOfSheets; i++) {
      Sheet sheet = pWorkbook.getSheetAt(i);
      System.out.println(sheet.getSheetName() + " ");
    }
  }
}
