module ums{
  export class ClassRoomService{
    public static $inject = ['appConstants','HttpClient','$q','notify','$sce','$window'];
    constructor(private appConstants: any, private httpClient: HttpClient,
                private $q:ng.IQService, private notify: Notify,
                private $sce:ng.ISCEService,private $window:ng.IWindowService) {

    }

    public getClassRooms():ng.IPromise<any>{
      var defer = this.$q.defer();
      var rooms:any={};
      this.httpClient.get("/ums-webservice-common/academic/classroom/program",'application/json',
          (json:any,etag:string)=>{
            rooms = json.entries;
            defer.resolve(rooms);
          },
          (response:ng.IHttpPromiseCallbackArg<any>)=>{
            console.error(response);
            this.notify.error("Error in fetching room data");
          });

      return defer.promise;
    }


  }

  UMS.service("classRoomService",ClassRoomService);
}