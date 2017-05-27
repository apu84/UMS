module ums{
    export class PublicationInformationService{
        public static $inject = ['registrarConstants', 'HttpClient', '$q', 'notify', '$sce', '$window'];

        url:string="employee/publication";

        constructor(private registrarConstants: any, private httpClient: HttpClient,
                    private $q: ng.IQService, private notify: Notify,
                    private $sce: ng.ISCEService, private $window: ng.IWindowService) {
        }

        public savePublicationInformation(json: any): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.post(this.url+"/savePublicationInformation", json, 'application/json')
                .success(() => {
                    this.notify.success("Successfully Saved");
                    defer.resolve("Saved");
                }).error((data) => {
                this.notify.error("Error in saving");
                defer.resolve("Error");
            });
            return defer.promise;
        }

        public getPublicationInformation(): ng.IPromise<any> {
            console.log("i am here " + "here too");
            let defer = this.$q.defer();
            this.httpClient.get(this.url+"/getPublicationInformation", HttpClient.MIME_TYPE_JSON,
                (json: any) => {
                    defer.resolve(json.entries);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    console.log(response);
                });
            return defer.promise;
        }

        public getPaginatedPublicationInformation(pageNumber: number, itemPerPage: number): ng.IPromise<any> {
            console.log("i am here " + "here too");
            let defer = this.$q.defer();
            this.httpClient.get(this.url+"/getPublicationInformation/pageNumber/" + pageNumber + "/ipp/" + itemPerPage, HttpClient.MIME_TYPE_JSON,
                (json: any) => {
                    defer.resolve(json.entries);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    console.log(response);
                });
            return defer.promise;
        }

        public getSpecificTeacherPublicationInformation(employeeId: string, status: string): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.get(this.url+"/getPublicationInformation/" + employeeId + "/" + status, HttpClient.MIME_TYPE_JSON,
                (json: any) => {
                    defer.resolve(json.entries);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    console.log(response);
                });
            return defer.promise;
        }

        public fetchRecords(employeeId: string, status: string): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.httpClient.get(this.url+"/getPublicationInformation/" + employeeId + "/" + status, HttpClient.MIME_TYPE_JSON,
                (json: any) => {
                    defer.resolve(json.entries);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    console.log(response);
                });
            return defer.promise;
        }

        // public getPager(totalItems, currentPage, pageSize){
        //
        //     currentPage = currentPage || 1;
        //
        //     pageSize = pageSize || 1;
        //
        //     let totalPages = Math.ceil(totalItems / pageSize);
        //
        //     let startPage, endPage;
        //
        //     if(totalPages <= 10){
        //         startPage = 1;
        //         endPage = totalPages;
        //     }
        //     else{
        //         if (currentPage <= 6) {
        //             startPage = 1;
        //             endPage = 10;
        //         } else if (currentPage + 4 >= totalPages) {
        //             startPage = totalPages - 9;
        //             endPage = totalPages;
        //         } else {
        //             startPage = currentPage - 5;
        //             endPage = currentPage + 4;
        //         }
        //     }
        //
        //     let startIndex = (currentPage - 1) * pageSize;
        //     let endIndex = Math.min(startIndex + pageSize - 1, totalItems - 1);
        //
        //     //var pages = _.range(startPage, endPage + 1);
        //     let pages = Array.from(Array(1), (_,  i) => startPage + i);
        //
        //     console.log("Pages Are");
        //     // console.log(pages);
        //
        //     return {
        //         totalItems: totalItems,
        //         currentPage: currentPage,
        //         pageSize: pageSize,
        //         totalPages: totalPages,
        //         startPage: startPage,
        //         endPage: endPage,
        //         startIndex: startIndex,
        //         endIndex: endIndex,
        //         pages: pages
        //     };
        // }
    }

    UMS.service("publicationInformationService", PublicationInformationService);
}