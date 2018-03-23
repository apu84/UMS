package org.ums.report.general.ledger;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.immutable.accounts.Account;
import org.ums.domain.model.immutable.accounts.AccountBalance;
import org.ums.domain.model.immutable.accounts.Currency;
import org.ums.domain.model.immutable.accounts.FinancialAccountYear;
import org.ums.domain.model.mutable.accounts.MutableAccountTransaction;
import org.ums.enums.accounts.definitions.currency.CurrencyFlag;
import org.ums.manager.CompanyManager;
import org.ums.manager.accounts.*;
import org.ums.persistent.model.accounts.PersistentAccountBalance;
import org.ums.service.AccountBalanceService;
import org.ums.service.AccountTransactionService;
import org.ums.util.UmsUtils;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
  private FinancialAccountYearManager mFinancialAccountYearManager;
  @Autowired
  private CompanyManager mCompanyManager;
  @Autowired
  private AccountBalanceService mAccountBalanceService;
  @Autowired
  private AccountTransactionService accountTransactionService;
  @Autowired
  private CurrencyManager mCurrencyManager;

  private Date mFromDate;
  private Date mToDate;
  protected BaseFont baseFont;
  private PdfTemplate totalPages;
  private float footerTextSize = 8f;
  private int pageNumberAlignment = Element.ALIGN_CENTER;
  Font mLiteFont = new Font(Font.FontFamily.TIMES_ROMAN, 10);
  Font mBoldFont = new Font(Font.FontFamily.TIMES_ROMAN, 10f, Font.BOLD, BaseColor.BLACK);
  Font mSmallFont = new Font(Font.FontFamily.TIMES_ROMAN, 3f);

  @Override
  public void createReport(Long pAccountId, String pGroupId, Date fromDate, Date toDate, OutputStream pOutputStream)
      throws Exception {

    mFromDate = fromDate;
    mToDate = toDate;

    Document document = new Document();
    document.setMargins(5f, 5f, 1f, 1f);
    document.addTitle("General Ledger Report");

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfWriter writer = PdfWriter.getInstance(document, baos);
    writer.setPageEvent(new GeneralLedgerReportHeaderAndFooter());
    document.open();
    document.setPageSize(PageSize.A4.rotate());
    document.newPage();

    document = createHeader(fromDate, toDate, document);

    /*
     * LineSeparator lineSeparator = new LineSeparator(); chunk = new Chunk(lineSeparator);
     * paragraph = new Paragraph(chunk + "", mSmallFont); document.add(paragraph);
     */
    float[] columnWidths = {1, 1, 1, 1, 1, 1, 1, 1};
    PdfPTable table = new PdfPTable(8);
    table.setWidthPercentage(100);
    PdfPCell cell = new PdfPCell();
    Paragraph paragraph = new Paragraph("Voucher", mBoldFont);
    cell.addElement(paragraph);
    cell.addElement(new Paragraph("Date", mBoldFont));
    setTopAndBottomBorderAndAddCell(table, cell);

    cell = new PdfPCell();
    cell.addElement(new Paragraph("Voucher", mBoldFont));
    cell.addElement(new Paragraph("No. ", mBoldFont));
    setTopAndBottomBorderAndAddCell(table, cell);

    cell = new PdfPCell();
    cell.addElement(new Paragraph("Narration", mBoldFont));
    setTopAndBottomBorderAndAddCell(table, cell);

    cell = new PdfPCell();
    cell.addElement(new Paragraph("Cheque No.", mBoldFont));
    cell.addElement(new Paragraph("Ref. No.", mBoldFont));
    setTopAndBottomBorderAndAddCell(table, cell);

    cell = new PdfPCell();
    cell.addElement(new Paragraph("Cheque Date.", mBoldFont));
    cell.addElement(new Paragraph("Ref. Date", mBoldFont));
    setTopAndBottomBorderAndAddCell(table, cell);

    cell = new PdfPCell();
    cell.addElement(new Paragraph("Debit", mBoldFont));
    setTopAndBottomBorderAndAddCell(table, cell);

    cell = new PdfPCell();
    cell.addElement(new Paragraph("Credit", mBoldFont));
    setTopAndBottomBorderAndAddCell(table, cell);

    cell = new PdfPCell();
    cell.addElement(new Paragraph("Running Balance", mBoldFont));
    setTopAndBottomBorderAndAddCell(table, cell);
    table.setHeaderRows(1);

    FinancialAccountYear currentFinancialAccountYear = mFinancialAccountYearManager.getOpenedFinancialAccountYear();
    AccountBalance accountBalance = new PersistentAccountBalance();
    List<MutableAccountTransaction> accountTransactions = new ArrayList<>();
    LocalDate fromDateLocalDateFormat = fromDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    LocalDate toDateLocalDateFormat = toDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    Date firstDateOfTheFromDateInstance = UmsUtils.convertToDate("01-"+fromDateLocalDateFormat.getMonthValue()+"-"+fromDateLocalDateFormat.getYear(), "dd-MM-yyyy");
    Currency currency = mCurrencyManager.getAll().stream().filter(c->c.getCurrencyFlag().equals(CurrencyFlag.BASE_CURRENCY)).collect(Collectors.toList()).get(0);
    if(pAccountId != null) {
      accountBalance =
          mAccountBalanceManager.getAccountBalance(currentFinancialAccountYear.getCurrentStartDate(),
              currentFinancialAccountYear.getCurrentEndDate(), mAccountManager.get(pAccountId));
      accountTransactions =
          mAccountTransactionManager.getAccountTransactions(UmsUtils.convertToDate("01-01-"+fromDateLocalDateFormat.getMonth().getValue(),"dd-MM-yyyy"), toDate, mAccountManager.get(pAccountId));

      Account account = mAccountManager.get(pAccountId);

      paragraph = new Paragraph("Account Name: ", mBoldFont);
      paragraph.setAlignment(Element.ALIGN_CENTER);
      cell = new PdfPCell(paragraph);
      cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
      setNoBorderAndAddCell(table, cell);

      cell = new PdfPCell(new Paragraph(account.getAccountName(), mLiteFont));
      setNoBorderAndAddCell(table, cell);
      Phrase phrase = new Phrase();
      phrase.add(new Paragraph("Opening Balance as on ", mBoldFont));
      phrase.add(new Paragraph(UmsUtils.formatDate(toDate, "dd-MM-yyyy"), mLiteFont));
      paragraph = new Paragraph(phrase);
      cell = new PdfPCell(paragraph);
      cell.setColspan(5);
      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
      setNoBorderAndAddCell(table, cell);
      BigDecimal totalOpeningBalance=  mAccountBalanceService.getTillLastMonthBalance(account,
              mFinancialAccountYearManager.getOpenedFinancialAccountYear(), fromDate);
      totalOpeningBalance = totalOpeningBalance.add(accountTransactionService.getTotalBalance(
              accountTransactions.
                      stream()
                      .filter(t->
                              t.getVoucherDate().after(UmsUtils.convertFromLocalDateToDate(UmsUtils.convertFromDateToLocalDate(firstDateOfTheFromDateInstance).minusDays(1)))
                              && t.getVoucherDate().before(fromDate)
                      )
                      .collect(Collectors.toList())));

      String balanceStr = NumberFormat.getCurrencyInstance().format(totalOpeningBalance,new StringBuffer(currency.getCurrencyCode()), new FieldPosition(4)).toString();
      cell = new PdfPCell(new Paragraph(balanceStr, mLiteFont));
      setNoBorderAndAddCell(table, cell);
    }

    document.add(table);
    document.close();
    baos.writeTo(pOutputStream);
  }

  private void setTopAndBottomBorderAndAddCell(PdfPTable table, PdfPCell cell) {
    cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
    table.addCell(cell);
  }

  private void setNoBorderAndAddCell(PdfPTable table, PdfPCell cell) {
    cell.setBorder(Rectangle.NO_BORDER);
    table.addCell(cell);
  }

  private Document createHeader(Date fromDate, Date toDate, Document document) throws DocumentException {
    PdfPTable headerTable = new PdfPTable(2);
    headerTable.setWidthPercentage(100f);
    PdfPCell leftCell = new PdfPCell();
    Phrase innerPhrase = new Phrase();
    Paragraph paragraph = new Paragraph("Company: ", mBoldFont);
    innerPhrase.add(paragraph);
    Company company = mCompanyManager.getDefaultCompany();
    paragraph = new Paragraph(company.getName(), mLiteFont);
    innerPhrase.add(paragraph);
    leftCell.addElement(innerPhrase);
    leftCell.setBorder(Rectangle.NO_BORDER);
    headerTable.addCell(leftCell);

    PdfPCell rightCell = new PdfPCell();
    innerPhrase = new Phrase();
    paragraph = new Paragraph("Date : ", mBoldFont);
    paragraph.setFont(mBoldFont);
    innerPhrase.add(paragraph);
    paragraph = new Paragraph(UmsUtils.formatDate(new Date(), "dd-MM-yyyy"), mLiteFont);
    paragraph.setFont(mLiteFont);
    innerPhrase.add(paragraph);
    paragraph = new Paragraph(innerPhrase);
    paragraph.setAlignment(Element.ALIGN_RIGHT);
    rightCell.addElement(paragraph);

    innerPhrase = new Phrase();
    paragraph = new Paragraph("Time :", mBoldFont);
    paragraph.setFont(mBoldFont);
    innerPhrase.add(paragraph);
    LocalTime time = LocalTime.now();
    DateFormat dateFormat = new SimpleDateFormat("hh.mm aa");
    paragraph = new Paragraph(dateFormat.format(new Date()).toString(), mLiteFont);
    paragraph.setFont(mLiteFont);
    innerPhrase.add(paragraph);
    paragraph = new Paragraph(innerPhrase);
    paragraph.setAlignment(Element.ALIGN_RIGHT);
    rightCell.addElement(paragraph);
    rightCell.setBorder(Rectangle.NO_BORDER);
    headerTable.addCell(rightCell);

    document.add(headerTable);

    Chunk chunk = new Chunk("General Ledger", mBoldFont);
    chunk.setUnderline(0.2f, -2f);
    paragraph = new Paragraph(chunk);
    paragraph.setAlignment(Element.ALIGN_CENTER);
    document.add(paragraph);
    paragraph = new Paragraph("(Account Wise)", mBoldFont);
    paragraph.setAlignment(Element.ALIGN_CENTER);
    document.add(paragraph);

    innerPhrase = new Phrase();
    paragraph = new Paragraph("Period :", mBoldFont);
    innerPhrase.add(paragraph);
    paragraph = new Paragraph(UmsUtils.formatDate(fromDate, "dd-MM-yyyy"), mLiteFont);
    innerPhrase.add(paragraph);
    paragraph = new Paragraph(" to ", mBoldFont);
    innerPhrase.add(paragraph);
    paragraph = new Paragraph(UmsUtils.formatDate(toDate, "dd-MM-yyyy"), mLiteFont);
    innerPhrase.add(paragraph);
    document.add(innerPhrase);
    return document;
  }

  class GeneralLedgerReportHeaderAndFooter extends PdfPageEventHelper {

    @Override
    public void onEndPage(PdfWriter writer, Document pDocument) {
      PdfContentByte cb = writer.getDirectContent();
      String text = String.format("Page %s of %s", writer.getCurrentPageNumber(), writer.getPageNumber());
      Paragraph paragraph = new Paragraph(text, mBoldFont);
      ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, new Phrase(paragraph),
          (pDocument.right() - pDocument.left()) / 2 + pDocument.leftMargin(), pDocument.bottom() + 10, 0);
    }
  }
}
