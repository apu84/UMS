package org.ums.common.report.generator;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.commons.lang.UnhandledException;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ums.domain.model.immutable.*;
import org.ums.manager.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by My Pc on 30-Aug-16.
 */
@Service
public class ClassRoutineGeneratorImpl implements ClassRoutineGenerator {

  @Autowired
  TeacherManager mTeacherManager;

  @Autowired
  CourseManager mCourseManager;

  @Autowired
  ClassRoomManager mClassRoomManager;

  @Autowired
  SemesterManager mSemesterManager;

  @Autowired
  DepartmentManager mDepartmentManager;

  @Autowired
  ProgramManager mProgramManager;

  @Autowired
  RoutineManager mRoutineManager;

  @Autowired
  AppSettingManager mAppSettingManager;

  @Autowired
  UserManager mUserManager;

  @Autowired
  EmployeeManager mEmployeeManager;

  enum ClassRoutineType {
    teacherAndStudentRoutine,
    roomBasedRoutine
  }

  @Override
  public void createClassRoutineStudentReport(OutputStream pOutputStream) throws Exception,
      IOException, DocumentException {

  }

  @Override
  public void createRoomBasedClassRoutineReport(OutputStream pOutputStream, int pSemesterId, int pRoomId) throws Exception, IOException, DocumentException {
    String userId = SecurityUtils.getSubject().getPrincipal().toString();
    User user = mUserManager.get(userId);
    Employee employee = mEmployeeManager.getByEmployeeId(user.getEmployeeId());
    String deptId = employee.getDepartment().getId();
    Department department = mDepartmentManager.get(deptId);

    Semester semester = mSemesterManager.get(pSemesterId);

    List<Program> programs  = mProgramManager.getAll().stream().filter(p->p.getDepartmentId().equals(deptId)).collect(Collectors.toList());

    Map<Integer,List<Routine>> roomidWithRoutineMap = new HashMap<>();

    if(pRoomId==9999){
      roomidWithRoutineMap = mRoutineManager.getRoutine(pSemesterId,programs.get(0).getId())
          .stream()
          .collect(Collectors.groupingBy(Routine::getRoomId));
    }else{
      roomidWithRoutineMap = mRoutineManager.getRoutine(pSemesterId,programs.get(0).getId())
          .stream()
          .filter(r->r.getRoomId().equals(pRoomId))
          .collect(Collectors.groupingBy(Routine::getRoomId));
    }

    Map<Integer,String> roomIdWithRoomNoMap = mClassRoomManager.getRoomsBasedOnRoutine(pSemesterId,programs.get(0).getId())
        .stream()
        .collect(Collectors.toMap(r->r.getId(),r->r.getRoomNo()));



    Document document = new Document();
    document.addTitle("Teacher's Routine");

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfWriter writer = PdfWriter.getInstance(document,baos);

    Font tableDataFont = new Font(Font.FontFamily.TIMES_ROMAN,7);
    Font tableDataFontTime = new Font(Font.FontFamily.TIMES_ROMAN,11);

    Font universityNameFont = new Font(FontFactory.getFont(FontFactory.TIMES_BOLD,20));
    Font headingFont = new Font(Font.FontFamily.TIMES_ROMAN,13);
    document.open();
    document.setPageSize(PageSize.A4.rotate());


    for(Map.Entry<Integer,List<Routine>>  entry : roomidWithRoutineMap.entrySet()){
      document.newPage();

      Paragraph paragraph = new Paragraph("Ahsanullah University of Science and Technology",universityNameFont);
      paragraph.setAlignment(Element.ALIGN_CENTER);

      document.add(paragraph);

      paragraph = new Paragraph(department.getLongName().toUpperCase(),headingFont);
      paragraph.setAlignment(Element.ALIGN_CENTER);
      document.add(paragraph);

      paragraph = new Paragraph("Class Schedule ("+semester.getName()+")",headingFont);
      paragraph.setAlignment(Element.ALIGN_CENTER);

      document.add(paragraph);

      paragraph = new Paragraph("ROOM NUMBER: "+ roomIdWithRoomNoMap.get(entry.getKey()).toUpperCase(),headingFont);
      paragraph.setAlignment(Element.ALIGN_CENTER);

      document.add(paragraph);
      document.add(new Paragraph(" "));

      if(entry.getValue().size()!=0){
        document = routineGenerator(document,entry.getValue(),ClassRoutineType.roomBasedRoutine);
      }
      else{
        Paragraph noRoutineParagraph= new Paragraph("No Routine Published Yet!");
        noRoutineParagraph.setAlignment(Element.ALIGN_CENTER);
        document.add(noRoutineParagraph);
      }
    }



    document.close();
    baos.writeTo(pOutputStream);
  }

