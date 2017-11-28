package org.ums.employee.report;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.ums.employee.academic.AcademicInformation;
import org.ums.employee.academic.AcademicInformationManager;
import org.ums.employee.additional.AdditionalInformationManager;
import org.ums.employee.award.AwardInformation;
import org.ums.employee.award.AwardInformationManager;
import org.ums.employee.experience.ExperienceInformation;
import org.ums.employee.experience.ExperienceInformationManager;
import org.ums.employee.personal.PersistentPersonalInformation;
import org.ums.employee.personal.PersonalInformation;
import org.ums.employee.personal.PersonalInformationManager;
import org.ums.employee.publication.PublicationInformation;
import org.ums.employee.publication.PublicationInformationManager;
import org.ums.employee.service.ServiceInformation;
import org.ums.employee.service.ServiceInformationManager;
import org.ums.employee.training.TrainingInformation;
import org.ums.employee.training.TrainingInformationManager;
import org.ums.manager.common.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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

  @Autowired
  CountryManager mCountryManager;

  @Autowired
  DivisionManager mDIvisionManager;

  @Autowired
  DistrictManager mDistrictManager;

  @Autowired
  ThanaManager mThanaManager;

  @Autowired
  AcademicDegreeManager mAcademicDegreeManager;

  @Override
  public void createEmployeeCV(String pEmployeeId, OutputStream pOutputStream) throws IOException, DocumentException {

    PersonalInformation personalInformation = new PersistentPersonalInformation();
    try {
      personalInformation = mPersonalInformationManager.getPersonalInformation(pEmployeeId);
    } catch(EmptyResultDataAccessException e) {
    }

    List<AcademicInformation> academicInformation = new ArrayList<>();
    try {
      academicInformation = mAcademicInformationManager.getEmployeeAcademicInformation(pEmployeeId);
    } catch(EmptyResultDataAccessException e) {

    }

    List<PublicationInformation> publicationInformation = new ArrayList<>();
    try {
      publicationInformation = mPublicationInformationManager.getEmployeePublicationInformation(pEmployeeId);
    } catch(EmptyResultDataAccessException e) {

    }

    List<TrainingInformation> trainingInformation = new ArrayList<>();
    try {
      trainingInformation = mTrainingInformationManager.getEmployeeTrainingInformation(pEmployeeId);
    } catch(EmptyResultDataAccessException e) {

    }

    List<AwardInformation> awardInformation = new ArrayList<>();
    try {
      awardInformation = mAwardInformationManager.getEmployeeAwardInformation(pEmployeeId);
    } catch(EmptyResultDataAccessException e) {

    }
    List<ExperienceInformation> experienceInformation = new ArrayList<>();
    try {
      experienceInformation = mExperienceInformationManager.getEmployeeExperienceInformation(pEmployeeId);
    } catch(EmptyResultDataAccessException e) {

    }

    List<ServiceInformation> serviceInformation = new ArrayList<>();
    try {
      serviceInformation = mServiceInformationManager.getServiceInformation(pEmployeeId);
    } catch(EmptyResultDataAccessException e) {

    }

    Document document = new Document();
    document.addTitle("Curriculum Vitae");

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfWriter writer = PdfWriter.getInstance(document, baos);

    Font generalFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11);
    Font titleFont = FontFactory.getFont(FontFactory.TIMES_BOLD, 12);
    Font titleSmallFont = FontFactory.getFont(FontFactory.TIMES_BOLD, 11);
    Font italicFont = FontFactory.getFont(FontFactory.TIMES_ITALIC, 11);
    Font italicSmallFont = FontFactory.getFont(FontFactory.TIMES_ITALIC, 9);

    document.open();
    document.setPageSize(PageSize.A4);

    String imageUrl = "https://lh4.googleusercontent.com/-KFRA5hmh56w/AAAAAAAAAAI/AAAAAAAAAEw/GONhyI4cuy8/photo.jpg";

    Image image = Image.getInstance(new URL(imageUrl));
    image.setAlignment(2);
    image.scaleAbsolute(110f, 120f);
    // document.add(image);
    Chunk chunk = new Chunk();
    chunk.setFont(titleFont);
    chunk.append("Curriculum Vitae");

    Paragraph paragraph = new Paragraph();
    paragraph.setAlignment(Element.ALIGN_CENTER);
    paragraph.add(chunk);
    emptyLine(paragraph, 1);
    document.add(paragraph);

    chunk = new Chunk();
    chunk.setFont(italicFont);
    chunk.append("of");

    paragraph = new Paragraph();
    paragraph.setAlignment(Element.ALIGN_CENTER);
    paragraph.add(chunk);
    emptyLine(paragraph, 1);
    document.add(paragraph);

    chunk = new Chunk();
    chunk.setFont(generalFont);
    chunk.append("Mir Md. Kawsur");
    chunk.append("\n");
    chunk.append("34/1 Kazi Riaz Uddin Road Posta Lalbagh Dhaka - 1211, Bangladesh");
    chunk.append("\n");
    chunk.append("01672494863");
    chunk.append("\n");
    chunk.append("kawsurilu@yahoo.com");

    paragraph = new Paragraph();
    paragraph.add(chunk);

    PdfPTable table = new PdfPTable(3);
    table.setWidthPercentage(100);
    table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);

    PdfPCell cell = new PdfPCell();
    cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
    cell.setBorder(0);
    cell.addElement(paragraph);
    table.addCell(cell);

    cell = new PdfPCell();
    cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
    cell.setBorder(0);
    cell.addElement(new Chunk(""));
    table.addCell(cell);

    cell = new PdfPCell();
    cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
    cell.setBorder(0);
    cell.addElement(image);
    table.addCell(cell);
    document.add(table);

    paragraph = new Paragraph();
    emptyLine(paragraph, 1);
    document.add(paragraph);

    table = new PdfPTable(1);
    table.setWidthPercentage(100);
    table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);

    chunk = new Chunk();
    chunk.setFont(titleFont);
    chunk.append("Education");

    paragraph = new Paragraph();
    paragraph.setFont(titleSmallFont);
    paragraph.add(chunk);

    cell = new PdfPCell();
    cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
    cell.setBorder(Rectangle.TOP);
    cell.addElement(paragraph);

    table.addCell(cell);
    document.add(table);

    table = new PdfPTable(new float[] {new Float(2), new Float(4), new Float(1), new Float(0.6)});
    table.setWidthPercentage(100);

    chunk = new Chunk();
    chunk.append("Bachelor of Science in computer science and engineering");

    cell = new PdfPCell();
    cell.addElement(chunk);
    table.addCell(cell);

    chunk = new Chunk();
    chunk.append("American International University-Bangladesh, Banani-Dhaka");

    cell = new PdfPCell();
    cell.addElement(chunk);
    table.addCell(cell);

    chunk = new Chunk();
    chunk.append("1st Division 1st Class");

    cell = new PdfPCell();
    cell.addElement(chunk);
    table.addCell(cell);

    chunk = new Chunk();
    chunk.append("1986");

    cell = new PdfPCell();
    cell.addElement(chunk);
    table.addCell(cell);

    document.add(table);

    document.close();
    baos.writeTo(pOutputStream);
  }

  void emptyLine(Paragraph p, int number) {
    for(int i = 0; i < number; i++) {
      p.add(new Paragraph(" "));
    }
  }
}
