module ums {

  export interface IAbc extends ng.IScope {
    statRecords:any;
    statParamModel:any;
    getStatData:Function;
  }

  export class MarksSubmissionStat {
    private statParamModel: ProgramSelectorModel;
    public static $inject = ['$scope', '$stateParams', 'appConstants', 'HttpClient','$q'];
        constructor(private $scope: IAbc, private $stateParams: any,
                private appConstants: any, private httpClient: HttpClient,private $q:ng.IQService) {

          $scope.statParamModel =new ProgramSelectorModel(this.appConstants, this.httpClient);
          $scope.statParamModel.setDepartmentPreset(true);
          $scope.statParamModel.setProgramPreset(true);
          $scope.statParamModel.status="C";
          $scope.statParamModel.examType="1";
          $scope.statParamModel.setProgramTypeId("11",true);
          $scope.statParamModel.enableSemester(true);

          this.$scope.getStatData=this.getStatData.bind(this);
    }

    private getStatData():void{
      this.fetchStatData().then((startData:any)=> {
        this.$scope.statRecords=startData;
        setTimeout(function(){
        $(".tablesorter").trigger("update");
        }, 500);
      });
    }
    private fetchStatData():ng.IPromise<any> {
      var url="academic/gradeSubmission/submissionstat/programtype/"+this.$scope.statParamModel.programTypeId+"/semester/"+this.$scope.statParamModel.semesterId+"/dept/01/examtype/"+
          this.$scope.statParamModel.examType+"/status/"+this.$scope.statParamModel.status;
      var defer = this.$q.defer();
      this.httpClient.get(url, this.appConstants.mimeTypeJson,
          (json:any, etag:string) => {
            var statData:any = json.entries;
            console.log(statData);
            defer.resolve(statData);
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });
      return defer.promise;
    }


  }
  UMS.controller("MarksSubmissionStat", MarksSubmissionStat);
}
