module ums {
    export class PagerService {
        public static $inject = ['registrarConstants', 'HttpClient', '$q', 'notify', '$sce', '$window'];

        constructor(private registrarConstants: any, private httpClient: HttpClient,
                    private $q: ng.IQService, private notify: Notify,
                    private $sce: ng.ISCEService, private $window: ng.IWindowService) {
        }

        public getPager(totalItems: number, currentPage: number, pageSize: number){

            currentPage = currentPage || 1;

            pageSize = pageSize || 1;

            let totalPages = Math.ceil(totalItems / pageSize);

            let startPage: number;

            let endPage: number;

            if(totalPages <= 10){
                startPage = 1;
                endPage = totalPages;
            }
            else{
                if (currentPage <= 6) {
                    startPage = 1;
                    endPage = 10;
                } else if (currentPage + 4 >= totalPages) {
                    startPage = totalPages - 9;
                    endPage = totalPages;
                } else {
                    startPage = currentPage - 5;
                    endPage = currentPage + 4;
                }
            }

            let startIndex = (currentPage - 1) * pageSize;
            let endIndex = Math.min(startIndex + pageSize - 1, totalItems - 1);

            // let pages = Array.from(Array(1), (_,  i) => startPage + i);

            return this.convertToJson(totalItems, currentPage, pageSize, totalPages, startPage, endPage, startIndex, endIndex);
        }

        public convertToJson(totalItems: number, currentPage: number, pageSize: number, totalPages: number, startPages: number, endPage: number, startIndex: number, endIndex: number){
            let defer = this.$q.defer();
            let jsonObject = {};

            //jsonObject['totalItems'] = totalItems;
            jsonObject['currentPage'] = currentPage;
            //jsonObject['pageSize'] = pageSize;
            //jsonObject['totalPages'] = totalPages;
            //jsonObject['startPages'] = startIndex;
            //jsonObject['endPages'] = endPage;
            jsonObject['startIndex'] = startIndex;
            jsonObject['endIndex'] = endIndex;
            // jsonObject['pages'] = pages;

            console.log("In Pager Json Convertetr");
            console.log(jsonObject);

            defer.resolve(jsonObject);
            return defer.promise;
        }
    }

    UMS.service("pagerService", PagerService);
}