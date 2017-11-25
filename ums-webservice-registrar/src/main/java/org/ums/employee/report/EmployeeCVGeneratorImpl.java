package org.ums.employee.report;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfImage;
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

  // public static final String IMG =
  // "https://lh4.googleusercontent.com/-KFRA5hmh56w/AAAAAAAAAAI/AAAAAAAAAEw/GONhyI4cuy8/photo.jpg";

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

    // Image image = Image.getInstance(IMG);
    // PdfImage stream = new PdfImage(image, "", null);

    Chunk curriculumVitaeTextChunk = new Chunk();
    curriculumVitaeTextChunk.setFont(title);
    curriculumVitaeTextChunk.append("Curriculum Vitae");
    Chunk ofTextChunk = new Chunk();
    ofTextChunk.setFont(italic);
    ofTextChunk.append("\n\n\n");
    ofTextChunk.append("of");
    ofTextChunk.append("\n\n\n");
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
    personAddressChunk.append(personalInformation.getPresentAddressCountryId() == null
        || personalInformation.getPresentAddressCountryId() == 0 ? "" : mCountryManager.get(
        personalInformation.getPresentAddressCountryId()).getName());
    personAddressChunk.append(" ");
    personAddressChunk.append(personalInformation.getPresentAddressDivisionId() == null
        || personalInformation.getPresentAddressDivisionId() == 0 ? "" : mDIvisionManager.get(
        personalInformation.getPresentAddressDivisionId()).getDivisionName());
    personAddressChunk.append(" ");
    personAddressChunk.append(personalInformation.getPresentAddressDistrictId() == null
        || personalInformation.getPresentAddressDistrictId() == 0 ? "" : mDistrictManager.get(
        personalInformation.getPresentAddressDistrictId()).getDistrictName());
    personAddressChunk.append(" ");
    personAddressChunk.append(personalInformation.getPresentAddressThanaId() == null
        || personalInformation.getPresentAddressThanaId() == 0 ? "" : mThanaManager.get(
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

    Paragraph academicInformationText = new Paragraph();
    emptyLine(academicInformationText, 4);
    academicInformationText.setAlignment(Element.ALIGN_LEFT);
    academicInformationText.setFont(common);
    academicInformationText.add(academicSectionHeaderChunk);
    emptyLine(academicInformationText, 1);
    for(AcademicInformation academicInformation1 : academicInformation) {
      Chunk degreeNameChunk = new Chunk();
      degreeNameChunk.setFont(title);
      degreeNameChunk.append(mAcademicDegreeManager.get(academicInformation1.getDegreeId()).getDegreeName());
      academicInformationText.add(degreeNameChunk);

      academicInformationText.add("\nInstitute: ");
      Chunk degreeTitle = new Chunk();
      degreeTitle.append(academicInformation1.getInstitute());
      academicInformationText.add(degreeTitle);

      academicInformationText.add("\nPassing Year: ");
      Chunk passingYear = new Chunk();
      passingYear.append(academicInformation1.getPassingYear());
      academicInformationText.add(passingYear);

      academicInformationText.add("\nPassing Year: ");
      Chunk result = new Chunk();
      result.append(academicInformation1.getResult() == null ? "(Not Given)" : academicInformation1.getResult());
      academicInformationText.add(result);

      emptyLine(academicInformationText, 1);
    }
    document.add(academicInformationText);

    Chunk publicationSectionHeaderChunk = new Chunk();
    publicationSectionHeaderChunk.append("Publication Information: ");
    publicationSectionHeaderChunk.setUnderline(0.5f, -2.3f);

    Paragraph publicationInformationText = new Paragraph();
    emptyLine(publicationInformationText, 4);
    publicationInformationText.setAlignment(Element.ALIGN_LEFT);
    publicationInformationText.setFont(common);
    publicationInformationText.add(publicationSectionHeaderChunk);
    emptyLine(publicationInformationText, 1);
    int i = 0;
    for(PublicationInformation publicationInformation1 : publicationInformation) {
      i++;
      Chunk publicationSerialChunk = new Chunk();
      publicationSerialChunk.setFont(title);
      publicationSerialChunk.append(i + ". ");
      publicationInformationText.add(publicationSerialChunk);

      Chunk publicationTitleChunk = new Chunk();
      publicationTitleChunk.setFont(common);
      publicationTitleChunk
          .append(publicationInformation1.getTitle() == null ? "" : publicationInformation1.getTitle());
      publicationInformationText.add(publicationTitleChunk);

      emptyLine(publicationInformationText, 1);
    }
    document.add(publicationInformationText);

    Chunk trainingSectionHeaderChunk = new Chunk();
    trainingSectionHeaderChunk.append("Training Information: ");
    trainingSectionHeaderChunk.setUnderline(0.5f, -2.3f);

    Paragraph trainingInformationText = new Paragraph();
    emptyLine(trainingInformationText, 4);
    trainingInformationText.setAlignment(Element.ALIGN_LEFT);
    trainingInformationText.setFont(common);
    trainingInformationText.add(trainingSectionHeaderChunk);
    emptyLine(trainingInformationText, 1);
    Chunk localTitle = new Chunk();
    localTitle.setFont(title);
    localTitle.append("Local\n\n");
    trainingInformationText.add(localTitle);
    for(TrainingInformation trainingInformation1 : trainingInformation) {
      if(trainingInformation1.getTrainingCategoryId() == 10) {
        Chunk trainingNameChunk = new Chunk();
        trainingNameChunk.setFont(title);
        trainingNameChunk.append(trainingInformation1.getTrainingName());
        trainingInformationText.add(trainingNameChunk);

        trainingInformationText.add("\nInstitute: ");
        Chunk trainingInstitution = new Chunk();
        trainingInstitution.append(trainingInformation1.getTrainingInstitute());
        trainingInformationText.add(trainingInstitution);

        trainingInformationText.add("\nFrom: ");
        Chunk trainingFrom = new Chunk();
        trainingFrom.append(trainingInformation1.getTrainingFromDate());
        trainingInformationText.add(trainingFrom);

        trainingInformationText.add("\nTo: ");
        Chunk trainingTo = new Chunk();
        trainingTo.append(trainingInformation1.getTrainingToDate());
        trainingInformationText.add(trainingTo);

        trainingInformationText.add("\nDuration: ");
        Chunk trainingDuration = new Chunk();
        trainingDuration.append(trainingInformation1.getTrainingDurationString());
        trainingInformationText.add(trainingDuration);

        emptyLine(trainingInformationText, 1);
      }
    }

    Chunk foreignTitle = new Chunk();
    foreignTitle.setFont(title);
    foreignTitle.append("Foreign\n\n");
    trainingInformationText.add(foreignTitle);
    for(TrainingInformation trainingInformation1 : trainingInformation) {
      if(trainingInformation1.getTrainingCategoryId() == 20) {
        Chunk trainingNameChunk = new Chunk();
        trainingNameChunk.setFont(title);
        trainingNameChunk.append(trainingInformation1.getTrainingName());
        trainingInformationText.add(trainingNameChunk);

        trainingInformationText.add("\nInstitute: ");
        Chunk trainingInstitution = new Chunk();
        trainingInstitution.append(trainingInformation1.getTrainingInstitute());
        trainingInformationText.add(trainingInstitution);

        trainingInformationText.add("\nFrom: ");
        Chunk trainingFrom = new Chunk();
        trainingFrom.append(trainingInformation1.getTrainingFromDate());
        trainingInformationText.add(trainingFrom);

        trainingInformationText.add("\nTo: ");
        Chunk trainingTo = new Chunk();
        trainingTo.append(trainingInformation1.getTrainingToDate());
        trainingInformationText.add(trainingTo);

        trainingInformationText.add("\nDuration: ");
        Chunk trainingDuration = new Chunk();
        trainingDuration.append(trainingInformation1.getTrainingDurationString());
        trainingInformationText.add(trainingDuration);

        emptyLine(trainingInformationText, 1);
      }
    }
    document.add(trainingInformationText);

    Chunk experienceSectionHeaderChunk = new Chunk();
    experienceSectionHeaderChunk.append("Experience Information: ");
    experienceSectionHeaderChunk.setUnderline(0.5f, -2.3f);

    Paragraph experienceInformationText = new Paragraph();
    emptyLine(experienceInformationText, 4);
    experienceInformationText.setAlignment(Element.ALIGN_LEFT);
    experienceInformationText.setFont(common);
    experienceInformationText.add(experienceSectionHeaderChunk);
    document.add(experienceInformationText);

    Chunk awardSectionHeaderChunk = new Chunk();
    awardSectionHeaderChunk.append("Award Information: ");
    awardSectionHeaderChunk.setUnderline(0.5f, -2.3f);

    Paragraph awardInformationText = new Paragraph();
    emptyLine(awardInformationText, 4);
    awardInformationText.setAlignment(Element.ALIGN_LEFT);
    awardInformationText.setFont(common);
    awardInformationText.add(awardSectionHeaderChunk);
    document.add(awardInformationText);

    Chunk serviceSectionHeaderChunk = new Chunk();
    serviceSectionHeaderChunk.append("Service Information: ");
    serviceSectionHeaderChunk.setUnderline(0.5f, -2.3f);

    Paragraph serviceInformationText = new Paragraph();
    emptyLine(serviceInformationText, 4);
    serviceInformationText.setAlignment(Element.ALIGN_LEFT);
    serviceInformationText.setFont(common);
    serviceInformationText.add(serviceSectionHeaderChunk);
    document.add(serviceInformationText);

    // List<Chunk> academicChunk = new ArrayList<>();
    // for(AcademicInformation academicInformation1: academicInformation) {
    // Chunk degreeName = new Chunk();
    // degreeName.setFont(common);
    // academicChunk.add(mAcademicDegreeManager.get(academicInformation1.getDegreeId()).getDegreeName());
    // }

    document.close();
    baos.writeTo(pOutputStream);
  }

  void emptyLine(Paragraph p, int number) {
    for(int i = 0; i < number; i++) {
      p.add(new Paragraph(" "));
    }
  }
}
