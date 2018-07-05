module ums {
  export class ClassRoutineSlotEditController {

    private slotRoutineList: ClassRoutine[];


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
      let slotRoutine: ClassRoutine = <ClassRoutine>{};
      this.slotRoutineList.push(slotRoutine);
    }

    public add() {
      console.log("in the add section");
      let slotRoutine: ClassRoutine = <ClassRoutine>{};
      this.slotRoutineList.push(slotRoutine);
    }

    public courseSelected(course: Course) {
      console.log("Course is selected");
      console.log(course);
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