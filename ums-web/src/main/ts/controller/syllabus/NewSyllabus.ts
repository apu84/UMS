///<reference path="../../model/master_data/Syllabus.ts"/>
///<reference path="../../model/NewSyllabusModel.ts"/>
///<reference path="../../model/NewSyllabusModel.ts"/>
///<reference path="../../../../../../ums-web-core/src/main/ts/service/HttpClient.ts"/>
///<reference path="../../../../../../ums-web-core/src/main/ts/lib/jquery.notific8.d.ts"/>

module ums {
  interface INewSyllabusScope extends ng.IScope {
    submit: Function;
    newSyllabusModel: NewSyllabusModel;
  }
  export class NewSyllabus {
    public static $inject = ['appConstants', 'HttpClient','$scope'];
    constructor(private appConstants:any,private httpClient:HttpClient,private $scope:INewSyllabusScope) {
      $scope.newSyllabusModel = new NewSyllabusModel(this.appConstants, this.httpClient);
      $scope.submit = this.submit.bind(this);
    }
    private submit():void {
      this.$scope.newSyllabusModel.syllabusId
          = this.$scope.newSyllabusModel.semesterId + "_" + this.$scope.newSyllabusModel.programSelector.programId;

      this.httpClient.post('academic/syllabus/', this.$scope.newSyllabusModel, 'application/json')
          .success((data, status, headers) => {
            //console.debug("Syllabus created, resource location : " + headers('location'));
            $.notific8('Successfully created a new Syllabus.');
          }).error((data) => {
            console.error(data);
          });

    }
  }
  UMS.controller('NewSyllabus', NewSyllabus);
}

