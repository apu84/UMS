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
import org.ums.domain.model.mutable.accounts.MutableReceipt;
import org.ums.manager.CompanyManager;
import org.ums.manager.accounts.AccountTransactionManager;
import org.ums.manager.accounts.ChequeRegisterManager;
import org.ums.manager.accounts.ReceiptManager;
import org.ums.manager.accounts.VoucherManager;
import org.ums.report.balance.sheet.BalanceSheetReportGenerator;
import org.ums.util.UmsUtils;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
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
  @Autowired
  private ReceiptManager mReceiptManager;
  @Autowired
  private VoucherManager mVoucherManager;

  Font mLiteFont = new Font(Font.FontFamily.TIMES_ROMAN, 10);
  Font mBoldFont = new Font(Font.FontFamily.TIMES_ROMAN, 10f, Font.BOLD, BaseColor.BLACK);
  Font mHeaderFont = new Font(Font.FontFamily.TIMES_ROMAN, 15f, Font.BOLD, BaseColor.BLACK);
  Font mLiteFontItalic = new Font(Font.FontFamily.TIMES_ROMAN, 10f, Font.ITALIC, BaseColor.BLACK);
  Font mBoldFontItalic = new Font(Font.FontFamily.TIMES_ROMAN, 10f, Font.BOLDITALIC, BaseColor.BLACK);
  Font mHeaderFontItalic = new Font(Font.FontFamily.TIMES_ROMAN, 15f, Font.BOLDITALIC, BaseColor.BLACK);
  Font mSmallFont = new Font(Font.FontFamily.TIMES_ROMAN, 3f);

  public void createVoucherReport(String pVoucherNo, Date pVoucherDate, OutputStream pOutputStream) throws Exception {
    List<MutableAccountTransaction> accountTransactionList =
        mTransactionManager.getByVoucherNoAndDate(pVoucherNo, pVoucherDate);
    Company company = mCompanyManager.getDefaultCompany();

    Document document = new Document();
    document.addTitle("Balance Sheet");

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfWriter writer = PdfWriter.getInstance(document, baos);
    writer.setPageEvent(new VoucherReportHeaderAndFooter());
    document.open();
    document.setPageSize(PageSize.A4.rotate());
    document.newPage();

    Paragraph paragraph = new Paragraph(company.getName(), mHeaderFont);
    paragraph.setAlignment(Element.ALIGN_CENTER);
    document.add(paragraph);

    PdfPTable headerTable = createHeader(accountTransactionList);
    document.add(headerTable);

    document.close();
    baos.writeTo(pOutputStream);
  }

  private PdfPTable createHeader(List<MutableAccountTransaction> pAccountTransactionList) {
    float[] tableWidth = new float[] {4, 4, 2, 2};
    PdfPTable table = new PdfPTable(tableWidth);
    table.setWidthPercentage(90);
    PdfPCell cell = new PdfPCell();

    String voucherNo = pAccountTransactionList.get(0).getVoucherNo().substring(2);
    Paragraph paragraph = new Paragraph("Voucher No :" + voucherNo, mLiteFont);
    cell.addElement(paragraph);
    cell.setBorder(Rectangle.NO_BORDER);
    table.addCell(cell);

    Voucher voucher = pAccountTransactionList.get(0).getVoucher();
    Chunk chunk = new Chunk(voucher.getName(), mBoldFont);
    chunk.setUnderline(1, 1);
    paragraph = new Paragraph(chunk);
    paragraph.setAlignment(Element.ALIGN_CENTER);
    cell = new PdfPCell(paragraph);
    cell.setBorder(Rectangle.NO_BORDER);
    table.addCell(cell);

    cell = new PdfPCell();
    paragraph = new Paragraph("Voucher Date : ", mLiteFont);
    paragraph.setAlignment(Element.ALIGN_RIGHT);
    cell.addElement(paragraph);
    paragraph = new Paragraph("Post Date : ", mLiteFont);
    paragraph.setAlignment(Element.ALIGN_RIGHT);
    cell.addElement(paragraph);
    cell.setBorder(Rectangle.NO_BORDER);
    table.addCell(cell);

    cell = new PdfPCell();
    String voucherDateStr = UmsUtils.formatDate(pAccountTransactionList.get(0).getVoucherDate(), "dd-MM-yyyy");
    String postDateStr =
        pAccountTransactionList.get(0).getPostDate() == null ? "Not Posted" : UmsUtils.formatDate(
            pAccountTransactionList.get(0).getPostDate(), "dd-MM-yyyy");
    paragraph = new Paragraph(voucherDateStr, mLiteFont);
    cell.addElement(paragraph);
    paragraph = new Paragraph(postDateStr, mLiteFont);
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
      voucherBodyTable = createVoucherBodyHeader(voucherBodyTable);


      return voucherBodyTable;
  }

  private PdfPTable createVoucherBodyRow(MutableAccountTransaction pMutableAccountTransaction,
      Map<Long, MutableChequeRegister> chequeRegisterMapWithTransactionId, PdfPTable pTable) {

    int serialHelper = 0;
    if(pMutableAccountTransaction.getSerialNo() == 0)
      serialHelper += 1;
    PdfPCell cell = new PdfPCell();
    Paragraph paragraph = new Paragraph((pMutableAccountTransaction.getSerialNo() + serialHelper) + "", mLiteFont);
    cell.addElement(paragraph);
    pTable.addCell(cell);

    paragraph = new Paragraph(pMutableAccountTransaction.getAccount().getAccountName(), mLiteFont);
    cell = new PdfPCell(paragraph);
    pTable.addCell(cell);

    paragraph = new Paragraph(" ", mLiteFont);
    cell = new PdfPCell(paragraph);
    pTable.addCell(cell);

    ChequeRegister chequeRegister =
        chequeRegisterMapWithTransactionId.containsKey(pMutableAccountTransaction.getId()) ? chequeRegisterMapWithTransactionId
            .get(pMutableAccountTransaction.getId()) : null;

    if(chequeRegister != null) {
      paragraph = new Paragraph(chequeRegister.getChequeNo(), mLiteFont);
      cell = new PdfPCell(paragraph);
      paragraph = new Paragraph(UmsUtils.formatDate(chequeRegister.getChequeDate(), "dd-MM-yyyy"), mLiteFont);
      paragraph.setAlignment(Element.ALIGN_RIGHT);
      cell.addElement(paragraph);
    }
    else {
      cell = new PdfPCell(new Paragraph(" "));
    }
    pTable.addCell(cell);

    return pTable;
  }

  private PdfPTable createVoucherBodyHeader(PdfPTable pTable) {

    PdfPCell cell = new PdfPCell();
    Paragraph paragraph = new Paragraph("S. No", mBoldFont);
    cell.addElement(paragraph);
    pTable.addCell(cell);

    cell = new PdfPCell(new Paragraph("Head of Account", mBoldFont));
    pTable.addCell(cell);

    cell = new PdfPCell(new Paragraph("Reference", mBoldFont));
    pTable.addCell(cell);

    paragraph = new Paragraph("Cheque No & Date", mBoldFont);
    paragraph.setAlignment(Element.ALIGN_CENTER);
    cell = new PdfPCell(paragraph);
    pTable.addCell(cell);

    paragraph = new Paragraph("Debit", mBoldFont);
    paragraph.setAlignment(Element.ALIGN_RIGHT);
    cell = new PdfPCell(paragraph);
    pTable.addCell(cell);

    paragraph = new Paragraph("Credit", mBoldFont);
    paragraph.setAlignment(Element.ALIGN_RIGHT);
    cell = new PdfPCell(paragraph);
    pTable.addCell(cell);
    return pTable;
  }

  class VoucherReportHeaderAndFooter extends PdfPageEventHelper {
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
