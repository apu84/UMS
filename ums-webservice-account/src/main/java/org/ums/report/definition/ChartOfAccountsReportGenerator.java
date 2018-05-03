package org.ums.report.definition;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.immutable.accounts.Account;
import org.ums.domain.model.immutable.accounts.Group;
import org.ums.enums.accounts.definitions.group.GroupType;
import org.ums.manager.CompanyManager;
import org.ums.manager.accounts.*;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Monjur-E-Morshed on 03-May-18.
 */
@Service
public class ChartOfAccountsReportGenerator {
  Font mLiteFont = new Font(Font.FontFamily.TIMES_ROMAN, 10);
  Font mBoldFont = new Font(Font.FontFamily.TIMES_ROMAN, 10f, Font.BOLD, BaseColor.BLACK);
  Font mHeaderFont = new Font(Font.FontFamily.TIMES_ROMAN, 15f, Font.BOLD, BaseColor.BLACK);
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

  public void createChartsOfAccountsReport(OutputStream pOutputStream) throws Exception {


    Company company = mCompanyManager.getDefaultCompany();
    List<Account> accountList = mAccountManager.getAccounts(company);

    Map<String, List<Account>> groupMapWithAccounts = accountList
        .stream()
        .collect(Collectors.groupingBy(a -> a.getAccGroupCode()));

    Document document = new Document();
    document.addTitle("Charts of Accounts");
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfWriter writer = PdfWriter.getInstance(document, baos);
    writer.setPageEvent(new ChartOfAccountsReportGenerator.ChartsOfAccountsHeaderAndFooter());
    document.open();
    document.setPageSize(PageSize.A4);
    document.newPage();


    Paragraph paragraph = new Paragraph(company.getName(), mHeaderFont);
    paragraph.setAlignment(Element.ALIGN_CENTER);
    document.add(paragraph);

    paragraph = new Paragraph("Chart of Accounts", mHeaderFont);
    paragraph.setAlignment(Element.ALIGN_CENTER);
    document.add(paragraph);

    Group assetGroup = mSystemGroupMapManager.get(GroupType.ASSETS.getValue()).getGroup();
    List<Group> assetRelatedGroupList = mGroupManager.getIncludingMainGroupList(Arrays.asList(assetGroup.getGroupCode()));
    Chunk chunk = new Chunk(assetGroup.getGroupName(), mBoldFont);
    chunk.setUnderline(1, -3);
    paragraph = new Paragraph(chunk);
    document.add(paragraph);
    document = createGroupBasedAccountReport(document, assetRelatedGroupList, groupMapWithAccounts);


    Group liabilityGroup = mSystemGroupMapManager.get(GroupType.LIABILITIES.getValue()).getGroup();
    List<Group> liabilityRelatedGroupList = mGroupManager.getIncludingMainGroupList(Arrays.asList(liabilityGroup.getGroupCode()));
    chunk = new Chunk(liabilityGroup.getGroupName(), mBoldFont);
    chunk.setUnderline(1, -3);
    paragraph = new Paragraph(chunk);
    document.add(paragraph);
    document = createGroupBasedAccountReport(document, liabilityRelatedGroupList, groupMapWithAccounts);

    Group incomeGroup = mSystemGroupMapManager.get(GroupType.INCOME.getValue()).getGroup();
    List<Group> incomeRelatedGroupList = mGroupManager.getIncludingMainGroupList(Arrays.asList(incomeGroup.getGroupCode()));
    chunk = new Chunk(incomeGroup.getGroupName(), mBoldFont);
    chunk.setUnderline(1, -3);
    paragraph = new Paragraph(chunk);
    document.add(paragraph);
    document = createGroupBasedAccountReport(document, incomeRelatedGroupList, groupMapWithAccounts);

    Group expenseGroup = mSystemGroupMapManager.get(GroupType.EXPENSES.getValue()).getGroup();
    List<Group> expenseRelatedGroupList = mGroupManager.getIncludingMainGroupList(Arrays.asList(expenseGroup.getGroupCode()));
    chunk = new Chunk(expenseGroup.getGroupName(), mBoldFont);
    chunk.setUnderline(1, -3);
    paragraph = new Paragraph(chunk);
    document.add(paragraph);
    document = createGroupBasedAccountReport(document, expenseRelatedGroupList, groupMapWithAccounts);


    document.close();
    baos.writeTo(pOutputStream);
  }

  private Document createGroupBasedAccountReport(Document pDocument, List<Group> pGroupList,
      Map<String, List<Account>> pGroupWithAccountMap) throws Exception {
    for(Group group : pGroupList) {
      if(pGroupWithAccountMap.containsKey(group.getGroupCode())) {
        Paragraph paragraph = new Paragraph(group.getGroupName(), mBoldFont);
        pDocument.add(paragraph);

        List<Account> groupDependentAccountList = pGroupWithAccountMap.get(group.getGroupCode());

        for(Account account : groupDependentAccountList) {
          paragraph = new Paragraph(account.getAccountName(), mLiteFont);
          pDocument.add(paragraph);
        }
      }

      pDocument.add(new Paragraph(" "));
      pDocument.add(new Paragraph(" "));
    }

    return pDocument;
  }

  class ChartsOfAccountsHeaderAndFooter extends PdfPageEventHelper {
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
