module ums{
  interface IStudentAdviser extends ng.IScope{

    showSaveButton:boolean;
    enableSaveButton:Function;
    save:Function;
    showLoader:boolean;


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
    getActiveTeachers:Function;
    assignTeacherId:Function;


    students:Array<Student>;
    addedStudents:Array<Student>;
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



  }

  interface IStudent{
    id:string;
    fullName:string;
    departmentId:string;
    department:string;
    adviser:string;
    status:number;
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
      //this.enableSelectPicker();

      $('.selectpicker').selectpicker({
        iconBase: 'fa',
        tickIcon: 'fa-check'
      });
    }


    private viewStudentById(){
      this.$scope.showStudentId=true;
      this.$scope.showStudentName=false;
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

    }

    private removeFromAddedList(student:Student){
      for(var i=0;i<this.$scope.addedStudents.length;i++){
        if(this.$scope.addedStudents[i]==student){
          this.$scope.addedStudentIdMap[student.id]=null;
          this.$scope.addedStudents.splice(i,1);
        }
      }
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
      this.activateUI(1);
    }

    private showChangeUI(){
      this.activateUI(2);
    }

    private showBulkAssignmentUI(){
      //this.enableSelectPicker();
      this.initialize();
      this.activateUI(3);
    }



    private getActiveTeachers(){
      this.employeeService.getActiveTeacherByDept().then((teachers:Array<IEmployee>)=>{
        this.$scope.teachers=[];
        console.log(teachers);
        this.$scope.teachers = teachers;
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
      this.$scope.fromStudentId = fromStudentId


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
      if(this.$scope.teacherId!=null){
        this.convertToJson().then((jsonData)=>{
          this.studentService.updateStudentsAdviser(jsonData).then((data)=>{
              this.initialize();
          })
        });
      }else{
        this.notify.warn("Adviser is not selected");
      }
    }

    private initialize(){
      this.$scope.addedStudents=[];
      this.$scope.addedStudentIdMap={};
      /*this.$scope.fromStudentId=null;
      this.$scope.toStudentId=null;*/
      this.$scope.selectedTeacher={};
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