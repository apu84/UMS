module ums{

  import Semester = ums.Semester;
  import RoutineConfig = ums.RoutineConfig;

  interface IConstant {
    id: string;
    name: string;
  }

    export class RoutineConfigController{

        private weekDays:IParameter[];
        private selectedDayFrom:IParameter;
        private selectedDayTo:IParameter;
        private startTime: string;
        private endTime: string;
        private duration:number;
      private selectedProgramType: IConstant;
      private programTypeList: IConstant[];
      private semesterList: Semester[];
      private selectedSemester: Semester;
      private routineConfigData: RoutineConfig;

      public static $inject = ['appConstants', 'HttpClient', '$q', 'notify', '$sce', '$window', 'routineConfigService', '$stateParams', 'semesterService'];
        constructor(private appConstants: any,
                    private httpClient: HttpClient,
                    private $q:ng.IQService,
                    private notify: Notify,
                    private $sce:ng.ISCEService,
                    private $window:ng.IWindowService,
                    private routineConfigService: RoutineConfigService,
                    private $stateParams: any,
                    private semesterService: SemesterService) {

          this.init();
        }


      public init() {
        this.weekDays = this.appConstants.weekDays;
        this.programTypeList = this.appConstants.programType;
        this.selectedProgramType = this.programTypeList[0];
        this.getSemesterList();
      }

      public getSemesterList() {
        this.semesterService.fetchSemesters(+this.selectedProgramType.id).then((semesterList: Semester[]) => {
          this.semesterList = [];
          this.semesterList = semesterList;
          this.selectedSemester = this.semesterList.filter((s: Semester) => s.status = Utils.SEMESTER_STATUS_ACTIVE)[0];
        });
        }

      public getRoutineConfigData() {
        Utils.expandRightDiv();

        this.routineConfigService.getBySemesterAndProgramType(this.selectedSemester.id, +this.selectedProgramType.id).then((routineConfigData: RoutineConfig) => {
          routineConfigData.startTimeObj = moment(routineConfigData.startTime, "hh:mm A").toDate();
          routineConfigData.endTimeObj = moment(routineConfigData.endTime, "hh:mm A").toDate();
          this.routineConfigData = routineConfigData;
        })
      }

      public saveOrUpdate() {
        console.log("In save or update section");
        this.routineConfigData.programType = +this.selectedProgramType.id;
        this.routineConfigData.semesterId = this.selectedSemester.id;
        this.routineConfigData.startTime = moment(this.routineConfigData.startTimeObj).format("hh:mm A");
        this.routineConfigData.endTime = moment(this.routineConfigData.endTimeObj).format("hh:mm A");
        this.routineConfigService.saveOrUpdate(this.routineConfigData).then((routineConfigData: RoutineConfig) => {
          this.routineConfigData = routineConfigData;
        })
      }

    }

  UMS.controller("RoutineConfigController", RoutineConfigController);
}