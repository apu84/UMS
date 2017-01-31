/**
 * Created by My Pc on 31-Jan-17.
 */

module ums{
  interface IAdmissionMigrationList extends ng.IScope{
    data:any;
    semesters:Array<Semester>;
    semester:Semester;
    programTypes:Array<IProgramType>;
    programType:IProgramType;
    admissionStudents:Array<AdmissionStudent>;
    migrationStudents:Array<AdmissionStudent>;
    admissionStudentMap:any;
    modalData:any;

    searchSpinner:boolean;
    showFileUploadSection:boolean;


    fetchTaletalkData:Function;
    fetchMigrationData:Function;
    fetchExcelFormat:Function;
    processData:Function;

  }


  interface  IProgramType{
    id:string;
    name:string;
  }


  class AdmissionMigrationList{

    public static $inject = ['appConstants','HttpClient','$scope','$q','notify','$sce','$window','semesterService','facultyService', 'admissionStudentService'];
    constructor(private appConstants: any,
                private httpClient: HttpClient,
                private $scope: IAdmissionMigrationList,
                private $q:ng.IQService,
                private notify: Notify,
                private $sce:ng.ISCEService,
                private $window:ng.IWindowService,
                private semesterService: SemesterService,
                private facultyService: FacultyService,
                private admissionStudentService:AdmissionStudentService) {


      $scope.programTypes = appConstants.programType;
      $scope.programType = $scope.programTypes[0];
      $scope.searchSpinner = false;
      $scope.showFileUploadSection = false;

      $scope.data = {
        settings:{
          colHeaders: true,
          rowHeaders: true,
          currentRowClassName: 'currentRow',
          currentColClassName: 'currentCol',
          fillHandle: false,
          manualRowResize:true,
          manualColumnResize:true,
          columnSorting:true,
          sortIndicator:true,
          readOnly:true,
          width:$(".page-content").width()-5,
          height:$(".page-content").height()-5,
          observeChanges:true,
          search:true,
          columns:[
            {"title":"Receipt Id","data":"receiptId"},
            {"title":"Student Name","data":"studentName"},
            {"title":"Quota","data":"quota"},
            {"title":"Unit","data":"unit"},
            {"title":"Migration Status", "data":"migrationDes"}]
        }

      };

      $scope.fetchTaletalkData = this.fetchTaletalkData.bind(this);
      $scope.fetchMigrationData = this.fetchMigrationData.bind(this);
      $scope.fetchExcelFormat = this.fetchExcelFormat.bind(this);
      $scope.processData = this.processData.bind(this);

      this.getSemesters();


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



    private fetchTaletalkData(){
      Utils.expandRightDiv();
      this.$scope.modalData={};
      this.$scope.searchSpinner=true;
      this.$scope.admissionStudents=[];
      this.$scope.admissionStudentMap={};

      this.admissionStudentService.fetchTaletalkData(this.$scope.semester.id, +this.$scope.programType.id).then((admissionStudents:Array<AdmissionStudent>)=>{

        for(var i=0;i<admissionStudents.length;i++){
          this.$scope.admissionStudents.push(admissionStudents[i]);
          this.$scope.admissionStudentMap[admissionStudents[i].receiptId] = admissionStudents[i];
        }
        this.$scope.migrationStudents = [];

        this.fetchMigrationData();

      });
    }


    private fetchMigrationData(){
      this.admissionStudentService.fetchMigrationData(this.$scope.semester.id).then((data)=>{
        this.$scope.migrationStudents = data;
        this.configureViewSection();
        this.$scope.searchSpinner = false;
      });
    }

    private configureViewSection(){
      if(this.$scope.migrationStudents.length>0){
        this.$scope.showFileUploadSection=false;
      }else{
        this.$scope.showFileUploadSection = true;
      }
    }


    private fetchExcelFormat(){
      this.admissionStudentService.downloadMigrationListXlsFile();
    }

    private processData(modalData:any){
      this.$scope.migrationStudents=[];
      this.$scope.searchSpinner=false;
      this.fillUpAdmissionStudents(modalData).then((students:Array<AdmissionStudent>)=>{
        this.$scope.showFileUploadSection=false;
      });
    }


    private fillUpAdmissionStudents(modalData:any):ng.IPromise<any>{

      var defer = this.$q.defer();

      var rows = modalData.split("\n");
      var counter:number=0;
      for(var r in rows){
        var cells = rows[r].split('\t');
        if(+r==0 && cells.length>1 || cells.length<1){
          this.notify.error("Wrong format, please paste the excel data again")
          break;
        }else{
          if(+r!=0){
            this.insertDataIntoAdmissionStudents(cells);
          }
        }

      }


      defer.resolve(this.$scope.admissionStudents);
      return defer.promise;
    }


    private insertDataIntoAdmissionStudents(cellData:Array<string>){

      var receiptId=cellData[0];
      var student:AdmissionStudent = this.$scope.admissionStudentMap[receiptId];
      student.migrationStatus=Utils.MIGRATION_ABLE;
      this.$scope.migrationStudents.push(student);
    }

  }

  UMS.controller("AdmissionMigrationList", AdmissionMigrationList);
}