/**
 * Created by My Pc on 17-Dec-16.
 */

module ums{
  import Semester = ums.Semester;
  interface IAdmissionTaletalkData extends ng.IScope{

    data:any;
    semesters:Array<Semester>;
    semester:Semester;
    programTypes:Array<IProgramType>;
    programType:IProgramType;

    showUploadPortion:boolean;

    getSemesters:Function;
    fetchTaletalkData:Function;
    fetchExcelFormat:Function;
  }


  interface  IProgramType{
    id:string;
    name:string;
  }


  class AdmissionTaletalkData{
    public static $inject = ['appConstants','HttpClient','$scope','$q','notify','$sce','$window','semesterService','facultyService', 'admissionStudentService'];
    constructor(private appConstants: any,
                private httpClient: HttpClient,
                private $scope: IAdmissionTaletalkData,
                private $q:ng.IQService,
                private notify: Notify,
                private $sce:ng.ISCEService,
                private $window:ng.IWindowService,
                private semesterService: SemesterService,
                private facultyService: FacultyService,
                private admissionStudentService:AdmissionStudentService) {

      $scope.programTypes = appConstants.programType;
      $scope.programType = $scope.programTypes[0];
      $scope.getSemesters = this.getSemesters.bind(this);
      $scope.fetchTaletalkData = this.fetchTaletalkData.bind(this);
      $scope.fetchExcelFormat = this.fetchExcelFormat.bind(this);

      this.getSemesters();
      Utils.setValidationOptions("form-horizontal");

    }




    private getSemesters(){
      console.log("This is program type");
      console.log(this.$scope.programType);

      this.semesterService.fetchSemesters(+this.$scope.programType.id).then((semesters:Array<Semester>)=>{
        this.$scope.semesters=[];
        for(var i=0;i<semesters.length;i++){
          if(semesters[i].status==2){
            this.$scope.semester = semesters[i];
          }
          this.$scope.semesters.push(semesters[i]);
        }
        console.log(this.$scope.semesters);
      });
    }

    private fetchTaletalkData(){
      Utils.expandRightDiv();

      this.admissionStudentService.fetchTaletalkData(this.$scope.semester.id).then((admissionStudents:Array<AdmissionStudent>)=>{
        if(admissionStudents.length==0){
          this.$scope.showUploadPortion=true;
        }else{
          this.$scope.showUploadPortion=false;
        }
      });
    }

    private fetchExcelFormat(){
      this.admissionStudentService.downloadExcelFile(this.$scope.semester.id);
    }

  }

  UMS.controller("AdmissionTaletalkData", AdmissionTaletalkData);
}