module ums{
  interface IStudentAdviser extends ng.IScope{

    selectedOptionTitle:string;
    showSaveButton:boolean;
    showAdviser:boolean;
    enableSaveButton:Function;
    save:Function;
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
    selectedTeacher:any;
    fromTeacherId:string;
    toTeacherId:string;
    teacherIdWithTeacherMap:any;
    getActiveTeachers:Function;
    assignTeacherId:Function;
    getStudentsOfTheTeacher:Function;
    getStudentsOfTheFirstTeacher:Function;
    getStudentsOfTheSecondTeacher:Function;


    students:Array<Student>;
    addedStudents:Array<Student>;
    fromStudents:Array<Student>;
    toStudents:Array<Student>;
    studentIds:Array<string>;
    studentIdsExt :Array<string>;
    addedStudentIdMap:any;
    autoCompleteResult:string;
    fromStudent:any;
    toStudent:Student;
    fromStudentId:string;
    setFromStudentId:Function;
    toStudentId:string;
    setToStudentId:Function;
    studentIdWithStudentMap:any;
    getActiveStudentsOfDept:Function;
    addAStudent:Function;
    addStudents:Function;
    viewStudentById:Function;
    viewStudentByIdAndName:Function;
    viewStudentByIdAndAdviser:Function;
    clearAddedStudents:Function;
    showStudentName:boolean;
    showStudentId:boolean;
    showAdviserName:boolean;
    removeFromAddedList:boolean;
    showStudentsByYearSemester:boolean;
    changeColor:Function;
    addAndSave:Function;
    cancelAddAndSave:Function;
    viewStudentByYearSemester:Function;
    viewStudentWithoutYearSemester:Function;
    getExistingStudentsOfAdviser:Function;
    existingStudetsOfAdivser:Array<Student>;
    assignedStudentsOfTheAdviser:number;
    yearSemesterMapForFromStudents:any;   //probably not needed
    yearSemestermapForToStudents:any;     // probably not needed
    categorizedFormStudents:Array<ICategorizedStudents>;
    categorizedToStudents:Array<ICategorizedStudents>;




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
    public static $inject = ['appConstants','HttpClient','$scope','$q','notify','semesterService','employeeService','studentService'];
    constructor(private appConstants: any, private httpClient: HttpClient, private $scope: IStudentAdviser,
                private $q:ng.IQService, private notify: Notify, private semesterService:SemesterService,
                private employeeService:EmployeeService,
                private studentService:StudentService) {

      $scope.fromStudent={};
      $scope.showLoader = false;
      $scope.showStudentId=true;
      $scope.showStudentName = false;
      $scope.showAdviserName=false;
      $scope.showSaveButton=false;
      $scope.shiftOptionSelected=false;
      $scope.changeOptionSelected=false;
      $scope.bulkAssignmentOptionSelected=false;
      $scope.showShiftUI=this.showShiftUI.bind(this);
      $scope.showSingleStudentUI = this.showSingleStudentUI.bind(this);
      $scope.showBulkAssignmentUI = this.showBulkAssignmentUI.bind(this);
      $scope.getActiveTeachers = this.getActiveTeachers.bind(this);
      $scope.getActiveStudentsOfDept = this.getActiveStudentsOfDept.bind(this);
      $scope.addAStudent = this.addAStudent.bind(this);
      $scope.setFromStudentId = this.setFromStudentId.bind(this);
      $scope.setToStudentId = this.setToStudentId.bind(this);
      $scope.setFirstAutoCompleteValue = this.setFirstAutoCompleteValue.bind(this);
      $scope.setSecondAutoCompleteValue = this.setSecondAutoCompleteValue.bind(this);
      $scope.removeFromAddedList = this.removeFromAddedList.bind(this);
      $scope.addStudents = this.addStudents.bind(this);
      $scope.viewStudentById = this.viewStudentById.bind(this);
      $scope.viewStudentByIdAndName = this.viewStudentByIdAndName.bind(this);
      $scope.enableSaveButton = this.enableSaveButton.bind(this);
      $scope.clearAddedStudents = this.clearAddedStudents.bind(this);
      $scope.save = this.save.bind(this);
      $scope.assignTeacherId = this.assignTeacherId.bind(this);
      $scope.getStudentsOfTheFirstTeacher=this.getStudentsOfTheFirstTeacher.bind(this);
      $scope.getStudentsOfTheSecondTeacher = this.getStudentsOfTheSecondTeacher.bind(this);
      $scope.changeColor = this.changeColor.bind(this);
      $scope.addAndSave = this.addAndSave.bind(this);
      $scope.cancelAddAndSave = this.cancelAddAndSave.bind(this);
      $scope.viewStudentByYearSemester = this.viewStudentByYearSemester.bind(this);
      $scope.viewStudentWithoutYearSemester = this.viewStudentWithoutYearSemester.bind(this);
      $scope.getExistingStudentsOfAdviser = this.getExistingStudentsOfAdviser.bind(this);
      $scope.viewStudentByIdAndAdviser = this.viewStudentByIdAndAdviser.bind(this);

      $('.selectpicker').selectpicker({
        iconBase: 'fa',
        tickIcon: 'fa-check'
      });

      $("#shift").hide();
      $("#change").hide();
      $("#bulk").hide();

      setTimeout(this.$scope.showSingleStudentUI(),2000);
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
      this.initializeFromAndToStudents(3);
      this.setSelection("shiftingAnchor","shiftingIcon");
      this.$scope.selectedOptionTitle="Shift Students' Advisor Information";
      this.$scope.showStudentsByYearSemester=false;
    }
    private setSelection(icon1,icon2){
      $("#"+icon1).css({"background-color":"black"});
      $("#"+icon2).css({"color":"white"});
    }
    private resetMainSelections(){
      $("#singleAnchor").css({"background-color":"white"});
      $("#bulkAnchor").css({"background-color":"white"});
      $("#shiftingAnchor").css({"background-color":"white"});
      $("#singleIcon").css({"color":"black"});
      $("#bulkIcon").css({"color":"black"});
      $("#shiftingIcon").css({"color":"black"});
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
      this.initialize();
    }
    private initialize(){
      this.$scope.existingStudetsOfAdivser=[];
      this.$scope.addedStudents=[];
      this.$scope.addedStudentIdMap={};
      this.$scope.selectedTeacher="";
      this.$scope.fromStudents=[];
      this.$scope.toStudents=[];
      this.$scope.fromTeacherId="";
      this.$scope.toTeacherId="";
      this.$scope.teacherId="";
      this.initializeFromAndToStudents(3);
    }

