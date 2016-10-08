module ums{
  interface IStudentAdviser extends ng.IScope{

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
    getActiveTeachers:Function;


    students:Array<IStudent>;
    addedStudents:Array<IStudent>;
    studentIds:Array<string>;
    studentIdsExt :Array<string>;
    addedStudentIdMap:any;
    autoCompleteResult:string;
    fromStudent:any;
    toStudent:IStudent;
    fromStudentId:string;
    setFromStudentId:Function;
    toStudentId:string;
    setToStudentId:Function;
    studentIdWithStudentMap:any;
    getActiveStudentsOfDept:Function;
    addAStudent:Function;
    addStudents:Function;
    showStudentName:boolean;
    showAdviserName:boolean;
    removeFromAddedList:boolean;

  }

  interface IStudent{
    id:string;
    fullName:string;
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
      //this.enableSelectPicker();

      $('.selectpicker').selectpicker({
        iconBase: 'fa',
        tickIcon: 'fa-check'
      });
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

    private removeFromAddedList(student:IStudent){
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
      this.$scope.addedStudentIdMap={};
      this.$scope.addedStudents=[];
      this.activateUI(3);
    }



    private getActiveTeachers(){
      this.employeeService.getActiveTeacherByDept().then((teachers:Array<IEmployee>)=>{
        this.$scope.teachers=[];
        console.log(teachers);
        this.$scope.teachers = teachers;
      });
    }

    private setFromStudentId(student:IStudent){
      this.$scope.fromStudentId=student.id;
    }

    private setToStudentId(student:IStudent){
      this.$scope.toStudentId = student.id;
    }

    private getActiveStudentsOfDept(){
      this.studentService.getActiveStudentsByDepartment().then((students:Array<IStudent>)=>{
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

      for(var i=+this.$scope.fromStudentId;i<=+this.$scope.toStudentId;i++){
        if(this.$scope.studentIdWithStudentMap[i.toString()]!=null){
          this.$scope.addedStudents.push(this.$scope.studentIdWithStudentMap[i.toString()]);
          this.$scope.addedStudentIdMap[i.toString()]='added';
        }
      }

      console.log("Added students");
      console.log(this.$scope.addedStudents);
      //this.$scope.$apply();

    }

    private setFirstAutoCompleteValue(fromStudentId:any){
      this.$scope.fromStudentId = fromStudentId


    }

    private setSecondAutoCompleteValue(fromStudentId:any){
      this.$scope.toStudentId = fromStudentId

    }

  }

  UMS.controller("StudentAdviser",StudentAdviser);
}