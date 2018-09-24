package org.ums.services.academic.routine.helper;

import com.itextpdf.text.pdf.PdfPTable;
import org.ums.itext.UmsCell;

import java.time.LocalTime;

/**
 * Created by Monjur-E-Morshed on 22-Sep-18.
 */
public class RoutineReportHelper {
  int index;
  PdfPTable table;
  LocalTime startTime;
  UmsCell mUmsCell;
  int alignment;

  public RoutineReportHelper() {}

  public RoutineReportHelper(int pIndex, PdfPTable pTable) {
    index = pIndex;
    table = pTable;
  }

  public UmsCell getUmsCell() {
    return mUmsCell;
  }

  public int getAlignment() {
    return alignment;
  }

  public void setAlignment(int pAlignment) {
    alignment = pAlignment;
  }

  public void setUmsCell(UmsCell pUmsCell) {
    mUmsCell = pUmsCell;
  }

  public LocalTime getStartTime() {
    return startTime;
  }

  public void setStartTime(LocalTime pStartTime) {
    startTime = pStartTime;
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int pIndex) {
    index = pIndex;
  }

  public PdfPTable getTable() {
    return table;
  }

  public void setTable(PdfPTable pTable) {
    table = pTable;
  }
}
