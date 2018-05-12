package org.ums.report.balance.sheet;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ums.domain.model.immutable.accounts.Account;
import org.ums.domain.model.immutable.accounts.AccountBalance;
import org.ums.domain.model.immutable.accounts.FinancialAccountYear;
import org.ums.domain.model.immutable.accounts.Group;
import org.ums.enums.accounts.definitions.group.GroupType;
import org.ums.enums.accounts.general.ledger.reports.BalanceSheetFetchType;
import org.ums.manager.CompanyManager;
import org.ums.manager.accounts.*;
import org.ums.report.balance.sheet.helper.CellAndTotalBalance;
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
  @Autowired
  private SystemGroupMapManager mSystemGroupMapManager;

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
    table.setWidthPercentage(100);
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
    table.setHeaderRows(1);

/*// create asset section
    PdfPCell assetCell = new PdfPCell(new Paragraph("Assets", mBoldFont));
    assetCell.setColspan(2);
    assetCell.setBorder(Rectangle.NO_BORDER);
    table.addCell(assetCell);*/

    List<Group> assetGroups = mGroupManager.getIncludingMainGroupList(Arrays.asList(mSystemGroupMapManager.get(GroupType.ASSETS, mCompanyManager.getDefaultCompany()).getGroup().getGroupCode()))
        .stream()
        .filter(a -> !a.getGroupCode().equals(mSystemGroupMapManager.get(GroupType.ASSETS, mCompanyManager.getDefaultCompany()).getGroup().getGroupCode()))
        .collect(Collectors.toList());
    CellAndTotalBalance assetCellAndTotalBalance = createGroupSection(
        pBalanceSheetFetchType,
        accountBalanceMapWithAccount,
        accountMapWithGroupCode,
        assetGroups);
    PdfPCell assetTitleCell = new PdfPCell();
    assetTitleCell.addElement(new Paragraph("Assets", mBoldFont));
    assetTitleCell.setBorder(Rectangle.NO_BORDER);
    PdfPTable leftTable = new PdfPTable(1);
    leftTable.addCell(assetCellAndTotalBalance.getCell());
    leftTable.setWidthPercentage(100);
    assetTitleCell.addElement(leftTable);
    assetTitleCell.setColspan(2);
    table.addCell(assetTitleCell);


    PdfPCell rightSection = new PdfPCell();
    List<Group> liabilitiesGroup = mGroupManager.getIncludingMainGroupList(Arrays.asList(mSystemGroupMapManager.get(GroupType.LIABILITIES, mCompanyManager.getDefaultCompany()).getGroup().getGroupCode()))
        .stream()
        .filter(a -> !a.getGroupCode().equals(mSystemGroupMapManager.get(GroupType.LIABILITIES, mCompanyManager.getDefaultCompany()).getGroup().getGroupCode()))
        .collect(Collectors.toList());

    CellAndTotalBalance liabilitiesCellAndTotalBalance = createGroupSection(
        pBalanceSheetFetchType,
        accountBalanceMapWithAccount,
        accountMapWithGroupCode,
        liabilitiesGroup
    );
    PdfPTable rightTable = new PdfPTable(1);
    cell = new PdfPCell(new Paragraph("Liabilities", mBoldFont));
    cell.setBorder(Rectangle.NO_BORDER);
    rightTable.addCell(cell);

    rightTable.addCell(liabilitiesCellAndTotalBalance.getCell());
    //rightSection.addElement(rightTable);

    List<Group> incomeGroup = mGroupManager.getIncludingMainGroupList(Arrays.asList(mSystemGroupMapManager.get(GroupType.INCOME, mCompanyManager.getDefaultCompany()).getGroup().getGroupCode()))
        .stream()
        .filter(a -> !a.getGroupCode().equals(mSystemGroupMapManager.get(GroupType.INCOME, mCompanyManager.getDefaultCompany()).getGroup().getGroupCode()))
        .collect(Collectors.toList());

    CellAndTotalBalance incomeCellAndTotalBalance = createGroupSection(
        pBalanceSheetFetchType,
        accountBalanceMapWithAccount,
        accountMapWithGroupCode,
        incomeGroup
    );

    cell = new PdfPCell(new Paragraph("Income", mBoldFont));
    cell.setBorder(Rectangle.NO_BORDER);
    rightTable.addCell(cell);

    rightTable.addCell(incomeCellAndTotalBalance.getCell());


    List<Group> expenseGroup = mGroupManager.getIncludingMainGroupList(Arrays.asList(mSystemGroupMapManager.get(GroupType.EXPENSES, mCompanyManager.getDefaultCompany()).getGroup().getGroupCode()))
        .stream()
        .filter(a -> !a.getGroupCode().equals(mSystemGroupMapManager.get(GroupType.EXPENSES, mCompanyManager.getDefaultCompany()).getGroup().getGroupCode()))
        .collect(Collectors.toList());

    CellAndTotalBalance expenseCellAndTotalBalance = createGroupSection(
        pBalanceSheetFetchType,
        accountBalanceMapWithAccount,
        accountMapWithGroupCode,
        expenseGroup
    );

    cell = new PdfPCell(new Paragraph("Expense", mBoldFont));
    cell.setBorder(Rectangle.NO_BORDER);
    rightTable.addCell(cell);

    rightTable.addCell(expenseCellAndTotalBalance.getCell());
    rightSection.addElement(rightTable);
    rightSection.setColspan(2);
    rightSection.setBorder(Rectangle.NO_BORDER);
    table.addCell(rightSection);
    document.add(table);

    PdfPTable totalAmountTable = new PdfPTable(2);
    PdfPTable assetTotalAmountTable = new PdfPTable(2);
    addTotalAmountOfTheSection(assetTotalAmountTable, assetCellAndTotalBalance.getTotalBalance());
    cell = new PdfPCell();
    cell.addElement(assetTotalAmountTable);
    cell.setBorder(Rectangle.NO_BORDER);
    totalAmountTable.addCell(cell);
    PdfPTable liabilitiesTotalAmountTable = new PdfPTable(2);
    addTotalAmountOfTheSection(liabilitiesTotalAmountTable, (liabilitiesCellAndTotalBalance.getTotalBalance().add(incomeCellAndTotalBalance.getTotalBalance())).subtract(expenseCellAndTotalBalance.getTotalBalance()));
    cell = new PdfPCell();
    cell.addElement(liabilitiesTotalAmountTable);
    cell.setBorder(Rectangle.NO_BORDER);
    totalAmountTable.addCell(cell);

    document.add(totalAmountTable);
    document.close();
    baos.writeTo(pOutputStream);
  }

  private CellAndTotalBalance createGroupSection(BalanceSheetFetchType pBalanceSheetFetchType,
      Map<Account, AccountBalance> pAccountBalanceMapWithAccount, Map<String, List<Account>> pAccountMapWithGroupCode,
      List<Group> pGroups) {
    PdfPCell cell;
    float[] innerTableCellWidth = new float[] {7, 3};
    PdfPTable assetTable = new PdfPTable(innerTableCellWidth);

    BigDecimal sectionTotalBalance = new BigDecimal(0);
    sectionTotalBalance =
        generateInternalGroupBody(pBalanceSheetFetchType, pAccountBalanceMapWithAccount, pAccountMapWithGroupCode,
            assetTable, pGroups, sectionTotalBalance);

    addTotalAmountOfTheSection(assetTable, sectionTotalBalance);

    cell = new PdfPCell();
    cell.addElement(assetTable);
    cell.setColspan(2);
    cell.setBorder(Rectangle.NO_BORDER);
    return new CellAndTotalBalance(cell, sectionTotalBalance);
    // pTable.addCell(cell);
  }

  private void addTotalAmountOfTheSection(PdfPTable pAssetTable, BigDecimal pSectionTotalBalance) {
    PdfPCell cell;
    Paragraph paragraph;
    cell = new PdfPCell();
    cell.addElement(new Paragraph("Total ", mBoldFont));
    cell.setPaddingBottom(3);
    cell.setPaddingTop(-3);
    cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
    pAssetTable.addCell(cell);

    cell = new PdfPCell();
    paragraph = new Paragraph(UmsAccountUtils.getFormattedBalance(pSectionTotalBalance), mBoldFont);
    paragraph.setAlignment(Element.ALIGN_RIGHT);
    cell.addElement(paragraph);
    cell.setPaddingBottom(3);
    cell.setPaddingTop(-3);
    cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
    pAssetTable.addCell(cell);
  }

  @NotNull
  private BigDecimal generateInternalGroupBody(BalanceSheetFetchType pBalanceSheetFetchType,
      Map<Account, AccountBalance> pAccountBalanceMapWithAccount, Map<String, List<Account>> pAccountMapWithGroupCode,
      PdfPTable pAssetTable, List<Group> ppGroups, BigDecimal sectionTotalBalance) {
    PdfPCell cell;
    Paragraph paragraph;

    for(Group group : ppGroups) {
      cell = new PdfPCell();
      if(pBalanceSheetFetchType.equals(BalanceSheetFetchType.DETAILED))
        paragraph = new Paragraph(group.getGroupName(), mBoldFont);
      else
        paragraph = new Paragraph(group.getGroupName(), mLiteFont);
      paragraph.setAlignment(Element.ALIGN_LEFT);
      cell.addElement(paragraph);
      if(pBalanceSheetFetchType.equals(BalanceSheetFetchType.DETAILED))
        cell.setColspan(2);
      cell.setBorder(Rectangle.NO_BORDER);
      pAssetTable.addCell(cell);
      List<Account> groupAccountList = new ArrayList<>();
      if(pAccountMapWithGroupCode.containsKey(group.getGroupCode()))
        groupAccountList = pAccountMapWithGroupCode.get(group.getGroupCode());

      if(groupAccountList.size() == 0) {
        cell = new PdfPCell();
        if(pBalanceSheetFetchType.equals(BalanceSheetFetchType.DETAILED))
          paragraph = new Paragraph(UmsAccountUtils.getFormattedBalance(new BigDecimal(0)), mBoldFont);
        else
          paragraph = new Paragraph(UmsAccountUtils.getFormattedBalance(new BigDecimal(0)), mLiteFont);
        paragraph.setAlignment(Element.ALIGN_RIGHT);
        cell.addElement(paragraph);
        if(pBalanceSheetFetchType.equals(BalanceSheetFetchType.DETAILED)) {
          cell.setColspan(2);
        }
        sectionTotalBalance = sectionTotalBalance.add(new BigDecimal(0));
        cell.setBorder(Rectangle.NO_BORDER);
        pAssetTable.addCell(cell);

      }
      else {
        BigDecimal totalAccountBalance = new BigDecimal(0);
        BigDecimal groupTotalBalance = new BigDecimal(0);
        groupTotalBalance =
            generateGroupBasedAccountSection(pBalanceSheetFetchType, pAccountBalanceMapWithAccount, pAssetTable,
                groupAccountList, totalAccountBalance, groupTotalBalance);

        cell = new PdfPCell();
        sectionTotalBalance = sectionTotalBalance.add(groupTotalBalance);
        if(pBalanceSheetFetchType.equals(BalanceSheetFetchType.DETAILED))
          paragraph = new Paragraph(UmsAccountUtils.getFormattedBalance(groupTotalBalance), mBoldFont);
        else
          paragraph = new Paragraph(UmsAccountUtils.getFormattedBalance(groupTotalBalance), mLiteFont);
        paragraph.setAlignment(Element.ALIGN_RIGHT);
        if(pBalanceSheetFetchType.equals(BalanceSheetFetchType.DETAILED))
          cell.setColspan(2);
        cell.addElement(paragraph);
        cell.setBorder(Rectangle.NO_BORDER);
        pAssetTable.addCell(cell);

      }
    }

    return sectionTotalBalance;

  }

  private BigDecimal generateGroupBasedAccountSection(BalanceSheetFetchType pBalanceSheetFetchType,
      Map<Account, AccountBalance> pAccountBalanceMapWithAccount, PdfPTable pAssetTable,
      List<Account> pGroupAccountList, BigDecimal pTotalAccountBalance, BigDecimal pGroupTotalBalance) {
    PdfPCell cell;
    Paragraph paragraph;
    for(Account account : pGroupAccountList) {
      AccountBalance accountBalance = pAccountBalanceMapWithAccount.get(account);
      BigDecimal accountTotalBalance = accountBalance.getTotCreditTrans().subtract(accountBalance.getTotDebitTrans());
      pTotalAccountBalance = pTotalAccountBalance.add(accountTotalBalance);
      pGroupTotalBalance = pGroupTotalBalance.add(accountTotalBalance);
      if(pBalanceSheetFetchType.equals(BalanceSheetFetchType.DETAILED)) {
        cell = new PdfPCell();
        paragraph = new Paragraph(account.getAccountName(), mLiteFont);
        cell.addElement(paragraph);
        cell.setBorder(Rectangle.NO_BORDER);
        pAssetTable.addCell(cell);

        cell = new PdfPCell();
        paragraph = new Paragraph(UmsAccountUtils.getFormattedBalance(accountTotalBalance), mLiteFont);
        paragraph.setAlignment(Element.ALIGN_RIGHT);
        cell.addElement(paragraph);
        cell.setBorder(Rectangle.NO_BORDER);
        pAssetTable.addCell(cell);
      }
    }
    return pGroupTotalBalance;
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
    float[] columnSize = new float[] {1, 6, 2};
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
          (pDocument.right() - pDocument.left()) / 2 + pDocument.leftMargin(), pDocument.bottom() - 10, 0);
    }
  }

}
