module ums {

  import ClassRoutine = ums.ClassRoutine;

  interface DeptProgram {
      deptId: string;
      programs: Program[];
  }


  export interface IConstant {
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
    private tableHeader: IRoutineTableHeader[];
    private weekDay: IConstant[];
    private state: any;
    private searchButtonClicked: boolean;
    private counter: number = 1;
    courseTeacherList: Employee[];
    roomList: ClassRoom[];
    selectedRoom: ClassRoom;
    showRoutineChart:boolean;
    routineTemplateFile: any;
    exceptions:string[];
    showLoader: boolean;


    public static $inject = ['appConstants', '$q', 'notify', 'semesterService', 'classRoomService', 'classRoutineService',
      'userService', 'routineConfigService', '$state', 'employeeService'];
    constructor(private appConstants: any,
                private $q:ng.IQService,
                private notify: Notify,
                private semesterService:SemesterService,
                private classRoomService:ClassRoomService,
                private classRoutineService:ClassRoutineService,
                private userService: UserService,
                private routineConfigService: RoutineConfigService,
                private $state:any,
                private employeeService: EmployeeService) {

      this.routineTemplateFile = {};
        this.init();
    }

    public init() {
      this.showRoutineChart = false;
      this.classRoutineService.showSectionWiseRoutine = true;
      this.classRoutineService.showTeacherWiseRoutine = false;
      this.classRoutineService.showRoomWiseRoutine = false;
      this.classRoutineService.sectionSpecific = true;
      this.state = this.$state;
        this.showRoutineSection=false;
        this.programType = this.UNDERGRADUATE;
        this.theorySectionList = this.appConstants.theorySections;
      this.classRoutineService.selectedTheorySection = this.theorySectionList[0];
      this.classRoutineService.studentsYear = '1';
      this.classRoutineService.studentsSemester = '1';
        this.deptList = this.appConstants.deptShort;
        this.deptMapWithId={};
        this.deptList.forEach((d:IParameter)=> this.deptMapWithId[d.id]=d);
        this.deptProgramList = this.appConstants.ugPrograms;
        this.deptProgramMapWithDept={};
        this.deptProgramList.forEach((d:DeptProgram)=>this.deptProgramMapWithDept[d.deptId]=d);
        this.fetchSemesters();
        this.fetchCurrentUser();
      this.searchButtonClicked = false;
      this.classRoutineService.enableEdit = true;
    }

    public uploadFile():void{
      let formData = new FormData();
      formData.append("file", this.routineTemplateFile);
      formData.append("semesterId",this.classRoutineService.selectedSemester.id.toString());
      formData.append("programId", this.classRoutineService.selectedProgram.id.toString());
      this.exceptions = [];
      this.showLoader = true;
      if(this.routineTemplateFile==null){
        this.notify.warn("No file chosen");
        this.showLoader = false;
      }
      else{
        this.classRoutineService.uploadFile(formData).then((exceptions:string[])=>{
          this.exceptions = exceptions;
          this.showLoader = false;
        })
      }
    }

    public fetchCurrentUser(){
        this.userService.fetchCurrentUserInfo().then((user:User)=>{
            this.loggedUser=<User>{};
            this.loggedUser=user;
            this.selectedDeptProgram = <DeptProgram>{};
            this.selectedDeptProgram = this.deptProgramMapWithDept[user.departmentId];
            this.selectedDept=<IParameter>{};
            this.selectedDept = this.deptMapWithId[user.departmentId];
          this.selectPrograms();
        });
    }

    public selectPrograms() {
      this.classRoutineService.selectedProgram = this.selectedDeptProgram.programs[0];
      this.programList = this.selectedDeptProgram.programs;
    }

    public fetchSemesters(){
        this.semesterService.fetchSemesters(+this.programType).then((semesterList: Semester[])=>{
            this.semesterList=[];
            this.selectedSemester=<Semester>{};
            for(let i:number=0; i<semesterList.length; i++){
                if(semesterList[i].status==this.ACTIVE_STATUS)
                  this.classRoutineService.selectedSemester = semesterList[i];
                this.semesterList.push(semesterList[i]);
            }
        });
    }

    public fetchRoutineConfig(): ng.IPromise<any> {
      let defer = this.$q.defer();
      this.routineConfigService.getBySemesterAndProgramType(this.classRoutineService.selectedSemester.id, +this.classRoutineService.selectedSemester.programTypeId).then((routineConfig: RoutineConfig) => {
        if (routineConfig.id == null)
          this.notify.error("Routine configuration is not yet set for the semester, please contact with registrar office")
        this.routineConfigService.routineConfig = routineConfig;
        defer.resolve(this.routineConfig = routineConfig);
      });
      return defer.promise;
    }

    public showSectionWiseRoutinePortion(){
      this.classRoutineService.showSectionWiseRoutine = true;
      this.classRoutineService.showTeacherWiseRoutine = false;
      this.classRoutineService.showRoomWiseRoutine = false;
      this.classRoutineService.sectionSpecific=true;
      this.fetchRoutineData();
    }

