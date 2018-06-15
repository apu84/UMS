package org.ums.report.transaction;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.immutable.accounts.AccountTransaction;
import org.ums.domain.model.immutable.accounts.ChequeRegister;
import org.ums.domain.model.immutable.accounts.Voucher;
import org.ums.domain.model.mutable.accounts.MutableAccountTransaction;
import org.ums.domain.model.mutable.accounts.MutableChequeRegister;
import org.ums.enums.accounts.definitions.account.balance.BalanceType;
import org.ums.manager.CompanyManager;
import org.ums.manager.accounts.AccountTransactionManager;
import org.ums.manager.accounts.ChequeRegisterManager;
import org.ums.report.itext.UmsCell;
import org.ums.report.itext.UmsParagraph;
import org.ums.util.Utils;
import org.ums.util.UmsUtils;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Monjur-E-Morshed on 10-Apr-18.
 */
@Service
public class TransactionReportGenerator {

  @Autowired
  private AccountTransactionManager mTransactionManager;
  @Autowired
  private CompanyManager mCompanyManager;
  @Autowired
  private ChequeRegisterManager mChequeRegisterManager;

  Font mLiteFont = new Font(Font.FontFamily.TIMES_ROMAN, 10);
  Font mBoldFont = new Font(Font.FontFamily.TIMES_ROMAN, 10f, Font.BOLD, BaseColor.BLACK);
  Font mHeaderFont = new Font(Font.FontFamily.TIMES_ROMAN, 15f, Font.BOLD, BaseColor.BLACK);
  Font mLiteFontItalic = new Font(Font.FontFamily.TIMES_ROMAN, 10f, Font.ITALIC, BaseColor.BLACK);
  Font mBoldFontItalic = new Font(Font.FontFamily.TIMES_ROMAN, 10f, Font.BOLDITALIC, BaseColor.BLACK);
  Font mHeaderFontItalic = new Font(Font.FontFamily.TIMES_ROMAN, 15f, Font.BOLDITALIC, BaseColor.BLACK);
  Font mSmallFont = new Font(Font.FontFamily.TIMES_ROMAN, 3f);

  public void createVoucherReport(String pVoucherNo, Date pVoucherDate, OutputStream pOutputStream) throws Exception {

    Company company = mCompanyManager.getDefaultCompany();
    pVoucherNo = company.getId() + pVoucherNo;
    List<MutableAccountTransaction> accountTransactionList =
        mTransactionManager.getByVoucherNoAndDate(pVoucherNo, pVoucherDate);

    Document document = new Document();
    document.addTitle("Voucher Report");

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfWriter writer = PdfWriter.getInstance(document, baos);
    writer.setPageEvent(new VoucherReportHeaderAndFooter());
    document.open();
    document.setPageSize(PageSize.A4.rotate());
    document.newPage();

    UmsParagraph paragraph = new UmsParagraph(company.getName(), mHeaderFont);
    paragraph.setAlignment(Element.ALIGN_CENTER);
    document.add(paragraph);

    PdfPTable headerTable = createHeader(accountTransactionList);
    document.add(headerTable);

    PdfPTable table = createVoucherReportBody(accountTransactionList);
    document.add(table);

    document = createCreateDescriptionSection(document, accountTransactionList);

    document = createAuthorizationSection(document);

    document.close();
    baos.writeTo(pOutputStream);
  }

