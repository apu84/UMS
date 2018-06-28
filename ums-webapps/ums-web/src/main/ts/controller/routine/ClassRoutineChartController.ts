module ums {
  interface DeptProgram {
    deptId: string;
    programs: Program[];
  }

  interface ITableHeader {
    startTime: string;
    endTime: string;
  }

  interface IConstant {
    id: string;
    name: string;
  }

  export class ClassRoutineChartController {
    private routineData: ClassRoutine[];
    private tableHeader: ITableHeader[];
    private weekDay: IConstant[];

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

      this.init();
    }

    public init() {
      this.generateHeader();
      this.generateBody();
    }

    public generateBody() {
      console.log("generating body");
      let weekDays: IConstant[] = [];
      weekDays = this.appConstants.weekday;
      console.log("week days");
      console.log(weekDays);
      this.weekDay = [];
      for (var i = 0; i < weekDays.length; i++) {
        if (+weekDays[i].id >= this.routineConfigService.routineConfig.dayFrom && +weekDays[i].id <= this.routineConfigService.routineConfig.dayTo)
          this.weekDay.push(weekDays[i]);
      }
    }

    public generateHeader() {
      console.log("Generating empty routine");
      let startTime: any = {};
      startTime = moment(this.routineConfigService.routineConfig.startTime, 'hh:mm A');
      let endTime = moment(this.routineConfigService.routineConfig.endTime, 'hh:mm A');
      this.tableHeader = [];
      while (startTime < endTime) {
        let tableHeaderTmp: ITableHeader = <ITableHeader>{};
        tableHeaderTmp.startTime = moment(startTime).format('hh:mm A');
        startTime = moment(startTime).add(this.routineConfigService.routineConfig.duration, 'm').toDate();
        tableHeaderTmp.endTime = moment(startTime).format('hh:mm A');
        this.tableHeader.push(tableHeaderTmp);
      }

      console.log("table header");
      console.log(this.tableHeader);
    }

  }

  UMS.controller("ClassRoutineChartController", ClassRoutineChartController);
}