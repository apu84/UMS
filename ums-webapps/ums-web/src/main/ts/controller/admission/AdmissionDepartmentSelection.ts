/**
 * Created by My Pc on 05-Jan-17.
 */

module ums{
  interface IAdmissionDepartmentSelection extends ng.IScope{
    deadLine:string;
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
    selectedStudent:AdmissionStudent;
    statistics:Array<AdmissionStudent>;
    statisticsMap:any;
    programs:Array<Program>;
    selectionPrograms:Array<Program>;
    selectedProgram:Program;
    waitingProgram:Program;
    programMap:any;
    departmentSelectionStatus:number;

    showReportSection:boolean;
    showStudentPortion:boolean;
    showSearch:boolean;
    disableSaveButton:boolean;

    showSearchBar:Function;
    saveAndRetrieveNext:Function;
    saveOnly:Function;
    getSemesters:Function;
    getAllStudents:Function;
    searchByReceiptId:Function;
    assignDeadline:Function;
    checkForSameSelectedPrograms:Function;
    enableReportSection:Function;
    enableDepartmentSelectionSection:Function;
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

    public static $inject = ['appConstants','HttpClient','$scope','$q','notify','$sce','$window','semesterService','facultyService', 'admissionStudentService','programService'];
    constructor(private appConstants: any,
                private httpClient: HttpClient,
                private $scope: IAdmissionDepartmentSelection,
                private $q:ng.IQService,
                private notify: Notify,
                private $sce:ng.ISCEService,
                private $window:ng.IWindowService,
                private semesterService: SemesterService,
                private facultyService: FacultyService,
                private admissionStudentService:AdmissionStudentService,
                private programService:ProgramService) {

      $scope.deadLine="";
      $scope.programTypes=appConstants.programType;
      $scope.programType = $scope.programTypes[0];
      $scope.showStudentPortion=false;
      $scope.showSearch = true;
      $scope.disableSaveButton=false;
      $scope.showReportSection = false;

      $scope.getSemesters= this.getSemesters.bind(this);
      $scope.getAllStudents = this.getAllStudents.bind(this);
      $scope.searchByReceiptId  = this.searchByReceiptId.bind(this);
      $scope.saveAndRetrieveNext = this.saveAndRetrieveNext.bind(this);
      $scope.showSearchBar = this.showSearchBar.bind(this);
      $scope.assignDeadline = this.assignDeadline.bind(this);
      $scope.checkForSameSelectedPrograms = this.checkForSameSelectedPrograms.bind(this);
      $scope.saveOnly = this.saveOnly.bind(this);
      $scope.enableReportSection = this.enableReportSection.bind(this);
      $scope.enableDepartmentSelectionSection = this.enableDepartmentSelectionSection.bind(this);
      $scope.receiptId="";

      this.getFaculties();
      this.getSemesters();
      this.getMeritListTypes();


    }

    private enableReportSection(){
        this.$scope.showReportSection=true;
    }

    private enableDepartmentSelectionSection(){
        this.$scope.showReportSection=false;
    }

    private assignDeadline(deadLine:string){
      this.$scope.deadLine=deadLine;
      console.log("Deadline");
      console.log(deadLine);
      console.log(this.$scope.deadLine);
    }

    private showSearchBar(){
      this.$scope.showSearch=true;
    }

    private getAllStudents(){
      Utils.expandRightDiv();

      var unit=this.getUnit();

      this.$scope.receiptIdMap={};
      this.admissionStudentService.fetchMeritList(this.$scope.semester.id,
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
        this.initializeSelect2("searchByReceiptId", this.$scope.admissionStudents,"Insert a Receipt ID");
        this.addDate();

      });

      this.getStatistics().then((statistics:any)=>{
        this.getPrograms();
      });
    }

    private getPrograms():void{
      this.programService.fetchProgram(+this.$scope.programType.id).then((programs:Array<Program>)=>{
        this.$scope.programs=[];
        this.$scope.selectionPrograms=[];
        var program:Program=<Program>{};
        program.id=0;
        program.shortName="Select A Program";
        this.$scope.programs.push(program);
        this.$scope.programMap={};
        console.log("---programs---");
        console.log(programs);
        for(var i=0;i<programs.length;i++){
          if(programs[i].id!=110200){
            this.$scope.programs.push(programs[i]);
            this.$scope.selectionPrograms.push(programs[i]);
            this.$scope.programMap[programs[i].id]=programs[i];
          }
        }
        /*this.$scope.waitingProgram=programs[0];
        this.getSelectedProgram();*/
      });
    }

