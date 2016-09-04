module ums{
  export class ClassRoutineService{
    public static $inject = ['appConstants','HttpClient','$q','notify','$sce','$window'];
    constructor(private appConstants: any, private httpClient: HttpClient,
                private $q:ng.IQService, private notify: Notify,
                private $sce:ng.ISCEService,private $window:ng.IWindowService) {

    }


    public getClassRoutineReportForTeacher():ng.IPromise<any>{
      var defer= this.$q.defer();

      this.httpClient.get("/ums-webservice-common/academic/routine/routineReportTeacher",  'application/pdf',
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

  UMS.service("classRoutineService",ClassRoutineService);
}