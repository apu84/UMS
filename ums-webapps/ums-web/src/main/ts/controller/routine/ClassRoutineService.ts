module ums{

  interface DeptProgram {
    deptId: string;
    programs: Program[];
  }

  interface ITableHeader {
    startTime: string;
    endTime: string;
  }

  interface IConstant {
    id: string;
    name: string;
  }

  export interface ClassRoutine {
    id: string;
    semesterId: number;
    semester: Semester;
    programId: string;
    program: Program;
    courseId: string;
    course: Course;
    day: string;
    section: string;
    sessionalSection: IConstant;
    academicYear: number;
    academicSemester: number;
    startTime: string;
    endTime: string;
    startTimeObj: Date;
    endTimeObj: Date;
    duration: string;
    room: ClassRoom;
    roomId: string;
    courseTeacher: CourseTeacherInterface[];
    employee: Employee;
    slotGroup: number;
    color: string;
  }

  export interface RoutineSlot{
    groupNo: number;
    day: string;
    startTime: string;
    endTime: string;
    slotBodyList: SlotBody[];
    routineList: ClassRoutine[];
  }

  export interface SlotBody{
    headers: IRoutineTableHeader[];
    routineList:ClassRoutine[];
  }

  export interface IRoutineTableHeader {
    startTime: string;
    endTime: string;
  }

  export interface IRoutineErrorLog{
    year: number;
    semester: number;
    section: string;
    errorMessage: string;
    startTime: string;
    endTime: string;
    day: number;
    routine: ClassRoutine;
  }

  export class ClassRoutineService{

    public routineData: ClassRoutine[];
    public slotRoutineList: ClassRoutine[];
    public tableHeader: ITableHeader[];
    public weekDay: IConstant[];
    public weekDayMapWithId: any;
    public selectedSemester: Semester;
    public selectedProgram: Program;
    public selectedTheorySection: IParameter;
    public studentsYear: string;
    public studentsSemester: string;
    public enableEdit: boolean;
    public sectionSpecific:boolean;
    public routineMapWithTimeAndDay: any;
    public courseList: Course[];
    public roomList: ClassRoom[];
    public teacherList: Employee[];
    public selectedHeader: IRoutineTableHeader;
    public selectedDay: IConstant;
    public sessionalSectionMap: { [key: string]: IConstant };
    public dayAndTimeMapWithRoutine: { [key: string]: ClassRoutine[] }; // map[day+startTime] = ClassRoutine[];
    public dayAndTimeMapWithGroup:{[key:string]:number};
    public dayAndTimeMapWithRoutineSlot: {[key:string]: RoutineSlot};
    public groupMapWithRoutineSlot: {[key:number]:RoutineSlot};
    public groupList: number[];
    public courseTeacherMapWithCourseIdAndSection: { [key: string]: CourseTeacherInterface[] }; // map[courseId]= CourseTeacher[];
    public courseTeacherWithSectionMap: { [key: string]: CourseTeacherInterface[] }; // map[courseId+section]= CourseTeacher[];
    public routineUrl: string = '/ums-webservice-academic/academic/routine';
    public showSectionWiseRoutine:boolean;
    public showTeacherWiseRoutine: boolean;
    public showRoomWiseRoutine:boolean;
    public selectedTeacher: Employee;
    public exceptions: IRoutineErrorLog[];
    public exceptionsMapWithYearSemesterSectionDayAndTime:{[key:string]: IRoutineErrorLog[]};



    public static $inject = ['appConstants','HttpClient','$q','notify','$sce','$window'];
    constructor(private appConstants: any, private httpClient: HttpClient,
                private $q:ng.IQService, private notify: Notify,
                private $sce:ng.ISCEService,private $window:ng.IWindowService) {
      this.routineUrl = 'academic/routine';
    }


    public getClassRoutineReportForTeacher():ng.IPromise<any>{
      var defer= this.$q.defer();

        var contentType: string = UmsUtil.getFileContentType("pdf");
        this.httpClient.get("/ums-webservice-academic/academic/routine/routineReportTeacher", undefined, (data: any, etag: string) => {
                UmsUtil.writeFileContent(data, contentType, 'class-routine.pdf');
            },
            (response: any) => {
                defer.resolve("success");
                console.error(response);
            }, 'arraybuffer');

      return defer.promise;
    }

    public getRoutineForTeacher(employeeId: string, semesterId: number):ng.IPromise<any>{
      console.log("Semester id :"+ semesterId);
      var defer = this.$q.defer();
      var routines:any={};
      this.httpClient.get("/ums-webservice-academic/academic/routine/routineForTeacher/employeeId/"+employeeId+'/semesterId/'+semesterId,'application/json',
          (data:any,etag:string)=>{
            routines = data.entries;
            defer.resolve(routines);
          },
          (response:ng.IHttpPromiseCallbackArg<any>)=>{
            console.error(response);
            this.notify.error("Error in fetching routine data");
          });

      return defer.promise;
    }

    public getRoutineBySemesterAndCourse(semesterId: number, courseId: string): ng.IPromise<ClassRoutine[]> {
      var defer: ng.IDeferred<ClassRoutine[]> = this.$q.defer();
      this.httpClient.get("/ums-webservice-academic/academic/routine/semester/" + semesterId + "/course/" + courseId, 'application/json',
          (data: ClassRoutine[], etag: string) => {
            defer.resolve(data);
          },
          (response: ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
            this.notify.error("Error in fetching routine data");
          });
      return defer.promise;
    }
    public getRoomBasedClassRoutine(semesterId:number, roomId?:number):ng.IPromise<any>{
      var defer= this.$q.defer();

      if(!roomId){
        roomId=9999;
      }

      this.httpClient.get("academic/routine/roomBasedRoutine/semester/"+semesterId+"/room/"+roomId,  'application/json',
          (data:any, etag:string) => {
            defer.resolve(data.entries);
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
            defer.resolve("failure");
          });

      return defer.promise;
    }



    public getRoomBasedClassRoutineInnerHtmlFormat(semesterId:number, roomId?:number):ng.IPromise<any>{
      var defer= this.$q.defer();

      if(!roomId){
        roomId=9999;
      }

      this.httpClient.get("academic/routine/roomBasedRoutine/semester/"+semesterId+"/room/"+roomId,  'application/pdf',
          (data:any, etag:string) => {
            var file = new Blob([data], {type: 'application/pdf'});
            var fileURL = this.$sce.trustAsResourceUrl(URL.createObjectURL(file));
            //this.$window.open(fileURL);
            defer.resolve(fileURL);
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
            defer.resolve("failure");
          },'arraybuffer');

      return defer.promise;
    }


    public getSemesterWiseRoutineReport(semesterId: number, programId: number, year: number, semester: number, section: string){
      this.httpClient.get(`${this.routineUrl}/semester-wise-report/semester-id/${semesterId}/program/${programId}/year/${year}/semester/${semester}/section/${section}`, 'application/pdf',
          (data:any, etag: string)=>{
            var file = new Blob([data], {type: 'application/pdf'});
            UmsUtil.writeFileContent(data, 'application/pdf', 'SemesterWiseRoutine');
            //this.$window.open(fileURL);
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          },'arraybuffer');
    }


    public getTeacherWiseReport(teacherId: string, semesterId: number){
      this.httpClient.get(`${this.routineUrl}/teacher-wise-report/teacher-id/${teacherId}/semester/${semesterId}`, 'application/pdf',
          (data:any, etag: string)=>{
            var file = new Blob([data], {type: 'application/pdf'});
            UmsUtil.writeFileContent(data, 'application/pdf', 'TeacherWiseRoutine');
            //this.$window.open(fileURL);
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          },'arraybuffer');
    }


    public getRoomWiseRoutine(roomId: number, semesterId: number){
      this.httpClient.get(`${this.routineUrl}/room-wise-report/room-id/${roomId}/semester/${semesterId}`, 'application/pdf',
          (data:any, etag: string)=>{
            var file = new Blob([data], {type: 'application/pdf'});
            UmsUtil.writeFileContent(data, 'application/pdf', 'RoomWiseRoutine');
            //this.$window.open(fileURL);
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          },'arraybuffer');
    }



    public getClassRoutineForStudents():ng.IPromise<any>{
      var defer = this.$q.defer();


      return defer.promise;
    }

    public getClassRoutineForEmployee(semesterId:number,
                                      programId: number,
                                      year:number,
                                      semester:number,
                                      section:string):ng.IPromise<any>{
      let defer = this.$q.defer();
      this.httpClient.get(this.routineUrl + "/semester/"
          + semesterId + "/program/" + programId + "/year/" + year + "/semester/" + semester + "/section/" + section, 'application/json',
          (data: ClassRoutine[], etag: string) => {
            defer.resolve(data);
          },
          (response:ng.IHttpPromiseCallbackArg<any>)=>{
            console.error(response);
            this.notify.error("Error in fetching routine data");
          });

      return defer.promise;
    }

    public saveOrUpdateClassRoutine(classRoutineList: ClassRoutine[]): ng.IPromise<ClassRoutine[]> {
      var defer: ng.IDeferred<ClassRoutine[]> = this.$q.defer();
      this.httpClient.put(this.routineUrl + "/saveOrUpdate", classRoutineList, 'application/json')
          .success((response: ClassRoutine[]) => {
            defer.resolve(response);
          }).error((data)=>{
        defer.resolve(undefined);
      });

      return defer.promise;
    }

    public deleteRoutineById(id: string): ng.IPromise<any> {
      let defer = this.$q.defer();
      this.httpClient.doDelete(this.routineUrl + "/id/" + id)
          .success((response) => defer.resolve(response))
          .error((response) => {
            console.error(response);
            this.notify.error("Error in removing routine data");
            defer.resolve(undefined);
          })
      return defer.promise;
    }

    public downloadRoutineTemplate(semesterId: number):any{
      let fileName: string = "RoutineTemplate";
      let contentType: string = UmsUtil.getFileContentType("xls");
      let url = this.routineUrl+"/routine-template/semester/"+semesterId;

      this.httpClient.get(url, contentType,
          (data:any, etag:string)=>{
        UmsUtil.writeFileContent(data, contentType, fileName);
          },
          (response:ng.IHttpPromiseCallbackArg<any>)=>{
            console.error(response);
          }, 'arraybuffer');
    }

    public uploadFile(formData: any): ng.IPromise<IRoutineErrorLog[]> {
      var defer: ng.IDeferred<IRoutineErrorLog[]> = this.$q.defer();

      var url = this.routineUrl+"/upload";
      this.httpClient.post(url, formData, undefined)
          .success((response: IRoutineErrorLog[]) => {
            this.notify.success("Routine template parsed successfully");
            console.log('response');
            console.log(response);
            defer.resolve(response);
          }).error((data) => {
        console.error(data);
      });

      return defer.promise;
    }

  }

  UMS.service("classRoutineService",ClassRoutineService);
}