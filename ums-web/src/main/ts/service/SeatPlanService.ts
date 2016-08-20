module ums{
  import IPromise = ng.IPromise;
  export class SeatPlanService{
    public static $inject = ['appConstants','HttpClient','$q','notify','$sce','$window'];
    constructor(private appConstants: any, private httpClient: HttpClient,
                private $q:ng.IQService, private notify: Notify,
                private $sce:ng.ISCEService,private $window:ng.IWindowService) {

    }

    public getSeatPlanAttendanceSheetReport(programType:number,
                                            semesterId:number,
                                            examType:number,
                                            examDate:string
                                            ): IPromise<any>{

      var defer= this.$q.defer();

      this.httpClient.get("/ums-webservice-common/academic/seatplan/programType/"+programType+"/semesterId/"+semesterId+"/examType/"+examType+"/examDate/"+examDate,  'application/pdf',
          (data:any, etag:string) => {
            var file = new Blob([data], {type: 'application/pdf'});
            var fileURL = this.$sce.trustAsResourceUrl(URL.createObjectURL(file));
            this.$window.open(fileURL);
            defer.resolve("success");
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
            defer.resolve("failure");
          },'arraybuffer');

      return defer.promise;
    }
  }

  UMS.service("seatPlanService",SeatPlanService);
}