///<reference path="../../../../../../ums-web-core/src/main/ts/lib/jquery.tablesorter.d.ts"/>
module ums {
  export class GridSyllabus {
    public static $inject = ['appConstants', '$scope', 'HttpClient'];
    constructor(private appConstants: any, private $scope: any, private httpClient: HttpClient) {
      $scope.data = {
        loadingVisibility: true,
        listVisibility:false
      };

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
            this.$scope.data.loadingVisibility=false;
            this.$scope.data.listVisibility=true;
          });

    }


  }
  UMS.controller('GridSyllabus', GridSyllabus);
}


