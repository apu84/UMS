/**
 * Created by Monjur-E-Morshed on 08-Dec-16.
 */

module ums{
  export class FacultyService{

    url:string="academic/faculty";

    public static $inject = ['appConstants','HttpClient','$q','notify','$sce','$window'];
    constructor(private appConstants: any, private httpClient: HttpClient,
                private $q:ng.IQService, private notify: Notify,
                private $sce:ng.ISCEService,private $window:ng.IWindowService) {

    }


    public getAllFaculties():ng.IPromise<any>{
      var defer = this.$q.defer();
      var faculties:Array<Faculty>=[];

      this.httpClient.get(this.url+"/all",this.appConstants.mimeTypeJson,
          (json:any, etag:string)=>{
            faculties = json.entries;
            defer.resolve(faculties);
            defer.resolve(faculties);
          },(response:ng.IHttpPromiseCallbackArg<any>)=>{
            console.error(response);
          });

      return defer.promise;
    }

  }

  UMS.service("facultyService", FacultyService);
}