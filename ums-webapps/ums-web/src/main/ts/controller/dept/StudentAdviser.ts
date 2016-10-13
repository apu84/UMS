module ums{
  interface IStudentAdviser extends ng.IScope{

    showSaveButton:boolean;
    enableSaveButton:Function;
    save:Function;
    showLoader:boolean;
    showOneInputArea:boolean;


    shiftOptionSelected:boolean;
    showShiftUI:Function;

    changeOptionSelected:boolean;
    showChangeUI:Function;

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

    from11Student:Array<Student>;
    from12Student:Array<Student>;
    from21Student:Array<Student>;
    from22Student:Array<Student>;
    from31Student:Array<Student>;
    from32Student:Array<Student>;
    from41Student:Array<Student>;
    from42Student:Array<Student>;
    from51Student:Array<Student>;
    from52Student:Array<Student>;


    to11Student:Array<Student>;
    to12Student:Array<Student>;
    to21Student:Array<Student>;
    to22Student:Array<Student>;
    to31Student:Array<Student>;
    to32Student:Array<Student>;
    to41Student:Array<Student>;
    to42Student:Array<Student>;
    to51Student:Array<Student>;
    to52Student:Array<Student>;


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
      $scope.showSaveButton=false;
      $scope.shiftOptionSelected=false;
      $scope.showOneInputArea=false;
      $scope.changeOptionSelected=false;
      $scope.bulkAssignmentOptionSelected=false;
      $scope.showShiftUI=this.showShiftUI.bind(this);
      $scope.showChangeUI = this.showChangeUI.bind(this);
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
      //this.enableSelectPicker();

      $('.selectpicker').selectpicker({
        iconBase: 'fa',
        tickIcon: 'fa-check'
      });
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
      if(type==1){
        this.$scope.fromStudents=[];
        this.$scope.from11Student=[];
        this.$scope.from12Student=[];
        this.$scope.from31Student=[];
        this.$scope.from32Student=[];
        this.$scope.from41Student=[];
        this.$scope.from42Student=[];
        this.$scope.from21Student=[];
        this.$scope.from22Student=[];
        this.$scope.from51Student=[];
        this.$scope.from52Student=[];
      }
      else if (type==2){
        this.$scope.toStudents=[];
        this.$scope.to11Student=[];
        this.$scope.to12Student=[];
        this.$scope.to21Student=[];
        this.$scope.to22Student=[];
        this.$scope.to31Student=[];
        this.$scope.to32Student=[];
        this.$scope.to41Student=[];
        this.$scope.to42Student=[];
        this.$scope.to51Student=[];
        this.$scope.to52Student=[];
      }
      else{
        this.$scope.fromStudents=[];
        this.$scope.toStudents = [];

        this.$scope.from11Student=[];
        this.$scope.from12Student=[];
        this.$scope.from31Student=[];
        this.$scope.from32Student=[];
        this.$scope.from41Student=[];
        this.$scope.from42Student=[];
        this.$scope.from21Student=[];
        this.$scope.from22Student=[];
        this.$scope.from51Student=[];
        this.$scope.from52Student=[];

        this.$scope.to11Student=[];
        this.$scope.to12Student=[];
        this.$scope.to21Student=[];
        this.$scope.to22Student=[];
        this.$scope.to31Student=[];
        this.$scope.to32Student=[];
        this.$scope.to41Student=[];
        this.$scope.to42Student=[];
        this.$scope.to51Student=[];
        this.$scope.to52Student=[];
      }

      this.$scope.addedStudents=[];



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
    }

    private viewStudentByIdAndName(){
      this.$scope.showStudentName=true;
      this.$scope.showStudentId=false;
    }

    private assignTeacherId(teacher:any){
      this.$scope.teacherId = teacher;
      console.log("THis is new teacher id:");
      console.log(this.$scope.teacherId);
    }


    private activateUI(activateNumber:number){
      this.disableAllUI().then((message:string)=>{
        if(activateNumber===1){
          this.$scope.shiftOptionSelected=true;
        }else if(activateNumber===2){
          this.$scope.changeOptionSelected=true;

        }else{
          this.$scope.bulkAssignmentOptionSelected=true;
        }
      });

      this.initialize();

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
      if(student.year==1 && student.academicSemester==1){
        this.$scope.from11Student.push(student);
      }
      else if(student.year==1 && student.academicSemester==2){
        this.$scope.from12Student.push(student);
      }
      else if(student.year==2 && student.academicSemester==1){
        this.$scope.from21Student.push(student);
      }
      else if(student.year==2 && student.academicSemester==2){
        this.$scope.from22Student.push(student);
      }
      else if(student.year==3 && student.academicSemester==1){
        this.$scope.from31Student.push(student);
      }
      else if(student.year==3 && student.academicSemester==2){
        this.$scope.from32Student.push(student);
      }
      else if(student.year==4 && student.academicSemester==1){
        this.$scope.from41Student.push(student);
      }
      else if(student.year==4 && student.academicSemester==2){
        this.$scope.from42Student.push(student);
      }
      else if(student.year==5 && student.academicSemester==1){
        this.$scope.from51Student.push(student);
      }
      else{
        this.$scope.from52Student.push(student);
      }
    }



    private insertIntoToStudentsWithYearSemester(student:Student){
      if(student.year==1 && student.academicSemester==1){
        this.$scope.to11Student.push(student);
      }
      else if(student.year==1 && student.academicSemester==2){
        this.$scope.to12Student.push(student);
      }
      else if(student.year==2 && student.academicSemester==1){
        this.$scope.to21Student.push(student);
      }
      else if(student.year==2 && student.academicSemester==2){
        this.$scope.to22Student.push(student);
      }
      else if(student.year==3 && student.academicSemester==1){
        this.$scope.to31Student.push(student);
      }
      else if(student.year==3 && student.academicSemester==2){
        this.$scope.to32Student.push(student);
      }
      else if(student.year==4 && student.academicSemester==1){
        this.$scope.to41Student.push(student);
      }
      else if(student.year==4 && student.academicSemester==2){
        this.$scope.to42Student.push(student);
      }
      else if(student.year==5 && student.academicSemester==1){
        this.$scope.to51Student.push(student);
      }
      else{
        this.$scope.to52Student.push(student);
      }
    }

    private getStudentsOfATeacher(teacherId:string,type:number):ng.IPromise<any>{
      var defer = this.$q.defer();
      this.studentService.getActiveStudentsOfTheTeacher(teacherId).then((students:Array<Student>)=>{
        for(var i=0;i<students.length;i++){
          students[i].backgroundColor="#DEF";

          if(type==1){
            this.$scope.fromStudents.push(students[i]);
            this.insertIntoFromStudentsWithYearSemester(students[i]);
          }else{
            this.$scope.toStudents.push(students[i]);
            this.insertIntoToStudentsWithYearSemester(students[i]);
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

    private showShiftUI(){
      this.$scope.showStudentsByYearSemester=false;
      this.initializeFromAndToStudents(3);
      this.activateUI(1);
    }

    private showChangeUI(){
      this.$scope.showOneInputArea=true;
      this.activateUI(2);
    }

    private showBulkAssignmentUI(){
      //this.enableSelectPicker();
      this.$scope.showOneInputArea=false;
      this.initialize();
      this.activateUI(3);
    }



    private getActiveTeachers(){
      this.employeeService.getActiveTeacherByDept().then((teachers:Array<IEmployee>)=>{
        this.$scope.teachers=[];
        console.log(teachers);
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
        //console.log(students);
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
        this.notify.warn("Already added!");
      }
      this.$scope.$apply();
    }

    private addStudents(){
      console.log("** in the addStudents() **");
      console.log("showing loader");
      this.$scope.showLoader=true;
      this.addStudentOfRange().then((data:any)=>{
        this.$scope.showLoader=false;
        console.log(this.$scope.showLoader);
      });
      //this.enableSaveButton();


      console.log("Added students");
      console.log(this.$scope.addedStudents);
      //this.$scope.$apply();

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
      this.$scope.fromStudentId = fromStudentId;

      if(this.$scope.showChangeUI){
        this.$scope.addedStudents=[];
      }


    }

    private setSecondAutoCompleteValue(fromStudentId:any){
      this.$scope.toStudentId = fromStudentId

    }

    private enableSaveButton(){
      console.log("I am in the enable save section");
      console.log("teacher id--->"+this.$scope.teacherId);
      if(this.$scope.addedStudents.length>0){
        this.$scope.showSaveButton=true;
      }

      console.log(this.$scope.showSaveButton);
    }

    private save(){
      if(this.$scope.teacherId!=null && this.$scope.teacherId!="" && this.$scope.addedStudents.length>0){
        this.convertToJson().then((jsonData)=>{
          this.studentService.updateStudentsAdviser(jsonData).then((data)=>{
              this.initialize();
          })
        });
      }else{
        this.notify.warn("All fields are not selected");
      }
    }

    private initialize(){
      this.$scope.addedStudents=[];
      this.$scope.addedStudentIdMap={};
      /*this.$scope.fromStudentId=null;
      this.$scope.toStudentId=null;*/
      this.$scope.selectedTeacher="";
      this.$scope.fromStudents=[];
      this.$scope.toStudents=[];
      this.$scope.fromTeacherId="";
      this.$scope.toTeacherId="";
      this.$scope.teacherId="";
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

      console.log(jsonObject);
      completeJson["entries"] = jsonObject;
      defer.resolve(completeJson);
      return defer.promise;
    }


  }

  UMS.controller("StudentAdviser",StudentAdviser);
}