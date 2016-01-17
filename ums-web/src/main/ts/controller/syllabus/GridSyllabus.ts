///<reference path="../../lib/jquery.tablesorter.d.ts"/>
module ums {
  export class GridSyllabus {
    public static $inject = ['appConstants', '$scope', 'HttpClient'];

    constructor(private appConstants: any, private $scope: any, private httpClient: HttpClient) {

      $(".tablesorter").tablesorter({
        headers: {
          0: {
            sorter: false
          }
        }
      });

      httpClient.get("academic/syllabus/all", 'application/json',
          (data: any, etag: string) => {
            this.$scope.syllabuses = data.entries;
          });

    }


  }
  UMS.controller('GridSyllabus', GridSyllabus);
}


