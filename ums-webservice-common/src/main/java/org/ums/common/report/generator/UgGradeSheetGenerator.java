package org.ums.common.report.generator;

import java.io.*;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

/**
 * Created by Ifti on 07-Jun-16.
 */
//http://developers.itextpdf.com/examples/itext-action-second-edition/chapter-4
public class UgGradeSheetGenerator {

  /** Path to the resulting PDF file. */
  public static final String RESULT
      = "d://hello.pdf";


  /**
   * Creates a PDF document.
   * @param filename the path to the new PDF document
   * @throws    DocumentException
   * @throws    IOException
   */
  public void createPdf(OutputStream outputStream,String filename)
      throws DocumentException, IOException {
    // step 1
    Document document = new Document();

// step 2
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfWriter writer = PdfWriter.getInstance(document, baos); // FileOutputStream(RESULT)

    HeaderFooter event = new HeaderFooter();
    writer.setBoxSize("art", new Rectangle(36, 54, 559, 788));
    writer.setPageEvent(event);

    // step 3

    document.open();
    for (int i=0;i<4;i++) {
      PdfPTable mainTable=getMainTable();
      mainTable.addCell(getSubTableHeader());
      mainTable.addCell("");
      mainTable.addCell(getSubTableHeader());
      document.add(mainTable);
      document.newPage();
    }
    // step 4


    document.add(new Paragraph("Hello World!"));
    // step 5
    document.close();
    baos.writeTo(outputStream);
  }
  public static PdfPTable getMainTable() throws DocumentException{
    // a table with three columns
    PdfPTable table = new PdfPTable(3);
    table.setWidths(new int[]{9, 2, 9});
    table.setWidthPercentage(100);
    // cell 1: location and time
    table.setWidthPercentage(100f);

    return table;
  }

  public static PdfPTable getSubTableHeader() throws DocumentException{
    // a table with three columns
    PdfPTable table = new PdfPTable(3);
    table.setWidths(new int[]{7, 7, 6});
    table.setWidthPercentage(100);

    PdfPCell cell;
    cell = new PdfPCell(new Phrase("AHSANULLAH UNIVERSITY OF SCIENCE AND TECHNOLOGY"));
    cell.setColspan(3);
    table.addCell(cell);

    return table;
  }
  /** Inner class to add a header and a footer. */
  class HeaderFooter extends PdfPageEventHelper {
    int pagenumber;
    public void onStartPage(PdfWriter writer,Document document) {
      pagenumber++;
    }
    public void onEndPage(PdfWriter writer, Document document) {
      Rectangle rect = writer.getBoxSize("art");
      ColumnText.showTextAligned(writer.getDirectContent(),
          Element.ALIGN_CENTER, new Phrase(String.format("page %d", pagenumber)),
          (rect.getLeft() + rect.getRight()) / 2, rect.getBottom() - 18, 0);
    }
  }

}