    private addAndSave(){

      this.addFromStudentsAndAssignTeacher().then((addedStudents:Array<Student>)=>{

        if(this.$scope.addedStudents.length>0){
          this.convertToJson().then((data)=>{
            this.studentService.updateStudentsAdviser(data);
            this.initializeFromAndToStudents(3).then((data)=>{
              this.getStudentsOfTheFirstTeacher(this.$scope.fromTeacherId);
              this.getStudentsOfTheSecondTeacher(this.$scope.toTeacherId);
            });

          });
        }
      });
    }

    private initializeFromAndToStudents(type:number):ng.IPromise<any>{

      //type 1 indicates : first teacher's students
      //type 2 indicates : second teacher's students
      //type 3 is for global, that is, it will initialize all.
      var defer = this.$q.defer();
      this.$scope.fromStudents=[];
      this.$scope.toStudents=[];
      this.$scope.addedStudents=[];
      this.$scope.yearSemesterMapForFromStudents={};
      this.$scope.yearSemestermapForToStudents={};
      this.$scope.categorizedFormStudents=[];
      this.$scope.categorizedToStudents=[];

      defer.resolve("success");
      return defer.promise;
    }

    private cancelAddAndSave(){
      for(var i=0;i<this.$scope.fromStudents.length;i++){
        if(this.$scope.fromStudents[i].backgroundColor=="YELLOW"){
          this.$scope.fromStudents[i].backgroundColor="#DEF";
        }
      }
    }


    private addFromStudentsAndAssignTeacher():ng.IPromise<any>{
      var defer = this.$q.defer();
      for(var i=0;i<this.$scope.fromStudents.length;i++){
        if(this.$scope.fromStudents[i].backgroundColor=="YELLOW"){
          this.$scope.addedStudents.push(this.$scope.fromStudents[i]);
          this.$scope.toStudents.push(this.$scope.fromStudents[i]);
        }
      }

      defer.resolve(this.$scope.fromStudents);
      this.$scope.teacherId = this.$scope.toTeacherId;
      return defer.promise;
    }

    private viewStudentById(){
      this.$scope.showStudentId=true;
      this.$scope.showStudentName=false;
      this.$scope.showStudentsByYearSemester;
      this.$scope.showAdviserName=false;
    }

    private viewStudentByIdAndName(){
      this.$scope.showStudentName=true;
      this.$scope.showStudentId=false;
      this.$scope.showAdviserName=false;
    }

