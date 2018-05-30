module ums{
   export class ExpelledInformationService{
       public static $inject = ['appConstants','HttpClient','$q','notify','$sce','$window'];
       constructor(private appConstants: any, private httpClient: HttpClient,
                   private $q:ng.IQService, private notify: Notify,
                   private $sce:ng.ISCEService,private $window:ng.IWindowService) {

       }
       public getCourses(studentId:string,examType:number):ng.IPromise<any>{
           var defer = this.$q.defer();
           this.httpClient.get('academic/expelledInformation/getCourseList/studentId/'+studentId+'/examType/'+examType,HttpClient.MIME_TYPE_JSON,
               (response: any) => {
                   defer.resolve(response);
               });
           return defer.promise;

       }
       public addExpelledStudentsRecord(json:any):ng.IPromise<any>{
           var defer = this.$q.defer();
           var that=this;
           console.log("Inside-Service")
           console.log(json);
           this.httpClient.post("academic/expelledInformation/addRecords",json,'application/json')
               .success(()=>{
                   this.notify.success("Successfully Saved");
                   defer.resolve('success')
               })
               .error((data)=>{
                   console.log(data);
                   this.notify.error("Problem in saving data");
                   defer.resolve('failure');
               });
           return defer.promise;
       }

    }
    UMS.service("ExpelledInformationService",ExpelledInformationService);
}