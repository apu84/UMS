package org.ums.report.generator;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ums.academic.resource.helper.EmpExamAttendanceHelper;
import org.ums.domain.model.dto.ExamRoutineDto;
import org.ums.domain.model.immutable.EmpExamAttendance;
import org.ums.domain.model.immutable.EmpExamInvigilatorDate;
import org.ums.domain.model.immutable.EmpExamReserveDate;
import org.ums.domain.model.immutable.ExamRoutine;
import org.ums.domain.model.mutable.MutableEmpExamAttendance;
import org.ums.employee.personal.PersonalInformationManager;
import org.ums.enums.ExamType;
import org.ums.enums.common.EmployeeType;
import org.ums.manager.*;
import org.ums.persistent.model.PersistentEmpExamAttendance;
import org.ums.report.itext.UmsCell;
import org.ums.report.itext.UmsParagraph;
import org.ums.util.UmsUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by Monjur-E-Morshed on 7/31/2018.
 */
@Service
public class EmpExamAttendanceGeneratorImp implements EmpExamAttendanceGenerator {
  @Autowired
  SemesterManager mSemesterManager;
  @Autowired
  ProgramManager mProgramManager;
  @Autowired
  PersonalInformationManager mPersonalInformationManager;
  @Autowired
  DepartmentManager mDepartmentManager;
  @Autowired
  ClassRoomManager mClassRoomManager;
  @Autowired
  EmployeeManager mEmployeeManager;
  @Autowired
  ExamRoutineManager mExamRoutineManager;
  @Autowired
  EmpExamAttendanceManager mEmpExamAttendanceManager;
  @Autowired
  EmpExamAttendanceHelper mEmpExamAttendanceHelper;
  @Autowired
  EmpExamInvigilatorDateManager mEmpExamInvigilatorDateManager;
  @Autowired
  EmpExamReserveDateManager mEmpExamReserveDateManager;

  public String parseDate(String pExamDate) throws ParseException {
    String date = UmsUtils.formatDate(pExamDate, "dd-MM-yyyy", "dd MMMM, yyyy");
    return date;
  }

  @Override
  public void createRoomMemorandum(Integer pSemesterId, Integer pExamType, OutputStream pOutputStream)
      throws IOException, DocumentException, ParseException {
    Document document = new Document();
    document.addTitle("Testimonial");

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfWriter writer = PdfWriter.getInstance(document, baos);

    Font fontTimes11Normal = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11);
    Font fontTimes8Normal = FontFactory.getFont(FontFactory.TIMES_ROMAN, 8);
    Font fontTimes12Bold = FontFactory.getFont(FontFactory.TIMES_BOLD, 12);
    Font fontTimes8Bold = FontFactory.getFont(FontFactory.TIMES_BOLD, 8);
    Font fontTimes10Bold = FontFactory.getFont(FontFactory.TIMES_BOLD, 10);
    Font fontTimes9Bold = FontFactory.getFont(FontFactory.TIMES_BOLD, 9);
    Font iums = FontFactory.getFont(FontFactory.TIMES_ITALIC, 6);
    String examDate = "";

    List<ExamRoutineDto> examDates = mExamRoutineManager.getExamDatesBySemesterAndType(pSemesterId,pExamType);
    for(ExamRoutineDto app : examDates) {
        String newDate=parseDate(app.getExamDate());
      examDate = examDate + newDate+",";
    }
    StringBuilder strb=new StringBuilder(examDate);
    int index=strb.lastIndexOf(",");
    examDate=strb.replace(index,",".length()+index,".").toString();

    document.open();
    document.setPageSize(PageSize.A4);

    UmsParagraph paragraph = null;
    Chunk chunk = null;
    Chunk chunk1 = null;


    paragraph = new UmsParagraph();
    document.add(paragraph);

