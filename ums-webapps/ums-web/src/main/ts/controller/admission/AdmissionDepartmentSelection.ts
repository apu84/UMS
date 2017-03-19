/**
 * Created by My Pc on 05-Jan-17.
 */

module ums{
  import IPromise = ng.IPromise;

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
    meritSerialNo:string;
    receiptIdMap:any;
    meritMap:any;
    selectedStudent:AdmissionStudent;
    statistics:Array<AdmissionStudent>;
    statisticsMap:any;
    programs:Array<Program>;
    selectionPrograms:Array<Program>;
    selectedProgram:Program;
    waitingProgram:Program;
    programMap:any;
    gridOpts:any;
    departmentSelectionStatus:number;
    fromMeritSerialNumber:number;
    toMeritSerialNumber:number;

    showModal:boolean;
    showReportSection:boolean;
    showStudentPortion:boolean;
    showSearch:boolean;
    disableSaveButton:boolean;
    focusSearch:boolean;
    focusMeritProgramSelection:boolean;
    focusWaitingProgramSelection:boolean;
    showSheetStyle:boolean;

    showSearchBar:Function;
    saveAndRetrieveNext:Function;
    saveOnly:Function;
    getSemesters:Function;
    getProgramsAndStatistics:Function;
    searchByMeritSerialNo:Function;
    assignDeadline:Function;
    checkForSameSelectedPrograms:Function;
    enableReportSection:Function;
    enableDepartmentSelectionSection:Function;
    focusOnSearchBar:Function;
    focusOnMeritProgramSelection:Function;
    focusOnWaitingProgramSelection:Function;
    saveWithChecking:Function;
    saveWithoutChecking: Function;
    showSheetLikeView:Function;
    showWindowedView:Function;
    submitMeritRange:Function;
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

