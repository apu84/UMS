package org.ums.report.generator.examAttendance;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.jvnet.hk2.internal.Collector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ums.domain.model.immutable.ExpelledInformation;
import org.ums.domain.model.immutable.StudentsExamAttendantInfo;
import org.ums.enums.CourseRegType;
import org.ums.enums.ExamType;
import org.ums.manager.*;
import org.ums.report.itext.UmsCell;
import org.ums.report.itext.UmsParagraph;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Created by Monjur-E-Morshed on 6/26/2018.
 */
@Service
class ExamAttendanceGeneratorImp implements ExamAttendanceGenerator {
  @Autowired
  SemesterManager mSemesterManager;
  @Autowired
  StudentsExamAttendantInfoManager mStudentsExamAttendantInfoManager;
  @Autowired
  ProgramManager mProgramManager;
  @Autowired
  ExpelledInformationManager mExpelledInformationManager;
  @Autowired
  StudentManager mStudentManager;
  @Autowired
  CourseManager mCourseManager;

  @Override
  public void createTestimonial(Integer pSemesterId, Integer pExamType, String pExamDate, OutputStream pOutputStream)
      throws IOException, DocumentException {
    Document document = new Document();
    document.addTitle("");

    document = new Document(PageSize.A4.rotate());
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfWriter writer = PdfWriter.getInstance(document, baos);

    Font fontTimes11Normal = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11);
    Font fontTimes8Normal = FontFactory.getFont(FontFactory.TIMES_ROMAN, 8);
    Font fontTimes6Normal = FontFactory.getFont(FontFactory.TIMES_ROMAN, 6);
    Font fontTimes8Bold = FontFactory.getFont(FontFactory.TIMES_BOLD, 8);
    Font fontTimes10Bold = FontFactory.getFont(FontFactory.TIMES_BOLD, 10);
    Font fontTimes9Bold = FontFactory.getFont(FontFactory.TIMES_BOLD, 9);

    document.open();
    document.setPageSize(PageSize.A4);

    UmsParagraph paragraph = null;
    Chunk chunk = null;

    paragraph = new UmsParagraph();
    document.add(paragraph);

    chunk = new Chunk("Ahsanullah University of Science and Technology");
    paragraph = new UmsParagraph();
    paragraph.setAlignment(Element.ALIGN_CENTER);
    paragraph.setFont(fontTimes10Bold);
    paragraph.add(chunk);
    document.add(paragraph);
    //
    chunk =
        new Chunk(ExamType.get(pExamType).getLabel() + " " + mSemesterManager.get(pSemesterId).getName()
            + "\nDaily Examination Report\n\n");
    paragraph = new UmsParagraph();
    paragraph.setAlignment(Element.ALIGN_CENTER);
    paragraph.setFont(fontTimes11Normal);
    paragraph.add(chunk);
    document.add(paragraph);

    chunk = new Chunk("Date of Examination: "+pExamDate+"\n");
    paragraph = new UmsParagraph();
    paragraph.setAlignment(Element.ALIGN_RIGHT);
    paragraph.add(chunk);
    document.add(paragraph);

      chunk = new Chunk("01. Student's attendant information\n");
      paragraph = new UmsParagraph();
      paragraph.setAlignment(Element.ALIGN_CENTER);
      paragraph.setFont(fontTimes10Bold);
      paragraph.add(chunk);
      document.add(paragraph);


    // get exam info data
    List<StudentsExamAttendantInfo> studentsExamAttendantInfoList =
        mStudentsExamAttendantInfoManager.getSemesterExamTypeDateWiseRecords(pSemesterId, pExamType, pExamDate);
    studentsExamAttendantInfoList.sort(Comparator.comparing(StudentsExamAttendantInfo::getYear).thenComparing(StudentsExamAttendantInfo::getSemester));
    Map<Integer,List<StudentsExamAttendantInfo>> programIdMap=studentsExamAttendantInfoList.
            stream().collect(Collectors.groupingBy(StudentsExamAttendantInfo::getProgramId));
      Map<Integer,List<StudentsExamAttendantInfo>> programMap=new TreeMap<Integer,List<StudentsExamAttendantInfo>>(programIdMap);
      List<ExpelledInformation> expelledInformation=mExpelledInformationManager.getSemesterExamTyeDateWiseRecords(pSemesterId,pExamType,pExamDate);