    chunk = new Chunk("No.:AUST/Exam/" + mSemesterManager.get(pSemesterId).getName());
    paragraph = new UmsParagraph();
    paragraph.setAlignment(Element.ALIGN_LEFT);
    paragraph.setFont(fontTimes8Normal);
    paragraph.add(chunk);
    document.add(paragraph);
    chunk = new Chunk("Date: ");
    paragraph = new UmsParagraph();
    paragraph.setAlignment(Element.ALIGN_RIGHT);
    paragraph.setFont(fontTimes8Normal);
    paragraph.add(chunk);
    document.add(paragraph);
    chunk = new Chunk("MEMORANDUM");
    paragraph = new UmsParagraph();
    paragraph.setAlignment(Element.ALIGN_CENTER);
    paragraph.setFont(fontTimes8Bold);
    paragraph.add(chunk);
    document.add(paragraph);
      UmsParagraph constantText = new UmsParagraph("The following faculty " +
              "and the staff members of the AUST are hereby requested to" +
              " perform Examination duty as invigilators and attendants respectively at " +
              "the examination room as stated against their names for Semester Final Examinations of  "+ mSemesterManager.get(pSemesterId).getName() + " scheduled to the held on: ",fontTimes8Normal);
      constantText.add(new Chunk(""+examDate+"\n\n",fontTimes8Bold));
      document.add(constantText);

      List<MutableEmpExamAttendance>  empExamAttendances=mEmpExamAttendanceHelper.getMutableEmpExamAttendances(pSemesterId,pExamType);
   Map<Long,List<EmpExamAttendance>> createGroupByRoomId=empExamAttendances.
            stream().collect(Collectors.groupingBy(EmpExamAttendance::getInvigilatorRoomId));
      Map<Long,List<EmpExamAttendance>> groupByRoomId=new TreeMap<Long,List<EmpExamAttendance>>(createGroupByRoomId);

    UmsCell cell;
    PdfPTable classRoomTable = new PdfPTable(4);
      classRoomTable.setWidths(new float[] { 3, 1,3,2 });
    classRoomTable.setSpacingBefore(5);
    classRoomTable.setSpacingAfter(5);
    classRoomTable.setWidthPercentage(100);

    // table creation for Info
    // program cell
      for(Map.Entry<Long,List<EmpExamAttendance>> entry:groupByRoomId.entrySet()){
          cell = new UmsCell(new Phrase("Examination Room: "+mClassRoomManager.get(entry.getKey()).getRoomNo(), fontTimes9Bold));
          cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
          cell.setColspan(4);
          cell.setHorizontalAlignment(UmsCell.ALIGN_LEFT);
          classRoomTable.addCell(cell);
          cell = new UmsCell(new Phrase("Invigilators' Name", fontTimes9Bold));
          cell.setHorizontalAlignment(UmsCell.ALIGN_LEFT);
          classRoomTable.addCell(cell);
          cell = new UmsCell(new Phrase("Dept", fontTimes9Bold));
          cell.setHorizontalAlignment(UmsCell.ALIGN_LEFT);
          classRoomTable.addCell(cell);
          cell = new UmsCell(new Phrase("Invigilation Date", fontTimes9Bold));
          cell.setHorizontalAlignment(UmsCell.ALIGN_LEFT);
          classRoomTable.addCell(cell);
          cell = new UmsCell(new Phrase("Reserve Date", fontTimes9Bold));
          cell.setHorizontalAlignment(UmsCell.ALIGN_LEFT);
          classRoomTable.addCell(cell);
          entry.getValue().sort(Comparator.comparing(EmpExamAttendance::getRoomInCharge).reversed().
                  thenComparing(EmpExamAttendance::getEmployeeType).
                  thenComparing(e->e.getDesignationId()));
          for(int i=0;i<entry.getValue().size();i++){
              String roomInCharge=entry.getValue().get(i).getRoomInCharge()==1?"(Room In Charge)":" ";
              cell = new UmsCell(new Phrase(""+mPersonalInformationManager.
                      get(entry.getValue().get(i).getEmployeeId()).getName()+" "+roomInCharge+"", fontTimes8Normal));
              cell.setHorizontalAlignment(UmsCell.ALIGN_LEFT);
              classRoomTable.addCell(cell);
              cell = new UmsCell(new Phrase(""+mEmployeeManager.
                      get(entry.getValue().get(i).getEmployeeId()).getDepartment().getShortName(), fontTimes8Normal));
              cell.setHorizontalAlignment(UmsCell.ALIGN_LEFT);
              classRoomTable.addCell(cell);
              if(entry.getValue().get(i).getInvigilatorDate().length()>1){
                  cell = new UmsCell(new Phrase(""+entry.getValue().get(i).getInvigilatorDate(), fontTimes8Normal));
                  cell.setHorizontalAlignment(UmsCell.ALIGN_LEFT);
                  classRoomTable.addCell(cell);
              }else {
                  cell = new UmsCell(new Phrase("Attendant", fontTimes8Normal));
                  cell.setHorizontalAlignment(UmsCell.ALIGN_LEFT);
                  classRoomTable.addCell(cell);
              }

              cell = new UmsCell(new Phrase(""+entry.getValue().get(i).getReserveDate(), fontTimes8Normal));
              cell.setHorizontalAlignment(UmsCell.ALIGN_LEFT);
              classRoomTable.addCell(cell);
          }
      }
      document.add(classRoomTable);
      getFooter(document, fontTimes10Bold,iums);