    private getSelectedProgram(){

      for(var i=0;i<this.$scope.statistics.length;i++){
        if(this.$scope.statistics[i].remaining>0){
          this.$scope.selectedProgram=this.$scope.programMap[this.$scope.statistics[i].programId];
          break;
        }
      }
    }

    private getStatistics():ng.IPromise<any>{
      var defer =this.$q.defer();
      this.admissionStudentService.fetchStatistics(this.$scope.semester.id,
          +this.$scope.programType.id,
          +this.$scope.meritType.id,
          "ENGINEERING").then((students:Array<AdmissionStudent>)=>{
        this.$scope.statistics=[];
        this.$scope.statisticsMap={};
        for(var i=0;i<students.length;i++){
          this.$scope.statistics.push(students[i]);
          this.$scope.statisticsMap[students[i].programId]=students[i];
        }
        defer.resolve(this.$scope.statistics);
      });
      return defer.promise;
    }

    private removeProgramsFromSelectionList(programId:number){
      console.log("selection programs");
      console.log(this.$scope.selectionPrograms);
      for(var i=0;i<this.$scope.selectionPrograms.length;i++){
        if(this.$scope.selectionPrograms[i].id==programId){
          this.$scope.selectionPrograms.splice(i,1);
          break;
        }
      }
    }

    private searchByReceiptId(receiptId:any){
      console.log("Deadline");
      console.log(this.$scope.deadLine);

      this.admissionStudentService.fetchAdmissionStudentByReceiptId(this.$scope.semester.id,+this.$scope.programType.id, receiptId).then((student:AdmissionStudent)=>{
        console.log("Receipt id student");
        console.log(student);
        this.$scope.selectedStudent=<AdmissionStudent>{};
        this.$scope.selectedStudent=student;
        this.$scope.showStudentPortion=true;
        if(student.programIdByMerit!=null || student.programIdByTransfer!=null){
          if(student.deadline==null){
            this.$scope.deadLine="";
          }else{

            this.$scope.deadLine = student.deadline;
          }
        }


        this.assignSelectedAndWaitingProgram();
        //this.getSelectedProgram();
      });
    }

