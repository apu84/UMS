module ums{

  export class SupplierService extends  BaseService{
    public static $inject = ['libConstants','HttpClient','$q','notify','$sce','$window','MessageFactory'];
    constructor(private libConstants: any, private httpClient: HttpClient,
                private $q:ng.IQService, private notify: Notify,
                private $sce:ng.ISCEService,private $window:ng.IWindowService, private messageFactory : MessageFactory) {
      super(libConstants,httpClient,$q, notify, $sce, $window, messageFactory);
    }

    public createNewRecord(supplier : ISupplier):ng.IPromise<any> {
      var resourceUrl = "supplier";
      return super.bCreateNewRecord(resourceUrl,resourceUrl,supplier);
    }

    public updateRecord(supplier : ISupplier):ng.IPromise<any> {
      var resourceUrl = "supplier/"+supplier.id;
      return super.bUpdateRecord(resourceUrl, supplier);
    }

    public deleteRecord(supplierId : string) : ng.IPromise<any> {
      var resourceUrl = "supplier/"+supplierId;
      return super.bDeleteRecord(resourceUrl, "supplier");
    }

    public getRecord(supplierId : string): ng.IPromise<any> {
      var resourceUrl = "supplier/"+supplierId
      return super.bGetRecord(resourceUrl);
    }

    /**
     * Get Supplier List for the Supplier Modal Panel
     */
    private fetchDataForPaginationTable(): Function {
      return super.bFetchDataForPaginationTable("supplier","name");
    }

    public fetchAllSuppliers() : ng.IPromise<any> {
      var resourceUrl = "supplier/all";
      return super.bGetAllRecord(resourceUrl);
    }


  }
  UMS.service("supplierService",SupplierService);

}