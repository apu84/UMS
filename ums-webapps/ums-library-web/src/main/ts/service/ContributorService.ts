module ums {

    export class ContributorService extends BaseService {
        public static $inject = ['libConstants', 'HttpClient', '$q', 'notify', '$sce', '$window', 'MessageFactory'];

        constructor(private libConstants: any, private httpClient: HttpClient,
                    private $q: ng.IQService, private notify: Notify,
                    private $sce: ng.ISCEService, private $window: ng.IWindowService, private messageFactory: MessageFactory) {

            super(libConstants, httpClient, $q, notify, $sce, $window, messageFactory);
        }

        public createNewRecord(supplier: ISupplier): ng.IPromise<any> {
            var resourceUrl = "contributor";
            return super.bCreateNewRecord(resourceUrl, resourceUrl, supplier);
        }

        public updateRecord(supplier: ISupplier): ng.IPromise<any> {
            var resourceUrl = "contributor/" + supplier.id;
            return super.bUpdateRecord(resourceUrl, supplier, "contributor");
        }

        public deleteRecord(supplierId: string): ng.IPromise<any> {
            var resourceUrl = "contributor/" + supplierId;
            return super.bDeleteRecord(resourceUrl, "supplier");
        }


        public getRecord(supplierId: string): ng.IPromise<any> {
            var resourceUrl = "contributor/" + supplierId
            return super.bGetRecord(resourceUrl);
        }

        private fetchDataForPaginationTable(): Function {
            return super.bFetchDataForPaginationTable("contributor", "full_name");
        }


        public fetchAllContributors(): ng.IPromise<any> {
            var resourceUrl = "contributor/all";
            return super.bGetAllRecord(resourceUrl);
        }

    }

    UMS.service("contributorService", ContributorService);
}