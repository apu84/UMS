module ums {

  export class LibrarySearchService {
    public static $inject = ['HttpClient', '$q', 'notify', '$sce', '$window'];

    constructor(private httpClient: HttpClient,
                private $q: ng.IQService, private notify: Notify,
                private $sce: ng.ISCEService, private $window: ng.IWindowService) {

    }

    public getDesiredBook(pbook: string): ng.IPromise<any> {
      var defer = this.$q.defer();
      this.httpClient.get("/ums-webservice-library/library/libraryBook/" + pbook,
          HttpClient.MIME_TYPE_JSON,
          (json: any)=> {
            defer.resolve(json.entries);
          },
          (response: ng.IHttpPromiseCallbackArg<any>)=> {
            console.error(response);
          });

      return defer.promise;
    }

    public getAllBooks() : ng.IPromise<any>{
      var defer = this.$q.defer();
      this.httpClient.get("ums-webservice-library/library/all",
          HttpClient.MIME_TYPE_JSON,
          (json: any)=> {
            defer.resolve(json.entries);
          },
          (response: ng.IHttpPromiseCallbackArg<any>)=> {
            console.error(response);
          });
      return defer.promise;
    }

    public removeBooks(pbook: string, pauthor: string): void{
      this.httpClient.delete("/ums-webservice-library/library/delete/book/" +pbook+"/author/"+ pauthor)
          .success(()=>{
            console.log("Successfully deleted");
          }).error((data)=>{
        console.log("Deletion failure");
        console.log(data);
      });
    }
  }

  UMS.service('LibrarySearchService', LibrarySearchService);
}