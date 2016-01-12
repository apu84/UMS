///<reference path="../../service/HttpClient.ts"/>
///<reference path="../../service/FileUpload.ts"/>
///<reference path="../../model/SemesterSyllabusMapModel.ts"/>

module ums {
  interface SemesterSyllabusMapScope extends ng.IScope {
    semesterSyllabusMapModel: SemesterSyllabusMapModel;
    submit: Function;
    goNext:Function;
    fetchSSmap:Function;
    map:any;
    maps:any;
    single:any;
    syllabusOptions: any;
    loadingVisibility1:boolean;
    loadingVisibility2:boolean;
    mapTableVisibility:boolean;
    mapDetailVisiblity:boolean;

  }
  export class SemesterSyllabusMap {
    public static $inject = ['appConstants', '$scope', 'HttpClient'];

    constructor(private appConstants:any, private $scope:SemesterSyllabusMapScope, private httpClient:HttpClient) {

      $scope.semesterSyllabusMapModel = new SemesterSyllabusMapModel(appConstants, httpClient);
      $scope.semesterSyllabusMapModel.programTypes.pop();
      $scope.goNext= this.goNext.bind(this);
      $scope.fetchSSmap= this.fetchSSmap.bind(this);

      $scope.mapTableVisibility=false;

      $scope.$watch(
          // This function returns the value being watched. It is called for each turn of the $digest loop
          () => {
            return $scope.semesterSyllabusMapModel.departmentId;
          },
          // This is the change listener, called when the value returned from the above function changes
          (newValue, oldValue) => {
            if (newValue !== oldValue) {
              // Only increment the counter if the value changed
              console.debug("deparmentId changed to: %o", newValue);
            }
          }
      );

    }

    private goNext():void {
      this.$scope.loadingVisibility1=true;
      this.$scope.mapTableVisibility=false;
      this.$scope.mapDetailVisiblity=false;
        this.httpClient.get('academic/ssmap/program/110500/semester/11012015', 'application/json',
            (json:any, etag:string) => {
              this.$scope.loadingVisibility1=false;
              this.$scope.maps = json.entries;
              this.$scope.mapTableVisibility=true;
            },
            (response:ng.IHttpPromiseCallbackArg<any>) => {
              console.error(response);
              this.$scope.loadingVisibility1=false;
            });


    }

    private fetchSSmap(mapId):void {
      this.$scope.loadingVisibility2=true;
      this.$scope.mapDetailVisiblity=false;

      this.httpClient.get('academic/ssmap/'+mapId, 'application/json',
          (json:any, etag:string) => {
            this.$scope.loadingVisibility2=false;
            this.$scope.single=json;
            this.$scope.mapDetailVisiblity=true;
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            this.$scope.loadingVisibility2=false;
            console.error(response);
          });


    }


  }
  UMS.controller('SemesterSyllabusMap', SemesterSyllabusMap);
}

