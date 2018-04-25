module ums {

  export class GroupController {
    public static $inject = ['$scope', '$modal', 'notify', 'GroupService'];

    public groups: IGroup[];
    public tempGroup: IGroup[];
    public addedGroup: IGroup;
    public removedGroup: IGroup;
    public groupMapWithId: any;
    public gridOptions: any;
    public handsOnTableFeature: any;

    constructor($scope: ng.IScope, private $modal: any, private notify: Notify, private groupService: GroupService) {

      this.groupMapWithId = {};
      this.gridOptions = <GridOptions>{};

      // this.gridOptions.data = {};
      this.initialize();
      this.initializeHandsOnTable();


    }

    private addModalClicked() {
      this.addedGroup = <IGroup>{};
      this.addedGroup.mainGroup = "";
    }

    private removeButtonClicked(group: IGroup) {
      this.removedGroup = group;
      console.log("Removed group");
      console.log(this.removedGroup);
    }

    private initialize() {
      this.groupService.getAllGroups().then((groups: IGroup[]) => {
        this.assignToGroupAndMap(groups);

      });


    }


    private remove() {
      this.groupService.deleteAGroup(this.removedGroup).then((groups) => {
        this.removedGroup = <IGroup>{};
        this.assignToGroupAndMap(groups);
      });
    }

    private assignToGroupAndMap(groups: IGroup[]) {
      this.groups = [];
      this.tempGroup = [];
      this.groups = groups;
      this.tempGroup = angular.copy(groups);
      this.groups.forEach((g: IGroup) => {
        this.groupMapWithId[g.groupCode] = g;
        g.mainGroupObject = this.groupMapWithId[g.mainGroup];
        g.flagBoolValue = (g.flag == null || g.flag == "N") ? false : true;
      });
    }

    private undo() {
      this.groups = [];
      this.groups = angular.copy(this.tempGroup);
    }

    private initializeHandsOnTable() {
      this.handsOnTableFeature = {};
      this.handsOnTableFeature = {
        settings: {
          colHeaders: true,
          rowHeaders: true,
          currentRowClassName: 'currentRow',
          currentColClassName: 'currentCol',
          fillHandle: false,
          manualRowResize: true,
          manualColumnResize: true,
          columnSorting: true,
          sortIndicator: true,
          readOnly: true,
          stretchH: 'all',
          height: $(".page-content").height() - 5,
          observeChanges: true,
          search: true,
          columns: [
            {"title": "Group Name", "data": "groupName"},
            {"title": "Under The Main Group Of", "data": "mainGroupObject.groupName"},
            {"title": "Sub Ledger", "data": "flag"}
          ]
        }
      };
    }


    private add() {
      this.addedGroup.mainGroup = this.addedGroup.mainGroupObject.groupCode;
      this.addedGroup.flag = this.addedGroup.flagBoolValue == true ? "Y" : "N";
      this.groups.push(this.addedGroup);
    }

    private saveOne() {
      this.addedGroup.mainGroup = this.addedGroup.mainGroupObject.groupCode;
      this.addedGroup.flag = this.addedGroup.flagBoolValue == true ? "Y" : "N";
      this.groupService.saveAGroup(this.addedGroup).then((groups) => {
        console.log("Fetched groups");
        this.addedGroup = <IGroup>{};
        this.assignToGroupAndMap(groups);

      });
    }

    private saveAll() {
      let newlyAddedGroups: IGroup[] = [];
      newlyAddedGroups = this.groups.filter((g: IGroup) => g.stringId == null);
      this.groupService.saveAllGroup(newlyAddedGroups).then((groups) => {
        this.assignToGroupAndMap(groups);
      });
    }
  }


  UMS.controller("GroupController", GroupController);
}