  private Document createAuthorizationSection(Document pDocument) throws Exception {

    PdfPTable pdfPTable = new PdfPTable(3);
    pdfPTable.setWidthPercentage(100);
    UmsCell cell = new UmsCell();

    UmsParagraph paragraph = new UmsParagraph("RECEIVED BY", mLiteFont);
    paragraph.setAlignment(Element.ALIGN_CENTER);
    cell.addElement(paragraph);
    cell.setBorder(Rectangle.NO_BORDER);
    pdfPTable.addCell(cell);

    cell = new UmsCell();
    paragraph = new UmsParagraph("PREPARED BY", mLiteFont);
    paragraph.setAlignment(Element.ALIGN_CENTER);
    cell.addElement(paragraph);
    cell.setBorder(Rectangle.NO_BORDER);
    pdfPTable.addCell(cell);

    cell = new UmsCell();
    paragraph = new UmsParagraph("AUTHORIZED BY", mLiteFont);
    paragraph.setAlignment(Element.ALIGN_CENTER);
    cell.addElement(paragraph);
    cell.setBorder(Rectangle.NO_BORDER);
    pdfPTable.addCell(cell);

    pdfPTable.setSpacingBefore(100);
    pDocument.add(pdfPTable);
    return pDocument;
  }

  private Document createCreateDescriptionSection(Document pDocument,
      List<MutableAccountTransaction> pAccountTransactionList) throws Exception {

    BigDecimal totalDebit = new BigDecimal(0);

    for(AccountTransaction accountTransaction : pAccountTransactionList) {
      if(accountTransaction.getBalanceType().equals(BalanceType.Dr))
        totalDebit = totalDebit.add(accountTransaction.getAmount());
    }

    UmsParagraph paragraph =
        new UmsParagraph("In words : " + Utils.convertNumberToWords(totalDebit) + " Only", mLiteFont);
    pDocument.add(paragraph);

    paragraph = new UmsParagraph("Narration : " + pAccountTransactionList.get(0).getNarration(), mLiteFont);
    pDocument.add(paragraph);
    return pDocument;
  }

  private PdfPTable createHeader(List<MutableAccountTransaction> pAccountTransactionList) {
    float[] tableWidth = new float[] {4, 4, 2, 2};
    PdfPTable table = new PdfPTable(tableWidth);
    table.setWidthPercentage(100);
    UmsCell cell = new UmsCell();

    String voucherNo = pAccountTransactionList.get(0).getVoucherNo().substring(2);
    UmsParagraph paragraph = new UmsParagraph("Voucher No :" + voucherNo, mLiteFont);
    cell.addElement(paragraph);
    cell.setBorder(Rectangle.NO_BORDER);
    table.addCell(cell);

    Voucher voucher = pAccountTransactionList.get(0).getVoucher();
    Chunk chunk = new Chunk(voucher.getName().toUpperCase(), mBoldFont);
    chunk.setUnderline(1, -4);
    paragraph = new UmsParagraph(chunk);
    paragraph.setAlignment(Element.ALIGN_CENTER);
    cell = new UmsCell(new UmsParagraph(" "));
    cell.addElement(paragraph);
    cell.setBorder(Rectangle.NO_BORDER);
    table.addCell(cell);

    cell = new UmsCell();
    paragraph = new UmsParagraph(" ", mLiteFont);
    paragraph.setAlignment(Element.ALIGN_RIGHT);
    cell.addElement(paragraph);
    paragraph = new UmsParagraph(" ", mLiteFont);
    paragraph.setAlignment(Element.ALIGN_RIGHT);
    cell.addElement(paragraph);
    cell.setBorder(Rectangle.NO_BORDER);
    table.addCell(cell);

    cell = new UmsCell();
    String voucherDateStr = UmsUtils.formatDate(pAccountTransactionList.get(0).getVoucherDate(), "dd-MM-yyyy");
    String postDateStr =
        pAccountTransactionList.get(0).getPostDate() == null ? "Not Posted" : UmsUtils.formatDate(
            pAccountTransactionList.get(0).getPostDate(), "dd-MM-yyyy");
    paragraph = new UmsParagraph("Voucher Date : " + voucherDateStr, mLiteFont);
    paragraph.setAlignment(Element.ALIGN_RIGHT);
    cell.addElement(paragraph);
    paragraph = new UmsParagraph("Post Date : " + postDateStr, mLiteFont);
    paragraph.setAlignment(Element.ALIGN_RIGHT);
    cell.addElement(paragraph);
    cell.setBorder(Rectangle.NO_BORDER);
    table.addCell(cell);

    return table;
  }

