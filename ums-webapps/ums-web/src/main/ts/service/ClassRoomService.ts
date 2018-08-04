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
      this.httpClient.get("/ums-webservice-academic/academic/classroom/program",'application/json',
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

    public getAllClassRooms():ng.IPromise<any>{
      var defer = this.$q.defer();
      console.log("-----");
      var rooms:any={};
      this.httpClient.get("/ums-webservice-academic/academic/classroom/all",'application/json',
          (json:any,etag:string)=>{
            rooms = json.entries;
              console.log("----"+json);
            defer.resolve(rooms);
          },
          (response:ng.IHttpPromiseCallbackArg<any>)=>{
            console.error(response);
            this.notify.error("Error in fetching room data");
          });

      return defer.promise;
    }

    public getClassRoomsForSeatPlan(): ng.IPromise<any> {
      var defer = this.$q.defer();
      var rooms: any = {};
      this.httpClient.get("academic/classroom/seat-plan/all", 'application/json',
          (json: any, etag: string) => {
        console.log(json);
            rooms = json;
            defer.resolve(rooms.rows);
          },
          (response: ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
            this.notify.error("Error in fetching room data for seat plan");
          });

      return defer.promise;
    }

    public getClassRoomsBasedOnRoutine(semesterId:number):ng.IPromise<any>{
      var defer = this.$q.defer();
      var rooms:any={};
      this.httpClient.get("/ums-webservice-academic/academic/classroom/forRoutine/semester/"+semesterId,'application/json',
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


    public getClassRoomsBasedOnSeatPlan(semesterId:number, examType:number):ng.IPromise<any>{
      console.log("examType in service");
      console.log(examType);
      var defer = this.$q.defer();
      var rooms:any={};
      this.httpClient.get("/ums-webservice-academic/academic/classroom/seatplan/semester/"+semesterId+"/examType/"+examType,'application/json',
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