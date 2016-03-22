///<reference path="../../service/HttpClient.ts"/>
///<reference path="../../lib/jquery.notific8.d.ts"/>
///<reference path="../../lib/jquery.notify.d.ts"/>
///<reference path="../../lib/jquery.jqGrid.d.ts"/>
module  ums{
  import Routine = ums.IRoutine;
  import Student = ums.Student;
  interface IStudentsRoutineScope extends ng.IScope{
    routine:Routine;
    studentId:string;
    hello:string;
    showRoutine:boolean;
    studentsRoutine:Function;
    getStudentInfo:Function;
    increaseTimeChecker:Function;
    studentsRoutineBySemesterAndProgram: Function;
    routines:Array<Routine>;
    student:Student;
    semesterId:string;
    programId: string;
    academicYear: string;
    academicSemester: string;
    departmentName:string;
    semesterName:string;
    semester:ISemester;
    days:any;
    routineTime:any;
    timeChecker:string;
    colspan:number;
    times:any;
    store:number;
    duration:string;
    checker:any;
    routineStore:any;
  }

  class IRoutineStore{
    day:string;
    colspan:string;
    courseId:string;
    roomNo:string;
  }

  interface ISemester{
    id:string;
    name:string;
  }

  export class StudentsRoutine{

    public static $inject = ['appConstants','HttpClient','$scope','$q'];
    constructor(private appConstants: any, private httpClient: HttpClient, private $scope: IStudentsRoutineScope,private $q:ng.IQService ){
      $scope.studentId="150105001";
      $scope.days = appConstants.weekDays;
      $scope.checker = appConstants.timeChecker;
      var times:string[]=['08:00 am'];
      $scope.routineTime = appConstants.routineTime;
      console.log('for day');
      console.log($scope.days);
      $scope.studentsRoutine = this.studentsRoutine.bind(this);

    }

    private studentsRoutine(){
      this.$scope.times=["08.00 AM","08.50 AM","09.40 AM","10.30 AM","11.20 AM","12.10 PM","01.00 PM","01.50 PM","02.40 PM","03.30 PM","04:20 PM","05.10 PM"];
      this.$scope.timeChecker="08.00 AM";
      this.$scope.colspan=1;

      if(this.$scope.studentId!=null){
        this.$scope.showRoutine = true;
      }

      this.getStudentInfo(this.$scope.studentId);

    }

    private createStudentsRoutine(routine:Array<Routine>){

      console.log('!!!!!!!!!!!!!!!!!!!!!!!!!!');
      console.log(routine);
      var routineStoreArr: IRoutineStore[] = [];

      for(var d=0;d<this.$scope.days.length;d++){

        for(var i=0;i<12;i++){
          var found:boolean = false;
          for(var routines=0;routines<routine.length; routines++){
            if(routine[routines].startTime == this.$scope.times[i] && routine[routines].day== this.$scope.days[d].id){
              var routineStore = new IRoutineStore();
              routineStore.day = "0"+routine[routines].day.toString();
              routineStore.colspan= routine[routines].duration.toString();
              routineStore.courseId = routine[routines].courseId;
              routineStore.roomNo = routine[routines].roomNo;
              //console.log(routineStore);
              routineStoreArr.push(routineStore);
              i=i+(routine[routines].duration-1);
              console.log(i);
              found = true;
              break;
            }
          }
          if(found==false){
            var routineStore = new IRoutineStore();
            routineStore.day="0"+(d+1).toString();
            routineStore.colspan="1";
            routineStore.courseId="";
            routineStore.roomNo="";
            routineStoreArr.push(routineStore);
          }
        }
      }
      console.log("ohh nooo");


      this.$scope.routineStore = routineStoreArr;
      console.log(this.$scope.routineStore);
    }

    private getStudentRoutineBySemesterAndProgram(semesterId:string,programId:string){
      this.httpClient.get('academic/routine/routineForStudent/'+semesterId+'/'+programId,'application/json',(json:any,etag:string)=>{
            this.$scope.routines = json.entries;
            this.createStudentsRoutine(this.$scope.routines);
          },
          (response:ng.IHttpPromiseCallbackArg<any>)=>{
            console.error(response);
          });
    }


    private getSemesterInfo(semesterId:string){
      this.httpClient.get('academic/semester/'+semesterId,'application/json',(json:any,etag:string)=>{
            console.log("semesters");
            console.log(json);
            this.$scope.semester = json;
            this.$scope.semesterName = this.$scope.semester.name;
            console.log(this.$scope.semesterName);
          },
          (response:ng.IHttpPromiseCallbackArg<any>)=>{
            console.error(response);
          });
    }

    private getStudentInfo(studentId: string):void{
      var defer = this.$q.defer();
      var studentArr: Array<any>;
      this.httpClient.get('academic/student/'+studentId,'application/json',(json:any,etag:string)=>{
            this.$scope.student = json;
            this.$scope.semesterId = this.$scope.student.semesterId;
            this.$scope.programId = this.$scope.student.programId;
            this.$scope.departmentName = this.appConstants.deptLong[this.$scope.student.department];
            this.getSemesterInfo(this.$scope.semesterId);
            this.getStudentRoutineBySemesterAndProgram(this.$scope.semesterId,this.$scope.programId);
          },
          (response:ng.IHttpPromiseCallbackArg<any>)=>{
            console.error(response);
          });
    }

  }

  UMS.controller("StudentsRoutine",StudentsRoutine);
}