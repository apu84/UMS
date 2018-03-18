package org.ums.report.generator.teachersEvaluationReport;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.academic.resource.teacher.evaluation.system.helper.Report;
import org.ums.academic.resource.teacher.evaluation.system.helper.StudentComment;
import org.ums.domain.model.immutable.ApplicationTES;
import org.ums.domain.model.immutable.CourseTeacher;
import org.ums.formatter.DateFormat;
import org.ums.manager.ApplicationTESManager;
import org.ums.manager.CourseManager;

import javax.swing.*;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static com.itextpdf.text.Rectangle.NO_BORDER;
import static com.itextpdf.text.Rectangle.RIGHT;

/**
 * Created by Rumi on 3/15/2018.
 */
@Component
public class TesGeneratorImp implements TesGenerator {
  @Autowired
  DateFormat mDateFormat;
  @Autowired
  ApplicationTESManager mApplicationTESManager;
  private String pCourseId;

  @Override
  public void createTesReport(String pCourseId, String pTeacherId, Integer pSemesterId, OutputStream pOutputStream)
      throws IOException, DocumentException {
    this.pCourseId=pCourseId;

    mDateFormat = new DateFormat("dd MMM YYYY");
    Document document = new Document(PageSize.A4.rotate());
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfWriter writer = PdfWriter.getInstance(document, baos);

    Font fontTimes11Normal = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11);
    Font fontTimes11Bold = FontFactory.getFont(FontFactory.TIMES_BOLD, 12);
    Font fontTimes14Bold = FontFactory.getFont(FontFactory.TIMES_BOLD, 14);
    document.open();
   // document.setPageSize(PageSize.A4.rotate());

    Paragraph paragraph = null;
    Chunk chunk = null;


    chunk = new Chunk("Teachers Evaluation Report\nAhsanullah University Of Science and Technology");

    paragraph = new Paragraph();
    paragraph.setAlignment(Element.ALIGN_CENTER);
    paragraph.setFont(fontTimes14Bold);
    paragraph.add(chunk);
    emptyLine(paragraph, 1);
    document.add(paragraph);
    //
    chunk = new Chunk("Course No: CSE 2201\n" +
            "Course Title: Numerical Methods\nTeacher Name: Mrs. Shanjida khatun\n" +
            "Department: Computer Science and Engineering");

    paragraph = new Paragraph();
    paragraph.setAlignment(Element.ALIGN_LEFT);
    paragraph.setFont(fontTimes11Bold);
    paragraph.add(chunk);
    document.add(paragraph);
    //

    chunk = new Chunk("Date: " + mDateFormat.format(new Date()));
    chunk.setFont(fontTimes11Normal);
    paragraph = new Paragraph();
    paragraph.setAlignment(Element.ALIGN_RIGHT);
    paragraph.setFont(fontTimes11Normal);
    paragraph.add(chunk);
    emptyLine(paragraph, 2);
    document.add(paragraph);
    chunk = new Chunk(" ");
    paragraph=new Paragraph(" ");

    //general report

      double cRoomObservation=0,noncRoomObservation=0;
      Integer countercR=0, counterncR=0;
      double totalPointsObtype1=0, totalStudentsObtype1=0, totalPointsObtype2=0, totalStudentsObtype2=0;
      DecimalFormat newFormat = new DecimalFormat("#.##");
      HashMap<Integer,Double> mapForCalculateResult=new HashMap<Integer,Double>();
      java.util.List<Report> reportList= new ArrayList<Report>();
      Integer studentNo=mApplicationTESManager.getTotalStudentNumber("","",11);
      List<ApplicationTES> applications=mApplicationTESManager.getAllQuestions(11012017);

      applications.forEach(a->{
        double value=mApplicationTESManager.getAverageScore("","",a.getQuestionId(),11);
        String questionDetails=mApplicationTESManager.getQuestionDetails(a.getQuestionId());
        Integer observationType=mApplicationTESManager.getObservationType(a.getQuestionId());
        reportList.add(new Report(a.getQuestionId(),questionDetails,value,studentNo,(Double.valueOf(newFormat.format(value/studentNo))),observationType));
        mapForCalculateResult.put(a.getQuestionId(),(value/studentNo));
      });

      for(Map.Entry m:mapForCalculateResult.entrySet()){
        Integer questionId=(Integer)m.getKey();
        if(mApplicationTESManager.getObservationType(questionId) ==1){
          countercR++;
          cRoomObservation=cRoomObservation+(double)m.getValue();
        }else {
          counterncR++;
          noncRoomObservation=noncRoomObservation+(double)m.getValue();
        }
      }
      cRoomObservation=Double.valueOf(newFormat.format((cRoomObservation/countercR)));
      noncRoomObservation=Double.valueOf((newFormat.format(noncRoomObservation/counterncR)));

