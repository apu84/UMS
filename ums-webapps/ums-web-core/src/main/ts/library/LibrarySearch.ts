module ums {
    export interface IRecordSearch extends ng.IScope {
        data: any;
        recordList: Array<IRecord>;
        pagination: any;
        pageChanged: Function;
        navigateRecord: Function;
        recordIdList: Array<String>;
        search: any;
        choice: string;

        doSearch: Function;

        record: IRecord;
        recordDetails: Function;

        supplierList: Array<ISupplier>;
        publisherList: Array<IPublisher>;
        contributorList: Array<IContributor>;
    }

    export class LibrarySearch {
        public static $inject = ['$scope', '$q', 'notify', 'libConstants', 'HttpClient'];

        constructor(private $scope: IRecordSearch,
                    private $q: ng.IQService, private notify: Notify, private libConstants: any, private httpClient: HttpClient) {

            $scope.recordList = Array<IRecord>();
            $scope.recordIdList = Array<String>();
            $scope.data = {
                itemPerPage: 10,
                totalRecord: 0,
                materialTypeOptions: libConstants.materialTypes,
                contributorRoles: libConstants.libContributorRoles
            };


            $scope.pagination = {};
            $scope.pagination.currentPage = 1;
            $scope.pageChanged = this.pageChanged.bind(this);
            $scope.navigateRecord = this.navigateRecord.bind(this);
            $scope.doSearch = this.doSearch.bind(this);
            $scope.search = {
                queryTerm: null,
                searchType: 'basic',
                itemType: Array(),
                title: '',
                author: '',
                subject: '',
                corpAuthor: '',
                publisher: '',
                yearFrom: '',
                yearTo: ''
            };

            $scope.recordDetails = this.recordDetails.bind(this);

            this.$scope.search.searchType = "basic";
            this.$scope.choice = "any";

            this.prepareFilter();

            this.fetchRecords(1);

            this.getAllSuppliers();
            this.getAllContributors();
            this.getAllPublishers();
        }

        private prepareFilter() {
            var filter: IFilter = <IFilter> {};

            filter.searchType = this.$scope.search.searchType;
            if (this.$scope.search.searchType == 'basic') {
                filter.basicQueryField = this.$scope.choice;
                filter.basicQueryTerm = this.$scope.search.queryTerm;
            }
            else if (this.$scope.search.searchType == 'advanced') {

            }
            this.$scope.search.filter = filter;
            localStorage["lms_search_filter"] = JSON.stringify(filter);
        }

        private doSearch(): void {
            this.prepareFilter();
            this.fetchRecords(1);
        }


        // common
        private pageChanged(pageNumber, randomValue) {
            if(randomValue == 1) {
                this.fetchRecords(pageNumber);
            }
        }

        private fetchRecords(pageNumber: number): void {
            this.getRecords(pageNumber, this.$scope.data.itemPerPage, "", this.$scope.search.filter).then((response: any) => {
                this.$scope.recordIdList = Array<String>();
                this.$scope.recordList = response.entries;
                this.$scope.data.totalRecord = response.total;
                this.prepareRecord();
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

        private getRecords(page: number, itemPerPage: number, orderBy: string, filter: any): ng.IPromise<any> {

            var defer = this.$q.defer();
            var tPage = page - 1;

            var resourceUrl = "/ums-webservice-library/record/all/ipp/" + itemPerPage + "/page/" + tPage + "/order/3?filter=" + encodeURIComponent(JSON.stringify(filter));


            this.httpClient.get(resourceUrl, 'application/json',
                (json: any, etag: string) => {
                    defer.resolve(json);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    console.error(response);
                });
            return defer.promise;
        }

        private navigateRecord(operation: string, mrnNo: string, pageNumber: number, currentIndex: number): void {
            localStorage["lms_page"] = pageNumber;
            localStorage["lms_current_index"] = currentIndex;
            window.location.href = "#/cataloging/" + operation + "/record/" + mrnNo;
        }

        private recordDetails(recordIndex: number) {
            this.$scope.record = <IRecord>{};
            this.$scope.record = this.$scope.recordList[recordIndex];
        }

        private prepareRecord(): void {
            for (var index = 0; index < this.$scope.recordList.length; index++) {
                this.$scope.recordList[index].contributorList = Array<IContributor>();
                this.$scope.recordList[index].subjectList = Array<ISubjectEntry>();
                this.$scope.recordList[index].noteList = Array<INoteEntry>();

                var jsonObj = $.parseJSON(this.$scope.recordList[index].contributorJsonString);

                if (jsonObj != null) {
                    for (var i = 0; i < jsonObj.length; i++) {
                        var contributor = <IContributor> {};
                        contributor.viewOrder = jsonObj[i].viewOrder;
                        contributor.role = jsonObj[i].role;
                        angular.forEach(this.$scope.data.contributorRoles, (attr: any) => {
                            if (attr.id == jsonObj[i].role) {
                                contributor.roleName = attr.name;
                            }
                        });
                        contributor.id = jsonObj[i].id;
                        contributor.name = this.$scope.contributorList[this.$scope.contributorList.map(function (e) {
                            return e.id;
                        }).indexOf(jsonObj[i].id)].name;

                        this.$scope.recordList[index].contributorList.push(contributor);
                    }
                }
                var jsonObj = $.parseJSON(this.$scope.recordList[index].noteJsonString);
                if (jsonObj != null) {
                    for (var i = 0; i < jsonObj.length; i++) {
                        var note = {viewOrder: jsonObj[i].viewOrder, note: jsonObj[i].note};
                        this.$scope.recordList[index].noteList.push(note);
                    }
                }
                var jsonObj = $.parseJSON(this.$scope.recordList[index].subjectJsonString);
                if (jsonObj != null) {
                    for (var i = 0; i < jsonObj.length; i++) {
                        var subject = {viewOrder: jsonObj[i].viewOrder, subject: jsonObj[i].subject};
                        this.$scope.recordList[index].subjectList.push(subject);
                    }
                }

                var jsonObj = $.parseJSON(this.$scope.recordList[index].physicalDescriptionString);
                if (jsonObj != null) {
                    var physicalDescription = {
                        pagination: jsonObj.pagination,
                        illustrations: jsonObj.illustrations,
                        accompanyingMaterials: jsonObj.accompanyingMaterials,
                        dimensions: jsonObj.dimensions,
                        paperQuality: jsonObj.paperQuality
                    };
                    this.$scope.recordList[index].physicalDescription = physicalDescription;
                }
            }
        }

        private getAllSuppliers(): void {
            this.fetchAllSuppliers().then((response: any) => {
                this.$scope.supplierList = response.entries;
            }, function errorCallback(response) {
                this.notify.error(response);
            });
        }

        private fetchAllSuppliers(): ng.IPromise<any> {
            var resourceUrl = "/ums-webservice-library/supplier/all";
            var defer = this.$q.defer();
            this.httpClient.get(resourceUrl, 'application/json',
                (json: any, etag: string) => {
                    defer.resolve(json);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    console.error(response);
                });
            return defer.promise;
        }

        private getAllPublishers(): void {
            this.fetchAllPublishers().then((response: any) => {
                this.$scope.publisherList = response.entries;
            }, function errorCallback(response) {
                this.notify.error(response);
            });
        }

        private fetchAllPublishers(): ng.IPromise<any> {
            var resourceUrl = "/ums-webservice-library/publisher/all";
            var defer = this.$q.defer();
            this.httpClient.get(resourceUrl, 'application/json',
                (json: any, etag: string) => {
                    defer.resolve(json);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    console.error(response);
                });
            return defer.promise;
        }

        private getAllContributors(): void {
            this.fetchAllContributors().then((response: any) => {
                this.$scope.contributorList = response.entries;
            }, function errorCallback(response) {
                this.notify.error(response);
            });
        }

        private fetchAllContributors(): ng.IPromise<any> {
            var resourceUrl = "/ums-webservice-library/contributor/all";
            var defer = this.$q.defer();
            this.httpClient.get(resourceUrl, 'application/json',
                (json: any, etag: string) => {
                    defer.resolve(json);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    console.error(response);
                });
            return defer.promise;
        }

    }


    UMS.controller("LibrarySearch", LibrarySearch);
}