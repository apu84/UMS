package org.ums.common.report.generator;

import java.io.*;
import java.util.*;
import java.util.List;

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
    document.setMargins(10,10,30,72);
// step 2
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfWriter writer = PdfWriter.getInstance(document, baos); // FileOutputStream(RESULT)

    HeaderFooter event = new HeaderFooter();
    writer.setBoxSize("art", new Rectangle(36, 54, 559, 788));
    writer.setPageEvent(event);

    // step 3

    List<String> studentList=new ArrayList<>();
    for(int i=0;i<66;i++){
      studentList.add(i,"Student-"+i);
    }
    document.open();
    for (int i=0;i<(studentList.size()/90)+1;i++) {
      PdfPTable mainTable=getMainTable();
      PdfPCell cell = new PdfPCell(getSubTableHeader());
      cell.setPadding(0);
      mainTable.addCell(cell);

      cell = new PdfPCell(new Paragraph(""));
      cell.setBorder(Rectangle.NO_BORDER);
      mainTable.addCell(cell);

      cell = new PdfPCell(getSubTableHeader());
      cell.setPadding(0);
      mainTable.addCell(cell);

      cell = new PdfPCell();
      cell.addElement(getGradeTable(studentList,i*90,45));
      cell.setPadding(0);
      cell.setBorder(Rectangle.NO_BORDER);
      mainTable.addCell(cell);

      cell = new PdfPCell(new Paragraph(""));
      cell.setBorder(Rectangle.NO_BORDER);
      mainTable.addCell(cell);

      cell = new PdfPCell();
      cell.addElement(getGradeTable(studentList,i*90+46,45));
      cell.setPadding(0);
      cell.setBorder(Rectangle.NO_BORDER);
      mainTable.addCell(cell);

      document.add(mainTable);

      document.newPage();
    }
    // step 4

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

    return table;
  }

  public PdfPTable getGradeTable(java.util.List<String> studentList,int startIndex,int total) throws DocumentException {
    // a table with three columns
    if(startIndex>studentList.size())
      return null;

    Font fontAUST = new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL);
    Font gradeSheetHeader = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
    Font nFont = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL);
    Font nuFont = new Font(Font.FontFamily.HELVETICA, 8, Font.UNDERLINE);
    Font nbFont = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD);

    PdfPTable table = new PdfPTable(6);
    table.setWidths(new int[]{2, 1,1,1,1,1});
    table.setWidthPercentage(100);


    Paragraph p = new Paragraph("Student No.", nbFont);
    PdfPCell cell = new PdfPCell(p);
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    table.addCell(cell);

    p = new Paragraph("Quiz\n(20).", nbFont);
    cell = new PdfPCell(p);
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    table.addCell(cell);

    p = new Paragraph("Class \nPerf.\n(10).", nbFont);
    cell = new PdfPCell(p);
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    table.addCell(cell);

    p = new Paragraph("Final\n(70)", nbFont);
    cell = new PdfPCell(p);
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    table.addCell(cell);

    p = new Paragraph("Total\n(100)", nbFont);
    cell = new PdfPCell(p);
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    table.addCell(cell);

    p = new Paragraph("Letter\nGrade", nbFont);
    cell = new PdfPCell(p);
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    table.addCell(cell);

  for(int i=startIndex;i<startIndex+total+1;i++){
    if(i>=studentList.size())
      break;
    p = new Paragraph(studentList.get(i), nbFont);
    cell = new PdfPCell(p);
    cell.setFixedHeight(12);
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    table.addCell(cell);

    p = new Paragraph("9", nFont);
    cell = new PdfPCell(p);
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    table.addCell(cell);

    p = new Paragraph("9", nFont);
    cell = new PdfPCell(p);
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    table.addCell(cell);

    p = new Paragraph("24", nFont);
    cell = new PdfPCell(p);
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    table.addCell(cell);

    p = new Paragraph("42", nFont);
    cell = new PdfPCell(p);
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    table.addCell(cell);

    p = new Paragraph("D", nFont);
    cell = new PdfPCell(p);
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    table.addCell(cell);
  }
    return table;
  }
  public PdfPTable getSubTableHeader() throws DocumentException{
    // a table with three columns
    Font fontAUST = new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL);
    Font gradeSheetHeader = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
    Font nFont = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL);
    Font nuFont = new Font(Font.FontFamily.HELVETICA, 8, Font.UNDERLINE);

    PdfPTable table = new PdfPTable(3);
    table.setWidths(new int[]{7, 5, 8});
    table.setWidthPercentage(100);

    PdfPCell pCell=new PdfPCell();
    pCell.setBorder(Rectangle.NO_BORDER);
    pCell.setColspan(3);
    pCell.setHorizontalAlignment(Element.ALIGN_CENTER);

    PdfPCell pCellB=new PdfPCell();
    pCellB.setColspan(3);
    pCellB.setHorizontalAlignment(Element.ALIGN_CENTER);

    Paragraph p=new Paragraph("AHSANULLAH UNIVERSITY OF SCIENCE AND TECHNOLOGY", fontAUST);
    PdfPCell cell = new PdfPCell(p);
    cell.setColspan(3);
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    table.addCell(cell);

    p=new Paragraph("Marks/Grade Sheet Submission (Spring 2015)", gradeSheetHeader);
    cell = new PdfPCell(p);
    cell.setColspan(3);
    cell.setBorder(Rectangle.NO_BORDER);
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    table.addCell(cell);

    p=new Paragraph("Theoretical", gradeSheetHeader);
    cell = new PdfPCell(p);
    cell.setColspan(3);
    cell.setBorder(Rectangle.NO_BORDER);
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    table.addCell(cell);

    p=new Paragraph("Examination : Final", nFont);
    cell = new PdfPCell(p);
    cell.setColspan(3);
    cell.setBorder(Rectangle.NO_BORDER);
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    table.addCell(cell);

    Paragraph parag1=new Paragraph("Department : ",nFont);//This gonna be bold font
    Paragraph parag2=new Paragraph("CSE ", nuFont); //This gonna be normal font
    Paragraph comb=new Paragraph();
    comb.add(parag1);
    comb.add(parag2);
    cell = new PdfPCell(comb);
    cell.setColspan(3);
    cell.setBorder(Rectangle.NO_BORDER);
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    table.addCell(cell);

    parag1=new Paragraph("Year : ",nFont);//This gonna be bold font
    parag2=new Paragraph("1st ", nuFont); //This gonna be normal font
    comb=new Paragraph();
    comb.add(parag1);
    comb.add(parag2);
    cell = new PdfPCell(comb);
    cell.setBorder(Rectangle.NO_BORDER);
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    table.addCell(cell);

    parag1=new Paragraph("Semester : ",nFont);//This gonna be bold font
    parag2=new Paragraph("1st ", nuFont); //This gonna be normal font
    comb=new Paragraph();
    comb.add(parag1);
    comb.add(parag2);
    cell = new PdfPCell(comb);
    cell.setColspan(2);
    cell.setBorder(Rectangle.NO_BORDER);
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    table.addCell(cell);

    parag1=new Paragraph("Course No : ",nFont);//This gonna be bold font
    parag2=new Paragraph("CSE 1101 ", nuFont); //This gonna be normal font
    comb=new Paragraph();
    comb.add(parag1);
    comb.add(parag2);
    cell = new PdfPCell(comb);
    cell.setColspan(2);
    cell.setBorder(Rectangle.NO_BORDER);
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    table.addCell(cell);

    parag1=new Paragraph("Course Title : ",nFont);//This gonna be bold font
    parag2=new Paragraph("Cjhemistry ", nuFont); //This gonna be normal font
    comb=new Paragraph();
    comb.add(parag1);
    comb.add(parag2);
    cell = new PdfPCell(comb);
    cell.setColspan(2);
    cell.setBorder(Rectangle.NO_BORDER);
    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    table.addCell(cell);

    p=new Paragraph("(The number of students registered for the course is - 207)", nFont);
    cell = new PdfPCell(p);
    cell.setColspan(3);
    cell.setBorder(Rectangle.NO_BORDER);
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    table.addCell(cell);

    parag1=new Paragraph("Credit Hour : ",nFont);//This gonna be bold font
    parag2=new Paragraph("3.0 ", nuFont); //This gonna be normal font
    comb=new Paragraph();
    comb.add(parag1);
    comb.add(parag2);
    cell = new PdfPCell(comb);
    cell.setColspan(2);
    cell.setFixedHeight(14);
    cell.setBorder(Rectangle.NO_BORDER);
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    table.addCell(cell);

    parag1=new Paragraph("Full Marks : ",nFont);//This gonna be bold font
    parag2=new Paragraph("100 ", nuFont); //This gonna be normal font
    comb=new Paragraph();
    comb.add(parag1);
    comb.add(parag2);
    cell = new PdfPCell(comb);
    cell.setColspan(2);
    cell.setFixedHeight(14);
    cell.setBorder(Rectangle.NO_BORDER);
    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    table.addCell(cell);
    return table;
  }
  /** Inner class to add a header and a footer. */
  class HeaderFooter extends PdfPageEventHelper {
    int pagenumber;
    public void onStartPage(PdfWriter writer,Document document) {
      pagenumber++;
    }
    public void onEndPage(PdfWriter writer, Document document)  {
//      Rectangle rect = writer.getBoxSize("art");
//      ColumnText.showTextAligned(writer.getDirectContent(),
//          Element.ALIGN_CENTER, new Phrase(String.format("page %d", pagenumber)),
//          (rect.getLeft() + rect.getRight()) / 2, rect.getBottom() - 18, 0);
      Font nFont = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL);

try {
  PdfPTable table = new PdfPTable(3);
  table.setWidths(new int[]{10,10,10});
  //table.setWidthPercentage(80);
  table.setTotalWidth(523);

  Paragraph p=new Paragraph("_________________\n\nSignature of the\nScrutinizer\n\nDate ___________", nFont);
  PdfPCell pCell = new PdfPCell(p);
  pCell.setBorder(Rectangle.NO_BORDER);
  pCell.setHorizontalAlignment(Element.ALIGN_LEFT);
  pCell.setPaddingLeft(20);
  table.addCell(pCell);

  p=new Paragraph("_________________\n\nSignature of the \nHead of the Dept./School\n\nDate ___________", nFont);
  pCell = new PdfPCell(p);
  pCell.setBorder(Rectangle.NO_BORDER);
  pCell.setHorizontalAlignment(Element.ALIGN_LEFT);
  pCell.setPaddingLeft(60);
  table.addCell(pCell);

  p=new Paragraph("_________________\n\nSignature of the \nExaminer\n\nDate ___________", nFont);
  pCell = new PdfPCell(p);
  pCell.setBorder(Rectangle.NO_BORDER);
  pCell.setHorizontalAlignment(Element.ALIGN_LEFT);
  pCell.setPaddingLeft(80);
  table.addCell(pCell);

  table.writeSelectedRows(0, -1, 36, 64, writer.getDirectContent());


}
catch (Exception ex){
  ex.printStackTrace();
}
    }
  }

}
