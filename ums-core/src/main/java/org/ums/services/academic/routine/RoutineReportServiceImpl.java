package org.ums.services.academic.routine;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ums.domain.model.immutable.*;
import org.ums.domain.model.immutable.routine.Routine;
import org.ums.domain.model.immutable.routine.RoutineConfig;
import org.ums.ems.profilemanagement.additional.AdditionalInformationManager;
import org.ums.enums.CourseType;
import org.ums.enums.ProgramType;
import org.ums.enums.common.EmploymentType;
import org.ums.enums.routine.DayType;
import org.ums.itext.UmsCell;
import org.ums.itext.UmsParagraph;
import org.ums.itext.UmsPdfPageEventHelper;
import org.ums.manager.*;
import org.ums.manager.routine.RoutineConfigManager;
import org.ums.manager.routine.RoutineManager;
import org.ums.persistent.model.routine.PersistentRoutine;
import org.ums.services.academic.routine.helper.RoutineGroup;
import org.ums.services.academic.routine.helper.RoutineReportHelper;
import org.ums.util.ReportUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.OutputStream;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.MINUTES;

/**
 * Created by Monjur-E-Morshed on 20-Sep-18.
 */
@Service
public class RoutineReportServiceImpl implements RoutineReportService {
  @Autowired
  SemesterManager mSemesterManager;
  @Autowired
  ProgramManager mProgramManager;
  @Autowired
  CourseManager mCourseManager;
  @Autowired
  ClassRoomManager mClassRoomManager;
  @Autowired
  RoutineManager mRoutineManager;
  @Autowired
  RoutineConfigManager mRoutineConfigManager;
  @Autowired
  EmployeeManager mEmployeeManager;
  @Autowired
  CourseTeacherManager mCourseTeacherManager;
  @Autowired
  AdditionalInformationManager mAdditionalInformationManager;

  @Override
  public void createTeachersRoutine(String pTeachersId, Integer pSemesterId, OutputStream pOutputStream)
      throws InvalidObjectException, DocumentException, IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();

    Document document = initializeDocument(baos);

    document = createSingleTeacherRoutineReport(pTeachersId, pSemesterId, pOutputStream, document);