    document.close();
    baos.writeTo(pOutputStream);

  }

  @Override
  public void createEmployeeAttendantList(Integer pSemesterId, Integer pExamType, String pExamDate, String pDeptId,
      OutputStream pOutputStream) throws IOException, DocumentException, ParseException {
      List<EmpExamAttendance> list =
              mEmpExamAttendanceManager.getInfoByInvigilatorDate(pSemesterId, pExamType, "16-09-2018");
      List<MutableEmpExamAttendance> empExamAttendance = getMutableEmpExamAttendance(list);
      empExamAttendance.sort(Comparator.comparing(EmpExamAttendance::getInvigilatorRoomId).thenComparing(EmpExamAttendance::getDesignationId));
      empExamAttendance=empExamAttendance.stream().filter(
              e->e.getEmployeeType()==EmployeeType.TEACHER.getId() && e.getDepartmentId().equals(pDeptId)
      ).collect(Collectors.toList());
      String reportName="Invigilators's Reporting Sheet";
      int invigilator=1;
      Document document = new Document();

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfWriter writer = PdfWriter.getInstance(document, baos);

    Font fontTimes11Normal = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11);
    Font fontTimes8Normal = FontFactory.getFont(FontFactory.TIMES_ROMAN, 8);
    Font fontTimes12Bold = FontFactory.getFont(FontFactory.TIMES_BOLD, 12);
    Font fontTimes8Bold = FontFactory.getFont(FontFactory.TIMES_BOLD, 8);
    Font fontTimes10Bold = FontFactory.getFont(FontFactory.TIMES_BOLD, 10);
    Font fontTimes9Bold = FontFactory.getFont(FontFactory.TIMES_BOLD, 9);
      Font iums = FontFactory.getFont(FontFactory.TIMES_ITALIC, 6);
    document.open();
    document.setPageSize(PageSize.A4);

    UmsParagraph paragraph = null;
    Chunk chunk = null;
    Chunk chunk1 = null;

    paragraph = new UmsParagraph();
    document.add(paragraph);

      getReportingHeader(pSemesterId, pExamType, pExamDate, pDeptId, reportName, document, fontTimes8Normal, fontTimes8Bold,invigilator);
      getData(pOutputStream, empExamAttendance, document, baos, fontTimes8Normal, fontTimes10Bold, iums,invigilator);
  }

  private void getData(OutputStream pOutputStream, List<MutableEmpExamAttendance> empExamAttendance, Document document,
      ByteArrayOutputStream baos, Font fontTimes8Normal, Font fontTimes10Bold, Font iums, Integer isReserve)
      throws DocumentException, IOException {
    UmsCell cell;
    PdfPTable empTable = new PdfPTable(6);
    empTable.setWidths(new float[] {1, 4, 2, 2, 2, 2});
    empTable.setSpacingBefore(5);
    empTable.setSpacingAfter(5);
    empTable.setWidthPercentage(100);
    cell = new UmsCell(new Phrase("Serial No", fontTimes8Normal));
    cell.setHorizontalAlignment(UmsCell.ALIGN_CENTER);
    empTable.addCell(cell);
    cell = new UmsCell(new Phrase("Invigilators' Name", fontTimes8Normal));
    cell.setHorizontalAlignment(UmsCell.ALIGN_CENTER);
    empTable.addCell(cell);
    if(isReserve == 1) {
      cell = new UmsCell(new Phrase("Room", fontTimes8Normal));
      cell.setHorizontalAlignment(UmsCell.ALIGN_CENTER);
      empTable.addCell(cell);
    }
    else {
      cell = new UmsCell(new Phrase("Dept", fontTimes8Normal));
      cell.setHorizontalAlignment(UmsCell.ALIGN_CENTER);
      empTable.addCell(cell);
    }

    cell = new UmsCell(new Phrase("Reporting Time", fontTimes8Normal));
    cell.setHorizontalAlignment(UmsCell.ALIGN_CENTER);
    empTable.addCell(cell);
    cell = new UmsCell(new Phrase("Signature", fontTimes8Normal));
    cell.setHorizontalAlignment(UmsCell.ALIGN_CENTER);
    empTable.addCell(cell);
    cell = new UmsCell(new Phrase("Remarks", fontTimes8Normal));
    cell.setHorizontalAlignment(UmsCell.ALIGN_CENTER);
    empTable.addCell(cell);
    if(empExamAttendance.size() > 1) {
      empTable.setHeaderRows(1);
    }
    int counter = 0;
    for(EmpExamAttendance app : empExamAttendance) {
      counter++;
      cell = new UmsCell(new Phrase("" + counter, fontTimes8Normal));
      cell.setHorizontalAlignment(UmsCell.ALIGN_CENTER);
      empTable.addCell(cell);
      cell =
          new UmsCell(new Phrase("" + mPersonalInformationManager.get(app.getEmployeeId()).getName(), fontTimes8Normal));
      cell.setHorizontalAlignment(UmsCell.ALIGN_LEFT);
      empTable.addCell(cell);
      if(isReserve == 1) {
        cell =
            new UmsCell(
                new Phrase("" + mClassRoomManager.get(app.getInvigilatorRoomId()).getRoomNo(), fontTimes8Normal));
        cell.setHorizontalAlignment(UmsCell.ALIGN_LEFT);
        empTable.addCell(cell);
      }
      else {
        cell =
            new UmsCell(new Phrase("" + mEmployeeManager.get(app.getEmployeeId()).getDepartment().getShortName(),
                fontTimes8Normal));
        cell.setHorizontalAlignment(UmsCell.ALIGN_LEFT);
        empTable.addCell(cell);
      }
      cell = new UmsCell(new Phrase(" ", fontTimes8Normal));
      cell.setHorizontalAlignment(UmsCell.ALIGN_CENTER);
      empTable.addCell(cell);
      cell = new UmsCell(new Phrase(" ", fontTimes8Normal));
      cell.setHorizontalAlignment(UmsCell.ALIGN_CENTER);
      empTable.addCell(cell);
      cell = new UmsCell(new Phrase(" ", fontTimes8Normal));
      cell.setHorizontalAlignment(UmsCell.ALIGN_CENTER);
      empTable.addCell(cell);
    }
    document.add(empTable);
    getFooter(document, fontTimes10Bold, iums);
    document.close();
    baos.writeTo(pOutputStream);
  }

  private void getReportingHeader(Integer pSemesterId, Integer pExamType, String pExamDate, String pDeptId,
      String reportName, Document document, Font fontTimes8Normal, Font fontTimes8Bold, Integer isReserve)
      throws DocumentException, ParseException {
    Chunk chunk;
    UmsParagraph paragraph;
    chunk = new Chunk("Ahsanullah University of Science And technology", fontTimes8Bold);
    paragraph = new UmsParagraph();
    paragraph.setAlignment(Element.ALIGN_CENTER);
    paragraph.setFont(fontTimes8Normal);
    paragraph.add(chunk);
    document.add(paragraph);
    chunk =
        new Chunk("Office of the Controller of the Examinations\n" + ExamType.get(pExamType).getLabel()
            + " Examination " + mSemesterManager.get(pSemesterId).getName() + "\n" + reportName, fontTimes8Normal);
    paragraph = new UmsParagraph();
    paragraph.setAlignment(Element.ALIGN_CENTER);
    paragraph.setFont(fontTimes8Normal);
    paragraph.add(chunk);
    document.add(paragraph);
    if(isReserve == 1) {
      chunk = new Chunk("" + mDepartmentManager.get(pDeptId).getLongName(), fontTimes8Bold);
      paragraph = new UmsParagraph();
      paragraph.setAlignment(Element.ALIGN_CENTER);
      paragraph.setFont(fontTimes8Normal);
      paragraph.add(chunk);
      document.add(paragraph);
    }
    chunk = new Chunk("Date of Examination: " + parseDate(pExamDate), fontTimes8Bold);
    paragraph = new UmsParagraph();
    paragraph.setAlignment(Element.ALIGN_RIGHT);
    paragraph.setFont(fontTimes8Bold);
    paragraph.add(chunk);
    document.add(paragraph);
  }

  @Override
    public void createReserveEmployeeAttendantList(Integer pSemesterId, Integer pExamType, String pExamDate, String pDeptId, OutputStream pOutputStream) throws IOException, DocumentException, ParseException {
        List<EmpExamAttendance> list =
                mEmpExamAttendanceManager.getInfoByReserveDate(pSemesterId, pExamType, "23-08-2018");
        List<MutableEmpExamAttendance> empExamAttendance = getMutableEmpExamAttendance(list);
        empExamAttendance.sort(Comparator.comparing(EmpExamAttendance::getDepartmentId).thenComparing(EmpExamAttendance::getDesignationId));
        empExamAttendance=empExamAttendance.stream().filter(
                e->e.getEmployeeType()==EmployeeType.TEACHER.getId()
        ).collect(Collectors.toList());
        String reportName="Reserve's Reporting Sheet";
        int reserve=2;
        Document document = new Document();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, baos);

        Font fontTimes11Normal = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11);
        Font fontTimes8Normal = FontFactory.getFont(FontFactory.TIMES_ROMAN, 8);
        Font fontTimes12Bold = FontFactory.getFont(FontFactory.TIMES_BOLD, 12);
        Font fontTimes8Bold = FontFactory.getFont(FontFactory.TIMES_BOLD, 8);
        Font fontTimes10Bold = FontFactory.getFont(FontFactory.TIMES_BOLD, 10);
        Font fontTimes9Bold = FontFactory.getFont(FontFactory.TIMES_BOLD, 9);
        Font iums = FontFactory.getFont(FontFactory.TIMES_ITALIC, 6);
        document.open();
        document.setPageSize(PageSize.A4);

        UmsParagraph paragraph = null;
        Chunk chunk = null;
        Chunk chunk1 = null;

        paragraph = new UmsParagraph();
        document.add(paragraph);

        getReportingHeader(pSemesterId, pExamType, pExamDate, pDeptId, reportName, document, fontTimes8Normal, fontTimes8Bold,reserve);
        getData(pOutputStream, empExamAttendance, document, baos, fontTimes8Normal, fontTimes10Bold, iums,reserve);
    }

  @Override
  public void createStaffAttendantList(Integer pSemesterId, Integer pExamType, String pExamDate,
      OutputStream pOutputStream) throws IOException, DocumentException, ParseException {
      List<EmpExamAttendance> list =
              mEmpExamAttendanceManager.getInfoBySemesterAndExamType(pSemesterId,pExamType);
      List<MutableEmpExamAttendance> empExamAttendance = getMutableEmpExamAttendance(list);
      empExamAttendance.sort(Comparator.comparing(EmpExamAttendance::getInvigilatorRoomId).thenComparing(EmpExamAttendance::getDesignationId));
      empExamAttendance=empExamAttendance.stream().filter(
              e->e.getEmployeeType()==EmployeeType.STAFF.getId()
      ).collect(Collectors.toList());
      Document document = new Document();

      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      PdfWriter writer = PdfWriter.getInstance(document, baos);

      Font fontTimes11Normal = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11);
      Font fontTimes8Normal = FontFactory.getFont(FontFactory.TIMES_ROMAN, 8);
      Font fontTimes12Bold = FontFactory.getFont(FontFactory.TIMES_BOLD, 12);
      Font fontTimes8Bold = FontFactory.getFont(FontFactory.TIMES_BOLD, 8);
      Font fontTimes10Bold = FontFactory.getFont(FontFactory.TIMES_BOLD, 10);
      Font fontTimes9Bold = FontFactory.getFont(FontFactory.TIMES_BOLD, 9);
      Font iums = FontFactory.getFont(FontFactory.TIMES_ITALIC, 6);
      document.open();
      document.setPageSize(PageSize.A4);

      UmsParagraph paragraph = null;
      Chunk chunk = null;
      Chunk chunk1 = null;

      paragraph = new UmsParagraph();
      document.add(paragraph);

      chunk = new Chunk("Ahsanullah University of Science And technology",fontTimes8Bold);
      paragraph = new UmsParagraph();
      paragraph.setAlignment(Element.ALIGN_CENTER);
      paragraph.setFont(fontTimes8Normal);
      paragraph.add(chunk);
      document.add(paragraph);
      chunk = new Chunk("Office of the Controller of the Examinations\n"+ ExamType.get(pExamType).getLabel()+" Examination "+mSemesterManager.get(pSemesterId).getName()+"\n" +
              "Staff's Signature Sheet",fontTimes8Normal);
      paragraph = new UmsParagraph();
      paragraph.setAlignment(Element.ALIGN_CENTER);
      paragraph.setFont(fontTimes8Normal);
      paragraph.add(chunk);
      document.add(paragraph);
      chunk = new Chunk("Date of Examination: "+parseDate(pExamDate),fontTimes8Bold);
      paragraph = new UmsParagraph();
      paragraph.setAlignment(Element.ALIGN_RIGHT);
      paragraph.setFont(fontTimes8Bold);
      paragraph.add(chunk);
      document.add(paragraph);
      UmsCell cell;
      PdfPTable empTable = new PdfPTable(6);
      empTable.setWidths(new float[] { 1,4,2,2,2,2 });
      empTable.setSpacingBefore(5);
      empTable.setSpacingAfter(5);
      empTable.setWidthPercentage(100);
      cell = new UmsCell(new Phrase("Serial No", fontTimes8Normal));
      cell.setHorizontalAlignment(UmsCell.ALIGN_CENTER);
      empTable.addCell(cell);
      cell = new UmsCell(new Phrase("Name", fontTimes8Normal));
      cell.setHorizontalAlignment(UmsCell.ALIGN_CENTER);
      empTable.addCell(cell);
      cell = new UmsCell(new Phrase("Room", fontTimes8Normal));
      cell.setHorizontalAlignment(UmsCell.ALIGN_CENTER);
      empTable.addCell(cell);
      cell = new UmsCell(new Phrase("Reporting Time", fontTimes8Normal));
      cell.setHorizontalAlignment(UmsCell.ALIGN_CENTER);
      empTable.addCell(cell);
      cell = new UmsCell(new Phrase("Signature", fontTimes8Normal));
      cell.setHorizontalAlignment(UmsCell.ALIGN_CENTER);
      empTable.addCell(cell);
      cell = new UmsCell(new Phrase("Remarks", fontTimes8Normal));
      cell.setHorizontalAlignment(UmsCell.ALIGN_CENTER);
      empTable.addCell(cell);
      int counter=0;
      for(EmpExamAttendance app:empExamAttendance){
          counter++;
          cell = new UmsCell(new Phrase(""+counter, fontTimes8Normal));
          cell.setHorizontalAlignment(UmsCell.ALIGN_LEFT);
          empTable.addCell(cell);
          cell = new UmsCell(new Phrase(""+mPersonalInformationManager.get(app.getEmployeeId()).getName(), fontTimes8Normal));
          cell.setHorizontalAlignment(UmsCell.ALIGN_LEFT);
          empTable.addCell(cell);
          cell = new UmsCell(new Phrase(""+mClassRoomManager.get(app.getInvigilatorRoomId()).getRoomNo(), fontTimes8Normal));
          cell.setHorizontalAlignment(UmsCell.ALIGN_LEFT);
          empTable.addCell(cell);
          cell = new UmsCell(new Phrase(" ", fontTimes8Normal));
          cell.setHorizontalAlignment(UmsCell.ALIGN_LEFT);
          empTable.addCell(cell);
          cell = new UmsCell(new Phrase(" ", fontTimes8Normal));
          cell.setHorizontalAlignment(UmsCell.ALIGN_LEFT);
          empTable.addCell(cell);
          cell = new UmsCell(new Phrase(" ", fontTimes8Normal));
          cell.setHorizontalAlignment(UmsCell.ALIGN_LEFT);
          empTable.addCell(cell);
      }
      document.add(empTable);
      getFooter(document, fontTimes10Bold,iums);

    document.close();
    baos.writeTo(pOutputStream);
  }

  public List<MutableEmpExamAttendance> getMutableEmpExamAttendance(List<EmpExamAttendance> list) {
    List<MutableEmpExamAttendance> empExamAttendance = new ArrayList<>();
    for(EmpExamAttendance app : list) {
      PersistentEmpExamAttendance newList = new PersistentEmpExamAttendance();
      newList.setId(app.getId());
      newList.setEmployeeId(app.getEmployeeId());
      newList.setDepartmentId(mEmployeeManager.get(app.getEmployeeId()).getDepartment().getId());
      newList.setInvigilatorRoomId(app.getInvigilatorRoomId());
      newList.setEmployeeType(mEmployeeManager.get(app.getEmployeeId()).getEmployeeType());
      newList.setDesignationId(mEmployeeManager.get(app.getEmployeeId()).getDesignationId());
      empExamAttendance.add(newList);
    }
    return empExamAttendance;
  }

  public void getFooter(Document document, Font fontTimes10Bold, Font iums) throws DocumentException {
    UmsCell cell;
    UmsParagraph paragraph = null;
    Chunk chunk = null;
    PdfPTable footer = new PdfPTable(4);
    footer.setSpacingBefore(2);
    footer.setSpacingAfter(2);
    footer.setWidthPercentage(100);
    // table creation for Info
    // program cell
    cell = new UmsCell(new Phrase("Generated by: IUMS", iums));
    cell.setBorder(Rectangle.NO_BORDER);
    cell.setHorizontalAlignment(UmsCell.ALIGN_LEFT);
    footer.addCell(cell);
    cell = new UmsCell(new Phrase(" ", fontTimes10Bold));
    cell.setBorder(Rectangle.NO_BORDER);
    cell.setHorizontalAlignment(UmsCell.ALIGN_CENTER);
    footer.addCell(cell);
    cell = new UmsCell(new Phrase(" ", fontTimes10Bold));
    cell.setBorder(Rectangle.NO_BORDER);
    cell.setHorizontalAlignment(UmsCell.ALIGN_CENTER);
    footer.addCell(cell);
    cell = new UmsCell(new Phrase(" ", fontTimes10Bold));
    cell.setBorder(Rectangle.NO_BORDER);
    cell.setHorizontalAlignment(UmsCell.ALIGN_RIGHT);
    footer.addCell(cell);
    document.add(footer);
  }
}
