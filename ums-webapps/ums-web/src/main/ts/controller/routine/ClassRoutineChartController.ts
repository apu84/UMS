module ums {
  import ClassRoom = ums.ClassRoom;

  interface DeptProgram {
    deptId: string;
    programs: Program[];
  }


  interface IConstant {
    id: string;
    name: string;
  }

  export class ClassRoutineChartController {
    private routineData: ClassRoutine[];
    private tableHeader: IRoutineTableHeader[];
    private weekDay: IConstant[];
    private counter: number = 0;

    public static $inject = ['appConstants', 'HttpClient', '$q', 'notify', '$sce', '$window', 'semesterService', 'courseService', 'classRoomService', 'classRoutineService', '$timeout', 'userService', 'routineConfigService', '$state', 'employeeService'];

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
                private employeeService: EmployeeService) {

      this.init();
    }

    public init() {
      this.generateHeader();
      this.generateBody();
      if (this.classRoutineService.enableEdit) {
        this.getCourseList();
        this.getClassRoomList();
        this.getTeacherList();
      }
    }

    public getCourseList() {
      this.courseService.getCourse(this.classRoutineService.selectedSemester.id,
          this.classRoutineService.selectedProgram.id,
          +this.classRoutineService.studentsYear,
          +this.classRoutineService.studentsSemester).then((courseList: Course[]) => {
        this.classRoutineService.courseList = [];
        this.classRoutineService.courseList = courseList;
      })
    }

    public getTeacherList() {
      this.employeeService.getActiveTeacherByDept().then((teacherList: Employee[]) => {
        this.classRoutineService.teacherList = [];
        this.classRoutineService.teacherList = teacherList;
      })
    }

    public getClassRoomList() {
      //todo fetch dept wise room list
      this.classRoomService.getClassRooms().then((roomList: ClassRoom[]) => {
        this.classRoutineService.roomList = [];
        this.classRoutineService.roomList = roomList;
        console.log("Room list");
        console.log(this.classRoutineService.roomList);
      });
    }

    public edit(day: string, header: IRoutineTableHeader) {
      this.classRoutineService.selectedDay = day;
      this.classRoutineService.selectedHeader = header;
      console.log("in the edit");
      $("#routineConfigModal").modal('show');
      this.counter += 2;
      this.$state.go('classRoutine.classRoutineChart.classRoutineSlotEditForm', {}, {reload: 'classRoutine.classRoutineChart.classRoutineSlotEditForm'}
      );
      // $('#routineConfigModal').modal('toggle');
      /*$('#myModal').modal('toggle');
      $('#myModal').modal('show');
      $('#myModal').modal('hide');*/
    }

    public generateBody() {
      console.log("generating body");
      let weekDays: IConstant[] = [];
      weekDays = this.appConstants.weekday;
      this.classRoutineService.weekDayMapWithId = {};
      weekDays.forEach((w: IConstant) => this.classRoutineService.weekDayMapWithId[w.id] = w.name);
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
        let tableHeaderTmp: IRoutineTableHeader = <IRoutineTableHeader>{};
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