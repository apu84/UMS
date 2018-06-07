module ums {


  interface IConstant {
    id: number;
    name: string;
  }

  export class SystemAccountMapController {
    public static $inject = ['$scope', '$modal', '$timeout', 'systemAccountMapService', 'accountConstants', '$q', 'AccountService'];

    private accountTypeList: IConstant[];
    private systemAccountMapList: ISystemAccountMap[];
    private systemAccountMapListDisplay: ISystemAccountMap[];
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

      this.initialize();
    }

    public initialize() {
      this.accountTypeList = this.accountConstants.accountTypes;
      this.systemAccountMapService.getAll().then((systemAccountMapList: ISystemAccountMap[]) => {
        console.log("Existing data");
        console.log(systemAccountMapList);
        if (systemAccountMapList == undefined || systemAccountMapList.length == 0)
          this.initializeEmptySystemAccountMapList();
        else {
          this.initializeExistingSystemAccountMapList(systemAccountMapList);
        }
      });
      this.accountService.getAllAccounts().then((accountList: IAccount[]) => this.accountList = accountList);

    }


    private initializeExistingSystemAccountMapList(systemAccountMapList: ISystemAccountMap[]) {
      this.systemAccountMapList = [];
      this.systemAccountMapListDisplay = [];
      systemAccountMapList.forEach((a: ISystemAccountMap) => this.systemAccountMapWithAccountType[a.accountType] = a);
      this.accountTypeList.forEach((a: IConstant) => {
        let systemAccountMap: ISystemAccountMap = <ISystemAccountMap>{};
        systemAccountMap.accountTypeName = a.name;
        if (this.systemAccountMapWithAccountType[a.id] != undefined) {
          systemAccountMap = this.systemAccountMapWithAccountType[a.id];
        } else {
          this.initialzeSystemAccountMap(a);
        }
      });
      this.systemAccountMapListDisplay = this.systemAccountMapList;
    }


    private initializeEmptySystemAccountMapList() {
      this.systemAccountMapList = [];
      this.systemAccountMapListDisplay = [];
      this.accountTypeMapWithId = {};
      this.accountTypeList.forEach((a: IConstant) => {
        this.initialzeSystemAccountMap(a);
      });

      this.systemAccountMapListDisplay = angular.copy(this.systemAccountMapList);
    }

    private initialzeSystemAccountMap(a: IConstant) {
      let systemAccountMap: ISystemAccountMap = <ISystemAccountMap>{};
      systemAccountMap.accountType = a.id;
      systemAccountMap.accountTypeName = a.name;
      this.systemAccountMapList.push(systemAccountMap);
      this.accountTypeMapWithId[a.id] = a;
    }

    public edit(systemAccountMap: ISystemAccountMap) {
      this.systemAccountMap = systemAccountMap;
    }

    public save(systemAccountMap: ISystemAccountMap) {
      systemAccountMap.accountId = systemAccountMap.account.id;
      console.log("System account map in save");
      console.log(this.systemAccountMap);
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