module ums {

  export interface DeptProgram {
      deptId: string;
      programs: Program[];
  }


  import IParameter = ums.IParameter;

    export class ClassRoutine  {

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
            console.log("Selected program");
            console.log(this.selectedDeptProgram.programs);
            this.programList = this.selectedDeptProgram.programs;
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

    public fetchRoutineConfig(){
        this.showRoutineSection=true;

        console.log("In the routine config");
        this.routineConfigService.getBySemesterAndProgram(this.selectedSemester.id, +this.selectedProgram.id).then((routineConfig:RoutineConfig)=>{
            if(routineConfig==undefined){
                this.notify.error("Routine configuration is not set");
                console.log("logged user");
                console.log(this.loggedUser);
                $("#routineConfigModal").modal('toggle');
                this.$state.go('classRoutine.classRoutineConfig',{semester:this.selectedSemester, user:this.loggedUser,department:this.selectedDept, program:this.selectedProgram});
            }
            else
                this.routineConfig = routineConfig;
        });
    }



    public searchForRoutineData(){
        Utils.expandRightDiv();
    }



  }

  UMS.controller("ClassRoutine", ClassRoutine);
}