module ums {
  export class BankHttpService {
    public static $inject = ['$q', 'HttpClient', 'notify'];

    constructor(private $q: ng.IQService,
                private httpClient: HttpClient,
                private notify: Notify) {

    }

    public getResource<T>(resource: BankResource<T>, conveter: Converter<T>): ng.IPromise<T> {
      let defer: ng.IDeferred<T> = this.$q.defer();
      this.httpClient.get(resource.getUri(), HttpClient.MIME_TYPE_JSON,
          (response: any) => defer.resolve(conveter.deserialize(response)));
      return defer.promise;
    }

    public postResource<T>(resource: BankResource<T>, converter: Converter<T>): ng.IPromise<T> {
      let defer: ng.IDeferred<T> = this.$q.defer();
      this.httpClient.post(resource.getUri(), converter.serialize(resource.getData()),
          HttpClient.MIME_TYPE_JSON)
          .success((response: any) => defer.resolve(converter.deserialize(response)))
          .error((response: any) => {
            this.notify.error("Error while creating");
            return defer.resolve(null);
          });
      return defer.promise;
    }

    public putResource<T>(resource: BankResource<T>, converter: Converter<T>): ng.IPromise<T> {
      let defer: ng.IDeferred<T> = this.$q.defer();
      this.httpClient.put(resource.getUri(), converter.serialize(resource.getData()),
          HttpClient.MIME_TYPE_JSON)
          .success((response: any) => defer.resolve(converter.deserialize(response)))
          .error((response: any) => {
            this.notify.error("Error while updating");
            return defer.resolve(null);
          });
      return defer.promise;
    }

    public deleteResource<T>(resource: BankResource<T>): ng.IPromise<boolean> {
      let defer: ng.IDeferred<boolean> = this.$q.defer();
      this.httpClient.doDelete(resource.getUri())
          .success((response: any) => defer.resolve(true))
          .error((response: any) => {
            this.notify.error("Error while deleting");
            return defer.resolve(false);
          });
      return defer.promise;
    }
  }

  UMS.service('BankHttpService', BankHttpService);
}
