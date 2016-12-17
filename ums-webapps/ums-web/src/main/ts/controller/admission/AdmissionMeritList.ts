/**
 * Created by My Pc on 01-Dec-16.
 */

module ums{

  interface IAdmissionMerit extends ng.IScope{

    semesters:Array<Semester>;
    semester:Semester;
    programTypes:Array<IProgramType>;
    programType:IProgramType;
    faculty:Faculty;
    faculties:Array<Faculty>;
    meritTypes:Array<IMeritListType>;
    meritType:IMeritListType;

    admissionStudents:Array<AdmissionStudent>;


    showUploadPortion:boolean;

    getSemesters:Function;
    fetchTaletalkData:Function;
  }

  interface  IProgramType{
    id:string;
    name:string;
  }

  interface IMeritListType{
    id:string;
    name:string;
  }

  class AdmissionMeritList{
    public static $inject = ['appConstants','HttpClient','$scope','$q','notify','$sce','$window','semesterService','facultyService', 'admissionStudentService'];
    constructor(private appConstants: any,
                private httpClient: HttpClient,
                private $scope: IAdmissionMerit,
                private $q:ng.IQService,
                private notify: Notify,
                private $sce:ng.ISCEService,
                private $window:ng.IWindowService,
                private semesterService: SemesterService,
                private facultyService: FacultyService,
                private admissionStudentService:AdmissionStudentService) {

      $scope.programTypes=appConstants.programType;

      $scope.getSemesters= this.getSemesters.bind(this);
      $scope.fetchTaletalkData = this.fetchTaletalkData.bind(this);

      this.getFaculties();
      this.getMeritListTypes();
    }

    private getMeritListTypes():void{
      this.$scope.meritTypes = [];
      this.$scope.meritTypes = this.appConstants.meritListTypes;
      this.$scope.meritTypes.splice(0,1);
    }

    private fetchTaletalkData():void{
      console.log(this.$scope.semester);
      this.admissionStudentService.fetchTaletalkData(this.$scope.semester.semesterId).then((students:Array<AdmissionStudent>)=>{
        if(students.length==0){
          this.$scope.showUploadPortion=false;
        }else{
          this.$scope.showUploadPortion=false;
          this.$scope.admissionStudents=[];
          this.$scope.admissionStudents=students;
        }
      });
    }

    private getSemesters(programType:number):void{
      console.log("program type");
      console.log(programType);
      this.semesterService.fetchSemesters(+programType,5).then((semesters:Array<Semester>)=>{
        this.$scope.semesters=semesters;
        console.log(this.$scope.semesters);
      });
    }



    private getFaculties(){
      this.facultyService.getAllFaculties().then((faculties:Array<Faculty>)=>{
        this.$scope.faculties=[];
        this.$scope.faculties=faculties;
      });
    }

  }

  UMS.controller("AdmissionMeritList",AdmissionMeritList);
}