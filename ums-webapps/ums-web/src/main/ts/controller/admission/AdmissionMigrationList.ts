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
    deadline:string;
    meritTypes:Array<IMeritListType>;
    meritType:IMeritListType;

    showMainSection:boolean;
    searchSpinner:boolean;
    showSpinner:boolean;
    disableSaveButton:boolean;
    showFileUploadSection:boolean;
    showAddButton:boolean;


    fetchTaletalkData:Function;
    fetchMigrationData:Function;
    fetchExcelFormat:Function;
    processData:Function;
    saveData:Function;
    assignDeadline:Function;
    addData:Function;

  }


  interface  IProgramType{
    id:string;
    name:string;
  }


  interface IMeritListType{
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
      $scope.disableSaveButton=true;
      $scope.showFileUploadSection = false;
      $scope.showSpinner = false;
      $scope.showMainSection=false;
      $scope.showAddButton=false;
      $scope.migrationStudents=[];
      $scope.meritTypes = [];
      $scope.meritTypes = this.appConstants.meritListTypes;
      $scope.meritType = this.$scope.meritTypes[1];

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
          stretchH:'all',
          readOnly:true,
          height:$(".page-content").height()-5,
          observeChanges:true,
          search:true,
          columns:[
            {"title":"Receipt Id","data":"receiptId"},
            {"title":"Student Name","data":"studentName"},
            {"title":"Merit Sl No.","data":"meritSlNo"},
            {"title":"Quota","data":"quota"},
            {"title":"Unit","data":"unit"},
            {"title":"Migration Status", "data":"migrationDes"},
            {"title":"Admission Deadline", "data":"deadline"}]
        }

      };

      $scope.fetchTaletalkData = this.fetchTaletalkData.bind(this);
      $scope.fetchMigrationData = this.fetchMigrationData.bind(this);
      $scope.fetchExcelFormat = this.fetchExcelFormat.bind(this);
      $scope.processData = this.processData.bind(this);
      $scope.saveData = this.saveData.bind(this);
      $scope.assignDeadline=this.assignDeadline.bind(this);
      $scope.addData = this.addData.bind(this);

      this.getSemesters();


    }

    private getSemesters(){
      this.semesterService.fetchSemesters(+this.$scope.programType.id, 5, Utils.SEMESTER_FETCH_WITH_NEWLY_CREATED).then((semesters:Array<Semester>)=>{
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
      this.$scope.showMainSection=true;
      console.log("Show main section");
      console.log(this.$scope.showMainSection);
      this.$scope.modalData={};
      this.$scope.modalData="";
      this.$scope.searchSpinner=true;
      this.$scope.admissionStudents=[];
      this.$scope.admissionStudentMap={};

      this.admissionStudentService.fetchTaletalkData(this.$scope.semester.id, +this.$scope.programType.id).then((admissionStudents:Array<AdmissionStudent>)=>{

        for(var i=0;i<admissionStudents.length;i++){
          admissionStudents[i].quota=this.$scope.meritType.name;
          this.$scope.admissionStudents.push(admissionStudents[i]);
          this.$scope.admissionStudentMap[admissionStudents[i].receiptId] = admissionStudents[i];
        }

        this.fetchMigrationData();

      });
    }

    private addDate():void{
      setTimeout(function () {
        $('.datepicker-default').datepicker();
        $('.datepicker-default').on('change', function () {
          $('.datepicker').hide();
        });
      }, 100);

    }

    private addData(){
      this.$scope.showFileUploadSection = true;
      this.$scope.showAddButton=false;
    }


    private assignDeadline(deadLine:string){
      this.$scope.deadline=deadLine;
      console.log("###########");
      console.log(deadLine);


    }

    private fetchMigrationData(){
      this.$scope.migrationStudents=[];
      this.admissionStudentService.fetchMigrationData(this.$scope.semester.id, this.$scope.meritType.name).then((data)=>{
        this.$scope.migrationStudents = data;
        console.log("Fetched migration students");
        console.log(this.$scope.migrationStudents);
        this.configureViewSection();
        this.$scope.searchSpinner = false;
      });
    }

    private configureViewSection(){
      if(this.$scope.migrationStudents.length>0){
        this.$scope.showAddButton=true;
        this.$scope.showFileUploadSection=false;
        this.$scope.showSpinner=false;

      }else{
        this.$scope.showFileUploadSection = true;
      }
    }


    private fetchExcelFormat(){
      this.admissionStudentService.downloadMigrationListXlsFile();
    }

    private processData(modalData:any){
      console.log("modal data ----->");
      console.log(modalData);
      this.$scope.disableSaveButton=false;
      this.$scope.migrationStudents=[];
      this.$scope.searchSpinner=false;
      this.addDate();
      this.fillUpAdmissionStudents(modalData).then((students:Array<AdmissionStudent>)=>{
        console.log("Modal students");
        console.log(students);
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

      this.$scope.showSpinner=false;
      defer.resolve(this.$scope.admissionStudents);
      return defer.promise;
    }


    private insertDataIntoAdmissionStudents(cellData:Array<string>){

      var receiptId=cellData[0];
      console.log(receiptId);
      console.log("Cell data");
      console.log(cellData);
      var student:AdmissionStudent = this.$scope.admissionStudentMap[receiptId];
      console.log("Student from map");
      console.log(student);
      student.migrationStatus=Utils.MIGRATION_ABLE;
      student.migrationDes="Migration-able";
      this.$scope.migrationStudents.push(student);
      console.log(this.$scope.migrationStudents);
    }

    private saveData(){
      if(this.$scope.deadline=="" || this.$scope.deadline==null){
        this.notify.error("Deadline is not selected");
      }else{
        this.convertToJson().then((json)=>{
          this.admissionStudentService.saveMigrationData(json).then((message)=>{
            this.fetchMigrationData();
          });
        });
      }

    }

    private convertToJson():ng.IPromise<any>{
      var defer  = this.$q.defer();
      var completeJson = {};
      var jsonObject=[];
      console.log("deadline");
      console.log(this.$scope.deadline);
      var students:Array<AdmissionStudent> = this.$scope.migrationStudents;

      for(var i=0;i<students.length;i++){
        var item:any={};
        item['receiptId'] = students[i].receiptId;
        item['semesterId'] = this.$scope.semester.id;
        item['deadline'] = this.$scope.deadline;
        item['quota']=this.$scope.meritType.name;
        jsonObject.push(item);
      }

      completeJson['entries'] = jsonObject;
      defer.resolve(completeJson);
      return defer.promise;
    }

  }

  UMS.controller("AdmissionMigrationList", AdmissionMigrationList);
}