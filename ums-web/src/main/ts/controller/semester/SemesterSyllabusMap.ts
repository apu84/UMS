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
  }
  export class SemesterSyllabusMap {
    public static $inject = ['appConstants', '$scope', 'HttpClient'];

    constructor(private appConstants:any, private $scope:SemesterSyllabusMapScope, private httpClient:HttpClient) {

      $scope.semesterSyllabusMapModel = new SemesterSyllabusMapModel(appConstants, httpClient);
      $scope.semesterSyllabusMapModel.programTypes.pop();
      $scope.goNext= this.goNext.bind(this);
      $scope.fetchSSmap= this.fetchSSmap.bind(this);

    }

    private goNext():void {

        this.httpClient.get('academic/ssmap/program/110500/semester/11012015', 'application/json',
            (json:any, etag:string) => {
              this.$scope.maps = json.entries;
            },
            (response:ng.IHttpPromiseCallbackArg<any>) => {
              console.error(response);
            });


    }

    private fetchSSmap(mapId):void {
      this.httpClient.get('academic/ssmap/'+mapId, 'application/json',
          (json:any, etag:string) => {
            this.$scope.single=json;
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });


    }


  }
  UMS.controller('SemesterSyllabusMap', SemesterSyllabusMap);
}

