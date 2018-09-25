module ums{


  class TeachersRoutine{
    private showLoader: boolean;

    public static $inject = ['$q','notify','$sce','$window','classRoutineService', 'employeeService', 'semesterService','routineConfigService'];
    constructor(private $q:ng.IQService, private notify: Notify,
                private $sce:ng.ISCEService,
                private $window:ng.IWindowService,
                private classRoutineService:ClassRoutineService,
                private employeeService: EmployeeService,
                private semesterService: SemesterService, private routineConfigService: RoutineConfigService) {

        this.fetchRoutineInformation();
    }

    private fetchRoutineInformation(){

      this.classRoutineService.showSectionWiseRoutine = false;
      this.classRoutineService.showRoomWiseRoutine = false;
      this.classRoutineService.showTeacherWiseRoutine = true;

      this.fetchActiveSemester().then((semester:Semester)=>{
        this.fetchRoutineConfig().then((routineConfig: RoutineConfig)=>{
          this.fetchUserInformation().then((teacher:Employee)=>{
            this.classRoutineService.getRoutineForTeacher(this.classRoutineService.selectedTeacher.id, this.classRoutineService.selectedSemester.id).then((routineList: ClassRoutine[])=>{
              this.classRoutineService.routineData = routineList;
              this.showLoader = false;
            });
          });
        });

      });
    }


    private fetchActiveSemester():ng.IPromise<any>{
      let defer:ng.IDeferred<Semester> = this.$q.defer();
      let ACTIVE_STATUS:number = 1;
      this.semesterService.fetchSemesters(UmsUtil.UNDERGRADUATE).then((semesters: any)=>{
        semesters.forEach((s: Semester)=>{
          if(s.status==ACTIVE_STATUS)
            this.classRoutineService.selectedSemester = s;
        })
        defer.resolve(this.classRoutineService.selectedSemester);
      });
      return defer.promise;
    }


    public fetchRoutineConfig(): ng.IPromise<any> {
      let defer = this.$q.defer();
      this.routineConfigService.getBySemesterAndProgramType(this.classRoutineService.selectedSemester.id,UmsUtil.UNDERGRADUATE).then((routineConfig: RoutineConfig) => {
        if (routineConfig.id == null)
          this.notify.error("Routine configuration is not yet set for the semester, please contact with registrar office")
        this.routineConfigService.routineConfig = routineConfig;
        defer.resolve(routineConfig);
      });
      return defer.promise;
    }

    private fetchUserInformation():ng.IPromise<any>{
      let defer: ng.IDeferred<Employee> = this.$q.defer();

      this.showLoader = true;

      this.employeeService.getLoggedEmployeeInfo().then((employee:Employee)=>{
        this.classRoutineService.selectedTeacher = <Employee>{};
        this.classRoutineService.selectedTeacher = employee;
        console.log(employee);
        console.log(this.classRoutineService.selectedTeacher);
        defer.resolve(this.classRoutineService.selectedTeacher);
      });

      return defer.promise;
    }

    public downloadRoutineReport(){
      this.classRoutineService.getTeacherWiseReport(this.classRoutineService.selectedTeacher.id, this.classRoutineService.selectedSemester.id);
    }

  }

  UMS.controller("TeachersRoutine",TeachersRoutine);

}