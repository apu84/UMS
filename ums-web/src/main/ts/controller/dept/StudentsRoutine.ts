///<reference path="../../service/HttpClient.ts"/>
///<reference path="../../lib/jquery.notific8.d.ts"/>
///<reference path="../../lib/jquery.notify.d.ts"/>
///<reference path="../../lib/jquery.jqGrid.d.ts"/>
module  ums{
  import Routine = ums.IRoutine;
  import Student = ums.Student;
  interface IStudentsRoutineScope extends ng.IScope{
    routine:Routine;
    hello:string;
    studentsRoutine:Function;
    getStudentInfo:Function;
    getCourses:Function;
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
    courseArr:Array<ICourse>
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
  interface ICourse {
    readOnly: boolean;
    index: number;
    id: string;
    no: string;
    title: string;
    year: number;
    semester: number;
  }

  export class StudentsRoutine{

    public static $inject = ['appConstants','HttpClient','$scope','$q'];
    constructor(private appConstants: any, private httpClient: HttpClient, private $scope: IStudentsRoutineScope,private $q:ng.IQService ){
      //$scope.studentId="150105001";
      $scope.days = appConstants.weekDays;
      $scope.checker = appConstants.timeChecker;
      var times:string[]=['08:00 am'];
      $scope.routineTime = appConstants.routineTime;
      console.log('for day');
      console.log($scope.days);
      $scope.studentsRoutine = this.studentsRoutine.bind(this);
      $scope.getCourses = this.getCourses.bind(this);

      this.studentsRoutine();

    }

    private studentsRoutine(){
      this.$scope.times=["08.00 AM","08.50 AM","09.40 AM","10.30 AM","11.20 AM","12.10 PM","01.00 PM","01.50 PM","02.40 PM","03.30 PM","04:20 PM","05.10 PM"];
      this.$scope.timeChecker="08.00 AM";
      this.$scope.colspan=1;

      this.getStudentInfo();

    }

    private createStudentsRoutine(routine:Array<Routine>){

      console.log('!!!!!!!!!!!!!!!!!!!!!!!!!!');
      console.log(routine);
      var routineStoreArr: IRoutineStore[] = [];
      console.log("-----course--arr-----");
      console.log(this.$scope.courseArr);

      for(var d=0;d<this.$scope.days.length;d++){

        for(var i=0;i<12;i++){
          var found:boolean = false;
          for(var routines=0;routines<routine.length; routines++){
            if(routine[routines].startTime == this.$scope.times[i] && routine[routines].day== this.$scope.days[d].id){
              var routineStore = new IRoutineStore();
              routineStore.day = "0"+routine[routines].day.toString();
              routineStore.colspan= routine[routines].duration.toString();   //routine[routines].courseId;
              for(var course = 0; course<this.$scope.courseArr.length; course++){
                if(this.$scope.courseArr[course].id == routine[routines].courseId){
                  routineStore.courseId = this.$scope.courseArr[course].no;
                }
              }
              console.log("----------");
              console.log(routineStore.courseId);
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

    private getStudentRoutineBySemesterAndProgram(){
      this.httpClient.get('academic/routine/routineForStudent','application/json',(json:any,etag:string)=>{
            this.$scope.routines = json.entries;
            this.getCourses().then((courseArr:Array<ICourse>)=>{
              this.createStudentsRoutine(this.$scope.routines);
            });

          },
          (response:ng.IHttpPromiseCallbackArg<any>)=>{
            console.error(response);
          });
    }


    /*
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
*/
    private getStudentInfo():void{
      var defer = this.$q.defer();
      var studentArr: Array<any>;
      this.httpClient.get('academic/student','application/json',(json:any,etag:string)=>{
            this.$scope.student = json;
            this.getStudentRoutineBySemesterAndProgram();

          },
          (response:ng.IHttpPromiseCallbackArg<any>)=>{
            console.error(response);
          });
    }

    private getCourses():ng.IPromise<any>{
      var defer = this.$q.defer();
      var courseArr:Array<ICourse>;
      this.httpClient.get('/ums-webservice-common/academic/course/semester/'+'11012016'+'/program/'+'110500', 'application/json',
          (json:any, etag:string) => {
            courseArr = json.entries;
            this.$scope.courseArr = courseArr;
            defer.resolve(courseArr);
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });
      return defer.promise;
    }

  }

  UMS.controller("StudentsRoutine",StudentsRoutine);
}