    public static $inject = ['appConstants','HttpClient','$scope','$q','notify','$sce','$window','semesterService','facultyService', 'admissionStudentService','programService','$timeout'];
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
                private programService:ProgramService,
                private $timeout: ng.ITimeoutService) {

      $scope.gridOpts = {};
      $scope.showModal=true;
      $scope.fromMeritSerialNumber=0;
      $scope.toMeritSerialNumber=0;
      $scope.deadLine="";
      $scope.meritSerialNo="";
      $scope.programTypes=appConstants.programType;
      $scope.programType = $scope.programTypes[0];
      $scope.showStudentPortion=false;
      $scope.showSearch = true;
      $scope.disableSaveButton=false;
      $scope.showReportSection = false;
      $scope.focusSearch=false;
      $scope.focusMeritProgramSelection=false;
      $scope.focusWaitingProgramSelection=false;
      $scope.showSheetStyle=false;


      $scope.getSemesters= this.getSemesters.bind(this);
      $scope.getProgramsAndStatistics = this.getProgramsAndStatistics.bind(this);
      $scope.searchByMeritSerialNo= this.searchByMeritSerialNo.bind(this);
      $scope.saveAndRetrieveNext = this.saveAndRetrieveNext.bind(this);
      $scope.showSearchBar = this.showSearchBar.bind(this);
      $scope.assignDeadline = this.assignDeadline.bind(this);
      $scope.checkForSameSelectedPrograms = this.checkForSameSelectedPrograms.bind(this);
      $scope.saveOnly = this.saveOnly.bind(this);
      $scope.enableReportSection = this.enableReportSection.bind(this);
      $scope.enableDepartmentSelectionSection = this.enableDepartmentSelectionSection.bind(this);
      $scope.focusOnSearchBar = this.focusOnSearchBar.bind(this);
      $scope.focusOnMeritProgramSelection =  this.focusOnMeritProgramSelection.bind(this);
      $scope.focusOnWaitingProgramSelection = this.focusOnWaitingProgramSelection.bind(this);
      $scope.saveWithChecking= this.saveWithChecking.bind(this);
      $scope.saveWithoutChecking = this.saveWithoutChecking.bind(this);
      $scope.showWindowedView = this.showWindowedView.bind(this);
      $scope.showSheetLikeView = this.showSheetLikeView.bind(this);
      $scope.submitMeritRange = this.submitMeritRange.bind(this);
      $scope.receiptId="";

      this.getFaculties();
      this.getSemesters();
      this.getMeritListTypes();


    }

    private showSheetLikeView(){
      this.$scope.showSheetStyle=true;
      this.getStatistics();

    }

    private showWindowedView(){
      this.$scope.showSheetStyle=false;
    }

    private submitMeritRange(){
      console.log(this.$scope.fromMeritSerialNumber);
      console.log(this.$scope.toMeritSerialNumber);
      if(this.$scope.fromMeritSerialNumber==null || this.$scope.toMeritSerialNumber==null){
        this.admissionStudentService.fetchStudentsByMeritRange(this.$scope.semester.id, +this.$scope.meritType.id, this.$scope.fromMeritSerialNumber, this.$scope.toMeritSerialNumber).then(students=>{
          this.$scope.admissionStudents=[];
          for(var i=0;i<students.length;i++){
            students[i].selectedProgram=this.$scope.programMap[students[i].programIdByMerit];
            students[i].waitingProgram = this.$scope.programMap[students[i].programIdByTransfer];
            this.$scope.admissionStudents.push(students[i]);
          }
        });
      }else{
        this.notify.error("Select merit range properly");
      }
    }


    private focusOnSearchBar(){
      /*this.$scope.focusSearch=true;
      this.$scope.focusMeritProgramSelection=false;
      this.$scope.focusWaitingProgramSelection=false;*/
      this.$timeout(()=>{
        $("#searchBar").focus();
        $("#meritSerialNumberLabel").css("color","blue");
        $("#waitingProgramLabel").css("color","black");
        $("#selectedProgramLabel").css("color","black");
      },100);

    }

    private focusOnMeritProgramSelection(){
      this.$scope.focusSearch=false;
      this.$scope.focusMeritProgramSelection=true;
      this.$scope.focusWaitingProgramSelection=false;
      this.$timeout(()=>{
        $("#selectedProgram").focus();
        $("#meritSerialNumberLabel").css("color","black");
        $("#waitingProgramLabel").css("color","black");
        $("#selectedProgramLabel").css("color","blue");

        console.log("IN the merit program");
      },100);

      console.log("In the merit program outer");

    }

    private focusOnWaitingProgramSelection(){
      this.$timeout(()=>{
        $("#waitingProgram").focus();
        $("#meritSerialNumberLabel").css("color","black");
        $("#waitingProgramLabel").css("color","blue");
        $("#selectedProgramLabel").css("color","black");
        console.log("In the waiting program");
      },100);
      console.log("In the waiting program outer");

    }

    private enableReportSection(){
        this.$scope.showReportSection=true;
    }

    private enableDepartmentSelectionSection(){
        this.$scope.showReportSection=false;
    }

    private saveWithChecking(){
        this.$scope.showModal = true;
    }

    private saveWithoutChecking(){
      this.$scope.showModal = false;
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

    private getProgramsAndStatistics(){
      Utils.expandRightDiv();

      var unit=this.getUnit();
      this.getPrograms().then((programs)=>{
        this.getStatistics();
      })


    }


    private assignStudentsToMaps(students: Array<ums.AdmissionStudent>):IPromise<any> {
      var defer = this.$q.defer();
      this.$scope.admissionStudents = [];
      this.$scope.receiptIdMap = {};
      this.$scope.meritMap = {};
      for (var i = 0; i < students.length; i++) {
        students[i].text = String(students[i].meritSlNo);
        students[i].selectedProgram = this.$scope.programMap[students[i].programIdByMerit];
        students[i].waitingProgram = this.$scope.programMap[students[i].programIdByTransfer];
        this.$scope.admissionStudents.push(students[i]);
        this.$scope.receiptIdMap[students[i].receiptId] = students[i];
        this.$scope.meritMap[students[i].meritSlNo] = students[i];
      }
      defer.resolve(students);
      return defer.promise;
    }

    private getPrograms():ng.IPromise<any>{
      var defer = this.$q.defer();
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
        defer.resolve(this.$scope.programs);
      });

      return defer.promise;
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

    private searchByMeritSerialNo(meritSerialNo:any){
      console.log(meritSerialNo);
      if(meritSerialNo!="" || meritSerialNo!=null || String(meritSerialNo)!="0"){
        this.admissionStudentService.fetchAdmissionStudentByMeritSerialNo(this.$scope.semester.id,+this.$scope.meritType.id, +meritSerialNo).then((student:AdmissionStudent)=>{
          console.log("Receipt id student");
          console.log(student);
          this.$scope.selectedStudent=<AdmissionStudent>{};
          this.$scope.selectedStudent=student;
          this.$scope.showStudentPortion=true;

          this.$scope.selectedStudent.waitingProgram = this.$scope.programMap[student.programIdByTransfer];
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

    }

    private assignSelectedAndWaitingProgram() {

      if (this.$scope.selectedStudent.programIdByMerit != null) {
        this.$scope.selectedStudent.selectedProgram = this.$scope.programMap[this.$scope.selectedStudent.programIdByMerit];
      } else {
        this.$scope.selectedStudent.selectedProgram = this.$scope.programs[0];
      }

      if (this.$scope.selectedStudent.programIdByTransfer != null) {
        this.$scope.selectedStudent.waitingProgram = this.$scope.programMap[this.$scope.selectedStudent.programIdByTransfer];
      } else {
        this.$scope.selectedStudent.waitingProgram = this.$scope.programs[0];
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
      console.log("checking selected programs");
      console.log(this.$scope.selectedStudent.selectedProgram.shortName);
      if(this.$scope.selectedStudent.selectedProgram.id!=0 &&
          this.$scope.selectedStudent.waitingProgram.id!=0 ){
        if(this.$scope.selectedStudent.selectedProgram.id==this.$scope.selectedStudent.waitingProgram.id){
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
      if(this.$scope.statisticsMap[this.$scope.selectedStudent.selectedProgram.id].remaining==0){
        this.$scope.disableSaveButton=true;
        this.notify.error("No seat remaining for the selected program");
      }
      else{
        this.$scope.disableSaveButton=false;
      }
    }

    private saveAndRetrieveNext(){
      if( this.$scope.deadLine==""){
        this.notify.error("Deadline is not selected");
      }
      else{
        this.convertToJson().then((json)=>{

          this.admissionStudentService.saveAndFetchNextStudentForDepartmentSelection(this.$scope.departmentSelectionStatus, json).then((data)=>{
            this.$scope.selectedStudent = data[0];
            this.$scope.receiptId="";
            this.$scope.receiptId = this.$scope.selectedStudent.receiptId;
            this.$scope.meritSerialNo = String(this.$scope.selectedStudent.meritSlNo);
            if(this.$scope.selectedStudent.programIdByMerit==null){
              this.$scope.selectedStudent.selectedProgram=this.$scope.programs[0];
            }
            if(this.$scope.selectedStudent.programIdByTransfer==null){
              this.$scope.selectedStudent.waitingProgram = this.$scope.programs[0];
            }
            //$("#searchByReceiptId").val(this.$scope.receiptId).trigger("change");
            this.getStatistics();
            this.$scope.showSearch=false;
            //this.initializeSelect2("searchByReceiptId",this.$scope.admissionStudents,"");
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
            this.searchByMeritSerialNo(this.$scope.meritSerialNo);
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
      console.log("Saving students");
      console.log(students);

        var item:any = {};
        item['semesterId']=students.semesterId;
        item['meritSlNo']=students.meritSlNo;
        item['receiptId'] = students.receiptId;
        item['admissionRoll'] = students.admissionRoll;
        item['programType'] = +this.$scope.programType.id;
        item['unit'] = students.unit;
        item['quota']=students.quota;
        item['deadline'] = this.$scope.deadLine;
        if(students.selectedProgram.id!=0){
          item['programIdByMerit'] = students.selectedProgram.id;
        }
        if(students.waitingProgram.id!=0){
          item['programIdByTransfer'] = students.waitingProgram.id;
        }
        if(this.$scope.selectedStudent.selectedProgram.id!=0 || this.$scope.selectedStudent.waitingProgram.id!=0){
          item['presentStatus']=Utils.PRESENT;

          if(this.$scope.selectedStudent.selectedProgram.id!=0 && this.$scope.selectedStudent.waitingProgram.id==0){
            item['departmentSelectionType']=Utils.MERIT_PROGRAM_SELECTED;
            this.$scope.departmentSelectionStatus = Utils.MERIT_PROGRAM_SELECTED;
          }
          else if(this.$scope.selectedStudent.selectedProgram.id!=0 && this.$scope.selectedStudent.waitingProgram.id!=0){
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