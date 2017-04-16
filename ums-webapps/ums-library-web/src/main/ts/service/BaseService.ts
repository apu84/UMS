module ums{

  /**
   * Base service for Modal Windows CRUD Operation.
   * Here b in every method is a prefix indicates that this is a method for base Service
   */
  export class BaseService {

    constructor(private bLibConstants: any, private bHttpClient: HttpClient,
                private b$q: ng.IQService, private bNotify: Notify,
                private b$sce: ng.ISCEService, private b$window: ng.IWindowService, private bMessageFactory: MessageFactory) {
    }

    public bCreateNewRecord(resourceUrl: string,messagePostfix : string, entry: any): ng.IPromise<any> {
        var defer = this.b$q.defer();
        // console.log(this.bLibConstants.mimeTypeJson);
        this.bHttpClient.post(resourceUrl, entry, 'application/json')
            .success(() => {
              defer.resolve(this.bMessageFactory.getSuccessMessage("Successfully created new "+messagePostfix));
            }).error((data) => {
          defer.resolve(this.bMessageFactory.getErrorMessage("Failed to create "+messagePostfix));
        });
      return defer.promise;
      }

    public bUpdateRecord(resourceUrl : string, entry : any, messagePostfix : string):ng.IPromise<any> {
      var defer = this.b$q.defer();
      this.bHttpClient.put(resourceUrl, entry, 'application/json').then((response) => {
        defer.resolve(this.bMessageFactory.getSuccessMessage("Successfully updated selected "+messagePostfix));
      }, function errorCallback(response) {
        console.error(response);
        defer.resolve(this.bMessageFactory.getErrorMessage("Failed to update  "+messagePostfix));
      });
      return defer.promise;
    }

    public bDeleteRecord(resourceUrl : string, messagePostfix : string) : ng.IPromise<any>{
      var defer = this.b$q.defer();
      this.bHttpClient.delete(resourceUrl)
          .success(()=>{
            defer.resolve(this.bMessageFactory.getSuccessMessage("Successfully deleted new "+messagePostfix));
          }).error((data)=>{
        defer.resolve(this.bMessageFactory.getErrorMessage("Failed to delete selected "+messagePostfix));
        console.log(data);
      });
      return defer.promise;
    }

    public bGetRecord(resourceUrl : string): ng.IPromise<any> {
      var defer = this.b$q.defer();
      this.bHttpClient.get(resourceUrl, 'application/json',
          (json: any, etag: string) => {
            defer.resolve(json);
          },
          (response: ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });
      return defer.promise;
    }

    protected bFetchDataForPaginationTable(urlPrefix:string, defaultOrderByField : string): Function {
      return (pageNumber:number, orderBy:string, itemPerPage: number, filterList : any): ng.IPromise<any>  => {
        var orderByClause = orderBy;

        if(orderByClause === undefined || !orderByClause)
          orderByClause = " order by "+defaultOrderByField+" asc";

        var resourceUrl = urlPrefix+"/all/ipp/" +itemPerPage + "/page/" + pageNumber + "/order/" + orderByClause+"/filter/"+JSON.stringify(filterList);
        var defer = this.b$q.defer();
        this.bHttpClient.get(resourceUrl, HttpClient.MIME_TYPE_JSON,
            (json: any, etag: string) => {
              defer.resolve(json);
            },
            (response: ng.IHttpPromiseCallbackArg<any>) => {
              console.error(response);
            });
        return defer.promise;
      };
    }

    public bGetAllRecord(resourceUrl : string): ng.IPromise<any> {
      var defer = this.b$q.defer();
      this.bHttpClient.get(resourceUrl, 'application/json',
          (json: any, etag: string) => {
            defer.resolve(json);
          },
          (response: ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });
      return defer.promise;
    }

  }
  }