    //

    //Table Creation for programs
    PdfPTable programTable = new PdfPTable(programMap.size());
    programTable.setSpacingBefore(5);
    programTable.setSpacingAfter(5);
    programTable.setWidthPercentage(100);
    //table creation for Info
   // program cell
    UmsCell cell;
    cell = new UmsCell(new Phrase("Programs"));
    cell.setHorizontalAlignment(UmsCell.ALIGN_CENTER);
    cell.setColspan(programMap.size());
    cell.setPaddingBottom(5);
    programTable.addCell(cell);
    //data cell

    //
    for(Map.Entry<Integer, List<StudentsExamAttendantInfo>> entry:programMap.entrySet()){
      cell = new UmsCell(new Phrase(mProgramManager.get(entry.getKey()).getShortName(),fontTimes9Bold));
      cell.setHorizontalAlignment(UmsCell.ALIGN_CENTER);
      cell.setPadding(2);
      programTable.addCell(cell);
      programTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
    }

    Integer totalReg=0,totalPs=0,totalAb=0,maxRowNo=0,totalExp=expelledInformation.size();
      for(Map.Entry<Integer, List<StudentsExamAttendantInfo>> entry:programMap.entrySet()){
       if(entry.getValue().size()>maxRowNo){
           maxRowNo=entry.getValue().size();
       }
      }
    for(Map.Entry<Integer, List<StudentsExamAttendantInfo>> entry:programMap.entrySet()){
      PdfPTable dataTable = new PdfPTable(4);
      dataTable.setWidthPercentage(100);
      dataTable.getDefaultCell().setPadding(5);
      cell = new UmsCell(new Phrase("Y/S",fontTimes8Normal));
      cell.setHorizontalAlignment(UmsCell.ALIGN_CENTER);
      dataTable.addCell(cell);
      cell = new UmsCell(new Phrase("Reg",fontTimes8Normal));
      cell.setHorizontalAlignment(UmsCell.ALIGN_CENTER);
      dataTable.addCell(cell);
      cell = new UmsCell(new Phrase("Pr",fontTimes8Normal));
      cell.setHorizontalAlignment(UmsCell.ALIGN_CENTER);
      dataTable.addCell(cell);
      cell = new UmsCell(new Phrase("Ab",fontTimes8Normal));
      cell.setHorizontalAlignment(UmsCell.ALIGN_CENTER);
      dataTable.addCell(cell);
      Integer deptTotalReg=0,deptTotalPs=0,deptTotalAb=0;
     for(int j=0;j<entry.getValue().size();j++){
      cell = new UmsCell(new Phrase(entry.getValue().get(j).getYear()+
              "/"+entry.getValue().get(j).getSemester(),fontTimes8Normal));
      cell.setHorizontalAlignment(UmsCell.ALIGN_CENTER);
      dataTable.addCell(cell);
      cell = new UmsCell(new Phrase(""+entry.getValue().get(j).getRegisteredStudents()==null ? ""+0:""+entry.getValue().get(j).getRegisteredStudents(),fontTimes8Normal));
      cell.setHorizontalAlignment(UmsCell.ALIGN_RIGHT);
      dataTable.addCell(cell);
      cell = new UmsCell(new Phrase(""+entry.getValue().get(j).getPresentStudents()==null ? ""+0:""+entry.getValue().get(j).getPresentStudents(),fontTimes8Normal));
      cell.setHorizontalAlignment(UmsCell.ALIGN_RIGHT);
      dataTable.addCell(cell);
      cell = new UmsCell(new Phrase(""+entry.getValue().get(j).getAbsentStudents()==null ? ""+0:""+entry.getValue().get(j).getAbsentStudents(),fontTimes8Normal));
      cell.setHorizontalAlignment(UmsCell.ALIGN_RIGHT);
      dataTable.addCell(cell);
      deptTotalReg=deptTotalReg+entry.getValue().get(j).getRegisteredStudents();
      deptTotalPs=deptTotalPs+entry.getValue().get(j).getPresentStudents();
      deptTotalAb=deptTotalAb+entry.getValue().get(j).getAbsentStudents();
      if(j==entry.getValue().size()-1){
          for(int k=0;k<maxRowNo-entry.getValue().size();k++){
              cell = new UmsCell(new Phrase(" ",fontTimes8Normal));
              cell.setHorizontalAlignment(UmsCell.ALIGN_CENTER);
              cell.setColspan(4);
              dataTable.addCell(cell);
          }
      }
    }
      totalReg=totalReg+deptTotalReg;
      totalPs=totalPs+deptTotalPs;
      totalAb=totalAb+deptTotalAb;
      cell = new UmsCell(new Phrase("Total",fontTimes8Normal));
      cell.setHorizontalAlignment(UmsCell.ALIGN_CENTER);
      dataTable.addCell(cell);
      cell = new UmsCell(new Phrase(""+deptTotalReg,fontTimes8Bold));
      cell.setHorizontalAlignment(UmsCell.ALIGN_RIGHT);
      dataTable.addCell(cell);
      cell = new UmsCell(new Phrase(""+deptTotalPs,fontTimes8Bold));
      cell.setHorizontalAlignment(UmsCell.ALIGN_RIGHT);
      dataTable.addCell(cell);
      cell = new UmsCell(new Phrase(""+deptTotalAb,fontTimes8Bold));
      cell.setHorizontalAlignment(UmsCell.ALIGN_RIGHT);
      dataTable.addCell(cell);
      programTable.addCell(dataTable);

    }
   document.add(programTable);
    cell = new UmsCell(new Phrase("\n\n"));
    cell.setHorizontalAlignment(UmsCell.ALIGN_CENTER);
    cell.setColspan(programMap.size());
    cell.setPaddingBottom(5);
    programTable.addCell(cell);
    //infoTable
    PdfPTable totalInfoTable = new PdfPTable(4);
    totalInfoTable.setWidthPercentage(100);
    totalInfoTable.getDefaultCell().setPadding(5);
    cell = new UmsCell(new Phrase("Total Registered: "+totalReg,fontTimes10Bold));
    cell.setHorizontalAlignment(UmsCell.ALIGN_RIGHT);
    totalInfoTable.addCell(cell);
    cell = new UmsCell(new Phrase("Total Present: "+totalPs,fontTimes10Bold));
    cell.setHorizontalAlignment(UmsCell.ALIGN_RIGHT);
    totalInfoTable.addCell(cell);
    cell = new UmsCell(new Phrase("Total Absent: "+totalAb,fontTimes10Bold));
    cell.setHorizontalAlignment(UmsCell.ALIGN_RIGHT);
    totalInfoTable.addCell(cell);
    cell = new UmsCell(new Phrase("Total Expelled: "+totalExp,fontTimes10Bold));
    cell.setHorizontalAlignment(UmsCell.ALIGN_RIGHT);
    totalInfoTable.addCell(cell);
    document.add(totalInfoTable);
      chunk = new Chunk("\n02. Expelled information\n");
      paragraph = new UmsParagraph();
      paragraph.setAlignment(Element.ALIGN_CENTER);
      paragraph.setFont(fontTimes10Bold);
      paragraph.add(chunk);
      document.add(paragraph);
    //Expelled Information
      //Table Creation for programs
      PdfPTable expelledInfoTable = new PdfPTable(7);
      expelledInfoTable.setSpacingBefore(5);
      expelledInfoTable.setSpacingAfter(5);
      expelledInfoTable.setWidthPercentage(100);
      //table creation for Info
      // program cell
      cell = new UmsCell(new Phrase("Student Name",fontTimes9Bold));
      cell.setHorizontalAlignment(UmsCell.ALIGN_CENTER);
      expelledInfoTable.addCell(cell);
      cell = new UmsCell(new Phrase("Student ID",fontTimes9Bold));
      cell.setHorizontalAlignment(UmsCell.ALIGN_CENTER);
      expelledInfoTable.addCell(cell);
      cell = new UmsCell(new Phrase("Dept",fontTimes9Bold));
      cell.setHorizontalAlignment(UmsCell.ALIGN_CENTER);
      expelledInfoTable.addCell(cell);
      cell = new UmsCell(new Phrase("Year/Sem.",fontTimes9Bold));
      cell.setHorizontalAlignment(UmsCell.ALIGN_CENTER);
      expelledInfoTable.addCell(cell);
      cell = new UmsCell(new Phrase("RegType",fontTimes9Bold));
      cell.setHorizontalAlignment(UmsCell.ALIGN_CENTER);
      expelledInfoTable.addCell(cell);
      cell = new UmsCell(new Phrase("Course Title",fontTimes9Bold));
      cell.setHorizontalAlignment(UmsCell.ALIGN_CENTER);
      expelledInfoTable.addCell(cell);
      cell = new UmsCell(new Phrase("Reason of Expulsion",fontTimes9Bold));
      cell.setHorizontalAlignment(UmsCell.ALIGN_CENTER);
      expelledInfoTable.addCell(cell);