    document.close();
    baos.writeTo(pOutputStream);
  }

  private Document createSingleTeacherRoutineReport(String pTeachersId, Integer pSemesterId,
      OutputStream pOutputStream, Document pDocument) throws DocumentException, InvalidObjectException {
    Employee employee = mEmployeeManager.get(pTeachersId);
    Semester semester = mSemesterManager.get(pSemesterId);

    UmsParagraph paragraph =
        new UmsParagraph(employee.getDepartment().getLongName() + ", AUST", FontFactory.getFont(FontFactory.TIMES_BOLD,
            12.0f));
    paragraph.setAlignment(Element.ALIGN_CENTER);
    pDocument.add(paragraph);

    paragraph =
        new UmsParagraph(EmploymentType.get(employee.getEmployeeType()).getLabel() + " Teacher Class Schedule, "
            + semester.getName(), FontFactory.getFont(FontFactory.TIMES, 12f));
    paragraph.setAlignment(Element.ALIGN_CENTER);
    pDocument.add(paragraph);

    paragraph =
        new UmsParagraph(employee.getPersonalInformation().getFullName(), FontFactory.getFont(FontFactory.TIMES_BOLD,
            11.0f));
    paragraph.setAlignment(Element.ALIGN_CENTER);
    pDocument.add(paragraph);

    List<Routine> routineList = mRoutineManager.getRoutineByTeacher(pTeachersId, pSemesterId);
    Map<String, List<CourseTeacher>> courseTeacherMap =
        createCourseTeacherMapWithSectionAndCourseId(pSemesterId, routineList);
    pDocument =
        createRoutineReportChart(pDocument, pSemesterId, ProgramType.UG, routineList, courseTeacherMap, pOutputStream);
    return pDocument;
  }

  @Override
  public void createSemesterWiseRoutine(Integer pSemesterId, Integer pProgramId, Integer pYear, Integer pSemester,
      String pSection, OutputStream pOutputStream) throws InvalidObjectException, DocumentException, IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    Document document = initializeDocument(baos);
    Semester semester = mSemesterManager.get(pSemesterId);
    Program program = mProgramManager.get(pProgramId);
    UmsParagraph paragraph =
        new UmsParagraph(program.getLongName() + ", AUST", FontFactory.getFont(FontFactory.TIMES_BOLD, 12.0f));
    paragraph.setAlignment(Element.ALIGN_CENTER);
    document.add(paragraph);

    paragraph =
        new UmsParagraph("Semester Wise Class Schedule, " + semester.getName(), FontFactory.getFont(FontFactory.TIMES,
            12f));
    paragraph.setAlignment(Element.ALIGN_CENTER);
    document.add(paragraph);

    paragraph =
        new UmsParagraph("Year: " + pYear + ", Semester: " + pSemester + " (Section " + pSection + ")",
            FontFactory.getFont(FontFactory.TIMES_BOLD, 11.0f));
    paragraph.setAlignment(Element.ALIGN_CENTER);
    document.add(paragraph);

    List<Routine> routineList =
        mRoutineManager.getRoutineBySemesterProgramIdYearSemesterAndSection(pSemesterId, pProgramId, pYear, pSemester,
            pSection);

    Map<String, List<CourseTeacher>> courseTeacherMapWithCourseIdAndSection =
        createCourseTeacherMapWithSectionAndCourseId(pProgramId, pSemesterId, pYear, pSemester, pSection);

    document =
        createRoutineReportChart(document, pSemesterId, ProgramType.UG, routineList,
            courseTeacherMapWithCourseIdAndSection, pOutputStream);

    document.close();
    baos.writeTo(pOutputStream);

  }

  @Override
  public void createRoomBasedRoutine(Long pRoomId, Integer pSemesterId, OutputStream pOutputStream)
      throws InvalidObjectException, DocumentException, IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();

    Document document = initializeDocument(baos);

    document = createSingleRoomRoutineReport(pRoomId, pSemesterId, pOutputStream, document);
    document.close();
    baos.writeTo(pOutputStream);
  }

  private Document createSingleRoomRoutineReport(Long pRoomId, Integer pSemesterId, OutputStream pOutputStream,
      Document pDocument) throws DocumentException, InvalidObjectException {
    Semester semester = mSemesterManager.get(pSemesterId);
    ClassRoom classRoom = mClassRoomManager.get(pRoomId);
    UmsParagraph paragraph =
        new UmsParagraph(classRoom.getRoomNo(), FontFactory.getFont(FontFactory.TIMES_BOLD, 12.0f));
    paragraph.setAlignment(Element.ALIGN_CENTER);
    pDocument.add(paragraph);

    paragraph =
        new UmsParagraph("Room Wise Class Schedule, " + semester.getName(), FontFactory.getFont(FontFactory.TIMES, 12f));
    paragraph.setAlignment(Element.ALIGN_CENTER);
    pDocument.add(paragraph);

    List<Routine> routineList =
        mRoutineManager.getRoutineBySemesterAndRoom(pSemesterId, Integer.parseInt(pRoomId.toString()));

    Map<String, List<CourseTeacher>> courseTeacherMap =
        createCourseTeacherMapWIthSectionAndCourseId(pSemesterId, routineList);
    pDocument =
        createRoutineReportChart(pDocument, pSemesterId, ProgramType.UG, routineList, courseTeacherMap, pOutputStream);
    return pDocument;
  }

  @Override
  public void createAllTeachersRoutine(Integer pSemesterId, Integer pProgramId, OutputStream pOutputStream) throws InvalidObjectException, DocumentException, IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();

    Document document = initializeDocument(baos);

    List<CourseTeacher> courseTeacherList = mCourseTeacherManager.getCourseTeacher(pSemesterId, pProgramId);
    Set<String> teacherIdSet = courseTeacherList.stream()
        .map(r-> r.getTeacher().getId())
        .collect(Collectors.toSet());

    List<String> teacherIdList = new ArrayList<>(teacherIdSet);

    int i=0;
    for(String teacherId: teacherIdList){
      if(i>0)
        document.newPage();

      document = createSingleTeacherRoutineReport(teacherId, pSemesterId, pOutputStream, document);

      i+=1;
    }

    document.close();
    baos.writeTo(pOutputStream);
  }

  @Override
  public void createAllRoomBasedRoutine(Integer pSemesterId, Integer pProgramId, OutputStream pOutputStream) throws InvalidObjectException, DocumentException, IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();

    Document document = initializeDocument(baos);

    List<Routine> routineList = mRoutineManager.getRoutineBySemesterAndProgram(pSemesterId, pProgramId);
    Set<Long> roomidSet = routineList
        .stream()
        .map(r-> r.getRoomId())
        .collect(Collectors.toSet());
    List<Long> roomIdList = new ArrayList<>(roomidSet);

    int i=0;

    for(Long roomid: roomIdList){

      if(i>0)
        document.newPage();

      document = createSingleRoomRoutineReport(roomid, pSemesterId, pOutputStream, document);

      i+=1;
    }

    document.close();
    baos.writeTo(pOutputStream);
  }

  @Override
  public void createRoutineTemplate(Integer pSemesterId, ProgramType pProgramType, OutputStream pOutputStream)
      throws Exception {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();

    RoutineConfig routineConfig = mRoutineConfigManager.get(pSemesterId, pProgramType);

    Workbook wb = new HSSFWorkbook();

    Sheet sheet = wb.createSheet("1-1-A");

    Long totalColumn =
        (routineConfig.getStartTime().until(routineConfig.getEndTime(), MINUTES)) / routineConfig.getDuration() + 1;
    LocalTime startTime = routineConfig.getStartTime();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
    Row row = sheet.createRow((short) 0);
    for(int i = 0; i < totalColumn; i++) {
      if(i == 0)
        row.createCell(i).setCellValue("Time/Day");
      else {
        String startTimeStr = formatter.format(startTime);
        startTime = startTime.plusMinutes(routineConfig.getDuration());
        String endTimeStr = formatter.format(startTime);
        row.createCell(i).setCellValue(startTimeStr + " - " + endTimeStr);
      }
    }

    for(int i = routineConfig.getDayFrom().getValue(); i <= routineConfig.getDayTo().getValue(); i++) {
      row = sheet.createRow((short) i);
      row.createCell(0).setCellValue(DayType.get(i).getLabel().toUpperCase());
    }

    wb.write(pOutputStream);
    baos.writeTo(pOutputStream);
  }

  private Document initializeDocument(ByteArrayOutputStream baos) throws DocumentException {
    Document document = new Document();
    document.addTitle("Routine");

    PdfWriter writer = PdfWriter.getInstance(document, baos);
    HeaderAndFooter headerAndFooter = new HeaderAndFooter();
    writer.setPageEvent(headerAndFooter);

    document.setPageSize(PageSize.A4.rotate());
    document.open();
    return document;
  }

  Document createRoutineReportChart(Document pDocument, Integer pSemesterId, ProgramType pProgramType,
      List<Routine> pRoutineList, Map<String, List<CourseTeacher>> pCourseTeacherMap, OutputStream pOutputStream)
      throws InvalidObjectException, DocumentException {

    RoutineConfig routineConfig = mRoutineConfigManager.get(pSemesterId, pProgramType);
    Long totalColumn = (routineConfig.getStartTime().until(routineConfig.getEndTime(), MINUTES))/routineConfig.getDuration() + 1L;
    pDocument.add(new UmsParagraph(" "));
    PdfPTable table = new PdfPTable(totalColumn.intValue());
    table.setLockedWidth(true);
    table.setTotalWidth(820);
    Map<Integer, List<Routine>> dayMapWithRoutine = pRoutineList.stream()
        .collect(Collectors.groupingBy(r->r.getDay()));

    for(int i = 0; i <= routineConfig.getDayTo().getValue(); i++) {
      if(i == 0) {
        table = createRoutineChartHeader(routineConfig, table, totalColumn.intValue());
      }
      else {
        table = createRoutineRows(dayMapWithRoutine.get(i), pCourseTeacherMap, table, DayType.get(i),routineConfig);
      }
    }

    pDocument.add(table);
    return pDocument;
  }

  PdfPTable createRoutineChartHeader(RoutineConfig pRoutineConfig, PdfPTable pTable, int pTotalColumn) {

    LocalTime startTime = pRoutineConfig.getStartTime();
    UmsCell cell = new UmsCell();
    UmsParagraph paragraph = new UmsParagraph();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");

    for(int i = 0; i < pTotalColumn; i++) {
      if(i == 0) {
        paragraph = new UmsParagraph("Time/Day", ReportUtils.mBoldFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        cell = new UmsCell(paragraph);
        pTable.addCell(cell);
      }
      else {
        LocalTime endTime = startTime.plusMinutes(pRoutineConfig.getDuration());
        paragraph =
            new UmsParagraph(formatter.format(startTime) + "-" + formatter.format(endTime), ReportUtils.mBoldFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        cell = new UmsCell(paragraph);
        pTable.addCell(cell);

        startTime = endTime;
      }
    }

    return pTable;
  }

  PdfPTable createRoutineRows(List<Routine> pRoutineList,
      Map<String, List<CourseTeacher>> pCourseTeacherMapWithSectionAndCourseId, PdfPTable pTable, DayType pDayType,
      RoutineConfig pRoutineConfig) {

    UmsCell cell = new UmsCell();
    UmsParagraph paragraph = new UmsParagraph(pDayType.getLabel().substring(0, 3), ReportUtils.mBoldFont);
    paragraph.setAlignment(Element.ALIGN_CENTER);
    cell.addElement(paragraph);
    pTable.addCell(cell);

    if(pRoutineList != null) {
      Map<Integer, RoutineGroup> routineGroupMap = createRoutineGroupMapWithGroupId(pRoutineList);
      Map<LocalTime, RoutineGroup> routineGroupMapWithStartTime = createRoutineGroupMapWithStartTime(routineGroupMap);
      LocalTime startTime = pRoutineConfig.getStartTime();
      while(!startTime.equals(pRoutineConfig.getEndTime()) && !startTime.isAfter(pRoutineConfig.getEndTime())) {
        RoutineReportHelper routineReportHelper =
            createSlot(startTime, routineGroupMapWithStartTime, pRoutineConfig, pCourseTeacherMapWithSectionAndCourseId);
        cell = routineReportHelper.getUmsCell();
        cell.setColspan(routineReportHelper.getAlignment());
        pTable.addCell(cell);
        startTime = routineReportHelper.getStartTime();
      }
    }
    else {
      Long totalColumn =
          pRoutineConfig.getStartTime().until(pRoutineConfig.getEndTime(), MINUTES) / pRoutineConfig.getDuration();
      for(int i = 1; i <= totalColumn.intValue(); i++) {
        cell = new UmsCell(new Paragraph(" "));
        pTable.addCell(cell);
      }
    }

    return pTable;
  }

  private RoutineReportHelper createSlot(LocalTime pStartTime,
      Map<LocalTime, RoutineGroup> pRoutineGroupMapWithStartTime, RoutineConfig pRoutineConfig,
      Map<String, List<CourseTeacher>> pCourseTeacherMap) {
    UmsCell cell = new UmsCell();
    RoutineReportHelper routineReportHelper = new RoutineReportHelper();
    if(pRoutineGroupMapWithStartTime.containsKey(pStartTime)) {
      routineReportHelper =
          createInternalSlotStructure(pRoutineGroupMapWithStartTime.get(pStartTime), pCourseTeacherMap, pRoutineConfig);
    }
    else {
      cell.addElement(new UmsParagraph(" "));
      pStartTime = pStartTime.plusMinutes(pRoutineConfig.getDuration());
      routineReportHelper.setUmsCell(cell);
      routineReportHelper.setStartTime(pStartTime);
    }

    return routineReportHelper;
  }

  RoutineReportHelper createInternalSlotStructure(RoutineGroup pRoutineGroup,
      Map<String, List<CourseTeacher>> courseTeacherMapWithCourseIdAndSection, RoutineConfig pRoutineConfig) {
    Long alignmentValue =
        (pRoutineGroup.getStartTime().until(pRoutineGroup.getEndTime(), MINUTES)) / pRoutineConfig.getDuration();
    PdfPTable table = new PdfPTable(alignmentValue.intValue());
    table.getDefaultCell().setPadding(0);
    UmsCell cell = new UmsCell();
    UmsParagraph paragraph = new UmsParagraph();
    List<Routine> routineList = pRoutineGroup.getRoutineList();

    while(routineList.size() > 0) {
      LocalTime startTime = pRoutineGroup.getStartTime();

      while(!startTime.equals(pRoutineGroup.getEndTime())) {
        if(routineList.size() > 0) {
          Routine routine = routineList.remove(0);
          if(routine.getStartTime().equals(startTime)) {
            paragraph = new UmsParagraph();
            String section=routine.getCourse().getCourseType().equals(CourseType.SESSIONAL)?"("+routine.getSection()+")":"";
            paragraph.add(routine.getCourse().getNo() +section);
            paragraph.setFont(ReportUtils.mLiteMediumFont);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setSpacingBefore(-5);
            cell = new UmsCell();
            cell.addElement(paragraph);
            paragraph = new UmsParagraph(routine.getRoom().getRoomNo(), ReportUtils.mLiteMediumFont);
            paragraph.setFont(ReportUtils.mLiteMediumFont);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setSpacingBefore(-3);
            cell.addElement(paragraph);
            Long cellAlignment =
                routine.getStartTime().until(routine.getEndTime(), MINUTES) / pRoutineConfig.getDuration();
            cell.setColspan(cellAlignment.intValue());

            List<String> courseTeacherNameList = new ArrayList<>();
            if(courseTeacherMapWithCourseIdAndSection.containsKey(routine.getCourseId()+routine.getSection()))
              courseTeacherNameList = courseTeacherMapWithCourseIdAndSection.get(routine.getCourseId()+routine.getSection())
                  .stream().map(c->{
                    String name =  c.getTeacher().getName().trim();
                    String[] nameArr = name.split(" ");
                    return nameArr[nameArr.length-1];
                  }).collect(Collectors.toList());

            paragraph = new UmsParagraph("("+(courseTeacherNameList.isEmpty()?"TBA": StringUtils.join(courseTeacherNameList, ","))+")", ReportUtils.mLiteMediumFont);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setSpacingBefore(-3);
            cell.addElement(paragraph);
            table.addCell(cell);
            startTime = routine.getEndTime();
          }
          else {
            startTime = startTime.plusMinutes(pRoutineConfig.getDuration());
            cell = new UmsCell(new UmsParagraph(""));
            table.addCell(cell);
            if((startTime.equals(pRoutineGroup.getEndTime()) || startTime.isAfter(pRoutineGroup.getEndTime()))
                && routineList.size() == 0)
              break;
          }

        }
        else {
          startTime = startTime.plusMinutes(pRoutineConfig.getDuration());
          cell = new UmsCell(new UmsParagraph(""));
          table.addCell(cell);
          if((startTime.equals(pRoutineGroup.getEndTime()) || startTime.isAfter(pRoutineGroup.getEndTime()))
              && routineList.size() == 0)
            break;
        }

      }
    }

    cell = new UmsCell(table);
    cell.setColspan(alignmentValue.intValue());

    RoutineReportHelper routineReportHelper = new RoutineReportHelper();
    routineReportHelper.setStartTime(pRoutineGroup.getEndTime());
    routineReportHelper.setUmsCell(cell);
    routineReportHelper.setAlignment(alignmentValue.intValue());
    return routineReportHelper;
  }

  private Map<LocalTime, RoutineGroup> createRoutineGroupMapWithStartTime(Map<Integer, RoutineGroup> pRoutineGroupMap) {
    Map<LocalTime, RoutineGroup> routineGroupMap = new HashMap<>();
    for(Map.Entry<Integer, RoutineGroup> entry : pRoutineGroupMap.entrySet()) {
      routineGroupMap.put(entry.getValue().getStartTime(), entry.getValue());
    }

    return routineGroupMap;
  }

  private Map<Integer, RoutineGroup> createRoutineGroupMapWithGroupId(List<Routine> pRoutineList) {
    Map<Integer, RoutineGroup> routineGroupMapWIthGroupId = new HashMap<>();
    for(Routine routine : pRoutineList) {
      if(routineGroupMapWIthGroupId.containsKey(routine.getSlotGroup())) {
        RoutineGroup routineGroup = routineGroupMapWIthGroupId.get(routine.getSlotGroup());
        routineGroup.getRoutineList().add(routine);
        routineGroup.setStartTime(routine.getStartTime().isBefore(routineGroup.getStartTime()) ? routine.getStartTime()
            : routineGroup.getStartTime());
        routineGroup.setEndTime(routine.getEndTime().isAfter(routineGroup.getEndTime()) ? routine.getEndTime()
            : routineGroup.getEndTime());
        routineGroupMapWIthGroupId.put(routine.getSlotGroup(), routineGroup);
      }
      else {
        RoutineGroup routineGroup = new RoutineGroup();
        List<Routine> routineList = new ArrayList<>();
        routineList.add(routine);
        routineGroup.setRoutineList(routineList);
        routineGroup.setStartTime(routine.getStartTime());
        routineGroup.setEndTime(routine.getEndTime());
        routineGroup.setGroupId(routine.getSlotGroup());
        routineGroup.setDayType(DayType.get(routine.getDay()));
        routineGroupMapWIthGroupId.put(routine.getSlotGroup(), routineGroup);
      }
    }
    return routineGroupMapWIthGroupId;
  }

  Map<String, List<CourseTeacher>> createCourseTeacherMapWithSectionAndCourseId(Integer pProgramId,
      Integer pSemesterId, Integer pAcademicYear, Integer pAcademicSemester, String pSection) {
    Map<String, List<CourseTeacher>> courseTeacherMapWithSectionAndCourseId = new HashMap<>();
    List<CourseTeacher> courseTeacherList =
        mCourseTeacherManager.getCourseTeacher(pProgramId, pSemesterId, pSection, pAcademicYear, pAcademicSemester);

    generateCourseTeacherMap(courseTeacherMapWithSectionAndCourseId, courseTeacherList);

    return courseTeacherMapWithSectionAndCourseId;
  }

  Map<String, List<CourseTeacher>> createCourseTeacherMapWithSectionAndCourseId(Integer pSemesterId, List<Routine> pRoutineList) {
    Map<String, List<CourseTeacher>> courseTeacherMapWithSectionAndCourseId = new HashMap<>();
    List<String> courseIdList = pRoutineList
        .stream()
        .map(c->c.getCourse().getId())
        .collect(Collectors.toList());
    Set<String> courseIdSet = new HashSet<>(courseIdList);
    courseIdList = new ArrayList<>(courseIdSet);
    List<CourseTeacher> courseTeacherList = mCourseTeacherManager.getCourseTeacher(pSemesterId, courseIdList);

    generateCourseTeacherMap(courseTeacherMapWithSectionAndCourseId, courseTeacherList);
    return courseTeacherMapWithSectionAndCourseId;
  }

  Map<String, List<CourseTeacher>> createCourseTeacherMapWIthSectionAndCourseId(Integer pSemesterId, List<Routine> pRoutineList){
    Map<String, List<CourseTeacher>> courseTeacherMapWithSectionAndCourseId = new HashMap<>();
    List<String> courseIdList = pRoutineList.stream()
        .map(r->r.getCourse().getId())
        .collect(Collectors.toList());
    List<CourseTeacher> courseTeacherList = mCourseTeacherManager.getCourseTeacher(pSemesterId, courseIdList);
    generateCourseTeacherMap(courseTeacherMapWithSectionAndCourseId, courseTeacherList);

    return courseTeacherMapWithSectionAndCourseId;
  }

  private void generateCourseTeacherMap(Map<String, List<CourseTeacher>> pCourseTeacherMapWithSectionAndCourseId,
      List<CourseTeacher> pCourseTeacherList) {
    for(CourseTeacher courseTeacher : pCourseTeacherList) {
      if(pCourseTeacherMapWithSectionAndCourseId.containsKey(courseTeacher.getCourseId() + courseTeacher.getSection())) {
        List<CourseTeacher> mapCourseTeacherList =
            pCourseTeacherMapWithSectionAndCourseId.get(courseTeacher.getCourseId() + courseTeacher.getSection());
        mapCourseTeacherList.add(courseTeacher);
        pCourseTeacherMapWithSectionAndCourseId.put(courseTeacher.getCourseId() + courseTeacher.getSection(),
            mapCourseTeacherList);
      }
      else {
        List<CourseTeacher> mapCourseTeacherList = new ArrayList<>();
        mapCourseTeacherList.add(courseTeacher);
        pCourseTeacherMapWithSectionAndCourseId.put(courseTeacher.getCourseId() + courseTeacher.getSection(),
            mapCourseTeacherList);
      }
    }
  }

  class HeaderAndFooter extends UmsPdfPageEventHelper {
    @Override
    public void onEndPage(PdfWriter writer, Document document) {
      // do noting on end page
    }
  }

}
