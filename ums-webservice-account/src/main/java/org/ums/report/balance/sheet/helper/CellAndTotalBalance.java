package org.ums.report.balance.sheet.helper;

import com.itextpdf.text.pdf.PdfPCell;

import java.math.BigDecimal;

/**
 * Created by Monjur-E-Morshed on 03-Apr-18.
 */
public class CellAndTotalBalance {
  private PdfPCell mCell;
  private BigDecimal mTotalBalance;

  public PdfPCell getCell() {
    return mCell;
  }

  public CellAndTotalBalance(PdfPCell pCell, BigDecimal pTotalBalance) {
    mCell = pCell;
    mTotalBalance = pTotalBalance;
  }

  public void setCell(PdfPCell pCell) {
    mCell = pCell;
  }

  public BigDecimal getTotalBalance() {
    return mTotalBalance;
  }

  public void setTotalBalance(BigDecimal pTotalBalance) {
    mTotalBalance = pTotalBalance;
  }
}