      for(int i=0;i<expelledInformation.size();i++){
          cell = new UmsCell(new Phrase(""+mStudentManager.get(expelledInformation.get(i).getStudentId()).getFullName(),fontTimes8Normal));
          cell.setHorizontalAlignment(UmsCell.ALIGN_CENTER);
          expelledInfoTable.addCell(cell);
          cell = new UmsCell(new Phrase(""+expelledInformation.get(i).getStudentId(),fontTimes8Normal));
          cell.setHorizontalAlignment(UmsCell.ALIGN_CENTER);
          expelledInfoTable.addCell(cell);
          cell = new UmsCell(new Phrase(""+mStudentManager.get(expelledInformation.get(i).getStudentId()).getDepartment().getShortName(),fontTimes8Normal));
          cell.setHorizontalAlignment(UmsCell.ALIGN_CENTER);
          expelledInfoTable.addCell(cell);
          cell = new UmsCell(new Phrase(""+mStudentManager.get(expelledInformation.get(i).getStudentId()).getCurrentYear()+"/"+
                  mStudentManager.get(expelledInformation.get(i).getStudentId()).getCurrentAcademicSemester(),fontTimes8Normal));
          cell.setHorizontalAlignment(UmsCell.ALIGN_CENTER);
          expelledInfoTable.addCell(cell);
          cell = new UmsCell(new Phrase(""+ CourseRegType.get(expelledInformation.get(i).getRegType()).getLabel(),fontTimes8Normal));
          cell.setHorizontalAlignment(UmsCell.ALIGN_CENTER);
          expelledInfoTable.addCell(cell);
          cell = new UmsCell(new Phrase(""+mCourseManager.get(expelledInformation.get(i).getCourseId()).getTitle()+"("+
                  mCourseManager.get(expelledInformation.get(i).getCourseId()).getNo()+")",fontTimes8Normal));
          cell.setHorizontalAlignment(UmsCell.ALIGN_CENTER);
          expelledInfoTable.addCell(cell);
          cell = new UmsCell(new Phrase(""+expelledInformation.get(i).getExpelledReason(),fontTimes8Normal));
          cell.setHorizontalAlignment(UmsCell.ALIGN_CENTER);
          expelledInfoTable.addCell(cell);
      }
      document.add(expelledInfoTable);
    document.close();
    baos.writeTo(pOutputStream);
  }
}