      for(int i=0;i<reportList.size();i++){
        if(reportList.get(i).getObservationType()==1){
          totalPointsObtype1=totalPointsObtype1+reportList.get(i).getTotalScore();
          totalStudentsObtype1=totalStudentsObtype1+reportList.get(i).getStudentNo();
        }else{
          totalPointsObtype2=totalPointsObtype2+reportList.get(i).getTotalScore();
          totalStudentsObtype2=totalStudentsObtype2+reportList.get(i).getStudentNo();
        }
      }
      //---
    Paragraph paragraph1 = null;
    Chunk chunk1 = null;


    chunk1 = new Chunk("Class Room Observation");

    paragraph1 = new Paragraph();
    paragraph1.setAlignment(Element.ALIGN_LEFT);
    paragraph1.setFont(fontTimes14Bold);
    paragraph1.add(chunk1);
    document.add(paragraph1);
    document.add(paragraph);
    //
    PdfPTable table= new PdfPTable(5);
    table.setWidthPercentage(100);
    table.setWidths(new float[] { 1, 10,1,1,1 });
    table.addCell("SL");
    table.addCell("Questions");
    table.addCell("Total Points");
    table.addCell("Total Students");
    table.addCell("Average");

   // document.add(table);
    int index=0;
    for(int i=0;i<reportList.size();i++){
      if(reportList.get(i).getObservationType()==1){
        index=index+1;
        table.addCell(""+index);
        table.addCell(""+reportList.get(i).getQuestionDetails());
        table.addCell(""+reportList.get(i).getTotalScore());
        table.addCell(""+reportList.get(i).getStudentNo());
        table.addCell(""+reportList.get(i).getAverageScore());
      }

    }
    try {
      document.add(table);
    } catch (DocumentException e) {
      e.printStackTrace();
    }
    //
    PdfPTable subset= new PdfPTable(5);
    subset.setWidthPercentage(100);
    subset.setWidths(new float[] { 1, 10,1,1,1 });
    subset.addCell("");
    subset.addCell("");
    subset.addCell(""+totalPointsObtype1);
    subset.addCell(""+totalStudentsObtype1);
    subset.addCell(""+cRoomObservation);
    try {
      document.add(subset);
    } catch (DocumentException e) {
      e.printStackTrace();
    }
    //
    document.add(paragraph);

    //
    Paragraph paragraph2 = null;
    Chunk chunk2 = null;


    chunk2 = new Chunk("Non-Class Room Observation");
    paragraph2 = new Paragraph();
    paragraph2.setAlignment(Element.ALIGN_LEFT);
    paragraph2.setFont(fontTimes14Bold);
    paragraph2.add(chunk2);
    document.add(paragraph2);
    paragraph=new Paragraph(" ");
    document.add(paragraph);
    //Non classRoom
    PdfPTable table1= new PdfPTable(5);
    table1.setWidthPercentage(100);
    table1.setWidths(new float[] { 1, 10,1,1,1 });
    table1.addCell("SL");
    table1.addCell("Questions");
    table1.addCell("Total Points");
    table1.addCell("Total Students");
    table1.addCell("Average");
    // document.add(table);

    for(int i=0;i<reportList.size();i++){
      if(reportList.get(i).getObservationType()==2){
        index=index+1;
        table1.addCell(""+index);
        table1.addCell(""+reportList.get(i).getQuestionDetails());
        table1.addCell(""+reportList.get(i).getTotalScore());
        table1.addCell(""+reportList.get(i).getStudentNo());
        table1.addCell(""+reportList.get(i).getAverageScore());
      }

    }
    try {
      document.add(table1);
    } catch (DocumentException e) {
      e.printStackTrace();
    }
    //
    PdfPTable subset1= new PdfPTable(5);
    subset1.setWidthPercentage(100);
    subset1.setWidths(new float[] { 1, 10,1,1,1 });
    subset1.addCell("");
    subset1.addCell("");
    subset1.addCell(""+totalPointsObtype2);
    subset1.addCell(""+totalStudentsObtype2);
    subset1.addCell(""+noncRoomObservation);
    try {
      document.add(subset1);
    } catch (DocumentException e) {
      e.printStackTrace();
    }
    //final result
    document.add(paragraph);
    PdfPTable tableGreen= new PdfPTable(5);
    tableGreen.setWidthPercentage(100);
    tableGreen.setWidths(new float[] { 1, 10,1,1,1 });
    PdfPCell cell=new PdfPCell(new Paragraph("   "));
    cell.setColspan(5);
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    cell.setBackgroundColor(BaseColor.GREEN);
    tableGreen.addCell(cell);
    tableGreen.addCell("");
    tableGreen.addCell("");
    tableGreen.addCell(""+(totalPointsObtype1+totalPointsObtype2));
    tableGreen.addCell(""+(totalStudentsObtype1+totalStudentsObtype2));
    tableGreen.addCell(""+(cRoomObservation+noncRoomObservation)/2);
    try {
      document.add(tableGreen);
    } catch (DocumentException e) {
      e.printStackTrace();
    }

