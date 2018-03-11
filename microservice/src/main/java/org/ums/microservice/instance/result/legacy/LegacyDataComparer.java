package org.ums.microservice.instance.result.legacy;

import java.io.File;
import java.io.FileOutputStream;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.shiro.mgt.SecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.ums.configuration.UMSConfiguration;
import org.ums.domain.model.immutable.StudentRecord;
import org.ums.manager.StudentRecordManager;
import org.ums.microservice.AbstractService;
import org.ums.result.legacy.LegacyTabulation;
import org.ums.result.legacy.LegacyTabulationManager;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

public class LegacyDataComparer extends AbstractService {
  private static final Logger mLogger = LoggerFactory.getLogger(LegacyDataComparer.class);
  final Font universityNameFont = new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.BOLD);
  final Font infoFont = new Font(Font.FontFamily.TIMES_ROMAN, 8);
  final Font tableFont = new Font(Font.FontFamily.TIMES_ROMAN, 10f);
  final Font pageNoFont = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.ITALIC);

  private UMSConfiguration mUMSConfiguration;
  private SecurityManager mSecurityManager;
  private LegacyTabulationManager mLegacyTabulationManager;
  private StudentRecordManager mStudentRecordManager;

  LegacyDataComparer() {}

  public LegacyDataComparer(UMSConfiguration pUMSConfiguration, SecurityManager pSecurityManager,
      LegacyTabulationManager pLegacyTabulationManager, StudentRecordManager pStudentRecordManager) {
    mUMSConfiguration = pUMSConfiguration;
    mSecurityManager = pSecurityManager;
    mLegacyTabulationManager = pLegacyTabulationManager;
    mStudentRecordManager = pStudentRecordManager;
  }

  @Override
  public void start() {
    List<LegacyTabulation> legacyTabulationList = mLegacyTabulationManager.getAll();
    Map<String, List<StudentRecord>> studentRecordMap =
        mStudentRecordManager.getAll().stream().collect(Collectors.groupingBy(StudentRecord::getStudentId));

    Document document = new Document();
    document.addTitle("Tabulation");

    PdfWriter writer = null;
    try {
      File file = new File("D:/legacy-tabulation-compare.pdf");
      FileOutputStream outputStream = new FileOutputStream(file);
      writer = PdfWriter.getInstance(document, outputStream);

      document.setPageSize(PageSize.LEGAL.rotate());
      document.setMargins(20, 20, 40, 40);

      HeaderFooter event = new HeaderFooter();
      writer.setBoxSize("art", new Rectangle(20, 54, 1024, 850));
      writer.setPageEvent(event);

      document.open();

      PdfPTable contentTable = new PdfPTable(12);
      contentTable.setWidths(new float[] {2, 5, 5, 5, 5, 5, 5, 25, 25, 6, 6, 6});
      contentTable.setWidthPercentage(100);

      PdfPCell cell = new PdfPCell();
      cell.setHorizontalAlignment(Element.ALIGN_LEFT);
      cell.setVerticalAlignment(Element.ALIGN_CENTER);
      Paragraph paragraph = new Paragraph("#", infoFont);
      paragraph.setAlignment(Element.ALIGN_LEFT);
      cell.addElement(paragraph);
      cell.setColspan(1);
      contentTable.addCell(cell);

      cell = new PdfPCell();
      cell.setHorizontalAlignment(Element.ALIGN_LEFT);
      cell.setVerticalAlignment(Element.ALIGN_CENTER);
      paragraph = new Paragraph("StudentId", infoFont);
      paragraph.setAlignment(Element.ALIGN_LEFT);
      cell.addElement(paragraph);
      cell.setColspan(1);
      contentTable.addCell(cell);

      cell = new PdfPCell();
      cell.setHorizontalAlignment(Element.ALIGN_LEFT);
      cell.setVerticalAlignment(Element.ALIGN_CENTER);
      paragraph = new Paragraph("SEMESTER", infoFont);
      paragraph.setAlignment(Element.ALIGN_LEFT);
      cell.addElement(paragraph);
      cell.setColspan(1);
      contentTable.addCell(cell);

      cell = new PdfPCell();
      cell.setHorizontalAlignment(Element.ALIGN_LEFT);
      cell.setVerticalAlignment(Element.ALIGN_CENTER);
      paragraph = new Paragraph("A. GPA", infoFont);
      paragraph.setAlignment(Element.ALIGN_LEFT);
      cell.addElement(paragraph);
      cell.setColspan(1);
      contentTable.addCell(cell);

      cell = new PdfPCell();
      cell.setHorizontalAlignment(Element.ALIGN_LEFT);
      cell.setVerticalAlignment(Element.ALIGN_CENTER);
      paragraph = new Paragraph("P. GPA", infoFont);
      paragraph.setAlignment(Element.ALIGN_LEFT);
      cell.addElement(paragraph);
      cell.setColspan(1);
      contentTable.addCell(cell);

      cell = new PdfPCell();
      cell.setHorizontalAlignment(Element.ALIGN_LEFT);
      cell.setVerticalAlignment(Element.ALIGN_CENTER);
      paragraph = new Paragraph("A. CGPA", infoFont);
      paragraph.setAlignment(Element.ALIGN_LEFT);
      cell.addElement(paragraph);
      cell.setColspan(1);
      contentTable.addCell(cell);

      cell = new PdfPCell();
      cell.setHorizontalAlignment(Element.ALIGN_LEFT);
      cell.setVerticalAlignment(Element.ALIGN_CENTER);
      paragraph = new Paragraph("P. GPA", infoFont);
      paragraph.setAlignment(Element.ALIGN_LEFT);
      cell.addElement(paragraph);
      cell.setColspan(1);
      contentTable.addCell(cell);

      cell = new PdfPCell();
      cell.setHorizontalAlignment(Element.ALIGN_LEFT);
      cell.setVerticalAlignment(Element.ALIGN_CENTER);
      paragraph = new Paragraph("A. COMMENT", infoFont);
      paragraph.setAlignment(Element.ALIGN_LEFT);
      cell.addElement(paragraph);
      cell.setColspan(1);
      contentTable.addCell(cell);

      cell = new PdfPCell();
      cell.setHorizontalAlignment(Element.ALIGN_LEFT);
      cell.setVerticalAlignment(Element.ALIGN_CENTER);
      paragraph = new Paragraph("P. COMMENT", infoFont);
      paragraph.setAlignment(Element.ALIGN_LEFT);
      cell.addElement(paragraph);
      cell.setColspan(1);
      contentTable.addCell(cell);

      cell = new PdfPCell();
      cell.setHorizontalAlignment(Element.ALIGN_LEFT);
      cell.setVerticalAlignment(Element.ALIGN_CENTER);
      paragraph = new Paragraph("GPA Match", infoFont);
      paragraph.setAlignment(Element.ALIGN_LEFT);
      cell.addElement(paragraph);
      cell.setColspan(1);
      contentTable.addCell(cell);

      cell = new PdfPCell();
      cell.setHorizontalAlignment(Element.ALIGN_LEFT);
      cell.setVerticalAlignment(Element.ALIGN_CENTER);
      paragraph = new Paragraph("CGPA Match", infoFont);
      paragraph.setAlignment(Element.ALIGN_LEFT);
      cell.addElement(paragraph);
      cell.setColspan(1);
      contentTable.addCell(cell);

      cell = new PdfPCell();
      cell.setHorizontalAlignment(Element.ALIGN_LEFT);
      cell.setVerticalAlignment(Element.ALIGN_CENTER);
      paragraph = new Paragraph("Comment Match", infoFont);
      paragraph.setAlignment(Element.ALIGN_LEFT);
      cell.addElement(paragraph);
      cell.setColspan(1);
      contentTable.addCell(cell);
      int serial = 0;
      for(int i = 0; i < legacyTabulationList.size(); i++) {
        LegacyTabulation legacyTabulation = legacyTabulationList.get(i);
        if(!legacyTabulation.getStudentId().startsWith("08") && !legacyTabulation.getStudentId().startsWith("09")
            && !legacyTabulation.getStudentId().startsWith("10") && !legacyTabulation.getStudentId().startsWith("12")
            && !legacyTabulation.getStudentId().startsWith("11")) {
          List<StudentRecord> studentRecords = studentRecordMap.get(legacyTabulation.getStudentId());
          boolean found = false;
          if(studentRecords != null) {
            for(StudentRecord studentRecord : studentRecords) {
              if(studentRecord.getSemesterId().intValue() == legacyTabulation.getSemesterId()
                  && studentRecord.getYear().intValue() == legacyTabulation.getYear()
                  && studentRecord.getAcademicSemester().intValue() == legacyTabulation.getAcademicSemester()) {
                found = true;
                boolean addToReport = false;

                String processGPAString = studentRecord.getGPA().toString();
                if(processGPAString.length() > 7) {
                  processGPAString = processGPAString.substring(0, 6);
                }
                String actualGPAString = legacyTabulation.getGpa().toString();
                if(actualGPAString.length() > 7) {
                  actualGPAString = actualGPAString.substring(0, 6);
                }

                String processCGPAString = studentRecord.getCGPA().toString();
                if(processCGPAString.length() > 7) {
                  processCGPAString = processCGPAString.substring(0, 6);
                }
                String actualCGPAString = legacyTabulation.getCgpa().toString();
                if(actualCGPAString.length() > 7) {
                  actualCGPAString = actualCGPAString.substring(0, 6);
                }

                Paragraph gpaMatch = null, cgpaMatch = null, commentMatch = null;

                if(!checkGpa(legacyTabulation.getGpa(), studentRecord.getGPA())) {
                  addToReport = true;
                  gpaMatch = new Paragraph("No", infoFont);
                }
                else {
                  gpaMatch = new Paragraph("Yes", infoFont);
                }

                if(!checkGpa(legacyTabulation.getCgpa(), studentRecord.getCGPA())) {
                  cgpaMatch = new Paragraph("No", infoFont);
                  addToReport = true;
                }
                else {
                  cgpaMatch = new Paragraph("Yes", infoFont);
                }

                if(!commentMatch(legacyTabulation.getComment(), studentRecord.getTabulationSheetRemarks())) {
                  commentMatch = new Paragraph("No", infoFont);
                  addToReport = true;
                }
                else {
                  commentMatch = new Paragraph("Yes", infoFont);
                }

                if(addToReport) {
                  cell = new PdfPCell();
                  cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                  cell.setVerticalAlignment(Element.ALIGN_CENTER);
                  paragraph = new Paragraph(++serial + "", infoFont);
                  paragraph.setAlignment(Element.ALIGN_LEFT);
                  cell.addElement(paragraph);
                  cell.setColspan(1);
                  contentTable.addCell(cell);

                  cell = new PdfPCell();
                  cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                  cell.setVerticalAlignment(Element.ALIGN_CENTER);
                  paragraph = new Paragraph(legacyTabulation.getStudentId(), infoFont);
                  paragraph.setAlignment(Element.ALIGN_LEFT);
                  cell.addElement(paragraph);
                  cell.setColspan(1);
                  contentTable.addCell(cell);

                  cell = new PdfPCell();
                  cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                  cell.setVerticalAlignment(Element.ALIGN_CENTER);
                  paragraph = new Paragraph(legacyTabulation.getSemester().getName(), infoFont);
                  paragraph.setAlignment(Element.ALIGN_LEFT);
                  cell.addElement(paragraph);
                  cell.setColspan(1);
                  contentTable.addCell(cell);

                  cell = new PdfPCell();
                  cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                  cell.setVerticalAlignment(Element.ALIGN_CENTER);
                  paragraph = new Paragraph(actualGPAString, infoFont);
                  paragraph.setAlignment(Element.ALIGN_LEFT);
                  cell.addElement(paragraph);
                  cell.setColspan(1);
                  contentTable.addCell(cell);

                  cell = new PdfPCell();
                  cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                  cell.setVerticalAlignment(Element.ALIGN_CENTER);
                  paragraph = new Paragraph(processGPAString, infoFont);
                  paragraph.setAlignment(Element.ALIGN_LEFT);
                  cell.addElement(paragraph);
                  cell.setColspan(1);
                  contentTable.addCell(cell);

                  cell = new PdfPCell();
                  cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                  cell.setVerticalAlignment(Element.ALIGN_CENTER);
                  paragraph = new Paragraph(actualCGPAString, infoFont);
                  paragraph.setAlignment(Element.ALIGN_LEFT);
                  cell.addElement(paragraph);
                  cell.setColspan(1);
                  contentTable.addCell(cell);

                  cell = new PdfPCell();
                  cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                  cell.setVerticalAlignment(Element.ALIGN_CENTER);
                  paragraph = new Paragraph(processCGPAString, infoFont);
                  paragraph.setAlignment(Element.ALIGN_LEFT);
                  cell.addElement(paragraph);
                  cell.setColspan(1);
                  contentTable.addCell(cell);

                  cell = new PdfPCell();
                  cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                  cell.setVerticalAlignment(Element.ALIGN_CENTER);
                  paragraph = new Paragraph(legacyTabulation.getComment(), infoFont);
                  paragraph.setAlignment(Element.ALIGN_LEFT);
                  cell.addElement(paragraph);
                  cell.setColspan(1);
                  contentTable.addCell(cell);

                  cell = new PdfPCell();
                  cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                  cell.setVerticalAlignment(Element.ALIGN_CENTER);
                  paragraph = new Paragraph(studentRecord.getTabulationSheetRemarks(), infoFont);
                  paragraph.setAlignment(Element.ALIGN_LEFT);
                  cell.addElement(paragraph);
                  cell.setColspan(1);
                  contentTable.addCell(cell);

                  cell = new PdfPCell();
                  cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                  cell.setVerticalAlignment(Element.ALIGN_CENTER);
                  gpaMatch.setAlignment(Element.ALIGN_LEFT);
                  cell.addElement(gpaMatch);
                  cell.setColspan(1);
                  contentTable.addCell(cell);

                  cell = new PdfPCell();
                  cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                  cell.setVerticalAlignment(Element.ALIGN_CENTER);
                  cgpaMatch.setAlignment(Element.ALIGN_LEFT);
                  cell.addElement(cgpaMatch);
                  cell.setColspan(1);
                  contentTable.addCell(cell);

                  cell = new PdfPCell();
                  cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                  cell.setVerticalAlignment(Element.ALIGN_CENTER);
                  commentMatch.setAlignment(Element.ALIGN_LEFT);
                  cell.addElement(commentMatch);
                  cell.setColspan(1);
                  contentTable.addCell(cell);
                  break;
                }
              }
            }
            if(!found) {
              cell = new PdfPCell();
              cell.setHorizontalAlignment(Element.ALIGN_LEFT);
              cell.setVerticalAlignment(Element.ALIGN_CENTER);
              paragraph = new Paragraph(++serial + "", infoFont);
              paragraph.setAlignment(Element.ALIGN_LEFT);
              cell.addElement(paragraph);
              cell.setColspan(1);
              contentTable.addCell(cell);

              cell = new PdfPCell();
              cell.setHorizontalAlignment(Element.ALIGN_LEFT);
              cell.setVerticalAlignment(Element.ALIGN_CENTER);
              paragraph = new Paragraph(legacyTabulation.getStudentId(), infoFont);
              paragraph.setAlignment(Element.ALIGN_LEFT);
              cell.addElement(paragraph);
              cell.setColspan(1);
              contentTable.addCell(cell);

              cell = new PdfPCell();
              cell.setHorizontalAlignment(Element.ALIGN_LEFT);
              cell.setVerticalAlignment(Element.ALIGN_CENTER);
              paragraph = new Paragraph(legacyTabulation.getSemester().getName(), infoFont);
              paragraph.setAlignment(Element.ALIGN_LEFT);
              cell.addElement(paragraph);
              cell.setColspan(1);
              contentTable.addCell(cell);

              String actualGPAString = legacyTabulation.getGpa().toString();
              if(actualGPAString.length() > 7) {
                actualGPAString = actualGPAString.substring(0, 6);
              }
              cell = new PdfPCell();
              cell.setHorizontalAlignment(Element.ALIGN_LEFT);
              cell.setVerticalAlignment(Element.ALIGN_CENTER);
              paragraph = new Paragraph(actualGPAString, infoFont);
              paragraph.setAlignment(Element.ALIGN_LEFT);
              cell.addElement(paragraph);
              cell.setColspan(1);
              contentTable.addCell(cell);

              cell = new PdfPCell();
              cell.setHorizontalAlignment(Element.ALIGN_LEFT);
              cell.setVerticalAlignment(Element.ALIGN_CENTER);
              paragraph = new Paragraph("---", infoFont);
              paragraph.setAlignment(Element.ALIGN_LEFT);
              cell.addElement(paragraph);
              cell.setColspan(1);
              contentTable.addCell(cell);

              String actualCGPAString = legacyTabulation.getCgpa().toString();
              if(actualCGPAString.length() > 7) {
                actualCGPAString = actualCGPAString.substring(0, 6);
              }
              cell = new PdfPCell();
              cell.setHorizontalAlignment(Element.ALIGN_LEFT);
              cell.setVerticalAlignment(Element.ALIGN_CENTER);
              paragraph = new Paragraph(actualCGPAString, infoFont);
              paragraph.setAlignment(Element.ALIGN_LEFT);
              cell.addElement(paragraph);
              cell.setColspan(1);
              contentTable.addCell(cell);

              cell = new PdfPCell();
              cell.setHorizontalAlignment(Element.ALIGN_LEFT);
              cell.setVerticalAlignment(Element.ALIGN_CENTER);
              paragraph = new Paragraph("---", infoFont);
              paragraph.setAlignment(Element.ALIGN_LEFT);
              cell.addElement(paragraph);
              cell.setColspan(1);
              contentTable.addCell(cell);

              cell = new PdfPCell();
              cell.setHorizontalAlignment(Element.ALIGN_LEFT);
              cell.setVerticalAlignment(Element.ALIGN_CENTER);
              paragraph = new Paragraph(legacyTabulation.getComment(), infoFont);
              paragraph.setAlignment(Element.ALIGN_LEFT);
              cell.addElement(paragraph);
              cell.setColspan(1);
              contentTable.addCell(cell);

              cell = new PdfPCell();
              cell.setHorizontalAlignment(Element.ALIGN_LEFT);
              cell.setVerticalAlignment(Element.ALIGN_CENTER);
              paragraph = new Paragraph("---", infoFont);
              paragraph.setAlignment(Element.ALIGN_LEFT);
              cell.addElement(paragraph);
              cell.setColspan(1);
              contentTable.addCell(cell);

              cell = new PdfPCell();
              cell.setHorizontalAlignment(Element.ALIGN_LEFT);
              cell.setVerticalAlignment(Element.ALIGN_CENTER);
              paragraph = new Paragraph("No", infoFont);
              paragraph.setAlignment(Element.ALIGN_CENTER);
              cell.addElement(paragraph);
              cell.setColspan(3);
              contentTable.addCell(cell);
            }
          }
        }
      }

      document.add(contentTable);
      document.close();
      mLogger.info("################### DONE ##################");
    } catch(Exception e) {
      e.printStackTrace();
    }
  }

  private boolean checkGpa(final Double pActualGpa, final Double pProcessedGpa) {
    if(pActualGpa - pProcessedGpa == 0D) {
      return true;
    }

    String processGPAString = pProcessedGpa.toString();
    if(processGPAString.length() > 7) {
      processGPAString = processGPAString.substring(0, 6);
    }
    String actualGPAString = pActualGpa.toString();
    if(actualGPAString.length() > 7) {
      actualGPAString = actualGPAString.substring(0, 6);
    }

    if(Double.parseDouble(processGPAString) - Double.parseDouble(actualGPAString) == 0D) {
      return true;
    }

    if(processGPAString.contains(actualGPAString)) {
      return true;
    }

    if(Math.abs(pProcessedGpa - pActualGpa) <= 0.0001D) {
      return true;
    }
    return false;
  }

  public boolean commentMatch(String pActualComment, String pProcessedComment) {
    if((StringUtils.isEmpty(pActualComment) && !StringUtils.isEmpty(pProcessedComment))
        || (!StringUtils.isEmpty(pActualComment) && StringUtils.isEmpty(pProcessedComment))) {
      return false;
    }
    pActualComment = pActualComment.trim();
    pProcessedComment = pProcessedComment.trim();

    if(pActualComment.equalsIgnoreCase(pProcessedComment)) {
      return true;
    }
    if(!StringUtils.isEmpty(pActualComment) && !StringUtils.isEmpty(pProcessedComment)) {
      List<String> actualCarryCourseList = getCourses(pActualComment, true);
      List<String> processedCarryCourseList = getCourses(pProcessedComment, false);
      if(actualCarryCourseList != null && processedCarryCourseList != null) {
        actualCarryCourseList = actualCarryCourseList.stream().map(String::toLowerCase).collect(Collectors.toList());
        Collections.sort(actualCarryCourseList);
        processedCarryCourseList =
            processedCarryCourseList.stream().map(String::toLowerCase).collect(Collectors.toList());
        Collections.sort(processedCarryCourseList);
        actualCarryCourseList.removeAll(processedCarryCourseList);
        return actualCarryCourseList.size() == 0;
      }
    }
    return false;
  }

  private List<String> getCourses(final String pComment, boolean pAddSpace) {
    List<String> actualCarryCourseList = null;

    if(pComment.indexOf("in ") > 0) {
      actualCarryCourseList = new ArrayList<>();
      List<String> actualCarriesList = null;
      String actualCarriesString = pComment.substring(pComment.indexOf("in") + 3);
      actualCarriesString = actualCarriesString.replace("&", ",");
      if(actualCarriesString.indexOf(",") > 0) {
        String[] actualCarries = actualCarriesString.split(",");
        actualCarriesList = Arrays.stream(actualCarries).map(String::trim).collect(Collectors.toList());
      }
      else {
        actualCarriesList = new ArrayList<>();
        actualCarriesList.add(actualCarriesString);
      }

      if(actualCarriesList != null) {
        Pattern pattern = Pattern.compile("\\d+");
        for(String course : actualCarriesList) {
          if(pAddSpace) {
            Matcher matcher = pattern.matcher(course);
            matcher.find();
            String courseNo = "";
            try {
              courseNo = matcher.group();
            } catch(Exception e) {
              e.printStackTrace();
            }
            int index = course.indexOf(courseNo);
            course = course.substring(0, index) + " " + course.substring(index);
            actualCarryCourseList.add(course);
          }
          else {
            actualCarryCourseList.add(course);
          }
        }
      }
    }

    return actualCarryCourseList;
  }

  @Override
  protected SecurityManager getSecurityManager() {
    return mSecurityManager;
  }

  @Override
  protected UMSConfiguration getUMSConfiguration() {
    return mUMSConfiguration;
  }

  private class HeaderFooter extends PdfPageEventHelper {
    int pagenumber;

    public void onStartPage(PdfWriter writer, Document document) {
      pagenumber++;
    }

    public void onEndPage(PdfWriter writer, Document document) {
      Font nFont = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL);
      Phrase footer = new Phrase(String.format("page %d", writer.getPageNumber()), nFont);
      ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_RIGHT, footer, document.right(),
          document.top() + 2, 0);
    }
  }

  public static void main(String[] args) {
    LegacyDataComparer dataComparer = new LegacyDataComparer();
    System.out.println(dataComparer.commentMatch(
        "Promoted to Second Year Second Semester with carry over in IPE2101, CSE1287, MATH1107 & CHEM1107",
        "Promoted to Second Year Second Semester with carry over in MATH 1107, CSE 1287, IPE 2101, CHEM 1107"));
  }
}