    private assignSelectedAndWaitingProgram() {

      if (this.$scope.selectedStudent.programIdByMerit != null) {
        this.$scope.selectedProgram = this.$scope.programMap[this.$scope.selectedStudent.programIdByMerit];
      } else {
        this.$scope.selectedProgram = this.$scope.programs[0];
      }

      if (this.$scope.selectedStudent.programIdByTransfer != null) {
        this.$scope.waitingProgram = this.$scope.programMap[this.$scope.selectedStudent.programIdByTransfer];
      } else {
        this.$scope.waitingProgram = this.$scope.programs[0];
      }
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

    private addDate():void{
      setTimeout(function () {
        $('.datepicker-default').datepicker();
        $('.datepicker-default').on('change', function () {
          $('.datepicker').hide();
        });
      }, 100);

    }

    private  initializeSelect2(selectBoxId,studentIds,placeHolderText){
      var data = studentIds;
      $("#"+selectBoxId).select2({
        minimumInputLength: 0,
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
        placeholder: placeHolderText
      });
    }


    private getMeritListTypes():void{
      this.$scope.meritTypes = [];
      this.$scope.meritTypes = this.appConstants.meritListTypes;
      this.$scope.meritType = this.$scope.meritTypes[1];
    }


    private getSemesters():void{
      this.semesterService.fetchSemesters(+this.$scope.programType.id,5, Utils.SEMESTER_FETCH_WITH_NEWLY_CREATED).then((semesters:Array<Semester>)=>{
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


    private checkForSameSelectedPrograms(){
      if(this.$scope.selectedProgram.id!=0 &&
          this.$scope.waitingProgram.id!=0 ){
        if(this.$scope.selectedProgram.id==this.$scope.waitingProgram.id){
          this.$scope.disableSaveButton=true;
          this.notify.error("Both Selected program and Waiting program can't be same");
        }else{
          this.$scope.disableSaveButton=false;
        }
      }else{
        this.$scope.disableSaveButton=false;
      }
      this.checkIfEmptyProgramIsSelected();
    }

    private checkIfEmptyProgramIsSelected(){
      if(this.$scope.statisticsMap[this.$scope.selectedProgram.id].remaining==0){
        this.$scope.disableSaveButton=true;
        this.notify.error("No seat remaining for the selected program");
      }
      else{
        this.$scope.disableSaveButton=false;
      }
    }

    private saveAndRetrieveNext(){
      if(this.$scope.selectedProgram.id!=0 && this.$scope.deadLine==""){
        this.notify.error("Deadline is not selected");
      }
      else{
        this.convertToJson().then((json)=>{

          this.admissionStudentService.saveAndFetchNextStudentForDepartmentSelection(this.$scope.departmentSelectionStatus, json).then((data)=>{
            this.$scope.selectedStudent = data[0];
            this.$scope.receiptId="";
            this.$scope.receiptId = this.$scope.selectedStudent.receiptId;
            $("#searchByReceiptId").val(this.$scope.receiptId).trigger("change");
            this.getStatistics();
            this.$scope.showSearch=false;
            this.initializeSelect2("searchByReceiptId",this.$scope.admissionStudents,"");
          });
        });
      }

    }

    private saveOnly(){
      if(this.$scope.selectedProgram.id!=0 && this.$scope.deadLine=="" || this.$scope.deadLine==null){
        this.notify.error("Deadline is not selected");
      }
      else{
        this.convertToJson().then((json)=>{

          this.admissionStudentService.saveAndFetchNextStudentForDepartmentSelection(this.$scope.departmentSelectionStatus, json).then((data)=>{
            this.searchByReceiptId(this.$scope.receiptId);
            this.getStatistics();
            this.$scope.showSearch=false;
            this.initializeSelect2("searchByReceiptId",this.$scope.admissionStudents,"");
          });
        });
      }
    }


    private convertToJson():ng.IPromise<any>{
      var defer = this.$q.defer();
      var completeJson={};
      var jsonObject = [];
      var students:AdmissionStudent = this.$scope.selectedStudent;

        var item:any = {};
        item['semesterId']=students.semesterId;
        item['meritSlNo']=students.meritSlNo;
        item['receiptId'] = students.receiptId;
        item['admissionRoll'] = students.admissionRoll;
        item['programType'] = +this.$scope.programType.id;
        item['unit'] = students.unit;
        item['quota']=students.quota;
        item['deadline'] = this.$scope.deadLine;
        if(this.$scope.selectedProgram.id!=0){
          item['programIdByMerit'] = this.$scope.selectedProgram.id;
        }
        if(this.$scope.waitingProgram.id!=0){
          item['programIdByTransfer'] = this.$scope.waitingProgram.id;
        }
        if(this.$scope.selectedProgram.id!=0 || this.$scope.waitingProgram.id!=0){
          item['presentStatus']=Utils.PRESENT;

          if(this.$scope.selectedProgram.id!=0 && this.$scope.waitingProgram.id==0){
            item['departmentSelectionType']=Utils.MERIT_PROGRAM_SELECTED;
            this.$scope.departmentSelectionStatus = Utils.MERIT_PROGRAM_SELECTED;
          }
          else if(this.$scope.selectedProgram.id!=0 && this.$scope.waitingProgram.id!=0){
            item['departmentSelectionType'] = Utils.MERIT_WAITING_PROGRAMS_SELECTED;
            this.$scope.departmentSelectionStatus = Utils.MERIT_WAITING_PROGRAMS_SELECTED
          }else{
            item['departmentSelectionType'] = Utils.WAITING_PROGRAM_SELECTED;
            this.$scope.departmentSelectionStatus = Utils.WAITING_PROGRAM_SELECTED;
          }
        }else{
          item['presentStatus']=Utils.ABSENT;
          this.$scope.departmentSelectionStatus=Utils.ABSENT;
        }


        jsonObject.push(item);

      completeJson["entries"] = jsonObject;
      defer.resolve(completeJson);
      return defer.promise;
    }

  }


  UMS.controller("AdmissionDepartmentSelection", AdmissionDepartmentSelection);
}