    //comment Section
    List<ApplicationTES> getDetailedResult=null;
    List<StudentComment> commentList= new ArrayList<StudentComment>();

    for(int i=0;i<applications.size();i++){
      Integer questionId=applications.get(i).getQuestionId();
      Integer observationType=mApplicationTESManager.getObservationType(questionId);
      String questionDetails=mApplicationTESManager.getQuestionDetails(questionId);
      getDetailedResult=mApplicationTESManager.getDetailedResult("","",11).
              stream().
              filter(a->a.getComment() !=null && a.getQuestionId()==questionId).collect(Collectors.toList());
      int size=getDetailedResult.size();

      if(getDetailedResult.size() !=0){
        String comments[] =new String[size];
        for(int j=0;j<size;j++){
          comments[j]=getDetailedResult.get(j).getComment();
        }
        commentList.add(new StudentComment(questionId,comments,observationType,questionDetails));
      }
    }
    //Itext Pdf Comment
    document.add(paragraph);
    Paragraph paragraph3 = null;
    Chunk chunk3 = null;
    chunk3 = new Chunk("Student Comments");
    paragraph3 = new Paragraph();
    paragraph3.setAlignment(Element.ALIGN_LEFT);
    paragraph3.setFont(fontTimes14Bold);
    paragraph3.add(chunk3);
    document.add(paragraph3);
    paragraph=new Paragraph(" ");
    document.add(paragraph);
    //
    //

    PdfPTable tableQuestions= new PdfPTable(2);
    tableQuestions.setWidthPercentage(100);
    tableQuestions.setWidths(new float[] { 1, 10});
    PdfPCell pdfWordCellRow1 = new PdfPCell();
    PdfPCell pdfWordCellRow2 = new PdfPCell();
    PdfPCell pdfWordCellRow3,pdfWordCellRow4;
    Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
    Font boldFont1 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
    pdfWordCellRow1.addElement(new Phrase("SL",boldFont));
    pdfWordCellRow1.setPaddingLeft(5);
    pdfWordCellRow1.setPaddingBottom(10);
    tableQuestions.addCell(pdfWordCellRow1);
    pdfWordCellRow2.addElement(new Phrase("QUESTIONS",boldFont));
    pdfWordCellRow2.setPaddingLeft(5);
    pdfWordCellRow2.setPaddingBottom(10);
    tableQuestions.addCell(pdfWordCellRow2);
    // document.add(table);
    int questionIndexNumber=0,commentIndexNumber=0;
    for(int i=0;i<commentList.size();i++){
        pdfWordCellRow3=new PdfPCell(new Phrase(""));
        pdfWordCellRow4=new PdfPCell(new Phrase(""));
        questionIndexNumber=questionIndexNumber+1;
        pdfWordCellRow3.addElement(new Phrase(""+questionIndexNumber,boldFont1));
        pdfWordCellRow3.setPaddingLeft(5);
        pdfWordCellRow3.setPaddingBottom(10);
        tableQuestions.addCell(pdfWordCellRow3);
        pdfWordCellRow4.addElement(new Phrase(""+commentList.get(i).getQuestionDetails(),boldFont1));
        pdfWordCellRow4.setPaddingLeft(5);
        pdfWordCellRow4.setPaddingBottom(10);
      tableQuestions.addCell(pdfWordCellRow4);
        tableQuestions.addCell("");
      PdfPTable tableComment = new PdfPTable(2);
      tableComment.setWidths(new float[] { 1,20});
      tableComment.setWidthPercentage(100);
      PdfPCell cellCommentIndex=new PdfPCell();
      cellCommentIndex.setPaddingBottom(10);
      cellCommentIndex.setBorder(0);
      PdfPCell cellComment=new PdfPCell();
      cellComment.setPaddingBottom(10);
      cellComment.setBorder(0);
      String arr[]=commentList.get(i).getComments();
      int j=0;
      for(j=0;j<commentList.get(i).getComments().length;j++){
        Paragraph cellParagraph = new Paragraph(""+(j+1)+". ");
        cellParagraph.setAlignment(Element.ALIGN_RIGHT);
        cellCommentIndex.addElement(cellParagraph);
        cellComment.addElement(new Phrase("" + arr[j]));
      }
      tableComment.addCell(cellCommentIndex);
      tableComment.addCell(cellComment);
      tableQuestions.addCell(tableComment);
      }
    try {
      document.add(tableQuestions);
    } catch (DocumentException e) {
      e.printStackTrace();
    }


    //
    index=0;
    document.close();
    baos.writeTo(pOutputStream);
  }

  void emptyLine(Paragraph p, int number) {
    for(int i = 0; i < number; i++) {
      p.add(new Paragraph(" "));
    }
  }

  void getObservationType(int obType) {

  }
}
