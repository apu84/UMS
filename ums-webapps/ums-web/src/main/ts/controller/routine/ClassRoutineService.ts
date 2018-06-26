module ums{

  export interface ClassRoutine {
    id: number;
    semesterId: number;
    semester: Semester;
    programId: number;
    program: Program;
    courseId: string;
    course: Course;
    day: string;
    section: string;
    academicYear: number;
    academicSemester: number;
    startTime: string;
    endTime: string;
    duration: string;
    roomId: number;
    }

  export class ClassRoutineService{

    private routineUrl: string = 'academic/routine';
    public static $inject = ['appConstants','HttpClient','$q','notify','$sce','$window'];
    constructor(private appConstants: any, private httpClient: HttpClient,
                private $q:ng.IQService, private notify: Notify,
                private $sce:ng.ISCEService,private $window:ng.IWindowService) {

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

     /* this.httpClient.get("/ums-webservice-academic/academic/routine/routineReportTeacher",  'application/pdf',
          (data:any, etag:string) => {
            var file = new Blob([data], {type: 'application/pdf'});
            var fileURL = this.$sce.trustAsResourceUrl(URL.createObjectURL(file));
            this.$window.open(fileURL);
            defer.resolve(fileURL);
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
            defer.resolve("failure");
          },'arraybuffer');*/

      return defer.promise;
    }

    public getRoutineForTeacher():ng.IPromise<any>{
      var defer = this.$q.defer();
      var routines:any={};
      this.httpClient.get("/ums-webservice-academic/academic/routine/routineForTeacher",'application/json',
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


    public getRoomBasedClassRoutine(semesterId:number, roomId?:number):ng.IPromise<any>{
      var defer= this.$q.defer();

      if(!roomId){
        roomId=9999;
      }

      this.httpClient.get("academic/routine/roomBasedRoutine/semester/"+semesterId+"/room/"+roomId,  'application/pdf',
          (data:any, etag:string) => {
            var file = new Blob([data], {type: 'application/pdf'});
            var fileURL = this.$sce.trustAsResourceUrl(URL.createObjectURL(file));
            this.$window.open(fileURL);
            defer.resolve(fileURL);
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
            defer.resolve("failure");
          },'arraybuffer');

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

    public saveClassRoutine(json:any):ng.IPromise<any>{
      var defer=this.$q.defer();
      this.httpClient.post(this.routineUrl,json,'application/json')
          .success(()=>{
            defer.resolve("success");
          }).error((data)=>{
        defer.resolve("error");
      });

      return defer.promise;
    }

  }

  UMS.service("classRoutineService",ClassRoutineService);
}