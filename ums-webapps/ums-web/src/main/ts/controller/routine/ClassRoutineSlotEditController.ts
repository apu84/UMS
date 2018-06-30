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
      slotRoutine.course = <Course>{};
      slotRoutine.room = <ClassRoom>{};
      this.slotRoutineList.push(slotRoutine);
    }
  }

  UMS.controller("ClassRoutineSlotEditController", ClassRoutineSlotEditController);
}