    public showTeacherWiseRoutinePortion(){
      this.classRoutineService.showSectionWiseRoutine = false;
      this.classRoutineService.showTeacherWiseRoutine = true;
      this.classRoutineService.showRoomWiseRoutine = false;
      this.showRoutineChart = false;
      this.classRoutineService.sectionSpecific=false;

      this.fetchActiveTeachers();
    }


    private fetchActiveTeachers(){
      this.courseTeacherList = [];
      this.employeeService.getActiveTeachers().then((employees: Employee[])=>{
        this.courseTeacherList = employees;
      })
    }

    public courseTeacherSelected(){
      console.log("Course teacher");
      console.log(this.classRoutineService.selectedTeacher);
      this.classRoutineService.sectionSpecific = false;
      this.fetchRoutineData();
    }


    public showRoomWiseRoutinePortion(){
      this.classRoutineService.showSectionWiseRoutine= false;
      this.classRoutineService.showTeacherWiseRoutine = false;
      this.classRoutineService.showRoomWiseRoutine = true;
      this.showRoutineChart = false;
      this.classRoutineService.sectionSpecific=false;

      this.fetchClassRooms();
    }


    private fetchClassRooms(){
      this.roomList = [];
      this.classRoomService.getClassRooms().then((rooms: ClassRoom[])=>{
        this.roomList = rooms;
      })
    }


    public roomSelected(){
      this.classRoutineService.sectionSpecific= false;
      this.fetchRoutineData();
    }

    public fetchRoutineData(): ng.IPromise<ClassRoutine[]> {
      let defer: ng.IDeferred<ClassRoutine[]> = this.$q.defer();
      this.showRoutineChart = false;
      this.fetchRoutineConfig().then((routineConfig: RoutineConfig) => {
        if (this.routineConfig == null || this.routineConfig == undefined) {
          this.notify.error("Routine of the semester is not yet configured, please contact with the registrar office");
          defer.resolve(undefined);
        }
        else {
          this.fetchRoutineInfo().then((routineData: ClassRoutine[]) => {
            console.log("Routine data");
            console.log(routineData);
            this.classRoutineService.routineData = [];
            this.classRoutineService.dayAndTimeMapWithRoutine = {};
            routineData.forEach((r:ClassRoutine)=>{
              r.startTimeObj = moment(r.startTime, "hh:mm A").toDate();
              r.endTimeObj = moment(r.endTime, "hh:mm A").toDate();
            })
            this.classRoutineService.routineData = routineData;
            this.showRoutineChart=true;
            defer.resolve(routineData);
          });
        }
      });
      return defer.promise;
    }

    private fetchRoutineInfo():ng.IPromise<ClassRoutine[]>{
      let defer: ng.IDeferred<ClassRoutine[]> = this.$q.defer();

      if(this.classRoutineService.showSectionWiseRoutine)
        this.fetchSectionWiseRoutineData().then((routineData:ClassRoutine[])=>defer.resolve(routineData));
      else if(this.classRoutineService.showTeacherWiseRoutine)
        this.fetchTeacherWiseRoutineData().then((routineData:ClassRoutine[])=>defer.resolve(routineData));
      else if(this.classRoutineService.showRoomWiseRoutine)
        this.fetchRoomWiseRoutineData().then((routineData:ClassRoutine[])=>defer.resolve(routineData));

      return defer.promise;

    }


    private fetchSectionWiseRoutineData():ng.IPromise<ClassRoutine[]>{
      let defer: ng.IDeferred<ClassRoutine[]> = this.$q.defer();
      this.classRoutineService.getClassRoutineForEmployee(this.classRoutineService.selectedSemester.id, this.classRoutineService.selectedProgram.id, +this.classRoutineService.studentsYear, +this.classRoutineService.studentsSemester, this.classRoutineService.selectedTheorySection.id).then((routineData: ClassRoutine[]) => {
        defer.resolve(routineData);
      });
      return defer.promise;
    }

    private fetchTeacherWiseRoutineData():ng.IPromise<ClassRoutine[]>{
      let defer: ng.IDeferred<ClassRoutine[]> = this.$q.defer();

      this.classRoutineService.getRoutineForTeacher(this.classRoutineService.selectedTeacher.id, this.classRoutineService.selectedSemester.id).then((routineData: ClassRoutine[])=>{
        defer.resolve(routineData);
      })
      return defer.promise;
    }


    private fetchRoomWiseRoutineData():ng.IPromise<ClassRoutine[]>{
      let defer: ng.IDeferred<ClassRoutine[]> = this.$q.defer();

      this.classRoutineService.getRoomBasedClassRoutine(this.classRoutineService.selectedSemester.id, +this.selectedRoom.id).then((routineData: ClassRoutine[])=>{
        defer.resolve(routineData);
      })
      return defer.promise;
    }
    public searchForRoutineData(){
      Utils.expandRightDiv();
      this.counter += 1;
      this.searchButtonClicked = true;
      this.showRoutineChart = false;
      this.fetchRoutineData().then((routineData) => {
        this.showRoutineChart = true;
        //this.$state.go('classRoutine.classRoutineChart', {}, {reload: 'classRoutine.classRoutineChart'});
      });
    }

  }

  UMS.controller("ClassRoutine", ClassRoutineController);
}