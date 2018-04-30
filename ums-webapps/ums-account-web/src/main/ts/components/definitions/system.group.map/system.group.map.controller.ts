module ums {

  interface IConstant {
    id: string;
    name: string;
  }

  export class SystemGroupMapController {

    public static $inject = ['$scope', '$modal', '$timeout', 'SystemGroupMapService', 'accountConstants', '$q', 'GroupService'];

    private groupTypeList: IConstant[];
    private systemGroupMapList: ISystemGroupMap[];
    private systemGroupMap: ISystemGroupMap;
    private groupTypeMapWithId: any;
    private groups: IGroup[];

    constructor(private $scope: ng.IScope,
                private $modal: any,
                private $timeout: ng.ITimeoutService,
                private systemGroupMapService: SystemGroupMapService,
                private accountConstants: any,
                private $q: ng.IQService,
                private groupService: GroupService) {
      this.initialize();

    }

    public initialize(): void {
      this.groupTypeList = [];
      this.groupTypeList = this.accountConstants.groupTypes;
      console.log(this.groupTypeList);
      this.groupTypeMapWithId = {};
      this.groupTypeList.forEach((i: IConstant) => this.groupTypeMapWithId[i.id] = i);
      this.systemGroupMapService.getAll().then((systemGroupMapList: ISystemGroupMap[]) => {
        this.systemGroupMapList = [];
        if (systemGroupMapList.length == 0)
          this.assignGroupTypeListToSystemGroupMapList();
        else
          this.systemGroupMapList = systemGroupMapList;
      });

      this.groupService.getAllGroups().then((groups: IGroup[]) => {
        this.groups = [];
        this.groups = groups;
      });
    }

    public assignGroupTypeListToSystemGroupMapList() {
      this.systemGroupMapList = [];
      for (let i = 0; i < this.groupTypeList.length; i++) {
        let systemGroupMap: ISystemGroupMap = <ISystemGroupMap>{};
        systemGroupMap.groupType = this.groupTypeList[i].id;
        systemGroupMap.groupTypeName = this.groupTypeList[i].name;
        this.systemGroupMapList.push(systemGroupMap);
      }
    }

    public edit(systemGroupMap: ISystemGroupMap) {
      console.log("In the edit section");
      this.systemGroupMap = systemGroupMap;
    }

    public save() {
      this.systemGroupMap.groupId = this.systemGroupMap.group.stringId;
      if (this.systemGroupMap.id == null) {
        this.systemGroupMapService.post(this.systemGroupMap).then((systemGroupMap: ISystemGroupMap) => this.systemGroupMap = systemGroupMap);
      } else {
        this.systemGroupMapService.update(this.systemGroupMap).then((systemGroupMap: ISystemGroupMap) => this.systemGroupMap = systemGroupMap);
      }
    }


  }

  UMS.controller("SystemGroupMapController", SystemGroupMapController);
}