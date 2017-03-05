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
    fontSize:number;
    glStatistics:Array<IStatistics>;
    raStatistics:Array<IStatistics>;
    ffStatistics:Array<IStatistics>;
    columnSize:string;

    showGA:boolean;
    showFF:boolean;
    showRA:boolean;
    showGCE:boolean;

    searchStatistics:Function;
    increaseFontSize:Function;
    decreaseFontSize:Function;
    closeNav:Function;
    showOrHideTable:Function;
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
      $scope.glStatistics=[];
      $scope.raStatistics=[];
      //$scope.gceStatistics=[];
      $scope.ffStatistics=[];
      $scope.fontSize=10;
      $scope.showGA = true;
      $scope.showFF = true;
      $scope.showRA = true;
      $scope.showGCE = true;
      $scope.columnSize="col-md-6";

      $scope.searchStatistics = this.searchStatistics.bind(this);
      $scope.closeNav = this.closeNav.bind(this);
      $scope.increaseFontSize = this.increaseFontSize.bind(this);
      $scope.decreaseFontSize = this.decreaseFontSize.bind(this);
      $scope.showOrHideTable = this.showOrHideTable.bind(this);
      this.getSemesters();
      this.getFaculties();
      this.initializeQuota();
    }


    private closeNav(){
      //angular.element(document.documentElement("#myModal").style.width=0);
      //angular.element("#myModal").style.width=0;
      angular.element("#moModal").css('width',0);
    }

    private showOrHideTable(type:string){
      this.switchOnOffContent(type).then((data)=>{
        this.configureColumnSize();
      });
    }

    private configureColumnSize(){
      var counter:number=0;
      if(this.$scope.showGA==true){
        counter+=1;
      }
      if(this.$scope.showFF==true){
        counter+=1;
      }
      if(this.$scope.showRA==true){
        counter+=1;
      }
      if(this.$scope.showGCE==true){
        counter+=1;
      }

      if(counter<=2){
       this.$scope.columnSize="col-md-12";
      }else{
        this.$scope.columnSize="col-md-6";
      }
    }


    private switchOnOffContent(type:string):ng.IPromise<any>{
      var defer = this.$q.defer();
      if(type=='ga'){
        this.$scope.showGA=!this.$scope.showGA;
      }
      else if(type=='ff'){
        this.$scope.showFF = !this.$scope.showFF;
      }
      else if(type=='ra'){
        this.$scope.showRA = !this.$scope.showRA;
      }else{
        this.$scope.showGCE = !this.$scope.showGCE;
      }
      defer.resolve("done");
      return defer.promise;
    }

    private increaseFontSize(){
      this.$scope.fontSize=this.$scope.fontSize+1;
      angular.element(".overlay-content").css('font-size',this.$scope.fontSize+"px");
    }

    private decreaseFontSize(){
      this.$scope.fontSize=this.$scope.fontSize-1;
      angular.element(".overlay-content").css('font-size',this.$scope.fontSize+"px");
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
      this.semesterService.fetchSemesters(+this.$scope.programType.id,5,Utils.SEMESTER_FETCH_WITH_NEWLY_CREATED).then((semesters:Array<Semester>)=>{
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
      },1000);
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
      //this.$scope.ffStatistics=[];
      var ffStatistics=this.$scope.ffStatistics;
      this.admissionStudentService.fetchStatistics(this.$scope.semester.id, +this.$scope.programType.id,Utils.FREEDOM_FIGHTER, this.$scope.faculty.shortName).then((data)=>{
        for(var i=0;i<data.length;i++){
          if(!ffStatistics[i]){
            ffStatistics.push(data[i]);
          }else{
            ffStatistics[i] = data[i];
          }
        }
        this.$scope.ffStatistics=data;
      });


    }

    private getRAStatistics(){
      var raStatistics=this.$scope.raStatistics;

      this.admissionStudentService.fetchStatistics(this.$scope.semester.id, +this.$scope.programType.id,Utils.REMOTE_AREA, this.$scope.faculty.shortName).then((data)=>{

        for(var i=0;i<data.length;i++){
          if(!raStatistics[i]){
            raStatistics.push(data[i]);
          }else{
            raStatistics[i]= data[i];
          }
        }
      });

    }

    private getGLStatistics(){
      var glStatistics = this.$scope.glStatistics;

      this.admissionStudentService.fetchStatistics(this.$scope.semester.id, +this.$scope.programType.id,Utils.GENERAL, this.$scope.faculty.shortName).then((data)=>{
        for(var i=0;i<data.length;i++){
          if(!glStatistics[i]){
            glStatistics.push(data[i]);
          }else{
            glStatistics[i]=data[i];
          }
        }
      });

    }

  }


  UMS.controller("AdmissionStatistics",AdmissionStatistics);
}