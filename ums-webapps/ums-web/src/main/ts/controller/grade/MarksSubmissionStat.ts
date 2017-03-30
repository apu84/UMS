module ums {

  export interface IAbc extends ng.IScope {
    statRecords: any;
    statParamModel: any;
    getStatData: Function;
    status: string;
    showStatTable : boolean;
    statLoading: boolean;
  }

  export class MarksSubmissionStat {
    private statParamModel: ProgramSelectorModel;
    public static $inject = ['$scope', '$stateParams', 'appConstants', 'HttpClient', '$q'];

    constructor(private $scope: IAbc, private $stateParams: any,
                private appConstants: any, private httpClient: HttpClient, private $q: ng.IQService) {

      $scope.statParamModel = new ProgramSelectorModel(this.appConstants, this.httpClient, true);
      $scope.statParamModel.setDepartment(undefined, FieldViewTypes.hidden);
      $scope.statParamModel.setProgram(undefined, FieldViewTypes.hidden);
      $scope.statParamModel.status = "C";
      $scope.statParamModel.examType = "1";
      $scope.statParamModel.setProgramType("11", true);
      $scope.showStatTable = false;
      $scope.statLoading = false;
      // $scope.statParamModel.enableSemesterOption(true);

      this.$scope.getStatData = this.getStatData.bind(this);
    }

    private getStatData(): void {
      if(this.$scope.statParamModel.status == "S")
        this.$scope.status = "Submitted";
      else if(this.$scope.statParamModel.status == "H")
        this.$scope.status = "Head Approved";
      else if(this.$scope.statParamModel.status == "C")
        this.$scope.status = "CoE Accepted";

      this.$scope.showStatTable = false;
      this.$scope.statLoading = true;

      this.fetchStatData().then((startData: any) => {
        this.$scope.statRecords = startData;

        this.$scope.statLoading = false;
        this.$scope.showStatTable = true;

        setTimeout(function () {
          $(".tablesorter").trigger("update");
        }, 600);
      });
    }



    private fetchStatData(): ng.IPromise<any> {
      var url = "academic/gradeSubmission/submissionstat/programtype/" + this.$scope.statParamModel.programTypeId + "/semester/" + this.$scope.statParamModel.semesterId + "/dept/01/examtype/" +
          this.$scope.statParamModel.examType + "/status/" + this.$scope.statParamModel.status;
      var defer = this.$q.defer();
      this.httpClient.get(url, this.appConstants.mimeTypeJson,
          (json: any, etag: string) => {

            var statData: any = json.entries;
            defer.resolve(statData);
          },
          (response: ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });
      return defer.promise;
    }


  }
  UMS.controller("MarksSubmissionStat", MarksSubmissionStat);
}
