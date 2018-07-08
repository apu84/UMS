module ums {
  export class ClassRoutineSlotEditController {

    private slotRoutineList: ClassRoutine[];


    private selectedCourse: Course;
    private selectedRoom: ClassRoom;
    private showCourseInfo: boolean;
    private showRoomInfo: boolean;
    private routineBasedOnCourse: ClassRoutine[];
    private routineBasedOnRoom: ClassRoutine[];


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

      this.slotRoutineList = [];
      this.init();
    }

    public init() {
      console.log("in the constructor in routine slot edit");
      this.slotRoutineList = [];
      this.showCourseInfo = false;
      this.showRoomInfo = false;
      let slotRoutine: ClassRoutine = <ClassRoutine>{};
      slotRoutine.startTime = this.classRoutineService.selectedHeader.startTime;
      slotRoutine.endTime = this.classRoutineService.selectedHeader.endTime;
      this.slotRoutineList.push(slotRoutine);
    }

    public add() {
      console.log("in the add section");
      let slotRoutine: ClassRoutine = <ClassRoutine>{};
      slotRoutine.startTime = this.classRoutineService.selectedHeader.startTime;
      slotRoutine.endTime = this.classRoutineService.selectedHeader.endTime;
      this.slotRoutineList.push(slotRoutine);
    }

    public courseSelected(course: Course) {
      this.selectedCourse = course;
      console.log('selected course');
      console.log(course);
      this.fetchCourseInfo();
    }

    public fetchCourseInfo() {
      console.log("fetching course info")
      this.showCourseInfo = true;
      this.showRoomInfo = false;
      this.classRoutineService.getRoutineBySemesterAndCourse(this.classRoutineService.selectedSemester.id, this.selectedCourse.id).then((classRoutineList: ClassRoutine[]) => {
        console.log("classRoutine list");
        console.log(classRoutineList);
        console.log(this.showCourseInfo);
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

    public roomSelected(room: ClassRoom) {
      this.selectedRoom = room;
      this.fetchRoomInfo();
    }

    public remove(routine: ClassRoutine) {
      for (var i = 0; i < this.slotRoutineList.length; i++) {
        if (routine == this.slotRoutineList[i]) {
          this.slotRoutineList.splice(i, 1);
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