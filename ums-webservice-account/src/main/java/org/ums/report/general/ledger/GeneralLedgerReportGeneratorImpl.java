package org.ums.report.general.ledger;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.immutable.accounts.*;
import org.ums.domain.model.immutable.accounts.Currency;
import org.ums.domain.model.mutable.accounts.MutableAccountBalance;
import org.ums.domain.model.mutable.accounts.MutableAccountTransaction;
import org.ums.domain.model.mutable.accounts.MutableChequeRegister;
import org.ums.enums.accounts.definitions.account.balance.BalanceType;
import org.ums.enums.accounts.definitions.currency.CurrencyFlag;
import org.ums.enums.accounts.definitions.group.GroupType;
import org.ums.enums.accounts.general.ledger.reports.FetchType;
import org.ums.manager.CompanyManager;
import org.ums.manager.accounts.*;
import org.ums.persistent.model.accounts.PersistentAccountBalance;
import org.ums.service.AccountBalanceService;
import org.ums.service.AccountTransactionService;
import org.ums.util.UmsAccountUtils;
import org.ums.util.UmsUtils;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.ums.util.UmsAccountUtils.*;

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
  private GroupManager mGroupManager;
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
  @Autowired
  private ChequeRegisterManager mChequeRegisterManager;
  @Autowired
  private SystemGroupMapManager mSystemGroupMapManager;

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
  public void createReport(Long pAccountId, String pGroupCode, Date fromDate, Date toDate, FetchType pFetchType, OutputStream pOutputStream)
      throws Exception {
    toDate = DateUtils.addHours(toDate, 23);
    toDate = DateUtils.addMinutes(toDate, 59);
    mFromDate = fromDate;
    mToDate = toDate;

    Document document = new Document();
    document.setMargins(15f, 15f, 20f, 20f);
    document.addTitle("General Ledger Report");

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfWriter writer = PdfWriter.getInstance(document, baos);
    document.open();
    document.setPageSize(PageSize.A4.rotate());
    document.newPage();
    writer.setPageEvent(new GeneralLedgerReportHeaderAndFooter());

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


    cell = new PdfPCell(new Paragraph("Debit", mBoldFont));
    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    setTopAndBottomBorderAndAddCell(table, cell);

    cell = new PdfPCell(new Paragraph("Credit", mBoldFont));
    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    setTopAndBottomBorderAndAddCell(table, cell);

    cell = new PdfPCell(new Paragraph("Running Balance", mBoldFont));
    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    setTopAndBottomBorderAndAddCell(table, cell);
    table.setHeaderRows(1);

    FinancialAccountYear currentFinancialAccountYear = mFinancialAccountYearManager.getOpenedFinancialAccountYear();
    AccountBalance accountBalance = new PersistentAccountBalance();
    LocalDate fromDateLocalDateFormat = fromDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    LocalDate toDateLocalDateFormat = toDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    List<MutableAccountTransaction> accountTransactions = new ArrayList<>();
    List<Account> accountsOfTheGroup = new ArrayList<>();
    if (pAccountId != null) {
      accountTransactions = mAccountTransactionManager.getAccountTransactions(UmsUtils.convertToDate("01-01-" + fromDateLocalDateFormat.getYear(), "dd-MM-yyyy"), toDate, mAccountManager.get(pAccountId));
    } else if (pGroupCode != null) {
      List<Group> groupList = mGroupManager.getIncludingMainGroupList(Arrays.asList(pGroupCode));
      accountsOfTheGroup = mAccountManager.getIncludingGroups(groupList.stream().map(g -> g.getGroupCode()).collect(Collectors.toList()));
      accountTransactions = mAccountTransactionManager.getAccountTransactions(UmsUtils.convertToDate("01-01-" + fromDateLocalDateFormat.getYear(), "dd-MM-yyyy"), toDate, accountsOfTheGroup);
    } else {
      accountTransactions = mAccountTransactionManager.getAccountTransactions(UmsUtils.convertToDate("01-01-" + fromDateLocalDateFormat.getYear(), "dd-MM-yyyy"), toDate);
    }


    Map<Long, List<AccountTransaction>> accountTransactionMapWithAccount = new HashMap<>();
    Map<Long, MutableChequeRegister> chequeRegisterMapWithTransactionId = new HashMap<>();
    List<MutableChequeRegister> chequeRegisters = new ArrayList<>();
    Set<Account> accountSet = new HashSet<>();
    List<Account> accountList = new ArrayList<>();
    Date firstDateOfTheFromDateInstance = UmsUtils.convertToDate("01-" + fromDateLocalDateFormat.getMonthValue() + "-" + fromDateLocalDateFormat.getYear(), "dd-MM-yyyy");
    Currency currency = mCurrencyManager.getAll().stream().filter(c -> c.getCurrencyFlag().equals(CurrencyFlag.BASE_CURRENCY)).collect(Collectors.toList()).get(0);

    if (accountTransactions.size() > 0) {
      accountTransactions.sort((o1, o2) -> o1.getVoucherDate().compareTo(o2.getVoucherDate()));
      accountTransactionMapWithAccount = accountTransactions
          .stream()
          .collect(Collectors.groupingBy(t -> t.getAccount().getId()));
      chequeRegisters = mChequeRegisterManager.getByTransactionIdList(accountTransactions.stream().map(t -> t.getId()).collect(Collectors.toList()));
      chequeRegisterMapWithTransactionId = chequeRegisters.stream()
          .collect(Collectors.toMap(c -> c.getAccountTransactionId(), c -> c));
    }


    if (pFetchType.equals(FetchType.TRANSACTION_SPECIFIC)) {

      if (accountTransactions.size() == 0)
        throw new Exception(new Throwable("No record found"));

      accountSet = accountTransactions
          .stream()
          .map(t -> t.getAccount())
          .collect(Collectors.toSet());
      accountList.addAll(accountSet);

    } else {
      if (pAccountId != null)
        accountList.add(mAccountManager.get(pAccountId));
      else if (pGroupCode != null) {
        accountList.addAll(accountsOfTheGroup);
      } else {
        accountList = mAccountBalanceManager.getAccountBalance(currentFinancialAccountYear.getCurrentStartDate(), currentFinancialAccountYear.getCurrentEndDate())
            .stream()
            .map(b -> mAccountManager.get(b.getAccountCode()))
            .collect(Collectors.toList());
      }

      accountSet.addAll(accountList);
    }



    List<MutableAccountBalance> accountBalanceList = mAccountBalanceManager.getAccountBalance(currentFinancialAccountYear.getCurrentStartDate(),
        currentFinancialAccountYear.getCurrentEndDate(), accountList);
    Map<Account, MutableAccountBalance> accountBalanceMap = accountBalanceList
        .stream()
        .collect(Collectors.toMap(a -> mAccountManager.get(a.getAccountCode()), a -> a));
    for (Account account : accountSet) {
      accountBalance = accountBalanceMap.get(account);

      paragraph = new Paragraph("Account Name: ", mBoldFont);
      paragraph.setAlignment(Element.ALIGN_CENTER);
      cell = new PdfPCell(paragraph);
      cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
      setTopBorderAndAddCell(table, cell);

      cell = new PdfPCell(new Paragraph(account.getAccountName(), mLiteFont));
      cell.setColspan(2);
      setTopBorderAndAddCell(table, cell);
      Phrase phrase = new Phrase();
      phrase.add(new Paragraph("Opening Balance as on ", mBoldFont));
      phrase.add(new Paragraph(UmsUtils.formatDate(fromDate, "dd-MM-yyyy"), mLiteFont));
      paragraph = new Paragraph(phrase);
      cell = new PdfPCell(paragraph);
      cell.setColspan(4);
      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
      setTopBorderAndAddCell(table, cell);
      BigDecimal totalOpeningBalance=  mAccountBalanceService.getTillLastMonthBalance(account,
          mFinancialAccountYearManager.getOpenedFinancialAccountYear(), fromDate, accountBalance);
      totalOpeningBalance = totalOpeningBalance.add(accountTransactionService.getTotalBalance(
              accountTransactions.
                      stream()
                      .filter(t->
                              t.getVoucherDate().after(UmsUtils.convertFromLocalDateToDate(UmsUtils.convertFromDateToLocalDate(firstDateOfTheFromDateInstance).minusDays(1)))
                              && t.getVoucherDate().before(fromDate)
                      )
                      .collect(Collectors.toList())));

      String balanceStr = UmsAccountUtils.getBalanceInDebitOrCredit(totalOpeningBalance);
      cell = new PdfPCell(new Paragraph(balanceStr, mLiteFont));
      cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
      setTopBorderAndAddCell(table, cell);

      List<AccountTransaction> accountReportBodyTransactions = new ArrayList<>();
      if (accountTransactionMapWithAccount.containsKey(account.getId()))
        accountReportBodyTransactions = accountTransactionMapWithAccount.get(account.getId()).stream()
          .filter(t -> t.getVoucherDate().after(UmsUtils.convertFromLocalDateToDate(fromDateLocalDateFormat.minusDays(1))) && t.getVoucherDate().before(UmsUtils.convertFromLocalDateToDate(toDateLocalDateFormat.plusDays(1))))
          .collect(Collectors.toList());

      for (AccountTransaction transaction : accountReportBodyTransactions) {
        cell = new PdfPCell(new Paragraph(UmsUtils.formatDate(transaction.getVoucherDate(), "dd-MM-yyyy"), mLiteFont));
        setNoBorderAndAddCell(table, cell);


        cell = new PdfPCell(new Paragraph(transaction.getVoucherNo().substring(2), mLiteFont));
        setNoBorderAndAddCell(table, cell);

        cell = new PdfPCell(new Paragraph(transaction.getNarration() == null ? "" : transaction.getNarration(), mLiteFont));
        setNoBorderAndAddCell(table, cell);


        if (transaction.getAccount().getAccGroupCode().equals(mSystemGroupMapManager.get(GroupType.BANK_ACCOUNTS.getValue()).getGroup().getGroupCode())) {
          ChequeRegister chequeRegister = chequeRegisterMapWithTransactionId.get(transaction.getId());

          cell = new PdfPCell(new Paragraph(chequeRegister.getChequeNo(), mLiteFont));
          setNoBorderAndAddCell(table, cell);


          cell = new PdfPCell(new Paragraph(UmsUtils.formatDate(chequeRegister.getChequeDate(), "dd-MM-yyyy"), mLiteFont));
          setNoBorderAndAddCell(table, cell);


        } else {
          cell = new PdfPCell(new Paragraph(""));
          cell.setColspan(2);
          setNoBorderAndAddCell(table, cell);

        }

        cell = new PdfPCell(new Paragraph(transaction.getBalanceType().equals(BalanceType.Dr) ? UmsAccountUtils.getFormattedBalance(transaction.getAmount()) : "", mLiteFont));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        setNoBorderAndAddCell(table, cell);


        cell = new PdfPCell(new Paragraph(transaction.getBalanceType().equals(BalanceType.Cr) ? UmsAccountUtils.getFormattedBalance(transaction.getAmount()) : " ", mLiteFont));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        setNoBorderAndAddCell(table, cell);


        if (transaction.getBalanceType().equals(BalanceType.Dr))
          totalOpeningBalance = totalOpeningBalance.add(transaction.getAmount());
        else
          totalOpeningBalance = totalOpeningBalance.subtract(transaction.getAmount());

        cell = new PdfPCell(new Paragraph(UmsAccountUtils.getBalanceInDebitOrCredit(totalOpeningBalance), mLiteFont));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        setNoBorderAndAddCell(table, cell);


      }

      cell = new PdfPCell(new Paragraph("Total/Closing Balance", mBoldFont));
      cell.setColspan(5);
      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
      setTopBorderAndAddCell(table, cell);

      BigDecimal totalDebitBalance = new BigDecimal(0);
      BigDecimal totalCreditBalance = new BigDecimal(0);

      totalDebitBalance = UmsAccountUtils.countTotalAmount(accountReportBodyTransactions.stream().filter(t -> t.getBalanceType().equals(BalanceType.Dr)).collect(Collectors.toList()));
      totalCreditBalance = UmsAccountUtils.countTotalAmount(accountReportBodyTransactions.stream().filter(t -> t.getBalanceType().equals(BalanceType.Cr)).collect(Collectors.toList()));

      cell = new PdfPCell(new Paragraph(UmsAccountUtils.getFormattedBalance(totalDebitBalance), mLiteFont));
      cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
      setTopBorderAndAddCell(table, cell);


      cell = new PdfPCell(new Paragraph(UmsAccountUtils.getFormattedBalance(totalCreditBalance), mLiteFont));
      cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
      setTopBorderAndAddCell(table, cell);

      cell = new PdfPCell(new Paragraph(UmsAccountUtils.getBalanceInDebitOrCredit(totalOpeningBalance), mLiteFont));
      cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
      setTopBorderAndAddCell(table, cell);
    }

    document.add(table);

    paragraph = new Paragraph("******* END OF REPORT *******", mLiteFont);
    paragraph.setAlignment(Element.ALIGN_CENTER);
    document.add(paragraph);
    document.close();
    writer.close();
    baos.writeTo(pOutputStream);
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
      String text = String.format("Page %s", writer.getCurrentPageNumber());
      Paragraph paragraph = new Paragraph(text, mBoldFont);
      ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, new Phrase(paragraph),
          (pDocument.right() - pDocument.left()) / 2 + pDocument.leftMargin(), pDocument.bottom() + 10, 0);
    }
  }

}
