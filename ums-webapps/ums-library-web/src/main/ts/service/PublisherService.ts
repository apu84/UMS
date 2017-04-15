module ums{

  export class PublisherService extends  BaseService {
    public static $inject = ['libConstants','HttpClient','$q','notify','$sce','$window','MessageFactory'];

    constructor(private libConstants: any, private httpClient: HttpClient,
                private $q:ng.IQService, private notify: Notify,
                private $sce:ng.ISCEService,private $window:ng.IWindowService, private messageFactory : MessageFactory) {

      super(libConstants,httpClient,$q, notify, $sce, $window, messageFactory);
    }

    public createNewRecord(publisher : IPublisher):ng.IPromise<any> {
      var resourceUrl = "publisher";
      return super.bCreateNewRecord(resourceUrl,resourceUrl,publisher);
    }

    public updateRecord(publisher : IPublisher):ng.IPromise<any> {
      var resourceUrl = "publisher/"+publisher.id;
      return super.bUpdateRecord(resourceUrl, publisher, "publisher");
    }

    public deleteRecord(publisherId : string) : ng.IPromise<any> {
      var resourceUrl = "publisher/"+publisherId;
      return super.bDeleteRecord(resourceUrl, "supplier");
    }

    public getRecord(publisherId : string): ng.IPromise<any> {
      var resourceUrl = "publisher/"+publisherId
      return super.bGetRecord(resourceUrl);
    }

    private fetchDataForPaginationTable(): Function {
      return super.bFetchDataForPaginationTable("publisher","name");
    }

    public fetchAllPublishers() : ng.IPromise<any> {
      var resourceUrl = "publisher/all";
      return super.bGetAllRecord(resourceUrl);

    }


  }
  UMS.service("publisherService",PublisherService);

}