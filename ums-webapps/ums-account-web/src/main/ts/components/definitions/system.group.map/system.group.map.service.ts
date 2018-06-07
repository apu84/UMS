module ums {


  export interface ISystemGroupMap {
    id: string;
    groupType: string;
    groupTypeName: string;
    group: IGroup;
    groupId: string;
    company: ICompany,
    companyId: string;
    modifiedBy: string;
    modifierName: string;
    modifiedDate: string;
  }

  export class SystemGroupMapService {

    public static $inject = ['$q', 'HttpClient'];

    private url = "account/definition/system-group-map"

    constructor(private $q: ng.IQService, private httpClient: HttpClient) {

    }

    public getAll(): ng.IPromise<ISystemGroupMap[]> {
      let defer: ng.IDeferred<ISystemGroupMap[]> = this.$q.defer();
      this.httpClient.get(this.url + "/all", HttpClient.MIME_TYPE_JSON,
          (response: any) => {
            console.log("System group map");
            console.log(response);
            defer.resolve(response);
          });

      return defer.promise;
    }

    public get(id: string): ng.IPromise<ISystemGroupMap> {
      let defer: ng.IDeferred<ISystemGroupMap> = this.$q.defer();
      this.httpClient.get(this.url + "/id/" + id, HttpClient.MIME_TYPE_JSON,
          (response: ISystemGroupMap) => defer.resolve(response));
      return defer.promise;
    }

    public post(systemGroupMap: ISystemGroupMap): ng.IPromise<ISystemGroupMap> {
      let defer: ng.IDeferred<ISystemGroupMap> = this.$q.defer();
      this.httpClient.post(this.url + "/save", systemGroupMap, HttpClient.MIME_TYPE_JSON)
          .success((response: ISystemGroupMap) => defer.resolve(response))
          .error((response) => {
            console.error(response);
            defer.resolve(undefined);
          });
      return defer.promise;
    }

    public update(systemGroupMap: ISystemGroupMap): ng.IPromise<ISystemGroupMap> {
      let defer: ng.IDeferred<ISystemGroupMap> = this.$q.defer();
      this.httpClient.put(this.url + "/update", systemGroupMap, HttpClient.MIME_TYPE_JSON)
          .success((response: ISystemGroupMap) => defer.resolve(response))
          .error((respnose) => {
            console.error(respnose);
            defer.resolve(undefined);
          });
      return defer.promise;
    }

  }

  UMS.service("SystemGroupMapService", SystemGroupMapService);
}