  @Override
  public void createClassRoutineTeacherReport(OutputStream pOutputStream) throws Exception, IOException, DocumentException {
    String teacherId= SecurityUtils.getSubject().getPrincipal().toString();
    User user = mUserManager.get(teacherId);
    Teacher teacher = mTeacherManager.get(user.getEmployeeId());

    List<Routine> routines = mRoutineManager.getTeacherRoutine(teacher.getId())
        .stream()
        .sorted(Comparator.comparing(Routine::getDay)
            .thenComparing( r->r.getStartTime().substring(Math.max(r.getStartTime().length()-2,0)))
            .thenComparing(Routine::getStartTime)
            .thenComparing(Routine::getSection))
        .collect(Collectors.toList());

    Department department = mDepartmentManager.get(teacher.getDepartment().getId());

    List<Semester> semesterList = mSemesterManager.getAll().stream()
                                                    .filter(pSemester -> {
                                                      try{
                                                        return pSemester.getStatus().getValue()==1;
                                                      }catch(Exception e){
                                                        throw new UnhandledException(e);
                                                      }
                                                    }).collect(Collectors.toList());

    Semester semester = semesterList.get(0);




    Document document = new Document();
    document.addTitle("Teacher's Routine");

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfWriter writer = PdfWriter.getInstance(document,baos);

    Font tableDataFont = new Font(Font.FontFamily.TIMES_ROMAN,7);
    Font tableDataFontTime = new Font(Font.FontFamily.TIMES_ROMAN,11);

    Font universityNameFont = new Font(FontFactory.getFont(FontFactory.TIMES_BOLD,20));
    Font headingFont = new Font(Font.FontFamily.TIMES_ROMAN,13);
    document.open();
    document.setPageSize(PageSize.A4.rotate());

    document.newPage();

    Paragraph paragraph = new Paragraph("Ahsanullah University of Science and Technology",universityNameFont);
    paragraph.setAlignment(Element.ALIGN_CENTER);

    document.add(paragraph);

    paragraph = new Paragraph(department.getLongName().toUpperCase(),headingFont);
    paragraph.setAlignment(Element.ALIGN_CENTER);
    document.add(paragraph);

    paragraph = new Paragraph("Class Schedule ("+semester.getName()+")",headingFont);
    paragraph.setAlignment(Element.ALIGN_CENTER);

    document.add(paragraph);

    paragraph = new Paragraph("TEACHER'S NAME: "+ teacher.getName().toUpperCase(),headingFont);
    paragraph.setAlignment(Element.ALIGN_CENTER);

    document.add(paragraph);
    document.add(new Paragraph(" "));

    if(routines.size()!=0){
      document = routineGenerator(document,routines,ClassRoutineType.teacherAndStudentRoutine);
    }
    else{
      Paragraph noRoutineParagraph= new Paragraph("No Routine Published Yet!");
      noRoutineParagraph.setAlignment(Element.ALIGN_CENTER);
      document.add(noRoutineParagraph);
    }


    document.close();
    baos.writeTo(pOutputStream);

  }