  private PdfPTable createVoucherReportBody(List<MutableAccountTransaction> pAccountTransactionList){
      List<Long> transactionIdList = pAccountTransactionList
              .stream()
              .map(t-> t.getId())
              .collect(Collectors.toList());
      List<MutableChequeRegister> chequeRegisterList = mChequeRegisterManager.getByTransactionIdList(transactionIdList);
      Map<Long, MutableChequeRegister> chequeRegisterMapWithTransactionid = chequeRegisterList
              .stream()
              .collect(Collectors.toMap(t->t.getAccountTransactionId(), t->t));

      float[] tableWidth = new float[]{1,4,1,2,2,2};
      PdfPTable voucherBodyTable = new PdfPTable(tableWidth);
      voucherBodyTable.setWidthPercentage(100);
      voucherBodyTable.setSpacingBefore(25f);
      voucherBodyTable.setSpacingAfter(25f);
      voucherBodyTable = createVoucherBodyHeader(voucherBodyTable);

      BigDecimal totalDebit = new BigDecimal(0);
      BigDecimal totalCredit = new BigDecimal(0);

      for(MutableAccountTransaction accountTransaction: pAccountTransactionList){
          voucherBodyTable = createVoucherBodyRow(accountTransaction, chequeRegisterMapWithTransactionid, voucherBodyTable);

          if(accountTransaction.getBalanceType().equals(BalanceType.Dr))
              totalDebit = totalDebit.add(accountTransaction.getAmount());
          else
              totalCredit = totalCredit.add(accountTransaction.getAmount());
      }

      addTotalSection(voucherBodyTable, totalDebit, totalCredit);

      return voucherBodyTable;
  }

  private void addTotalSection(PdfPTable voucherBodyTable, BigDecimal totalDebit, BigDecimal totalCredit) {
    UmsCell cell = new UmsCell();
    UmsParagraph paragraph = new UmsParagraph("Total    ", mBoldFont);
    paragraph.setAlignment(Element.ALIGN_RIGHT);
    cell = new UmsCell(paragraph);
    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    cell.setVerticalAlignment(Element.ALIGN_CENTER);
    cell.setColspan(4);
    voucherBodyTable.addCell(cell);

    paragraph = new UmsParagraph(Utils.getFormattedBalance(totalDebit), mBoldFont);
    paragraph.setAlignment(Element.ALIGN_RIGHT);
    cell = new UmsCell(paragraph);
    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    cell.setVerticalAlignment(Element.ALIGN_CENTER);
    voucherBodyTable.addCell(cell);

    paragraph = new UmsParagraph(Utils.getFormattedBalance(totalCredit), mBoldFont);
    paragraph.setAlignment(Element.ALIGN_RIGHT);
    cell = new UmsCell(paragraph);
    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    cell.setVerticalAlignment(Element.ALIGN_CENTER);
    voucherBodyTable.addCell(cell);
  }

  private PdfPTable createVoucherBodyRow(MutableAccountTransaction pMutableAccountTransaction,
      Map<Long, MutableChequeRegister> chequeRegisterMapWithTransactionId, PdfPTable pTable) {

    int serialHelper = 0;
    if(pMutableAccountTransaction.getSerialNo() == 0)
      serialHelper += 1;
    UmsCell cell = new UmsCell();
    UmsParagraph paragraph =
        new UmsParagraph((pMutableAccountTransaction.getSerialNo() + serialHelper) + "", mLiteFont);
    cell = new UmsCell(paragraph);
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    pTable.addCell(cell);

    paragraph = new UmsParagraph(pMutableAccountTransaction.getAccount().getAccountName(), mLiteFont);
    cell = new UmsCell(paragraph);
    pTable.addCell(cell);

    paragraph = new UmsParagraph(" ", mLiteFont);
    cell = new UmsCell(paragraph);
    pTable.addCell(cell);

    cell = addChequeSection(pMutableAccountTransaction, chequeRegisterMapWithTransactionId);
    pTable.addCell(cell);

    pTable = addDebitAndCreditSection(pTable, pMutableAccountTransaction);

    return pTable;
  }

