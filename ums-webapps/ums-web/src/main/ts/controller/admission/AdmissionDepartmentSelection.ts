/**
 * Created by My Pc on 05-Jan-17.
 */

module ums{
  interface IAdmissionDepartmentSelection extends ng.IScope{
    semesters:Array<Semester>;
    semester:Semester;
    programTypes:Array<IProgramType>;
    programType:IProgramType;
    faculty:Faculty;
    faculties:Array<Faculty>;
    meritTypes:Array<IMeritListType>;
    meritType:IMeritListType;
    admissionStudents:Array<AdmissionStudent>;
    admissionStudent:AdmissionStudent;
    receiptId:string;
    receiptIdMap:any;


    getSemesters:Function;
    getAllStudents:Function;
  }

  interface  IProgramType{
    id:string;
    name:string;
  }

  interface IMeritListType{
    id:string;
    name:string;
  }

  class AdmissionDepartmentSelection{

    public static $inject = ['appConstants','HttpClient','$scope','$q','notify','$sce','$window','semesterService','facultyService', 'admissionStudentService'];
    constructor(private appConstants: any,
                private httpClient: HttpClient,
                private $scope: IAdmissionDepartmentSelection,
                private $q:ng.IQService,
                private notify: Notify,
                private $sce:ng.ISCEService,
                private $window:ng.IWindowService,
                private semesterService: SemesterService,
                private facultyService: FacultyService,
                private admissionStudentService:AdmissionStudentService) {

      $scope.programTypes=appConstants.programType;
      $scope.programType = $scope.programTypes[0];

      $scope.getSemesters= this.getSemesters.bind(this);
      $scope.getAllStudents = this.getAllStudents.bind(this);
      $scope.receiptId="";

      this.getFaculties();
      this.getSemesters();
      this.getMeritListTypes();


    }


    private getAllStudents(){
      Utils.expandRightDiv();

      var unit=this.getUnit();

      this.$scope.receiptIdMap={};
      this.admissionStudentService.fetchTaletalkDataWithMeritType(this.$scope.semester.id,
          +this.$scope.programType.id,
          +this.$scope.meritType.id,
          unit)
          .then((students:Array<AdmissionStudent>)=>{

        this.$scope.admissionStudents=[];
        for(var i=0;i<students.length;i++){
          this.$scope.admissionStudents.push(students[i]);
          this.$scope.receiptIdMap[students[i].receiptId] = students[i];
        }

        console.log(students[1]);
        this.initializeSelect2("searchByReceiptId", this.$scope.admissionStudents);

      });
    }

    private getUnit():string{
      var unit:string="";
      if(this.$scope.faculty.shortName=='BUSINESS'){
        unit="BBA"
      }else{
        unit="ENGINEERING";
      }
      return unit;
    }



    private  initializeSelect2(selectBoxId,studentIds){
      var data = studentIds;
      $("#"+selectBoxId).select2({
        minimumInputLength: 1,
        query: function (options) {
          var pageSize = 100;
          var startIndex = (options.page - 1) * pageSize;
          var filteredData = data;
          if (options.term && options.term.length > 0) {
            if (!options.context) {
              var term = options.term.toLowerCase();
              options.context = data.filter(function (metric:any) {
                return ( metric.id.indexOf(term) !== -1 );
              });
            }
            filteredData = options.context;
          }
          options.callback({
            context: filteredData,
            results: filteredData.slice(startIndex, startIndex + pageSize),
            more: (startIndex + pageSize) < filteredData.length
          });
        },
        placeholder: "Select a Receipt Id"
      });
      // Her is the exmaple code for select2 with pagination.....
      //http://jsfiddle.net/Z7bDG/1/
    }


    private getMeritListTypes():void{
      this.$scope.meritTypes = [];
      this.$scope.meritTypes = this.appConstants.meritListTypes;
      this.$scope.meritType = this.$scope.meritTypes[1];
    }

    private getSemesters():void{
      this.semesterService.fetchSemesters(+this.$scope.programType.id,5).then((semesters:Array<Semester>)=>{
        this.$scope.semesters=semesters;
        for(var i=0;i<semesters.length;i++){
          if(semesters[i].status==2){
            this.$scope.semester = semesters[i];
            break;
          }
        }
      });
    }

    private getFaculties(){
      this.facultyService.getAllFaculties().then((faculties:Array<Faculty>)=>{
        this.$scope.faculties=[];
        for(var i=0;i<faculties.length;i++){
            this.$scope.faculties.push(faculties[i]);
        }
        this.$scope.faculty=faculties[0];

      });
    }

  }


  UMS.controller("AdmissionDepartmentSelection", AdmissionDepartmentSelection);
}