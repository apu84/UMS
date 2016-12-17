/**
 * Created by kawsu on 12/10/2016.
 */

module ums {

  export class LibrarySearchService {
    public static $inject = ['HttpClient', '$q', 'notify', '$sce', '$window'];

    constructor(private httpClient: HttpClient,
                private $q: ng.IQService, private notify: Notify,
                private $sce: ng.ISCEService, private $window: ng.IWindowService) {

    }

    public getDesiredBook(pbook: string): ng.IPromise<any> {
      var defer = this.$q.defer();
      this.httpClient.get("library/libraryBook/" + pbook,
          HttpClient.MIME_TYPE_JSON,
          (json: any)=> {
            defer.resolve(json.entries);
          },
          (response: ng.IHttpPromiseCallbackArg<any>)=> {
            console.error(response);
          });

      return defer.promise;
    }
  }

  UMS.service('LibrarySearchService', LibrarySearchService);
}