module ums {
  export interface IGroup {
    id: string;
    stringId: string;
    compCode: string;
    groupCode: string;
    displayCode: string;
    groupName: string;
    mainGroup: string;
    mainGroupObject: IGroup;
    flag: string;
    flagBoolValue: boolean;$
    taxLimit: number;
    tdsPercent: number;
    defaultComp: string;
    statusFlag: string;
    modifiedDate: string;
    modifiedBy: string;
  }

  export enum GroupFlag{
    YES="Y",
    NO="N"
  }

  export class GroupService {
    public static $inject = ['$q', 'HttpClient'];

    private groupServiceURL: string = "";

    constructor(private $q: ng.IQService, private httpClient: HttpClient) {
      this.groupServiceURL = "account/definition/group"
    }

    public getAllGroups(): ng.IPromise<IGroup[]> {
      let defer: ng.IDeferred<IGroup[]> = this.$q.defer();
      this.httpClient.get(this.groupServiceURL + "/all", HttpClient.MIME_TYPE_JSON,
          (response: IGroup[]) => defer.resolve(response));
      return defer.promise;
    }

    public saveAGroup(json: any): ng.IPromise<IGroup[]> {
      let defer: ng.IDeferred<IGroup[]> = this.$q.defer();
      this.httpClient.post(this.groupServiceURL + "/save", json, HttpClient.MIME_TYPE_JSON)
          .success((response: IGroup[]) => defer.resolve(response))
          .error((error) => {
            console.error(error);
            defer.resolve(undefined);
          });
      return defer.promise;
    }

    public deleteAGroup(group:IGroup):ng.IPromise<IGroup[]>{
        let defer: ng.IDeferred<IGroup[]> = this.$q.defer();
        this.httpClient.post(this.groupServiceURL+"/delete", group, HttpClient.MIME_TYPE_JSON)
            .success((response:IGroup[])=>defer.resolve(response))
            .error((error) => {
                console.error(error);
                defer.resolve(undefined);
            });
        return defer.promise;
    }

    public saveAllGroup(groups:IGroup[]):ng.IPromise<IGroup[]>{
      let defer: ng.IDeferred<IGroup[]>=this.$q.defer();
      this.httpClient.post(this.groupServiceURL+"/saveAll", groups, HttpClient.MIME_TYPE_JSON)
          .success((response:IGroup[])=>defer.resolve(response))
          .error((error)=>{
            console.error(error);
            defer.resolve(undefined);
          });

      return defer.promise;
    }

  }

  UMS.service("GroupService", GroupService);
}