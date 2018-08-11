package org.ums.report.generator.seatPlan.support;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.domain.model.immutable.SeatPlan;
import org.ums.domain.model.immutable.Semester;
import org.ums.enums.ExamType;
import org.ums.manager.SeatPlanManager;
import org.ums.manager.SemesterManager;
import org.ums.report.itext.UmsPdfPageEventHelper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.ums.report.generator.seatPlan.support.ReportSupport.addHeaderToSittingArrangementDataTable;
import static org.ums.report.generator.seatPlan.support.ReportSupport.getSittingArrangementHeader;

/**
 * Created by Monjur-E-Morshed on 13-May-17.
 */
@Component
public class SittingArrangementReport {

  @Autowired
  private SemesterManager mSemesterManager;

  @Autowired
  private SeatPlanManager mSeatPlanManager;

  public static final String DEST = "seat_plan_report.pdf";

  public void createSeatPlanSittingArrangementReport(int pSemesterId, ExamType pExamType,
                                                     OutputStream pOutputStream) throws IOException, DocumentException {
    List<SeatPlan> seatPlans = mSeatPlanManager.getSittingArrangement(pSemesterId, pExamType);

    Map<String, List<SeatPlan>> seatPlanMapUnordered = seatPlans
        .stream()
        .sorted(Comparator.comparing(s -> s.getStudent().getProgram().getId()))
        .collect(Collectors.groupingBy(s -> s.getStudent().getProgram().getId().toString() + s.getStudent().getCurrentYear() + s.getStudent().getCurrentAcademicSemester() + ""));

    Map<String, List<SeatPlan>> seatPlanMap = new TreeMap<>();
    seatPlanMap = seatPlanMapUnordered;
    List sortedSeatPlanMapKeys = new ArrayList(seatPlanMap.keySet());
    Collections.sort(sortedSeatPlanMapKeys);

    /*seatPlanMapUnordered.entrySet()
        .stream()
        .sorted(Map.Entry.<Integer, List<SeatPlan>> comparingByKey())
        .forEachOrdered(x-> seatPlanMap.put(x.getKey(), x.getValue()));*/

    Document document = new Document();
    document.addTitle("Seat Plan Attendence Sheet");
    Semester semester = mSemesterManager.get(pSemesterId);


    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfWriter writer = PdfWriter.getInstance(document, baos);
    SittingArrangementReportEventHandler eventHandler = new SittingArrangementReportEventHandler();
    writer.setPageEvent(eventHandler);
    document.open();
//    SittingArrangement sittingArrangement = new SittingArrangement();
//    writer.setPageEvent(sittingArrangement);
    document.setPageSize(PageSize.A4);

    Font lightFont = FontFactory.getFont(FontFactory.TIMES, 12);

    Font boldFont = FontFactory.getFont(FontFactory.TIMES_BOLD, 12);


   // document.newPage();

    for (int i = 0; i < sortedSeatPlanMapKeys.size(); i++) {
      //PdfContentByte cb = writer.getDirectContent();
      List<SeatPlan> seatPlansOfTheMap = seatPlanMap.get(sortedSeatPlanMapKeys.get(i));
      /*sittingArrangement.setSittingArrangement(pExamType, semester, seatPlansOfTheMap.get(0).getStudent());*/
      /*sittingArrangement.setSittingArrangement(pExamType, semester, seatPlansOfTheMap.get(0).getStudent());
      //writer.setPageEvent(sittingArrangement);
      //document.open();
      Paragraph paragraph = new Paragraph("Hello there");
      document.add(paragraph);
      *//*Map<String,List<SeatPlan>> seatPlanMapByRoomNo = seatPlansOfTheMap.stream().collect(Collectors.groupingBy(s-> s.getClassRoom().getRoomNo()));


      for(String roomKey: seatPlanMapByRoomNo.keySet()){

      }*/


      Map<Long, List<SeatPlan>> seatPlanMapByRoomNoUnordered = seatPlansOfTheMap
          .stream()
          .collect(Collectors.groupingBy(s -> s.getClassRoom().getId()));

      Map<Long, List<SeatPlan>> seatPlanMapByRoomNo = new TreeMap<>();
      seatPlanMapByRoomNo = seatPlanMapByRoomNoUnordered;
      List seatPlanMapByRoomNoKeys = new ArrayList(seatPlanMapByRoomNo.keySet());
      PdfPTable headerTable = getSittingArrangementHeader(pExamType, boldFont, seatPlansOfTheMap, seatPlanMapByRoomNo);
      document.add(headerTable);
      Collections.sort(seatPlanMapByRoomNoKeys);


      /*seatPlanMapByRoomNoUnordered.entrySet()
          .stream()
          .sorted(Map.Entry.<Long, List<SeatPlan>>comparingByKey())
          .forEachOrdered(x-> seatPlanMapByRoomNo.put(x.getKey(), x.getValue()));*/

      float[] tableWidth = new float[]{2, 10, 2};
      Font lightFontInner = new Font();
      lightFontInner = FontFactory.getFont(FontFactory.TIMES, 12);

      Font boldFontInner = new Font();
      boldFontInner = FontFactory.getFont(FontFactory.TIMES_BOLD, 12);

      Integer seatPlanKeySize = new Integer(seatPlanMapByRoomNo.size());
      if (seatPlanKeySize <= 6) {
        lightFontInner = FontFactory.getFont(FontFactory.TIMES, 18);

        boldFontInner = FontFactory.getFont(FontFactory.TIMES_BOLD, 18);
      } else if (seatPlanKeySize > 6 && seatPlanKeySize <= 10) {
        lightFontInner = FontFactory.getFont(FontFactory.TIMES, 14);

        boldFontInner = FontFactory.getFont(FontFactory.TIMES_BOLD, 14);
      } else if (seatPlanKeySize > 10 && seatPlanKeySize <= 16) {
        lightFontInner = FontFactory.getFont(FontFactory.TIMES, 12);

        boldFontInner = FontFactory.getFont(FontFactory.TIMES_BOLD, 12);
      } else {
        lightFontInner = FontFactory.getFont(FontFactory.TIMES, 10);

        boldFontInner = FontFactory.getFont(FontFactory.TIMES_BOLD, 10);
      }

      PdfPTable sittingArrangementTable = new PdfPTable(3);
      sittingArrangementTable.setWidths(tableWidth);
      sittingArrangementTable.setWidthPercentage(100);
      sittingArrangementTable.setPaddingTop(20);
      PdfPCell tableHeaderCell = new PdfPCell();
      Paragraph cellData = new Paragraph("Room No.", boldFontInner);
      addHeaderToSittingArrangementDataTable(sittingArrangementTable, tableHeaderCell, cellData);

      cellData = new Paragraph("Student's Id", boldFontInner);
      addHeaderToSittingArrangementDataTable(sittingArrangementTable, tableHeaderCell, cellData);

      cellData = new Paragraph("No. of Students", boldFontInner);
      addHeaderToSittingArrangementDataTable(sittingArrangementTable, tableHeaderCell, cellData);

      for (int j = 0; j < seatPlanMapByRoomNoKeys.size(); j++) {
        PdfPCell bodyCell = new PdfPCell();
        Paragraph bodyParagraph = new Paragraph(seatPlanMapByRoomNo.get(seatPlanMapByRoomNoKeys.get(j)).get(0).getClassRoom().getRoomNo(), lightFontInner);
        bodyCell.addElement(bodyParagraph);
        bodyCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        bodyCell.setVerticalAlignment(Element.ALIGN_CENTER);
        sittingArrangementTable.addCell(bodyCell);
        String[] studentIds = new String[seatPlanMapByRoomNo.get(seatPlanMapByRoomNoKeys.get(j)).size()];
        List<SeatPlan> bodySeatPlanList = seatPlanMapByRoomNo.get(seatPlanMapByRoomNoKeys.get(j));
        for (int x = 0; x < bodySeatPlanList.size(); x++) {
          studentIds[x] = bodySeatPlanList.get(x).getStudent().getId();
        }
        bodyCell = new PdfPCell(new Paragraph(String.join(", ", studentIds), lightFontInner));
        bodyCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        bodyCell.setVerticalAlignment(Element.ALIGN_CENTER);
        sittingArrangementTable.addCell(bodyCell);

        bodyCell = new PdfPCell(new Paragraph(bodySeatPlanList.size() + "", lightFontInner));
        bodyCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        sittingArrangementTable.addCell(bodyCell);
      }

      document.add(sittingArrangementTable);

      document.newPage();


    }

    document.close();
    baos.writeTo(pOutputStream);

  }

  class SittingArrangementReportEventHandler extends UmsPdfPageEventHelper {
    @Override
    public void onStartPage(PdfWriter writer, Document document) {}
  }
}
