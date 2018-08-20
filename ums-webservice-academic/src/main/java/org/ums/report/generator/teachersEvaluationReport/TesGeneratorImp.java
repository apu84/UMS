package org.ums.report.generator.teachersEvaluationReport;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.ums.academic.resource.teacher.evaluation.system.ApplicationTESResourceHelper;
import org.ums.academic.resource.teacher.evaluation.system.helper.ComparisonReport;
import org.ums.academic.resource.teacher.evaluation.system.helper.QuestionWiseReport;
import org.ums.academic.resource.teacher.evaluation.system.helper.Report;
import org.ums.academic.resource.teacher.evaluation.system.helper.StudentComment;
import org.ums.domain.model.immutable.ApplicationTES;
import org.ums.domain.model.immutable.Department;
import org.ums.ems.profilemanagement.personal.PersonalInformationManager;
import org.ums.enums.FacultyType;
import org.ums.enums.tes.ObservationType;
import org.ums.formatter.DateFormat;
import org.ums.manager.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Rumi on 3/15/2018.
 */
@Service
public class TesGeneratorImp implements TesGenerator {

  @Autowired
  @Qualifier("genericDateFormat")
  private DateFormat mDateFormat;
  @Autowired
  private ApplicationTESManager mApplicationTESManager;
  @Autowired
  private CourseManager mCourseManager;
  @Autowired
  private PersonalInformationManager mPersonalInformationManager;
  @Autowired
  private SemesterManager mSemesterManager;
  @Autowired
  private DepartmentManager mDepartmentManager;
  @Autowired
  private EmployeeManager mEmployeeManager;
  @Autowired
  private ApplicationTESResourceHelper mApplicationTESResourceHelper;
  @Autowired
  ApplicationTesQuestionManager mApplicationTesQuestionManager;
  @Autowired
  UGRegistrationResultManager mUgRegistrationResultManager;
  @Autowired
  CourseTeacherManager mCourseTeacherManager;
  private String courseId;
  private String teacherId;
  private Integer semesterId;

