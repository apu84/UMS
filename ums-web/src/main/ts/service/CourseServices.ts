module ums{
  export class CourseService{

    public static $inject = ['appConstants','HttpClient','$q','notify','$sce','$window'];
    constructor(private appConstants: any, private httpClient: HttpClient,
                private $q:ng.IQService, private notify: Notify,
                private $sce:ng.ISCEService,private $window:ng.IWindowService) {

    }

    public getCourse(semesterId:number,programId:number,year:number,semester:number):ng.IPromise<any>{

      var defer = this.$q.defer();
      var courses:any={};
      this.httpClient.get("/ums-webservice-common/academic/course/semester/"+
          semesterId+"/program/"+programId+"/year/"+year+"/semester/"+semester,
          'application/json',
          (json:any,etag:string)=>{
            courses = json.entries;
            defer.resolve(courses);
          },
          (response:ng.IHttpPromiseCallbackArg<any>)=> {
            console.log(response);
          })

      return defer.promise;

    }

    public getCourseBySemesterAndProgramType(semesterId:number, programType:number):ng.IPromise<any>{
      var defer = this.$q.defer();
      var courses:any={};
      this.httpClient.get('/ums-webservice-common/academic/course/semester'+semesterId+'/programType/'+programType,'application/json',
          (json:any,etag:string)=>{
            courses=json.entries;
            defer.resolve(courses);
          },
          (response:ng.IHttpPromiseCallbackArg<any>)=>{
            console.error(response);
            this.notify.error("Error in fetching course data");
          });

      return defer.promise;
    }

  }


  UMS.service("courseService",CourseService);
}