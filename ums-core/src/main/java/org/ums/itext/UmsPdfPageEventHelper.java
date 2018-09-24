package org.ums.itext;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import org.ums.util.UmsUtils;

/**
 * Created by Monjur-E-Morshed on 11-Aug-18.
 */
public class UmsPdfPageEventHelper extends PdfPageEventHelper {

  @Override
  public void onStartPage(PdfWriter writer, Document document) {
    PdfContentByte cb = writer.getDirectContent();
    ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, new Phrase(UmsUtils.getIUMSHeaderParagraph()),
        document.right() + 10, document.top() + 10, 0);
  }

  @Override
  public void onEndPage(PdfWriter writer, Document document) {
    PdfContentByte cb = writer.getDirectContent();
    ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, new Phrase(UmsUtils.getIUMSHeaderParagraph()),
        document.right() + 10, document.bottom() + 10, 0);
  }
}
