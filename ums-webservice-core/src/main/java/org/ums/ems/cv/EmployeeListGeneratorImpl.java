package org.ums.ems.cv;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.domain.model.immutable.Employee;
import org.ums.ems.profilemanagement.personal.PersonalInformationManager;
import org.ums.manager.DepartmentManager;
import org.ums.manager.DesignationManager;
import org.ums.manager.EmployeeManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class EmployeeListGeneratorImpl implements EmployeeListGenerator {

  @Autowired
  private EmployeeManager mEmployeeManager;

  @Autowired
  private DesignationManager mDesignationManager;

  @Autowired
  private DepartmentManager mDepartmentManager;

  @Autowired
  private PersonalInformationManager mPersonalInformationManager;

  Font mBoldFont = new Font(Font.FontFamily.TIMES_ROMAN, 10f, Font.BOLD, BaseColor.BLACK);

  public static final Integer CREATE_NEW_PAGE_FOR_EACH_DEPT = 1;
  public static final Integer CREATE_CONTINUOUS_PRINTING = 2;

  @Override
    public void printEmployeeList(String pDeptList, String pEmpTypeList, Integer pChoice, OutputStream pOutputStream) throws IOException, DocumentException {

        List<Employee> employeeList = mEmployeeManager.downloadEmployeeList(pDeptList, pEmpTypeList);

        Map<String, List<Employee>> employeeGroupByDept =
                employeeList.stream()
                        .collect(Collectors.groupingBy(w -> w.getDepartment().getId()));

        Map<String, List<Employee>> employeeSortedGroupByDept = employeeGroupByDept.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        Document document = new Document();
        document.addTitle("Meeting Minutes");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        EmployeeListFooter footer = new EmployeeListFooter();
        writer.setPageEvent(footer);

        Font fontTimes11Normal = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11);
        Font fontTimes11Bold = FontFactory.getFont(FontFactory.TIMES_BOLD, 11);
        Font fontTimes14Bold = FontFactory.getFont(FontFactory.TIMES_BOLD, 14);
        Font fontTimes16Bold = FontFactory.getFont(FontFactory.TIMES_BOLD, 16);

        document.setPageSize(PageSize.A4);
        document.setMargins(50, 45, 50, 60);
        document.setMarginMirroring(false);
        document.open();

        Paragraph paragraph = null;
        Chunk chunk = null;

        chunk = new Chunk("Ahsanullah University of Science & Technology", fontTimes16Bold);
        paragraph = new Paragraph(chunk);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);
        chunk = new Chunk("141-142, Love Road, Tejgaon I/A, Dhaka-1208", fontTimes11Bold);
        paragraph = new Paragraph(chunk);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);

        paragraph = new Paragraph();
        emptyLine(paragraph, 1);
        document.add(paragraph);

        chunk = new Chunk("List of Employees", fontTimes14Bold);
        chunk.setUnderline(1.0f, -2.3f);
        paragraph = new Paragraph(chunk);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);

        paragraph = new Paragraph();
        emptyLine(paragraph, 1);
        document.add(paragraph);

        int k = 0;

        for (Map.Entry<String, List<Employee>> map : employeeSortedGroupByDept.entrySet()) {

            chunk = new Chunk("Department: ", fontTimes11Normal);
            Chunk chunk1 = new Chunk(mDepartmentManager.get(map.getKey()).getLongName(), fontTimes11Bold).setUnderline(1.0f, -2.3f);
            paragraph = new Paragraph(chunk);
            paragraph.add(chunk1);
            document.add(paragraph);

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setTotalWidth(new float[]{30, 70, 180, 110});

            paragraph = new Paragraph();
            emptyLine(paragraph, 1);
            document.add(paragraph);
            k = 0;

            PdfPCell cell = new PdfPCell(new Phrase("#"));
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Employee Id"));
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Employee Name"));
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Designation"));
            table.addCell(cell);

            for (Employee employee : map.getValue()) {
                k++;
                cell = new PdfPCell(new Phrase(k + "."));
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(employee.getId()));
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(mPersonalInformationManager.get(employee.getId()).getFullName()));
                table.addCell(cell);

                cell =
                        new PdfPCell(new Phrase(mDesignationManager.get(employee.getDesignationId())
                                .getDesignationName()));
                table.addCell(cell);

            }
            document.add(table);
            if (pChoice.equals(CREATE_NEW_PAGE_FOR_EACH_DEPT)) {
                document.newPage();
            } else {
                paragraph = new Paragraph();
                emptyLine(paragraph, 4);
                document.add(paragraph);
            }
        }

        document.close();
        baos.writeTo(pOutputStream);
    }

  void emptyLine(Paragraph p, int number) {
    for(int i = 0; i < number; i++) {
      p.add(new Paragraph(" "));
    }
  }

  class EmployeeListFooter extends PdfPageEventHelper {

    @Override
    public void onEndPage(PdfWriter writer, Document pDocument) {
      PdfContentByte cb = writer.getDirectContent();
      String text = String.format("Page %s", writer.getCurrentPageNumber());
      Paragraph paragraph = new Paragraph(text, mBoldFont);
      ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, new Phrase(paragraph),
          (pDocument.right() - pDocument.left()) / 2 + pDocument.leftMargin(), pDocument.bottom() - 25, 0);
    }
  }
}
