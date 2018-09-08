module ums {

    import IAdvancedSearchMap = ums.IAdvancedSearchMap;

    export class RecordSearch {
        public static $inject = [
            '$q',
            'notify',
            'libConstants',
            'catalogingService',
            '$stateParams',
            'supplierService',
            'publisherService',
            'contributorService',
            'contributor',
            'supplier',
            'publisher'
        ];

        private data: any;
        private recordList: Array<IRecord>;
        private pagination: any;
        private recordIdList: Array<String>;
        private search: any;
        private choice: string;
        private choiceType: string;
        private record: IRecord;
        private supplierList: Array<ISupplier>;
        private publisherList: Array<IPublisher>;
        private contributorList: Array<IContributor>;

        constructor(private $q: ng.IQService,
                    private notify: Notify,
                    private libConstants: any,
                    private catalogingService: CatalogingService,
                    private $stateParams: any,
                    private supplierService: SupplierService,
                    private publisherService: PublisherService,
                    private contributorService: ContributorService,
                    private contributor: any,
                    private supplier: any,
                    private publisher: any) {

            this.recordList = Array<IRecord>();
            this.recordIdList = Array<String>();
            this.data = {
                itemPerPage: 10,
                totalRecord: 0,
                materialTypeOptions: libConstants.materialTypes,
                contributorRoles: libConstants.libContributorRoles
            };
            this.pagination = {};
            this.pagination.currentPage = 1;
            this.search = {
                queryTerm: '',
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

            if ($stateParams["1"] == null || $stateParams["1"] == "old") {
                let filter: ISearchFilter = JSON.parse(localStorage.getItem("lms_search_filter"));
                this.search.queryTerm = filter.basicQueryTerm;
                this.choice = filter.basicQueryField;
                this.choiceType = localStorage.getItem("lms_search_type");
            } else {
                this.search.searchType = "basic";
                this.choice = "any";
                this.choiceType = "Exact";
            }

            this.prepareFilter();

            if ($stateParams["1"] == null || $stateParams["1"] == "old") {
                let page = Number(localStorage.getItem("lms_page"));
                this.pagination.currentPage = page;
                this.fetchRecords(page);
            }
            else {
                this.fetchRecords(1);
            }

            this.contributorList = contributor;
            this.supplierList = supplier;
            this.publisherList = publisher;

            this.search.itemType = "Book";
        }

        private prepareFilter() {
            let filter: ISearchFilter = <ISearchFilter> {};
            filter.searchType = this.search.searchType;
            if (this.search.searchType == 'basic') {
                filter.basicQueryField = this.choice;
                filter.basicQueryTerm = this.search.queryTerm;

                if (this.choiceType == "Exact") {
                }
                else if (this.choiceType == "Likely") {
                    filter.basicQueryTerm = "*" + this.search.queryTerm + "*";
                }
            }
            else if (this.search.searchType == 'advanced_search') {
                filter.advancedSearchFilter = [];
                let advanceSearchMap1: IAdvancedSearchMap = <IAdvancedSearchMap>{};
                advanceSearchMap1.key = 'materialType_s';
                advanceSearchMap1.value = this.search.itemType;
                filter.advancedSearchFilter.push(advanceSearchMap1);
                if (this.search.title) {
                    let advanceSearchMap2: IAdvancedSearchMap = <IAdvancedSearchMap>{};
                    advanceSearchMap2.key = 'title_t';
                    advanceSearchMap2.value = "*" + this.search.title + "*";
                    filter.advancedSearchFilter.push(advanceSearchMap2);
                }
                if (this.search.author) {
                    let advanceSearchMap3: IAdvancedSearchMap = <IAdvancedSearchMap>{};
                    advanceSearchMap3.key = 'contributors_txt';
                    advanceSearchMap3.value = "*" + this.search.author + "*";
                    filter.advancedSearchFilter.push(advanceSearchMap3);
                }
                if (this.search.subject) {
                    let advanceSearchMap4: IAdvancedSearchMap = <IAdvancedSearchMap>{};
                    advanceSearchMap4.key = 'subjects_txt';
                    advanceSearchMap4.value = "*" + this.search.subject + "*";
                    filter.advancedSearchFilter.push(advanceSearchMap4);
                }
                if (this.search.corpAuthor) {
                    let advanceSearchMap5: IAdvancedSearchMap = <IAdvancedSearchMap>{};
                    advanceSearchMap5.key = 'corpAuthorMain_s';
                    advanceSearchMap5.value = "*" + this.search.corpAuthor + "*" ;
                    filter.advancedSearchFilter.push(advanceSearchMap5);
                }
                if (this.search.publisher) {
                    let advanceSearchMap6: IAdvancedSearchMap = <IAdvancedSearchMap>{};
                    advanceSearchMap6.key = 'publisher_txt';
                    advanceSearchMap6.value = "*" + this.search.publisher + "*";
                    filter.advancedSearchFilter.push(advanceSearchMap6);
                }
                if (this.search.yearFrom && this.search.yearTo) {
                    let advanceSearchMap7: IAdvancedSearchMap = <IAdvancedSearchMap>{};
                    advanceSearchMap7.key = 'yearOfPublication_i';
                    advanceSearchMap7.value = "[" + this.search.yearFrom + " TO " + this.search.yearTo + "]";
                    filter.advancedSearchFilter.push(advanceSearchMap7);
                }
            }
            this.search.filter = filter;
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
            this.catalogingService.fetchRecords(pageNumber, this.data.itemPerPage, "", this.search.filter).then((response: any) => {
                this.recordIdList = Array<String>();
                this.recordList = response.entries;
                this.data.totalRecord = response.total;
                this.prepareRecord();
                for (let i = 0; i < this.recordList.length; i++) {
                    this.recordIdList.push(this.recordList[i].mfnNo);
                }

                localStorage["lms_records"] = JSON.stringify(this.recordIdList);
                localStorage["lms_current_index"] = 0;
                localStorage["lms_total_record"] = response.total;
                localStorage["lms_page"] = pageNumber;
                localStorage["lms_search_type"] = this.choiceType;

            }, function errorCallback(response) {
                this.notify.error(response);
            });
        }

        private navigateRecord(operation: string, mfnNo: string, pageNumber: number, currentIndex: number): void {
            localStorage["lms_page"] = pageNumber;
            localStorage["lms_current_index"] = currentIndex;
            window.location.href = "#/cataloging/" + operation + "/record/" + mfnNo;
        }

        private recordDetails(recordIndex: number) {
            this.record = <IRecord>{};
            this.record = this.recordList[recordIndex];
        }

        private prepareRecord(): void {
            for (let index = 0; index < this.recordList.length; index++) {
                this.recordList[index].contributorList = Array<IContributor>();
                this.recordList[index].subjectList = Array<ISubjectEntry>();
                this.recordList[index].noteList = Array<INoteEntry>();

                let contributorJsonStringObj = $.parseJSON(this.recordList[index].contributorJsonString);

                if (contributorJsonStringObj != null) {
                    for (let i = 0; i < contributorJsonStringObj.length; i++) {
                        let contributor = <IContributor> {};
                        contributor.viewOrder = contributorJsonStringObj[i].viewOrder;
                        contributor.role = contributorJsonStringObj[i].role;
                        angular.forEach(this.data.contributorRoles, (attr: any) => {
                            if (attr.id == contributorJsonStringObj[i].role) {
                                contributor.roleName = attr.name;
                            }
                        });
                        contributor.id = contributorJsonStringObj[i].id;
                        contributor.name = this.contributorList[this.contributorList.map(function (e) {
                            return e.id;
                        }).indexOf(contributorJsonStringObj[i].id)].name;

                        this.recordList[index].contributorList.push(contributor);
                    }
                }
                let noteJsonStringObj = $.parseJSON(this.recordList[index].noteJsonString);
                if (noteJsonStringObj != null) {
                    for (let i = 0; i < noteJsonStringObj.length; i++) {
                        let note = {viewOrder: noteJsonStringObj[i].viewOrder, note: noteJsonStringObj[i].note};
                        this.recordList[index].noteList.push(note);
                    }
                }
                let subjectJsonStringObj = $.parseJSON(this.recordList[index].subjectJsonString);
                if (subjectJsonStringObj != null) {
                    for (let i = 0; i < subjectJsonStringObj.length; i++) {
                        let subject = {
                            viewOrder: subjectJsonStringObj[i].viewOrder,
                            subject: subjectJsonStringObj[i].subject
                        };
                        this.recordList[index].subjectList.push(subject);
                    }
                }

                let physicalDescriptionStringObj = $.parseJSON(this.recordList[index].physicalDescriptionString);
                if (physicalDescriptionStringObj != null) {
                    this.recordList[index].physicalDescription = {
                        pagination: physicalDescriptionStringObj.pagination,
                        illustrations: physicalDescriptionStringObj.illustrations,
                        accompanyingMaterials: physicalDescriptionStringObj.accompanyingMaterials,
                        dimensions: physicalDescriptionStringObj.dimensions
                    };
                }
            }
        }
    }


    UMS.controller("RecordSearch", RecordSearch);
}