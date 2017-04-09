module ums{

  export class SupplierService{
    public static $inject = ['libConstants','HttpClient','$q','notify','$sce','$window'];
    constructor(private libConstants: any, private httpClient: HttpClient,
                private $q:ng.IQService, private notify: Notify,
                private $sce:ng.ISCEService,private $window:ng.IWindowService) {
    }

    public createNewRecord(supplier : ISupplier):ng.IPromise<any> {
      var resourceUrl = "supplier";
      var defer = this.$q.defer();
      console.log( this.libConstants.mimeTypeJson);
      this.httpClient.post(resourceUrl, supplier, 'application/json')
          .success(()=>{
            var msg : NotifyMessage  =<NotifyMessage>{};
            msg.responseType = "success";
            msg.message = "Successfully Created New Supplier";
            defer.resolve(msg);
          }).error((data)=>{
            console.log(data);
        defer.resolve({"responseType":"error", "message" : "Failed to Create Supplier"});
      });
      return defer.promise;
    }

    public updateRecord(supplier : ISupplier):ng.IPromise<any> {
      var resourceUrl = "supplier/"+supplier.id;
      var defer = this.$q.defer();
      this.httpClient.put(resourceUrl, supplier, 'application/json').then(function successCallback(response) {
        defer.resolve();
      }, function errorCallback(response) {
        console.error(response);
      });
      return defer.promise;
    }



    public deleteRecord(supplierId : string) : ng.IPromise<any>{
      var defer = this.$q.defer();
      var resourceUrl = "supplier/"+supplierId;
        this.httpClient.delete(resourceUrl)
            .success(()=>{
              var msg : NotifyMessage  =<NotifyMessage>{};
              msg.responseType = "success";
              msg.message = "Successfully Deleted Selected Supplier";
              defer.resolve(msg);
            }).error((data)=>{
              defer.resolve("Failed to Delete Selected Supplier");
              console.log(data);
        });
      return defer.promise;
      }

      public getRecord(supplierId : string): ng.IPromise<any> {
        var defer = this.$q.defer();
      var resourceUrl = "supplier/"+supplierId
        this.httpClient.get(resourceUrl, 'application/json',
                (json: any, etag: string) => {
                  defer.resolve(json);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                  console.error(response);
                });
        return defer.promise;
      }

    /**
     * Get Supplier List for the Supplier Modal Panel
     */
    private fetchDataForPaginationTable(): Function {
      return (pageNumber:number, orderBy:string, itemPerPage: number, filterList : any): ng.IPromise<any>  => {
        var orderByClause = orderBy;
        if(orderByClause === undefined || !orderByClause)
          orderByClause = " order by name asc";

        var resourceUrl = "supplier/all/ipp/" +itemPerPage + "/page/" + pageNumber + "/order/" + orderByClause+"/filter/"+JSON.stringify(filterList);
        var defer = this.$q.defer();
        this.httpClient.get(resourceUrl, HttpClient.MIME_TYPE_JSON,
            (json: any, etag: string) => {
              defer.resolve(json);
            },
            (response: ng.IHttpPromiseCallbackArg<any>) => {
              console.error(response);
            });
        return defer.promise;
      };
    }


  }
  UMS.service("supplierService",SupplierService);

}