module ums {


  interface IConstant {
    id: number;
    name: string;
  }

  export class SystemAccountMapController {
    public static $inject = ['$scope', '$modal', '$timeout', 'SystemAccountMapService', 'accountConstants', '$q', 'AccountService'];

    private accountTypeList: IConstant[];
    private systemAccountMapList: ISystemAccountMap[];
    private systemAccountMapWithAccountType: any;
    private systemAccountMap: ISystemAccountMap;
    private accountTypeMapWithId: any;
    private accountList: IAccount[];

    constructor(private $scope: ng.IScope,
                private $modal: any,
                private $timeout: ng.ITimeoutService,
                private systemAccountMapService: SystemAccountMapService,
                private accountConstants: any,
                private $q: ng.IQService,
                private accountService: AccountService) {

    }

    public initialize() {
      this.accountTypeList = this.accountConstants.accountTypes;
      this.systemAccountMapService.getAll().then((systemAccountMapList: ISystemAccountMap[]) => {
        if (systemAccountMapList == undefined)
          this.initializeEmptySystemAccountMapList();
        else {
          this.initializeExistingSystemAccountMapList(systemAccountMapList);
        }
      });
      this.accountService.getAllAccounts().then((accountList: IAccount[]) => this.accountList = accountList);

    }


    private initializeExistingSystemAccountMapList(systemAccountMapList: ISystemAccountMap[]) {
      systemAccountMapList.forEach((a: ISystemAccountMap) => this.systemAccountMapWithAccountType[a.accountType] = a);
      this.systemAccountMapList = [];
      this.accountTypeList.forEach((a: IConstant) => {
        let systemAccountMap: ISystemAccountMap = <ISystemAccountMap>{};
        if (this.systemAccountMapWithAccountType[a.id]) {
          systemAccountMap = this.systemAccountMapWithAccountType[a.id];
        } else {
          this.initialzeSystemAccountMap(a);
        }
      });
    }


    private initializeEmptySystemAccountMapList() {
      this.systemAccountMapList = [];
      this.accountTypeList.forEach((a: IConstant) => {
        this.initialzeSystemAccountMap(a);
      });
    }

    private initialzeSystemAccountMap(a: IConstant) {
      let systemAccountMap: ISystemAccountMap = <ISystemAccountMap>{};
      systemAccountMap.accountType = a.id;
      this.systemAccountMapList.push(systemAccountMap);
      this.accountTypeMapWithId[a.id] = a;
    }

    public edit(systemAccountMap: ISystemAccountMap) {
      this.systemAccountMap = systemAccountMap;
    }

    public save(systemAccountMap: ISystemAccountMap) {
      systemAccountMap.accountId = systemAccountMap.account.id;
      this.systemAccountMapService.createOrUpdate(systemAccountMap).then((s: ISystemAccountMap) => {
        this.map(s, systemAccountMap);
      });
    }

    private map(source: ISystemAccountMap, destination: ISystemAccountMap) {
      destination.id = source.id;
      destination.accountType = source.accountType;
      destination.accountId = source.accountId;
      destination.account = source.account;
      destination.modifiedBy = source.modifiedBy;
      destination.modifierName = source.modifierName;
      destination.modifiedDate = source.modifiedDate;
    }
  }


  UMS.controller("SystemAccountMapController", SystemAccountMapController);
}