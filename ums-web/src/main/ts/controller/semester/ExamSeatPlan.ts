module ums{
  interface IExamSeatPlanScope extends ng.IScope{
    semesterList:Array<ISemester>;
    seatPlanGroupList:Array<ISeatPlanGroup>;

    data:any;
    groupNumber:number;
    semester:ISemester
    examType:number;
    system:number;
    update:number;
    seatPlanGroupListLength:number;
    semesterId:number;

    arr :any;

    getSemesterInfo:Function;
    getSeatPlanGroupInfo:Function;
    getGroups:Function;
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
      $scope.arr = arr;
      $scope.update = 0;
      $scope.groupNumber = 1;
      $scope.getSemesterInfo = this.getSemesterInfo.bind(this);
      $scope.getSeatPlanGroupInfo = this.getSeatPlanGroupInfo.bind(this);
      $scope.getGroups = this.getGroups.bind(this);
      this.initialize();

    }

    private initialize():void{
      this.getSemesterInfo().then((semesterArr:Array<ISemester>)=>{

      });
    }



    private getGroups():void{
      this.getSeatPlanGroupInfo().then((groupArr:Array<ISeatPlanGroup>)=>{
        this.$scope.seatPlanGroupList = groupArr;
        this.$scope.seatPlanGroupListLength = this.$scope.seatPlanGroupList.length;
      });
    }


    private getSeatPlanGroupInfo():ng.IPromise<any>{
      var defer = this.$q.defer();
      var seatPlanGroupList:Array<ISeatPlanGroup>;
      this.httpClient.get('/ums-webservice-common/academic/seatPlanGroup/semester/'+this.$scope.semesterId+'/type/'+this.$scope.examType+'/update/'+this.$scope.update, 'application/json',
          (json:any, etag:string) => {
            seatPlanGroupList = json.entries;

            defer.resolve(seatPlanGroupList);
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });
      return defer.promise;
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