/**
 * Created by My Pc on 04-Jan-17.
 */

module ums{

  export class AdmissionTotalSeatService{

    public static $inject = ['appConstants','HttpClient','$q','notify','$sce','$window'];
    constructor(private appConstants: any, private httpClient: HttpClient,
                private $q:ng.IQService, private notify: Notify,
                private $sce:ng.ISCEService,private $window:ng.IWindowService) {

    }

    public fetchAdmissionTotalSeat(semesterId:number, programType:number):ng.IPromise<any>{
      var url="academic/admissionTotalSeat/semester/"+semesterId+"/programType/"+programType;
      var defer = this.$q.defer();

      this.httpClient.get(url, this.appConstants.mimeTypeJson,
          (json:any, etag:string)=>{

            var seats:any = {};
            seats   = json.entries;
            console.log("in service")
            console.log(seats);
            console.log("json");
            console.log(json);
            defer.resolve(seats);
          },
          (response:ng.IHttpPromiseCallbackArg<any>)=>{
            console.error(response);
          });

      return defer.promise;
    }

    public saveAdmissionTotalSeatInfo(json:any):ng.IPromise<any>{
      var defer=this.$q.defer();
      var url="academic/admissionTotalSeat/save";
      this.httpClient.post(url,json,'application/json')
          .success(()=>{
            defer.resolve("success");
          }).error((data)=>{
        defer.resolve("error");
      });

      return defer.promise;
    }


    public updateAdmissionTotalSeat(json:any):ng.IPromise<any>{
      var defer=this.$q.defer();
      var url="academic/admissionTotalSeat/update";
      this.httpClient.put(url,json,'application/json')
          .success(()=>{
            defer.resolve("success");
          }).error((data)=>{
        defer.resolve("error");
      });

      return defer.promise;
    }

  }

  UMS.service("admissionTotalSeatService", AdmissionTotalSeatService);
}