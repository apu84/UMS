module ums{
  interface IStudentAdviser extends ng.IScope{
    selectedOptionTitle:string;
    showAdviser:boolean;
    enableSaveButton:Function;
    saveSingleStudentAdvisor:Function;
    saveAdvisorForBulkStudents:Function;
    showLoader:boolean;
    shiftOptionSelected:boolean;
    showShiftUI:Function;
    changeOptionSelected:boolean;
    showSingleStudentUI:Function;
    bulkAssignmentOptionSelected:boolean;
    showBulkAssignmentUI:Function;
    setFirstAutoCompleteValue:Function;
    setSecondAutoCompleteValue:Function;
    panelHeading:string;
    teachers:Array<IEmployee>;
    teacherId:string;
    singleTeacher:any;
    bulkTeacher:any;
    selectedTeacher:any;
    fromTeacherId:string;
    toTeacherId:string;
    teacherIdWithTeacherMap:any;
    getActiveTeachers:Function;
    getStudentsOfTheTeacher:Function;
    getStudentsOfTheFirstTeacher:Function;
    getStudentsOfTheSecondTeacher:Function;
    students:Array<Student>;
    singleStudentId:string;
    singleStudent:Student;
    fromStudents:Array<Student>;
    toStudents:Array<Student>;
    shiftingStudents:Array<Student>;
    studentIds:Array<string>;
    addedBulkStudentMap:any;
    addedShiftStudentMap:any;
    fromStudentId:string;
    toStudentId:string;
    studentIdWithStudentMap:any;
    getActiveStudentsOfDept:Function;
    addAStudent:Function;
    addStudents:Function;
    viewStudentById:Function;
    viewStudentByIdAndName:Function;
    toggleAdvisorInfo:Function;
    clearBulkStudents:Function;
    bulkShowStudentName:boolean;
    bulkShowStudentId:boolean;
    shiftShowStudentName:boolean;
    shiftShowStudentId:boolean;
    showAdviserName:boolean;
    removeFromAddedList:boolean;
    removeFromBulkStudents:boolean;
    showStudentsByYearSemester:boolean;
    changeColor:Function;
    saveAdvisorShifting:Function;
    resetShiftStudentSelection:Function;
    viewStudentByYearSemester:Function;
    viewStudentWithoutYearSemester:Function;
    getExistingStudentsOfAdviser:Function;
    existingStudentsOfAdivser:Array<Student>;
    bulkSelectedStudents:Array<Student>;
    shiftSelectedStudents:Array<Student>;
    assignedStudentsOfTheAdviser:number;
    categorizedFormStudents:Array<ICategorizedStudents>;
    categorizedToStudents:Array<ICategorizedStudents>;
    initializeShiftingStudents:Function;
    searchSingleStudent:Function;
  }
  interface ICategorizedStudents{
    header:string;
    key:number;
    students:Array<Student>;
  }
  interface IEmployee{
    id:string;
    employeeName:string;
  }

  class StudentAdviser{
    private anchorPrefix=".btn.btn-xs.btn-default.";

    public static $inject = ['appConstants','HttpClient','$scope','$q','notify','semesterService','employeeService','studentService'];
    constructor(private appConstants: any, private httpClient: HttpClient, private $scope: IStudentAdviser,
                private $q:ng.IQService, private notify: Notify, private semesterService:SemesterService,
                private employeeService:EmployeeService,
                private studentService:StudentService) {

      $scope.showLoader = false;
      $scope.bulkShowStudentId=true;
      $scope.bulkShowStudentName = false;
      $scope.shiftShowStudentId=true;
      $scope.shiftShowStudentName = false;
      $scope.showAdviserName=false;
      $scope.shiftOptionSelected=false;
      $scope.changeOptionSelected=false;
      $scope.bulkAssignmentOptionSelected=false;
      $scope.showShiftUI=this.showShiftUI.bind(this);
      $scope.showSingleStudentUI = this.showSingleStudentUI.bind(this);
      $scope.showBulkAssignmentUI = this.showBulkAssignmentUI.bind(this);
      $scope.getActiveTeachers = this.getActiveTeachers.bind(this);
      $scope.getActiveStudentsOfDept = this.getActiveStudentsOfDept.bind(this);
      $scope.addAStudent = this.addAStudent.bind(this);
      $scope.removeFromAddedList = this.removeFromAddedList.bind(this);
      $scope.removeFromBulkStudents = this.removeFromBulkStudents.bind(this);
      $scope.addStudents = this.addStudents.bind(this);
      $scope.viewStudentById = this.viewStudentById.bind(this);
      $scope.viewStudentByIdAndName = this.viewStudentByIdAndName.bind(this);
      $scope.clearBulkStudents = this.clearBulkStudents.bind(this);
      $scope.saveSingleStudentAdvisor = this.saveSingleStudentAdvisor.bind(this);
      $scope.saveAdvisorForBulkStudents = this.saveAdvisorForBulkStudents.bind(this);
      $scope.getStudentsOfTheFirstTeacher=this.getStudentsOfTheFirstTeacher.bind(this);
      $scope.getStudentsOfTheSecondTeacher = this.getStudentsOfTheSecondTeacher.bind(this);
      $scope.changeColor = this.changeColor.bind(this);
      $scope.saveAdvisorShifting = this.saveAdvisorShifting.bind(this);
      $scope.resetShiftStudentSelection = this.resetShiftStudentSelection.bind(this);
      $scope.viewStudentByYearSemester = this.viewStudentByYearSemester.bind(this);
      $scope.viewStudentWithoutYearSemester = this.viewStudentWithoutYearSemester.bind(this);
      $scope.getExistingStudentsOfAdviser = this.getExistingStudentsOfAdviser.bind(this);
      $scope.toggleAdvisorInfo = this.toggleAdvisorInfo.bind(this);
      $scope.searchSingleStudent=this.searchSingleStudent.bind(this);
      $scope.initializeShiftingStudents=this.initializeShiftingStudents.bind(this);
      $scope.categorizedFormStudents=[];
      $scope.categorizedToStudents=[];

      $('.selectpicker').selectpicker({
        iconBase: 'fa',
        tickIcon: 'fa-check'
      });

      $("#shift").hide();
      $("#change").hide();
      $("#bulk").hide();
     this.initialize();
      setTimeout(this.$scope.showSingleStudentUI(),500);
    }

    private  initializeSelect2(selectBoxId,studentIds){
      var data = studentIds;
    $("#"+selectBoxId).select2({
      minimumInputLength: 2,
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
      placeholder: "Select a Student"
    });
      // Her is the exmaple code for select2 with pagination.....
      //http://jsfiddle.net/Z7bDG/1/
  }
    private showSingleStudentUI(){
      this.activateUI(1);
      this.resetMainSelections();
      this.setSelection("singleAnchor","singleIcon");
      this.$scope.selectedOptionTitle="View/Change Student's Advisor Information";
    }
    private showBulkAssignmentUI(){
      this.activateUI(2);
      this.resetMainSelections();
      this.setSelection("bulkAnchor","bulkIcon");
      this.$scope.selectedOptionTitle="Bulk Advisor Assignment";
    }
    private showShiftUI(){
      this.activateUI(3);
      this.resetMainSelections();
      this.setSelection("shiftingAnchor","shiftingIcon");
      this.$scope.selectedOptionTitle="Shift Students' Advisor Information";
      this.$scope.showStudentsByYearSemester=false;
    }
    private setSelection(icon1,icon2){
      $(this.anchorPrefix+icon1).css({"background-color":"black"});
      $(".fa."+icon2).css({"color":"white"});
    }
    private resetMainSelections(){
      $(this.anchorPrefix+"singleAnchor").css({"background-color":"white"});
      $(this.anchorPrefix+"bulkAnchor").css({"background-color":"white"});
      $(this.anchorPrefix+"shiftingAnchor").css({"background-color":"white"});
      $(".fa.singleIcon").css({"color":"black"});
      $(".fa.bulkIcon").css({"color":"black"});
      $(".fa.shiftingIcon").css({"color":"black"});
    }

    private resetSubSelection(type){
      if(type=="bulk_id_name"){
        $(this.anchorPrefix+"bulkStudentIdAnchor").css({"background-color":"white"});
        $(".fa.fa-user.bulkStudentIdIcon").css({"color":"black"});

        $(this.anchorPrefix+"bulkStudentNameAnchor").css({"background-color":"white"});
        $(".fa.fa-indent.bulkStudentNameIcon").css({"color":"black"});
      }
      else if(type=="shift_id_name"){
        $(this.anchorPrefix+"shiftStudentIdAnchor").css({"background-color":"white"});
        $(".fa.fa-user.shiftStudentIdIcon").css({"color":"black"});

        $(this.anchorPrefix+"shiftStudentNameAnchor").css({"background-color":"white"});
        $(".fa.fa-indent.shiftStudentNameIcon").css({"color":"black"});
      }
      else if(type=="year_semester"){
        $(this.anchorPrefix+"yearSemesterAnchor").css({"background-color":"white"});
        $(".fa-th.yearSemesterIcon").css({"color":"black"});

        $(this.anchorPrefix+"yearSemesterClearAnchor").css({"background-color":"white"});
        $(".fa-align-justify.yearSemesterClearIcon").css({"color":"black"});
      }
      else if(type=="advisor"){
        $(this.anchorPrefix+"showAdvisorAnchor").css({"background-color":"white"});
        $(".fa.fa-sa.showAdvisorIcon").css({"color":"black"});

        $(this.anchorPrefix+"hideAdvisorAnchor").css({"background-color":"white"});
        $(".fa.fa-ha.hideAdvisorIcon").css({"color":"black"});
      }

    }


    private activateUI(activateNumber:number){
      this.disableAllUI().then((message:string)=>{
        if(activateNumber==1){
          this.$scope.changeOptionSelected=true;
          $("#shift").hide();
          $("#change").show();
          $("#bulk").hide();
        }else if(activateNumber==2){
          this.$scope.bulkAssignmentOptionSelected=true;
          $("#shift").hide();
          $("#change").hide();
          $("#bulk").show();
        }else{
          this.$scope.shiftOptionSelected=true;
          $("#shift").show();
          $("#change").hide();
          $("#bulk").hide();
        }
      });

    }
    private initialize(){
      this.$scope.addedBulkStudentMap={};
      this.$scope.addedShiftStudentMap={};
      this.$scope.existingStudentsOfAdivser=[];
      this.$scope.bulkSelectedStudents=[];
      this.$scope.shiftSelectedStudents=[];
      this.$scope.selectedTeacher="";
      this.$scope.fromStudents=[];
      this.$scope.toStudents=[];
      this.$scope.fromTeacherId="";
      this.$scope.toTeacherId="";
      this.$scope.teacherId="";
      this.$scope.singleTeacher={};
    }

    private viewStudentById(prefix:string){
      if(prefix=="bulk") {
        this.$scope.bulkShowStudentId = true;
        this.$scope.bulkShowStudentName = false;
      }else if(prefix=="shift"){
        this.$scope.shiftShowStudentId = true;
        this.$scope.shiftShowStudentName = false;
        this.$scope.showStudentsByYearSemester=false;
      }
      this.resetSubSelection(prefix+"_id_name");
      this.setSelection(prefix+"StudentIdAnchor","fa-user."+prefix+"StudentIdIcon");
    }

    private viewStudentByIdAndName(prefix:string){
      if(prefix=="bulk") {
        this.$scope.bulkShowStudentId = false;
        this.$scope.bulkShowStudentName = true;
      }else if(prefix=="shift"){
        this.$scope.shiftShowStudentId = false;
        this.$scope.shiftShowStudentName = true;
        //this.$scope.showStudentsByYearSemester=false;
      }
      this.resetSubSelection(prefix+"_id_name");
      this.setSelection(prefix+"StudentNameAnchor","fa-indent."+prefix+"StudentNameIcon");
    }
    private viewStudentByYearSemester(){
      this.$scope.showStudentsByYearSemester = true;
      this.resetSubSelection("year_semester");
      this.setSelection("yearSemesterAnchor","fa-th.yearSemesterIcon");
    }
    private viewStudentWithoutYearSemester(){
      this.$scope.showStudentsByYearSemester = false;
      this.resetSubSelection("year_semester");
      this.setSelection("yearSemesterClearAnchor","fa-align-justify.yearSemesterClearIcon");
    }

    private toggleAdvisorInfo(action:string) {
      if (action == "show") {
        this.$scope.showAdviserName = true;
        this.resetSubSelection("advisor");
        this.setSelection("showAdvisorAnchor","fa-sa.showAdvisorIcon");
    }
      else {
        this.$scope.showAdviserName = false;
        this.resetSubSelection("advisor");
        this.setSelection("hideAdvisorAnchor","fa-ha.hideAdvisorIcon");
      }
    }

    private changeColor(student:Student){
      if(student.backgroundColor==Utils.SELECTED_COLOR)
        student.backgroundColor=Utils.DEFAULT_COLOR;
      else
        student.backgroundColor=Utils.SELECTED_COLOR;
    }

    private saveSingleStudentAdvisor(){
      this.convertToJson([this.$scope.singleStudent],this.$scope.singleTeacher.id).then((jsonData)=>{
        this.studentService.updateStudentsAdviser(jsonData).then((data)=>{
          if(data=="success")
                    this.$scope.singleStudent.adviser=this.$scope.singleTeacher.id;
                    this.$scope.singleTeacher.id="";
        })
      });
    }
    private saveAdvisorForBulkStudents(){
      if(this.$scope.bulkTeacher.id==null){
        this.notify.error("Please select an advisor.");
        return;
      }
      this.convertToJson(this.$scope.bulkSelectedStudents,this.$scope.bulkTeacher.id).then((jsonData)=>{
        this.studentService.updateStudentsAdviser(jsonData).then((data)=>{
          if(data=="success"){
            for(var i=0;i<this.$scope.bulkSelectedStudents.length;i++)
              this.$scope.bulkSelectedStudents[i].adviser=this.$scope.bulkTeacher.id;
          }
          this.clearBulkStudents();
          this.getExistingStudentsOfAdviser(this.$scope.bulkTeacher.id);
        })
      });
    }


    private initializeShiftingStudents(teacherType:string):ng.IPromise<any>{
      var defer = this.$q.defer();
      if(teacherType=="from") {
        this.$scope.fromStudents = [];
        this.$scope.categorizedFormStudents = [];
      }
      else if(teacherType=="to") {
        this.$scope.toStudents=[];
        this.$scope.categorizedToStudents=[];
      }
      defer.resolve("success");
      return defer.promise;
    }

    private resetShiftStudentSelection(){
      for(var i=0;i<this.$scope.fromStudents.length;i++){
        if(this.$scope.fromStudents[i].backgroundColor==Utils.SELECTED_COLOR){
          this.$scope.fromStudents[i].backgroundColor=Utils.DEFAULT_COLOR;
        }
      }
    }


    private addFromStudentsAndAssignTeacher():ng.IPromise<any>{
      var defer = this.$q.defer();
      var shiftSelectedStudents=[];
      for(var i=0;i<this.$scope.fromStudents.length;i++){
        if(this.$scope.fromStudents[i].backgroundColor==Utils.SELECTED_COLOR){
          shiftSelectedStudents.push(this.$scope.fromStudents[i]);
        }
      }
      defer.resolve(shiftSelectedStudents);
      return defer.promise;
    }

    private removeFromAddedList(student:Student){
      for(var i=0;i<this.$scope.bulkSelectedStudents.length;i++){
        if(this.$scope.bulkSelectedStudents[i]==student){
          this.$scope.bulkSelectedStudents.splice(i,1);
        }
      }
    }
    private removeFromBulkStudents(student:Student){
      for(var i=0;i<this.$scope.bulkSelectedStudents.length;i++){
        if(this.$scope.bulkSelectedStudents[i]==student){
          this.$scope.addedBulkStudentMap[student.id]=null;
          this.$scope.bulkSelectedStudents.splice(i,1);
        }
      }
    }


    private getStudentsOfTheFirstTeacher(teacherId:string){
      if(teacherId==null || teacherId=="") return;
      this.$scope.fromTeacherId=teacherId;
      if(this.$scope.fromStudents.length==0){
        this.getStudentsOfTeacher(teacherId,1).then((students:Array<Student>)=>{
        });
      }else{
        this.initializeShiftingStudents("from").then((data)=>{
          this.getStudentsOfTeacher(teacherId,1).then((students:Array<Student>)=>{
          });
        });
      }
    }
    private insertIntoFromStudentsWithYearSemester(student:Student){
      var header:string = student.year+" Year,"+student.academicSemester+" Semester";
      var key= Number(String(student.year)+""+(String(student.academicSemester)));
      if(this.$scope.categorizedFormStudents.length==0){
        this.$scope.categorizedFormStudents.push(this.pushNewValueIntoCategorizedStudents(student,header, key));
      }
      else{
        var foundKey:boolean=false;
        for(var i=0;i<this.$scope.categorizedFormStudents.length;i++){
          if(this.$scope.categorizedFormStudents[i].key==key){
            this.pushStudentIntoExistingCategorizedStudents(student,this.$scope.categorizedFormStudents[i]);
            foundKey=true;
            break;
          }
        }
        if(foundKey==false){
          this.$scope.categorizedFormStudents.push(this.pushNewValueIntoCategorizedStudents(student,header,key));
        }
      }
      this.$scope.categorizedFormStudents.sort((a,b)=>{
        return Number(a.key)-Number(b.key);
      });

    }


    private pushNewValueIntoCategorizedStudents(student:Student,header,key):ICategorizedStudents{
      var categorizedFromStudents=<ICategorizedStudents>{} ;
      categorizedFromStudents.header=header;
      categorizedFromStudents.key=key;
      categorizedFromStudents.students=[];
      categorizedFromStudents.students.push(student);
      return categorizedFromStudents;
    }


    private pushStudentIntoExistingCategorizedStudents(student:Student,categorizedStudents:ICategorizedStudents){
      categorizedStudents.students.push(student);
    }

    private insertIntoToStudentsWithYearSemester(student:Student){
      var header:string = student.year+" Year,"+student.academicSemester+" Semester";
      var key= Number(String(student.year)+""+(String(student.academicSemester)));
      if(this.$scope.categorizedToStudents.length==0){
        this.$scope.categorizedToStudents.push(this.pushNewValueIntoCategorizedStudents(student,header, key));
      }
      else{
        var foundKey:boolean=false;
        for(var i=0;i<this.$scope.categorizedToStudents.length;i++){
          if(this.$scope.categorizedToStudents[i].key==key){
            this.pushStudentIntoExistingCategorizedStudents(student,this.$scope.categorizedToStudents[i]);
            foundKey=true;
            break;
          }

        }
        if(foundKey==false){
          this.$scope.categorizedToStudents.push(this.pushNewValueIntoCategorizedStudents(student,header,key)) ;
        }
      }

      this.$scope.categorizedToStudents.sort((a,b)=>{
        return Number(a.key)-Number(b.key);
      });
    }

    private getStudentsOfTeacher(teacherId:string,type:number):ng.IPromise<any>{
      var defer = this.$q.defer();
      this.studentService.getActiveStudentsOfTheTeacher(teacherId).then((students:Array<Student>)=>{
        this.$scope.assignedStudentsOfTheAdviser = angular.copy(students.length);

        for(var i=0;i<students.length;i++){
          students[i].backgroundColor=Utils.DEFAULT_COLOR;

          if(type==1){
            this.$scope.fromStudents.push(students[i]);
            this.insertIntoFromStudentsWithYearSemester(students[i]);
          }else if(type==2){
            this.$scope.toStudents.push(students[i]);
            this.insertIntoToStudentsWithYearSemester(students[i]);
          }
          else{
            this.$scope.existingStudentsOfAdivser.push(students[i]);
          }
        }
        defer.resolve(students);
      });
      return defer.promise;
    }

    private getStudentsOfTheSecondTeacher(teacherId:string){
      if(teacherId==null || teacherId=="") return;
      this.$scope.toTeacherId = teacherId;
      if(this.$scope.toStudents.length==0){
        this.getStudentsOfTeacher(teacherId,2).then((students:Array<Student>)=>{
        });
      }else{
        this.initializeShiftingStudents("to").then((data)=>{
          this.getStudentsOfTeacher(teacherId,2).then((students:Array<Student>)=>{
          });
        });
      }
    }

    private disableAllUI():ng.IPromise<any>{
      var defer= this.$q.defer();
      this.$scope.changeOptionSelected=false;
      this.$scope.bulkAssignmentOptionSelected=false;
      this.$scope.shiftOptionSelected=false;
      defer.resolve("done");
      return defer.promise;
    }

    private getActiveTeachers(){
      this.employeeService.getActiveTeacherByDept().then((teachers:Array<IEmployee>)=>{
        this.$scope.teachers=[];
        this.$scope.teacherIdWithTeacherMap={};
        for(var i=0;i<teachers.length;i++){
          this.$scope.teachers.push(teachers[i]);
          this.$scope.teacherIdWithTeacherMap[teachers[i].id] = teachers[i];
        }
      });
    }
    private getActiveStudentsOfDept(){
      this.studentService.getActiveStudentsByDepartment().then((students:Array<Student>)=>{
        this.$scope.students=[];
        this.$scope.studentIds=[];
        this.$scope.studentIdWithStudentMap={};
        for(var i=0;i<students.length;i++){
          students[i].backgroundColor=Utils.DEFAULT_COLOR;

          students[i].text=students[i].id;
          this.$scope.students.push(students[i]);
          this.$scope.studentIds.push(students[i].id);
          this.$scope.studentIdWithStudentMap[this.$scope.students[i].id] = this.$scope.students[i];
        }
        this.initializeSelect2("singleStudentList", this.$scope.students);
        this.initializeSelect2("fromStudentList", this.$scope.students);
        this.initializeSelect2("toStudentList", this.$scope.students);

        this.$scope.students = students;


      });
    }

    private searchSingleStudent(){
      this.$scope.singleStudent=this.$scope.studentIdWithStudentMap[this.$scope.singleStudentId];
    }

    private addAStudent(){
      var fromStudentId = this.$scope.fromStudentId;
      if(this.$scope.addedBulkStudentMap[fromStudentId]==null && this.$scope.studentIdWithStudentMap[fromStudentId]!=null){
        this.$scope.bulkSelectedStudents.push(this.$scope.studentIdWithStudentMap[fromStudentId]);
        this.$scope.addedBulkStudentMap[fromStudentId]='added';
      }
    }

    private addStudents(){
      this.$scope.showLoader=true;
      this.addStudentOfRange().then((data:any)=>{
        this.$scope.showLoader=false;
      });
    }

    private clearBulkStudents(){
      this.$scope.bulkSelectedStudents=[];
      this.$scope.addedBulkStudentMap={};
    }

    private addStudentOfRange():ng.IPromise<any>{
      var defer = this.$q.defer();
      for(var i=+this.$scope.fromStudentId;i<=+this.$scope.toStudentId;i++){
        if(this.$scope.studentIdWithStudentMap[i.toString()]!=null && this.$scope.addedBulkStudentMap[i.toString()]==null){
          this.$scope.bulkSelectedStudents.push(this.$scope.studentIdWithStudentMap[i.toString()]);
        }
      }
      defer.resolve('success');
      return defer.promise;
    }

    private saveAdvisorShifting(){

      this.addFromStudentsAndAssignTeacher().then((shiftSelectedStudents:Array<Student>)=>{
        this.$scope.shiftSelectedStudents=shiftSelectedStudents;
        if(this.$scope.shiftSelectedStudents.length>0){
          this.convertToJson(this.$scope.shiftSelectedStudents,this.$scope.toTeacherId).then((jsonData)=>{

            this.studentService.updateStudentsAdviser(jsonData).then((data)=>{
              if(data=="success") {
                for(var i=0;i<shiftSelectedStudents.length;i++) {
                  this.$scope.shiftSelectedStudents[i].adviser = this.$scope.toTeacherId;
                  this.$scope.studentIdWithStudentMap[this.$scope.shiftSelectedStudents[i].id] = this.$scope.shiftSelectedStudents[i];
                  this.searchSingleStudent();
                }

                this.initializeShiftingStudents("from").then((data)=> {
                  this.getStudentsOfTheFirstTeacher(this.$scope.fromTeacherId);
                });
                this.initializeShiftingStudents("to").then((data)=> {
                  this.getStudentsOfTheSecondTeacher(this.$scope.toTeacherId);
                });
              }
            });
          });
        }
      });
    }


    private initializeExistingStudentsOfAdviser():ng.IPromise<any>{
      var defer = this.$q.defer();
      this.$scope.existingStudentsOfAdivser=[];
      defer.resolve("success");
      return defer.promise;
    }

    private getExistingStudentsOfAdviser(teacherId:string){
      if(teacherId==null || teacherId=="") return;
      this.initializeExistingStudentsOfAdviser().then((data)=>{
        this.getStudentsOfTeacher(teacherId,3);
      });
    }
    private initializeTeachersStudentForBulkAssignment(){
      this.$scope.toStudents=[];
    }

    private convertToJson(studentArray:Array<Student>,teacherId:string):ng.IPromise<any>{
      var defer = this.$q.defer();
      var completeJson={};
      var jsonObject = [];
      for(var i=0;i<studentArray.length;i++){
        var item:any={};
        var student:Student = studentArray[i];
        item['id'] = student.id;
        item['adviser'] = teacherId;
        jsonObject.push(item);
      }

      completeJson["entries"] = jsonObject;
      defer.resolve(completeJson);
      return defer.promise;
    }
  }
  UMS.controller("StudentAdviser",StudentAdviser);
}