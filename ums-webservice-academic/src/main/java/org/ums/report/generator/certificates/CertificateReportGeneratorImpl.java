package org.ums.report.generator.certificates;

import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.fee.FeeCategory;
import org.ums.fee.FeeCategoryManager;
import org.ums.report.generator.certificates.support.*;
import org.ums.report.generator.testimonial.TestimonialGenerator;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Monjur-E-Morshed on 07-Nov-17.
 */
@Component
public class CertificateReportGeneratorImpl implements CertificateReportGenerator {

  @Autowired
  SemesterFinalGradesheetReport mSemesterFinalGradesheetReport;
  @Autowired
  FeeCategoryManager mFeeCategoryManager;
  @Autowired
  CertificateReport mCertificateReport;
  @Autowired
  TranscriptReport mTranscriptReport;
  @Autowired
  LanguageProficiencyCertificateReport mLanguageProficiencyCertificateReport;
  @Autowired
  MigrationCertificateReport migrationCertificateReport;
  @Autowired
  TestimonialGenerator mTestimonialGenerator;

  @Override
  public void createReport(String pFeeCategoryId, String pStudentId, Integer pSemesterId, OutputStream pOutputStream)
      throws IOException, DocumentException {
    FeeCategory feeCategory = mFeeCategoryManager.get(pFeeCategoryId);

    if (feeCategory.getFeeId().equals("GRADESHEET_PROVISIONAL") || feeCategory.getFeeId().equals("GRADESHEET_DUPLICATE")) {
      mSemesterFinalGradesheetReport.createGradeSheetPdf(feeCategory, pStudentId, pSemesterId, pOutputStream);
    } else if (feeCategory.getFeeId().equals("PROVISIONAL_CERTIFICATE_DUPLICATE")
        || feeCategory.getFeeId().equals("PROVISIONAL_CERTIFICATE_INITIAL")
        || feeCategory.getFeeId().equals("CERTIFICATE_CONVOCATION")
        || feeCategory.getFeeId().equals("CERTIFICATE_DUPLICATE")) {
      mCertificateReport.createGradeSheetPdf(feeCategory, pStudentId, pSemesterId, pOutputStream);
    } else if (feeCategory.getFeeId().equals("GRADESHEET_PROVISIONAL")
        || feeCategory.getFeeId().equals("GRADESHEET_DUPLICATE")) {
      mTranscriptReport.createGradeSheetPdf(feeCategory, pStudentId, pSemesterId, pOutputStream);
    } else if (feeCategory.getFeeId().equals("CERTIFICATE_LANGUAGE_PROFICIENCY")) {
      mLanguageProficiencyCertificateReport.createLanguageProficiencyCertificateReport(feeCategory, pStudentId,
          pSemesterId, pOutputStream);
    } else if (feeCategory.getFeeId().equals("CERTIFICATE_STUDENTSHIP")) {

    } else if (feeCategory.getFeeId().equals("CERTIFICATE_MIGRATION")) {
      migrationCertificateReport.createMigrationCertificatePdf(feeCategory, pStudentId, pSemesterId, pOutputStream);
    } else if (feeCategory.getFeeId().equals("TESTIMONIAL_DEPARTMENT")) {
      mTestimonialGenerator.createTestimonial(pStudentId, pOutputStream);
    } else {

    }

  }
}
