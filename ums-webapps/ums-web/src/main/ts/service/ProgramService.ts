/**
 * Created by My Pc on 04-Jan-17.
 */

module ums{
  export class ProgramService{

    public static $inject = ['appConstants','HttpClient','$q','notify','$sce','$window'];
    constructor(private appConstants: any, private httpClient: HttpClient,
                private $q:ng.IQService, private notify: Notify,
                private $sce:ng.ISCEService,private $window:ng.IWindowService) {

    }


    public fetchProgram(programType:number):ng.IPromise<any>{
      console.log("in the service");
      console.log(programType);
      var url="academic/program/programType/"+programType;
      var defer = this.$q.defer();

      this.httpClient.get(url, this.appConstants.mimeTypeJson,
          (json:any, etag:string)=>{
            var programs:any = json.entries;
            defer.resolve(programs);
          },
          (response:ng.IHttpPromiseCallbackArg<any>)=>{
            console.error(response);
          });

      return defer.promise;
    }


  }

  UMS.service("programService", ProgramService);

}