module ums {
  export class ClassRoutineSlotEditController {


    private selectedCourse: Course;
    private selectedRoom: ClassRoom;
    private showCourseInfo: boolean;
    private showRoomInfo: boolean;
    private routineBasedOnCourse: ClassRoutine[];
    private routineBasedOnRoom: ClassRoutine[];
    private THEORY_TYPE: number = 1;
    private SESSIONAL_TYPE: number = 2;
    private courseTeacher: Employee = <Employee>{};
    private sectionList: any = {};


    public static $inject = ['appConstants', 'HttpClient', '$q', 'notify', '$sce', '$window', 'semesterService', 'courseService', 'classRoomService', 'classRoutineService', '$timeout', 'userService', 'routineConfigService', '$state'];

    constructor(private appConstants: any,
                private httpClient: HttpClient,
                private $q: ng.IQService,
                private notify: Notify,
                private $sce: ng.ISCEService,
                private $window: ng.IWindowService,
                private semesterService: SemesterService,
                private courseService: CourseService,
                private classRoomService: ClassRoomService,
                private classRoutineService: ClassRoutineService,
                private $timeout: ng.ITimeoutService,
                private userService: UserService,
                private routineConfigService: RoutineConfigService,
                private $state: any) {

      this.classRoutineService.slotRoutineList = [];
      this.init();
    }

    public init() {
      this.classRoutineService.slotRoutineList = [];
      this.showCourseInfo = false;
      this.showRoomInfo = false;
      let slotRoutine: ClassRoutine = <ClassRoutine>{};
      slotRoutine = this.initialzeRoutine(slotRoutine);
      this.classRoutineService.slotRoutineList.push(slotRoutine);
      this.setSessionalSection();
    }


    private initialzeRoutine(slotRoutine: ClassRoutine): ClassRoutine {
      slotRoutine.startTime = this.classRoutineService.selectedHeader.startTime;
      slotRoutine.endTime = this.classRoutineService.selectedHeader.endTime;
      slotRoutine.semesterId = this.classRoutineService.selectedSemester.id;
      slotRoutine.semester = this.classRoutineService.selectedSemester;
      slotRoutine.programId = this.classRoutineService.selectedProgram.id;
      slotRoutine.program = this.classRoutineService.selectedProgram;
      slotRoutine.duration = this.routineConfigService.routineConfig.duration.toString();
      slotRoutine.academicYear = +this.classRoutineService.studentsYear;
      slotRoutine.academicSemester = +this.classRoutineService.studentsSemester;
      slotRoutine.section = this.classRoutineService.selectedTheorySection.id;
      slotRoutine.day = this.classRoutineService.selectedDay.id;
      return slotRoutine;
    }

    public add() {
      let slotRoutine: ClassRoutine = <ClassRoutine>{};
      slotRoutine = this.initialzeRoutine(slotRoutine);
      this.classRoutineService.slotRoutineList.push(slotRoutine);
    }

    public courseSelected(slotRoutine: ClassRoutine) {
      this.selectedCourse = slotRoutine.course;
      let startTime: any = {};
      startTime = moment(slotRoutine.startTime, 'hh:mm A');
      if (slotRoutine.course.type_value == this.SESSIONAL_TYPE) {
        let endTime: any = moment(startTime).add(this.routineConfigService.routineConfig.duration * 3, 'm').toDate();
        slotRoutine.endTime = moment(endTime).format('hh:mm A');
        slotRoutine.sessionalSection = this.sectionList[0];
        slotRoutine.duration = (this.routineConfigService.routineConfig.duration * 3).toString();
      } else {
        let endTime: any = moment(startTime).add(this.routineConfigService.routineConfig.duration, 'm').toDate();
        slotRoutine.endTime = moment(endTime).format('hh:mm A');
        slotRoutine.duration = this.routineConfigService.routineConfig.duration.toString();
      }
      slotRoutine.courseId = slotRoutine.course.id;
      this.fetchCourseInfo();
    }

