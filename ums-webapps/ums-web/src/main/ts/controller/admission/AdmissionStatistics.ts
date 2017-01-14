/**
 * Created by Monjur-E-Morshed on 14-Jan-17.
 */

module ums{
  interface IAdmissionStatistics extends ng.IScope{
    semesters:Array<Semester>;
    quota:Array<String>;
    semester:Semester;
    programTypes:Array<IProgramType>;
    programType:IProgramType;
    faculty:Faculty;
    faculties:Array<Faculty>;

    glStatistics:Array<IStatistics>;
    raStatistics:Array<IStatistics>;
    ffStatistics:Array<IStatistics>;

    searchStatistics:Function;
  }

  interface  IProgramType{
    id:string;
    name:string;
  }

  interface IStatistics{
    programId:number;
    programName:string;
    allocatedSeat:number;
    selected:number;
    remaining:number;
    waiting:number;
  }

  class AdmissionStatistics{
    public static $inject = ['appConstants','HttpClient','$scope','$interval','$q','notify','$sce','$window','semesterService','facultyService', 'admissionStudentService'];
    constructor(private appConstants: any,
                private httpClient: HttpClient,
                private $scope: IAdmissionStatistics,
                private $interval: ng.IIntervalService,
                private $q:ng.IQService,
                private notify: Notify,
                private $sce:ng.ISCEService,
                private $window:ng.IWindowService,
                private semesterService: SemesterService,
                private facultyService: FacultyService,
                private admissionStudentService:AdmissionStudentService
                ) {

      $scope.programTypes = appConstants.programType;
      $scope.programType = $scope.programTypes[0];


      $scope.searchStatistics = this.searchStatistics.bind(this);

      this.getSemesters();
      this.getFaculties();
      this.initializeQuota();
    }


    private getFaculties(){
      this.facultyService.getAllFaculties().then((faculties:Array<Faculty>)=>{
        this.$scope.faculties=[];
        for(var i=0;i<faculties.length;i++){
          if(faculties[i].shortName!='BUSINESS'){
            this.$scope.faculties.push(faculties[i]);
          }
        }
        this.$scope.faculty=faculties[0];

      });
    }

    private getSemesters(){
      this.semesterService.fetchSemesters(+this.$scope.programType.id).then((semesters:Array<Semester>)=>{
        this.$scope.semesters=[];
        for(var i=0;i<semesters.length;i++){
          if(semesters[i].status==2){
            this.$scope.semester = semesters[i];
          }
          this.$scope.semesters.push(semesters[i]);
        }
      });
    }

    private searchStatistics(){
      Utils.expandRightDiv();
      this.getStatistics();
      console.log(this.$interval);
      this.$interval(()=>{
        this.getStatistics();
      },10000);
    }

    private getStatistics(){
      this.getGLStatistics();
      this.getFFStatistics();
      this.getRAStatistics();
    }

    private initializeQuota(){
      this.$scope.quota=[];
      this.$scope.quota.push("RA");
      this.$scope.quota.push("GL");
      this.$scope.quota.push("FF");
    }

    private getFFStatistics(){
      this.$scope.ffStatistics=[];

      this.admissionStudentService.fetchStatistics(this.$scope.semester.id, +this.$scope.programType.id,Utils.FREEDOM_FIGHTER, this.$scope.faculty.shortName).then((data)=>{
        this.$scope.ffStatistics=data;
      });


    }

    private getRAStatistics(){
      this.$scope.raStatistics=[];

      this.admissionStudentService.fetchStatistics(this.$scope.semester.id, +this.$scope.programType.id,Utils.REMOTE_AREA, this.$scope.faculty.shortName).then((data)=>{
        this.$scope.raStatistics=data;
      });

    }

    private getGLStatistics(){
      this.$scope.glStatistics=[];

      this.admissionStudentService.fetchStatistics(this.$scope.semester.id, +this.$scope.programType.id,Utils.GENERAL, this.$scope.faculty.shortName).then((data)=>{
        this.$scope.glStatistics=data;
      });

    }

  }


  UMS.controller("AdmissionStatistics",AdmissionStatistics);
}