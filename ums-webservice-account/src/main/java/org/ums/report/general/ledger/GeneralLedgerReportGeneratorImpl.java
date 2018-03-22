package org.ums.report.general.ledger;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ums.domain.model.immutable.Company;
import org.ums.manager.CompanyManager;
import org.ums.manager.accounts.AccountBalanceManager;
import org.ums.manager.accounts.AccountManager;
import org.ums.manager.accounts.AccountTransactionManager;
import org.ums.util.UmsUtils;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 20-Mar-18.
 */
@Service
public class GeneralLedgerReportGeneratorImpl implements GeneralLedgerReportGenerator {
  @Autowired
  private AccountTransactionManager mAccountTransactionManager;
  @Autowired
  private AccountManager mAccountManager;
  @Autowired
  private AccountBalanceManager mAccountBalanceManager;
  @Autowired
  private CompanyManager mCompanyManager;
  private Date mFromDate;
  private Date mToDate;
  protected BaseFont baseFont;
  private PdfTemplate totalPages;
  private float footerTextSize = 8f;
  private int pageNumberAlignment = Element.ALIGN_CENTER;

  @Override
  public void createReport(Long pAccountId, String pGroupId, Date fromDate, Date toDate, OutputStream pOutputStream)
      throws Exception {

    mFromDate = fromDate;
    mToDate = toDate;
    Font mLiteFont = new Font(Font.FontFamily.TIMES_ROMAN, 10);
    Font mBoldFont = new Font(Font.FontFamily.TIMES_ROMAN, 10f, Font.BOLD, BaseColor.BLACK);

    Document document = new Document();
    document.addTitle("General Ledger Report");

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfWriter writer = PdfWriter.getInstance(document, baos);
    writer.setPageEvent(new GeneralLedgerReportHeaderAndFooter());
    Font fontTimes11Normal = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11);
    Font fontTimes11Bold = FontFactory.getFont(FontFactory.TIMES_BOLD, 12);
    Font fontTimes14Bold = FontFactory.getFont(FontFactory.TIMES_BOLD, 14);
    document.open();
    document.setPageSize(PageSize.A4.rotate());

    PdfPTable headerTable = new PdfPTable(2);
    PdfPCell leftCell = new PdfPCell();
    Phrase innerPhrase = new Phrase();
    Paragraph paragraph = new Paragraph("Company:");
    paragraph.setFont(mBoldFont);
    innerPhrase.add(paragraph);
    Company company = mCompanyManager.getDefaultCompany();
    paragraph = new Paragraph(company.getName());
    innerPhrase.add(paragraph);
    leftCell.addElement(innerPhrase);
    leftCell.setBorder(Rectangle.NO_BORDER);
    headerTable.addCell(leftCell);

    PdfPCell rightCell = new PdfPCell();
    innerPhrase = new Phrase();
    paragraph = new Paragraph("Date : ");
    paragraph.setFont(mBoldFont);
    innerPhrase.add(paragraph);
    paragraph = new Paragraph(UmsUtils.formatDate(new Date(), "dd-MM-yyyy"));
    paragraph.setFont(mLiteFont);
    innerPhrase.add(paragraph);
    rightCell.addElement(innerPhrase);

    innerPhrase = new Phrase();
    paragraph = new Paragraph("Time :");
    paragraph.setFont(mBoldFont);
    innerPhrase.add(paragraph);
    paragraph = new Paragraph(new Date().getTime());
    paragraph.setFont(mLiteFont);
    innerPhrase.add(paragraph);
    rightCell.addElement(innerPhrase);

    rightCell.setVerticalAlignment(Element.ALIGN_RIGHT);
    rightCell.setBorder(Rectangle.NO_BORDER);
    headerTable.addCell(rightCell);

    document.add(headerTable);
    document.close();
    baos.writeTo(pOutputStream);
  }

  class GeneralLedgerReportHeaderAndFooter extends PdfPageEventHelper {
    public void onStartPage(PdfWriter pPdfWriter, Document pDocument) {
      PdfContentByte cb = pPdfWriter.getDirectContent();
      Font mLiteFont = new Font(Font.FontFamily.TIMES_ROMAN, 10);
      Font mBoldFont = new Font(Font.FontFamily.TIMES_ROMAN, 10f, Font.BOLD, BaseColor.BLACK);
      PdfPTable headerTable = new PdfPTable(2);
      PdfPCell rightCell = new PdfPCell();
      Company defaultCompany = mCompanyManager.getDefaultCompany();
      Phrase innerPhrase = new Phrase();
      Paragraph paragraph = new Paragraph("Company:", mBoldFont);
      innerPhrase.add(paragraph);
      paragraph = new Paragraph(defaultCompany.getName(), mLiteFont);
      innerPhrase.add(paragraph);
      rightCell.addElement(innerPhrase);
      headerTable.addCell(rightCell);
      Phrase header = new Phrase();
      header.add(headerTable);
      ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, innerPhrase, (pDocument.right() - pDocument.left()) / 2
          + pDocument.leftMargin(), pDocument.top() + 10, 0);
    }

    @Override
    public void onEndPage(PdfWriter writer, Document pDocument) {
      PdfContentByte cb = writer.getDirectContent();
      String text = String.format("Page %s of %s", writer.getCurrentPageNumber(), writer.getPageNumber());

      ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, new Phrase(text), (pDocument.right() - pDocument.left()) / 2
          + pDocument.leftMargin(), pDocument.bottom() + 10, 0);
    }
  }
}
