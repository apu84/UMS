package org.ums.report.balance.sheet;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ums.domain.model.immutable.accounts.Account;
import org.ums.domain.model.immutable.accounts.AccountBalance;
import org.ums.domain.model.immutable.accounts.FinancialAccountYear;
import org.ums.domain.model.immutable.accounts.Group;
import org.ums.enums.accounts.definitions.group.GroupType;
import org.ums.enums.accounts.general.ledger.reports.BalanceSheetFetchType;
import org.ums.manager.CompanyManager;
import org.ums.manager.accounts.AccountBalanceManager;
import org.ums.manager.accounts.AccountManager;
import org.ums.manager.accounts.FinancialAccountYearManager;
import org.ums.manager.accounts.GroupManager;
import org.ums.util.UmsAccountUtils;
import org.ums.util.UmsUtils;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

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
  @Autowired
  private FinancialAccountYearManager mFinancialAccountYearManager;

  public void createBalanceSheetReport(Date pDate, BalanceSheetFetchType pBalanceSheetFetchType,
                                       BalanceSheetFetchType pDebtorLedgerFetchType, OutputStream pOutputStream) throws Exception {


    FinancialAccountYear financialAccountYear = mFinancialAccountYearManager.getOpenedFinancialAccountYear();
    List<AccountBalance> accountBalanceList = mAccountBalanceManager.getAccountBalance(financialAccountYear.getCurrentStartDate(), financialAccountYear.getCurrentEndDate());
    Map<Account, AccountBalance> accountBalanceMapWithAccount = accountBalanceList
        .stream()
        .collect(Collectors.toMap(a -> mAccountManager.get(a.getAccountCode()), a -> a));
    List<Account> accountList = accountBalanceList.stream()
        .map(a -> mAccountManager.get(a.getAccountCode()))
        .collect(Collectors.toList());
    Map<String, List<Account>> accountMapWithGroupCode = accountList
        .stream()
        .collect(Collectors.groupingBy(a -> a.getAccGroupCode()));
    List<Group> groupList = mGroupManager.getAll();
    Map<String, Group> groupMapWithGroupCode = groupList
        .stream()
        .collect(Collectors.toMap(g -> g.getGroupCode(), g -> g));


    Document document = new Document();
    document.addTitle("Balance Sheet");

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfWriter writer = PdfWriter.getInstance(document, baos);
    writer.setPageEvent(new BalanceSheetReportHeaderAndFooter());
    document.open();
    document.setPageSize(PageSize.A4.rotate());
    document.newPage();
    generateInitialHeaderInfo(pDate, document);

    float[] columnLengths = new float[]{4, 2, 4, 2};
    PdfPTable table = new PdfPTable(columnLengths);
    PdfPCell cell = new PdfPCell();
    Paragraph paragraph = new Paragraph("Particulars", mBoldFont);
    addTopAndBottomBorderedCell(document, table, cell, paragraph);


    cell = new PdfPCell();
    paragraph = new Paragraph("Amount (BDT) ", mBoldFont);
    paragraph.setAlignment(Element.ALIGN_RIGHT);
    addTopAndBottomBorderedCell(document, table, cell, paragraph);

    cell = new PdfPCell();
    paragraph = new Paragraph("Particulars", mBoldFont);
    addTopAndBottomBorderedCell(document, table, cell, paragraph);


    cell = new PdfPCell();
    paragraph = new Paragraph("Amount (BDT)", mBoldFont);
    paragraph.setAlignment(Element.ALIGN_RIGHT);
    addTopAndBottomBorderedCell(document, table, cell, paragraph);
    table.setHeaderRows(0);

    float[] innerTableCellWidth = new float[]{7, 3};
    PdfPTable assetTable = new PdfPTable(innerTableCellWidth);
    List<Group> assetGroups = mGroupManager.getIncludingMainGroupList(Arrays.asList(GroupType.ASSETS.getValue()));

    BigDecimal totalAssetBalance = new BigDecimal(0);

    for (Group group : assetGroups) {
      cell = new PdfPCell();
      paragraph = new Paragraph(group.getGroupName(), mBoldFont);
      cell.addElement(paragraph);
      if (pBalanceSheetFetchType.equals(BalanceSheetFetchType.DETAILED))
        cell.setColspan(2);
      assetTable.addCell(cell);
      List<Account> groupAccountList = new ArrayList<>();
      if (accountMapWithGroupCode.containsKey(group.getGroupCode()))
        groupAccountList = accountMapWithGroupCode.get(group.getGroupCode());

      if (groupAccountList.size() == 0) {
        paragraph = new Paragraph(UmsAccountUtils.getFormattedBalance(new BigDecimal(0)), mBoldFont);
        paragraph.setAlignment(Element.ALIGN_RIGHT);
        cell.addElement(paragraph);
        cell.setColspan(2);
        if (pBalanceSheetFetchType.equals(BalanceSheetFetchType.DETAILED))
          cell = new PdfPCell();
        table.addCell(cell);


      } else {
        BigDecimal totalAccountBalance = new BigDecimal(0);
        BigDecimal groupTotalBalance = new BigDecimal(0);
        for (Account account : groupAccountList) {
          AccountBalance accountBalance = accountBalanceMapWithAccount.get(account);
          BigDecimal accountTotalBalance = accountBalance.getTotCreditTrans().subtract(accountBalance.getTotDebitTrans());
          totalAccountBalance.add(accountTotalBalance);
          groupTotalBalance.add(accountTotalBalance);
          if (pBalanceSheetFetchType.equals(BalanceSheetFetchType.DETAILED)) {
            cell = new PdfPCell();
            paragraph = new Paragraph(account.getAccountName(), mLiteFont);
            cell.addElement(paragraph);
            cell.setBorder(Rectangle.NO_BORDER);
            assetTable.addCell(cell);

            cell = new PdfPCell();
            paragraph = new Paragraph(UmsAccountUtils.getFormattedBalance(accountTotalBalance), mLiteFont);
            paragraph.setAlignment(Element.ALIGN_RIGHT);
            cell.addElement(paragraph);
            cell.setBorder(Rectangle.NO_BORDER);
            assetTable.addCell(cell);
          }
        }

        cell = new PdfPCell();
        paragraph = new Paragraph(UmsAccountUtils.getFormattedBalance(groupTotalBalance), mBoldFont);
        paragraph.setAlignment(Element.ALIGN_RIGHT);
        if (pBalanceSheetFetchType.equals(BalanceSheetFetchType.DETAILED))
          cell.setColspan(2);
        assetTable.addCell(cell);

      }

    }

    cell = new PdfPCell();
    cell.addElement(assetTable);
    cell.setColspan(2);
    table.addCell(cell);

//    for test
    cell = new PdfPCell(new Paragraph(""));
    cell.setColspan(2);
    table.addCell(cell);

    document.add(table);
    document.close();
    baos.writeTo(pOutputStream);
  }

  private void addTopAndBottomBorderedCell(Document pDocument, PdfPTable pTable, PdfPCell pCell, Paragraph pParagraph)
      throws DocumentException {
    pCell.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
    pCell.addElement(pParagraph);
    pCell.setPaddingTop(-2);
    pCell.setPaddingBottom(5);
    pTable.addCell(pCell);
  }

  private void generateInitialHeaderInfo(Date pDate, Document pDocument) throws DocumentException {
    float[] columnSize = new float[]{1, 6, 2};
    PdfPTable table = new PdfPTable(columnSize);
    PdfPCell cell = new PdfPCell(new Paragraph(""));
    cell.setBorder(Rectangle.NO_BORDER);
    table.addCell(cell);

    cell = new PdfPCell();
    Phrase innerPhrase = new Phrase();
    Paragraph paragraph = new Paragraph("Balance Sheet For ", mLiteFont);
    innerPhrase.add(paragraph);
    paragraph = new Paragraph(mCompanyManager.getDefaultCompany().getName(), mBoldFont);
    innerPhrase.add(paragraph);
    paragraph = new Paragraph(innerPhrase);
    paragraph.setAlignment(Element.ALIGN_CENTER);
    cell.addElement(paragraph);

    innerPhrase = new Phrase();
    paragraph = new Paragraph("As on ", mLiteFont);
    paragraph.setAlignment(Element.ALIGN_CENTER);
    innerPhrase.add(paragraph);
    paragraph = new Paragraph(UmsUtils.formatDate(pDate, "dd-MM-yyyy"), mBoldFont);
    paragraph.setAlignment(Element.ALIGN_CENTER);
    innerPhrase.add(paragraph);
    paragraph = new Paragraph(innerPhrase);
    paragraph.setAlignment(Element.ALIGN_CENTER);
    cell.addElement(paragraph);
    cell.setBorder(Rectangle.NO_BORDER);
    table.addCell(cell);

    cell = new PdfPCell();
    innerPhrase = new Phrase();
    paragraph = new Paragraph("Date : ", mBoldFont);
    innerPhrase.add(paragraph);
    paragraph = new Paragraph(UmsUtils.formatDate(new Date(), "dd-MM-yyyy"), mLiteFont);
    innerPhrase.add(paragraph);
    cell.addElement(innerPhrase);
    innerPhrase = new Phrase();
    paragraph = new Paragraph("Time : ", mBoldFont);
    paragraph.setFont(mBoldFont);
    innerPhrase.add(paragraph);
    LocalTime time = LocalTime.now();
    DateFormat dateFormat = new SimpleDateFormat("hh.mm aa");
    paragraph = new Paragraph(dateFormat.format(new Date()).toString(), mLiteFont);
    innerPhrase.add(paragraph);
    cell.addElement(innerPhrase);
    cell.setBorder(Rectangle.NO_BORDER);
    table.addCell(cell);

    pDocument.add(table);
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
