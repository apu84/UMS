module ums {
    export interface IRecordSearchScope extends ng.IScope {
        data: any;
        recordList: Array<IRecord>;
        pagination: any;
        pageChanged: Function;
        navigateRecord: Function;
        recordIdList: Array<String>;
        search: any;
        choice: string;

        doSearch: Function;
    }

    export class RecordSearch {
        public static $inject = ['$scope', '$q', 'notify', 'libConstants', 'catalogingService', '$stateParams'];

        constructor(private $scope: IRecordSearchScope,
                    private $q: ng.IQService, private notify: Notify, private libConstants: any,
                    private catalogingService: CatalogingService, private $stateParams: any) {

            $scope.recordList = Array<IRecord>();
            $scope.recordIdList = Array<String>();
            $scope.data = {
                itemPerPage: 10,
                totalRecord: 0
            };


            $scope.pagination = {};
            $scope.pagination.currentPage = 1;
            $scope.pageChanged = this.pageChanged.bind(this);
            $scope.navigateRecord = this.navigateRecord.bind(this);
            $scope.doSearch = this.doSearch.bind(this);
            $scope.search = {queryTerm: ""};

            if ($stateParams["1"] == null || $stateParams["1"] == "old") {
                var filter: IFilter = JSON.parse(localStorage.getItem("lms_search_filter"));
                this.$scope.search.queryTerm = filter.basicQueryTerm;
                this.$scope.choice = filter.basicQueryField;

            } else {
                this.$scope.search.searchType = "basic";
                this.$scope.choice = "any";
            }

            this.prepareFilter();

            if ($stateParams["1"] == null || $stateParams["1"] == "old") {
                var page = Number(localStorage.getItem("lms_page"));
                $scope.pagination.currentPage = page;
                this.fetchRecords(page);
            }
            else {
                this.fetchRecords(1);
            }


        }

        private prepareFilter() {
            var filter: IFilter = <IFilter> {};

            filter.searchType = "basic";
            filter.basicQueryField = this.$scope.choice;
            filter.basicQueryTerm = this.$scope.search.queryTerm;
            this.$scope.search.filter = filter;
            localStorage["lms_search_filter"] = JSON.stringify(filter);
        }

        private doSearch(): void {
            this.prepareFilter();
            this.fetchRecords(1);
        }


        // common
        private pageChanged(pageNumber) {
            this.fetchRecords(pageNumber);
        }

        private fetchRecords(pageNumber: number): void {
            this.catalogingService.fetchRecords(pageNumber, this.$scope.data.itemPerPage, "", this.$scope.search.filter).then((response: any) => {
                this.$scope.recordIdList = Array<String>();
                this.$scope.recordList = response.entries;
                this.$scope.data.totalRecord = response.total;
                for (var i = 0; i < this.$scope.recordList.length; i++) {
                    this.$scope.recordIdList.push(this.$scope.recordList[i].mfnNo);
                }

                localStorage["lms_records"] = JSON.stringify(this.$scope.recordIdList);
                localStorage["lms_current_index"] = 0;
                localStorage["lms_total_record"] = response.total;
                localStorage["lms_page"] = pageNumber;

            }, function errorCallback(response) {
                this.notify.error(response);
            });
        }

        private navigateRecord(operation: string, mrnNo: string, pageNumber: number, currentIndex: number): void {
            localStorage["lms_page"] = pageNumber;
            localStorage["lms_current_index"] = currentIndex;
            window.location.href = "#/cataloging/" + operation + "/record/" + mrnNo;
        }

    }

    UMS.controller("RecordSearch", RecordSearch);
}