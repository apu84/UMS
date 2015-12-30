///<reference path="../../lib/jquery.tablesorter.d.ts"/>
module ums {
  export class GridSyllabus {
    public static $inject = ['appConstants','$scope'];
    constructor(private appConstants:any,private $scope:any) {

      $(".tablesorter").tablesorter({
        headers: {
          0: {
            sorter: false
          }
        }
      });

    }


  }
  UMS.controller('GridSyllabus', GridSyllabus);
}


