module ums {

  interface IConstant {
    id: string;
    name: string;
  }

  export class SystemGroupMapController {

    public static $inject = ['$scope', '$modal', '$timeout', 'SystemGroupMapService', 'accountConstants', '$q', 'GroupService'];

    private groupTypeList: IConstant[];
    private systemGroupMapList: ISystemGroupMap[];
    private systemGroupMapListDisplay: ISystemAccountMap[];
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

        this.assignGroupTypeListToSystemGroupMapList(systemGroupMapList);

      });

      this.groupService.getAllGroups().then((groups: IGroup[]) => {
        this.groups = [];
        this.groups = groups;
      });
    }

    public assignGroupTypeListToSystemGroupMapList(systemGroupMapList: ISystemGroupMap[]) {
      let systemGroupMapWithGroupTypeId:any={};
      systemGroupMapList.forEach((s:ISystemGroupMap)=> systemGroupMapWithGroupTypeId[s.groupType]=s);
      this.systemGroupMapList = [];
      this.systemGroupMapListDisplay = [];
      for (let i = 0; i < this.groupTypeList.length; i++) {
        let systemGroupMap: ISystemGroupMap = <ISystemGroupMap>{};
        if(systemGroupMapWithGroupTypeId[this.groupTypeList[i].id]!=null){
          systemGroupMap = systemGroupMapWithGroupTypeId[this.groupTypeList[i].id];
        }else{
            systemGroupMap.groupType = this.groupTypeList[i].id;
        }
          systemGroupMap.groupTypeName = this.groupTypeList[i].name;
          this.systemGroupMapList.push(systemGroupMap);
      }

      this.systemGroupMapListDisplay = angular.copy(this.systemGroupMapList);
      console.log("System group map");
      console.log(this.systemGroupMapList);
    }

    public edit(systemGroupMap: ISystemGroupMap) {
      console.log("In the edit section");
      this.systemGroupMap = systemGroupMap;
    }

    public save(systemGroupMap:ISystemGroupMap) {
      console.log("Existing system group map");
      console.log(systemGroupMap);
      systemGroupMap.groupId = this.systemGroupMap.group.id;
      if (this.systemGroupMap.id == null) {
        this.systemGroupMapService.post(systemGroupMap).then((output: ISystemGroupMap) => {
          this.map(output, systemGroupMap);
        });
      } else {
        console.log("In the update section");
        this.systemGroupMapService.update(systemGroupMap).then((output: ISystemGroupMap) => this.map(output, systemGroupMap));
      }
    }

    private map(source:ISystemGroupMap, destination: ISystemGroupMap){
      destination.id=source.id;
      destination.groupType = source.groupType;
      destination.group = source.group;
      destination.groupTypeName = this.groupTypeMapWithId[source.groupType].name;
      destination.company = source.company;
      destination.companyId = source.companyId;
      destination.modifiedBy = source.modifiedBy;
      destination.modifiedDate = source.modifiedDate;
      destination.modifierName = source.modifierName;
    }


  }

  UMS.controller("SystemGroupMapController", SystemGroupMapController);
}