  @Override
  public void createTesReport(String pCourseId, String pTeacherId, Integer pSemesterId, OutputStream pOutputStream)
      throws IOException, DocumentException {
    this.courseId=pCourseId;
    this.teacherId=pTeacherId;
    this.semesterId=pSemesterId;
    DecimalFormat newFormat = new DecimalFormat("#.##");
    Integer studentNo = mApplicationTESManager.getTotalStudentNumber(pTeacherId, pCourseId, pSemesterId);

    String selectedSectionForReview = "", sectionForReview = "";
    Integer selectedRegisteredStudents = 0, registeredStudents = 0;
    double percentage = 0;
    List<ApplicationTES> allSectionList =
            mCourseTeacherManager.getAllSectionForSelectedCourse(pCourseId, pTeacherId, pSemesterId);
    List<String> addSection= new ArrayList<String>();
    List<ApplicationTES> sectionList = mApplicationTESManager.getSectionList(pCourseId, pSemesterId, pTeacherId);
      sectionForReview = mApplicationTESResourceHelper.getDataForSections(sectionForReview, addSection, allSectionList);
    registeredStudents = mUgRegistrationResultManager.getTotalRegisteredStudentForCourse(pCourseId,
                    addSection, pSemesterId);
   addSection.clear();
    try {
      for(int k = 0; k < sectionList.size(); k++) {
        selectedSectionForReview = selectedSectionForReview + sectionList.get(k).getSection() + " ";
        addSection.add(sectionList.get(k).getSection());

      }
      selectedRegisteredStudents =
              mUgRegistrationResultManager.getTotalRegisteredStudentForCourse(pCourseId,addSection,
                      pSemesterId);
      double total = ((double) studentNo / (double) selectedRegisteredStudents);
      percentage = Double.valueOf(newFormat.format((total * 100)));
    } catch(Exception e) {
      e.printStackTrace();
    }




    mDateFormat = new DateFormat("dd MMM YYYY");
    Document document = new Document(PageSize.A4.rotate());
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfWriter writer = PdfWriter.getInstance(document, baos);

    Font fontTimes11Normal = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11);
    Font fontTimes11Bold = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12);
    Font fontTimes14Bold = FontFactory.getFont(FontFactory.TIMES_BOLD, 14);
    Font tt = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
    document.open();
   // document.setPageSize(PageSize.A4.rotate());

    Paragraph paragraph = null;
    Chunk chunk = null;


    chunk = new Chunk("Teachers Evaluation Report\nAhsanullah University of Science And Technology");

    paragraph = new Paragraph();
    paragraph.setAlignment(Element.ALIGN_CENTER);
    paragraph.setFont(fontTimes14Bold);
    paragraph.add(chunk);
    emptyLine(paragraph, 1);
    document.add(paragraph);
  //  String name=mEmployeeManagerManager.get(pTeacherId).getDepartment();
    //
    chunk = new Chunk("Semester: "+mSemesterManager.get(semesterId).getName()+"\n"+"Course Title: "+mCourseManager.get(courseId).getTitle()+" ("+mCourseManager.get(pCourseId).getNo()+")\n"+
    "Teacher Name: "+mPersonalInformationManager.get(teacherId).getName()+"\n"+
            "Number of Student Registered For This Course: "+registeredStudents+"("+sectionForReview+")"+"\n"+
            "Number of Student Eligible For Evaluation: "+selectedRegisteredStudents+"("+selectedSectionForReview+")"+"\n"+
            "Student Reviewed: "+studentNo+" ("+percentage+"%)"+"\n"+
    "Department: "+mEmployeeManager.get(teacherId).getDepartment().getLongName());

    paragraph = new Paragraph();
    paragraph.setAlignment(Element.ALIGN_LEFT);
    paragraph.setFont(fontTimes11Bold);
    paragraph.add(chunk);
    document.add(paragraph);
    //

    chunk = new Chunk("Date: " + mDateFormat.format(new Date()));
    chunk.setFont(fontTimes11Normal);
    paragraph = new Paragraph();
    paragraph.setAlignment(Element.ALIGN_RIGHT);
    paragraph.setFont(fontTimes11Normal);
    paragraph.add(chunk);
    emptyLine(paragraph, 2);
    document.add(paragraph);
    chunk = new Chunk(" ");
    paragraph=new Paragraph(" ");

    //general report

      double classRoomObservation=0,nonClassRoomObservation=0;
      Integer counterForClassRoomObservation=0, counterForNonClassRoomObservation=0;
      double totalPointsObtype1=0, totalStudentsObtype1=0, totalPointsObtype2=0, totalStudentsObtype2=0;
    HashMap<Long,Double> mapForCalculateResult=new HashMap<Long,Double>();
    List<Report> reportList= new ArrayList<Report>();
    List<ApplicationTES> applications=mApplicationTesQuestionManager.getAllQuestions(pSemesterId);
    
     if(studentNo !=0){
       applications.forEach(a->{
         Integer observationType=mApplicationTesQuestionManager.get(a.getQuestionId()).getObservationType();
         if(observationType!=ObservationType.NON_TEACHING_OBSERVATION.getValue()){
           double value=0;
           value=mApplicationTESManager.getAverageScore(pTeacherId,pCourseId,a.getQuestionId(),pSemesterId);
           String questionDetails=mApplicationTesQuestionManager.get(a.getQuestionId()).getQuestionDetails();
           reportList.add(new Report(a.getQuestionId(),questionDetails,value,studentNo,(Double.valueOf(newFormat.format(value/studentNo))),observationType));
           mapForCalculateResult.put(a.getQuestionId(),(value/studentNo));
         }

       });
       for(Map.Entry m:mapForCalculateResult.entrySet()){
         Long questionId=(Long)m.getKey();
         if(mApplicationTesQuestionManager.get(questionId).getObservationType() == ObservationType.CLASSROOM_OBSERVATION.getValue()){
           counterForClassRoomObservation++;
           classRoomObservation=classRoomObservation+(double)m.getValue();
         }else if(mApplicationTesQuestionManager.get(questionId).getObservationType() ==ObservationType.NON_CLASSROOM_OBSERVATION.getValue()){
           counterForNonClassRoomObservation++;
           nonClassRoomObservation=nonClassRoomObservation+(double)m.getValue();
         }else{

         }
       }
       classRoomObservation=Double.valueOf(newFormat.format(classRoomObservation/counterForClassRoomObservation));
       nonClassRoomObservation=Double.valueOf(newFormat.format(nonClassRoomObservation/counterForNonClassRoomObservation));

       for(int i=0;i<reportList.size();i++){
         if(reportList.get(i).getObservationType()==1){
           totalPointsObtype1=totalPointsObtype1+reportList.get(i).getTotalScore();
           totalStudentsObtype1=totalStudentsObtype1+reportList.get(i).getStudentNo();
         }else{
           totalPointsObtype2=totalPointsObtype2+reportList.get(i).getTotalScore();
           totalStudentsObtype2=totalStudentsObtype2+reportList.get(i).getStudentNo();
         }
       }
     }

      //---
    Paragraph paragraph1 = null;
    Chunk chunk1 = null;


    chunk1 = new Chunk("Class Room Observation");

    paragraph1 = new Paragraph();
    paragraph1.setAlignment(Element.ALIGN_LEFT);
    paragraph1.setFont(fontTimes14Bold);
    paragraph1.add(chunk1);
    document.add(paragraph1);
    document.add(paragraph);
    //
    PdfPTable table= new PdfPTable(5);
    table.setWidthPercentage(100);
    table.setWidths(new float[] { 1, 15,3,3,2 });
    table.addCell("SL");
    table.addCell("Questions");
    table.addCell("Total Points");
    table.addCell("Total Students");
    table.addCell("Average");

   // document.add(table);
    int index=0;
    for(int i=0;i<reportList.size();i++){
      if(reportList.get(i).getObservationType()==1){
        index=index+1;
        table.addCell(new Phrase(""+index,tt));
        table.addCell(new Phrase(""+reportList.get(i).getQuestionDetails(),tt));
        table.addCell(new Phrase(""+reportList.get(i).getTotalScore(),tt));
        table.addCell(new Phrase(""+reportList.get(i).getStudentNo(),tt));
        table.addCell(new Phrase(""+reportList.get(i).getAverageScore(),tt));
      }

    }
    try {
      document.add(table);
    } catch (DocumentException e) {
      e.printStackTrace();
    }
    //
    PdfPTable subset= new PdfPTable(5);
    subset.setWidthPercentage(100);
    subset.setWidths(new float[] { 1, 15,3,3,2 });
    subset.addCell("");
    subset.addCell("");
    subset.addCell(""+totalPointsObtype1);
    subset.addCell(""+totalStudentsObtype1);
    subset.addCell(""+classRoomObservation);
    try {
      document.add(subset);
    } catch (DocumentException e) {
      e.printStackTrace();
    }
    //
    document.add(paragraph);

    //
    Paragraph paragraph2 = null;
    Chunk chunk2 = null;


    chunk2 = new Chunk("Non-Class Room Observation");
    paragraph2 = new Paragraph();
    paragraph2.setAlignment(Element.ALIGN_LEFT);
    paragraph2.setFont(fontTimes14Bold);
    paragraph2.add(chunk2);
    document.add(paragraph2);
    paragraph=new Paragraph(" ");
    document.add(paragraph);
    //Non classRoom
    PdfPTable table1= new PdfPTable(5);
    table1.setWidthPercentage(100);
    table1.setWidths(new float[] { 1, 15,3,3,2 });
    table1.addCell("SL");
    table1.addCell("Questions");
    table1.addCell("Total Points");
    table1.addCell("Total Students");
    table1.addCell("Average");
    // document.add(table);

    for(int i=0;i<reportList.size();i++){
      if(reportList.get(i).getObservationType()==2){
        index = setValueInCell(tt, reportList, index, table1, i);
      }

    }
    try {
      document.add(table1);
    } catch (DocumentException e) {
      e.printStackTrace();
    }
    //
    PdfPTable subset1= new PdfPTable(5);
    subset1.setWidthPercentage(100);
    subset1.setWidths(new float[] { 1, 15,3,3,2 });
    subset1.addCell("");
    subset1.addCell("");
    subset1.addCell(""+totalPointsObtype2);
    subset1.addCell(""+totalStudentsObtype2);
    subset1.addCell(""+nonClassRoomObservation);
    try {
      document.add(subset1);
    } catch (DocumentException e) {
      e.printStackTrace();
    }
    //final result
    document.add(paragraph);
    PdfPTable tableGreen= new PdfPTable(5);
    tableGreen.setWidthPercentage(100);
    tableGreen.setWidths(new float[] { 1, 15,3,3,2 });
    PdfPCell cell=new PdfPCell(new Paragraph("   "));
    cell.setColspan(5);
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    cell.setBackgroundColor(BaseColor.GREEN);
    tableGreen.addCell(cell);
    tableGreen.addCell("");
    tableGreen.addCell("");
    tableGreen.addCell(""+(totalPointsObtype1+totalPointsObtype2));
    tableGreen.addCell(""+(totalStudentsObtype1+totalStudentsObtype2));
    tableGreen.addCell(""+Double.valueOf(newFormat.format((classRoomObservation+nonClassRoomObservation)/2)));
    try {
      document.add(tableGreen);
    } catch (DocumentException e) {
      e.printStackTrace();
    }

    //comment Section
    List<ApplicationTES> getDetailedResult=null;
    List<StudentComment> commentList= new ArrayList<StudentComment>();

    getComment(pCourseId, pTeacherId, pSemesterId, applications, commentList);
    //Itext Pdf Comment
    document.add(paragraph);
    Paragraph paragraph3 = null;
    Chunk chunk3 = null;
    chunk3 = new Chunk("Non Teaching Observation");
    paragraph3 = new Paragraph();
    paragraph3.setAlignment(Element.ALIGN_LEFT);
    paragraph3.setFont(fontTimes14Bold);
    paragraph3.add(chunk3);
    document.add(paragraph3);
    paragraph=new Paragraph(" ");
    document.add(paragraph);
    //
    //

    PdfPTable tableQuestions= new PdfPTable(2);
    tableQuestions.setWidthPercentage(100);
    tableQuestions.setWidths(new float[] { 1, 20});
    PdfPCell pdfWordCellRow1 = new PdfPCell();
    PdfPCell pdfWordCellRow2 = new PdfPCell();
    PdfPCell pdfWordCellRow3,pdfWordCellRow4;
    Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
    Font boldFont1 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
    pdfWordCellRow1.addElement(new Phrase("SL",boldFont));
    pdfWordCellRow1.setPaddingLeft(5);
    pdfWordCellRow1.setPaddingBottom(10);
    tableQuestions.addCell(pdfWordCellRow1);
    pdfWordCellRow2.addElement(new Phrase("QUESTIONS",boldFont));
    pdfWordCellRow2.setPaddingLeft(5);
    pdfWordCellRow2.setPaddingBottom(10);
    tableQuestions.addCell(pdfWordCellRow2);
    // document.add(table);
    int questionIndexNumber=0,commentIndexNumber=0;
    for(int i=0;i<commentList.size();i++){
        pdfWordCellRow3=new PdfPCell(new Phrase(""));
        pdfWordCellRow4=new PdfPCell(new Phrase(""));
        questionIndexNumber=questionIndexNumber+1;
        pdfWordCellRow3.addElement(new Phrase(""+questionIndexNumber,boldFont1));
        pdfWordCellRow3.setPaddingLeft(5);
        pdfWordCellRow3.setPaddingBottom(10);
        tableQuestions.addCell(pdfWordCellRow3);
        pdfWordCellRow4.addElement(new Phrase(""+commentList.get(i).getQuestionDetails(),boldFont1));
        pdfWordCellRow4.setPaddingLeft(5);
        pdfWordCellRow4.setPaddingBottom(10);
      tableQuestions.addCell(pdfWordCellRow4);
        tableQuestions.addCell("");
      PdfPTable tableComment = new PdfPTable(2);
      tableComment.setWidths(new float[] { 1,20});
      tableComment.setWidthPercentage(100);
      PdfPCell cellCommentIndex=new PdfPCell();
      cellCommentIndex.setPaddingBottom(10);
      cellCommentIndex.setBorder(0);
      PdfPCell cellComment=new PdfPCell();
      cellComment.setPaddingBottom(10);
      cellComment.setBorder(0);
      String arr[]=commentList.get(i).getComments();
      int j=0;
      for(j=0;j<commentList.get(i).getComments().length;j++){
        Paragraph cellParagraph = new Paragraph(""+(j+1)+". ");
        cellParagraph.setAlignment(Element.ALIGN_RIGHT);
        cellCommentIndex.addElement(cellParagraph);
        cellComment.addElement(new Phrase("" + arr[j]));
      }
      tableComment.addCell(cellCommentIndex);
      tableComment.addCell(cellComment);
      tableQuestions.addCell(tableComment);
      }
    try {
      document.add(tableQuestions);
    } catch (DocumentException e) {
      e.printStackTrace();
    }


    //
    index=0;
    document.close();
    baos.writeTo(pOutputStream);
  }

  private int setValueInCell(Font tt, List<Report> reportList, int index, PdfPTable table1, int i) {
    index = index + 1;
    table1.addCell(new Phrase("" + index, tt));
    table1.addCell(new Phrase("" + reportList.get(i).getQuestionDetails(), tt));
    table1.addCell(new Phrase("" + reportList.get(i).getTotalScore(), tt));
    table1.addCell(new Phrase("" + reportList.get(i).getStudentNo(), tt));
    table1.addCell(new Phrase("" + reportList.get(i).getAverageScore(), tt));
    return index;
  }

  private void getComment(String pCourseId, String pTeacherId, Integer pSemesterId, List<ApplicationTES> applications, List<StudentComment> commentList) {
    List<ApplicationTES> getDetailedResult;
    for(int i = 0; i<applications.size(); i++){
      Long questionId=applications.get(i).getQuestionId();
      Integer observationType=mApplicationTesQuestionManager.get(questionId).getObservationType();
      if(observationType ==ObservationType.NON_TEACHING_OBSERVATION.getValue()){
        String questionDetails=mApplicationTesQuestionManager.get(questionId).getQuestionDetails();
        getDetailedResult=mApplicationTESManager.getDetailedResult(pTeacherId,pCourseId,pSemesterId).
                stream().
                filter(a->a.getComment() !=null && a.getQuestionId().equals(questionId)).collect(Collectors.toList());
        int size=getDetailedResult.size();
        mApplicationTESResourceHelper.setCommentToList(getDetailedResult,commentList,questionId,observationType,questionDetails,size);

      }
    }
  }

  void emptyLine(Paragraph p, int number) {
    for(int i = 0; i < number; i++) {
      p.add(new Paragraph(" "));
    }
  }

  void getObservationType(int obType) {

  }

  @Override
  public void createTesReportSuperAdmin(String pDeptId, Integer pSemesterId, OutputStream pOutputStream)
      throws IOException, DocumentException {
    //
    mDateFormat = new DateFormat("dd MMM YYYY");
    Document document = new Document(PageSize.A4.rotate());
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfWriter writer = PdfWriter.getInstance(document, baos);

    Font fontTimes11Normal = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11);
    Font fontTimes11Bold = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12);
    Font fontTimes14Bold = FontFactory.getFont(FontFactory.TIMES_BOLD, 14);
    document.open();


    Paragraph paragraph = null;
    Chunk chunk = null;


    chunk = new Chunk("Teachers Evaluation Report\nAhsanullah University of Science And Technology");

    paragraph = new Paragraph();
    paragraph.setAlignment(Element.ALIGN_CENTER);
    paragraph.setFont(fontTimes14Bold);
    paragraph.add(chunk);
    emptyLine(paragraph, 1);
    document.add(paragraph);
    paragraph=new Paragraph(" ");
    document.add(paragraph);


    //
    final String all="08",maximum = "09", minimum = "10", engineering = "11", businessAndSocial = "12", architecture = "13";
    Integer facultyId = 0;
    String departmentName="";
    if(pDeptId.equals(all)){
      departmentName="All Department Teachers List";
    }else if(pDeptId.equals(maximum)){
      departmentName="Maximum Score Holders of All Departments";
    }else if(pDeptId.equals(minimum)) {
      departmentName="Minimum Score Holders of All Departments";
    }else if(pDeptId.equals(engineering)) {
      departmentName=FacultyType.Engineering.getLabel();;
    }else if(pDeptId.equals(businessAndSocial)) {
      departmentName=FacultyType.Business.getLabel();
    }else if(pDeptId.equals(architecture)) {
      departmentName=FacultyType.Architecture.getLabel();
    }else{
      departmentName=mDepartmentManager.get(pDeptId).getLongName();
    }
    chunk = new Chunk("Semester: "+mSemesterManager.get(pSemesterId).getName()+"\n"+
            "Department: "+departmentName);

    paragraph = new Paragraph();
    paragraph.setAlignment(Element.ALIGN_LEFT);
    paragraph.setFont(fontTimes11Bold);
    paragraph.add(chunk);
    document.add(paragraph);

    chunk = new Chunk("Date: " + mDateFormat.format(new Date()));
    chunk.setFont(fontTimes11Normal);
    paragraph = new Paragraph();
    paragraph.setAlignment(Element.ALIGN_RIGHT);
    paragraph.setFont(fontTimes11Normal);
    paragraph.add(chunk);
    emptyLine(paragraph, 2);
    document.add(paragraph);
    chunk = new Chunk(" ");
    paragraph=new Paragraph(" ");
    //
    facultyId= mApplicationTESResourceHelper.getFacultyType(pDeptId,engineering,businessAndSocial,architecture);

    List<ApplicationTES> applications = mApplicationTESManager.getFacultyListForReport(pDeptId, pSemesterId);
    List<ApplicationTES> parameters = null;
    List<Department> getDeptList = mDepartmentManager.getAll();
    List<ComparisonReport> report = new ArrayList<ComparisonReport>();
    List<ComparisonReport> reportMaxMin = new ArrayList<ComparisonReport>();
    List<ComparisonReport> reportFaculty = new ArrayList<ComparisonReport>();
    List<ComparisonReport> pdfReport = new ArrayList<ComparisonReport>();
    DecimalFormat newFormat = new DecimalFormat("#.##");
    for(int i = 0; i < applications.size(); i++) {
      parameters = mApplicationTESManager.getParametersForReport(applications.get(i).getTeacherId(), pSemesterId);
      for(int j = 0; j < parameters.size(); j++) {
        double score = 0;
        Integer studentNo =
                mApplicationTESManager.getTotalStudentNumber(parameters.get(j).getTeacherId(),
                        parameters.get(j).getReviewEligibleCourseId(), pSemesterId);
        List<ApplicationTES> app = mApplicationTesQuestionManager.getAllQuestions(pSemesterId);
        if(studentNo != 0) {
          score =mApplicationTESResourceHelper.
                  getScore(parameters.get(j).getTeacherId(), parameters.get(j).getReviewEligibleCourseId(), pSemesterId,
                          studentNo, app);
        }
        String teacherName, deptName, courseNo, courseTitle, programName = "";
        Integer registeredStudents=0;double percentage=0;
        teacherName = mPersonalInformationManager.get(parameters.get(j).getTeacherId()).getName();
        deptName = mEmployeeManager.get(parameters.get(j).getTeacherId()).getDepartment().getShortName();
        courseNo = mCourseManager.get(parameters.get(j).getReviewEligibleCourseId()).getNo();
        courseTitle = mCourseManager.get(parameters.get(j).getReviewEligibleCourseId()).getTitle();
        List<ApplicationTES> sectionList=mApplicationTESManager.getSectionList(parameters.get(j).getReviewEligibleCourseId(), pSemesterId,parameters.get(j).getTeacherId());
        List<String> addSection = new ArrayList<String>();

        try {
          programName = mApplicationTESManager.getCourseDepartmentMap(parameters.get(j).getReviewEligibleCourseId(), pSemesterId);
          for(int k=0;k<sectionList.size();k++){
           addSection.add(sectionList.get(k).getSection());
          }
          registeredStudents=mUgRegistrationResultManager.getTotalRegisteredStudentForCourse
                  (parameters.get(j).getReviewEligibleCourseId(),addSection, pSemesterId);
          double total=((double)studentNo/(double)registeredStudents);
          percentage=Double.valueOf(newFormat.format((total*100)));
        } catch(Exception e) {
          e.printStackTrace();
        }
        if(programName.equals(null) || programName.equals("")) {
          programName = "Not Found";
        }
        report.add(new ComparisonReport(teacherName, deptName, courseNo, courseTitle, score, percentage, programName,
                parameters.get(j).getTeacherId(), parameters.get(j).getReviewEligibleCourseId(), parameters.get(j)
                .getDeptId()));
      }

    }

    if(pDeptId.equals(maximum)){
      for(int k=0;k<getDeptList.size();k++){
        double max=-1;
        String dp=getDeptList.get(k).getId();
        List<ComparisonReport> list=report.stream().filter(a->a.getDeptId().equals(dp)).collect(Collectors.toList());
        for(int l=0;l<list.size();l++){
          if(list.get(l).getDeptId().equals(getDeptList.get(k).getId())){
            if(list.get(l).getTotalScore()> max){
              max=list.get(l).getTotalScore();
            }

          }
        }
        double finalMax = max;
        list=list.stream().filter(a->a.getTotalScore()== finalMax).collect(Collectors.toList());
        for(int l=0;l<list.size();l++){
          reportMaxMin.add(new ComparisonReport(list.get(l).getTeacherName(), list.get(l).getDeptName(),
                  list.get(l).getCourseNo(), list.get(l).getCourseTitle(), list.get(l).getTotalScore()==-1? 0:max,
                  list.get(l).getReviewPercentage(), list.get(l).getProgramName(),
                  list.get(l).getTeacherId(), list.get(l).getCourseId(), list.get(l).getDeptId()));
        }

      }
     pdfReport=reportMaxMin;
    }else if(pDeptId.equals(minimum)){
      for(int k=0;k<getDeptList.size();k++){
        double min=10;
        String dp=getDeptList.get(k).getId();
        List<ComparisonReport> list=report.stream().filter(a->a.getDeptId().equals(dp)).collect(Collectors.toList());
        for(int l=0;l<list.size();l++){
          if(list.get(l).getDeptId().equals(getDeptList.get(k).getId())){
            if(list.get(l).getTotalScore()< min){
              min=list.get(l).getTotalScore();
            }

          }
        }
        double finalMin = min;
        list=list.stream().filter(a->a.getTotalScore()== finalMin).collect(Collectors.toList());
        for(int l=0;l<list.size();l++){
          reportMaxMin.add(new ComparisonReport(list.get(l).getTeacherName(), list.get(l).getDeptName(),
                  list.get(l).getCourseNo(), list.get(l).getCourseTitle(), list.get(l).getTotalScore()==10? 0:min,
                  list.get(l).getReviewPercentage(), list.get(l).getProgramName(),
                  list.get(l).getTeacherId(), list.get(l).getCourseId(), list.get(l).getDeptId()));
        }

      }
    pdfReport=reportMaxMin;
    }else if(pDeptId.equals(engineering) || pDeptId.equals(businessAndSocial) || pDeptId.equals(architecture)) {
      List<ApplicationTES> facultyWiseDeptList=mApplicationTESManager.getDeptListByFacultyId(facultyId);
      List<ComparisonReport> tempList=null;
      reportFaculty=mApplicationTESResourceHelper.mergeList(report, reportFaculty, facultyWiseDeptList);
      pdfReport=reportFaculty;
    }else{
     pdfReport=report;
    }
    pdfReport.sort(Comparator.comparing((ComparisonReport::getTotalScore)).reversed());
    PdfPTable tableQuestions= new PdfPTable(8);
    tableQuestions.setWidthPercentage(100);
    tableQuestions.setWidths(new float[] { 1,5,2,2,6,2,2,2});
    PdfPCell pdfWordCellColumn1 = new PdfPCell();
    PdfPCell pdfWordCellColumn2 = new PdfPCell();
    PdfPCell pdfWordCellColumn3 = new PdfPCell();
    PdfPCell pdfWordCellColumn4 = new PdfPCell();
    PdfPCell pdfWordCellColumn5 = new PdfPCell();
    PdfPCell pdfWordCellColumn6 = new PdfPCell();
    PdfPCell pdfWordCellColumn7 = new PdfPCell();
    PdfPCell pdfWordCellColumn8 = new PdfPCell();

    Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
    Font boldFont1 = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
    pdfWordCellColumn1.addElement(new Phrase("SL",boldFont));
    pdfWordCellColumn1.setPaddingLeft(5);
    pdfWordCellColumn1.setPaddingBottom(5);
    tableQuestions.addCell(pdfWordCellColumn1);
    pdfWordCellColumn2.addElement(new Phrase("Teacher Name",boldFont));
    pdfWordCellColumn2.setPaddingLeft(5);
    pdfWordCellColumn2.setPaddingBottom(5);
    tableQuestions.addCell(pdfWordCellColumn2);
    pdfWordCellColumn3.addElement(new Phrase("Department",boldFont));
    pdfWordCellColumn3.setPaddingLeft(5);
    pdfWordCellColumn3.setPaddingBottom(5);
    tableQuestions.addCell(pdfWordCellColumn3);
    pdfWordCellColumn4.addElement(new Phrase("Course No",boldFont));
    pdfWordCellColumn4.setPaddingLeft(5);
    pdfWordCellColumn4.setPaddingBottom(5);
    tableQuestions.addCell(pdfWordCellColumn4);
    pdfWordCellColumn5.addElement(new Phrase("Course Title",boldFont));
    pdfWordCellColumn5.setPaddingLeft(5);
    pdfWordCellColumn5.setPaddingBottom(5);
    tableQuestions.addCell(pdfWordCellColumn5);
    pdfWordCellColumn6.addElement(new Phrase("Program Name",boldFont));
    pdfWordCellColumn6.setPaddingLeft(5);
    pdfWordCellColumn6.setPaddingBottom(5);
    tableQuestions.addCell(pdfWordCellColumn6);
    pdfWordCellColumn7.addElement(new Phrase("Score",boldFont));
    pdfWordCellColumn7.setPaddingLeft(5);
    pdfWordCellColumn7.setPaddingBottom(5);
    tableQuestions.addCell(pdfWordCellColumn7);
    pdfWordCellColumn8.addElement(new Phrase("Reviewer",boldFont));
    pdfWordCellColumn8.setPaddingLeft(5);
    pdfWordCellColumn8.setPaddingBottom(5);
    tableQuestions.addCell(pdfWordCellColumn8);
    tableQuestions.setHeaderRows(1);
    PdfPCell pdfWordCellRow1 = new PdfPCell();
    PdfPCell pdfWordCellRow2 = new PdfPCell();
    PdfPCell pdfWordCellRow3 = new PdfPCell();
    PdfPCell pdfWordCellRow4 = new PdfPCell();
    PdfPCell pdfWordCellRow5 = new PdfPCell();
    PdfPCell pdfWordCellRow6 = new PdfPCell();
    PdfPCell pdfWordCellRow7 = new PdfPCell();
    PdfPCell pdfWordCellRow8 = new PdfPCell();

    int index=0;
    for(int n=0;n<pdfReport.size();n++){
      index++;
      tableQuestions.addCell(new Phrase(""+index,boldFont1));
      tableQuestions.addCell(new Phrase(""+pdfReport.get(n).getTeacherName(),boldFont1));
      tableQuestions.addCell(new Phrase(""+pdfReport.get(n).getDeptName(),boldFont1));
      tableQuestions.addCell(new Phrase(""+pdfReport.get(n).getCourseNo(),boldFont1));
      tableQuestions.addCell(new Phrase(""+pdfReport.get(n).getCourseTitle(),boldFont1));
      tableQuestions.addCell(new Phrase(""+pdfReport.get(n).getProgramName(),boldFont1));
      tableQuestions.addCell(new Phrase(""+pdfReport.get(n).getTotalScore(),boldFont1));
      tableQuestions.addCell(new Phrase(""+pdfReport.get(n).getReviewPercentage()+"%",boldFont1));
    }

    try {
      document.add(tableQuestions);
    } catch (DocumentException e) {
      e.printStackTrace();
    }
    
    document.close();
    baos.writeTo(pOutputStream);

  }

  @Override
  public void getQuestionWiseReports(String pDeptId, Integer pYear, Integer pSemester, Integer pSemesterId, Long pQuestionId,OutputStream pOutputStream) throws IOException, DocumentException {
    mDateFormat = new DateFormat("dd MMM YYYY");
    Document document = new Document(PageSize.A4.rotate());
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfWriter writer = PdfWriter.getInstance(document, baos);

    Font fontTimes11Normal = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11);
    Font fontTimes11Bold = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12);
    Font fontTimes14Bold = FontFactory.getFont(FontFactory.TIMES_BOLD, 14);
    document.open();


    Paragraph paragraph = null;
    Chunk chunk = null;


    chunk = new Chunk("Teachers Evaluation Report\nAhsanullah University of Science And Technology");

    paragraph = new Paragraph();
    paragraph.setAlignment(Element.ALIGN_CENTER);
    paragraph.setFont(fontTimes14Bold);
    paragraph.add(chunk);
    emptyLine(paragraph, 1);
    document.add(paragraph);
    paragraph=new Paragraph(" ");
    document.add(paragraph);
    chunk = new Chunk("Semester: "+mSemesterManager.get(pSemesterId).getName()+"\n"+
            "Year-Semester: "+pYear+"-"+pSemester+"\n"+
            "Department: "+mDepartmentManager.get(pDeptId).getLongName()+"\n"+
            "Question Details:  "+ mApplicationTesQuestionManager.get(pQuestionId).getQuestionDetails());

    paragraph = new Paragraph();
    paragraph.setAlignment(Element.ALIGN_LEFT);
    paragraph.setFont(fontTimes11Bold);
    paragraph.add(chunk);
    document.add(paragraph);

    chunk = new Chunk("Date: " + mDateFormat.format(new Date()));
    chunk.setFont(fontTimes11Normal);
    paragraph = new Paragraph();
    paragraph.setAlignment(Element.ALIGN_RIGHT);
    paragraph.setFont(fontTimes11Normal);
    paragraph.add(chunk);
    emptyLine(paragraph, 2);
    document.add(paragraph);
    chunk = new Chunk(" ");
    paragraph=new Paragraph(" ");
    DecimalFormat newFormat = new DecimalFormat("#.##");
    List<QuestionWiseReport> reportList = mApplicationTESResourceHelper.getQuestionWiseReports(pDeptId, pYear, pSemester, pSemesterId, pQuestionId, newFormat);
    reportList.sort(Comparator.comparing((QuestionWiseReport::getScore)).reversed());
    PdfPTable tableQuestions= new PdfPTable(7);
    tableQuestions.setWidthPercentage(100);
    tableQuestions.setWidths(new float[] { 1,7,3,7,4,3,3});
    PdfPCell pdfWordCellColumn1 = new PdfPCell();
    PdfPCell pdfWordCellColumn2 = new PdfPCell();
    PdfPCell pdfWordCellColumn4 = new PdfPCell();
    PdfPCell pdfWordCellColumn5 = new PdfPCell();
    PdfPCell pdfWordCellColumn6 = new PdfPCell();
    PdfPCell pdfWordCellColumn7 = new PdfPCell();
    PdfPCell pdfWordCellColumn8 = new PdfPCell();

    Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
    Font boldFont1 = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
    pdfWordCellColumn1.addElement(new Phrase("SL",boldFont));
    pdfWordCellColumn1.setPaddingLeft(5);
    pdfWordCellColumn1.setPaddingBottom(5);
    tableQuestions.addCell(pdfWordCellColumn1);
    pdfWordCellColumn2.addElement(new Phrase("Teacher Name",boldFont));
    pdfWordCellColumn2.setPaddingLeft(5);
    pdfWordCellColumn2.setPaddingBottom(5);
    tableQuestions.addCell(pdfWordCellColumn2);
    pdfWordCellColumn4.addElement(new Phrase("Course No",boldFont));
    pdfWordCellColumn4.setPaddingLeft(5);
    pdfWordCellColumn4.setPaddingBottom(5);
    tableQuestions.addCell(pdfWordCellColumn4);
    pdfWordCellColumn5.addElement(new Phrase("Course Title",boldFont));
    pdfWordCellColumn5.setPaddingLeft(5);
    pdfWordCellColumn5.setPaddingBottom(5);
    tableQuestions.addCell(pdfWordCellColumn5);
    pdfWordCellColumn6.addElement(new Phrase("Program Name",boldFont));
    pdfWordCellColumn6.setPaddingLeft(5);
    pdfWordCellColumn6.setPaddingBottom(5);
    tableQuestions.addCell(pdfWordCellColumn6);
    pdfWordCellColumn7.addElement(new Phrase("Score",boldFont));
    pdfWordCellColumn7.setPaddingLeft(5);
    pdfWordCellColumn7.setPaddingBottom(5);
    tableQuestions.addCell(pdfWordCellColumn7);
    pdfWordCellColumn8.addElement(new Phrase("Reviewer",boldFont));
    pdfWordCellColumn8.setPaddingLeft(5);
    pdfWordCellColumn8.setPaddingBottom(5);
    tableQuestions.addCell(pdfWordCellColumn8);
    tableQuestions.setHeaderRows(1);
    int index=0;
    for(int n=0;n<reportList.size();n++){
      index++;
      tableQuestions.addCell(new Phrase(""+index,boldFont1));
      tableQuestions.addCell(new Phrase(""+reportList.get(n).getTeacherName(),boldFont1));
      tableQuestions.addCell(new Phrase(""+reportList.get(n).getCourseNo(),boldFont1));
      tableQuestions.addCell(new Phrase(""+reportList.get(n).getCourseTitle(),boldFont1));
      tableQuestions.addCell(new Phrase(""+reportList.get(n).getProgramName(),boldFont1));
      tableQuestions.addCell(new Phrase(""+reportList.get(n).getScore(),boldFont1));
      tableQuestions.addCell(new Phrase(""+reportList.get(n).getPercentage()+"%",boldFont1));
    }

    try {
      document.add(tableQuestions);
    } catch (DocumentException e) {
      e.printStackTrace();
    }

    document.close();
    baos.writeTo(pOutputStream);


  }
  // /pdf generation

}
