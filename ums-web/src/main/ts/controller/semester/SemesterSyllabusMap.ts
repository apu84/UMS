///<reference path="../../service/HttpClient.ts"/>
///<reference path="../../service/FileUpload.ts"/>
///<reference path="../../model/SemesterSyllabusMapModel.ts"/>

module ums {
  interface SemesterSyllabusMapScope extends ng.IScope {
    semesterSyllabusMapModel: SemesterSyllabusMapModel;
    submit: Function;
  }
  export class SemesterSyllabusMap {
    public static $inject = ['appConstants', '$scope', 'HttpClient'];

    constructor(private appConstants:any, private $scope:SemesterSyllabusMapScope, private httpClient:HttpClient) {

      $scope.semesterSyllabusMapModel = new SemesterSyllabusMapModel(appConstants, httpClient);
      //$scope.submit = this.submit.bind(this);

    }

  }
  UMS.controller('SemesterSyllabusMap', SemesterSyllabusMap);
}