  Document routineGenerator( Document document, List<Routine> routines,ClassRoutineType type) throws Exception,
      IOException, DocumentException {

    Font tableDataFont = new Font(Font.FontFamily.TIMES_ROMAN,7);
    Font tableDataFontTime = new Font(Font.FontFamily.TIMES_ROMAN,11);

    Map<Integer,List<ClassRoom>> roomsMap = mClassRoomManager.getAll()
        .stream()
        .collect(Collectors.groupingBy(ClassRoom::getId,Collectors.toList()));

    List<Semester> semesterList = mSemesterManager.getAll().stream()
        .filter(pSemester -> {
          try{
            return pSemester.getStatus().getValue()==1;
          }catch(Exception e){
            throw new UnhandledException(e);
          }
        }).collect(Collectors.toList());
    Map<String,Course> courseMapWithCourseId = new HashMap<>();
    if(type == ClassRoutineType.roomBasedRoutine){
      String userId = SecurityUtils.getSubject().getPrincipal().toString();
      User user = mUserManager.get(userId);
      Employee employee = mEmployeeManager.getByEmployeeId(user.getEmployeeId());
      String deptId = employee.getDepartment().getId();

      List<Program> programs  = mProgramManager.getAll().stream().filter(p->p.getDepartmentId().equals(deptId)).collect(Collectors.toList());

      courseMapWithCourseId = mCourseManager.getBySemesterProgram(semesterList.get(0).getId().toString(), programs.get(0).getId().toString()).stream()
          .collect(Collectors.toMap(c->c.getId(),c->c));
    }

    Semester semester = semesterList.get(0);

    Map<Integer,String> dateMap = new HashMap<>();
    dateMap.put(1,"SATURDAY");
    dateMap.put(2,"SUNDAY");
    dateMap.put(3,"MONDAY");
    dateMap.put(4,"TUESDAY");
    dateMap.put(5,"WEDNESDAY");
    dateMap.put(6,"THURSDAY");


    //AppSetting appSetting = mAppSettingManager.getAll();

    Map<String,String> appSettingMap = mAppSettingManager.getAll().stream()
        .collect(Collectors.toMap(AppSetting::getParameterName,AppSetting::getParameterValue));


    SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
    Time startTime = new Time(timeFormat.parse(appSettingMap.get("class start time")).getTime());
    Time endTime =new Time(timeFormat.parse(appSettingMap.get("class end time")).getTime());
    int classDuration = Integer.parseInt(appSettingMap.get("class duration"));


    PdfPTable table = new PdfPTable(13);
    table.setWidthPercentage(105);
    PdfPCell dayTimeCell = new PdfPCell(new Paragraph("Time  /   Day",tableDataFontTime));
    table.addCell(dayTimeCell);

    Time timeInitializer = new Time(timeFormat.parse(appSettingMap.get("class start time")).getTime());

    while(true){
      if(timeInitializer.equals(endTime)){
        break;
      }else{
        DateFormat startTimeFormat = new SimpleDateFormat();
        Time tempTime =  new Time(timeInitializer.getTime()) ;
        tempTime.setTime(tempTime.getTime()+classDuration*60000);
        final SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
        final Date startTimeFormated = sdf.parse(timeInitializer.toString());
        final Date endTimeFormated = sdf.parse(tempTime.toString());
        PdfPCell cell = new PdfPCell(new Paragraph(  new SimpleDateFormat("hh:mm a").format(startTimeFormated)+"-"+new SimpleDateFormat("hh:mm a").format(endTimeFormated),tableDataFontTime));
        timeInitializer.setTime(timeInitializer.getTime()+classDuration * 60000);
        //timeInitializer = timeInitializer
        table.addCell(cell);
      }
    }

    SimpleDateFormat routineTimeFormat= new SimpleDateFormat("hh:mm a");

    Time routineTime = new Time(routineTimeFormat.parse(routines.get(0).getStartTime()).getTime());
    for(int i=1;i<=6;i++){
      PdfPCell dayCell = new PdfPCell();
      Paragraph dayParagraph = new Paragraph(dateMap.get(i));
      dayParagraph.setFont(FontFactory.getFont(FontFactory.TIMES_BOLDITALIC,9));
      dayCell.addElement(dayParagraph);
      table.addCell(dayCell);

      timeInitializer =  new Time(timeFormat.parse(appSettingMap.get("class start time")).getTime());

      while(true){
        if(timeInitializer.getTime()>=endTime.getTime()){
          break;
        }else{
          if(routines.size()!=0){
            if(routineTime.equals(timeInitializer) && routines.get(0).getDay()==i){
              List<String> sectionList = new ArrayList<>();
              String courseNo = routines.get(0).getCourseNo();
              String courseId= routines.get(0).getCourseId();
              sectionList.add(routines.get(0).getSection());
              String sections= routines.get(0).getSection();
              String roomNo = roomsMap.get(routines.get(0).getRoomId()).get(0).getRoomNo() ;
              int duration  = routines.get(0).getDuration();

              int routineIterator=1;
              while(true){
                if(routines.size()>1){
                  if(routines.get(routineIterator).getCourseId().equals(courseId) && routines.get(routineIterator).getDay()==i){
                    if(sectionList.contains(routines.get(routineIterator).getSection())==false)
                    {
                      sections = sections+"+"+routines.get(routineIterator).getSection();
                      sectionList.add(routines.get(routineIterator).getSection());
                    }
                    routines.remove(0);
                  }else{
                    break;
                  }
                }else{
                  break;
                }

              }

              Paragraph upperParagraph = new Paragraph();
              Paragraph lowerParagraph = new Paragraph();

              if(type == ClassRoutineType.teacherAndStudentRoutine){
                upperParagraph = new Paragraph(courseNo+"("+sections+")",tableDataFont);
                lowerParagraph = new Paragraph(roomNo,tableDataFont);
              }
              else{
                Course course = courseMapWithCourseId.get(courseId);
                upperParagraph = new Paragraph(course.getYear()+"-"+course.getSemester()+"("+sections+")",tableDataFont);
                lowerParagraph= new Paragraph(" ");
              }


              upperParagraph.setAlignment(Element.ALIGN_CENTER);
              lowerParagraph.setAlignment(Element.ALIGN_CENTER);
              PdfPCell cell = new PdfPCell();
              cell.addElement(upperParagraph);
              cell.addElement(lowerParagraph);
              cell.setColspan(duration);
              table.addCell(cell);
              if(routines.size()!=0){
                routines.remove(0);
                if(routines.size()!=0){
                  routineTime =new Time(routineTimeFormat.parse(routines.get(0).getStartTime()).getTime());
                }
              }
              timeInitializer.setTime(timeInitializer.getTime()+(duration*classDuration) * 60000);

            }else{
              PdfPCell cell = new PdfPCell();
              cell.addElement(new Paragraph(" "));
              cell.addElement(new Paragraph(" "));

              table.addCell(cell);
              timeInitializer.setTime(timeInitializer.getTime()+classDuration * 60000);
            }
          }
          else{
            PdfPCell cell = new PdfPCell();
            cell.addElement(new Paragraph(" "));
            cell.addElement(new Paragraph(" "));

            table.addCell(cell);
            timeInitializer.setTime(timeInitializer.getTime()+classDuration * 60000);
          }

        }
      }


    }

    document.add(table);
    return document;
  }
}
