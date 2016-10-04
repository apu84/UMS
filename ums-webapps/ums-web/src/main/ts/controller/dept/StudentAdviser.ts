module ums{
  interface IStudentAdviser extends ng.IScope{

    shiftOptionSelected:boolean;
    showShiftUI:Function;

    changeOptionSelected:boolean;
    showChangeUI:Function;

    bulkAssignmentOptionSelected:boolean;
    showBulkAssignmentUI:Function;

    panelHeading:string;

    teachers:Array<IEmployee>;
    teacherId:string;
    getActiveTeachers:Function;


    students:Array<IStudent>;
    fromStudentId:string;
    toStudentId:string;
    getActiveStudentsOfDept:Function;

  }

  interface IStudent{
    id:string;
    fullName:string;
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

      $scope.shiftOptionSelected=false;
      $scope.changeOptionSelected=false;
      $scope.bulkAssignmentOptionSelected=false;
      $scope.showShiftUI=this.showShiftUI.bind(this);
      $scope.showChangeUI = this.showChangeUI.bind(this);
      $scope.showBulkAssignmentUI = this.showBulkAssignmentUI.bind(this);
      $scope.getActiveTeachers = this.getActiveTeachers.bind(this);
      $scope.getActiveStudentsOfDept = this.getActiveStudentsOfDept.bind(this);

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
      this.activateUI(3);
    }



    private getActiveTeachers(){
      this.employeeService.getActiveTeacherByDept().then((teachers:Array<IEmployee>)=>{
        this.$scope.teachers=[];
        console.log(teachers);
        this.$scope.teachers = teachers;
      });
    }

    private getActiveStudentsOfDept(){
      this.studentService.getActiveStudentsByDepartment().then((students:Array<IStudent>)=>{
        this.$scope.students=[];
        this.$scope.students = students;
        console.log(students);
      });
    }



  }

  UMS.controller("StudentAdviser",StudentAdviser);
}