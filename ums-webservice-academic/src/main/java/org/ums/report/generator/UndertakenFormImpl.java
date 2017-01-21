package org.ums.report.generator;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class UndertakenFormImpl implements UndertakenFormGenerator {

  public static final String DEST = "UndertakenForm.pdf";

  @Override
  public void createUndertakenForm(OutputStream pOutputStream) throws IOException,
      DocumentException {

    Document document = new Document();
    document.addTitle("UnderTaking Form");

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfWriter writer = PdfWriter.getInstance(document, baos);

    document.open();
    document.setPageSize(PageSize.A4);

    Paragraph helloParagraph =
        new Paragraph("Hello World", FontFactory.getFont(FontFactory.TIMES_ROMAN, 14));
    document.add(helloParagraph);
    document.close();
    baos.writeTo(pOutputStream);
  }
}