    public setSessionalSection() {
      if (this.classRoutineService.selectedTheorySection.id == 'A')
        this.sectionList = this.appConstants.sessionalSectionsA;
      else if (this.classRoutineService.selectedTheorySection.id == 'B')
        this.sectionList = this.appConstants.sessionalSectionsB;
      else if (this.classRoutineService.selectedTheorySection.id == 'C')
        this.sectionList = this.appConstants.sessionalSectionsC;
      else
        this.sectionList = this.appConstants.sessionalSectionsD;
    }


    public addSelectedTeacher(slotRoutine: ClassRoutine) {
      let courseTeacher: CourseTeacherInterface = <CourseTeacherInterface>{};
      courseTeacher.course = slotRoutine.course;
      courseTeacher.courseId = slotRoutine.courseId;
      courseTeacher.courseType = slotRoutine.course.type_value;
      courseTeacher.section = slotRoutine.section;
      courseTeacher.semesterId = slotRoutine.semesterId.toString();
      courseTeacher.teacher = <Teacher>{};
      courseTeacher.teacher.id = slotRoutine.employee.id;
      courseTeacher.teacherId = slotRoutine.employee.id;
      courseTeacher.teacher.name = slotRoutine.employee.employeeName;
      courseTeacher.teacher.departmentId = slotRoutine.employee.deptOfficeId;
      courseTeacher.teacher.departmentName = slotRoutine.employee.deptOfficeName;
      courseTeacher.teacher.designationName = slotRoutine.employee.designationName;
      courseTeacher.teacher.status = slotRoutine.employee.status;
      if (slotRoutine.courseTeacher == undefined)
        slotRoutine.courseTeacher = [];
      slotRoutine.courseTeacher.push(courseTeacher);
      slotRoutine.employee = <Employee>{};
    }

    public fetchCourseInfo() {
      this.showCourseInfo = true;
      this.showRoomInfo = false;
      this.classRoutineService.getRoutineBySemesterAndCourse(this.classRoutineService.selectedSemester.id, this.selectedCourse.id).then((classRoutineList: ClassRoutine[]) => {

        this.routineBasedOnCourse = [];
        this.routineBasedOnCourse = classRoutineList;
      })
    }

    public fetchRoomInfo() {
      this.showCourseInfo = false;
      this.showRoomInfo = true;
      this.classRoutineService.getRoomBasedClassRoutine(this.classRoutineService.selectedSemester.id, this.selectedRoom.id).then((classRoutineList: ClassRoutine[]) => {
        this.routineBasedOnRoom = [];
        this.routineBasedOnRoom = classRoutineList;
      })
    }

    public roomSelected(slotRoutine: ClassRoutine) {
      this.selectedRoom = slotRoutine.room;
      slotRoutine.roomId = slotRoutine.room.id;
      this.fetchRoomInfo();
    }

    public save() {
      this.classRoutineService.saveOrUpdateClassRoutine(this.classRoutineService.slotRoutineList).then((updatedRoutineList: ClassRoutine[]) => {
        this.classRoutineService.routineData = [];
        this.classRoutineService.routineData = updatedRoutineList;
      })
    }

    public remove(routine: ClassRoutine) {
      for (var i = 0; i < this.classRoutineService.slotRoutineList.length; i++) {
        if (routine == this.classRoutineService.slotRoutineList[i]) {
          this.classRoutineService.slotRoutineList.splice(i, 1);
          break;
        }
      }
    }

    public assignEndTime(classRoutine: ClassRoutine) {
      let duration: number = angular.copy(this.routineConfigService.routineConfig.duration);
      if (classRoutine.course.type_value == Utils.COURSE_TYPE_SESSIONAL)
        duration = duration * 3;
      let startTime: any = {};
      startTime = moment(classRoutine.startTime, 'hh:mm A');
      let endTime: any = moment(startTime).add(duration, 'm').toDate();
      classRoutine.endTime = moment(endTime).format('hh:mm A');
    }
  }

  UMS.controller("ClassRoutineSlotEditController", ClassRoutineSlotEditController);
}