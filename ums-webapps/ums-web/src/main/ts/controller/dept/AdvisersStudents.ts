/**
 * Created by My Pc on 21-Nov-16.
 */

module ums{
  interface  IAdvisersStudents extends ng.IScope{

    teacher:Employee;
    students:Array<Student>;
    studentsByYearSemester:Array<IStudentsByYearSemester>;
    backgroundColor:string;

    yearSemesterMapWithIStudentsByYearSemester:any;


    showStudentId:boolean;
    showStudentName:boolean;
    showStudentsByYearSemester:boolean;

    getTeacherInfo:Function;
    studentsOfAdviser:Function;
    viewStudentById:Function;
    viewStudentByIdAndName:Function;
  }

  interface IStudentsByYearSemester{
    year:number;
    semester:number;
    key:number;
    students:Array<Student>;
  }

  class AdvisersStudents{
    public static $inject = ['appConstants','HttpClient','$scope','$q','notify','semesterService','employeeService','studentService'];
    constructor(private appConstants: any, private httpClient: HttpClient, private $scope: IAdvisersStudents,
                private $q:ng.IQService, private notify: Notify, private semesterService:SemesterService,
                private employeeService:EmployeeService,
                private studentService:StudentService) {

      $scope.students=[];
      $scope.backgroundColor=Utils.DEFAULT_COLOR;
      $scope.showStudentsByYearSemester=true;
      $scope.yearSemesterMapWithIStudentsByYearSemester={};
      $scope.studentsByYearSemester=[];
      $scope.showStudentId=true;
      $scope.showStudentName=false;
      $scope.studentsOfAdviser = this.studentsOfAdviser.bind(this);
      $scope.viewStudentById=this.viewStudentById.bind(this);
      $scope.viewStudentByIdAndName = this.viewStudentByName.bind(this);

    }

    private getTeacherInfo():void{

    }

    private viewStudentById(){
      this.$scope.showStudentId=true;
      this.$scope.showStudentName=false;
    }

    private viewStudentByName(){
      this.$scope.showStudentId=false;
      this.$scope.showStudentName=true;
    }

    private studentsOfAdviser(){
      this.employeeService.getLoggedEmployeeInfo().then((employee:Employee)=>{

        this.studentService.getActiveStudentsOfTheTeacher(employee.id).then((students:Array<Student>)=>{

          this.$scope.students = students;

          for(var i=0;i<students.length;i++){
            this.insertStudentsByYearAndSemester(students[i]);
          }

        });

      });
    }

    private insertStudentsByYearAndSemester(student:Student):void{
      if(this.$scope.studentsByYearSemester.length==0){
        this.createNewStudentsByYearSemester(student);
      }else{
        if(this.$scope.yearSemesterMapWithIStudentsByYearSemester[student.year+""+student.academicSemester]==null){
          this.createNewStudentsByYearSemester(student);
        }
        else{
          this.insertIntoExistingStudentsByYearSemester(student);
        }
      }

    }

    private sortStudentsWithYearSemester(){
      this.$scope.studentsByYearSemester.sort((a,b)=>{
        return Number(a.key)-Number(b.key);
      });
    }

    private insertIntoExistingStudentsByYearSemester(student:Student):void{
      var studentsByYearSemester:IStudentsByYearSemester=this.$scope.yearSemesterMapWithIStudentsByYearSemester[student.year+""+student.academicSemester];
      studentsByYearSemester.students.push(student);
      this.sortStudentsWithYearSemester();
    }

    private createNewStudentsByYearSemester(student:Student):void{
      var studentsByYearSemester:IStudentsByYearSemester = <IStudentsByYearSemester>{};
      studentsByYearSemester.year= student.year;
      studentsByYearSemester.semester = student.academicSemester;
      studentsByYearSemester.students=[];
      studentsByYearSemester.key=+(student.year+""+student.academicSemester);
      studentsByYearSemester.students.push(student);
      this.$scope.studentsByYearSemester.push(studentsByYearSemester);
      this.$scope.yearSemesterMapWithIStudentsByYearSemester[student.year+""+student.academicSemester]=studentsByYearSemester;
      this.sortStudentsWithYearSemester();
    }
  }

  UMS.controller("AdvisersStudents", AdvisersStudents)

}