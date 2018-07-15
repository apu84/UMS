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
    private sectionList: IConstant[] = [];
    private sessionalSectionMap: any = {};
    private slotGroupNo: number;


    public static $inject = ['appConstants', 'HttpClient', '$q', 'notify', '$sce', '$window', 'semesterService', 'courseService', 'classRoomService', 'classRoutineService', '$timeout', 'userService', 'routineConfigService', '$state', 'courseTeacherService'];

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
                private $state: any,
                private courseTeacherService: CourseTeacherService) {

      this.init();
    }

    public init() {
      this.showCourseInfo = false;
      this.showRoomInfo = false;
      if (this.classRoutineService.slotRoutineList.length == 0) {
        this.slotGroupNo = Math.floor((Math.random() * 10000) + 1);
        let slotRoutine: ClassRoutine = <ClassRoutine>{};
        slotRoutine.slotGroup = this.slotGroupNo;
        slotRoutine = this.initialzeRoutine(slotRoutine);
        this.classRoutineService.slotRoutineList.push(slotRoutine);
      }
      this.setSessionalSection().then((sectionList: any) => {
        this.assignSectionsToSessionalCourses();
      });
    }


    private initialzeRoutine(slotRoutine: ClassRoutine): ClassRoutine {
      slotRoutine.startTime = this.classRoutineService.selectedHeader.startTime;
      slotRoutine.endTime = this.classRoutineService.selectedHeader.endTime;
      slotRoutine.semesterId = this.classRoutineService.selectedSemester.id;
      slotRoutine.semester = this.classRoutineService.selectedSemester;
      slotRoutine.programId = this.classRoutineService.selectedProgram.id.toString();
      slotRoutine.program = this.classRoutineService.selectedProgram;
      if (slotRoutine.duration == null)
        slotRoutine.duration = this.routineConfigService.routineConfig.duration.toString();
      else
        slotRoutine.duration = slotRoutine.duration.toString();
      slotRoutine.academicYear = +this.classRoutineService.studentsYear;
      slotRoutine.academicSemester = +this.classRoutineService.studentsSemester;
      slotRoutine.section = this.classRoutineService.selectedTheorySection.id;
      slotRoutine.day = this.classRoutineService.selectedDay.id;
      return slotRoutine;
    }

    public add() {
      let slotRoutine: ClassRoutine = <ClassRoutine>{};
      slotRoutine.slotGroup = this.slotGroupNo;
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
      this.assignExistingTeacher(slotRoutine);
      this.fetchCourseInfo();
    }

    private assignExistingTeacher(slotRoutine: ClassRoutine) {
      if (this.classRoutineService.courseTeacherMap[slotRoutine.course.id] != undefined) {
        let courseTeacherList: CourseTeacherInterface[] = angular.copy(this.classRoutineService.courseTeacherMap[slotRoutine.course.id]);
        courseTeacherList.forEach((c: CourseTeacherInterface) => c.id = undefined);
        slotRoutine.courseTeacher = courseTeacherList;
      } else {
        slotRoutine.courseTeacher = [];
      }
    }


    public removeCourseTeacher(routine: ClassRoutine, courseTeacher: CourseTeacherInterface) {
      for (var i = 0; i < routine.courseTeacher.length; i++) {
        if (routine.courseTeacher[i] == courseTeacher) {
          routine.courseTeacher.splice(i, 1);

          if (courseTeacher.id != null) {
            this.courseTeacherService.delete(courseTeacher.id);
          }
          break;
        }
      }
    }

    public setSessionalSection(): ng.IPromise<any> {
      let defer: ng.IDeferred<any> = this.$q.defer();
      if (this.classRoutineService.selectedTheorySection.id == 'A')
        this.sectionList = this.appConstants.sessionalSectionsA;
      else if (this.classRoutineService.selectedTheorySection.id == 'B')
        this.sectionList = this.appConstants.sessionalSectionsB;
      else if (this.classRoutineService.selectedTheorySection.id == 'C')
        this.sectionList = this.appConstants.sessionalSectionsC;
      else
        this.sectionList = this.appConstants.sessionalSectionsD;

      defer.resolve(this.sectionList);
      return defer.promise;
    }

    public assignSectionsToSessionalCourses() {
      this.sessionalSectionMap = {};
      this.sectionList.forEach((s) => {
        this.sessionalSectionMap[s.id] = s;
      });

      this.classRoutineService.slotRoutineList.forEach((routine: ClassRoutine) => {
        routine.sessionalSection = this.sessionalSectionMap[routine.section];
      })
    }

    public addSelectedTeacher(slotRoutine: ClassRoutine) {
      let courseTeacher: CourseTeacherInterface = <CourseTeacherInterface>{};
      courseTeacher.course = slotRoutine.course;
      courseTeacher.courseId = slotRoutine.courseId;
      courseTeacher.courseType = slotRoutine.course.type_value;
      courseTeacher.section = slotRoutine.section;
      courseTeacher.semesterId = this.classRoutineService.selectedSemester.id.toString();
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
      this.createCourseTeacherMapForSlot(courseTeacher);

    }

    private createCourseTeacherMapForSlot(courseTeacher: CourseTeacherInterface) {
      let courseTeacherList: CourseTeacherInterface[] = [];
      if (this.classRoutineService.courseTeacherWithSectionMap[courseTeacher.courseId + courseTeacher.section] == undefined) {
        courseTeacherList.push(courseTeacher);
        this.classRoutineService.courseTeacherWithSectionMap[courseTeacher.courseId + courseTeacher.section] = courseTeacherList;
        this.classRoutineService.courseTeacherMap[courseTeacher.courseId] = courseTeacherList;
      } else {
        courseTeacherList = this.classRoutineService.courseTeacherWithSectionMap[courseTeacher.courseId + courseTeacher.section];
        courseTeacherList.push(courseTeacher);
        this.classRoutineService.courseTeacherWithSectionMap[courseTeacher.courseId + courseTeacher.section] = courseTeacherList;
      }
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
      this.classRoutineService.getRoomBasedClassRoutine(this.classRoutineService.selectedSemester.id, +this.selectedRoom.id).then((classRoutineList: ClassRoutine[]) => {
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
      if (routine.courseTeacher != undefined && routine.courseTeacher.length > 0) {
        routine.courseTeacher.forEach((t: CourseTeacherInterface) => {
          t.semesterId = this.classRoutineService.selectedSemester.id.toString();
          t.section = this.classRoutineService.selectedTheorySection.id;
        })
        this.courseTeacherService.deleteTeacherList(routine.courseTeacher).then((response) => {
          if (response != undefined)
            this.removeSlotRoutine(routine);
        })
      } else {
        this.removeSlotRoutine(routine);
      }

    }

    private removeFromMainRoutineData(routine: ClassRoutine) {
      for (var i = 0; i < this.classRoutineService.routineData.length; i++) {
        if (this.classRoutineService.routineData[i] = routine) {
          this.classRoutineService.routineData.splice(i, 1);
          break;
        }
      }
    }

    private removeSlotRoutine(routine: ClassRoutine) {
      for (var i = 0; i < this.classRoutineService.slotRoutineList.length; i++) {
        if (routine == this.classRoutineService.slotRoutineList[i]) {
          if (routine.id != null) {
            this.classRoutineService.deleteRoutineById(routine.id);
            this.removeFromMainRoutineData(routine);
            this.classRoutineService.slotRoutineList.splice(i, 1);
          }
          else
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