///<reference path="../../service/HttpClient.ts"/>

module ums {

  export class StudentProfile {
    public static $inject = ['appConstants', '$scope', 'HttpClient'];
    constructor(private appConstants:any,private $scope:any, private httpClient:HttpClient,private location:any) {



    }
  }
  UMS.controller('StudentProfile', StudentProfile);
}