  private PdfPTable addDebitAndCreditSection(PdfPTable pTable, MutableAccountTransaction pMutableAccountTransaction) {

    UmsCell cell = new UmsCell();
    UmsParagraph paragraph = new UmsParagraph();

    String modifiedAmount = Utils.getFormattedBalance(pMutableAccountTransaction.getAmount());
    if(pMutableAccountTransaction.getBalanceType().equals(BalanceType.Dr)) {
      paragraph = new UmsParagraph(modifiedAmount, mLiteFont);
      paragraph.setAlignment(Element.ALIGN_RIGHT);
      cell = new UmsCell(paragraph);
      cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
      pTable.addCell(cell);

      cell = new UmsCell(new UmsParagraph(" "));
      pTable.addCell(cell);
    }
    else {
      cell = new UmsCell(new UmsParagraph(" "));
      pTable.addCell(cell);

      cell = new UmsCell();
      paragraph = new UmsParagraph(modifiedAmount, mLiteFont);
      paragraph.setAlignment(Element.ALIGN_RIGHT);
      cell = new UmsCell(paragraph);
      cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
      pTable.addCell(cell);
    }

    return pTable;
  }

  private UmsCell addChequeSection(MutableAccountTransaction pMutableAccountTransaction,
      Map<Long, MutableChequeRegister> pChequeRegisterMapWithTransactionId) {
    UmsCell cell = new UmsCell();

    ChequeRegister chequeRegister =
        pChequeRegisterMapWithTransactionId.containsKey(pMutableAccountTransaction.getId()) ? pChequeRegisterMapWithTransactionId
            .get(pMutableAccountTransaction.getId()) : null;

    UmsParagraph paragraph = new UmsParagraph();
    if(chequeRegister != null) {
      cell = new UmsCell();
      paragraph = new UmsParagraph(chequeRegister.getChequeNo(), mLiteFont);
      cell.addElement(paragraph);
      paragraph = new UmsParagraph(UmsUtils.formatDate(chequeRegister.getChequeDate(), "dd-MM-yyyy"), mLiteFont);
      paragraph.setAlignment(Element.ALIGN_RIGHT);
      cell.addElement(paragraph);
    }
    else {
      cell = new UmsCell(new UmsParagraph(" "));
    }

    return cell;
  }

  private PdfPTable createVoucherBodyHeader(PdfPTable pTable) {

    UmsCell cell = new UmsCell();
    UmsParagraph paragraph = new UmsParagraph("S. No", mBoldFont);
    cell = new UmsCell(paragraph);
    pTable.addCell(cell);

    cell = new UmsCell(new UmsParagraph("Head of Account", mBoldFont));
    pTable.addCell(cell);

    cell = new UmsCell(new UmsParagraph("Reference", mBoldFont));
    pTable.addCell(cell);

    paragraph = new UmsParagraph("Cheque No & Date", mBoldFont);
    cell = new UmsCell(paragraph);
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    pTable.addCell(cell);

    paragraph = new UmsParagraph("Debit", mBoldFont);
    cell = new UmsCell(paragraph);
    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    pTable.addCell(cell);

    paragraph = new UmsParagraph("Credit", mBoldFont);
    cell = new UmsCell(paragraph);
    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    pTable.addCell(cell);
    return pTable;
  }

  class VoucherReportHeaderAndFooter extends PdfPageEventHelper {
    @Override
    public void onEndPage(PdfWriter writer, Document pDocument) {
      PdfContentByte cb = writer.getDirectContent();
      String text = String.format("Page %s", writer.getCurrentPageNumber());
      UmsParagraph paragraph = new UmsParagraph(text, mBoldFont);
      ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, new Phrase(paragraph),
          (pDocument.right() - pDocument.left()) / 2 + pDocument.leftMargin(), pDocument.bottom() - 10, 0);
    }
  }
}
