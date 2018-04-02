package org.ums.util;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import org.ums.domain.model.immutable.accounts.AccountTransaction;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 24-Mar-18.
 */
public class UmsAccountUtils {
  public static String getBalanceInDebitOrCredit(BigDecimal pBalance) {
    String balanceType = pBalance.compareTo(new BigDecimal(0)) >= 0 ? " Dr" : " Cr";
    String balanceFormat = NumberFormat.getCurrencyInstance().format(pBalance.abs());
    balanceFormat = balanceFormat.substring(1);
    return balanceFormat + balanceType;
  }

  public static String getFormattedBalance(BigDecimal pBalance) {
    String formattedCurrency = NumberFormat.getCurrencyInstance().format(pBalance);
    if(formattedCurrency.contains("(") || formattedCurrency.contains(")")) {
      formattedCurrency = formattedCurrency.replace("(", "");
      formattedCurrency = formattedCurrency.replace(")", "");
      formattedCurrency = "-" + formattedCurrency;
    }
    return formattedCurrency.replace("$", "");
  }

  public static BigDecimal countTotalAmount(List<AccountTransaction> pAccountTransactionList) {
    BigDecimal totalBalance = new BigDecimal(0);
    for(AccountTransaction transaction : pAccountTransactionList) {
      totalBalance = totalBalance.add(transaction.getAmount());
    }
    return totalBalance;
  }

  public static void setTopAndBottomBorderAndAddCell(PdfPTable table, PdfPCell cell) {
    cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
    cell.setPaddingBottom(5);
    table.addCell(cell);
  }

  public static void setNoBorderAndAddCell(PdfPTable table, PdfPCell cell) {
    cell.setBorder(Rectangle.NO_BORDER);
    cell.setPaddingBottom(5);
    table.addCell(cell);
  }

  public static void setTopBorderAndAddCell(PdfPTable table, PdfPCell cell) {
    cell.setBorder(Rectangle.TOP);
    cell.setPaddingBottom(5);
    table.addCell(cell);
  }
}
