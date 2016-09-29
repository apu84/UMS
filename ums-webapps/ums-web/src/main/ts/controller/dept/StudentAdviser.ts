module ums{
  interface IStudentAdviser extends ng.IScope{

  }


  class StudentAdviser{
    public static $inject = ['appConstants','HttpClient','$scope','$q','notify'];
    constructor(private appConstants: any, private httpClient: HttpClient, private $scope: IStudentAdviser,
                private $q:ng.IQService, private notify: Notify) {

    }
  }

  UMS.controller("StudentAdviser",StudentAdviser);
}