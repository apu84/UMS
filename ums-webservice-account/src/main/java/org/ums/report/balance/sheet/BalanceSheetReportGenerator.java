package org.ums.report.balance.sheet;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ums.enums.accounts.general.ledger.reports.BalanceSheetFetchType;
import org.ums.manager.CompanyManager;
import org.ums.manager.accounts.AccountBalanceManager;
import org.ums.manager.accounts.AccountManager;
import org.ums.manager.accounts.GroupManager;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 29-Mar-18.
 */
@Service
public class BalanceSheetReportGenerator {

  Font mLiteFont = new Font(Font.FontFamily.TIMES_ROMAN, 10);
  Font mBoldFont = new Font(Font.FontFamily.TIMES_ROMAN, 10f, Font.BOLD, BaseColor.BLACK);
  Font mSmallFont = new Font(Font.FontFamily.TIMES_ROMAN, 3f);

  @Autowired
  private CompanyManager mCompanyManager;
  @Autowired
  private GroupManager mGroupManager;
  @Autowired
  private AccountBalanceManager mAccountBalanceManager;
  @Autowired
  private AccountManager mAccountManager;

  public void createBalanceSheetReport(Date pDate, BalanceSheetFetchType pBalanceSheetFetchType,
      BalanceSheetFetchType pDebtorLedgerFetchType, OutputStream pOutputStream) throws Exception {

    Document document = new Document();
    document.addTitle("Balance Sheet");

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfWriter writer = PdfWriter.getInstance(document, baos);
    writer.setPageEvent(new BalanceSheetReportHeaderAndFooter());
    document.open();
    document.setPageSize(PageSize.A4.rotate());
    document.newPage();

    document.close();
    baos.writeTo(pOutputStream);
  }

  class BalanceSheetReportHeaderAndFooter extends PdfPageEventHelper {
    @Override
    public void onEndPage(PdfWriter writer, Document pDocument) {
      PdfContentByte cb = writer.getDirectContent();
      String text = String.format("Page %s", writer.getCurrentPageNumber());
      Paragraph paragraph = new Paragraph(text, mBoldFont);
      ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, new Phrase(paragraph),
          (pDocument.right() - pDocument.left()) / 2 + pDocument.leftMargin(), pDocument.bottom() + 10, 0);
    }
  }

}
