module ums {
  export interface IRecordSearchScope extends ng.IScope {
    data: any;
    recordList : Array<IRecord>;
  }

  export class RecordSearch {
    public static $inject = ['$scope', '$q', 'notify', 'libConstants','catalogingService'];
    constructor(private $scope: ICatalogingScope,
                private $q: ng.IQService, private notify: Notify, private libConstants: any,
                private catalogingService : CatalogingService) {

      $scope.recordList = Array<IRecord>();

      $scope.data = {

      };

      this.fetchRecords();
    }

    private fetchRecords() : void {
      this.catalogingService.fetchRecords().then((response : any ) => {
        console.log(response);
        console.log( response.entries);
        this.$scope.recordList = response;

        console.log(this.$scope.recordList);
      }, function errorCallback(response) {
        this.notify.error(response);
      });
    }


  }

  UMS.controller("RecordSearch", RecordSearch);
}