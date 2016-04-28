module ums{
  interface IExamSeatPlanScope extends ng.IScope{
    semesterList:Array<ISemester>;
    seatPlanGroupList:Array<ISeatPlanGroup>;
    roomList:any;

    data:any;
    groupNumber:number;
    semester:ISemester
    examType:number;
    system:number;
    update:number;
    seatPlanGroupListLength:number;
    semesterId:number;
    groupNoForSeatPlanViewing:number;

    groupSelected:boolean;
    showGroupSelectionPanel:boolean;
    showGroupSelection:boolean;

    arr :any;

    getSemesterInfo:Function;
    getSeatPlanGroupInfo:Function;
    createOrViewSeatPlan:Function;
    getGroups:Function;
    getRoomInfo:Function;
    getRoomList:Function;
    closeRoomInfoWindow: Function;
    showGroups:Function;
  }

  interface IRoom{
    id:number;
    roomNo:string;
    totalRow:number;
    totalColumn:number;
    capacity:number;
  }


  interface ISemester{
    id:number;
    name:string;
    startDate:string;
    status:number;
  }

  interface ISeatPlanGroup{
    id:number;
    semesterId:number;
    programId:number;
    programName:string;
    year:number;
    semester:number;
    groupNo:number;
    type:number;
    lastUpdated:string;

  }

  export class ExamSeatPlan{
    public static $inject = ['appConstants','HttpClient','$scope','$q','notify'];
    constructor(private appConstants: any, private httpClient: HttpClient, private $scope: IExamSeatPlanScope,private $q:ng.IQService, private notify: Notify ) {

      var arr : { [key:number]:Array<ISeatPlanGroup>; } = {};

      $scope.groupSelected = false;
      $scope.showGroupSelectionPanel = true;
      $scope.showGroupSelection = false;
      $scope.arr = arr;
      $scope.update = 0;
      $scope.groupNumber = 1;
      $scope.getSemesterInfo = this.getSemesterInfo.bind(this);
      $scope.getSeatPlanGroupInfo = this.getSeatPlanGroupInfo.bind(this);
      $scope.getGroups = this.getGroups.bind(this);
      $scope.getRoomInfo = this.getRoomInfo.bind(this);
      $scope.getRoomList = this.getRoomList.bind(this);
      $scope.closeRoomInfoWindow = this.closeRoomInfoWindow.bind(this);
      $scope.createOrViewSeatPlan = this.createOrViewSeatPlan.bind(this);
      $scope.showGroups = this.showGroups.bind(this);
      this.initialize();

    }

    private initialize():void{
      this.getSemesterInfo().then((semesterArr:Array<ISemester>)=>{

      });
    }

    private showGroups():void{
      /*if(this.$scope.semesterId!=null && this.$scope.examType!=null && this.$scope.system!=null){
        this.$scope.showGroupSelection = true;
      }*/
      this.$scope.showGroupSelection = true;

    }

    private getGroups():void{
      this.getSeatPlanGroupInfo().then((groupArr:Array<ISeatPlanGroup>)=>{
        this.$scope.seatPlanGroupList = groupArr;
        this.$scope.seatPlanGroupListLength = this.$scope.seatPlanGroupList.length;
      });
    }

    private getRoomList():void{
        this.getRoomInfo();

        this.$scope.groupSelected = true;
        this.$scope.showGroupSelectionPanel = false;

        //console.log(this.$scope.roomList[0].roomN
        //umber);

    }


    private closeRoomInfoWindow():void{
      this.$scope.groupSelected = false;
      this.$scope.showGroupSelectionPanel = true;
    }

    private createOrViewSeatPlan():void{
        this.getRoomList();
    }

    private getSeatPlanGroupInfo():ng.IPromise<any>{
      var defer = this.$q.defer();
      var seatPlanGroupList:Array<ISeatPlanGroup>;
      this.httpClient.get('/ums-webservice-common/academic/seatPlanGroup/semester/'+this.$scope.semesterId +'/type/'+this.$scope.examType+'/update/'+this.$scope.update, 'application/json',
          (json:any, etag:string) => {
            seatPlanGroupList = json.entries;

            defer.resolve(seatPlanGroupList);
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });
      return defer.promise;
    }

    private getRoomInfo():void{
     /* var defer = this.$q.defer();
      var roomList:Array<IRoom>;
      this.httpClient.get('/ums-webservice-common/academic/classroom/all', 'application/json',
          (json:any, etag:string) => {
            roomList = json;
            console.log("-------------getRoomInfo----------");

            for(var r in roomList){
              console.log(r);
            }

            defer.resolve(roomList);
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });
      return defer.promise;*/
      this.httpClient.get("academic/classroom/all", HttpClient.MIME_TYPE_JSON,
          (response) => {
            this.$scope.roomList = response.rows;
          });
    }


    private getSemesterInfo():ng.IPromise<any>{
      var defer = this.$q.defer();
      this.httpClient.get('/ums-webservice-common/academic/semester/all', 'application/json',
          (json:any, etag:string) => {
            this.$scope.semesterList = json.entries;
            console.log("---inside semesterInfo---");
            console.log(this.$scope.semesterList);
            defer.resolve(this.$scope.semesterList);
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });
      return defer.promise;
    }
  }

  UMS.controller("ExamSeatPlan",ExamSeatPlan);
}