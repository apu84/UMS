package org.ums.employee.report;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.employee.academic.AcademicInformationManager;
import org.ums.employee.additional.AdditionalInformationManager;
import org.ums.employee.award.AwardInformationManager;
import org.ums.employee.experience.ExperienceInformationManager;
import org.ums.employee.personal.PersonalInformation;
import org.ums.employee.personal.PersonalInformationManager;
import org.ums.employee.publication.PublicationInformationManager;
import org.ums.employee.service.ServiceInformationManager;
import org.ums.employee.training.TrainingInformationManager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Component
public class EmployeeCVGeneratorImpl implements EmployeeCVGenerator {

  @Autowired
  PersonalInformationManager mPersonalInformationManager;

  @Autowired
  AcademicInformationManager mAcademicInformationManager;

  @Autowired
  PublicationInformationManager mPublicationInformationManager;

  @Autowired
  TrainingInformationManager mTrainingInformationManager;

  @Autowired
  AwardInformationManager mAwardInformationManager;

  @Autowired
  ExperienceInformationManager mExperienceInformationManager;

  @Autowired
  AdditionalInformationManager mAdditionalInformationManager;

  @Autowired
  ServiceInformationManager mServiceInformationManager;

  @Override
  public void createEmployeeCV(String pEmployeeId, OutputStream pOutputStream) throws IOException, DocumentException {

    Document document = new Document();
    document.addTitle("Curriculum Vitae");

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfWriter writer = PdfWriter.getInstance(document, baos);

    Font common = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14);

    document.open();
    document.setPageSize(PageSize.A4);

    Paragraph paragraph = new Paragraph();
    paragraph.setFont(common);
    paragraph.add("Hello World");
    document.add(paragraph);

    document.close();
    baos.writeTo(pOutputStream);
  }
}
