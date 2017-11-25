package org.ums.employee.report;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.ums.employee.academic.AcademicInformation;
import org.ums.employee.academic.AcademicInformationManager;
import org.ums.employee.academic.PersistentAcademicInformation;
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
import org.ums.manager.common.CountryManager;
import org.ums.manager.common.DistrictManager;
import org.ums.manager.common.DivisionManager;
import org.ums.manager.common.ThanaManager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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

    Font common = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14);
    Font title = FontFactory.getFont(FontFactory.TIMES_BOLD, 16);
    Font italic = FontFactory.getFont(FontFactory.TIMES_ITALIC, 12);

    document.open();
    document.setPageSize(PageSize.A4);

    Chunk curriculumVitaeTextChunk = new Chunk();
    curriculumVitaeTextChunk.setFont(title);
    curriculumVitaeTextChunk.append("Curriculum Vitae\n");
    Chunk ofTextChunk = new Chunk();
    ofTextChunk.setFont(italic);
    ofTextChunk.append("of\n");
    Chunk personNameTextChunk = new Chunk();
    personNameTextChunk.setFont(title);
    personNameTextChunk.append(personalInformation.getFirstName() == null ? "" : personalInformation.getFirstName());
    personNameTextChunk.append(" ");
    personNameTextChunk.append(personalInformation.getLastName() == null ? "" : personalInformation.getLastName());
    personNameTextChunk.append("\n");
    Chunk personAddressChunk = new Chunk();
    personAddressChunk.setFont(common);
    personAddressChunk.append(personalInformation.getPresentAddressLine1() == null ? "" : personalInformation
        .getPresentAddressLine1());
    personAddressChunk.append(" ");
    personAddressChunk.append(personalInformation.getPresentAddressLine2() == null ? "" : personalInformation
        .getPresentAddressLine2());
    personAddressChunk.append(" ");
    personAddressChunk.append(personalInformation.getPresentAddressCountryId() == 0 ? "" : mCountryManager.get(
        personalInformation.getPresentAddressCountryId()).getName());
    personAddressChunk.append(" ");
    personAddressChunk.append(personalInformation.getPresentAddressDivisionId() == 0 ? "" : mDIvisionManager.get(
        personalInformation.getPresentAddressDivisionId()).getDivisionName());
    personAddressChunk.append(" ");
    personAddressChunk.append(personalInformation.getPresentAddressDistrictId() == 0 ? "" : mDistrictManager.get(
        personalInformation.getPresentAddressDistrictId()).getDistrictName());
    personAddressChunk.append(" ");
    personAddressChunk.append(personalInformation.getPresentAddressThanaId() == 0 ? "" : mThanaManager.get(
        personalInformation.getPresentAddressThanaId()).getThanaName());
    personAddressChunk.append(" ");
    personAddressChunk.append(personalInformation.getPresentAddressPostCode() == null ? "" : personalInformation
        .getPresentAddressPostCode());
    personAddressChunk.append("\n");
    Chunk personMobile = new Chunk();
    personMobile.setFont(common);
    personMobile.append(personalInformation.getMobileNumber() == null ? "" : personalInformation.getMobileNumber());
    personMobile.append("\n");
    Chunk personEmail = new Chunk();
    personEmail.setFont(common);
    personEmail.append(personalInformation.getOrganizationalEmail() == null ? "" : personalInformation
        .getOrganizationalEmail());
    personEmail.append(" ");
    personEmail.append(personalInformation.getPersonalEmail() == null ? "" : personalInformation.getPersonalEmail());
    personEmail.append("\n");

    Paragraph header = new Paragraph();
    header.setAlignment(Element.ALIGN_CENTER);
    header.add(curriculumVitaeTextChunk);
    header.add(ofTextChunk);
    header.add(personNameTextChunk);
    header.add(personAddressChunk);
    header.add(personMobile);
    header.add(personEmail);
    document.add(header);

    Chunk academicSectionHeaderChunk = new Chunk();
    academicSectionHeaderChunk.append("Academic Information: ");
    academicSectionHeaderChunk.setUnderline(0.5f, -2.3f);

    Paragraph personalInformationText = new Paragraph();
    emptyLine(personalInformationText, 4);
    personalInformationText.setAlignment(Element.ALIGN_LEFT);
    personalInformationText.setFont(common);
    personalInformationText.add(academicSectionHeaderChunk);
    document.add(personalInformationText);

    List<Chunk> academicChunk = new ArrayList<>();
    for(int i = 0; i < academicInformation.size(); i++){
      Chunk degreeName = new Chunk();
      degreeName.setFont(common);
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