    private viewStudentByIdAndAdviser(){
      this.$scope.showAdviserName = true;
      this.$scope.showStudentId=false;
      this.$scope.showStudentName=false;
    }

    private assignTeacherId(teacher:any){
      this.$scope.teacherId = teacher;
    }




    private removeFromAddedList(student:Student){
      for(var i=0;i<this.$scope.addedStudents.length;i++){
        if(this.$scope.addedStudents[i]==student){
          this.$scope.addedStudentIdMap[student.id]=null;
          this.$scope.addedStudents.splice(i,1);
        }
      }
    }


    private changeColor(student:Student){
      student.backgroundColor="YELLOW";
    }

    private getStudentsOfTheFirstTeacher(teacherId:string){
      this.$scope.fromTeacherId=teacherId;

      if(this.$scope.fromStudents.length==0){
        this.getStudentsOfATeacher(teacherId,1).then((students:Array<Student>)=>{

        });
      }else{
        this.initializeFromAndToStudents(1).then((data)=>{
          this.getStudentsOfATeacher(teacherId,1).then((students:Array<Student>)=>{
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

    private getStudentsOfATeacher(teacherId:string,type:number):ng.IPromise<any>{
      var defer = this.$q.defer();
      this.studentService.getActiveStudentsOfTheTeacher(teacherId).then((students:Array<Student>)=>{
        this.$scope.assignedStudentsOfTheAdviser = angular.copy(students.length);

        for(var i=0;i<students.length;i++){
          students[i].backgroundColor="#DEF";

          if(type==1){
            this.$scope.fromStudents.push(students[i]);
            this.insertIntoFromStudentsWithYearSemester(students[i]);
          }else if(type==2){
            this.$scope.toStudents.push(students[i]);
            this.insertIntoToStudentsWithYearSemester(students[i]);
          }
          else{
            this.$scope.existingStudetsOfAdivser.push(students[i]);
          }
        }



        defer.resolve(students);
      });

      return defer.promise;

    }

    private getStudentsOfTheSecondTeacher(teacherId:string){
      this.$scope.toTeacherId = teacherId;
      if(this.$scope.toStudents.length==0){
        this.getStudentsOfATeacher(teacherId,2).then((students:Array<Student>)=>{

        });
      }else{
        this.initializeFromAndToStudents(2).then((data)=>{
          this.getStudentsOfATeacher(teacherId,2).then((students:Array<Student>)=>{

          });
        });
      }
    }

    private viewStudentByYearSemester(){
      this.$scope.showStudentsByYearSemester = true;

    }

    private viewStudentWithoutYearSemester(){
      this.$scope.showStudentsByYearSemester = false;
    }


    private disableAllUI():ng.IPromise<any>{
      var defer= this.$q.defer();
      this.$scope.shiftOptionSelected=false;
      this.$scope.changeOptionSelected=false;
      this.$scope.bulkAssignmentOptionSelected=false;

      defer.resolve("done");
      return defer.promise;
    }





    private getActiveTeachers(){
      this.employeeService.getActiveTeacherByDept().then((teachers:Array<IEmployee>)=>{
        this.$scope.teachers=[];
        this.$scope.teacherIdWithTeacherMap={};
        //this.$scope.teachers = teachers;
        for(var i=0;i<teachers.length;i++){
          this.$scope.teachers.push(teachers[i]);
          this.$scope.teacherIdWithTeacherMap[teachers[i].id] = teachers[i];
        }
      });
    }

    private setFromStudentId(student:Student){
      this.$scope.fromStudentId=student.id;
    }

    private setToStudentId(student:Student){
      this.$scope.toStudentId = student.id;
    }

    private getActiveStudentsOfDept(){
      this.studentService.getActiveStudentsByDepartment().then((students:Array<Student>)=>{
        this.$scope.students=[];
        this.$scope.studentIds=[];
        this.$scope.studentIdsExt=[];
        this.$scope.studentIdWithStudentMap={};
        for(var i=0;i<students.length;i++){
          students[i].backgroundColor="#DEF";
          this.$scope.students.push(students[i]);
          this.$scope.studentIds.push(students[i].id);
          this.$scope.studentIdsExt.push(students[i].id);
          this.$scope.studentIdWithStudentMap[this.$scope.students[i].id] = this.$scope.students[i];
        }

        this.$scope.students = students;


      });
    }

    private addAStudent(){
      //this.enableSaveButton();
      var fromStudentId = this.$scope.fromStudentId;
      if(this.$scope.addedStudentIdMap[fromStudentId]==null){
        this.$scope.addedStudents.push(this.$scope.studentIdWithStudentMap[fromStudentId]);
        this.$scope.addedStudentIdMap[fromStudentId]='added';
      }
      else{
        //do nothing
      }
      this.$scope.$apply();
    }

    private addStudents(){

      this.$scope.showLoader=true;
      this.addStudentOfRange().then((data:any)=>{
        this.$scope.showLoader=false;
      });
      //this.enableSaveButton();




    }

    private clearAddedStudents(){
      this.$scope.addedStudents=[];
      this.$scope.addedStudentIdMap={};
    }

    private addStudentOfRange():ng.IPromise<any>{
      var defer = this.$q.defer();
      for(var i=+this.$scope.fromStudentId;i<=+this.$scope.toStudentId;i++){
        if(this.$scope.studentIdWithStudentMap[i.toString()]!=null && this.$scope.addedStudentIdMap[i.toString()]==null){
          this.$scope.addedStudents.push(this.$scope.studentIdWithStudentMap[i.toString()]);
          this.$scope.addedStudentIdMap[i.toString()]='added';
        }
      }

      defer.resolve('success');
      return defer.promise;
    }

    private setFirstAutoCompleteValue(fromStudentId:any){

      if(this.$scope.studentIdWithStudentMap[fromStudentId]!=null){
        this.$scope.fromStudentId = fromStudentId;


        if(this.$scope.changeOptionSelected){
          this.$scope.addedStudents=[];
          this.$scope.addedStudentIdMap={};
        }
      }else{
        this.notify.error("Not a valid student id");
      }



    }

    private setSecondAutoCompleteValue(fromStudentId:any){
      if(this.$scope.studentIdWithStudentMap[fromStudentId]!=null){
        this.$scope.toStudentId = fromStudentId
      }
      else{
        this.notify.error("Not a valid student id");
      }

    }

    private enableSaveButton(){

      if(this.$scope.addedStudents.length>0){
        this.$scope.showSaveButton=true;
      }

    }

    private save(){
      if(this.$scope.teacherId!=null && this.$scope.teacherId!="" && this.$scope.addedStudents.length>0){
        this.convertToJson().then((jsonData)=>{
          this.studentService.updateStudentsAdviser(jsonData).then((data)=>{
            this.initialize();
            this.initializeFromAndToStudents(3);
          })
        });
      }else{
        this.notify.warn("All fields are not selected");
      }
    }


    private initializeExistingStudentsOfAdviser():ng.IPromise<any>{
      var defer = this.$q.defer();
      this.$scope.existingStudetsOfAdivser=[];
      defer.resolve("success");
      return defer.promise;
    }

    private getExistingStudentsOfAdviser(teacherId:string){
      this.initializeExistingStudentsOfAdviser().then((data)=>{
        this.getStudentsOfATeacher(teacherId,3);
      });
    }

    private initializeTeachersStudentForBulkAssignment(){
      this.$scope.toStudents=[];
    }

    private convertToJson():ng.IPromise<any>{
      var defer = this.$q.defer();
      var completeJson={};
      var jsonObject = [];


      for(var i=0;i<this.$scope.addedStudents.length;i++){
        var item:any={};
        var student:Student = this.$scope.addedStudents[i];
        item['id'] = student.id;
        item['fullName'] = student.fullName;
        item['departmentId'] = student.departmentId;
        item['semesterId'] = student.semesterId.toString();
        item['programId'] = student.programId;
        item['fatherName'] = student.fatherName;
        item['motherName'] = student.motherName;
        item['dateOfBirth'] = student.dateOfBirth;
        item['gender'] = student.gender;
        item['presentAddress'] = student.presentAddress;
        item['permanentAddress'] = student.permanentAddress;
        item['mobileNo'] = student.mobileNo;
        item['phoneNo'] = student.phoneNo;
        item['bloodGroup'] = student.bloodGroup;
        item['email'] = student.email;
        item['guardianName'] = student.guardianName;
        item['guardianMobileNo'] = student.guardianMobileNo;
        item['guardianPhoneNo'] = student.guardianPhoneNo;
        item['guardianEmail'] = student.guardianEmail;
        item['adviser'] = this.$scope.teacherId;
        this.$scope.addedStudents[i].adviser=this.$scope.teacherId;
        jsonObject.push(item);
      }

      completeJson["entries"] = jsonObject;
      defer.resolve(completeJson);
      return defer.promise;
    }


  }

  UMS.controller("StudentAdviser",StudentAdviser);
}