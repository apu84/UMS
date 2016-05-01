module ums {
  export interface IMarksSubmissionScope extends ng.IScope {
    visibleProjects: any;
  }
  var abc=[{
    facility: "Atlanta",
    code: "C-RD34",
    cost: 540000,
    conditionRating: 52,
    extent: 100,
    planYear: 2014
  }, {
    facility: "Seattle",
    code: "CRDm-4",
    cost: 23000,
    conditionRating: 40,
    extent: 88,
    planYear: 2014
  }, {
    facility: "Austin",
    code: "GR-5",
    cost: 1200000,
    conditionRating: 92,
    extent: 90,
    planYear: 2014
  }, {
    facility: "Dayton",
    code: "LY-7",
    cost: 123000,
    conditionRating: 71,
    extent: 98,
    planYear: 2014
  }, {
    facility: "Portland",
    code: "Dm-4",
    cost: 149000,
    conditionRating: 89,
    extent: 77,
    planYear: 2014
  }, {
    facility: "Dallas",
    code: "AW-3",
    cost: 14000,
    conditionRating: 89,
    extent: 79,
    planYear: 2014
  }, {
    facility: "Houston",
    code: "Dm-4",
    cost: 1100000,
    conditionRating: 93,
    extent: 79,
    planYear: 2014
  }, {
    facility: "Boston",
    code: "DD3",
    cost: 1940000,
    conditionRating: 86,
    extent: 80,
    planYear: 2015
  }, {
    facility: "New York",
    code: "ER1",
    cost: 910000,
    conditionRating: 87,
    extent: 82,
    planYear: 2015
  }];
  export class MarksSubmission {
    public static $inject = ['$scope', 'appConstants', 'HttpClient'];

    constructor(private $scope:IMarksSubmissionScope,
                private appConstants:any,
                private httpClient:HttpClient) {

      $scope.visibleProjects = abc;

    }

  }

  UMS.controller('MarksSubmission', MarksSubmission);
}
