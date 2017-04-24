module ums {
  export interface IRecordSearchScope extends ng.IScope {
    data: any;
    recordList : Array<IRecord>;
    pagination: any;
    pageChanged: Function;
    navigateRecord : Function;
    recordIdList : Array<String>;
  }

  export class RecordSearch {
    public static $inject = ['$scope', '$q', 'notify', 'libConstants','catalogingService','$stateParams'];
    constructor(private $scope: IRecordSearchScope,
                private $q: ng.IQService, private notify: Notify, private libConstants: any,
                private catalogingService : CatalogingService, private $stateParams: any) {

      $scope.recordList = Array<IRecord>();
      $scope.recordIdList = Array<String>();
      $scope.data = {
        itemPerPage:10,
        totalRecord:0
      };


      $scope.pagination = {};
      $scope.pagination.currentPage = 1;
      $scope.pageChanged = this.pageChanged.bind(this);
      $scope.navigateRecord = this.navigateRecord.bind(this);

      this.fetchRecords(1);
    }


    // common
    private pageChanged (pageNumber) {
      this.fetchRecords(pageNumber);
    }

    private fetchRecords(pageNumber : number) : void {
      this.catalogingService.fetchRecords(pageNumber,this.$scope.data.itemPerPage,"","").then((response : any ) => {

        this.$scope.recordList = response.entries;
         this.$scope.data.totalRecord = response.total;
        for(var i=0;i < this.$scope.recordList.length;i++){
          this.$scope.recordIdList.push(this.$scope.recordList[i].mfnNo);
        }
        localStorage["lms_records"] = JSON.stringify(this.$scope.recordIdList);
        localStorage["lms_current_index"] = 0;
        localStorage["lms_total_record"] = response.total;
      }, function errorCallback(response) {
        this.notify.error(response);
      });
    }

    private navigateRecord(operation:string, mrnNo:string, pageNumber: number, currentIndex : number) : void {
      localStorage["lms_page"] = pageNumber;
      localStorage["lms_current_index"] = currentIndex;
      window.location.href = "#/cataloging/"+operation+"/record/"+mrnNo;
    }

  }

  UMS.controller("RecordSearch", RecordSearch);
}