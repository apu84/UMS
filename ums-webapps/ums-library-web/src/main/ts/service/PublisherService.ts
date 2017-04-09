module ums{

  export class PublisherService extends  BaseService {
    public static $inject = ['libConstants','HttpClient','$q','notify','$sce','$window','MessageFactory'];

    constructor(private libConstants: any, private httpClient: HttpClient,
                private $q:ng.IQService, private notify: Notify,
                private $sce:ng.ISCEService,private $window:ng.IWindowService, private messageFactory : MessageFactory) {

      super(libConstants,httpClient,$q, notify, $sce, $window, messageFactory);
    }

    public createNewRecord(supplier : ISupplier):ng.IPromise<any> {
      var resourceUrl = "publisher";
      return super.bCreateNewRecord(resourceUrl,resourceUrl,supplier);
    }

    public updateRecord(supplier : ISupplier):ng.IPromise<any> {
      var resourceUrl = "publisher/"+supplier.id;
      return super.bUpdateRecord(resourceUrl, supplier);
    }

    public deleteRecord(supplierId : string) : ng.IPromise<any> {
      var resourceUrl = "publisher/"+supplierId;
      return super.bDeleteRecord(resourceUrl, "supplier");
    }

    public getRecord(supplierId : string): ng.IPromise<any> {
      var resourceUrl = "publisher/"+supplierId
      return super.bGetRecord(resourceUrl);
    }

    private fetchDataForPaginationTable(): Function {
      return super.bFetchDataForPaginationTable("publisher","name");
    }


  }
  UMS.service("publisherService",PublisherService);

}