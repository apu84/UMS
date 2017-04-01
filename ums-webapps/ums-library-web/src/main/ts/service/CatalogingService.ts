module ums{

  export class CatalogingService{
    public static $inject = ['libConstants','HttpClient','$q','notify','$sce','$window'];
    constructor(private libConstants: any, private httpClient: HttpClient,
                private $q:ng.IQService, private notify: Notify,
                private $sce:ng.ISCEService,private $window:ng.IWindowService) {
    }

    /**
     * Fetch Items for a given Record Id
     */
    public fetchItems(masterFileNo : string): ng.IPromise<any> {
      var defer = this.$q.defer();
      var resourceUrl = "item/mfn/"+masterFileNo
      this.httpClient.get(resourceUrl, 'application/json',
          (json: any, etag: string) => {
            defer.resolve(json.entries);
          },
          (response: ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });
      return defer.promise;
    }


    /**
     * Save a Record. It will either Create a new record or Update an existing record.
     */
    public saveRecord(record : IRecord):ng.IPromise<any> {
      var resourceUrl = "record";
      var defer = this.$q.defer();

      record.contributorJsonString = JSON.stringify(record.contributorList);
      record.noteJsonString = JSON.stringify(record.noteList);
      record.subjectJsonString = JSON.stringify(record.subjectList);

      if (record.mfnNo != undefined) {
        this.httpClient.put(resourceUrl + '/' + record.mfnNo, record, 'application/json')
            .success(() => {
              var msg : NotifyMessage  =<NotifyMessage>{};
              msg.responseType = "success";
              msg.message = "Data Saved Updated";
              defer.resolve(msg);
            }).error((data) => {
        });
      }
      else {
        this.httpClient.post(resourceUrl, record, 'application/json').then((response : any) => {
          var recordId = Utils.getIdFromUrl(response.headers('Location'));
          record.mfnNo = recordId;
          var msg : NotifyMessage  =<NotifyMessage>{};
          msg.responseType = "success";
          msg.message = "Data Saved Successfully";
          defer.resolve(msg);

        }, function errorCallback(response) {
          console.error(response);
          defer.resolve({"responseType":"error", "message" : "Failed to Create Supplier"});
        });
      }
      return defer.promise;
    }


    /**
     * Save a Item. It will either Create a new Item or Update an existing Item.
     */
    public saveItem(item : IItem):ng.IPromise<any> {
      var resourceUrl = "item";
      var defer = this.$q.defer();

      if (item.itemId != undefined) {
        this.httpClient.put(resourceUrl + '/' + item.itemId, item, 'application/json')
            .success(() => {
              var msg : NotifyMessage  =<NotifyMessage>{};
              msg.responseType = "success";
              msg.message = "Data Saved Updated";
              defer.resolve(msg);
            }).error((data) => {
        });
      }
      else {
        this.httpClient.post(resourceUrl, item, 'application/json').then((response : any) => {
          var itemId = Utils.getIdFromUrl(response.headers('Location'));
          item.itemId = itemId;
          var msg : NotifyMessage  =<NotifyMessage>{};
          msg.responseType = "success";
          msg.message = "Data Saved Successfully";
          defer.resolve(msg);

        }, function errorCallback(response) {
          console.error(response);
          defer.resolve({"responseType":"error", "message" : "Failed to Create Supplier"});
        });
      }
      return defer.promise;
    }

    public saveBulkItems(complete_json : any): ng.IPromise<any> {
      var resourceUrl = "item/batch";
      var defer = this.$q.defer();
      this.httpClient.post(resourceUrl, complete_json, 'application/json').then((response : any) => {
        var msg : NotifyMessage  =<NotifyMessage>{};
        msg.responseType = "success";
        msg.message = "Data Saved Successfully";
        defer.resolve(msg);
      }, function errorCallback(response) {
        defer.resolve({"responseType":"error", "message" : "Failed to Create Supplier"});
      });
      return defer.promise;
    }

    public  setBulkItemsValue(bulkItemList: Array<IItem>, config : any) : void {
      var copyStartFrom: number = 0;
      var incrementSegment;
      var firstAccession;

      for (var i: number = 0; i < bulkItemList.length; i++) {
        var item: IItem = bulkItemList[i];

        if (config.copyStartFrom != "") {
          item.copyNumber = Number(config.copyStartFrom) + i;
        }
        if (config.internalNote != "") {
          item.internalNote = config.internalNote;
        }
        item.status = config.status;
        item.price = Number(config.price);
        item.accessionDate = config.accessionDate;
        item.barcode = config.barcode;

        if (config.firstAccession != "" && config.incrementSegment != "") {
          firstAccession = config.firstAccession;
          incrementSegment = config.incrementSegment;
        }
        var serial = Number(incrementSegment) + i;
        item.accessionNumber = firstAccession.replace(incrementSegment, serial);
      }
    }
  }

  UMS.service("catalogingService",CatalogingService);
}