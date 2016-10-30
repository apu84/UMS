module  ums{
  interface IStudentsRoutineScope extends ng.IScope{
    routine:IRoutine;
    hello:string;
    studentsRoutine:Function;
    getStudentInfo:Function;
    getCourses:Function;
    increaseTimeChecker:Function;
    studentsRoutineBySemesterAndProgram: Function;
    routines:Array<IRoutine>;
    student:Student;
    semesterId:string;
    programId: string;
    academicYear: string;
    academicSemester: string;
    roomIdRoomNoMap:any;
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
    courseIdMapCourse:any;
    department:any;
  }

  class IRoutineStore{
    day:string;
    colspan:string;
    courseId:string;
    roomNo:string;
    roomId:number;
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

    public static $inject = ['appConstants','HttpClient','$scope','$q','classRoomService'];
    constructor(private appConstants: any, private httpClient: HttpClient, private $scope: IStudentsRoutineScope,private $q:ng.IQService, private classRoomService: ClassRoomService ){
      //$scope.studentId="150105001";
      $scope.days = appConstants.weekDays;
      $scope.checker = appConstants.timeChecker;
      var times:string[]=['08:00 am'];
      $scope.routineTime = appConstants.routineTime;
      $scope.courseIdMapCourse={};
      $scope.department=appConstants.deptLong;
      $scope.studentsRoutine = this.studentsRoutine.bind(this);
      $scope.getCourses = this.getCourses.bind(this);

      this.studentsRoutine();

    }

    private studentsRoutine(){
      this.$scope.times=["08:00 AM","08:50 AM","09:40 AM","10:30 AM","11:20 AM","12:10 PM","01:00 PM","01:50 PM","02:40 PM","03:30 PM","04:20 PM","05:10 PM"];
      this.$scope.timeChecker="08.00 AM";
      this.$scope.colspan=1;

      this.getStudentInfo().then((studentArr:Student)=>{

      });

    }

    private createStudentsRoutine(routine:Array<IRoutine>){

      var routineStoreArr: IRoutineStore[] = [];

      for(var d=0;d<this.$scope.days.length;d++){

        for(var i=0;i<12;i++){
          var found:boolean = false;
          for(var routines=0;routines<routine.length; routines++){

            if(routine[routines].startTime == this.$scope.times[i] && routine[routines].day== Number(this.$scope.days[d].id)){
              var routineStore = new IRoutineStore();
              routineStore.day = "0"+routine[routines].day.toString();
              routineStore.colspan= routine[routines].duration.toString();   //routine[routines].courseId;

              routineStore.courseId = this.$scope.courseIdMapCourse[routine[routines].courseId].no;

              routineStore.roomNo = this.$scope.roomIdRoomNoMap[routine[routines].roomId] ;

              routineStoreArr.push(routineStore);
              i=i+(routine[routines].duration-1);
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


      this.$scope.routineStore = routineStoreArr;

    }

    private getStudentRoutineBySemesterAndProgram(){
      this.httpClient.get('academic/routine/routineForStudent','application/json',(json:any,etag:string)=>{
            this.$scope.routines = json.entries;
            this.$scope.roomIdRoomNoMap={};
            this.getRooms().then((rooms:Array<any>)=>{


             this.getCourses().then((courseArr:Array<ICourse>)=>{

                this.createStudentsRoutine(this.$scope.routines);

              });
            });

          },
          (response:ng.IHttpPromiseCallbackArg<any>)=>{
            console.error(response);
          });
    }


    private getRooms():ng.IPromise<any>{
      var defer=this.$q.defer();
      this.classRoomService.getClassRoomsBasedOnRoutine(this.$scope.student[0].semesterId).then((rooms:Array<any>)=> {

        for (var i = 0; i < rooms.length; i++) {
          this.$scope.roomIdRoomNoMap[rooms[i].id] = rooms[i].roomNo;
        }
      });

      defer.resolve(this.$scope.roomIdRoomNoMap);
      return defer.promise;

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
    private getStudentInfo():ng.IPromise<any>{
      var defer = this.$q.defer();
      var studentArr: Array<any>;
      this.httpClient.get('academic/student/getStudentInfoById','application/json',(json:any,etag:string)=>{
            this.$scope.student = json.entries;

            this.getStudentRoutineBySemesterAndProgram();
            defer.resolve(this.$scope.student);

          },
          (response:ng.IHttpPromiseCallbackArg<any>)=>{
            console.error(response);
          });
      return defer.promise;
    }

    private getCourses():ng.IPromise<any>{
      var defer = this.$q.defer();
      var courseArr:Array<ICourse>;
      this.httpClient.get('academic/course/semester/'+this.$scope.student[0].semesterId+'/program/'+this.$scope.student[0].programId+'/year/'+this.$scope.student[0].year+'/academicSemester/'+this.$scope.student[0].academicSemester, 'application/json',
          (json:any, etag:string) => {
            courseArr = json.entries;
            this.$scope.courseArr=[];
            for(var i=0;i<courseArr.length;i++){
              this.$scope.courseIdMapCourse[courseArr[i].id] = courseArr[i];
              this.$scope.courseArr.push(courseArr[i]);
            }

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