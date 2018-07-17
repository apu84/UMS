package org.ums.employee.cv;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.immutable.Employee;
import org.ums.employee.personal.PersonalInformationManager;
import org.ums.manager.DepartmentManager;
import org.ums.manager.DesignationManager;
import org.ums.manager.EmployeeManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Comparator;
import java.util.List;

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

    @Override
    public void printEmployeeList(String pDeptList, String pEmpTypeList, OutputStream pOutputStream) throws IOException, DocumentException {

        List<Employee> employeeList = mEmployeeManager.downloadEmployeeList(pDeptList, pEmpTypeList);

        List<Department> departmentList = mDepartmentManager.getAll();

        departmentList.sort(Comparator.comparing(Department::getId));

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

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setTotalWidth(new float[]{30, 50, 150, 120, 100});

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

        cell = new PdfPCell(new Phrase("Department"));
        table.addCell(cell);

        for (int j = 0; j < employeeList.size(); j++) {
            k++;
            cell = new PdfPCell(new Phrase(k + "."));
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(employeeList.get(j).getId()));
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(mPersonalInformationManager.get(employeeList.get(j).getId()).getName()));
            table.addCell(cell);

            cell =
                    new PdfPCell(new Phrase(mDesignationManager.get(employeeList.get(j).getDesignationId())
                            .getDesignationName()));
            table.addCell(cell);

            cell =
                    new PdfPCell(new Phrase(mDepartmentManager.get(employeeList.get(j).getDepartment().getId())
                            .getShortName()));
            table.addCell(cell);

        }
        document.add(table);

        document.close();
        baos.writeTo(pOutputStream);
    }

    void emptyLine(Paragraph p, int number) {
        for (int i = 0; i < number; i++) {
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
