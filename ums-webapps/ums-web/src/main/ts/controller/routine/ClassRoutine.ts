module ums {

  export interface DeptProgram {
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

  import IParameter = ums.IParameter;

  export class ClassRoutineController {

    private selectedSemester: Semester;
    private semesterList: Semester[];
    private programType: string;
    private ACTIVE_STATUS:number=1;
    private NEWLY_CREATED_STATUS:number=2;
    private UNDERGRADUATE:string='11';
    private POSTGRADUATE:string='22';
    private studentsYear:string;
    private studentsSemester: string;
    private selectedTheorySection:IParameter;
    private theorySectionList:IParameter[];
    private deptList:IParameter[];
    private selectedDept: IParameter;
    private deptMapWithId: any;
    private programList:Program[];
    private selectedProgram:Program;
    private deptProgramList: DeptProgram[];
    private selectedDeptProgram: DeptProgram;
    private deptProgramMapWithDept:any;
    private routineConfig:RoutineConfig;
    private showRoutineSection:boolean;
    private loggedUser: User;
    private routineData: ClassRoutine[];
    private tableHeader: ITableHeader[];
    private weekDay: IConstant[];

    public static $inject = ['appConstants','HttpClient','$q','notify','$sce','$window','semesterService','courseService','classRoomService','classRoutineService','$timeout','userService','routineConfigService','$state'];
    constructor(private appConstants: any,
                private httpClient: HttpClient,
                private $q:ng.IQService,
                private notify: Notify,
                private $sce:ng.ISCEService,
                private $window:ng.IWindowService,
                private semesterService:SemesterService,
                private courseService:CourseService,
                private classRoomService:ClassRoomService,
                private classRoutineService:ClassRoutineService,
                private $timeout : ng.ITimeoutService,
                private userService: UserService,
                private routineConfigService: RoutineConfigService,
                private $state:any) {

        this.init();
    }

    private init(){
        this.showRoutineSection=false;
        this.programType = this.UNDERGRADUATE;
        this.theorySectionList = this.appConstants.theorySections;
        this.selectedTheorySection = this.theorySectionList[0];
        this.studentsYear='1';
        this.studentsSemester='1';
        this.deptList = this.appConstants.deptShort;
        this.deptMapWithId={};
        this.deptList.forEach((d:IParameter)=> this.deptMapWithId[d.id]=d);
        this.deptProgramList = this.appConstants.ugPrograms;
        this.deptProgramMapWithDept={};
        this.deptProgramList.forEach((d:DeptProgram)=>this.deptProgramMapWithDept[d.deptId]=d);
        this.fetchSemesters();
        this.fetchCurrentUser();
    }

    public fetchCurrentUser(){
        this.userService.fetchCurrentUserInfo().then((user:User)=>{
            this.loggedUser=<User>{};
            this.loggedUser=user;
            this.selectedDeptProgram = <DeptProgram>{};
            this.selectedDeptProgram = this.deptProgramMapWithDept[user.departmentId];
            this.selectedDept=<IParameter>{};
            this.selectedDept = this.deptMapWithId[user.departmentId];
            this.selectedProgram = this.selectedDeptProgram.programs[0];
            this.programList = this.selectedDeptProgram.programs;
        });
    }

    public fetchRoutineConfig(): ng.IPromise<any> {
      let defer = this.$q.defer();
      this.routineConfigService.getBySemesterAndProgramType(this.selectedSemester.id, +this.selectedSemester.programTypeId).then((routineConfig: RoutineConfig) => {
        this.routineConfig = routineConfig;
        defer.resolve(routineConfig);
      });
      return defer.promise;
    }

    public generateBody() {
      let weekDays: IConstant[] = [];
      weekDays = this.appConstants.weekday;
      console.log("week days");
      console.log(weekDays);
      this.weekDay = [];
      for (var i = 0; i < weekDays.length; i++) {
        if (+weekDays[i].id >= this.routineConfig.dayFrom && +weekDays[i].id <= this.routineConfig.dayTo)
          this.weekDay.push(weekDays[i]);
      }
    }

    public generateHeader() {
      console.log("Generating empty routine");
      let startTime: any = {};
      startTime = moment(this.routineConfig.startTime, 'hh:mm A');
      let endTime = moment(this.routineConfig.endTime, 'hh:mm A');
      this.tableHeader = [];
      while (startTime < endTime) {
        let tableHeaderTmp: ITableHeader = <ITableHeader>{};
        tableHeaderTmp.startTime = moment(startTime).format('hh:mm A');
        startTime = moment(startTime).add(this.routineConfig.duration, 'm').toDate();
        tableHeaderTmp.endTime = moment(startTime).format('hh:mm A');
        this.tableHeader.push(tableHeaderTmp);
      }
    }

    public fetchRoutineData() {
      this.fetchRoutineConfig().then((routineConfig: RoutineConfig) => {
        if (this.routineConfig == null || this.routineConfig == undefined) {
          this.notify.error("Routine of the semester is not yet configured, please contact with the registrar office");
        }
        else {

          this.classRoutineService.getClassRoutineForEmployee(this.selectedSemester.id, this.selectedProgram.id, +this.studentsYear, +this.studentsSemester, this.selectedTheorySection.id).then((routineData: ClassRoutine[]) => {
            this.generateHeader();
            this.generateBody();
            this.routineData = [];
            this.routineData = routineData;
            if (routineData.length == 0)
              console.log("routine data");
            console.log(routineData);
          });
        }
      });
    }



    public fetchSemesters(){
        this.semesterService.fetchSemesters(+this.programType).then((semesterList: Semester[])=>{
            this.semesterList=[];
            this.selectedSemester=<Semester>{};
            for(let i:number=0; i<semesterList.length; i++){
                if(semesterList[i].status==this.ACTIVE_STATUS)
                    this.selectedSemester = semesterList[i];
                this.semesterList.push(semesterList[i]);
            }
            console.log(this.semesterList);
        });
    }



    public searchForRoutineData(){
      this.fetchRoutineData();
      Utils.expandRightDiv();

    }


  }

  UMS.controller("ClassRoutine", ClassRoutineController);
}