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
    private config: any;

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
      this.accountTypeMapWithId = {};
      this.systemAccountMapList = [];
      this.systemAccountMapListDisplay = [];
      this.systemAccountMapWithAccountType = {};
      this.systemAccountMapService.getAll().then((systemAccountMapList: ISystemAccountMap[]) => {
        console.log("From get all");
        console.log(systemAccountMapList);
        if (systemAccountMapList == undefined || systemAccountMapList.length == 0)
          this.initializeEmptySystemAccountMapList();
        else {
          this.initializeExistingSystemAccountMapList(systemAccountMapList);
        }
      });
      this.accountService.getAllAccounts().then((accountList: IAccount[]) => this.accountList = accountList);

    }

    public initializeSelectize() {
      this.config = {
        create: true,
        valueField: 'id',
        labelField: 'accountName',
        delimiter: '|',
        placeholder: 'Select Account'
      }
    }


    private initializeExistingSystemAccountMapList(systemAccountMapList: ISystemAccountMap[]) {


      systemAccountMapList.forEach((a: ISystemAccountMap) => this.systemAccountMapWithAccountType[a.accountType] = a);
      this.accountTypeList.forEach((a: IConstant) => {
        let systemAccountMap: ISystemAccountMap = <ISystemAccountMap>{};
        if (this.systemAccountMapWithAccountType[a.id] != undefined) {
          systemAccountMap = this.systemAccountMapWithAccountType[a.id];
        } else {
          systemAccountMap = this.initialzeSystemAccountMap(a);
        }
        systemAccountMap.accountTypeName = a.name;
        this.systemAccountMapList.push(systemAccountMap);
      });
      this.systemAccountMapListDisplay = this.systemAccountMapList;
    }


    private initializeEmptySystemAccountMapList() {

      this.accountTypeList.forEach((a: IConstant) => {
        let systemAccountMap: ISystemAccountMap = this.initialzeSystemAccountMap(a);
        this.systemAccountMapList.push(systemAccountMap);
      });

      this.systemAccountMapListDisplay = angular.copy(this.systemAccountMapList);
    }

    private initialzeSystemAccountMap(a: IConstant): ISystemAccountMap {
      let systemAccountMap: ISystemAccountMap = <ISystemAccountMap>{};
      systemAccountMap.accountType = a.id;
      systemAccountMap.accountTypeName = a.name;
      this.accountTypeMapWithId[a.id] = a;
      return systemAccountMap;
    }

    public edit(systemAccountMap: ISystemAccountMap) {
      this.systemAccountMap = <ISystemAccountMap>{};
      this.systemAccountMap = systemAccountMap;
    }

    public save(systemAccountMap: ISystemAccountMap) {
      this.systemAccountMap.accountId = systemAccountMap.account.id;
      this.systemAccountMapService.createOrUpdate(this.systemAccountMap).then((s: ISystemAccountMap) => {
        this.map(s, this.systemAccountMap);
      });
    }

    private map(source: ISystemAccountMap, destination: ISystemAccountMap) {
      console.log("Source");
      console.log(source);
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