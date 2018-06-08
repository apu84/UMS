package org.ums.report;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.domain.model.immutable.meeting.AgendaResolution;
import org.ums.domain.model.immutable.meeting.Schedule;
import org.ums.manager.meeting.AgendaResolutionManager;
import org.ums.manager.meeting.ScheduleManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Component
public class MeetingMinutesGeneratorImpl implements MeetingMinutesGenerator {

  @Autowired
  private ScheduleManager mScheduleManager;

  @Autowired
  private AgendaResolutionManager mAgendaResolutionManager;

  public void createMeetingMinutes(int pMeetingType, int pMeetingNo, int pPrintingType, OutputStream pOutputStream)
      throws DocumentException, IOException {

    Schedule schedule = mScheduleManager.get(pMeetingType, pMeetingNo);

    List<AgendaResolution> agendaResolutionList = mAgendaResolutionManager.getAgendaResolution(schedule.getId());

    Document document = new Document();
    document.addTitle("Meeting Minutes");

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfWriter writer = PdfWriter.getInstance(document, baos);

    Font fontTimes11Normal = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11);
    Font fontTimes11Bold = FontFactory.getFont(FontFactory.TIMES_BOLD, 12);
    Font fontTimes14Bold = FontFactory.getFont(FontFactory.TIMES_BOLD, 14);

    document.open();
    document.setPageSize(PageSize.A4);

    Paragraph paragraph = null;
    Chunk chunk = null;

    chunk = new Chunk("AHSANULLAH UNIVERSITY OF SCIENCE & TECHNOLOGY");
    paragraph = new Paragraph(chunk);
    paragraph.setFont(fontTimes14Bold);
    paragraph.setAlignment(Element.ALIGN_CENTER);
    document.add(paragraph);

    chunk = new Chunk("141-142, Love Road, Tejgaon I/A, Dhaka-1208");
    paragraph = new Paragraph(chunk);
    paragraph.setFont(fontTimes11Normal);
    paragraph.setAlignment(Element.ALIGN_CENTER);
    document.add(paragraph);

    paragraph = new Paragraph();
    emptyLine(paragraph, 2);
    document.add(paragraph);

    for(AgendaResolution agendaResolution : agendaResolutionList) {
      chunk = new Chunk("No: " + agendaResolution.getAgendaNo());
      paragraph = new Paragraph(chunk);
      paragraph.setFont(fontTimes11Normal);
      document.add(paragraph);

      chunk = new Chunk("Agenda: " + agendaResolution.getAgenda());
      paragraph = new Paragraph(chunk);
      paragraph.setFont(fontTimes14Bold);
      document.add(paragraph);

      if(pPrintingType == 2 && !agendaResolution.getResolution().isEmpty()) {
        chunk = new Chunk("Resolution: " + agendaResolution.getResolution());
        paragraph = new Paragraph(chunk);
        paragraph.setFont(fontTimes14Bold);
        document.add(paragraph);
      }

      paragraph = new Paragraph();
      emptyLine(paragraph, 1);
      document.add(paragraph);
    }

    document.close();
    baos.writeTo(pOutputStream);
  }

  void emptyLine(Paragraph p, int number) {
    for(int i = 0; i < number; i++) {
      p.add(new Paragraph(" "));
    }
  }
}
